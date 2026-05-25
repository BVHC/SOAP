from zeep import Client

url = 'http://36.50.135.242:2221/CharacterService?wsdl'
client = Client(wsdl=url)

studentCode = "B22DCCN292"
qCode = "pCXTGqF4"

# 1. Nhận chuỗi từ server
s = client.service.requestString(studentCode=studentCode, qCode=qCode)
print("Chuỗi nhận được từ server:", s)

# 2. Đảo ngược chuỗi (Trong Python chỉ cần dùng cú pháp [::-1])
reversed_string = s[::-1]
print("Chuỗi sau khi đảo ngược:", reversed_string)

# 3. Gửi kết quả ngược lại lên server
response = client.service.submitString(studentCode=studentCode, qCode=qCode, data=reversed_string)
print("Kết quả server trả về:", response)