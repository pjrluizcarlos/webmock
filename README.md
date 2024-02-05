# Webmock Service

Webmock Service is a configurable HTTP mock service built with Spring Boot and Spring Web.  
This application allows you to easily set up and customize mock responses for various HTTP requests.

## Installation

To get started with MockHTTP Server, follow these steps:

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/pjrluizcarlos/webmock-service.git
   cd webmock-service
   ```

2. **Build and Run the Application:**

   ```bash
   ./gradlew clean build bootRun
   ```

   The MockHTTP Server will be accessible at `http://localhost:8080`.

## Database Location

By default, the database is stored in an H2 file located at `./data/database`.
It can be changed at [application.yaml](src/main/resources/application.yaml)

## API Documentation (OpenAPI)

This project is using the dependency [springdoc-openapi-starter-webmvc-ui](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui).
After you run the application, it will be exposed http://localhost:8080/swagger-ui/index.html.

## API Endpoints

### 1. Get All Mock Configurations:

- **Endpoint:** `/mocks-configuration`
- **HTTP Method:** GET

#### cURL Example:

```bash
curl -X GET http://localhost:8080/mocks-configuration
```

**Example Response Body:**
```json
[
  {
    "id": 1,
    "uri": "/example",
    "method": "GET",
    "status": 200, 
    "response": "{\"test\":1}"
  },
  {
    "id": 2,
    "uri": "/another",
    "method": "POST",
    "status": 201, 
    "response": "{\"test\":2}"
  }
]
```

### 2. Save Mock Configuration:

- **Endpoint:** `/mocks-configuration`
- **HTTP Method:** POST

#### cURL Example:

```bash
curl -X POST -H "Content-Type: application/json" -d '{
  "uri": "/new-mock",
  "method": "GET",
  "status": 308,
  "response": "{\"test\":1}"
}' http://localhost:8080/mocks-configuration
```

**Example Response Body:**
```json
{
  "id": 3,
  "uri": "/new-mock",
  "method": "GET", 
  "status": 308,
  "response": "{\"test\":1}"
}
```

### 3. Delete Mock Configuration by ID:

- **Endpoint:** `/mocks-configuration/{id}`
- **HTTP Method:** DELETE

#### cURL Example:

```bash
curl -X DELETE http://localhost:8080/mocks-configuration/1
```

**Example Response Body:** (No response body for successful deletion)

### 4. Execute Mock:

- **Endpoint:** `/mocks/**`
- **HTTP Method:** Any

#### cURL Example:

```bash
curl -X GET http://localhost:8080/mocks/some-path
```

**Example Response Body (your configured response body):**
```json
{
  "test": 1
}
```

## Contributing

If you'd like to contribute to Webmock Service, please fork the repository and submit a pull request.   
We welcome contributions and feedback!

## License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.