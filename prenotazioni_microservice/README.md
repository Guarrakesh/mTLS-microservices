Docker has a simple "Dockerfile" file format that it uses to specify the “layers” of an image. Create the following Dockerfile in your Spring Boot project:


## GRADLE
If you use Gradle, you can run it with the following command:

```./gradlew build && java -jar build/libs/pds-docker-0.1.0.jar```

After application is built, run this command

```mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)```
## MAVEN
```./mvnw package && java -jar target/pds-docker-0.1.0.jar```

After application is built, run this command

```mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)```


### RUN 
``` make up ```
