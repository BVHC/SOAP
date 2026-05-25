from zeep import Client
# https://www.python.org/downloads/
# python -m pip install zeep
# python -u "d:\SOAP\src\PYTHON\SOAP_data.py"
# 1. Khởi tạo client với đường dẫn WSDL
wsdl_url = 'http://36.50.135.242:2221/DataService?wsdl'
client = Client(wsdl=wsdl_url)

# 2. Khai báo mã sinh viên và mã câu hỏi (qCode)
studentCode = "B22DCCN292"
qCode = "MkEUWFPQ" # <-- CHÚ Ý: BẠN HÃY THAY MÃ CÂU HỎI VÀO ĐÂY!

# 3. Lấy danh sách số nguyên từ server
# Hàm thật trong WSDL là getData(studentCode, qCode)
data_list = client.service.getData(studentCode=studentCode, qCode=qCode)
print("Danh sách nhận được:", data_list)

# 4. Tính toán (ví dụ: tính tổng)
total_sum = sum(data_list)
print("Tổng tính được:", total_sum)

# 5. Gửi kết quả lên server
# Hàm thật là submitDataInt(studentCode, qCode, data)
response = client.service.submitDataInt(studentCode=studentCode, qCode=qCode, data=total_sum)
print("Kết quả server trả về:", response)
