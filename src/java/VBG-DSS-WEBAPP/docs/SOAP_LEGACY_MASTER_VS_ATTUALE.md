# Confronto contratto SOAP: master (legacy) vs attuale (DSS 6)

Analisi del formato **request/response** del servizio di validazione su **master** per allineare l’endpoint attuale al legacy.

---

## 1. Contratto su master (legacy)

**Interfaccia:** `eu.europa.ec.markt.dss.ws.ValidationService`  
**Implementazione:** `eu.europa.ec.markt.dss.ws.impl.ValidationServiceImpl`

### Request (operazione `validateDocument`)

| Parametro           | Tipo        | Nome nel WS     |
|---------------------|------------|------------------|
| document            | WSDocument | document         |
| originalDocument    | WSDocument | originalDocument |
| giveBackContent     | Boolean    | giveBackContent  |

- **WSDocument**: `binary` (DataHandler / MTOM), `name`, `mimeType` (non un unico DTO con signedDocument/originalDocument).

### Response

- **Tipo:** `WSValidationReport` (non `WSReportsDTO`).
- **Contenuto:**
  - `timeInformation` → `WSTimeInformation`
  - `signatureInformationList` → `List<WSSignatureInformation>`
  - `detachedTsVerificationResult` → `List<WSTimestampVerificationResult>`
  - `content` → `WSDocument`

Struttura “a oggetti” (BES/T/XL, livelli firma, certificati, timestamp), non XML SimpleReport/DetailedReport/DiagnosticData.

---

## 2. Contratto attuale (DSS 6 + adapter)

### Request

- **Operazione:** `validateDocument` (nome allineato al legacy).
- **Parametro unico:** `DataToValidateDTO` (DSS 6) con:
  - `signedDocument` (RemoteDocument: bytes + name)
  - `originalDocument` (opzionale)
  - `policy`, ecc.

### Response

- **Tipo:** `WSReportsDTO` (DSS 6).
- **Contenuto:** XML in tre blocchi:
  - `DiagnosticData` (namespace `http://dss.esig.europa.eu/validation/diagnostic`)
  - `SimpleReport` (namespace `http://dss.esig.europa.eu/validation/simple-report`)
  - `DetailedReport` (namespace `http://dss.esig.europa.eu/validation/detailed-report`)

---

## 3. Differenze principali

| Aspetto        | Master (legacy)                    | Attuale (DSS 6)                          |
|----------------|-------------------------------------|------------------------------------------|
| **Request**    | 3 parametri (WSDocument, WSDocument, Boolean) | 1 parametro (DataToValidateDTO)          |
| **Response**   | WSValidationReport (oggetti BES/T/XL, WSSignatureInformation, ecc.) | WSReportsDTO (DiagnosticData, SimpleReport, DetailedReport in XML) |
| **WSDL**       | Tipi WSDocument, WSValidationReport, ecc.     | Tipi DataToValidateDTO, WSReportsDTO (XSD DSS 6) |

Quindi **sì, serve una mappatura** per allinearsi al legacy su master:

1. **Request:** accettare la signature legacy  
   `validateDocument(WSDocument document, WSDocument originalDocument, Boolean giveBackContent)`  
   e costruire da essa un `DataToValidateDTO` per chiamare la DSS 6.

2. **Response:** dopo la validazione DSS 6, convertire  
   `WSReportsDTO` (o `Reports`) → `WSValidationReport`  
   (timeInformation, signatureInformationList, detachedTsVerificationResult, content) con le stesse strutture usate su master.

---

## 4. Cosa serve per l’allineamento

1. **Tipi legacy nel progetto**
   - Copiare da master (e da JAR se servono) le classi WS usate nel contratto:
     - `WSDocument`, `WSValidationReport`, `WSTimeInformation`, `WSSignatureInformation`, `WSSignatureLevelBES`, `WSSignatureVerification`, `WSTimestampVerificationResult`, `WSCertificateVerification`, `WSQCStatementInformation`, `WSRevocationVerificationResult`, eventuali altre referenziate da queste.
   - Adattare package/import se si restano in `eu.europa.ec.markt.dss.ws` (o spostare in un package “legacy” del progetto).

2. **Endpoint che espone il contratto legacy**
   - Un servizio (es. `LegacyValidationServiceAdapter`) che:
     - espone **solo** la signature legacy:  
       `WSValidationReport validateDocument(WSDocument document, WSDocument originalDocument, Boolean giveBackContent)`;
     - non espone più la signature attuale con `DataToValidateDTO`/`WSReportsDTO` (o la si può tenere su un altro endpoint/operazione se serve).

3. **Conversione request**
   - `document` / `originalDocument` (WSDocument) → costruzione di `DataToValidateDTO` (signedDocument/originalDocument come RemoteDocument/DTO DSS 6).
   - `giveBackContent` → usato per decidere se includere il contenuto estratto nel report (equivalente nel mapping a `WSValidationReport.content`).

4. **Conversione response (mappatura report)**
   - Da `Reports` o `WSReportsDTO` (DSS 6) costruire un `WSValidationReport`:
     - `timeInformation` da dati di validazione/DiagnosticData.
     - `signatureInformationList` da SimpleReport/DetailedReport/DiagnosticData (firme, livelli BES/T/XL, certificati, ecc.).
     - `detachedTsVerificationResult` da eventuali timestamp in Reports/DiagnosticData.
     - `content` da contenuto estratto (se `giveBackContent == true`), eventualmente wrappato in `WSDocument`.

La parte più delicata è il mapping **Reports/WSReportsDTO → WSValidationReport** (molti campi e strutture diverse tra DSS 6 e il modello report legacy).

---

## 5. Passi operativi suggeriti

1. **Recuperare le classi legacy da master**  
   Copiare in questo branch le classi WS (e le dipendenze di tipo report/validation che servono) da `src/eu/europa/ec/markt/dss/ws/` (e da `src/eu/europa/ec/markt/dss/validation/report/` se usate da WS*), verificando eventuali classi mancanti nel JAR `dss-ws-2.0-SNAPSHOT.jar` (es. `WSSignatureInformation`, `WSTimeInformation`, `WSTimestampVerificationResult`).

2. **Implementare l’adapter di request**  
   `validateDocument(WSDocument, WSDocument, Boolean)` → build `DataToValidateDTO` → chiamata a `SoapDocumentValidationService.validateSignature(DataToValidateDTO)` (o al servizio di validazione DSS 6 che usi oggi).

3. **Implementare il mapper di response**  
   `WSReportsDTO` / `Reports` → `WSValidationReport` (popolando timeInformation, signatureInformationList, detachedTsVerificationResult, content).

4. **Pubblicare l’endpoint**  
   Esporre l’adapter con la signature legacy (stesso service/port/namespace del master) e rimuovere o duplicare l’attuale endpoint basato su `DataToValidateDTO`/`WSReportsDTO` a seconda che i client usino solo il contratto legacy o entrambi.

Se vuoi, il passo successivo può essere: elenco preciso dei file da copiare da master e una proposta di package per le classi legacy nel progetto attuale.
