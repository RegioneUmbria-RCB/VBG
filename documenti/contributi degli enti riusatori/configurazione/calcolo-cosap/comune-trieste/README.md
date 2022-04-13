# **Calcolo COSAP**
Di seguito verrà illustrata una guida per la configurazione del calcolo degli importi COSAP utilizzata dal Comune di Trieste

I calcoli si afferiscono a diverse tipologie di attività riguardanti vari ambiti come Mezzi Pubblicitari, Occupazione Suolo Pubblico, Edilizia ....; in questo scenario ci siamo integrati
con il software Esatto della ditta Advanced System il quale effettua la parte di calcolo importi e ne gestisce tutto il flusso della rateizzazione/PagamentiModena

## Calcolo degli importi
Come accennato sopra, il calcolo avviene passando i dati necessari ( dati della concessione e dell'occupazione ) al software ESATTO mediante chiamate agli Web Services messi a disposzione
In uscita, tale software restituisce:
 - identificativo del calcolo ( utilizzato per le successive eventuali chiamate di annullamento/modifica )
 - l'importo calcolato
 - il modello 3 di PagoPA
 - lo IUV ( o gli IUV se viene rateizzato ) per poter verificare lo stato della posizione debitoria

La chiamata avviene mediante un pulsante all'interno della scheda dinamica che gli operatori utilizzano dopo aver rilasciato la concessione e prima di averla notificata al cittadino
Tutti i dati tornati vengono memorizzati nella medesima scheda dinamica; il modello 3 di PagoPA viene salvato in un campo di tipo Upload e quindi comparirà anche tra i documenti dell'istanza

## Verifica stato pagamento
Non ci sono automatismi/schedulazione ma il tutto avviene tramite un'apposita scheda dinamica che permette di verificare lo stato delle posizioni debitorie associate in qualsiasi momento
Qualora un onere risulti pagato viene memorizzata anche la data di pagamento

## Preventivazione
E' stata creata una specifica scheda che permette, sia lato front che back, di poter richiedere una simulazione di calcolo per ottenere un preventivo. Essendo una simulazione, non viene
generata nessuna posizione debitoria ma viene semplicemente mostrato l'importo dovuto e la formula utilizzata per calcolare tale importo

## Modifica del calcolo
Qualora i dati della concessione variassero nel tempo ( subentri, ampliamenti/riduzioni delle superfici dell'occupazione, ... ), tramite un'apposita scheda dinamica viene inviata una
richiesta di modifica al servizio del Software esatto passando l'identificativo del calcolo che ha subito la variazione e il nuovo set di dati
In risposta si ottiene:
 - identificativo del calcolo
 - l'importo calcolato
 - il modello 3 di PagoPA 
 - lo IUV ( o gli IUV se viene rateizzato ) per poter verificare lo stato della posizione debitoria

 Ovviamente il modello 3 PagoPA e l'eventuale IUV viene ritornato solamente se il conguaglio è positivo

 ## Cessazione di una concessione
 Tramite un metodo degli Web Service del software Esatto, è possibile annullare una concessione ( e quindi tutti i calcoli ad essa collegata e le posizioni non ancora pagate ) passando
 come dati
  - l'identificativo del calcolo
  - la data di cessazione

 Anche in questo caso non ci sono automatismi ma il tutto avviene tramite un pulsante e una formula di una scheda dinamica legata agli interventi di cessazione/revoca