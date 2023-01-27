# INSTALLAZIONE ALFRESCO IN VBG

lo scopo di questa documentazione è dimostrare come installare alfresco sia nei sistemi operativi Windows che Linux.
Per ottenere ciò, devi avere docker e docker compose in esecuzione sulla tua macchina

## Installazione Docker Windows

1. Scaricare l'eseguibile da questo link [Download docker](https://docs.docker.com/desktop/windows/install/).

2. Fare doppio clic su Docker Desktop Installer.exe per eseguire il programma di installazione.

3. Se non hai già scaricato il programma di installazione (Docker Desktop Installer.exe), puoi ottenerlo da Docker Hub. In genere viene scaricato nella cartella Download oppure puoi eseguirlo dalla barra dei download recenti nella parte inferiore del browser web.

4. Quando richiesto, assicurati che l'opzione Usa WSL 2 invece di Hyper-V nella pagina Configurazione sia selezionata o meno a seconda della scelta del back-end.

5. Se il tuo sistema supporta solo una delle due opzioni, non sarai in grado di selezionare quale backend utilizzare.

6. Seguire le istruzioni della procedura guidata di installazione per autorizzare il programma di installazione e procedere con l'installazione.

7. Quando l'installazione ha esito positivo, fare clic su Chiudi per completare il processo di installazione.

8. Se il tuo account amministratore è diverso dal tuo account utente, devi aggiungere l'utente al gruppo docker-users. Esegui Gestione computer come amministratore e vai a Utenti e gruppi locali > Gruppi > utenti docker. Fare clic con il pulsante destro del mouse per aggiungere l'utente al gruppo. Esci e riconnettiti per rendere effettive le modifiche.

## Installazione Docker Ubuntu

Prima di installare Docker Engine per la prima volta su una nuova macchina host, è necessario configurare il repository Docker. Successivamente, puoi installare e aggiornare Docker dal repository.
Configura il repository

Aggiorna l'indice del pacchetto apt e installa i pacchetti per consentire ad apt di utilizzare un repository su HTTPS:

    sudo apt-get update

    sudo apt-get install\
        certificati ca\
        ricciolo\
        gnupg\
        lsb-rilascio

Aggiungi la chiave GPG ufficiale di Docker:

    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

Utilizzare il comando seguente per configurare il repository stabile. Per aggiungere il repository nightly o test, aggiungi la parola nightly o test (o entrambi) dopo la parola stable nei comandi seguenti. Ulteriori informazioni sui canali notturni e di prova.

     echo\
      "deb [arch=$(dpkg --print-architecture) firmato-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu\
      $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

Installa Docker Engine

Aggiorna l'indice del pacchetto apt e installa l'ultima versione di Docker Engine e containerd, oppure vai al passaggio successivo per installare una versione specifica:

    sudo apt-get update

    sudo apt-get install docker-ce docker-ce-cli containerd.io

Hai più repository Docker?

Se hai più repository Docker abilitati, l'installazione o l'aggiornamento senza specificare una versione nel comando apt-get install o apt-get update installa sempre la versione più alta possibile, che potrebbe non essere appropriata per le tue esigenze di stabilità.

Per installare una versione specifica di Docker Engine, elenca le versioni disponibili nel repository, quindi seleziona e installa:

Elenca le versioni disponibili nel tuo repository:

    apt-cache madison docker-ce

b. Installa una versione specifica utilizzando la stringa di versione dalla seconda colonna, ad esempio 5:18.09.1~3-0~ubuntu-xenial.

    sudo apt-get install docker-ce=<VERSION_STRING> docker-ce-cli=<VERSION_STRING> containerd.io

Questo comando scarica un'immagine di prova e la esegue in un contenitore. Quando il contenitore viene eseguito, stampa un messaggio ed esce.

Docker Engine è installato e funzionante. Il gruppo Docker viene creato ma non vi vengono aggiunti utenti. Devi usare sudo per eseguire i comandi Docker. Passa alla postinstallazione di Linux per consentire agli utenti non privilegiati di eseguire i comandi Docker e per altri passaggi di configurazione facoltativi.

## Installa Compose su sistemi desktop Windows

**Docker Desktop per Windows** include Compose insieme ad altre app Docker, quindi la maggior parte degli utenti Windows non ha bisogno di installare Compose separatamente. Per le istruzioni di installazione, vedere [Installa Docker Desktop su Windows](https://docs.docker.com/desktop/windows/install/).

## Installa Compose su sistemi desktop Linux

Esegui questo comando per scaricare la versione stabile corrente di Docker Compose:

    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/ bin/docker-componi

Per installare una versione diversa di Compose, sostituisci la 1.29.2 con la versione di Compose che desideri utilizzare. Per istruzioni su come installare Compose 2.2.3 su Linux, vedere Installare Compose 2.0.0 su Linux

In caso di problemi con l'installazione con curl, vedere la scheda Opzioni di installazione alternative sopra.

Applica le autorizzazioni eseguibili al binario:

    sudo chmod +x /usr/local/bin/docker-compose

# Installazione Alfresco

Questo programma ha le seguenti dipendenze:

-   Node.js
-   Yeoman

Puoi scaricare e installare `Node.js`dalla pagina web ufficiale:

<https://nodejs.org/en/download/>

Oppure puoi utilizzare uno qualsiasi dei gestori di pacchetti forniti dal prodotto:

<https://nodejs.org/en/download/package-manager/>

Una volta installato Node.js, puoi installare [Yeoman](http://yeoman.io/) come modulo:

```source-shell
$ npm install -g yo
```

E infine, puoi installare questo generatore:

```source-shell
$ npm install --global generator-alfresco-docker-installer
```

Viene fornita la distribuzione per Docker Compose, quindi le seguenti dipendenze devono essere soddisfatte dal server utilizzato per eseguire la configurazione generata:

-   Docker
-   Docker Componi

Puoi installare *Docker Desktop* per Windows o Mac e *Docker Server* per Linux.

<https://docs.docker.com/install/>

Devi anche aggiungere il programma *Docker Compose* alla tua installazione.

<https://docs.docker.com/compose/install/>

[](https://www.npmjs.com/package/generator-alfresco-docker-installer#running)Corsa
----------------------------------------------------------------------------------

Crea una cartella in cui verranno prodotti i file modello Docker Compose ed esegui il generatore.

> > > Se hai scaricato questo progetto, **non** riutilizzare la cartella del codice sorgente. Crea una cartella vuota per generare il modello Docker Compose ovunque.

```
$ mkdir docker-compose
$ cd docker-compose

$ yo alfresco-docker-installer

```

Sono disponibili diverse opzioni per creare la configurazione.

```
? Which ACS version do you want to use? 7.2

```

È possibile utilizzare Alfresco 6.1, 6.2, 7.0, 7.1 o 7.2

```
? How may GB RAM are available for Alfresco (16 is minimum required)? 16

```

La piattaforma Alfresco può funzionare con meno di 16 GB di RAM, ma si consiglia di fornire almeno 16 GB nel server Docker. Questo generatore limiterà la quantità di memoria per ogni servizio in modo che corrisponda alle tue risorse.

```
? Do you want to use HTTPs for Web Proxy?

```

Questa opzione abilita HTTP per ogni servizio. I certificati SSL predefiniti (pubblici e privati) sono forniti nella `config/cert`cartella. Questi certificati non sono consigliati per gli ambienti di produzione, quindi è necessario sostituire questi file con i tuoi certificati.

```
? What is the name of your server?

```

Se stai distribuendo su un server diverso da `localhost`, includi in questa opzione il nome del tuo server. Per esempio:`alfresco.com`

```
? Choose the password for your admin user (admin)

```

Alfresco fornisce una `admin`password per impostazione predefinita, scegline una diversa per le nuove distribuzioni. Quando si utilizza questa opzione su Alfresco Repository precompilati, questa impostazione non viene applicata, poiché la password è già archiviata nel database esistente. Per impostazione predefinita `system.preferred.password.encoding`utilizza `bcrypt10`l'algoritmo, quindi le password vengono archiviate nel database cifrato con salt.

```
? What HTTP port do you want to use (all the services are using the same port)? 80 or 443

```

Porta HTTP che deve essere utilizzata da ogni servizio. Se stai utilizzando un computer Linux, dovrai specificare una porta maggiore di 1024 quando non inizi come `root`utente.

```
? Do you want to use FTP (port 2121)? No

```

Abilita la configurazione per FTP, utilizzando per impostazione predefinita la porta 2121.

```
? Do you want to use MariaDB instead of PostgreSQL? No

```

Alfresco utilizza PostgreSQL per impostazione predefinita, ma in alternativa `MariaDB`può essere utilizzato come database.

```
? Are you using different languages (this is the most common scenario)? Yes

```

Per impostazione predefinita, molte organizzazioni archiviano documenti in lingue diverse o gli utenti accedono alla piattaforma con browser configurati in lingue diverse. Se questo è il tuo caso, abilita questa configurazione.

```
? Would you like to use HTTP or Shared Secret for Alfresco-SOLR communication?
  http  << Not available when using ACS 7.2+
  https
  secret

```

Per impostazione predefinita, la comunicazione tra Alfresco e SOLR avviene in chiaro `http`. Poiché le API esterne sono protette `proxy`e la console Web SOLR è protetta da utente e password, la configurazione predefinita potrebbe essere quella giusta per molte distribuzioni. **Questa opzione è stata disabilitata da ACS 7.2!**

Quando si utilizza `secret`l'opzione (disponibile solo dalla versione 7.1.0), la comunicazione Alfresco e SOLR avviene in HTTP semplice ma include una parola segreta condivisa nell'intestazione HTTP. Questo dovrebbe essere un approccio più sicuro per gli ambienti aperti.

Inoltre, quando si utilizza `https`l'opzione, la comunicazione tra SOLR e Alfresco utilizza il TLS reciproco. Questo protocollo include l'autenticazione del client tramite certificati digitali, che possono anche essere un'alternativa sicura.

```
? Do you want to use credentials for Events service (ActiveMQ)? No

```

Per impostazione predefinita, non è disponibile l'autenticazione per il servizio ActiveMQ. Quando si sceglie `Yes`questa opzione, verranno richiesti nome utente e password da utilizzare per accedere ad ActiveMQ Alfresco Broker. Se abiliti questa opzione, ricorda di utilizzare queste credenziali per utilizzare i messaggi da ActiveMQ quando utilizzi Out of Process SDK o simili.

```
? Do you want to create an internal SMTP server? No

```

Questo servizio fornisce un server SMTP interno (per le e-mail in uscita) basato su un Postfix Relay. Se desideri utilizzare il tuo server di posta, puoi configurarlo manualmente dopo la generazione del modello Docker Compose.

```
? Do you want to create an internal LDAP server? No

```

Questo servizio fornisce un server OpenLDAP interno (per l'autenticazione). Se desideri utilizzare il tuo server LDAP o AD, puoi configurarlo manualmente dopo la generazione del modello Docker Compose.

```
? Select the addons to be installed:
  Google Docs 3.1.0                           : https://github.com/Alfresco/google-docs/tree/V3.0.3
  JavaScript Console 0.7                      : https://github.com/AFaust/js-console
  Order of the Bee Support Tools 1.1.0.0      : https://github.com/OrderOfTheBee/ootbee-support-tools
  Share Site Creators 0.0.8                   : https://github.com/jpotts/share-site-creators
  Simple OCR 2.3.1 (for ACS 6.x)              : https://github.com/keensoft/alfresco-simple-ocr
  Alfresco OCR Transformer 1.0.0 (for ACS 7+) : https://github.com/aborroy/alf-tengine-ocr
  ESign Cert 1.8.2                            : https://github.com/keensoft/alfresco-esign-cert

```

Per impostazione predefinita viene fornito un piccolo catalogo di componenti aggiuntivi affidabili *,* ma puoi installarne altri utilizzando le cartelle di distribuzione.

```
? Are you using a Windows host to run Docker?

```

Quando si utilizza un host Windows per eseguire Docker, vengono utilizzati [i volumi Docker](https://docs.docker.com/storage/volumes/) standard invece di [Bind Docker volumi](https://docs.docker.com/storage/bind-mounts/) . Questa opzione è più facile da eseguire negli ambienti Docker Windows.

```
? Do you want to use a start script? Yes

```

Lo script wrapper per il file docker-compose offre funzionalità utili come attesa per il completamento dell'avvio di alfresco e molto altro. Utilizzare "./start.sh -h" per ulteriori informazioni.

```
? Do you want to get the script to create host volumes? No

When using Linux as host, you can get the script `create_volumes.sh` in Docker Compose folder. The script should be run only once, and be the first one to be executed, before the docker-compose up command, to create the initial `data` and `logs` host folders with the expected permissions.

```