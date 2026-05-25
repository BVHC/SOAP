import sys
import os
import grpc

# Đảm bảo Python nhận diện đúng thư mục để import các file sinh ra từ proto
sys.path.append(os.path.dirname(os.path.abspath(__file__)))
import judge_pb2
import judge_pb2_grpc

def run():
    # 1. Kết nối đến gRPC Server (plaintext, không TLS) tại cổng 2240
    server_address = "36.50.135.242:2240"
    channel = grpc.insecure_channel(server_address)
    
    # Chú ý sử dụng TypedJudgeServiceStub thay vì JudgeServiceStub cũ
    stub = judge_pb2_grpc.TypedJudgeServiceStub(channel)

    student_code = "B22DCCN292"
    question_alias = "xw74a56O"

    # 2. Gửi yêu cầu RequestTyped để nhận đề bài
    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print("Đã nhận phản hồi từ Server:")
        print(f"Request ID: {response.request_id}")
        
        # 3. Phân tích dữ liệu text_batch
        if not response.HasField("text_batch"):
            print("Lỗi: Server không trả về kiểu dữ liệu text_batch!")
            return
            
        text_batch = response.text_batch
        print(f"Mode nhận được: {text_batch.mode}")
        
        # Các nhãn cần đếm
        targets = ["account", "payment", "refund", "shipping"]
        
        # Tạo Dictionary để lưu số lượng đếm được cho mỗi nhãn
        counts = {t: 0 for t in targets}
        
        # Duyệt qua từng entry (mỗi phần tử trong list entries)
        for entry in text_batch.entries:
            # Chuyển thành chữ thường để so sánh không phân biệt hoa/thường
            entry_lower = entry.lower()
            for t in targets:
                # Kiểm tra nếu nhãn xuất hiện trong chuỗi entry
                if t in entry_lower:
                    counts[t] += 1
                    
        # Lọc ra các nhãn có count > 0 và sắp xếp theo thứ tự ABC
        values = []
        final_counts = {}
        
        # Hàm sorted(targets) đảm bảo danh sách đã được sắp xếp tăng dần theo ABC
        for t in sorted(targets):
            if counts[t] > 0:
                values.append(t)
                final_counts[t] = counts[t]
                
        print(f"Danh sách nhãn có xuất hiện (values): {values}")
        print(f"Số lượng tương ứng (counts): {final_counts}")

        # 4. Đóng gói vào đối tượng TextBatchAnswer
        text_answer = judge_pb2.TextBatchAnswer(
            values=values,
            counts=final_counts
        )
        
        # Tạo request submit tổng thể
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            text_batch_answer=text_answer
        )
        
        print("\nĐang gửi kết quả SubmitTyped...")
        submit_res = stub.SubmitTyped(submit_req)
        print("Kết quả từ Server:")
        print(f"Status: {submit_res.status}")
        print(f"Message: {submit_res.message}")
        
    except grpc.RpcError as e:
        print(f"Lỗi gRPC xảy ra: {e.code()} - {e.details()}")

if __name__ == "__main__":
    run()
