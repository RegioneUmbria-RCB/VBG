# Pubblicazione area riservata core

## Verificare che il Framework .net core 6.0 sia installato

Da terminale lanciare il comando

`
dotnet --list-runtimes
`

Nell'output dovrebbe comparire il testo

`
Microsoft.AspNetCore.App 6.0.*
`

dove * è una delle sottoversioni del framework (il numero è ininfluente).

Se si riceve un errore "dotnet non è stato riconosciuto come un comando" o se la versione 6 del runtime non è stata trovata passare alla sezione "Installazione del runtime di .net (WINDOWS/LINUX)"

## Installazione del runtime di .net (WINDOWS)

Andare all'indirizzo <https://dotnet.microsoft.com/en-us/download/dotnet/6.0> e scaricare la versione di *RUNTIME* per il proprio sistema operativo (sotto windows si consiglia di installare la hosting bundle)

## Installazione o aggiornamento sotto IIS

Estrarre i files presenti nello zip scaricato da devel3 nella cartella "\VBG\ar-core". E' necessario arrestare IIS ma potrebbe bastare anche solo arrestare il pool di applicazioni che serve l'application ar-core.

### SOLO se si sta effettuando la prima installazione su questa macchina

- Creare una cartella (ad esempio sotto \VBG\ar-core) e configurarla come applicazione sotto IIS.
  - Sarebbe preferibile utilizzare un pool di connessioni specifico (ar-core) che deve girare a 64 bit e utilizzare la modalità integrata. Se non è stato modificato si può copiare dal DefaultAppPool
- Portare phantomjs nella macchina
  - Scaricare il file phantomjs.bundle.zip da <http://devel3.init.gruppoinit.it/download/phantomjs>
  - Creare una cartella "phantomjs" sotto "ar-core"
  - Estrarre i contenuti del file "phantomjs.bundle.zip" nella cartella creata nel passaggio precedente
- Rinominare il file "web.config_" in "web.config"
- Rinominare il file "appsettings.dev.json" in "appsettings.prod.json"
- (SOLO SU INSTALLZIONE FVG) Rinominare il file "managed-data-mappings.nocopy.xml" che si trova nella cartella "ar-core/moduli-fvg/compilazione" in "managed-data-mappings.xml"
- Aprire il file "appsettings.prod.json" e verificare che gli url e le credenziali contenute siano corrette
  - Configurare il path in cui è stato estratto l'eseguibile di phantomjs nella sezione "Settings"->"phantomjsPath" (il path può essere relativo o assoluto) probabilmente sarà ./phantomjs (gli slash sono al rovescio come su linux)
- A questo punto l'installazione dovrebbe essere completata

## Prima installazione sotto Linux (Ubuntu)

### Installazione del runtime di .net (LINUX)

Prima di installare .NET, eseguire i comandi seguenti per aggiungere la chiave di firma del pacchetto
Microsoft all'elenco di chiavi attendibili

```shell
wget https://packages.microsoft.com/config/ubuntu/22.04/packages-microsoftprod.deb -O packages-microsoft-prod.deb
sudo dpkg -i packages-microsoft-prod.deb
rm packages-microsoft-prod.deb
```

Inoltre, per utilizzare HTTPS copiare la seguente riga

`
sudo apt-get install apt-transport-https
`

In seguito installare l’SDK attraverso il seguente comando

`
sudo apt-get update && sudo apt-get install -y dotnet-sdk-6.0
`

A questo punto potrebbe risultare utile aggiornare i pacchetti installati con il comando:

`
sudo apt-get update
`

Infine per verificare che .NET sia correttamente installato, copiare la seguente riga

`
dotnet --version
`

### (Opzionale) Installare e configurare NGINX

Il prossimo step è quello di installare il proxy server Nginx e avviarlo come segue:

```shell
sudo apt-get install nginx
sudo service nginx start
```

A questo punto bisogna configurare il Proxy modificando il file default.txt nella cartella sites-available,
quindi la prima cosa da fare è spostarsi nella directory con il seguente comando:

`
cd /etc/nginx/sites-available/
`

In seguito aprire il file default.txt e modificare la sezione “location” come mostrato di seguito:

```nginx
location / {
    proxy_pass http://localhost:5000;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection keep-alive;
    proxy_set_header Host $host;
    proxy_cache_bypass $http_upgrade;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    # First attempt to serve request as file, then
    # as directory, then fall back to displaying a 404.
    try_files $uri $uri/ =404;
}
```

### Installazione dell'applicazione .net

- Installare i prerequisiti
  - scaricare Phantomjs da <https://phantomjs.org/download.html> (seguire le indicazioni per Linux 64bit)
  - installare il pacchetto "fontconfig" `sudo apt-get install fontconfig`
  - installare "libgs-dev" `sudo apt-get install libgs-dev`
- Creare (se non è già stata creata) la cartella /var/www/dotnet/ar-core
- Copiare all'interno i files contenuti nello zip di distribuzione.
- Rinominare il file "appsettings.dev.json" in "appsettings.prod.json"
- Aprire il file "appsettings.prod.json" e verificare che gli url e le credenziali contenute siano corrette
- (SOLO SU INSTALLZIONE FVG) Rinominare il file "managed-data-mappings.nocopy.xml" che si trova nella cartella "ar-core/moduli-fvg/compilazione" in "managed-data-mappings.xml"
- A questo punto l'insstallazione dovrebbe essere completata

### Configurazione di Kestrel come servizio

creare il file `ar-core.service` nella cartella `/etc/systemd/system/`

Copiare all'interno (verificando che i path siano corretti):

```ini
[Unit]
Description=Area riservata core
[Service]
WorkingDirectory=/var/www/dotnet/ar-core
ExecStart=dotnet /var/www/dotnet/ar-core/AreaRiservataCore.dll
Restart=always
RestartSec=10
KillSignal=SIGINT
SyslogIdentifier=ar-core
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
sudo systemctl enable ar-core.service
sudo systemctl start ar-core.service
```

A questo punto è possibile visualizzare lo stato del servizio come segue:

`sudo systemctl status ar-core.service`

A questo punto il servizio è correttamente configurato e l’applicazione verrà lanciata in maniera automatica
senza la necessità di avviarla manualmente.
