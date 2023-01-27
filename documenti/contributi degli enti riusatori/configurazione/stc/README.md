# STC

## Definizioni

Nel seguente documento si intende per:

- STC

   Sistema Territoriale di Comunicazione
   E' un sistema che permette a più applicativi presenti su un territorio di comunicare tra loro; per territorio si intende ad esempio un comune e per applicativi si intendono i vari software di Back-End e Front-End in uso presso l'ente comune, la comunicazione è territoriale perché potrebbe coinvolgere anche Back-End di enti terzi come ASL, Arpa, VVF...

- NLA

   Nodo Locale Applicativo
   E' una componente che è in relazione uno ad uno con gli applicativi presenti nel territorio, in una comunicazione tra nodi NLA sarà sempre presente un nodo mittente ed un nodo destinatario. Un nodo può parlare la stessa lingua (formato xml) con qualsiasi nodo facente parte della rete STC. Un nodo NLA può rappresentare un mittente o un destinatario a seconda dei casi.

## A cosa serve STC

Il sistema STC è una componente tecnologica che rappresenta la rete degli applicativi presenti in un determinato territorio.

Il sistema STC è un orchestratore che fornisce la possibilità di far colloquiare tra loro gli applicativi presenti all'interno di un ente comunale (es. SUAP, edilizia, commercio, altri uffici), di estendere la comunicazione verso altre amministrazioni terze (ASL, VVF, Arpa ...) e verso enti di livello superiore (Provincia e Regione).

I nodi satellite di STC denominati NLA rappresentano delle  "plug-in" configurate per tradurre il "linguaggio" (tracciati XML), utilizzato per le comunicazioni interne alla rete STC, nel "linguaggio" utilizzato dal Back-End che il nodo rappresenta. Qualora la comunicazione avvenga verso un ente esterno il nodo si appoggerà ad una struttura di comunicazione predisposta allo scopo (es. porte di dominio).

Le informazioni che il sistema può veicolare riguardano tutti i contenuti presenti nei procedimenti in gestione agli enti. La struttura xml dei messaggi è frutto dell'esperienza maturata da In.I.T. nel corso degli anni con riferimento:

- alla gestione dei procedimenti SUAP, edilizia e in genere di tutti gli uffici degli enti comunali;
- all' integrazione con i contenuti di vari progetti E-Gov (es. People, AIDA, Jesyre);
- alla certificazione a Doc Area;
- all'integrazione con i sistemi regionali (Es. CART - Cooperazione Applicativa Regione Toscana)
- all'integrazione con la ComUnica proveniente dalle camere di commercio;

Ogni nodo NLA che si integra alla rete STC sarà sistematicamente in grado di comunicare con tutti gli altri nodi della rete.

La logica di comunicazione vede sempre un nodo mittente e un nodo destinatario.

Di seguito viene mostrata una lista di possibili nodi NLA che potrebbero fare parte di una rete STC:

- Nodo di Back-End del SUAP
- Nodo di Back-End dell'edilizia
- Nodo di Front-End di presentazione domanda e interazione on-line del cittadino
- Nodo di Back-End di ASL
  La comunicazione dipende dalla configurazione del nodo e può avvenire in cooperazione applicativa o via PEC in base a quanto stabilito con l'ente terzo che rappresenta. Il messaggio piò essere strutturato come da specifiche dpr 7/09/2010 n. 160.
- Nodo di Back-End di Arpa
  Come per il nodo ASL
- Nodo di Back-End della Regione
  Potrebbe essere un nodo che ha il compito di recuperare informazioni sugli eventi dei procedimenti e di comunicarle ad un repository statistico della Regione;
- Nodo per l'invio di PEC strutturata verso l'interessato (dpr 7/09/2010 n. 160)
  Il nodo sarà in grado di inviare una mail PEC strutturata come indicato dall'art.6 comma 1 "Ricevute telematiche" del "allegato tecnico" del dpr 7/09/2010 n. 160
- Nodo di ricezione PEC strutturata (dpr 7/09/2010 n. 160)
  Il nodo è in grado di leggere una PEC strutturata come da specifiche dell'allegato tecnico del dpr 7/09/2010 n. 160 e di trasferirla verso il nodo destinatario della PEC.

Nella figura 1 è mostrato STC e i nodi NLA che potrebbero prendere parte alla comunicazione.

![Diagramma](immagini/Aspose.Words.283d58c6-0a04-4850-8340-6af7c8a5efd7.001.png "Art38-Deploy2")

Fig. 1

Una volta sviluppato il nodo che parla con un ente terzo ASL, questi sarà configurato in modo che colloqui con l'ente per interoperabilità o PEC ed ogni nodo della rete STC (SUAP,Edilizia...) sarà in grado di colloquiare con ASL senza dover apportare modifiche al suo modo di colloquiare con STC.

## Funzionalità del sistema STC

Il sistema STC espone i seguenti Web Service utilizzabili dal generico nodo NLA, un nodo NLA non necessariamente deve essere in grado di consumare tutti i metodi, dipende dalla natura del nodo.

- **Login**: metodo per l'autenticazione del nodo NLA
  Per ogni nodo NLA sono rilasciate delle credenziali.
- **CheckToken**: metodo per la verifica della validità della chiave (token) che è stata generata durante l'operazione di login.
- **ListaPratiche**: viene utilizzato dal nodo mittente per ricevere, dal nodo destinatario, una lista di pratiche che rispettano determinati filtri; questo metodo viene utilizzato da un nodo di Front-End che tramite la sua area riservata permette al cittadino di vedere lo stato delle sue pratiche, in accoppiata con RihiestaPratica effettua una vera e propria visura della pratica.
- **RichiestaPratica**: ritorna la visura di una pratica indicando gli estremi della pratica del nodo destinatario della richiesta.
- **RichiestaPraticaCollegata**: ritorna la visura di una pratica indicando dagli estremi della pratica del mittente. Ad esempio dal SUAP si vuole conoscere lo stato del procedimento edilizio che è stato attivato in fase di avvio di un procedimento complesso.
- **NotificaAttivita**: metodo per l'invio della richiesta di esecuzione di una particolare attività nel nodo NLA destinatario; per attività si intende una richiesta di parere o semplicemente una comunicazione.
- **AllegatoBinario**: i messaggi relativi alle "pratiche" e alle "attività" possono fare riferimento a degli allegati i quali vengono comunicati solo per riferimento e possono essere "scaricati" o visualizzati attraverso questo metodo.

## Funzionalità del nodo NLA

Due nodi NLA non possono comunicare direttamente tra loro, ma lo fanno sempre tramite STC; il nodo mittente fa una richiesta a STC che la inoltra al nodo destinatario.
Gli identificativi univoci di pratiche e attività sono gestiti dal sistema STC in modo tale che il nodo NLA mittente può utilizzare i suoi identificativi per richiedere informazioni sulle pratiche collegate del destinatario.

Di seguito la lista dei metodi web che un nodo NLA deve implementare per entrare a far parte delle rete STC:

- **RichiestaPraticaNLA**: questo metodo ritorna i dettagli di una pratica.
- **InserimentoPraticaNLA**: questo metodo inserisce una pratica nell'applicativo che è rappresentato dal nodo, come risposta vengono tornati il numero assegnato alla pratica e l'eventuale protocollo.
- **InserimentoAttivitaNLA**: il metodo inserisce una attività nell'applicativo che il nodo rappresenta, l'attività può essere ad esempio "richiesta parere" da parte del mittente.
- **AllegatoBinarioNLA**: il metodo ritorna il file binario richiesto.

Ognuno dei metodi ha tra gli elementi dei messaggi di input la stringa **token** con la quale verificare la validità del messaggio inviato utilizzando il web method **CheckToken** esposto dal sistema STC.

## Utilizzo del servizio STC

## Autenticazione al sistema

Il web method **Login** consente l'autenticazione, da parte di un nodo NLA, al sistema STC, di seguito è riportato la segnatura come indicata nel WSDL relativo:

 ```xml
<wsdl:operation name="Login">
    <wsdl:input message="tns:LoginRequest" name="LoginRequest"/>
    <wsdl:output message="tns:LoginResponse" name="LoginResponse"/>
</wsdl:operation>
 ```

Il messaggio di input è caratterizzato dal seguente schema XSD:

Gli elementi username e password sono quelli forniti durante la registrazione del nodo NLA al sistema STC.

```xml
<element name="LoginRequest" >
<complexType>
    <sequence>
        <element name="username"  type="string">< element>
        <element name="password" type="string" >< element>
    </sequence>
</complexType>
</element>

```

Il metodo Login resituisce il seguente messaggio:

il messaggio è composto da un elemento booleano che indica l'esito

dell'autenticazione(true: autenticazione riuscita, false:autenticazione negata) e da una stringa che rappresenta il token rilasciato a seguito di un'autenticazione riuscita.

```xml

<element name="LoginResponse">
<complexType>
    <sequence>
        <element name="result" type="boolean">< element>
        <element name="token"  type="string">< element>
    </sequence>
</complexType>
</element>

```

## Verifica messaggi in ingresso

Questo web method può essere invocato dal nodo NLA ogni volta che il nodo riceve una chiamata ai web service esposti. Ognuno dei messaggi che riceve conterrà la voce token in modo tale da permettere all'NLA chiamato di verificare su STC la validità del messaggio inviato.

Il web method è così costituito:

```xml
<wsdl:operation name="CheckToken">
    <wsdl:input message="tns:CheckTokenRequest" name="CheckTokenRequest"/>
    <wsdl:output message="tns:CheckTokenResponse" name="CheckTokenResponse"/>
</wsdl:operation>

```

Il messaggio di input è rappresentato dal seguente schema XSD:

```xml

<element name="CheckTokenRequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
    </sequence>
</complexType>
</element>

```

Il messaggio di risposta è rappresentato dal seguente XSD:

result=true: verifica positiva del token, il messaggio è attendibile

result=false: verifica negativa del token, il messaggio non è attendibile

```xml
<element name="CheckTokenResponse" >
<complexType>
    <sequence>
        <element name="result"  type="boolean" >< element>
    </sequence>
</complexType>
</element>
```

## Notifica attività

Il web method NotificaAttivita permette l'invio da parte di un NLA mittente di una richiesta di esecuzione di una determinata attività nell'NLA di destinazione.

Es. può essere invocato da un nodo che rappresenta un SUAP il quale vuole notificare l'avvio di un procedimento edilizio al nodo destinatario dell'edilizia.

La segnatura del metodo è la seguente:

```xml
<wsdl:operation name="NotificaAttivita">
    <wsdl:input message="tns:NotificaAttivitaRequest" name="NotificaAttivitaRequest"/>
    <wsdl:output message="tns:NotificaAttivitaResponse" name="NotificaAttivitaResponse"/>
</wsdl:operation>

```

Il messaggio di input è caratterizzato dal seguente schema XSD:

```xml
<element name="NotificaAttivitaRequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
        <element name="sportelloMittente"  type="types:SportelloType" >
    </element>
    <element name="sportelloDestinatario"  type="types:SportelloType" >
</element>
<element name="datiAttivita"  type="types:DettaglioAttivitaType" >
</element>
<element name="rifPraticaDestinatario"  type="types:RiferimentiPraticaType"  maxOccurs="1"  minOccurs="0" >
</element>
</sequence>
</complexType>
</element>

```

Il messaggio è composto dal token rilasciato a seguito della chiamata al metodo Login, dall'identificativo dell'NLA mittente (elemento sportelloMittente), dall'identificativo dell'NLA destinatario (elemento sportelloDestinatario) e dal dettaglio dell'attività (elemento datiAttivita).

L'identificazione dell'NLA è contenuta nell'elemento SportelloType, costituito dal seguente XSD:

```xml
<complexType name="SportelloType" >
<sequence>
    <element name="idEnte"  type="string" >< element>
    <element name="idSportello"  type="string" >< element>
</sequence>
</complexType>
```

Gli elementi idEnte ed IdSportello rappresentano gli identificativi univoci di un nodo NLA e sono definiti in STC in fase di registrazione del nodo NLA.

I dati inviati nell'elemento datiAttivita corrispondono ai dati relativi all'attività eseguita nell'NLA mittente tramite i quali l'NLA destinatario è in grado di individuare l'attività da eseguire sul proprio sistema.

Il messaggio di risposta è rappresentato dallo schema XSD seguente:

```xml
<element name="NotificaAttivitaResponse" >
<complexType>
    <choice minOccurs="1" >
    <element name="dettaglioattivita"  type="types:RiferimentiAttivitaType"  minOccurs="0"  maxOccurs="1" >
</element>
<element name="dettaglioErrore"  type="types:ErroreType"  minOccurs="0"  maxOccurs="unbounded" >
</element>
</choice>
</complexType>
</element>

```

Il messaggio di risposta può essere quindi composto o dai riferimenti dell'attività inserita nell'NLA destinatario o da una lista di errori che motivano l'errata esecuzione del metodo.

## Richiesta pratica

STC mette a disposizione il web method RichiestaPratica che permette ad un NLA mittente di richiedere i dettagli di una pratica che risiede in un NLA destinatario.

La segnatura del metodo è la seguente:

```xml
<wsdl:operation name="RichiestaPratica">
    <wsdl:input message="tns:RichiestaPraticaRequest" name="RichiestaPraticaRequest"/>
    <wsdl:output message="tns:RichiestaPraticaResponse" name="RichiestaPraticaResponse"/>
</wsdl:operation>

```

Il messaggio di input è caratterizzato dal seguente schema XSD:

```xml
<element name="RichiestaPraticaRequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
        <element name="sportelloMittente"  type="types:SportelloType" >
    </element>
    <element name="sportelloDestinatario"  type="types:SportelloType" >
</element>
<element name="rifPratica"  type="types:RiferimentiPraticaType" >
</element>
</sequence>
</complexType>
</element>

```

Come si vede dallo schema sono presenti i tre elementi che caratterizzano tutti i messaggi scambiati tra NLA e STC (tranne la Login e la CheckToken): token, sportelloMittente e sportelloDestinatario.

L'elemento distintivo è l'idPratica che identifica la pratica ricercata nell'NLA di destinazione.

In risposta a tale input avremo il seguente messaggio (schema XSD):

```xml
<element name="RichiestaPraticaResponse" >
<complexType>
    <sequence>
        <element name="dettaglioPratica"  type="types:DettaglioPraticaType"  maxOccurs="1"  minOccurs="0" >
    </element>
    <element name="dettaglioErrore"  type="types:ErroreType"  minOccurs="0"  maxOccurs="unbounded" >
</element>
</sequence>
</complexType>
</element>

```

Il messaggio riporta il dettaglio della pratica richiesta. ( per i dettagli dei tipi si rimanda al capitolo "STC wsdl del servizio").

## Richiesta allegato

Il web method AllegatoBinario permette di recuperare eventuali files collegati ad una pratica

La segnatura del metodo è la seguente:

```xml
<wsdl:operation name="AllegatoBinario">
    <wsdl:input message="tns:AllegatoBinarioRequest" name="AllegatoBinarioRequest"/>
    <wsdl:output message="tns:AllegatoBinarioResponse" name="AllegatoBinarioResponse"/>
</wsdl:operation>
```

Il messaggio di input è rappresentato dal seguente schema XSD:

```xml
<element name="AllegatoBinarioRequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
        <element name="sportelloMittente"  type="types:SportelloType" >
    </element>
    <element name="sportelloDestinatario"  type="types:SportelloType" >
</element>
<element name="riferimentiAllegato"  type="types:RiferimentiAllegatoType" >
</element>
</sequence>
</complexType>
</element>
```

Il messaggio di output è rappresentato dal seguente schema XSD:

```xml
<element name="AllegatoBinarioResponse" >
<complexType>
    <sequence>
        <element name="mimeType"  type="string"  minOccurs="0"  maxOccurs="1" />
        <element name="binaryData"  type="base64Binary" />
        <element name="fileName"  type="string" />
    </sequence>
</complexType>
</element>

```

## Gestione errori del servizio STC

La notifica degli errori riscontrati dal sistema STC al nodo NLA chiamante avviene sempre sul canale http tramite l'invio di un envelope SOAP contenente come unica parte dell'elemento body, l'elemento fault.

L'elemento fault contiene solamente due tipi di codici:

**SOAP-ENV:Client**, indica un errore relativo al messaggio inviato dal client del servizio (nodo NLA).

**SOAP-ENV:Server**, indica un errore interno al sistema STC

Esempio di errore di tipo SOAP-ENV:Client

```xml

<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Body>
        <SOAP-ENV:Fault>
            <faultcode>SOAP-ENV:Client</faultcode>
            <faultstring xml:lang="en">Validation error</faultstring>
            <detail>
                <spring-ws:ValidationError xmlns:spring-ws="http://springframework.org/spring-ws">

cvc-complex-type.2.4.b: The content of element 'q0:CheckTokenRequest' is not complete. One of '{"http://sigepro.init.it/rte":token}' is expected 
</spring-ws:ValidationError>
            </detail>
        </SOAP-ENV:Fault>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>

```

Esempio di errore di tipo SOAP-ENV:Server

```xml

<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Body>
        <SOAP-ENV:Fault>
            <faultcode>SOAP-ENV:Server</faultcode>
            <faultstring xml:lang="en">Internal error</faultstring>
        </SOAP-ENV:Fault>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>

```

## Utilizzo del servizio NLA

### Richiesta pratica NLA

NLA attraverso questo web method ritorna i dati di una pratica presente nel suo back-office.
Deve essere utilizzato il tag "rifPratica" per rintracciare una pratica nel back-office di riferimento, si consiglia di seguire le ricerche utilizzando i tag secondo la seguente priorità:

1. rifPratica.idPratica
1. rifPratica.numeroPratica e rifPratica.dataPratica
1. rifPratica.numeroProtocolloGenerale e rifPratica.dataProtocolloGenerale

Il tag "altriDati" sarà popolato a seconda degli accordi tra NLA e potrebbe contenere informazioni aggiuntive per recuperare una pratica.

Il metodo può tornare una sola pratica, nel caso in cui i filtri selezionino più pratiche si consiglia di generare un errore.

Segnatura metodo

```xml
<wsdl:operation name="**RichiestaPraticaNLA**">
 <wsdl:input 

message="**tns:RichiestaPraticaNLARequest**" name="**RichiestaPraticaNLARequest**" /> 
 <wsdl:output 

message="**tns:RichiestaPraticaNLAResponse**" name="**RichiestaPraticaNLAResponse**" /> 
 </wsdl:operation>

XSD messaggio di input

<element name=*"RichiestaPraticaNLARequest"*>

<complexType>

<sequence>

<element name=*"token"* type=*"string"*></element>

<element name=*"sportelloMittente"* type=*"types:SportelloType"*>

</element>

<element name=*"sportelloDestinatario"* type=*"types:SportelloType"*>

</element>

<element name=*"rifPratica"* type=*"types:RiferimentiPraticaType"*></element>

</sequence>

</complexType>

</element>
```

XSD messaggio di output

```xml
<element name=*"RichiestaPraticaNLAResponse"*>

<complexType>

<sequence>

<element name=*"dettaglioPratica"* type=*"types:DettaglioPraticaType"* maxOccurs=*"1"* minOccurs=*"0"*></element>

<element name=*"dettaglioErrore"* type=*"types:ErroreType"* minOccurs=*"0"* maxOccurs=*"unbounded"*></element>

</sequence>

</complexType>

</element>

```

### Inserimento pratica

Questo metodo viene utilizzato per comunicare all'NLA di inserire una nuova pratica nel suo back-office, nonostante il sistema STC sia in grado di conoscere tutte le pratiche che sono transitate in esso si consiglia di utilizzare gli elementi "dettagliPratica" e "rifPraticaDestinatario" per ricercare la pratica evitando un doppio inserimento.
La richiesta del doppio inserimento potrebbe avvenire per le pratiche che non sono presenti in STC sin dalla loro creazione e per sopraggiunti errori sulla rete di comunicazione.

Per una corretta gestione dei messaggi è consigliabile consultare la sezione 6.5

Segnatura metodo

```xml
<wsdl:operation name="InserimentoPraticaNLA">
    <wsdl:input message="tns:InserimentoPraticaNLARequest" name="InserimentoPraticaNLARequest"/>
    <wsdl:output message="tns:InserimentoPraticaNLAResponse" name="InserimentoPraticaNLAResponse"/>
</wsdl:operation>

```

XSD messaggio di input

```xml

<element name="InserimentoPraticaNLARequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
        <element name="sportelloMittente"  type="types:SportelloType" >
    </element>
    <element name="sportelloDestinatario"  type="types:SportelloType" >
</element>
<element name="dettaglioPratica"  type="types:DettaglioPraticaType" >
</element>
<element name="rifPraticaDestinatario"  type="types:RiferimentiPraticaType"  maxOccurs="1"  minOccurs="0" >< element>
</sequence>
</complexType>
</element>

```

XSD messaggio di output

```xml

<element name="InserimentoPraticaNLAResponse" >
<complexType>
    <choice minOccurs="1" >
    <element name="dettaglioPratica"  type="types:RiferimentiPraticaType"  minOccurs="0" >
</element>
<element name="dettaglioErrore"  type="types:ErroreType"  minOccurs="0"  maxOccurs="unbounded" >
</element>
</choice>
</complexType>
</element>

```

### Inserimento attività NLA

Questo metodo viene utilizzato per comunicare al NLA di inserire una nuova attività nel back-office, ad esempio "Richiesta di parere", "Rilascio di parere", "Richiesta di integrazione documentale", "Documenti integrati"...

Segnatura metodo

```xml
<wsdl:operation name="InserimentoAttivitaNLA">
    <wsdl:input message="tns:InserimentoAttivitaNLARequest" name="InserimentoAttivitaNLARequest"/>
    <wsdl:output message="tns:InserimentoAttivitaNLAResponse" name="InserimentoAttivitaNLAResponse"/>
</wsdl:operation>

```

XSD messaggio di input

```xml
<element name="InserimentoAttivitaNLARequest" >
<complexType>
    <sequence>
        <element name="token"  type="string" >< element>
        <element name="sportelloMittente"  type="types:SportelloType" >
    </element>
    <element name="sportelloDestinatario"  type="types:SportelloType" >
</element>
<element name="datiAttivita"  type="types:DettaglioAttivitaType" >
</element>
<element name="rifPraticaMittente"  type="types:RiferimentiPraticaType"  maxOccurs="1"  minOccurs="0" >< element>
</sequence>
</complexType>
</element>

```

XSD messaggio di output

```xml

<element name="InserimentoAttivitaNLAResponse" >
<complexType>
    <choice minOccurs="1" >
    <element name="dettaglioAttivita"  type="types:RiferimentiAttivitaType"  minOccurs="0" >
</element>
<element name="dettaglioErrore"  type="types:ErroreType"  minOccurs="0"  maxOccurs="unbounded" >
</element>
</choice>
</complexType>
</element>

```

### Richiesta allegato NLA

Questo metodo viene utilizzato per richiedere al NLA un allegato presente nel back-office, questa modalità avviene quando nella richiesta di inserimentoPratica e inserimentoAttivita gli allegati non vengono passati in-line ma solo per riferimento.

Segnatura metodo

```xml
<wsdl:operation name="AllegatoBinarioNLA">
    <wsdl:input message="tns:AllegatoBinarioNLARequest" name="AllegatoBinarioNLARequest"/>
    <wsdl:output message="tns:AllegatoBinarioNLAResponse" name="AllegatoBinarioNLAResponse"/>
</wsdl:operation>

```

XSD messaggio di input

```xml
<element name="AllegatoBinarioNLARequest">
    <complexType>
        <sequence>
            <element name="token" type="string"/>
            <element name="sportelloMittente" type="types:SportelloType">

</element>
            <element name="sportelloDestinatario" type="types:SportelloType">

</element>
            <element name="riferimentiAllegato" type="types:RiferimentiAllegatoType"/>
        </sequence>
    </complexType>
</element>

```

XSD messaggio di output

```xml
<element name="AllegatoBinarioNLAResponse">
    <complexType>
        <sequence>
            <element name="mimeType" type="string" minOccurs="0" maxOccurs="1"/>
            <element name="binaryData" type="base64Binary"/>
            <element name="fileName" type="string"/>
        </sequence>
    </complexType>
</element>

```

## Diagrammi di sequenza

Nei diagrammi seguenti è evidenziata l'interazione tra due nodi NLA, nodo mittente (NLA-MIT) e nodo destinatario (NLA-DEST) mediata dal sistema STC.

Nei diagrammi non è evidenziata la fase di login che deve precedere ognuna delle chiamate in modo da recuperare un token valido.

Per ognuna delle chiamate effettuate da STC verso NLA è possibile utilizzare il metodo CheckToken per avere la certezza di essere stati chiamati dal STC di riferimento prima del processamento del messaggio.

### Diagrammi Notifica attività

![Notifica attività](immagini/Aspose.Words.283d58c6-0a04-4850-8340-6af7c8a5efd7.002.png)

1. Dopo lo scatenarsi di un evento nel back-office mittente l'NLA mittente notifica all'NLA destinatario una attività tramite STC;
1. STC verifica se nel suo DB c'è già stata una comunicazione tra mittente e destinatario in riferimento alla pratica oggetto della notifica del mittente
   1. Nello schema STC non trova comunicazioni pregresse;
1. STC richiede all'NLA mittente la pratica
1. STC aggiorna il suo DB e richiede l'inserimento della pratica all'NLA destinatario;
1. L'NLA destinatario inserisce la pratica nel back-office destinatario e torna i riferimenti ad STC;
1. STC aggiorna nel suo DB la relazione tra le due pratiche;
1. STC richiede l'inserimento dell'attività all'NLA destinatario indicando che è una attività riferita alla pratica (idPratica) che il back-office ha appena inserito;
1. L'NLA destinatario inserisce l'attività e torna l'identificativo ad STC;
1. STC aggiorna il suo DB mettendo in relazione gli idAttivita del mittente e del destinatario che appartengono alle due pratiche messe in relazione al passaggio 6.
1. STC risponde all'NLA mittente l'esito della notifica.
   Per l'NLA mittente non è necessario salvare gli id della pratica e dell'attività del destinatario perché nelle future comunicazioni utilizzerà i suoi riferimenti delegando STC a conoscere quali sono quelli riferiti al destinatario.

### Diagrammi Richiesta pratica

![Richiesta Pratica](immagini/Aspose.Words.283d58c6-0a04-4850-8340-6af7c8a5efd7.003.png)

Il metodo serve a tornare la visura di una pratica è lo stesso utilizzato da STC nel passaggio 3 dello schema "Notifica Attività".

### Diagrammi Richiesta allegato

![Richiesta allegato](immagini/Aspose.Words.283d58c6-0a04-4850-8340-6af7c8a5efd7.004.png)

La richiesta allegato è implementata per evitare di passare gli allegati in-line, nello schema "Notifica Attività" gli allegati possono essere scaricati dal destinatario in modo asincrono rispetto alla richiesta di inserimento pratica o inserimento attività.

## WSDL STC

Visualizzabile al seguente URL:

ultima versione sviluppo: [http://devel3.init.gruppoinit.it/stc/services/stc?wsdl](modellazione STC)

## WSDL NLA

Visualizzabile al seguente URL:

ultima versione sviluppo: [http://devel3.init.gruppoinit.it/stc/services/nla?wsdl](modellazione NLA)

## Nodi NLA

## Nla-Proxy

Il nodo permette di far comunicare due VBG installati su due datacenter distinti.
la documentazione del nodo e della configurazione è disponibile nella cartella
[nla-proxy](./nla-proxy/README.md)
