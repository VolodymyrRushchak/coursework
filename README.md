Для того, щоб запустити програму, потрібно у консолі перейти в кореневу директорію проекту та виконати команду "mvn org.springframework.boot:spring-boot-maven-plugin:run".

Щоб надсилати запити та користуватися CRUD операціями, найзручніше використовувати застосунок Postman. Всі запити потрібно надсилати на адресу http://localhost:8080


Доступні команди:

Отримати всі сутності заданого типу(GET):
/rivers/GET
/measurementStations/GET
/measurements/GET

Отримати сутність заданого типу за id(GET/{id}):
/rivers/GET/{id}
/measurementStations/GET/{id}
/measurements/GET/{id}

Створити сутність заданого типу(POST):
/rivers/POST
/measurementStations/POST
/measurements/POST

Обновити сутність заданого типу за id(PUT/{id}):
/rivers/PUT/{id}
/measurementStations/PUT/{id}
/measurements/PUT/{id}

Видалити сутність заданого типу за id(DELETE/{id}):
/rivers/DELETE/{id}
/measurementStations/DELETE/{id}
/measurements/DELETE/{id}
