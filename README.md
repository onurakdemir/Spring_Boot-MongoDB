# Message middleware with Spring-boot and Rest.

## Swagger-ui endpoints
```
http://localhost:8080/v2/api-docs
http://localhost:8080/swagger-ui.html
```

## Rest methods and mappings.
```
Send message        -> POST /users/{id}/messages
Incoming message    -> GET /users/{id}/messages?type=sent
Sent message        -> GET /users/{id}/messages?type=incoming
Read message_detail -> GET /messages/{id}
Estimate_day        -> GET /predict?type=day
Estimate_week       -> GET /predict?type=week
```

## Weekly/Daily Estimation
A fundamental prediction for the expected incoming message count for week/day.
#### Daily:
All the historical message data is search for the same day as the 
queried day (Sunday, Monday, etc.) with the assumption that
the same day will get the more or less the same amount of message.

#### Weekly:
All the historical message data is search for the same week as the 
queried day (52 weeks) with the assumption that
the same week will get the more or less the same amount of message.

## Developers
* This project uses Lombok, please install lombok plugin for your IDE and enable annotation processor.
* Postman scripts located at /postman directory.
* Run&Build
```
gradlew build
gradlew bootRun
```
