# Prova del servizio SOAP di validazione con SoapUI

## Prerequisiti

- Applicazione deployata e avviata (es. Tomcat con il WAR).
- SoapUI installato (o Ready API).

---

## Quick start: preparare una chiamata SOAP per testare la validazione

### Passo 1 – Creare il progetto SoapUI

1. **File → New SOAP Project**.
2. **Project Name**: es. `DSS-Validazione`.
3. **Initial WSDL**:  
   `http://localhost:8090/vbg-legacy-dss-webapp/wservice/validation?wsdl`  
   (usa porta e context che usi tu; es. 8080 se Tomcat è su 8080).
4. **Create Requests** = selezionato → OK.

SoapUI crea le operazioni (es. **validateSignature** o **validateDocument**). Usa quella che nel WSDL riceve un `DataToValidate` / `dataToValidate`.

### Passo 2 – Ottenere il Base64 del PDF da validare

**PowerShell (Windows):**

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\percorso\documento_firmato.pdf"))
```

Copia l’output (una lunga stringa senza spazi/a capo).

**Alternativa – salvare in file (utile per PDF grandi):**

```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\percorso\documento_firmato.pdf")) | Set-Content -Path "base64.txt" -NoNewline
```

Poi apri `base64.txt` e copia il contenuto.

### Passo 3 – Compilare la richiesta SOAP

Apri la richiesta dell’operazione di validazione **validateSignature**. Il servizio DSS si aspetta:

- **Namespace**: `http://validation.dss.esig.europa.eu/`
- **Nome del parametro**: **`dataToValidateDTO`** (non `dataToValidate`)

Sostituisci il body con questo schema:

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:validateSignature xmlns:ns2="http://validation.dss.esig.europa.eu/">
         <dataToValidateDTO>
            <signedDocument>
               <bytes>INCOLLA_QUI_LA_STRINGA_BASE64_DEL_PDF</bytes>
               <name>documento_firmato.pdf</name>
            </signedDocument>
         </dataToValidateDTO>
      </ns2:validateSignature>
   </soap:Body>
</soap:Envelope>
```

- Sostituisci **`INCOLLA_QUI_LA_STRINGA_BASE64_DEL_PDF`** con la stringa Base64 ottenuta al passo 2.
- **`<name>`**: puoi lasciare `documento_firmato.pdf` o usare il nome reale del file (solo per riferimento).

### Passo 4 – Inviare e leggere la risposta

- Clicca **Submit** (icona play verde).
- La risposta SOAP conterrà un **WSReportsDTO** con:
  - **simpleReport** (o **simpleReportXml**): esito per ogni firma (**VALID** / **INDETERMINATE** / **INVALID**).
  - **detailedReport**: dettagli dei building block.
  - **diagnosticData**: certificati, timestamp, catena, ecc.

Se la richiesta è malformata (namespace/elementi sbagliati), il server può restituire un fault SOAP: in quel caso usa la richiesta **generata da SoapUI** dal WSDL e cambia solo il valore di `<bytes>` e `<name>`.

---

## 1. URL del servizio

- **Endpoint**: `http://<host>:<port>/<context-root>/wservice/validation`  
  Esempio: `http://localhost:8090/vbg-legacy-dss-webapp/wservice/validation`
- **WSDL**: stesso URL con `?wsdl`  
  Esempio: `http://localhost:8090/vbg-legacy-dss-webapp/wservice/validation?wsdl`

Sostituisci host, porta e context-root con i valori del tuo ambiente.

### Compatibilità SIGEPRO / client legacy

I client costruiti sull’ex DSS Web Application (es. SIGEPRO) cercano nel WSDL il **servizio** con QName `{http://impl.ws.dss.markt.ec.europa.eu/}ValidationService`. L’endpoint è configurato (in `CxfSoapValidationEndpointPublisher`) per pubblicare il servizio con questo nome e namespace, così il WSDL risulta compatibile con tali client. Le operazioni e i messaggi restano quelli della libreria DSS (`http://validation.dss.esig.europa.eu/`).

### Errore "Could not write attachments" (client PAL / JaxWsClientProxy)

Se il **client** (es. PAL backoffice, `DSSWSClient.validateDocument`) riceve `javax.xml.ws.WebServiceException: Could not write attachments`, l'errore è lato client durante l'invio della request (allegato MTOM) o durante la gestione della response (contenuto estratto + certificati).

**Sul client (PAL):** verificare che MTOM sia abilitato sul proxy JAX-WS e che il `DataHandler` usato per il `WSDocument` fornisca uno stream leggibile (non già consumato). Se l'errore avviene in ricezione, provare a chiamare con **`giveBackContent=false`** così la response non include il documento estratto.

**Sul server (DSS):** è stata introdotta una `DataSource` che non lancia da `getOutputStream()` per evitare eccezioni durante la serializzazione MTOM.

## 2. Creare il progetto in SoapUI

1. **File → New SOAP Project** (o "New SOAP Project" dalla schermata iniziale).
2. **Project Name**: es. `DSS-Validazione`.
3. **Initial WSDL**: incolla l’URL del WSDL, ad esempio  
   `http://localhost:8090/vbg-legacy-dss-webapp/wservice/validation?wsdl`
4. Lascia **Create Requests** selezionato se vuoi che SoapUI crei una richiesta per ogni operazione.
5. Clicca **OK**.

SoapUI scaricherà il WSDL e creerà le operazioni (es. `validateSignature`, `getOriginalDocuments`).

## 3. Operazione da usare: validateSignature

- Apri la richiesta **validateSignature** sotto il binding del servizio.
- Il corpo della richiesta SOAP invia un `DataToValidateDTO` che contiene:
  - **signedDocument**: documento firmato (tipo `RemoteDocument`: contenuto in Base64 + nome file).
  - **originalDocument** (opzionale): documento originale per firme detached.
  - **policy** (opzionale): policy di validazione personalizzata.

## 4. Esempio di richiesta (solo documento firmato)

Il servizio DSS usa **namespace** `http://validation.dss.esig.europa.eu/` e parametro **`dataToValidateDTO`**. Sostituisci il body con:

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:validateSignature xmlns:ns2="http://validation.dss.esig.europa.eu/">
         <dataToValidateDTO>
            <signedDocument>
               <bytes>BASE64_DEL_CONTENUTO_BINARIO_DEL_PDF_FIRMATO</bytes>
               <name>documento_firmato.pdf</name>
            </signedDocument>
            <!-- originalDocument e policy opzionali, possono essere omessi -->
         </dataToValidateDTO>
      </ns2:validateSignature>
   </soap:Body>
</soap:Envelope>
```

Per ottenere il Base64 del PDF:

- **Windows (PowerShell)**:
  ```powershell
  [Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\path\to\documento_firmato.pdf"))
  ```
- **Linux/macOS**:
  ```bash
  base64 -w0 documento_firmato.pdf
  ```
- In SoapUI puoi anche usare uno script o un attachment MTOM se il servizio lo espone con MTOM.

Incolla la stringa Base64 al posto di `BASE64_DEL_CONTENUTO_BINARIO_DEL_PDF_FIRMATO` e adatta il nome file in `<name>`.

## 5. Invio e risposta

- Clicca **Submit** (icona play verde) o **Send**.
- La risposta conterrà un `WSReportsDTO` con, tra gli altri:
  - **simpleReport**: esito per ogni firma (VALID / INDETERMINATE / FAILED).
  - **detailedReport**: dettagli dei building block.
  - **diagnosticData**: dati diagnostici (certificati, timestamp, ecc.).

## 6. Se il WSDL non è raggiungibile

- Verifica che l’applicazione sia avviata e che il context root sia corretto.
- Verifica che non ci siano proxy/firewall che bloccano `localhost`.
- Controlla i log del server (CXF/Tomcat) per errori all’avvio degli endpoint.
- Se usi HTTPS o una porta diversa, adatta host e porta nell’URL del WSDL.

## 7. Endpoint alternativo (stesso contratto)

Se in fase di sviluppo l’app è su un altro path (es. `/vbg-legacy-dss-webapp-2.0.0-dss6`), usa quello come context-root negli URL sopra.

---

## 8. Errore "Failed to load url ... 0" in SoapUI

L’errore **Failed to load url; ... 0** di solito indica che SoapUI non riceve risposta dal server (connessione rifiutata o URL errato). Controlla in ordine:

### 8.1 Applicazione in esecuzione

- Tomcat (o il server) deve essere avviato e il WAR deployato.
- Porta **8090**: verifica che sia quella configurata (es. in Tomcat `server.xml` o variabile d’ambiente).

### 8.2 Context path corretto

Il context path è il nome del WAR **senza** `.war`. Per questo progetto il build può produrre:

- **`vbg-legacy-dss-webapp.war`** → context path: **`/vbg-legacy-dss-webapp`**
- Se non usi `<warName>` in pom, il WAR può essere `vbg-legacy-dss-webapp-2.0.0-dss6.war` → context path: **`/vbg-legacy-dss-webapp-2.0.0-dss6`**

**Prova nel browser (stessa macchina del server):**

1. `http://localhost:8090/`  
   Verifica che Tomcat risponda e, se c’è la lista delle applicazioni, controlla il nome dell’app (es. `vbg-legacy-dss-webapp`).
2. `http://localhost:8090/vbg-legacy-dss-webapp/`  
   Dovresti vedere la home dell’applicazione (o una 404 se non c’è index).
3. `http://localhost:8090/vbg-legacy-dss-webapp/wservice/validation?wsdl`  
   Se il context è corretto e l’endpoint è attivo, qui compare il WSDL (XML). Se ottieni 404, prova con l’altro context:  
   `http://localhost:8090/vbg-legacy-dss-webapp-2.0.0-dss6/wservice/validation?wsdl`

### 8.3 Usare l’URL che funziona nel browser

- Se nel browser il WSDL si apre con un URL (es. con `...-dss6/...`), **usa esattamente quell’URL** come “Initial WSDL” in SoapUI.

### 8.4 Log del server

- Controlla i log di Tomcat all’avvio: il contesto Spring deve caricarsi senza errori (es. LOTL, bean `cxfSoapValidationEndpointPublisher`). Eventuali eccezioni in fase di init possono impedire la pubblicazione dell’endpoint e quindi il WSDL non sarà servito.

---

## 9. Errore "Unmarshalling Error" (soap:Client)

Se la risposta è un **SOAP Fault** con `faultcode` `soap:Client` e `faultstring` **Unmarshalling Error**, il body della richiesta non corrisponde allo schema atteso dal servizio DSS.

**Causa tipica:** namespace o nome dell'elemento parametro sbagliati.

**Correzioni:**

1. **Namespace** dell'operazione deve essere:  
   `http://validation.dss.esig.europa.eu/`  
   (non `http://soap.validation.ws.dss.esig.europa.eu/`).

2. **Nome del parametro** deve essere: **`dataToValidateDTO`**  
   (non `dataToValidate`). L'interfaccia JAX-WS DSS usa `@WebParam(name = "dataToValidateDTO")`.

Esempio di body corretto:

```xml
<ns2:validateSignature xmlns:ns2="http://validation.dss.esig.europa.eu/">
   <dataToValidateDTO>
      <signedDocument>
         <bytes>...</bytes>
         <name>documento_firmato.pdf</name>
      </signedDocument>
   </dataToValidateDTO>
</ns2:validateSignature>
```

In SoapUI, se la richiesta è stata generata dal WSDL, verifica che l'elemento figlio diretto di `validateSignature` sia proprio `dataToValidateDTO` e che il namespace di `validateSignature` sia `http://validation.dss.esig.europa.eu/`.
