# Metadati degli interventi

Nell'apposita sezione all'interno della scheda di un intervento è possibile specificare una serie di metadati.
Un metadato è una coppia chiave/valore arbitraria che può servire a svariati scopi (anche le stampe, esportazioni).

Sono presenti anche una serie di metadati codificati che vengono riportati qui di seguito dove viene specificato anche il loro scopo.

## Dizionario dei metadati

### DOWNLOAD_PRATICA_ZIP_NOMEFILE

La funzionalità download pratica zip per mette di scaricare dall'area riservata il contenuto documentale di una pratica in una cartella zip.
Come comportamento predefinito viene creato uno zip file con il nome **pratica-\<UUID\>.zip** dove **UUID** è un attributo della pratica.
All'interno dello zip i file sono nominati con il loro nome registrato.

Questo METADATO configurato nella voce degli interventi permette di sovrascrivere questo comportamento e indicare una stringa di sostituzione usando dei segnaposto.

I segnaposto disponibili sono
- {protocollo_istanza}
  viene riportato il numero protocollo della pratica (se presente) con una serie di zeri per comporre una stringa di 8 caratteri. Ad esempio, se il numero di protocollo è **123** sarà riportato **00000123**
- {data_protocollo_istanza}
 viene riportata la data di protocollo (se presente) della pratica nel formato **yyyyMMdd** (es per **21/04/1969** sarà riportato **19690421**)
- {anno_protocollo_istanza}
 viene riportato l'anno del protocollo (se presente) della pratica
- {uuid_pratica}
 viene riportato l'attributo UUID della pratica



### FRONTEND_TITOLO_SERVIZIO

Vedi funzionalità [(Messaggistica Rabbit)](/configurazione/tipimovimento/comunicazioni-rabbit/README.md)


### FRONTEND_SOTTOTITOLO_SERVIZIO

Vedi funzionalità [(Messaggistica Rabbit)](/configurazione/tipimovimento/comunicazioni-rabbit/README.md)

### WS_ATTI.CODICE_TRATTAMENTO