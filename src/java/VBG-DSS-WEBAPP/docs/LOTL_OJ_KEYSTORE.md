# Perché la demo DSS passa e la nostra app no: certificati OJ (Official Journal)

Nella **demo ufficiale DSS** (`dss-demonstrations/dss-demo-webapp`) la validazione delle firme CIE passa perché la LOTL (List of Trusted Lists) è configurata con i **certificati OJ (Official Journal della Gazzetta Ufficiale UE)**.

## Cosa fa la demo

1. **Verifica la firma della LOTL**  
   Il file `eu-lotl.xml` è firmato dalla Commissione europea. Per fidarsi del suo contenuto (elenco di puntatori alle TSL nazionali, es. Italia), DSS deve **verificare quella firma** usando i certificati pubblicati nella Gazzetta Ufficiale UE (Official Journal).

2. **Configurazione in `DSSBeanConfig.java`**:
   - `lotlSource.setCertificateSource(ojContentKeyStore())` → keystore con i certificati OJ
   - `lotlSource.setSigningCertificatesAnnouncementPredicate(new OfficialJournalSchemeInformationURI(currentOjUrl))` → solo i certificati annunciati a quel URL sono accettati per la firma LOTL

3. **Senza OJ** il job scarica la LOTL ma **non può validarne la firma**, quindi non estrae correttamente le TSL nazionali (inclusa l’Italia con la radice CIE). Il `TrustedListsCertificateSource` resta vuoto o incompleto → **NO_CERTIFICATE_CHAIN_FOUND**.

## Cosa abbiamo aggiunto nella nostra app

In **`LotlLoader`** è stato introdotto lo stesso meccanismo:

- **`ojKeystorePath`** (obbligatorio per il corretto funzionamento): path al keystore che contiene i certificati OJ (es. `classpath:keystore.p12`).
- **`ojKeystorePassword`**: password del keystore (nella demo è `dss-password`).
- **`ojUrl`**: URL della pubblicazione OJ (es. `https://eur-lex.europa.eu/legal-content/EN/TXT/?uri=REDACTED

Se `ojKeystorePath` è impostato, la LOTL viene validata con questi certificati e le TSL (Italia/CIE) vengono caricate come nella demo.

## Dove trovare `keystore.p12`

Il file **`keystore.p12`** (certificati OJ) è usato dalla demo DSS. Puoi:

1. **Copiarlo dalla demo**  
   Nella repo `dss-demonstrations`, il keystore è referenziato in `dss-demo-webapp/src/main/resources/dss.properties` come `oj.content.keystore.filename = keystore.p12`. Se nel progetto demo è presente in `src/main/resources/keystore.p12`, copialo nella nostra app in `src/` (o in una cartella risorse che finisce in `WEB-INF/classes`), così che sia in classpath come `keystore.p12`.

2. **Scaricarlo dalla distribuzione DSS**  
   Se nella repo non c’è (es. è in .gitignore), puoi recuperarlo dalla [distribuzione ufficiale DSS](https://ec.europa.eu/digital-building-blocks/wikis/display/DIGITAL/Digital+Signature+Services) o dalla demo deployata.

3. **Verifica in `applicationContext.xml`**  
   Il bean `lotlLoader` ha:
   - `ojKeystorePath = classpath:keystore.p12`
   - `ojKeystorePassword = REDACTED
   - `ojUrl` = URL OJ come sopra  

   Assicurati che `keystore.p12` sia nel classpath (es. in `src/` con le altre risorse).

Dopo aver aggiunto il keystore OJ e riavviato, nei log dovresti vedere:
`[LOTL] Certificati OJ (Official Journal) configurati per la verifica della firma LOTL`
e la validazione CIE dovrebbe comportarsi come nella demo.
