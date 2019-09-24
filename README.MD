
# GlobalGym API
A rewrite of [GlobalGym](https://github.com/MarkNjunge/GlobalGym-Android)'s api using [Ktor](https://ktor.io).

## Built with

[Ktor](https://ktor.io/) - Web framework

[Jdbi](https://github.com/jdbi/jdbi) - Database access

[VuePress](https://vuepress.vuejs.org/) - Routes documentation


## Installation
Create a [PostgreSQL](https://www.postgresql.org/) database.

Clone the repository
```
git clone git@github.com:MarkNjunge/GlobalGym-api-ktor.git
```

Create an `application.conf` in resources based on `resources/application.conf.sample`.
```
cp resources/application.conf.sample resources/application.conf
```
[Optional] Build the routes documentation.  Requires [VuePress](https://vuepress.vuejs.org/) to be installed.
```
vuepress build resources\docs
```
Run the main function in `Application.kt` in an IDE, or run the command below in a cli
```
gradlew run
```

## Configuration

The _application.conf_ file is the main way of setting configs. Environment variables can also be used and will take
priority over the config file.

| ENV variable      | .conf mapping               |
| ----------------- | --------------------------- |
| PORT              | ktor.deployment.port        |
| DATABASE_URL      | database.url                |
| DATABASE_USERNAME | database.username           |
| DATABASE_PASSWORD | database.password           |
| AUTH_KEY          | other.authKey               |

## Building
### Jar
Build the routes documentation.  Requires [VuePress](https://vuepress.vuejs.org/) to be installed.
```
vuepress build resources\docs
```
Build the jar
```Bash
gradlew shadowJar
```
You can then run the jar using
```Bash
java -jar build\libs\globalgym-api.jar
```
### Docker

Use the instructions above to first build the jar

Build a docker image
```Bash
docker build -t globalgym-api .
```
Run the image
```Bash
docker run -it -p 3000:3000 --rm globalgym-api
```
