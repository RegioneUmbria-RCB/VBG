# Integrazione di VBG con i servizi di ProcediMarche - Manuale utente

## Introduzione
La regione Marche mette a disposizione degli enti locali una serie di servizi JSON che consentono al singolo ente di consultare il dizionario regionale dei procedimenti definiti nel sistema ProcediMarche. Tramite i servizi gli enti potranno anche pubblicare sul portale regionale ProcediMarche una serie di informazioni specifiche dell'ente relative al procedimento.
In di questo documento sono descritte le funzionalità che sono state aggiunte in VBG per consentire agli operatori dei comuni di interagire con i servizi di ProcediMarche dal backoffice.


## Accesso al dizionario dei procedimenti di ProcediMarche
Per accedere al dizionario regionale dei procedimenti esposto dai servizi di ProcediMarche occorre cliccare sul bottone ‘ProcediMarche' che è presente in fondo all'albero degli interventi.
![](/configurazione/procedimarche/immagini/utepm1.png)


## Elenco dei procedimenti di ProcediMarche
1.	Cliccando sul bottone ‘ProcediMarche' viene visualizzato l'elenco dei procedimenti definiti a livello regionale nel portale ProcediMarche. Di ciascun procedimento viene visualizzato l'identificativo, il nome, la descrizione e la categoria di appartenenza
2.	Sarà possibile collegare i procedimenti regionali a specifici procedimenti di VBG. I procedimenti regionali già collegati a VBG visualizzeranno un'icona specifica   che indica l'esistenza del collegamento.
3.	L'elenco potrà essere ordinato per id, nome, descrizione e categoria e potrà essere filtrato per nome, descrizione e categoria
![](/configurazione/procedimarche/immagini/utepm2.png)

## Dettaglio del procedimento di ProcediMarche
1.	Nell'ultima colonna dell'elenco dei procedimenti un bottone consentirà di accedere alla relativa scheda di dettaglio
### Sezione dati regionali
2.	La scheda di dettaglio riporterà per tutti i procedimenti una sezione con i dati regionali presenti in ProcediMarche relativi al procedimento selezionato. Tutte queste informazioni di competenza regionale saranno sempre in sola consultazione.
3.	Le informazioni presenti in questa sezione sono illustrate nell'immagine seguente. 


![language](/configurazione/procedimarche/immagini/utepm3.png)
### Collegamento di un procedimento regionale ad un procedimento di VBG
4.	Se il procedimento regionale non è ancora stato collegato ad un procedimento di VBG sotto alla sezione dei dati regionali sarà presente un elenco a tendina ed un bottone ‘Collega procedimento locale'
5.	Per collegare il procedimento regionale ad uno dei procedimenti censiti in VBG l'utente dovrà semplicemente selezionare il procedimento locale desiderato dall'elenco a tendina e cliccare sul bottone ‘Collega procedimento locale'.
6.	Una volta che il collegamento è stato creato con successo il sistema ricaricherà la scheda di dettaglio del procedimento in cui apparirà anche la sezione destinata ai dati locali che l'ente potrà utilizzare per pubblicare su ProcediMarche le informazioni utili ai cittadini che desiderano attivare quel tipo di procedimento nel territorio di competenza dell'ente locale.
### Scollegamento di un procedimento regionale da un procedimento di VBG
7.	Un procedimento di ProcediMarche che è stato collegato ad un procedimento di VBG potrà essere scollegato utilizzando il bottone ‘Scollega procedimento locale'.

### Sezione dati locali
8.	Se il procedimento regionale di ProcediMarche è collegato ad un procedimento di VBG sarà presente anche una sezione dedicata ai dati del procedimento di pertinenza dell'ente locale.
9.	I dati di pertinanza del comune nella sezione dati locali potranno essere liberamente editati dagli operatori e salvati in VBG tramite il bottone ‘Salva procedimento locale' in modo tale da poter essere ripresi in qualunque momento.
10.	Le informazioni presenti in questa sezione sono illustrate nell'immagine seguente. 
![language](/configurazione/procedimarche/immagini/utepm4.png)
11. Le prime due righe della maschera presentano due link. Il primo consente di visualizzare il procedimento di VBG che abbiamo collegato. Il secondo consente di visualizzare la scheda del al nodo dell'albero dei procedimenti da cui si attiva il procedimento stesso, se presente.
 12. Alcuni dei campi editabili presentano un'icona a fianco  che consente di recuperare dal procedimento VBG collegato (o dal nodo dell'albero dei procedimenti) l'informazione corrispondente se presente. Queste informazioni provenienti da VBG vengono già recuperate in modo automatico nel momento in cui il procedimento regionale viene collegato a quello locale così da agevolare l'utente nella compilazione dei dati locali da pubblicare. Ovviamente i valori suggeriti potranno essere modificati liberamente dagli operatori.
13. Nel caso dei campi nome e cognome del responsabile. L'informazione non viene recuperata in automatico ma è possibile recuperarla manualmente cliccando sull'icona a fianco del campo. Il caricamento automatico non avviene per il motivo che in VBG nome e cognome del responsabile sono memorizzati in un unica stringa mentre qui vengono richiesti separatamente. L'icona può comunque essere d'ausilio alla compilazione manuale del campo.
14. In ogni caso i valori proposti da VBG sono solo un aiuto alla compilazione epossono essere modificati liberamente dall'operatore prima della pubblicazione su ProcediMarche

### Salvataggio dei dati locali in VBG 
15.	Cliccando sul bottone ‘Salva procedimento locale' i dati della sezione dati specifici dell'ente saranno salvati nella base dati di VBG. I dati non vengono trasmessi a ProcediMarche in questa operazione.

### Pubblicazione dei dati locali su ProcediMarche
16.	Cliccando sul bottone ‘Pubblica su ProcdediMarche' i dati della sezione dati specifici dell'ente locale saranno trasmessi al sistema ProcediMarche e resi immediatamente disponibili ai cittadini sul portale informativo regionale all'indirizzo https://procedimenti.regione.marche.it/
Durante l'operazione i dati pubblicati vengono anche salvati nella base dati di VBG.

### Eliminazione dei dati locali di un procedimento da ProcediMarche

17. Cliccando sul bottone Elimina da ProcdediMarche' i dati della sezione dati specifici dell'ente locale saranno eliminati dal portale di ProcediMarche.


