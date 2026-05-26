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
    question_alias = "BKjXY6Mo" # Mã câu hỏi bài Shipping

    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print(f"Đã nhận phản hồi từ Server, Request ID: {response.request_id}")
        
        # 2. Xử lý dữ liệu báo giá vận chuyển
        if not response.HasField("shipping_quote"):
            print("Lỗi: Server không trả về dữ liệu shipping_quote!")
            return
            
        shipping_data = response.shipping_quote
        weight_kg = shipping_data.weight_kg
        max_eta = shipping_data.max_eta_days
        quotes = shipping_data.quotes
        
        print(f"Cân nặng: {weight_kg}kg, Thời gian giao hàng tối đa (max_eta): {max_eta} days")
        print(f"Đã nhận được {len(quotes)} báo giá vận chuyển từ các hãng.")
        
        # 3. Lọc và Tìm báo giá rẻ nhất
        best_quote = None
        best_fee = float('inf') # Khởi tạo giá trị vô cùng lớn
        
        for q in quotes:
            # Điều kiện 1: Chỉ xét các báo giá có eta_days <= max_eta_days
            if q.eta_days <= max_eta:
                # Tính tổng chi phí: total_fee = base_fee + weight_kg * per_kg_fee
                fee = q.base_fee + weight_kg * q.per_kg_fee
                fee = round(fee, 2) # Làm tròn 2 chữ số thập phân
                
                # Cập nhật nếu giá rẻ hơn, hoặc giá bằng nhau nhưng reliability cao hơn
                if fee < best_fee:
                    best_fee = fee
                    best_quote = q
                elif fee == best_fee:
                    # Giá bằng nhau thì chọn độ tin cậy cao hơn
                    if best_quote is None or q.reliability > best_quote.reliability:
                        best_quote = q
                        best_fee = fee
                        
        if best_quote is None:
            print("Lỗi: Không tìm thấy báo giá nào phù hợp với yêu cầu thời gian!")
            return
            
        print(f"\n👉 ĐÃ CHỌN HÃNG VẬN CHUYỂN: {best_quote.carrier}")
        print(f"   Tổng phí: {best_fee}")
        print(f"   Thời gian giao (ETA): {best_quote.eta_days} ngày")
        print(f"   Độ tin cậy: {best_quote.reliability}")
        
        # 4. Đóng gói vào đối tượng ShippingQuoteAnswer
        answer = judge_pb2.ShippingQuoteAnswer(
            carrier=best_quote.carrier,
            total_fee=best_fee,
            eta_days=best_quote.eta_days
        )
        
        # Gửi SubmitTyped
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            shipping_quote_answer=answer
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
