# Introduzione 

Componente per il ricalcolo aree delle istanze


## Configurazione del Servizio di Ricalcolo Aree

### Configurazione URL Backoffice

Per configurare l'URL che il backoffice deve chiamare, è necessario configurare il parametro **WSHOSTURL_RICALCOLOAREE** oppure accedere al database `ibcsecurity` e aggiornare la seguente tabella:

- **Tabella**: `comunisecurityparam`
- **Valori da inserire**:
  - `property`: `WSHOSTURL_RICALCOLOAREE`
  - `url`: `<url>` (sostituire con l'URL del servizio, tipo questo http://localhost:8082/rest-api)
  - `descrizione`: inserire una descrizione opportuna e significativa del servizio

Assicurarsi che l'URL indicato sia raggiungibile e corretto per l'invocazione da parte del backoffice.


### Requisiti Obbligatori

- Deve essere configurata una **URL valida** per il web service della **security**.
- **Assicurarsi che l'URL del servizio VBG sia valido**.
- **L'utenza fornita per SIGE deve essere valida e avere i permessi necessari per accedere al servizio**.


## Installazione su Docker

**TODO INSERIRE FILE COMPOSE**

Le seguenti variabili d'ambiente devono essere configurate per l'avvio corretto della componente **Spring Boot**:

```env
RICALCOLOAREE_MANAGEMENT_PORT=<mport>
RICALCOLOAREE_SERVER_PORT=<port>
RICALCOLOAREE_TOKEN_PWD=REDACTED
RICALCOLOAREE_TOKEN_URL=REDACTED
RICALCOLOAREE_TOKEN_USER=REDACTED
```

- `<mport>`: porta di management della componente
- `<port>`: porta principale di esposizione del servizio
- `<pwd>`: password dell'utenza SIGE
- `<urlsige>`: URL del web service SIGE
- `<user>`: utenza SIGE


- per la compilazione Maven usare i goal **checkstyle:check pmd:check clean compile package**
- Tasto destro su Dockerfile ==> Run ==> Docker image build
  - se richiesto il nome dell'immagine deve essere **vbg.ricalcolo-aree**
- da cmd o wsl lanciare 

```shell

# (questo sotto solo se non funziona la build da eclipse)
docker build -t vbg.ricalcolo-aree:latest . 

docker image tag vbg.ricalcolo-aree:latest registry/vbg.ricalcolo-aree:latest
docker image push registry/vbg.ricalcolo-aree:latest
 
```





## Installazione come servizio

Installazione della jdk-17.0.14+7-jre presa da https://adoptium.net/temurin/releases/?version=17.

Questa guida riporta l'installazione nella directory 

> /usr/java/jdk-17.0.16+8/

creazione della directory che ospiterà l'applicazione

```shell
sudo mkdir /opt/vbg/vbg-ricalcolo-aree/
sudo mkdir /opt/vbg/vbg-ricalcolo-aree/logs
sudo mv ricalcoloaree-0.0.1-SNAPSHOT.jar /opt/vbg/vbg-ricalcolo-aree/ricalcoloaree.jar
```

esternalizzazione del file logback-spring.xml (altrimenti non parte). scrivere nel file la configurazione appropriata

```shell
sudo vim /opt/vbg/vbg-ricalcolo-aree/logback-spring.xml
```

Aggiunta dell'utente che eseguirà l'applicazione

```shell
sudo useradd -m -U -d /home/vbgappusr -s /bin/false vbgappusr

```

Creazione dello UNIT FILE per il servizio

```shell
sudo vim /etc/systemd/system/vbg-ricalcolo-aree.service


 
```

Contenuto del file **vbg-ricalcolo-aree.service**

```shell
[Unit]
Description=VBG Ricalcolo Aree
After=syslog.target

[Service]
User=REDACTED
ExecStart=/usr/java/jdk-17.0.16+8/bin/java -Dlogging.config=/opt/vbg/vbg-ricalcolo-aree/logback-spring.xml -Xms256M -jar /opt/vbg/vbg-ricalcolo-aree/ricalcoloaree.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target


```

Impostazione dei permessi sulle cartelle

```shell
sudo chown -R vbgappusr:vbgappusr /opt/vbg/
```

Impostazione delle variabili di ambiente del servizio


```shell
sudo systemctl edit vbg-ricalcolo-aree
```

Esempio di configurazione (parametri da verificare)

```shell

[Service]
Environment="RICALCOLOAREE_MANAGEMENT_PORT=<mport>"
Environment="RICALCOLOAREE_SERVER_PORT=<port>"
Environment="RICALCOLOAREE_TOKEN_PWD=REDACTED
Environment="RICALCOLOAREE_TOKEN_URL=REDACTED
Environment="RICALCOLOAREE_TOKEN_USER=REDACTED


```

Abilitazione e avvio del servizio

```shell
sudo systemctl enable vbg-ricalcolo-aree
sudo systemctl start vbg-ricalcolo-aree
```

