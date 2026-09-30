# ISTRUZIONI DI COMPILAZIONE PROGETTI

Vengono riportate le istruzioni di compilazione dei vari progetti

## Progetti Java

### Maven

All'interno della cartella è presente il file **pom.xml** con il quale all'interno del proprio IDE eseguire la compilazione con il classico comando

```shell

mvn clean compile

```

### Ant

All'interno del progetto è presente il file build.xml con il quale eseguire il goal di compilazione/creazione war

### Progetti backend/areariservata2/api-backend

Nel progetto **VBG-BUILD-PROGETTI-JAVA** è presente il file

> build-vbg-java-apps.xml

che serve per compilare i progetti mediante ant

### Docker

Le applicazioni potrebbero essere deployate in immagini Docker secondo questi template.

Ad esempio **api-backend** (progetto ANT):

```docker

FROM tomcat:9.0-jdk8-temurin-jammy

ADD ./ext/docker/*.jar $CATALINA_HOME/lib
ADD ./gp-api-backend/target/*.war $CATALINA_HOME/webapps

RUN apt-get -y update && \
    apt-get install unzip

WORKDIR $CATALINA_HOME/webapps

RUN for FILE in *.war; do unzip $FILE -d ./${FILE%%.*}/; done && \
    rm -rf *.war && \
    apt-get -y remove unzip

RUN addgroup tomcat
RUN useradd -g tomcat tomcat
RUN chown -R tomcat.tomcat $CATALINA_HOME
USER tomcat
WORKDIR $CATALINA_HOME

```

Ad esempio **ibcsecurity** (progetto maven):

```docker

FROM eclipse-temurin:25-jre-jammy

RUN apt-get update && \
    apt-get --yes install --no-install-recommends curl && \
    rm -rf /var/cache/apt/archives /var/lib/apt/lists/*

COPY target/security*.jar ibcsecurity.jar


ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /ibcsecurity.jar ${0} ${@}"]

HEALTHCHECK CMD curl --fail http://localhost:8080/ibcsecurity/actuator/health || exit

```

## Progetti .NET

Per la compilazione dei progetti .NET usare il comando

```shell
msbuild <nome-soluzione>.slnx
```

sulle diverse soluzioni

## Portale

### Fontoffice

Progetto angular, nella cartella di progetto sono presenti i file:

- `README.md`
- `build-version.bat`

nella folder di progetto per la guida all'installazione

## Database

Importare i database su Motore MySQL 5.7+ o MySQL8/9+
