# StayTuneBack
Для запуска необходима IDEA, поменять username и password на ваши от PostgreSQL в файле application.properties в папке /src/main/resources. Там же, в строчке spring.datasource.url=jdbc:postgresql://localhost:5432/staytune нужно указать существующую на вашем устройстве БД вместо staytune, либо создать у себя БД с таким названием. Также  в строчке pring.jpa.hibernate.ddl-auto=update изменить на create для автоматического создания всех таблиц. После первого запуска изменить на обратно на update.

Гайд по созданию postgresql:
https://www.youtube.com/watch?v=IbVPbF7HTL4

Также нужно выбрать в правых вкладках Database и создать БД. Там указать свой логин и пароль от PostgreSQL.

Чтобы сгенерировать список концертов нужно выполнить GET запрос в Postman:
http://localhost:8080/create

