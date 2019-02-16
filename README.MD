# GlobalGym API
A rewrite of [GlobalGym](https://github.com/MarkNjunge/GlobalGym-Android)'s api using [Ktor](https://ktor.io).

## Cloning
Create an `application.conf` in resources based on `resources/application.conf`.
```Bash
cp resources/application.conf.sample resources/application.conf
```

## Configuration

The _application.conf_ file is the main way of setting configs. Environment variables can also be used and will take 
priority over the config file.

| ENV variable      | .conf mapping               |
| ----------------- | --------------------------- |
| PORT              | ktor.deployment.port        |
| DATABASE_URL      | database.url        |
| DATABASE_USERNAME | database.username           |
| DATABASE_PASSWORD | database.password           |

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
