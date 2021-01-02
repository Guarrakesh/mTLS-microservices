## Setup

* Copy spring.env.example and db.env.example in each microservice
folder. Rename them respectively "spring.env" and "db.env" 
and edit them according to your preferences"

* For each microservice do:
    * ```cd xxx_microservice```
    
        (WSL ONLY) If use WSL (Ubuntu) with Windows 10, run the following to convert mvnw to compatible executable:
        * ```cd spring```
        * ```'dos2unix mvnw'```
  
* ```make spring-build``` 

* Return to root dir and run
```make up```