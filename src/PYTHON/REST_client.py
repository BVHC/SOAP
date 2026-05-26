import requests
import re

def run():
    student_code = "B22DCCN292"
    question_alias = "vII49QII"
    base_url = "http://36.50.135.242:2230/api/rest/character"
    
    # 1. Gửi GET request để lấy đề bài
    print("Đang gửi yêu cầu GET...")
    response = requests.get(f"{base_url}?studentCode={student_code}&qCode={question_alias}")
    
    if response.status_code != 200:
        print(f"Lỗi HTTP GET: {response.status_code} - {response.text}")
        return
        
    data_json = response.json()
    request_id = data_json.get("requestId")
    text_data = data_json.get("data", "")
    
    print(f"Đã nhận phản hồi từ Server!")
    print(f"Request ID: {request_id}")
    print(f"Dữ liệu gốc (trích đoạn): {text_data[:150]}...\n")
    
    # 2. Xử lý chuỗi (Che giấu dữ liệu bằng Regex)
    
    # a. Thay email bằng [EMAIL]
    email_regex = r'\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b'
    masked_data = re.sub(email_regex, '[EMAIL]', text_data)
    
    # b. Thay số điện thoại (10 chữ số bắt đầu bằng 0) bằng [PHONE]
    phone_regex = r'\b0\d{9}\b'
    masked_data = re.sub(phone_regex, '[PHONE]', masked_data)
    
    # c. Thay token (dạng token=<giá_trị>) bằng token=[TOKEN]
    # Ký tự token kéo dài đến khi gặp dấu cách hoặc dấu ngoặc/kí tự đặc biệt phân tách (dấu |)
    token_regex = r'token=[^\s\|]+'
    masked_data = re.sub(token_regex, 'token=[TOKEN]', masked_data)
    
    print(f"Dữ liệu sau khi che (trích đoạn): {masked_data[:150]}...\n")
    
    # 3. Gửi POST request nộp bài
    submit_url = f"{base_url}/submit"
    payload = {
        "studentCode": student_code,
        "qCode": question_alias,
        "requestId": request_id,
        "answer": masked_data
    }
    
    print("Đang gửi kết quả POST...")
    submit_response = requests.post(submit_url, json=payload)
    
    print(f"Mã trạng thái phản hồi: {submit_response.status_code}")
    # Đọc kết quả dạng JSON nếu có
    try:
        print(f"Kết quả từ Server: {submit_response.json()}")
    except:
        print(f"Kết quả từ Server: {submit_response.text}")

if __name__ == "__main__":
    run()
