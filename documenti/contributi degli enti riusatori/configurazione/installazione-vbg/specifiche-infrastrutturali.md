# SPECIFICHE INFRASTRUTTURALI

Le caratteristiche hardware dipendono molto dal tipo di utilizzo di VBG in quanto potrebbe gestire, ad esempio, bandi che richiedono risorse maggiormente indirizzate verso la parte
di frontend dei cittadini piuttosto che di backend. 

Nella scelta dell'hardware si devono considerare:
 - gli accessi stimati
 - le quantità di dati previsti (tenendo in considerazione che l'applicativo memorizza i file delle pratiche con possibilità di memorizzazione su oggetti blob nel database o su Filesystem), 
 - le politiche di sicurezza dei dati a livello di raid e backup delle informazioni.
A seconda che la configurazione sia più o meno estesa e del numero di accessi e utilizzo degli applicativi, è necessario prevedere macchine più o meno performanti.
Nelle tabelle riportate di seguito sono indicati i requisiti hardware da soddisfare in relazione ai server.

Tali requisiti sono stati presi da una tipica installazione di VBG in grado di gestire un buon numero di accessi sia di frontend che di backend

## Database Server
Le tipologie di database supportate sono 2:
 - Oracle 
 - MySql

E' possibile utilizzare un database server già esistente presso l'Ente oppure creare un server dedicato con queste caratteristiche

| Caratteristica | Requisito |
| ------ | ------- |
| **Sistema Operativo:** | Il sistema operativo va scelto tra:  Red Hat Enterprise Linux ES release 4 o superiore, CentOS 7.X, Ubuntu 20.04 LTS,  Windows Server 2016 o superiore |
| **CPU** | almeno 2 a 64 bit |
| **RDBMS** | Oracle 10.2 o superiore, MySql 5.x |
| **RAM** | 6gb |
| **Disco fisso nr. 1** | 100gb dedicato ( sarà necessario espanderlo in futuro in base all'utilizzo ) |

## Application Server
In questo server viene installato il cuore applicativo della suite VBG sviluppato con tecnologia JAVA

| Caratteristica | Requisito |
| ------ | ------- |
| **Sistema Operativo:** | Il sistema operativo va scelto tra: Red Hat Enterprise Linux ES release 4 o superiore, CentOS 7.X, Ubuntu 20.04 LTS, Windows Server 2016 o superiore |
| **CPU** | almeno 2 a 64 bit |
| **Application Server nr 1** | Tomcat 6.0.X, java 1.6.x |
| **Application Server nr 2** | Tomcat 6.0.X, java 1.6.x |
| **Application Server nr 3** | Tomcat 7.0.X, java 1.7.x |
| **RAM** | 10gb |
| **Disco fisso nr. 1** | 50gb dedicati |

## Web Server
In questo server vengono installati ulteriori features di VBG ( come ad esempio le schede dinamiche ) necessari alla corretta gestione della pratica e sono sviluppati con 
tecnologia .NET

| Caratteristica | Requisito |
| ------ | ------- |
| **Sistema Operativo:** | Attualmente il sistema operativo deve essere Microsoft Windows 2016 a 64 bit |
| **CPU** | almeno 2 a 64 bit |
| **Web Server** | IIS7 |
| **RAM** | 4gb |
| **Disco fisso nr. 1** | 60gb dedicati |

## File Server
Questo server memorizza tutti i documenti che transitano in VBG ( presentati dai cittadini o prodotti dall'Ente )
E' opzionale in quanto i documenti possono essere memorizzati all'interno delle tabelle del database; viene fortemente consigliato, qualora il database server sia MySQL.
E' possibile usare anche un percorso di rete condiviso già in forza presso l'ente.

| Caratteristica | Requisito |
| ------ | ------- |
| **Sistema Operativo:** | Il sistema operativo va scelto tra:  Red Hat Enterprise Linux ES release 4 o superiore, CentOS 7.X, Ubuntu 20.04 LTS, Windows Server 2016 o superiore |
| **CPU** | almeno 2 a 64 bit |
| **RAM** | 2gb |
| **Disco fisso nr. 1** | 200gb dedicati ( si potrebbe iniziare con una dimensione più bassa del 50% ed aumentarla su richiesta ) |

## Esposizione della suite VBG su internet/intranet

Il server "Web Server" deve essere esposto a chi presenta le domande ( per quanto riguarda il frontend ) e a chi gestisce l'istruttoria delle pratiche ( per quanto riguarda il backend ).
Questo può avvenire tramite esposizione diretta delle potre 80 o 443, oppure esposizione tramite reverse proxy ( quindi il server Web Server non transita in DMZ ).

In assenza di un reverse proxy, è possibile  prevedere una ulteriore macchina con le seguenti caratteristiche su cui verrà installato l'applicativo NGINX che verrà utilizzato 
come reverse proxy di frontiera per tutte le richieste da e verso VBG.

| Caratteristica | Requisito |
| ------ | ------- |
| **Sistema Operativo:** | Il sistema operativo va scelto tra:  Red Hat Enterprise Linux ES release 4 o superiore, CentOS 7.X, Ubuntu 20.04 LTS, Windows Server 2016 o superiore |
| **CPU** | almeno 2 a 64 bit |
| **RAM** | 3gb |
| **Disco fisso nr. 1** | 50gb dedicati |