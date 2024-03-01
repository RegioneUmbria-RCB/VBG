# Applica layer PDF

Per attivare la funzionalità bisogna attivare il parametro  **APPLICA_LAYER** nella verticalizzazione **PROTOCOLLO_ATTIVO**.

In seguito è necessario attivare la verticalizzazione **APPLICA_LAYER_PROT_PDF** e configurare i parametri (ad esempio):

|Parametro| Valore | Descrizione|
|-----|-----|-----|
|COD_MAIL_TIPO	|(inserire il rifermimento alla mail/testo tipo)|Codice mail tipo da cui prendere l'oggetto per creare la stringa stampata nell'annotazione dell'**Istanza**	|
|COD_MAIL_TIPO_MOV | (inserire il rifermimento alla mail/testo tipo) | Codice mail tipo da cui prendere l'oggetto per creare la stringa stampata nell'annotazione del **movimento** |
|COLORE_FONT_ANNOTATION	| (valori) |Colore del font dell'annotazione secondo il formato RGB Es. 136,136,136. Se non corretto o vuoto prende quello di default.	|
|DIM_RETTANGOLO_X	|(valori)|Dimensione x del rettangolo che racchiude l'annotazione	|
|DIM_RETTANGOLO_Y	|(valori)|Dimensione y del rettangolo che racchiude l'annotazione	|
|IS_BORDO_ANNOTAZIONE_ATTIVO	|(valori)|Può assumere i valori 0(NULL),1. 0: Bordo annotazione non presente; 1: Bordo annotazione presente|
|LOCK_ANNOTATION	|(valori)|Valori ammessi - 1: Annotazione non modificabile/eliminabile, 0: Annotazione modificabile/eliminabile. Default non modificabile	|
|NUM_PAGINA_ANNOTATION	|(valori)|Indica il numero della pagina su cui applicare l'annotazione. Valori che può assumere: NUMERO INTERO ( Es. 1,2,3. Se numero intero è maggiore del numero pagine documento verrà apposta sull'ultima pagina), vuoto o numero non intero : Pagina 1, valore 0: Tutte le pagine, valore -1 : Ultima pagina.|
|POSIZIONE_X	|(valori)|punto dell'ascisse dove posizionare il testo	|
|POSIZIONE_Y	|(valori)|punto dell'ordinata dove posizionare il testo |

La funzionalità comporta l'apposizione di una annotazione riportante i dati del protocollo applicato ad un'istanza o a un movimento di una pratica.