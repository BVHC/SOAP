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
    question_alias = "hGaKLLBc" # Mã câu hỏi bài xét điều kiện học vụ

    print("Đang gửi yêu cầu RequestTyped...")
    request = judge_pb2.TypedJudgeRequest(
        student_code=student_code,
        question_alias=question_alias
    )
    
    try:
        response = stub.RequestTyped(request)
        print(f"Đã nhận phản hồi từ Server, Request ID: {response.request_id}")
        
        # 2. Lấy dữ liệu Enrollment
        if not response.HasField("enrollment"):
            print("Lỗi: Server không trả về dữ liệu enrollment!")
            return
            
        enrollment_data = response.enrollment
        completed_courses = set(enrollment_data.completed_courses)
        required_courses = set(enrollment_data.required_courses)
        gpa = enrollment_data.gpa
        min_gpa = enrollment_data.min_gpa
        
        print(f"GPA hiện tại: {gpa}, GPA yêu cầu (min_gpa): {min_gpa}")
        print(f"Số môn đã hoàn thành: {len(completed_courses)}")
        print(f"Số môn bắt buộc: {len(required_courses)}")
        
        # 3. Tính toán missing_courses
        # missing_courses = required_courses trừ đi completed_courses
        missing_courses = list(required_courses - completed_courses)
        
        # BẮT BUỘC: Sắp xếp tăng dần theo mã môn
        missing_courses.sort()
        
        # 4. Tính gpa_gap (làm tròn 2 chữ số)
        gpa_gap = max(0.0, min_gpa - gpa)
        gpa_gap = round(gpa_gap, 2)
        
        # 5. Xác định eligible
        # eligible đúng khi KHÔNG thiếu môn VÀ gpa_gap bằng 0
        eligible = (len(missing_courses) == 0) and (gpa_gap == 0.0)
        
        print(f"👉 Danh sách môn còn thiếu (missing_courses): {missing_courses}")
        print(f"👉 Khoảng cách GPA (gpa_gap): {gpa_gap}")
        print(f"👉 Đủ điều kiện ra trường? (eligible): {eligible}")
        
        # 6. Đóng gói vào đối tượng EnrollmentAnswer
        answer = judge_pb2.EnrollmentAnswer(
            eligible=eligible,
            missing_courses=missing_courses,
            gpa_gap=gpa_gap
        )
        
        # Gửi SubmitTyped
        submit_req = judge_pb2.TypedSubmitRequest(
            student_code=student_code,
            question_alias=question_alias,
            request_id=response.request_id,
            enrollment_answer=answer
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
