import requests

def run():
    student_code = "B22DCCN292"
    question_alias = "rcsdXKeC"
    base_url = "http://36.50.135.242:2230/api/rest/path"
    
    # 1. Gửi GET request (Lấy danh sách Invoice)
    print("Đang gửi yêu cầu GET lần 1...")
    first_url = f"{base_url}?studentCode={student_code}&qCode={question_alias}"
    response = requests.get(first_url)
    
    if response.status_code != 200:
        print(f"Lỗi HTTP GET lần 1: {response.status_code} - {response.text}")
        return
        
    data_json = response.json()
    request_id = data_json.get("requestId")
    invoices = data_json.get("data", [])
    
    print(f"Đã nhận phản hồi từ Server!")
    print(f"Request ID: {request_id}")
    print(f"Danh sách Invoice: {invoices}")
    
    if len(invoices) == 0:
        print("Lỗi: Danh sách invoice trả về rỗng!")
        return
        
    # 2. Chọn một invoice ID bất kỳ từ danh sách
    # Ta đơn giản lấy ID của phần tử đầu tiên (index = 0)
    chosen_id = invoices[0].get("id")
    print(f"👉 Quyết định chọn Invoice ID: {chosen_id}")
    
    # 3. Gửi GET request lần 2 (có Path Parameter và Query Parameter)
    # Gắn {invoiceId} thẳng vào đường dẫn (Path Parameter)
    # Gắn các thông tin khác ở đằng sau dấu ? (Query Parameter)
    submit_url = f"{base_url}/{chosen_id}?studentCode={student_code}&qCode={question_alias}&requestId={request_id}&currency=USD"
    
    print("\nĐang gửi yêu cầu GET lần 2 (Nộp bài)...")
    submit_response = requests.get(submit_url)
    
    print(f"Mã trạng thái phản hồi: {submit_response.status_code}")
    try:
        print(f"Kết quả từ Server: {submit_response.json()}")
    except:
        print(f"Kết quả từ Server: {submit_response.text}")

if __name__ == "__main__":
    run()
