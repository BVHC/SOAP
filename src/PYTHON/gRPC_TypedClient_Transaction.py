import sys
import os
import grpc

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
    question_alias = "rqfemEcq" # Mã câu hỏi mới

    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print(f"Đã nhận phản hồi từ Server, Request ID: {response.request_id}")
        
        # 2. Xử lý dữ liệu giao dịch
        if not response.HasField("transaction_risk_batch"):
            print("Lỗi: Server không trả về dữ liệu transaction_risk_batch!")
            return
            
        transactions = response.transaction_risk_batch.transactions
        print(response)
        print(f"Đã nhận {len(transactions)} giao dịch để kiểm tra.")
        
        high_risk_ids = []
        total_amount = 0.0
        
        # 3. Phân loại giao dịch rủi ro
        for tx in transactions:
            # Điều kiện review:
            # - amount >= 5000, HOẶC
            # - chargeback_count >= 2, HOẶC
            # - new_device=true VÀ country khác "VN"
            if tx.amount >= 5000 or tx.chargeback_count >= 2 or (tx.new_device and tx.country != "VN"):
                high_risk_ids.append(tx.transaction_id)
                total_amount += tx.amount
                
        # 4. Tính toán kết quả
        review_count = len(high_risk_ids)
        total_amount = round(total_amount, 2) # Làm tròn 2 chữ số thập phân
        
        print(f"Số lượng giao dịch cần review (review_count): {review_count}")
        print(f"Tổng tiền rủi ro (total_high_risk_amount): {total_amount}")
        
        # 5. Đóng gói vào đối tượng TransactionRiskAnswer
        answer = judge_pb2.TransactionRiskAnswer(
            high_risk_transaction_ids=high_risk_ids,
            review_count=review_count,
            total_high_risk_amount=total_amount
        )
        
        # Gửi SubmitTyped
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            transaction_risk_answer=answer
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
