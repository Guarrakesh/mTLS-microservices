## Setup

1) Copy spring.env.example and db.env.example in each microservice
folder. Rename them respectively "spring.env" and "db.env" 
and edit them according to your preferences"

2) For each microservice do:
* ```cd xxx_microservice```
* ```If use WSL (Ubuntu) with Windows 10 run 'dos2unix mvnw' otherwise skip this step```
  
* ```make spring-build``` 

3) Return to root dir and run
```make up```