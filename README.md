# Landing Save Form

Microservice to save a form called landing.

### Info about application:
- **Contextpath:** /mkt/form
- **Port:** 9000
- **BD:** Postgresql
- **Maven:** 3.5.4
- **Spring Boot:** 2.1.12
- **Coverage UT:** Jacoco 0.8.5
- **Docs OpenApi**: 1.2.30
- **Code Quality:** Sonarqube 7.8
- **Profiles:** dev and prod

### Endpoints
| Endpoint | Method | Description |
| ------ | ------ | ------ |
| **/landing** | GET | Retrieves all the landings in the BD |
| **/landing** | POST | Save a landings in the BD |
| **/landing/{folio}** | GET | Retrieve a specific landing from the BD |
| **/landing/{folio}** | PUT | Update a specific landing in BD or creates a one new |
| **/landing/{folio}** | DELETE | Delete a specific landing in BD |

### Other endpoints
| Endpoint | Method | Description |
| ------ | ------ | ------ |
| **/app/build-info** | GET | Retrieves info about version of the application |
| **/docs/swagger-ui.html** | GET | Show the docs |
| **/actuator/health** | GET | Show status application |

### Request example (Create a new landing)
```json
{
    "personalData": {
        "firstName": "Name",
        "secondName": "Name",
        "lastName": "LastName",
        "secondLastName": "LastName",
        "email": "dev.test@test.com",
        "homePhone": "5555555555",
        "cellPhone": "6666666666"
    }
}
```

### Response example (Retrieve the landing)
```json
{
    "folio": 1,
    "personalData": {
        "firstName": "Name",
        "secondName": "Name",
        "lastName": "LastName",
        "secondLastName": "LastName",
        "email": "dev.test@test.com",
        "homePhone": "5555555555",
        "cellPhone": "6666666666"
    },
    "dateCreated": "01/02/2020",
    "dateModified": "01/02/2020"
}
```
### Error Response (if something bad happens)
```json
{
    "code":"CODE",
    "type": "TYPE",
    "description": "DESC"
}
```

### Build
```sh
mvn clean package
```
### Only Execute UT and Verify Coverage
```sh
mvn clean test
```





