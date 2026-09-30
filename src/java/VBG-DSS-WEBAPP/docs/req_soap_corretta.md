# Richiesta SOAP corretta per validateSignature

Struttura **minima** accettata dal servizio DSS: il documento da validare va in **`signedDocument`**, non in `cryptographicSuite`. Gli elementi opzionali (`originalDocuments`, `policy`, `cryptographicSuite`, `evidenceRecords`, `signatureId`, `validationTime`, `tokenExtractionStrategy`) vanno omessi o lasciati vuoti; evitare il valore `?` che può causare Unmarshalling Error.

Sostituisci `INCOLLA_BASE64_PDF_QUI` con la stringa Base64 del PDF (es. da PowerShell: `[Convert]::ToBase64String([IO.File]::ReadAllBytes("C:\path\prova.pdf"))`).

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:val="http://validation.dss.esig.europa.eu/">
   <soapenv:Header/>
   <soapenv:Body>
      <val:validateSignature>
         <dataToValidateDTO>
            <signedDocument>
               <bytes>INCOLLA_BASE64_PDF_QUI</bytes>
               <name>prova.pdf</name>
            </signedDocument>
         </dataToValidateDTO>
      </val:validateSignature>
   </soapenv:Body>
</soapenv:Envelope>
```

**Errore nella chiamata in req_soap.md:** il base64 del PDF era dentro `<cryptographicSuite>` e `<signedDocument>` conteneva solo un riferimento MTOM (`cid:...`). Il servizio si aspetta il documento binario (in base64) proprio in `dataToValidateDTO.signedDocument`.
