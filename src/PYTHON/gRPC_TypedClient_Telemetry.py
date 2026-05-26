import sys
import os
import grpc
import math

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
    question_alias = "n72HMMVP" # Mã câu hỏi bài Sensor Telemetry

    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print(f"Đã nhận phản hồi từ Server, Request ID: {response.request_id}")
        
        # 2. Lấy dữ liệu Telemetry
        if not response.HasField("sensor_telemetry"):
            print("Lỗi: Server không trả về dữ liệu sensor_telemetry!")
            return
            
        telemetry = response.sensor_telemetry
        threshold = telemetry.threshold
        readings = telemetry.readings
        
        n = len(readings)
        print(f"Đã nhận {n} bản ghi readings. Ngưỡng (threshold): {threshold}")
        
        if n == 0:
            print("Không có dữ liệu đọc nào!")
            return
            
        # 3. Tính toán các giá trị
        values = [r.value for r in readings]
        
        # a. Tính average (trung bình)
        average = sum(values) / n
        average = round(average, 2)
        
        # b. Tính anomaly_count (số reading có value > threshold)
        anomaly_count = sum(1 for v in values if v > threshold)
        
        # c. Tính p95
        # "p95 là phần tử tại vị trí ceil(n * 0.95) - 1 sau khi sắp xếp tăng dần"
        sorted_values = sorted(values)
        idx = math.ceil(n * 0.95) - 1
        p95 = sorted_values[idx]
        p95 = round(p95, 2)
        
        print(f"Average: {average}")
        print(f"p95: {p95} (tại vị trí index {idx})")
        print(f"Anomaly Count: {anomaly_count}")
        
        # 4. Đóng gói vào đối tượng SensorTelemetryAnswer
        answer = judge_pb2.SensorTelemetryAnswer(
            average=average,
            p95=p95,
            anomaly_count=anomaly_count
        )
        
        # Gửi SubmitTyped
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            sensor_telemetry_answer=answer
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
