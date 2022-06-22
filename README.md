利用區分GET、POST、PUT、DELETE
來達成各腳色的權限控管
|Role        |permissions             |
|------------|------------------------|
|super user  |`GET、POST、PUT、DELETE` |
|manager     |`GET、PUT、DELETE`      |
|operator    |`GET、POST`             |

---
Authentication/Authorization
使用JWT實作

---
swagger
http://{localhost}:{port}/swagger-ui.html