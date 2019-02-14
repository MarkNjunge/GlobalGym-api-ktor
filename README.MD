# GlobalGym API
A rewrite of [GlobalGym](https://github.com/MarkNjunge/GlobalGym-Android)'s api using [Ktor](https://ktor.io).

## Cloning
Create an `application.conf` in resources based on `resources/application.conf`.
```Bash
cp resources/application.conf.sample resources/application.conf
```

## Running
### Via an IDE
Run the main function in `Application.kt`

### Via the command line
```Bash
gradlew run
```

## Building
### Jar
```Bash
gradlew shadowJar
```
You can then run the jar using
```Bash
java -jar build\libs\globalgym-api.jar
```
### Docker
Build the jar
```Bash
gradlew shadowJar
```
Build a docker image
```Bash
docker build -t globalgym-api .
```
Run the image
```Bash
docker run -it -p 3000:3000 --rm globalgym-api
```