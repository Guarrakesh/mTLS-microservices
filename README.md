#mTLS Microservice and Short Lived Certificates
This project is a sandbox university project that aims to implement a secure
microservice-like environment using **Mutual Authentication (mTLS)** between microservices.
This is split in 2 branches: 
* **main-scenario-1**: is the scenario in which the certificates for 
  each microservice are generated externally and copy-pasted inside the microservice directory.
  Certificates should be *long-lived* and its expiration-revokation must be handled manually 
* **main-scenario-2**: is like the scenario-1 with the addition of the ***short-lived* certificates**. Certification management and privisioning is made with **Lemur**, that handles 
the expirations of short-lived certificates and distributes the new certificate to the microservices.
  
This project is a two-people university project, made for the ***Secure System Design*** course of *Valentina Casola, Università Federico II di Napoli* and with the support of 
*Ermanno Battista*, from Fervento.
It strongly refers to the **Part 3: Service-to-Service Communication** of the *Microservice Security in Action, Prabath Siriwardena and Nuwan Dias - Manning 2020*

The project is **Dockerized** and it is recommended to run it on Unix-like systems with Docker installed (Linux, MacOS) or in Windows with the WSL.
At the moment Docker images used are *maven:3.6.3-jdk-8* and *openjdk:8-jre-alpine* for the **Spring Boot** containers and *wodby/mariadb:10* for the *Databases* (for now disabled).
For Lemur we are using Nginx, PostegreSQL and [TODO]

## Setup
* Pull the project 
```console
git clone https://github.com/Guarrakesh/mTLS-microservices.git
```
* Copy spring.env.example and db.env.example in each microservice
folder. Rename them respectively "spring.env" and "db.env" 
and edit the *spring.env* according to your preferences:
  * In this sandbox environment, the keystores file (CA and Microservice) are already present, in a production environment you should create your own certificates, either with Lemur or with external tool.
  * edit **SSL_KEYSTORE_DIR**, **SSL_TRUSTSTORE_DIR** with the full path (container path) of the relative keystores.
    Note that **/app** is the CONTAINER_DIR and **data** is the DATA_DIR in external **.env**. spring/data is a **linked volume** to /app/data in docker-compose.yml
  * edit **SSL_KEYSTORE_PASSWORD**, **SSL_TRUSTSTORE_PASSWORD**
  * In the *spring.env* you can Enable/Disable TLS and Client Authentication for each microservice.
* At this moment, databases are disabled, we are using in-memory data. So you can ignore the *db.env* file.
  
* Make sure you don't have other Docker container that may have conflicting ports with the Spring Boot ones.
* Pull Up the Docker compose and wait for completion:
```console 
make up
```



## Utilities

### Dump LEMUR Database
* Install a PostegreSQL client to have pg_dump and psql binaries
* docker exec -ti mTLS_lemur_postgres psql -U lemur -d postgres -c "drop database lemur with (force)"
* docker exec -ti mTLS_lemur_postgres psql -U lemur -d postgres -c "create database lemur"

* ./pg_dump -h localhost -p 5432 -U lemur > {DUMP_FILE}
* ./psql -h localhost -p 5432 -U lemur lemur < ${DUMP_FILE}


### Dependency Check

* Copy spring.env to spring.local.env and remove absolute paths of the container and leave relative paths for "spring" folder
* ``` cd {microservice_folder}```
* ``` source spring.local.env ```
* ``` cd spring ```
* ``` ./mvnw dependecy-check:check```