# Keystore aggiuntivo per radice CIE (trust anchor)

Se dopo aver abilitato LOTL e AIA la validazione delle firme CIE resta **INDETERMINATE / NO_CERTIFICATE_CHAIN_FOUND**, il certificato **radice** della catena CIE non è riconosciuto dalla Trusted List caricata dalla LOTL. In quel caso puoi aggiungere manualmente la radice CIE come trust anchor tramite un keystore opzionale.

## Dal report dettagliato

Nel Detailed Report la catena ha 3 certificati (tutti con `Source="SIGNATURE"`). Il blocco **XCV (X.509 Certificate Validation)** fallisce perché la radice (il terzo certificato) non è presente tra i trust anchor. Aggiungendo quel certificato radice in un keystore e configurandolo in Spring, il verifier lo considererà trusted.

## Come ottenere la radice CIE

1. **Dal PDF firmato**  
   Puoi estrarre il terzo certificato della catena (quello con Id che nel report è l’ultimo `ChainItem`, es. `C-87364FB476E74962...`) usando uno strumento di estrazione certificati dal PDF (es. Adobe, strumenti DSS, o librerie che leggono la catena PAdES) ed esportarlo in DER o PEM.

2. **Dal portale CIE**  
   Il certificato radice CA CIE è disponibile su:  
   [Certification Authority - CIE](https://www.cartaidentita.interno.gov.it/en/public-and-business-administration/certification-autority/)  
   Scarica la CA radice e salvala in formato PEM o DER.

## Creare il keystore (PKCS12) con la radice

Con la radice in un file PEM (es. `cie-root.pem`):

```bash
# Crea un keystore PKCS12 contenente solo il certificato radice (nessuna chiave privata)
openssl pkcs12 -export -nokeys -in cie-root.pem -out trusted-roots.p12 -passout pass:
```

Oppure con un file DER:

```bash
openssl x509 -inform DER -in cie-root.der -out cie-root.pem
openssl pkcs12 -export -nokeys -in cie-root.pem -out trusted-roots.p12 -passout pass:
```

La password può essere vuota (`-passout pass:`); in `applicationContext.xml` userai `trustedKeystorePassword` vuota o `""`.

## Configurazione nell’applicazione

1. Copia `trusted-roots.p12` in `src/` (o in una cartella inclusa nelle risorse del WAR, es. `WebContent/WEB-INF/classes/`) così che sia in classpath come `trusted-roots.p12`.

2. In `src/applicationContext.xml` decommenta e imposta le proprietà del bean `lotlLoader`:

   ```xml
   <property name="trustedKeystorePath" value="classpath:trusted-roots.p12" />
   <property name="trustedKeystorePassword" value="" />
   <property name="trustedKeystoreType" value="PKCS12" />
   ```

3. Riavvia l’applicazione. Nei log dovresti vedere una riga tipo:  
   `[LOTL] Keystore aggiuntivo caricato come trust anchor: classpath:trusted-roots.p12 (certificati: 1)`.

Se il path è un file sul filesystem usa ad esempio:

- `value="file:/path/assoluto/trusted-roots.p12"`  
- oppure `value="C:/path/trusted-roots.p12"` (Windows).

Dopo aver aggiunto la radice CIE in questo keystore e aver configurato `trustedKeystorePath`, la validazione delle firme CIE che usano quella catena dovrebbe andare a buon fine (salvo altri problemi, es. revoca o scadenza).
