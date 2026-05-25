import sys
import os
import grpc
# python -m pip install grpcio grpcio-tools
# python -m grpc_tools.protoc -I . --python_out=. --grpc_python_out=. judge.proto

# Đảm bảo Python nhận diện đúng thư mục để import các file sinh ra từ proto
sys.path.append(os.path.dirname(os.path.abspath(__file__)))
import judge_pb2
import judge_pb2_grpc

def run():
    # 1. Kết nối đến gRPC Server (plaintext, không TLS) tại cổng 2240
    server_address = "36.50.135.242:2240"
    channel = grpc.insecure_channel(server_address)
    stub = judge_pb2_grpc.JudgeServiceStub(channel)

    student_code = "B22DCCN292"
    question_alias = "4DCGleoI"

    # 2. Gửi yêu cầu Request để nhận đề bài
    print("Đang gửi yêu cầu Request...")
    request = judge_pb2.JudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.Request(request)
        print("Đã nhận phản hồi từ Server:")
        print(f"Request ID: {response.request_id}")
        print(f"Data nhận được: '{response.data}'")
        
        # 3. Tách dữ liệu số nguyên phân tách bằng dấu phẩy và tính tổng
        # Ví dụ: data = "1,2,3,4"
        if not response.data:
            print("Lỗi: Server không trả về dữ liệu số!")
            return
            
        # Dùng kỹ thuật cắt chuỗi và list comprehension của Python
        numbers = [int(x) for x in response.data.split(",") if x.strip()]
        total_sum = sum(numbers)
        print(f"Danh sách số: {numbers}")
        print(f"Tổng tính được: {total_sum}")

        # 4. Gửi kết quả lại bằng phương thức Submit (answer ở dạng chuỗi)
        submit_req = judge_pb2.SubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            answer=str(total_sum)
        )
        
        print("Đang gửi kết quả Submit...")
        submit_res = stub.Submit(submit_req)
        print("Kết quả từ Server:")
        print(f"Status: {submit_res.status}")
        print(f"Message: {submit_res.message}")
        
    except grpc.RpcError as e:
        print(f"Lỗi gRPC xảy ra: {e.code()} - {e.details()}")

if __name__ == "__main__":
    run()
