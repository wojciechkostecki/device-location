# Device Location
> Application for tracking the geolocation of devices.
>>The task of the REST service is to record information about the geolocation of mobile devices.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Setup](#setup)
* [Project Status](#project-status)
* [Contact](#contact)


## General Information
- Project created in Java according to REST API.
- The Device model has been connected to the Geolocation model using the OneToMany relationship.
- Endpoints for Save, Read, Edit, and Delete (CRUD) have been created for the Device.
- An endpoint for saving has been created for Geolocation with the assignment to the given Device by Id.
- Authorization and authentication using JWT.
- After registration, the User is assigned the basic Role: ‘USER’.
- User roles are responsible for access to endpoints.
- After starting the application User roles are saved in the database and the administrator is added.
- Device and Geolocation endpoint tests created with MockMvc.


## Technologies Used
- Java
- Spring Boot
- PostgreSQL
- H2
- Junit 5
- MockMvc
- JSON Web Tokens
- Lombok
- Mapstruct
- Docker

## Setup
Use the commands:

```git clone https://github.com/wojciechkostecki/device-location.git```

```cd device-location```

```docker-compose --file src/main/docker/app-local-dev.yml up```

```mvn spring-boot:run```

## Project Status
Project is: _in progress_

## Contact
Created by [@wojciechkostecki](https://www.linkedin.com/in/wojciech-kostecki-7816411a4/)
