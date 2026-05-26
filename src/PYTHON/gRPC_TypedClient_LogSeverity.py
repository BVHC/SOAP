import sys
import os
import grpc
import re

# Đảm bảo Python nhận diện đúng thư mục để import các file sinh ra từ proto
sys.path.append(os.path.dirname(os.path.abspath(__file__)))
import judge_pb2
import judge_pb2_grpc

def run():
    # 1. Kết nối đến gRPC Server
    server_address = "36.50.135.242:2240"
    channel = grpc.insecure_channel(server_address)
    stub = judge_pb2_grpc.TypedJudgeServiceStub(channel)

    student_code = "B22DCCN292"
    question_alias = "7mkFY8jV" # Mã câu hỏi bài đếm Log Severity

    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print(f"Đã nhận phản hồi từ Server, Request ID: {response.request_id}")
        print(f"res: {response}")
        # 2. Xử lý dữ liệu TextBatch
        if not response.HasField("text_batch"):
            print("Lỗi: Server không trả về dữ liệu text_batch!")
            return
            
        text_batch = response.text_batch
        print(f"Chế độ nhận được: {text_batch.mode}")
        
        counts = {}
        first_error_code = None
        
        # 3. Phân tích từng dòng log
        for entry in text_batch.entries:
            entry = entry.strip()
            if not entry:
                continue
                
            # a. Đếm số dòng theo severity đầu dòng
            # split() mặc định sẽ cắt theo khoảng trắng, phần tử [0] chính là chữ đầu tiên (VD: INFO, ERROR)
            parts = entry.split()
            if parts:
                severity = parts[0]
                if severity not in counts:
                    counts[severity] = 0
                counts[severity] += 1
                
            # b. Tìm mã lỗi đầu tiên xuất hiện (theo mẫu code=...)
            if first_error_code is None:
                # Dùng regex tìm chữ code=, và lấy toàn bộ ký tự phía sau cho đến khi gặp khoảng trắng
                match = re.search(r'code=([^\s\|]+)', entry)
                if match:
                    first_error_code = match.group(1)             
        # values chỉ chứa mã lỗi đầu tiên (nếu có)
        values = []
        if first_error_code:
            values.append(first_error_code)
            
        print(f"Số lượng các dòng Log (counts): {counts}")
        print(f"Mã lỗi đầu tiên tìm thấy (values): {values}")
        
        # 4. Đóng gói vào đối tượng TextBatchAnswer
        answer = judge_pb2.TextBatchAnswer(
            values=values,
            counts=counts
        )
        
        # Gửi SubmitTyped
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            text_batch_answer=answer
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
