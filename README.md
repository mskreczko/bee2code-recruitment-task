# bee2code recruitment task

## Running
```
https://github.com/mskreczko/bee2code-recruitment-task.git
cd bee2code-recruitment-task
echo "POSTGRES_PASSWORD=<db_password>" > .env
docker-compose up
```

## API Endpoints

========= PACJENCI =========

Lista pacjentów: 
```curl -X GET http://localhost:8080/api/v1/patients```

Jeden pacjent: 
```curl -X GET http://localhost:8080/api/v1/patients/:id```

Dodanie nowego pacjenta:
```curl -X POST -H "Content-Type: application/json" -d '{"email": "test@test.com", "name": "John Doe", "phoneNumber": "123456789"}' http://localhost:8080/api/v1/patients```

Modyfikacja danych kontaktowych: 
```curl -X PATCH -H "Content-Type: application/json" -d '{"name": "", "email": "test@gmail.com", "phoneNumber": ""}' http://localhost:8080/api/v1/patients/:id```

Usunięcie pacjenta: 
```curl -X DELETE http://localhost:8080/api/v1/patients/:id```

========= PROJEKTY BADAWCZE =========

Lista projektów: 
```curl -X GET http://localhost:8080/api/v1/projects```

Jeden projekt: 
```curl -X GET http://localhost:8080/api/v1/projects/:id```

Dodanie nowego projektu: 
```curl -X POST -H "Content-Type: application/json" -d '{"name": "test project", "description": "lorem ipsum"}' http://localhost:8080/api/v1/projects```

Modyfikacja informacji o projekcie: 
```curl -X PATCH -H "Content-Type: application/json" -d '{"name": "", "description": "lorem ipsum dolor sit amet"}' http://localhost:8080/api/v1/projects/:id```

Usunięcie projektu: 
```curl -X DELETE http://localhost:8080/api/v1/projects/:id```

========= ZGODY NA UDZIAŁ W PROJEKCIE =========

Dodanie zgody: 
```curl -X POST http://localhost:8080/api/v1/consents?patient_id=:patient&project_id=:project```

Usunięcie zgody: 
```curl -X DELETE http://localhost:8080/api/v1/consents/:id```

========= ZLECENIA NA BADANIA LABORATORYJNE =========

Dodanie zlecenia: 
```curl -X POST -H "Content-Type: application/json" -d '{"patientId": ":patient", "researchProjectId": ":project", "deadline": "2023-04-15"'} http://localhost:8080/api/v1/lab-orders```
