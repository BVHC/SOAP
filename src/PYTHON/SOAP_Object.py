from zeep import Client

# 1. Khởi tạo Client cho ObjectService
url = 'http://36.50.135.242:2221/ObjectService?wsdl'
client = Client(wsdl=url)

studentCode = "B22DCCN292"
qCode = "pLjSbxHU"

# 2. Gọi hàm requestProductY để lấy đối tượng ProductY từ server
product = client.service.requestProductY(studentCode=studentCode, qCode=qCode)
print("Đối tượng ban đầu nhận từ server:")
print(product)

# 3. Tính toán finalPrice
# Công thức: finalPrice = price * (1 + taxRate / 100) * (1 - discount / 100)
price = product.price
taxRate = product.taxRate
discount = product.discount

finalPrice = price * (1 + taxRate / 100) * (1 - discount / 100)
print(f"Tính toán: price={price}, taxRate={taxRate}, discount={discount} -> finalPrice={finalPrice}")

# 4. Gán finalPrice ngược lại vào đối tượng
product.finalPrice = finalPrice

# 5. Gửi đối tượng đã cập nhật trở lại server qua hàm submitProductY
# Lưu ý: tham số thứ 3 của hàm submitProductY trong WSDL tên là 'data'
response = client.service.submitProductY(studentCode=studentCode, qCode=qCode, data=product)
print("Kết quả server trả về:", response)
