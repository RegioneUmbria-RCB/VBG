# Sorteggi delle pratiche

## Introduzione
Tramite la voce di menu Utilità -> <Modulo> -> Sorteggi pratiche è possibile procedere con tutti i sorteggi delle pratiche utili ai fini normativi 
o di controllo da parte dell'Ente ( sopralluoghi, .... )
Nella gestione dei sorteggi è possibile definire 
- i filtri utilizzati per indicare le pratiche che prenderanno parte al sorteggio
- la percentuale di campionamento ( ad esempio solo il 50% delle istanze papabili devono essere sorteggiate per il controllo, ... )
- le azioni da intraprendere per le istanze sorteggiate ( creare un movimento nelle pratiche sorteggiate, generare un documento tipo di verbale, ... )

## Configurare i filtri
Al momento di effettuare un sorteggio è possibile indicare i seguenti filtri

| Filtro | Descrizione |
| ------ | ----------- |
| **Archivio pratiche** | Se specificato, verranno considerate le sole istanze presenti in quell'archivio pratiche |
| **Tipologia intervento** | Se specificato, verranno considerate le sole istanze appartenenti a quella voce dell'albero degli interventi. E' possibile indicare anche una cartella e non una singola foglia |
| **Tipo procedura** | Se specificato, verranno considerate le sole istanze appartenenti a quella procedura |
| **Stato** |  Se specificato, verranno considerate le sole istanze in quello stato |
| **Data movimento dal giorno / al giorno** | Se specificato, verranno indicate le sole istanze che hanno un movimento nell'elaborazione che rientra nel range di dati. Il movimento viene chiesto tramite il filtro Tipo Movimento, se non indicato si applica il range di date al movimento di avvio delle istanze. E' possibile indicare una qualsiasi delle due date oppure entrambe |
| **Tipo movimento** | Se specificato, verranno considerate le sole istanze in cui è presente quel movimento nell'elaborazione |
| **Tipologia di ricerca del movimento** | Viene usato in concomitanza col parametro Tipo movimento e permette di cercarlo tra i movimenti fatti, i movimenti da fare o entrambi |
| **Tipologia Esito** | Utilizzato in concomitanza col parametro Tipo movimento e forza la ricerca tra i movimenti fatti che abbiano come esito quello indicato nel filtro |
| **Percentuale** | Una volta applicati i filtri di cui sopra, questo parametro determina quale percentuale di istanze devono effettivamente essere sorteggiate. Per le altre istanze viene comunque registrato il fatto che sono state campionate per il sorteggio ma non sono state effettivamente sorteggiate. Se vuoto viene considerato 100% |
| **Estrazione a gruppi di n.** | Una volta applicati i filtri di cui sopra, serve ad indicare se le istanze devono essere campionate a blocchi e non tutte insieme. A ogni blocco viene applicati il filtro Percentuale. Se un blocco non viene completato ( esempio estrazione a gruppi di 5 istanze e ne rimangono 4 ) quelle istanze non vengono sorteggiate ma comunque risulterà che hanno preso parte al sorteggio. Per default se non specificato viene considerato un blocco unico |
| **Arrotondamento** | Tramite questo parametro, viene impostato l'arrotondamento qualora, tramite il filtro Percentuale il conteggio delle istanze da sorteggiare torni un numero non intero. ( ad esempio su un totale di 10 istanze e percentuale 33% verrebbero fuori 3,3 istanze ) 
| **Salva** | Non è un filtro ma serve a determinare se il sorteggio va salvato oppure si tratta di una semplice simulazione. |
| **Descrizione** | Viene mostrato solamente se si decide di salvare il sorteggio e indica il nome con cui verrà salvato questo sorteggio |
| **Categoria** | Viene mostrato solamente se si decide di salvare il sorteggio e indica se deve essere collegato ad una specifica categoria configurabile dall'ente |


## Algoritmo
L'algoritmo utilizzato è **Random** ( java.util.Random )
  
## Esecuzione del sorteggio
In prima battuta vengono applicati gli eventuali filtri impostati per capire quali istanze dovranno prendere parte al sorteggio ( oltre ad escludere
 automaticamente le istanze che hanno già preso parte a sorteggi precedenti ).
Sulle istanze estrapolate, viene applicato l'algoritmo sopra indicato per verificare quali delle istanze che hanno preso parte al sorteggio
verranno effettivamente sorteggiate ( tenendo conto di quanto eventualmente specificato nei filtri **Percentuale**, **Estrazione a gruppi di n.**, **Arrotondamento** )
Infine nei filtri è stato indicare di salvare il sorteggio, questo viene memorizzato sul Database altrimenti viene solamente mostrato a video senza
persistere e le istanze non saranno effettivamente sorteggiate

## Schedulazione sorteggi
Attraverso il menu Configurazione -> Tutti i backoffice -> Operazioni pianificate è possibile schedulare i sorteggi in maniera tale da non doverli
fare manualmente tutte le volte.