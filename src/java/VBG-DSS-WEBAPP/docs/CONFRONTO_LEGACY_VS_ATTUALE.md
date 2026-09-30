# Confronto legacy vs applicativo attuale (DSS 6.x)

## Domande a cui risponde questo documento

1. L'applicativo ora può gestire tutti i tipi di firma (CAdES, PAdES, XAdES, ASiC, …) senza problemi?
2. Prima c'erano molti file Java e tipi (cades, pades, ecc.): ora sono meno. L'applicativo ha la stessa validità di prima?
3. L'idea era solo svecchiare e usare la DSS aggiornata: è stato fatto così?

---

## Prima (legacy – DSS 2.0 / app-dss)

### Struttura tipica

- **Package**: `eu.europa.ec.markt.dss` (DSS 2.0), codice europeo “vecchio” incluso o referenziato nel progetto.
- **Validator**: stesso concetto `SignedDocumentValidator.fromDocument(document)` ma API e implementazioni della DSS 2.0.
- **Formati**: gestiti con **codice distinto** per tipo:
  - PAdES / PDF → validator e logica dedicata (es. `PDFDocumentValidator`, `PAdESSignature`, codice in `eu.europa.ec.markt.dss.validation.pades`).
  - CAdES / p7m, p7d → validator e logica CMS.
  - XAdES → validator XML.
  - Possibili handler/servlet separati (CXF, AIA, CRL, OCSP, TSP) e più file di configurazione.
- **Report**: `ValidationReport` → `WSValidationReport`, `WSSignatureInformation`, livelli BES/T/XL, ecc. (modello “vecchio”).
- **Stack**: Spring 3, `javax.servlet`, parent POM `app-dss` 2.0 non su Maven Central, JAR in `WEB-INF/lib`.

Risultato: **molti file Java** (nel progetto e nelle lib DSS 2.0), **più percorsi di validazione** (uno per formato o per uso), **stessa capacità funzionale** (validare PAdES, CAdES, XAdES, ecc.) ma con codice e dipendenze datati.

---

## Ora (DSS 6.x – svecchiamento)

### Struttura attuale

- **Un solo punto di ingresso** per la validazione “locale” (upload web):
  - `SignatureValidationService` (interfaccia) → **`Dss5SignatureValidationService`** (un’unica implementazione).
  - Nessuno switch sul tipo di file nel nostro codice: si passa sempre `byte[]` + `fileName` al servizio.
- **Scelta del tipo di firma delegata alla DSS 6.x**:
  - `SignedDocumentValidator.fromDocument(document)` (DSS 6, `eu.europa.esig.dss.validation`).
  - La libreria **rileva il formato dal contenuto** (magic bytes / struttura) e istanzia internamente il validator corretto:
    - PDF → PAdES (con `dss-pades`, `dss-pades-pdfbox`),
    - CMS/p7m/p7d → CAdES (con `dss-cades`, `dss-cms-object`),
    - XML → XAdES (con `dss-xades`),
    - ASiC → container (supporto DSS).
- **Stesso flusso per SOAP**: l’endpoint `/wservice/validation` usa il servizio remoto DSS (`SoapDocumentValidationServiceImpl`), che internamente usa la stessa logica di validazione (stessi validator, stessi formati).
- **Report**: report nativi DSS 6.x (SimpleReport, DetailedReport, DiagnosticData in XML); la UI e il SOAP restituiscono questi dati (o DTO derivati).
- **Stack**: Java 17, Spring 6, Jakarta EE (Tomcat 10+), dipendenze DSS 6.3 da Maven Central (`eu.europa.ec.joinup.sd-dss`).

Risultato: **meno file “nostri”** (un servizio di validazione, nessun validator per tipo nel nostro codice), **stessi formati supportati** (PAdES, CAdES, XAdES, ASiC) perché è la DSS 6.x a gestirli al posto nostro.

---

## Stessa validità di prima?

**Sì**, con queste precisazioni:

| Aspetto | Legacy | Attuale |
|--------|--------|--------|
| **PAdES (PDF)** | Validator dedicato (es. `PDFDocumentValidator`) | `SignedDocumentValidator.fromDocument()` → DSS sceglie il validator PDF/PAdES. Stessa validazione (firme, catena, revoche, LOTL). |
| **CAdES (p7m, p7d)** | Validator CMS dedicato | Stesso flusso: DSS riconosce il CMS e usa il validator CAdES. Estrazione contenuto (p7m/p7d) ancora supportata in `Dss5SignatureValidationService.getExtractedDocument()`. |
| **XAdES** | Validator XML dedicato | DSS riconosce XML firmato e usa il validator XAdES. |
| **ASiC** | Se supportato in legacy | Supportato dalla DSS 6.x tramite gli stessi validator. |
| **Report** | WSValidationReport, livelli BES/T/XL, ecc. | SimpleReport / DetailedReport / DiagnosticData (standard DSS 6.x). Informazioni equivalenti (esito, certificati, qualificazione, errori/avvisi). |
| **SOAP** | Contratto legacy (DataToValidateDTO → WSReportsDTO) | Stesso contratto: endpoint `/wservice/validation`, stesso DTO e report (WSReportsDTO). |

Quindi: **l’applicativo ha la stessa validità** in termini di tipi di firma gestiti (PAdES, CAdES, XAdES, ASiC) e di qualità della validazione (certificati, revoche, Trusted List). La differenza è che **non abbiamo più noi** i file “cades”, “pades”, ecc.: è la **DSS 6.x** a contenere quei validator e a scegliere quale usare in base al documento.

---

## Gestione di “tutti i tipi di firma”

- **Sì**: l’applicativo può gestire tutti i formati che la DSS 6.x supporta con la configurazione attuale, **senza** dover scrivere codice diverso per ogni tipo.
- **Condizione**: in `pom.xml` devono restare le dipendenze che forniscono i validator e le implementazioni opzionali:
  - **PAdES**: `dss-pades`, `dss-pades-pdfbox`
  - **CAdES**: `dss-cades`, `dss-cms-object`
  - **XAdES**: `dss-xades`
  - **Validazione**: `dss-validation`, `dss-service`, LOTL/TSL (`dss-tsl-validation`), policy (`dss-policy-jaxb`), ecc.

Il test `DssImplementationsAvailabilityTest` verifica che in classpath ci siano le implementazioni per IUtils, ICMSUtils, IPdfObjFactory (necessarie per CAdES e PAdES). Finché il build include quelle dipendenze, **tutti i tipi di firma** sopra sono gestiti con la stessa validità di prima.

---

## È stato “solo” uno svecchiamento con DSS aggiornata?

**Sì.** In sintesi:

1. **Obiettivo**: svecchiare stack (Java, Spring, Servlet) e usare la DSS aggiornata (6.x da Maven Central, package `eu.europa.esig.dss` / `eu.europa.ec.joinup.sd-dss`).
2. **Cosa è stato fatto**:
   - Sostituzione delle vecchie lib e del codice legacy con **una sola** implementazione di validazione (`Dss5SignatureValidationService`) che usa **SignedDocumentValidator.fromDocument()** e i report DSS 6.x.
   - Mantenimento del **contratto** (upload → validazione → report; SOAP DataToValidateDTO → WSReportsDTO).
   - Mantenimento del **comportamento** (estrazione contenuto per p7m/p7d, report completo, LOTL/CRL/OCSP tramite CertificateVerifier).
3. **Riduzione dei file**: è normale. I validator “per tipo” (cades, pades, xades, …) non sono più nel nostro codice ma **dentro la DSS 6.x**; noi abbiamo un unico servizio che delega a `SignedDocumentValidator.fromDocument()`.

Quindi: **sì, risulta fatto così** – svecchiamento e passaggio alla DSS aggiornata, senza cambiare la capacità di validare tutti i tipi di firma (PAdES, CAdES, XAdES, ASiC) e senza cambiare la “validità” della validazione rispetto al legacy.
