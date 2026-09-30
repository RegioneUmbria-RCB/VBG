# Azioni protocollo

## Crea istanza da sistema esterno

Se abilitata la regola **PRATICA_DA_SISTEMA_ESTERNO** allora nella maschera di *Azioni Protocollo* dopo aver letto il protocollo e scelto uno di **CREA ISTANZA** o **CREA MOVIMENTO** comparirà la funzionalità:

- **CREA ISTANZA DA SISTEMA ESTERNO**
- **CREA MOVIMENTO DA SISTEMA ESTERNO**

La funzionalità contatta una componente che effettua le seguenti operazioni in ordine di precedenza:

- se presente un file ***.SUAP.xml** cerca di recuperare le informazioni secondo le specifiche di impresa in un giorno.
- se presente il file **faldone_telematico.xml** o **faldone telematico.xml** cerca di elaborare le istanze secondo questo formato
- se presente un file **.eml** cerca di elaborare la mail supponendo che segua i formalismi di invio delle pratiche ZES ovvero con i link agli allegati scritti nella mail

Il risultato nel caso di presentazione pratica è quello che la maggior parte delle informazioni sarà popolato (esempio anagrafiche, documenti) senza che l'operatore debba leggere le info dalla mail o dai documenti, in quanto vengono recuperate dai vari XML (se rispettano le specifiche I1G).

## Prerequisiti

### NLA-INFOCAMERE

Va installato e configurato lo war nla-infocamere che contiene la logica per leggere i dati.

### STC

Va configurato il nodo con le credenziali e la url:

- `http://nla-infocamere:8080/nla-infocamere/services/NlaSoap11?wsdl`

### Backoffice

Va censita l'amministrazione Nodo INFOCAMERE con le informazioni del nodo sui parametri.
