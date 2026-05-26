import requests

def run():
    student_code = "B22DCCN292"
    question_alias = "XqxzRcRW"
    base_url = "http://36.50.135.242:2230/api/rest/object"
    
    # 1. Gửi GET request để lấy đề bài
    print("Đang gửi yêu cầu GET...")
    response = requests.get(f"{base_url}?studentCode={student_code}&qCode={question_alias}")
    
    if response.status_code != 200:
        print(f"Lỗi HTTP GET: {response.status_code} - {response.text}")
        return
        
    data_json = response.json()
    request_id = data_json.get("requestId")
    product = data_json.get("data", {})
    
    print(f"Đã nhận phản hồi từ Server!")
    print(f"Request ID: {request_id}")
    print(f"Dữ liệu sản phẩm nhận được: {product}")
    
    # 2. Xử lý tính toán giá cuối cùng
    price = product.get("price", 0.0)
    tax_rate = product.get("taxRate", 0.0)
    discount = product.get("discount", 0.0)
    
    # Công thức: finalPrice = price * (1 + taxRate / 100) * (1 - discount / 100)
    final_price = price * (1 + tax_rate / 100) * (1 - discount / 100)
    final_price = round(final_price, 2)  # Làm tròn 2 chữ số thập phân
    
    print(f"Giá cuối cùng tính được (finalPrice): {final_price}")
    
    # 3. Gửi POST request nộp bài
    submit_url = f"{base_url}/submit"
    payload = {
        "studentCode": student_code,
        "qCode": question_alias,
        "requestId": request_id,
        "answer": {
            "finalPrice": final_price
        }
    }
    
    print("\nĐang gửi kết quả POST...")
    submit_response = requests.post(submit_url, json=payload)
    
    print(f"Mã trạng thái phản hồi: {submit_response.status_code}")
    # Đọc kết quả dạng JSON nếu có
    try:
        print(f"Kết quả từ Server: {submit_response.json()}")
    except:
        print(f"Kết quả từ Server: {submit_response.text}")

if __name__ == "__main__":
    run()
