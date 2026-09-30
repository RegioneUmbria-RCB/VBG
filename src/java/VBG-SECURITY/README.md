# Introduction 

Progetto Security in Spring Boot


# Getting Started

Scaricarlo come project e poi configurarlo come maven project

# Build and Test

Per lanciarlo vanno specificate le variabili di ambiente:

```properties

SECURITY_SERVER_PORT=8080

SECURITY_JDBC_DRIVER=com.mysql.cj.jdbc.Driver
SECURITY_JDBC_PWD=REDACTED
SECURITY_JDBC_URL=REDACTED
SECURITY_JDBC_USERNAME=REDACTED
SECURITY_HIBERNATE_DIALECT=<lasciare vuoto o in caso di maria db org.hibernate.dialect.MariaDBDialect>


SECURITY_DELETE_TOKEN_ENABLED=true
SECURITY_DELETE_TOKEN_CRON=0 30 0 * * ?
SECURITY_DELETE_TOKEN_SAVE_FILE=true
SECURITY_DELETE_TOKEN_LOGS_DIR=/logs
```


## Docker

Per creare l'immagine è necessario che sia:
- installato ed acceso Docker Desktop
- che sia attivato nelle configurazioni di docker desktop il flag "Expose daemon on tcp://localhost:2375 without TLS"
- nella view di Eclipse o STS sia configurata una connessione verso tcp://localhost:2375
- Tasto destro su Dockerfile ==> Run ==> Docker image build
  - se richiesto il nome dell'immagine deve essere **vbg.security:latest**
- da cmd o wsl lanciare 

```shell

docker image tag vbg.security:latest registry/vbg.security:latest
 
docker image push registry/vbg.security:latest
 
```

Nel caso di versioni es: 2.118

```shell

docker image tag vbg.security:latest registry/vbg.security:2.118
 
docker image push registry/vbg.security:2.118
 
```


## Test di carico con Jmeter

E' presente nella cartella src/test/resources il file **jmeter-ibcsecurity.jmx** che serve per lanciare i test di carico per l'applicativo.

Si usa [Apache JMeter™](https://jmeter.apache.org/) e vanno installati:

- il [Plugin Manager](https://jmeter-plugins.org/wiki/PluginsManager/) che serve ad installare le varie plugin.
- le seguenti plugin:
    - Custom SOAP Sampler
    - WS Security for SOAP
