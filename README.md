# Order Service
Service ini bertujuan untuk mengelola order paket perjalanan user. Service ini memiliki fitur sebagai berikut:
- Membuat order dan mengirimkan data tersebut ke payment service melalui kafka
- Update status order berdasarkan message yang dikirim oleh payment service melalui kafka
- Get order by user id
- Get order by order id
