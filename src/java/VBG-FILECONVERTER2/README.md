# Introduction 
Il progetto è il porting in spring boot del progetto per convertire i file nei vari formati usando le librerie di Libreoffice.

# Getting Started

Il progetto è una spring boot application che contiene le interfacce per convertire i file nei vari formati.

Installazione: è necessario che sia installata la suite libreoffice.

Il file docker contiene quanto necessario per l'installazione in container.

Nel file compose.yml è necessario specificare la variabile di ambiente office.home

Esempio file compose.yml

```YML
  fileconverter:
    image: "localhost:8300/vbg.fileconverter2:latest"
    container_name: "fileconverter"
    environment:
      JAVA_OPTS: "-Doffice.home=/usr/lib/libreoffice"
    restart: "always"
    volumes:
      - "/opt/docker/hosts:/etc/hosts:ro"
    ports:
      - "9080:9080"

```

Per creare l'immagine è necessario che sia:
- installato ed acceso Docker Desktop
- che sia attivato nelle configurazioni di docker desktop il flag "Expose daemon on tcp://localhost:2375 without TLS"
- nella view di Eclipse o STS sia configurata una connessione verso tcp://localhost:2375
- l'immagine si basa su una immagine di partenza denominata **registry/vbg.fileconverter2-base:1.0.0** e creata con il file **DockerfileImmagineBase** 
- il progetto va compilato con i comandi maven: "clean compile package"
- Tasto destro su Dockerfile ==> Run ==> Docker image build
- da cmd o wsl lanciare 

```shell
 docker image tag vbg.fileconverter2:latest registry/vbg.fileconverter2:latest
 docker image push registry/vbg.fileconverter2:latest
``` 


Nel caso di versioni es: 2.118

```shell

 docker image tag vbg.fileconverter2:latest registry/vbg.fileconverter2:2.118
 docker image push registry/vbg.fileconverter2:2.118


```

# Personalizzare i font

Per personalizzare i font è necessario recuperare i fonts e installarli nelle directory del container

```shell

    volumes:
      - "/opt/docker/hosts:/etc/hosts:ro"
      - "/opt/docker/fileconverter/garabd.ttf:/usr/share/fonts/garamond/garabd.ttf"
      - "/opt/docker/fileconverter/garait.ttf:/usr/share/fonts/garamond/garait.ttf"
      - "/opt/docker/fileconverter/garamond-bold.xml:/usr/share/fonts/garamond/garamond-bold.xml"
      - "/opt/docker/fileconverter/garamond-italic.xml:/usr/share/fonts/garamond/garamond-italic.xml"
      - "/opt/docker/fileconverter/garamond.xml:/usr/share/fonts/garamond/garamond.xml"
      - "/opt/docker/fileconverter/gara.ttf:/usr/share/fonts/garamond/gara.ttf"

```
