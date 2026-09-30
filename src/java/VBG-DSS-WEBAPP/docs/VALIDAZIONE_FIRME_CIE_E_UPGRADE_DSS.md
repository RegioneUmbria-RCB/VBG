# Validazione firme (CIE) e upgrade DSS

## Situazione attuale

- La componente di validazione firme è basata sul progetto europeo **DSS (Digital Signature Services)** in versione **2.0-SNAPSHOT** (circa 2011).
- Parte del codice DSS è **copiata nel repository** (`src/eu/europa/ec/markt/dss/`); altre parti arrivano da JAR Maven (`dss-document`, `dss-service`, `dss-ws`) con parent `app-dss` 2.0-SNAPSHOT.
- Stack PDF: **Lowagie (iText)** e classi come `PdfPKCS7`, `ITextPDFSignatureService`, `SignatureValidationCallback` (probabilmente nel JAR `dss-document`).
- Sono stati segnalati **ticket per file firmati con CIE (Carta d’Identità Elettronica)** che non vengono validati correttamente.

## Perché le firme CIE possono fallire

1. **SubFilter**: il codice in `PDFDocumentValidator.verifyLevelBES()` considera “raccomandati” solo `ETSI.CAdES.detached` e `ETSI.RFC3161`. Le firme CIE (e molte PAdES “Adobe-style”) usano spesso **`adbe.pkcs7.detached`** o **`adbe.pkcs7.sha1`**. Oggi viene solo loggato un warning; in altri punti la logica potrebbe escludere questi SubFilter.
2. **Struttura del dictionary della firma**: è già presente un fix (BOCCI 2022-08-12) che usa `Contents` se `Type` è null. In alcuni PDF (anche CIE) **`/Contents`** può essere uno **stream** invece che una stringa; usare solo `.getBytes()` sul `PdfObject` può fallire se l’oggetto è un `PRStream`.
3. **Algoritmi e certificati**: CIE 3.0 e certificati italiani potrebbero usare algoritmi o catene non pienamente gestiti dalla vecchia DSS 2.0 (es. OCSP/CRL, formati di certificato).
4. **Librerie obsolete**: Bouncy Castle, Lowagie/iText e DSS 2.0 sono molto datate; bug e incompatibilità con PDF generati da tool moderni (incluso CIE) sono probabili.

## Opzioni di soluzione

### Opzione A (consigliata): upgrade alla DSS europea aggiornata

- **Progetto attuale**: [esig/dss](https://github.com/esig/dss) (LGPL-2.1).
- **Versione**: 6.x (es. 6.2/6.3); in alternativa 5.13 se si vuole restare su `javax.*`.
- **Vantaggi**:
  - Validazione PAdES/CAdES/XAdES/ASiC aggiornata, con supporto a formati e algoritmi moderni.
  - Supporto migliore a firme CIE e eIDAS.
  - Meno codice custom da mantenere; si usa la libreria come dipendenza Maven.
- **Impatto**:
  - Package diversi: `eu.europa.ec.markt.dss` → `eu.europa.esig.dss`.
  - API diverse: `SignedDocumentValidator.fromDocument()` → `SignedDocumentValidator.fromDocument()` (nuova API in `eu.europa.esig.dss`), report in formato `DetailedReport`/`SimpleReport` invece di `ValidationReport` attuale.
  - Serve un **adapter** che:
    - riceve il file (come oggi),
    - chiama la nuova DSS per la validazione,
    - mappa il risultato nel `WSValidationReport` (o in un DTO equivalente) così che **ValidationServlet** e **validazione.jsp** possano restare invariati, oppure
  - in alternativa si adatta la UI al nuovo formato di report della DSS 6.x.
- **Dipendenze Maven**: aggiungere (es.) `dss-pades`, `dss-validation`, `dss-model`, `dss-service` dal gruppo `eu.europa.esig.dss` (Maven Central) e rimuovere progressivamente il codice copiato e le dipendenze da `app-dss` 2.0.

### Opzione B: fix mirati nel codice attuale (short-term)

- **SubFilter**: in `PDFDocumentValidator.verifyLevelBES()` accettare anche `adbe.pkcs7.detached` e `adbe.pkcs7.sha1` (e eventualmente altri SubFilter comuni per PAdES) in modo che le firme CIE/Adobe non siano considerate “invalid or missing SubFilter”.
- **Contents come stream**: in `PAdESSignature` (e ovunque si legga `/Contents` del signature dictionary) estrarre i byte in modo sicuro: se l’oggetto è un `PRStream` usare `PdfReader.getStreamBytes()`, altrimenti il comportamento attuale (es. `getBytes()` per stringhe).
- **Limitazioni**: non risolve problemi dovuti a bug nelle JAR `dss-document`/`dss-service` (es. `ITextPDFSignatureService` che non invoca il callback per certi tipi di firma). Se il fallimento è in quelle librerie, l’unica strada robusta è l’upgrade (Opzione A).

## Interventi applicati in questo repository

1. **SubFilter estesi** in `PDFDocumentValidator`: accettati anche `adbe.pkcs7.detached` e `adbe.pkcs7.sha1` per ridurre falsi negativi su firme CIE/Adobe.
2. **Estrazione sicura di `/Contents`** in `PAdESSignature`: gestione esplicita del caso in cui `Contents` sia uno stream (`PRStream`), usando `PdfReader.getStreamBytes()` dove necessario.

Questi interventi possono risolvere parte dei ticket CIE; se i problemi persistono, si raccomanda di pianificare l’**upgrade alla DSS 6.x** (Opzione A) e l’introduzione di un adapter verso il vostro `WSValidationReport`/servlet.

## Dipendenze Maven per upgrade (Opzione A)

Per migrare a DSS 6.x da Maven Central (gruppo `eu.europa.esig.dss`):

- `eu.europa.esig.dss:dss-model`
- `eu.europa.esig.dss:dss-pades` (validazione PAdES)
- `eu.europa.esig.dss:dss-validation`
- `eu.europa.esig.dss:dss-service` (se si usa il servizio di validazione)

Per restare su `javax.*` usare DSS 5.13.x invece di 6.x.

## Riferimenti

- [DSS GitHub - esig/dss](https://github.com/esig/dss)
- [DSS Webapp demo (CEF)](https://ec.europa.eu/digital-building-blocks/DSS/webapp-demo/home)
- [DSS 6.3 API (PDFDocumentValidator)](https://ec.europa.eu/digital-building-blocks/DSS/webapp-demo/apidocs/eu/europa/esig/dss/pades/validation/PDFDocumentValidator.html)
- [CIE - Firma digitale](https://www.cartaidentita.interno.gov.it/argomenti/firma-digitale/)
- [CIE 3.0 - FEA e verifica](https://docs.italia.it/italia/cie/cie-middleware-windows-docs/it/master/funzioni-FEA-verifica.html)
