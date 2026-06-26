# Gestione metadati Token

Se il servizio di autenticazione lo supporta è possibile recuperare i metadati della sessione di autenticazione e mostrarli nella ricevuta/riepilogo.

I sistemi al momento supportati sono Cohesion e Federa

## Recupero di un metadato nel riepilogo/ricevuta

Inserire il selettore xsl 

```
<xsl:value-of select="/Istanze/Metadati/IstanzeMetadati/Chiave[text()='NOME_METADATO']/../Valore" />
```

Ad Esempio

```
<xsl:value-of select="/Istanze/Metadati/IstanzeMetadati/Chiave[text()='IDENTIFICATIVO_DIGITALE_PRESENTATORE']/../Valore" />
```