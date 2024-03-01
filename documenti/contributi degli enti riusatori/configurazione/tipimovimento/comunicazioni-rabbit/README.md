# Configurazione messaggistica RABBIT

La funzionalità permette di configurare la messaggistica utente da inviare alla componente middleware RABBIT che è delegata a gestire lo scambio di messaggi applicativi/utente verso diversi attori che li consumano (es. DOMANDA-ONLINE).

Deve essere attiva la configurazione delle componenti RABBIT come descritto in [Attivazione delle componenti Rabbit](/componenti/README.md#attivazione-della-configurazione-nelle-varie-applicazioni)

Per ogni movimento è possibile specificare una serie di TOPIC/mail tipo a partire dal pulsante di azione **CONFIGURAZIONE RABBIT** presente nella maschera dei tipimovimento.

La mail/testo tipo non è obbligatoria per tutti i movimenti.

È necessario, prima di configurare questo messaggio, creare il testo tipo che serve per impostare il corpo del messaggio che sarà inoltrato.

È possibile farlo dal menù **Archivi-->ArchiviSoftware (o di base)-->mail testi tipo**

Il testo da creare DEVE essere di tipo **MESSAGGI RABBIT** che permette di creare messaggi in linguaggio markdown (supportato per le comunicazioni).

Il messaggio che sarà inviato sarà composto di un titolo, un sottotitolo ed il corpo.

Il corpo del messaggio sarà il testo impostato nella mail/testo tipo.

Per quello che riguarda il titolo ed il sottotitolo è necessario impostare nell'albero dei procedimenti i metadati:

- FRONTEND_TITOLO_SERVIZIO
- FRONTEND_SOTTOTITOLO_SERVIZIO

Se non impostati sarà usato come titolo l'oggetto della mail/testotipo (Sottotitolo rimarrà comunque nullo).

Se si imposta sull'oggetto il segnaposto **[ALBERO_LIVELLO(1)]** viene riportata la voce della foglia dell'intervento dell'albero.