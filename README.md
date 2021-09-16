# VBG (Virtual Business Gate) #

## Descrizione ##
"Virtual Business Gate" è la piattaforma unica (front office e back office) per la gestione di servizi procedurali frutto
dell’integrazione completa fra lo Sportello Unico per le Attività Produttive (SUAP),il Commercio, lo Sportello Unico per l’Edilizia
(SUE). Il Cittadino/Impresa, potrà interagire con il front- office di VBG sia attraverso il portale regionale sia accedendo
direttamente allo Sportello Unico Integrato (SUI). Dalla pagina del SUI, selezionando il Comune di interesse, sarà possibile
giungere al SUI dello specifico Comune nel quale sono esposti gli sportelli del SUAP, del SUE e del commercio. Il SUI, attraverso
la porta applicativa, dialogherà con il protocollo informatico ed eventualmente potrà interagire con un SUAP esistente. Il portale
VBG inoltre interagisce con il sottosistema del pagamenti e implementa la funzionalità di single signon.
Il back office del portale, oltre a consentire la completa gestione dei procedimenti relativi ai servizi esposti sul front-office, mette a disposizione del personale dell’Ente un potente strumento CMS per l’inserimento, l’aggiornamento e la personalizzazione
dei contenuti informativi, potendo nel contempo decidere quali servizi esporre all’utenza e il loro relativo livello di erogazione.
Il WFM di cui il prodotto VBG è dotato consente di disegnare il flusso di un qualsiasi procedimento correlandolo con il modello
organizzativo dell’Ente ed espone una porta applicativa per consentire la comunicazione con qualunque Protocollo Informatico a
norma “AIPA”.

### Benefici in termini di riduzione di costi conseguiti dall'Amministrazione ###
Il progetto “Virtual Business Gate” permette:
la velocizzazione generale dei procedimenti, grazie alla gestione digitale delle informazioni e la forte riduzione del cartaceo;
Benefici in termini di miglioramento del servizio reso a cittadini e imprese conseguiti dall'Amministrazione
Aumento della trasparenza dei procedimenti amministrativi mediante la pubblicazione di tutte le informazioni ritenute utili per
utenti, in qualsiasi momento dell’iter procedurale delle pratiche;una riorganizzazione gestionale e procedurale, non solo a livello
di singolo ente, ma anche a livello di interazione tra gli stessi; l’erogazione del servizio in qualsiasi momento e luogo,
indipendente dalla presenza fisica allo sportello.
l’erogazione del servizio in qualsiasi momento e luogo, indipendente dalla presenza fisica allo sportello
### Benefici derivanti all'amministrazione dall'elaborazione dei dati che la soluzione utilizza o genera ###
Costituzione di un “Data Base”, contenente i dati concernenti le domande di autorizzazione, il relativo iter procedurale e gli
adempimenti necessari per le procedure autorizzatorie, nonché tutte le informazioni disponibili a livello regionale, ivi comprese
quelle concernenti le attività promozionali, che saranno fornite forniti alle istituzioni che ne faranno apposita richiesta o nel caso
di adempimenti a leggi regionali o statali.
VBG, se integrato con il SI dell’Ente può anche fornire informazioni sia per quanto attiene al Sistema Tributi che per il PRG.
### Elementi di semplificazione della procedura tradizionale introdotti dalla soluzione ###
La soluzione VBG attiva un processo di razionalizzazione organizzativa e procedurale attraverso il miglioramento dei flussi dei
procedimenti, la standardizzazione dei moduli e la generazione di una base dati validata dal singolo utente. I maggiori benefici si
hanno soprattutto negli endoprocedmenti in quanto invitano gli enti coinvolti ad assumere un atteggiamento sempre più
cooperativo.
### Altri benefici derivanti dall'utilizzo della soluzionenon ricompresi nelle tipologie prima indicate ###
La diffusione di procedmenti tra gli enti porta ad una omogenizzazione, sul territorio regionale, delle modalità di accesso ai
servizi.


## Struttura del repository ##
Il repository ha la seguente struttura

Folder   |  Descrizione
---------|-------------
[bin](./bin)|Questo folder contiente i file compilati o binari delle web app java. Negli war sono inclusi i sorgenti applicativi. 
[documenti](./documenti)|Questo folder contiene la parte documentale del progetto. Il folder è suddiviso in sub folders per contenere documenti tra loro omogenei. 
[src](./src)|Questo folder contiente la parte del codice sorgente delle componenti .NET. I sorgenti degli applicativi java sono inclusi negli archivi war presenti nella directory [bin] 
[tools](./tools)|Questo folder contiene tutti gli eseguibili dei prodotti necessari al corretto funzionamento dell'applicativo e alla sua compilazione (es. maven, ant, ecc.)


## Ambiente di esercizio e di sviluppo ##
Prerequisiti di natura tecnica (hw e sw di base) per il funzionamento della soluzione.
+ Linux OS
+ Windows OS
+ Apache webserver
+ IIS Web Server
+ Nginx 
+ Tomcat 6
+ JRE 1.6.x
+ MySQL 5.x
+ Eclipse

## Licenza ##
Il software è rilasciato secondo la licenza EUPL 1.2. Per maggiori informazioni fare riferimento al [testo della licenza](https://joinup.ec.europa.eu/sites/default/files/custom-page/attachment/eupl_v1.2_it.pdf)

## Librerie utilizzate ##
Il file denominato ["Componenti e librerie"](./Componenti-e-librerie.xlsx) contiene la lista delle librerie utilizzate nell'applicativo e le relative licenze d'utilizzo.

## Riferimenti ##
In questa sezione vanno inseriti i riferimenti alla persona/persone ovvero alla struttura che ha in carico la gestione del prodotto e può fornire informazioni utili. 
