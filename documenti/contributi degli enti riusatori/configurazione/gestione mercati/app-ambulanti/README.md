# APP Ambulanti

## Installazione (dalla versione 2.112)

Scaricare l'ultima versione dell'applicazione da http://devel3.init.gruppoinit.it/download/2.X/core. I files dell'applicazione sono:

- **app-ambulanti-csi.zip** per l'installazione CSI
- **app-ambulanti-multicomune.zip** per tutte le altre installazioni

## Prerequisiti

### Verificare che il Framework .net core 7.0.X sia installato

Da terminale lanciare il comando

`
dotnet --list-runtimes
`

Nell'output dovrebbe comparire il testo

`
Microsoft.AspNetCore.App 7.0.*
`

dove * è una delle sottoversioni del framework (il numero è ininfluente).

Se si riceve un errore "dotnet non è stato riconosciuto come un comando" o se la versione 7 del runtime non è stata trovata passare alla sezione **Installazione del runtime di .net (WINDOWS/LINUX)** altrimenti passare alla sezione **Prima installazione dell'applicazione sotto WINDOWS/LINUX** oppure **Aggiornamento dell'applicazione**

### Installazione del runtime di .net (WINDOWS/LINUX)

Dalla versione 2.112 l'app è un'applicazione .net core che serve le pagine angular tramite server interno o tramite IIS
Prima di procedere all'installazione dell'app è necessario installare l' ASP.NET Core Runtime 7.0.X dall'indirizzo <https://dotnet.microsoft.com/en-us/download/dotnet/7.0>

- Nel caso di installazione sotto windows è sufficiente installare solamente la **hosting bundle**
- Nel caso di installazione sotto linux fare riferimento alle guide relative alle varie distribuzioni presenti nella sezione **ASP.NET Core Runtime 7.0.**->**Linux** (se possibile evitare di installare i binari direttamente)

## Prima installazione dell'applicazione sotto WINDOWS

 Creare una cartella (ad esempio sotto \VBG\app-ambulanti) e configurarla come applicazione sotto IIS. Nel caso di installazione multitenant la cartella va chiamata in base al nome dell'installazione (ad esempio \vbg\app-ambulanti-nomecomune) in quanto l'app non è multicomune e ogni installazione avrà la sua configurazione.

- Sarebbe preferibile utilizzare un pool di connessioni specifico (app-ambulanti) che deve girare a 64 bit e utilizzare la modalità integrata. Se non è stato modificato si può copiare dal DefaultAppPool
- Effettuare un backup del file config.json
- Eliminare tutti i files contenuti, eventualmente, all'interno della cartella \app-ambulanti
- Estrarre i files scaricati nella cartella \app-ambulanti
- Non dovrebbe essere necessario modificare il file web.config
- Rinominare il file **appsettings.Development.json** in **appsettings.prod.json**
- Aprire il file **appsettings.prod.json** e verificare che gli url e i parametri contenuti siano corretti confrontando con il file config.json di backup
- A questo punto l'installazione dovrebbe essere completata

## Prima installazione dell'applicazione sotto LINUX

_Questa guida vale nel caso in cui la cartella dell'applicazione sia stata chiamata "app-ambulanti", nel caso di installazione con nome diverso (es. app-ambulanti-nomecomune vanno aggiornati i nomi dei files presenti in seguito e i relativi puntamenti)._

- Creare la cartella dell'applicazione in **/var/www/dotnet/7.0/app-ambulanti** ed estrarre al suo interno i files contenuti nello zip
- Rinominare il file **"appsettings.Development.json"** in **"appsettings.prod.json"**
- Aprire il file **"appsettings.prod.json"** e verificare che gli url e i parametri contenuti siano corretti
- A questo punto l'installazione dovrebbe essere completata e si può procedere alle fasi successive

### Configurazione di Kestrel come servizio

Questa guida vale nel caso in cui la cartella dell'applicazione sia stata chiamata "app-ambulanti", nel caso di installazione con nome diverso (es. app-ambulanti-nomecomune vanno aggiornati i nomi dei files presenti in seguito e i relativi puntamenti).

creare il file `app-ambulanti.service` nella cartella `/etc/systemd/system/`

Copiare all'interno (verificando che i path siano corretti):

```ini
[Unit]
Description=App ambulanti NOME_COMUNE
[Service]
WorkingDirectory=/var/www/dotnet/7.0/app-ambulanti
ExecStart=dotnet /var/www/dotnet/7.0/app-ambulanti/AppAmbulanti.dll
Restart=always
RestartSec=10
KillSignal=SIGINT
SyslogIdentifier=app-ambulanti
User=root
Environment=ASPNETCORE_ENVIRONMENT=Production
Environment=DOTNET_PRINT_TELEMETRY_MESSAGE=false
[Install]
WantedBy=multi-user.target
```

Se il comando dotnet non dovesse essere trovato va impostato il path completo riportandoci l'output del comando 

`which dotnet`

(normalmente è /usr/bin/dotnet)

Una volta terminate le modifiche, il passaggio successivo è quello di abilitare e avviare il servizio con i
seguenti comandi:

```shell
sudo systemctl enable app-ambulanti.service
sudo systemctl start app-ambulanti.service
```

A questo punto è possibile visualizzare lo stato del servizio come segue:

`sudo systemctl status app-ambulanti.service`

A questo punto il servizio è correttamente configurato e l’applicazione verrà lanciata in maniera automatica
senza la necessità di avviarla manualmente.

## Aggiornamento dell'applicazione

Per aggiornare l'applicazione è sufficiente arrestare il servizio (fermando il pool di IIS sotto windows o tramite systemctl sotto linux) e sovrascrivere i files con quelli estratti dalla versione dell'applicazione.
Eventualmente verificare se nel file **appsettings.Development.json** siano presenti parametri non presenti in **appsettings.prod.json**. Nel caso in cui siano presenti andranno riportati nel file **appsettings.Development.json** e configurati in maniera coerente con l'installazione

## Note per l'installazione sotto NGINX

Nel caso di installazione sotto NGINX normalmente i parametri per la location sono i seguenti **(aggiornare se necessario l'indirizzo e la porta su cui è in funzione l'applicazione)**:

```nginx
		location /NOME_LOCATION/ {
			proxy_http_version 1.1;
			proxy_set_header Upgrade $http_upgrade;
			proxy_set_header Connection 'upgrade';
			proxy_set_header Host $host;
			proxy_cache off;
			proxy_buffering off;
			proxy_cache_bypass $http_upgrade;
			proxy_read_timeout 100s;

			proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
			proxy_set_header X-Forwarded-Proto $scheme;
			proxy_set_header X-Forwarded-Prefix '/NOME_LOCATION';

            proxy_pass http://localhost:5000/;
		}

```
