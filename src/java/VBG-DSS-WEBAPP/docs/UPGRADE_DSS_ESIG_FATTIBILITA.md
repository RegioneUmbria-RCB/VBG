# Fattibilità upgrade alla DSS europea (esig/dss)

## Risposta breve

**Sì, è fattibile** aggiornare l’applicativo alla versione più recente del progetto europeo [esig/dss](https://github.com/esig/dss). Il lavoro è consistente ma ben delimitabile: dipendenze, livello di validazione (con eventuale adapter), configurazione Spring e (opzionale) adeguamento della UI.

---

## Contesto attuale vs DSS 6.x

| Aspetto | Vostro progetto (DSS 2.0) | esig/dss 6.x |
|--------|---------------------------|--------------|
| **Package** | `eu.europa.ec.markt.dss` | `eu.europa.esig.dss` |
| **Documento** | `Document` / `WSDocument` | `DSSDocument` / `InMemoryDocument` |
| **Validator** | `SignedDocumentValidator.fromDocument(document)` | `SignedDocumentValidator.fromDocument(document)` (stesso concetto, API diversa) |
| **Report** | `ValidationReport` → `WSValidationReport` (lista `SignatureInformation`, livelli BES/T/XL/…) | `Reports` con `SimpleReport`, `DetailedReport`, `DiagnosticData` (JAXB/XML) |
| **Config** | Spring 3 + `applicationContext.xml` + `dss_applicationContext_cxf.xml` (da JAR) | Configurazione basata su `CertificateVerifier`, data loader, trusted list (in dss-demonstrations: Spring Boot) |
| **Dipendenze** | Parent `app-dss` 2.0-SNAPSHOT (non su Maven Central), `dss-document`, `dss-service`, `dss-ws` | BOM/moduli su Maven Central: `dss-model`, `dss-pades`, `dss-cades`, `dss-xades`, `dss-validation`, `dss-service`, ecc. |

L’idea è **usare la DSS 6.x come libreria** e far sì che il flusso “upload file → validazione → report” resti disponibile, possibilmente senza stravolgere subito la JSP di verifica firma.

---

## Due strade possibili

### Opzione 1: Adapter (mantenere la UI attuale)

- **Obiettivo**: ValidationServlet e `validazione.jsp` continuano a usare `WSValidationReport` e le classi `WS*` (WSSignatureInformation, WSSignatureLevelBES, ecc.).
- **Implementazione**:
  1. Aggiungere le dipendenze Maven della DSS 6.x (vedi sotto).
  2. Introdurre un **servizio di validazione** che:
     - riceve il documento come oggi (es. `WSDocument` o byte[] + nome file);
     - converte in `DSSDocument` (es. `new InMemoryDocument(bytes, nomeFile)`);
     - crea il validator con `SignedDocumentValidator.fromDocument(dssDocument)` (API `eu.europa.esig.dss`);
     - imposta il `CertificateVerifier` (configurato con CRL/OCSP/TSL come in DSS 6.x);
     - chiama `validateDocument()` e ottiene `Reports`;
     - mappa `Reports` (SimpleReport + eventuale DetailedReport/DiagnosticData) → `WSValidationReport` e relativi bean (`WSSignatureInformation`, livelli, certificati, timestamp, ecc.).
  3. Il bean Spring che oggi espone la validazione (es. `dss.validation.validationservice`) può essere sostituito da questa nuova implementazione che internamente usa solo la DSS 6.x.
  4. **Configurazione Spring**: sostituire i bean legati alla DSS 2.0 (CertificateVerifier, fonti CRL/OCSP/TSL) con quelli della DSS 6.x; si può prendere a riferimento [dss-demonstrations](https://github.com/esig/dss-demonstrations) (es. `dss-demo-webapp`) e adattare al vostro `applicationContext.xml` senza obbligo di passare a Spring Boot.

- **Pro**: la pagina “Verifica Firma Digitale” e il flusso esistente restano invariati.
- **Contro**: l’adapter Report → WS* è impegnativo (molti campi e livelli da mappare) e andrà mantenuto in caso di cambi della DSS.

### Opzione 2: Adottare il report della DSS 6.x (cambiare la UI)

- **Obiettivo**: restituire alla UI il report nativo della DSS 6.x (es. XML SimpleReport/DetailedReport o un HTML generato da quello).
- **Implementazione**:
  1. Stesse dipendenze e stesso uso di `SignedDocumentValidator` + `CertificateVerifier` della DSS 6.x.
  2. Il servizio di validazione, invece di costruire `WSValidationReport`, restituisce direttamente `Reports` (o `getXmlSimpleReport()` / `getXmlDetailedReport()`).
  3. **ValidationServlet** e **validazione.jsp** vengono modificati per:
     - ricevere il report in formato XML/JSON o un DTO semplificato;
     - visualizzare la nuova struttura (es. una tabella per firma, livello, certificato, esito) in modo analogo a oggi ma basata su SimpleReport/DetailedReport.

- **Pro**: niente adapter WS*; allineamento alla demo europea e ai formati standard.
- **Contro**: refactoring della JSP e del flusso di presentazione; eventuale migrazione da JSP a altro (es. REST + frontend) in un secondo tempo.

---

## Passi tecnici comuni (indipendenti dall’opzione)

### 1. Build e dipendenze Maven

- Il `pom.xml` attuale è sotto `WebContent/META-INF/maven/...` e usa il parent `app-dss` 2.0-SNAPSHOT, che **non è su Maven Central**. Per l’upgrade conviene:
  - **O** spostare il `pom` nella root del progetto (layout Maven standard) e rimuovere il parent `app-dss`.
  - **O** creare un `pom.xml` in root che definisce tutte le dipendenze in modo esplicito (Spring, Servlet API, ecc.) e che usa il modulo attuale come sottoprogetto o come unico modulo senza parent europeo.

- **Dipendenze DSS 6.x** (Maven Central, gruppo `eu.europa.esig.dss`), da aggiungere con una versione unica (es. `6.2` o `6.3`):
  - `dss-model` (modello dati, `DSSDocument`, ecc.)
  - `dss-pades`, `dss-cades`, `dss-xades` (secondo i formati che vi servono)
  - `dss-validation` (SignedDocumentValidator, Reports)
  - `dss-service` (servizi di validazione e configurazione)
  - Eventualmente `dss-policy` se usate policy ETSI.

- **Rimuovere** (o non più usare):
  - Parent `eu.europa.ec.markt.dss:app-dss:2.0-SNAPSHOT`
  - Dipendenze `dss-document`, `dss-service`, `dss-ws`, `applet-service`, `applet-package`, `tlmanager-package` del vecchio gruppo.

### 2. Conversione documento

- **Input**: oggi il servlet costruisce un `WSDocument` (o equivalente) dal file caricato.
- **Per la DSS 6.x**: creare un `DSSDocument` a partire dai byte e dal nome file, ad es.:
  - `DSSDocument dssDoc = new InMemoryDocument(inputStream, fileName);`
- Il servizio di validazione può accettare ancora `WSDocument` (o `byte[]` + nome) e fare al suo interno questa conversione, così il chiamante (ValidationServlet) non cambia.

### 3. Configurazione Spring (CRL, OCSP, TSL, CertificateVerifier)

- La DSS 6.x usa un **CertificateVerifier** con:
  - data loader (HTTP) per CRL/OCSP/AIA;
  - trusted list source (es. LOTL UE);
  - eventuale cache CRL/OCSP (anche JDBC, come oggi).
- Potete:
  - **Riusare** il vostro `CommonsHttpDataLoader` (o adattarlo all’interfaccia prevista dalla DSS 6.x, se diversa).
  - Sostituire i bean `cacheCrlSource`, `crlSource`, `ocspSource`, `TrustedListSource` con i corrispettivi della DSS 6.x (nomi e package diversi).
  - Consultare la documentazione e i file di config in [dss-demonstrations](https://github.com/esig/dss-demonstrations) (es. `dss-demo-webapp`) per un esempio pronto.

### 4. Codice da tenere / rimuovere

- **Da tenere** (logica vostra e contratto attuale):
  - `it.gruppoinit.dss.*` (ValidationServlet, ProcessedItem, CommonsHttpDataLoader, ecc.).
  - Se scegliete l’**Opzione 1**: le classi `WS*` (WSValidationReport, WSSignatureInformation, WSSignatureLevelBES, …) e l’adapter che le popola a partire da `Reports`.
  - Contratto dell’interfaccia di validazione (es. `validateDocument(WSDocument, ...)` → `WSValidationReport`) così come usato dal servlet e dalla JSP.

- **Da rimuovere** (o mettere in un modulo “legacy” disattivato) dopo la migrazione:
  - Tutto il package `eu.europa.ec.markt.dss` in-tree (validator, report, ws, signature, validation, ecc.), una volta che il nuovo flusso usa solo la DSS 6.x.
  - Eventuale codice in `com.lowagie` se presente nel progetto (nella DSS 6.x la gestione PDF è interna alla libreria).

### 5. CXF / Web service

- Oggi è importato `dss_applicationContext_cxf.xml` (da JAR). Se esponete la validazione anche come Web service SOAP, con l’upgrade dovrete:
  - definire voi il contratto (WSDL) e i bean Spring per CXF, oppure
  - passare a un’API REST che restituisce il report (es. XML/JSON della DSS 6.x).
- Se la validazione è usata **solo** dalla web app (upload + validazione.jsp), potete ignorare CXF e tenere solo il servizio chiamato dal ValidationServlet.

---

## Stima degli sforzi (indicativa)

| Fase | Opzione 1 (Adapter) | Opzione 2 (Nuovo report) |
|------|----------------------|---------------------------|
| Pom, dipendenze, build | 1–2 gg | 1–2 gg |
| Config Spring (CertificateVerifier, TSL, CRL, OCSP) | 1–2 gg | 1–2 gg |
| Servizio validazione (DSS 6.x + conversione documento) | 1–2 gg | 1 gg |
| Adapter Reports → WSValidationReport | 3–5 gg | — |
| Adeguamento validazione.jsp / presentazione | — | 2–4 gg |
| Test (PAdES, CAdES, CIE, p7m, PDF, ASiC) | 2–3 gg | 2–3 gg |

---

## Conclusione

Aggiornare l’applicativo all’ultima versione della DSS europea (esig/dss) **è fattibile** e consente di:

- validare correttamente le firme CIE e gli altri formati supportati dalla DSS 6.x;
- allinearsi agli standard e alla demo ufficiale;
- ridurre il codice custom legato alla vecchia DSS 2.0.

La scelta tra **Opzione 1** (adapter per mantenere la UI attuale) e **Opzione 2** (nuovo report e UI aggiornata) dipende da quanto volete preservare l’attuale pagina di verifica firma rispetto al costo di sviluppo e manutenzione dell’adapter.

Se indicate quale opzione preferite (adapter vs nuovo report) e dove si trova il `pom.xml` con cui buildate (root vs WebContent/META-INF/maven), si può dettagliare il piano passo passo (es. primo sprint: solo dipendenze + servizio di validazione DSS 6.x + conversione WSDocument → DSSDocument, senza ancora toccare la JSP).

---

## Integrazione effettuata (DSS 5.x – Opzione 2)

È stata realizzata l’integrazione con **DSS 5.13.1** (eu.europa.ec.joinup.sd-dss) su Maven Central:

- **pom.xml** (root): dipendenze DSS 5.x (dss-model, dss-pades, dss-cades, dss-xades, dss-service), Spring 5.3, javax.servlet; esclusione dalla compilazione del codice legacy (`eu.europa.ec.markt.dss`, `com.lowagie`, `it.gruppoinit.dss.http`).
- **applicationContext.xml**: `CommonCertificateVerifier` e `CommonsDataLoader` (DSS 5.x), bean `dss.validation.validationservice` → `Dss5SignatureValidationService`.
- **web.xml**: lasciato solo il servlet di verifica firma; rimossi i servlet CXF e handler AIA/CRL/OCSP/TSP (legacy).
- **Nuovo pacchetto** `it.gruppoinit.dss.validation`: `ValidationResultDTO`, `SignatureValidationService`, `Dss5SignatureValidationService` (validazione con `SignedDocumentValidator.fromDocument()`, report XML Simple/Detailed/Diagnostic).
- **ValidationServlet** e **ProcessedItem**: uso di `SignatureValidationService` e `ValidationResultDTO`; salvataggio del contenuto estratto (p7m/p7d) come prima.
- **validazione.jsp**: visualizzazione del Simple Report (e Detailed Report in sezione espandibile) in XML, link al file estratto se presente.

**Aggiornamento a DSS 6.x (Jakarta EE)**  
Il progetto è stato portato a **DSS 6.3** con stack Jakarta:
- **Java 11**, **Spring 6.1**, **Jakarta Servlet 5** (Tomcat 10+)
- **javax → jakarta**: servlet, JSP/JSTL, listener; `web-app` schema Jakarta EE 5
- **commons-fileupload2-jakarta-servlet5** al posto di commons-fileupload 1.x
- **CertificateVerifier** e **AdvancedSignature** in DSS 6: `eu.europa.esig.dss.spi.validation`, `eu.europa.esig.dss.spi.signature`
- Dipendenza **dss-policy-jaxb** aggiunta (richiesta da DSS 6.x)

Build: `mvn clean package -DskipTests` dalla root. WAR: `target/vbg-legacy-dss-webapp-2.0.0-dss6.war`.  
Runtime: **Tomcat 10+** (o altro server Jakarta EE 9+).
