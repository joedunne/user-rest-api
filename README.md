## User Rest API <a name="userRestAPI"></a>

**Required components for running the application in development mode**
```
OpenJDK v8 or higher
Docker (optional)
```

**Install**
```
Clone the repo

Then build with the following command
./gradlew build bootJar
```

**Start the internal application in development mode**
```
Commands to run locally
java -jar ./build/libs/user-rest-api-1.0.0.jar

URL to to execute the endpoint locally (In a browser, or request from a tool like curl)
http://localhost:8080/api/allusers
```

**Optional but would really impress us**
```
Docker image can be build with the following gradle command
./gradelw build docker

start the container 
docker run -p 8080:8080 -t com.merrill/user-rest-api

Then execute the endpoint locally (In a browser, or request from a tool like curl)
http://localhost:8080/api/allusers
```
**Informational**
```
Developer setup for IntelliJ or Eclipse requires the Lombok plugin. IntelliJ also requires turning on Annonation Processing.
https://projectlombok.org/setup/intellij
```
