# Python Cheat Sheet cho các bài toán SOAP (PTIT / Exam Server)

Tài liệu này tổng hợp các cú pháp và hàm Python cơ bản, cực kỳ hữu ích để giải quyết 3 dạng bài tập SOAP phổ biến: **Chuỗi (String)**, **Dữ liệu số/mảng (Data)**, và **Đối tượng cấu trúc (Object)**.

---

## 1. Xử lý Chuỗi (String Manipulation)
Các bài toán về Chuỗi thường yêu cầu đảo ngược, mã hóa, đếm ký tự, tách ghép hoặc định dạng chuỗi.

### Các hàm cơ bản & Ví dụ:
```python
s = "  Hello PTIT Student 123  "

# 1. Loại bỏ khoảng trắng thừa ở 2 đầu
s_trimmed = s.strip()  # "Hello PTIT Student 123"

# 2. Đảo ngược chuỗi (Cú pháp cắt lát - Slicing siêu nhanh)
reversed_s = s_trimmed[::-1]  # "321 tnedutS TlTP olleH"

# 2.b. Cắt chuỗi (Substring / Slicing) [start:end]
# (Lưu ý: Chỉ số start được lấy, chỉ số end KHÔNG được lấy)
sub_s1 = s_trimmed[0:5]    # Lấy từ ký tự index 0 đến 4 -> "Hello"
sub_s2 = s_trimmed[6:]     # Lấy từ index 6 đến hết -> "PTIT Student 123"
sub_s3 = s_trimmed[-3:]    # Lấy 3 ký tự cuối cùng -> "123"
sub_s4 = s_trimmed[6:10]   # Lấy từ index 6 đến 9 -> "PTIT"

# 3. Viết hoa / Viết thường / Viết hoa chữ cái đầu
upper_s = s_trimmed.upper()  # "HELLO PTIT STUDENT 123"
lower_s = s_trimmed.lower()  # "hello ptit student 123"
title_s = s_trimmed.title()  # "Hello Ptit Student 123"

# 4. Tìm kiếm chuỗi con (Trả về chỉ số đầu tiên tìm thấy, hoặc -1 nếu không thấy)
pos = s_trimmed.find("PTIT")  # 6
is_in = "PTIT" in s_trimmed  # True (Cách kiểm tra nhanh)

# 5. Đếm số lần xuất hiện của ký tự/chuỗi con
count_t = s_trimmed.lower().count('t')  # Đếm số chữ 't' không phân biệt hoa thường -> 4

# 6. Thay thế ký tự / chuỗi con
new_s = s_trimmed.replace("123", "2026")  # "Hello PTIT Student 2026"

# 7. Tách chuỗi thành mảng (Mặc định tách theo khoảng trắng)
words = s_trimmed.split()  # ['Hello', 'PTIT', 'Student', '123']

# 8. Ghép mảng thành chuỗi
joined_s = "-".join(words)  # "Hello-PTIT-Student-123"

# 9. Lấy ký tự chỉ chứa số hoặc chữ
only_letters = "".join([c for c in s_trimmed if c.isalpha()])  # "HelloPTITStudent"
only_digits = "".join([c for c in s_trimmed if c.isdigit()])   # "123"
```

---

## 2. Xử lý Mảng và Số (Data / List / Array)
Các bài toán dạng này thường yêu cầu tính tổng, tìm max/min, sắp xếp, lọc số nguyên tố, chẵn lẻ, hoặc loại bỏ trùng lặp.

### Các hàm cơ bản & Ví dụ:
```python
numbers = [7328, 4569, 5695, 8706, 3142, 202, 665, 9191, 1048, 5919, 2987, 2073, 18, 7058]

# 1. Độ dài mảng (Số lượng phần tử)
length = len(numbers)  # 14

# 2. Các hàm toán học cơ bản
total = sum(numbers)  # Tính tổng toàn bộ mảng -> 59902
maximum = max(numbers)  # Số lớn nhất -> 9191
minimum = min(numbers)  # Số nhỏ nhất -> 18

# 3. Sắp xếp mảng (Không thay đổi mảng gốc)
sorted_asc = sorted(numbers)  # Sắp xếp tăng dần
sorted_desc = sorted(numbers, reverse=True)  # Sắp xếp giảm dần

# 4. Sắp xếp trực tiếp trên mảng gốc
numbers.sort() 

# 5. Lọc mảng (Sử dụng List Comprehension cực nhanh trong Python)
evens = [x for x in numbers if x % 2 == 0]    # Lọc các số chẵn
odds = [x for x in numbers if x % 2 != 0]     # Lọc các số lẻ
greater_than_1000 = [x for x in numbers if x > 1000] # Lọc số > 1000

# 6. Loại bỏ các phần tử trùng lặp (Giữ lại các phần tử duy nhất)
duplicate_list = [1, 2, 2, 3, 4, 4, 5]
unique_list = list(set(duplicate_list))  # [1, 2, 3, 4, 5]

# 7. Đảo ngược vị trí các phần tử trong mảng
reversed_list = numbers[::-1]
```

---

## 3. Xử lý Đối tượng (Object / Dictionary)
Trong SOAP Python (dùng thư viện `zeep`), các đối tượng (Object) trả về từ Server thường có dạng phức tạp (như class tự tạo của Java). `zeep` sẽ tự động chuyển chúng thành các đối tượng có thuộc tính giống như đối tượng trong Python.

### Cách làm việc với Object trả về từ SOAP:
```python
# Giả sử Server trả về một Object sinh viên gồm: studentCode, name, gpa
# Trong Python, bạn truy cập trực tiếp bằng dấu chấm (.) giống như Java:

print(student_obj.studentCode)  # In ra mã SV
print(student_obj.name)         # In ra tên SV

# ⚠️ BÍ QUYẾT ĐẶC BIỆT:
# Đôi khi Object của SOAP rất phức tạp và khó nhìn cấu trúc. 
# Bạn có thể chuyển nó thành Dictionary (bản đồ key-value) của Python để dễ xử lý:
from zeep.helpers import serialize_object

student_dict = serialize_object(student_obj)
print(student_dict)  # {'studentCode': 'B22DCCN292', 'name': 'Nguyen Van A', 'gpa': 3.2}

# Bây giờ bạn có thể truy cập như Dictionary:
print(student_dict["name"])
```

### Các thao tác Dictionary cơ bản (Dành cho bài toán Object):
```python
# 1. Tạo mới một Object dưới dạng Dictionary
student = {
    "studentCode": "B22DCCN292",
    "name": "Nguyen Van A",
    "scores": [8, 9, 7]
}

# 2. Thêm hoặc cập nhật thuộc tính
student["gpa"] = 3.2          # Thêm thuộc tính gpa
student["name"] = "Nguyen Van B"  # Cập nhật tên

# 3. Lấy giá trị an toàn (Không bị crash nếu key không tồn tại)
age = student.get("age", 20)  # Lấy "age", nếu không có thì mặc định là 20

# 4. Duyệt qua tất cả các thuộc tính của đối tượng
for key, value in student.items():
    print(f"{key}: {value}")
```

---

## 4. So sánh cú pháp Java vs Python (Cho bạn dễ chuyển đổi)

| Thao tác | Cú pháp Java | Cú pháp Python |
| :--- | :--- | :--- |
| **In ra màn hình** | `System.out.println("Hello");` | `print("Hello")` |
| **Đảo ngược chuỗi** | `new StringBuilder(s).reverse().toString();` | `s[::-1]` |
| **Tính tổng mảng** | Dùng vòng lặp `for` cộng dồn | `sum(arr)` |
| **Lọc số chẵn** | Dùng `for` và `if` rồi `add` vào List mới | `[x for x in arr if x % 2 == 0]` |
| **Gộp mảng chuỗi** | `String.join("-", list);` | `"-".join(list)` |
| **Kiểm tra con** | `s.contains("abc");` | `"abc" in s` |
