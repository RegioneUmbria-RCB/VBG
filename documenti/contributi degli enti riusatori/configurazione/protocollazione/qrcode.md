# Applicare layer con QR code

Si è reso necessario di avere, all’interno dei documenti prodotti dai professionisti, la possibilità di applicare un layer con un qr che inquadrato rimandi ad un documento contente le informazioni dell'istanza.

Il documento dovrà essere ricavato da una lettera tipo.

## Funzionalità

Per accedere alla funzionalità occorre attivare la verticalizzazione **QRCODE** e attivare il parametro **TEMPLATE_AUTOMATICO** impostando il valore **S**.

Altri parametri da configurare sono elencati nella tabella sottostante:
| Parametro | Valore ammesso | Descrizione |
|---|---|---|
| TEMPLATE_AUTOMATICO | S/N(null)| Indica se è attiva o meno la funzionalità |
| TEMPLATE_CHIAVE_MAC | stringa | Una   |
| TEMPLATE_HEIGHT | numerico | Indica l'altezza dell'immagine del QR code |
| TEMPLATE_PAGE_NUM | numerico | Indica il numero di pagina in cui andrà applicato il QR|
| TEMPLATE_POS_X | numerico | Indica la coordinata delle ascisse |
| TEMPLATE_POS_Y | numerico | Indica la coordinata delle ordinate |
| TEMPLATE_RIF_LETTERA_TIPO | numerico | È il riferimento alla lettera tipo |
| TEMPLATE_URL_DOWNLOAD | stringa | Indica l'url che verrà richiamato dall'areariservata2 .(*) |
| TEMPLATE_WIDTH | numerico | Indica la larghezza dell'immagine QR code |

L'url che deve essere richiamato dall'areariservata è il seguente: 

../areariservata2/services/rest/istanze/{alias}/{software}/{guid}/generatemplate/{idtemplate}/{mac}
