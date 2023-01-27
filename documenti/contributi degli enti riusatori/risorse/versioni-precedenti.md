
# **Premessa**
La documentazione sugli aggiornamenti non intende essere una documentazione in grado di formare gli operatori all'utilizzo delle nuove funzionalità dell'applicativo, ma intende essere uno strumento che permetta agli operatori di capire se una nuova funzionalità può portare benefici alle gestione delle loro pratiche e se l'unità organizzativa intende usufruirne.

Nel caso in cui una nuova funzionalità risulti interessante, sarà possibile chiedere maggiori delucidazioni al personale addetto alla formazione  che fornirà ulteriori dettagli e gli strumenti necessari alla configurazione della stessa.

Alcune funzionalità saranno già presenti nelle nuove versioni e andranno configurate o sfruttate, altre funzionalità vanno installate su richiesta.

I bug e le nuove funzionalità sono state riportate con i testi scritti al momento della loro individuazione per cui nel testo potrebbe essere stato utilizzato il tempo futuro, comunque gli aggiornamenti presenti in questo documento riguardano sempre bug risolti o nuove funzionalità sviluppate.

Nei testi potrebbero essere presenti dettagli tecnici che hanno permesso agli sviluppatori di individuare il campo di intervento.

# **Premessa**
La documentazione sugli aggiornamenti non intende essere una documentazione in grado di formare gli operatori all'utilizzo delle nuove funzionalità dell'applicativo, ma intende essere uno strumento che permetta agli operatori di capire se una nuova funzionalità può portare benefici alle gestione delle loro pratiche e se l'unità organizzativa intende usufruirne.

Nel caso in cui una nuova funzionalità risulti interessante, sarà possibile chiedere maggiori delucidazioni al personale addetto alla formazione  che fornirà ulteriori dettagli e gli strumenti necessari alla configurazione della stessa.

Alcune funzionalità saranno già presenti nelle nuove versioni e andranno configurate o sfruttate, altre funzionalità vanno installate su richiesta.

I bug e le nuove funzionalità sono state riportate con i testi scritti al momento della loro individuazione per cui nel testo potrebbe essere stato utilizzato il tempo futuro, comunque gli aggiornamenti presenti in questo documento riguardano sempre bug risolti o nuove funzionalità sviluppate.

Nei testi potrebbero essere presenti dettagli tecnici che hanno permesso agli sviluppatori di individuare il campo di intervento.

Il numero riportato sotto ogni bug/nuova funzionalità è riferito al sistema di gestione bugzilla di In.I.T..

La data di versione è riferita al primo rilascio della versione che però successivamente potrebbe avere avuto delle patch, nella lista dei bug sono riportati anche quelli riferiti alle patch.
# **Definizioni, riferimenti, convenzioni**
- **ATTIVA**
  Il temine attiva indica una catena di collegamenti che è composta di istanze la cui "somma" di azioni (vedi AZIONE) finisce con un "+".
  Nell'esempio mostrato nella descrizione del termine AZIONE possiamo dire che l'attività è ATTIVA e quindi contata come 1 fino alla presentazione dell'istanza 30.
- **ATTIVITA'**
  Un'attività commerciale può essere rappresentata da una lista di collegamenti.
  L'attività può essere:
  - ATTIVA – OPERANTE
  - ATTIVA – NON OPERANTE
  - NON ATTIVA
- **AUTORIZZAZIONE**
  Al provvedimento finale può essere legata un'autorizzazione che ad esempio nell'edilizia potrebbe essere un "Permesso di costruire" oppure una "Autorizzazione ambientale".
- **AZIONE**	
  L'azione è il comportamento di un'istanza e può assumere i seguenti valori "+", "-", "=".
  In genere l'azione è molto utilizzata nei software in cui più istanze collegate costituiscono un'unica "pratica" o "fascicolo" come ad esempio nel commercio dove più istanze collegate formano un'attività.
  Nell'esempio vengono mostrate una lista di istanze con le rispettive "azioni"
  - Istanza  1 - Nuova apertura esercizio di vicinato (+)
  - Istanza 10 - Sub ingresso (=)
  - Istanza 18 - Trasferimento (=)
  - Istanza 24 - Aumento di superficie (=)
  - Istanza 30 - Cessazione (-)

La lista mostra che dopo l'istanza 1 è presente un'attività (Es. un negozio) in una determinata localizzazione.
Dopo l'istanza 24 il negozio è sempre 1 anche se è un'altra localizzazione, ha un altro proprietario ed ha cambiato la superficie di vendita.
Dopo l'istanza 30 il negozio non è più presente nel territorio comunale.

- **BASE.DOC**
  Vedi DOCUMENTI TIPO
- **CATENA COLLEGAMENTI**
  Sono istanze collegate tra loro in maniera che una è il seguito di un'altra e insieme formano un fascicolo che le raggruppa.
- **DATA VALIDITA'**
  Un'istanza ha validità a seconda del tipo di procedura con la quale è stata gestita.
  Una DIA è valida dalla data di presentazione della domanda, un permesso di costruire è valido dalla data del rilascio del permesso.
  Per ogni procedura è possibile impostare quale è l'evento che lo rende valido.
- **DOCUMENTI TIPO**
  Sono documenti scritti in word a partire dal modello base "Base.doc".
  I documenti tipo sono l'unione di un normale documento word e una serie di "campi web" che fungono da segnaposto.
  Il formato finale è un documento con estensione ".rtf" rich text format generato dal menù "Salva con nome" di word.
  Durante la produzione di un documento tipo, un componente sostituisce i segnaposto con i dati reali presenti nell'istanza dalla quale si sta stampando.
  - Esempio:
    se si scrive il seguente testo in un documento di word
    "Il numero [-NUMEROPROTOCOLLO-] è il numero protocollo dell'istanza [-CODICEISTANZA-]."
    Poi lo si salva in formato rtf e lo si associa ad un documento tipo, al momento della stampa dall'istanza 1 con numero protocollo 100 si avrà come risultato un documento rtf son scritto all'interno:
    "Il numero 100 è il numero protocollo dell'istanza 1."

I nomi assegnati ai segnaposto sono visibili con il tasto "Inserisci campi web" presente nel documento "Base.doc" scaricabile da "Configurazione" / "Back Office" / "Configurazione Back Office" / "Dati generali" alla voce
Modello Documenti tipo	Scarica l'ultima versione del documento base.doc 

- **MODELLO DATI DINAMICI**
  E' l'insieme di informazioni che possono essere personalizzate dagli operatori e possono essere associate ad un'istanza o ad un'anagrafica.
- **OPERANTE**
  Un'attività non operante è un'attività che è attiva sul territorio ma che ha comunicato la sua inoperatività per un determinato periodo.
  In genere è un movimento dell'iter dell'istanza che rende un'attività non operante.
- **SOFTWARE**
  Indica un modulo dell'installazione, in genere all'interno del comune rappresenta una unità organizzativa.
  Di seguito l'elenco di alcuni dei software installabili:
  - Attività Produttive
  - Commercio su area privata
  - Commercio su area pubblica
  - Pratiche Edilizie
  - Pubblici Esercizi
  - Strutture Ricettive
  - Pubblicità
  - Ecc…
- **PARIX** (<http://www.infocamere.it/doc/Parix.pdf>)
  Piattaforma di Accesso al Registro Imprese in formato XML.
  Realizzata da InfoCamere, è la piattaforma di accesso ai dati del Registro delle Imprese, tenuto dalle Camere di Commercio italiane, che mette a disposizione dei servizi applicativi delle Pubbliche Amministrazioni le informazioni aggiornate sulle imprese. 
- **PARIX GATE**
  è la componente che realizza la funzionalità di cooperazione applicativa tra le Pubbliche Amministrazioni ed è formato dal "gateway" e dalla "porta applicativa" dell'ente (prerequisiti PARIX Dati e PARIX Data Base) e consente ad altri enti locali di interrogare,tramite le proprie applicazioni, l'archivio di sintesi integrato con gli altri data base.
  In genere sono le regioni che mettono a disposizione il servizio.
#
#
# **Versione 2.11**
29/12/2011
## **Albo pretorio**
E' stata aggiunta la possibilità di pubblicare atti sull'albo pretorio, è un modulo trasversale agli altri che può essere richiamato con una voce di menù o attraverso un link.
## **Gestione mercati - miglioria rilascio concessioni**


Oggetto della modifica: svincolare il rilascio di una concessione in una pratica dalla configurazione sulla voce dell'albero dei procedimenti del mercato e dell'uso.

1. Nell'inserimento dell'istanza Il bottone "nuova concessione"  compare se per il software è presente almeno un mercato/una fiera.

2. In fase di rilascio della concessione, se la voce dell'albero dell'istanza è legata ad una manifestazione e ad un giorno allora non è possibile scegliere diversamente; se la voce dell'albero dell'istanza non è legata a nessuna manifestazione allora l'operatore è obbligato a sceglierne una tra quelle configurate

` `3. In fase di rilascio della concessione, l'utente ha la possibilità di selezionare un registro diverso da quello proposto

` `Le istanze che hanno delle concessioni nelle quali il procedimento scelto non  ha configurata nessuna manifestazione, non vengono prese in considerazione nella creazione delle graduatorie.
## **Stradario - Comuni Associati**


Nel caso di comuni associati nella pagina di inserimento istanze lo stradario  filtra per codice comune mettendo a disposizione solo le vie relative al comune scelto e quelle a cui non è associato un comune specifico. In fase di modifica di un'istanza è stata tolta la possibilità di cambiare il comune. 

Sempre nel caso di un'istallazione multi comune,  nella fase della ricerca dell'istanza è stata inserita la possibilità di scegliere il comune di riferimento. 

Inoltre nella lista delle istanze è stata inserita una colonna opzionale che riporta il comune in caso si tratti di un'installazione multi comune.
## **Cancellazione documenti provenienti da Chiamate STC**


Adesso è possibile cancellare i documenti provenienti da STC come  documenti dell'istanza, allegati del movimento, allegati di un endoprocedimento dell'istanza. E' previsto un messaggio  di conferma cancellazione che notifica all'utente che si sta per cancellare un documento proveniente da un sistema esterno. L'operatore può decidere se proseguire o annullare l'operazione.
## **Ripristino della "S" che mostra lo storico del posteggio**

Nella lista dei posteggi di un mercato è stata ripristinata la "S" che mostra la lista dei subentri. La lista viene mostrata se l'operatore clicca la "S".  Lo storico potrà essere recuperato non solo per singolo posteggio , ma anche per ogni uso di quel posteggio.
## **Gestione della ricerca sugli archivi di base delle causali oneri da associare a una voce dell'albero**


Aggiunto il check box accanto al campo di ricerca delle causali oneri che permette di scegliere se filtrare per in base all'archivio corrente (non selezionato) o all'archivio di base (selezionato).
## **Ripristinare l'icona salva note per un'istanza chiusa**


E' stato  ripristinato il funzionamento precedente, che permette di salvare le note anche se un'istanza è chiusa.
## **Ordinare i dati secondo un criterio in Istanze/ricerca autorizzazioni e Istanze/ricerca concessioni** 


Aggiunto sulla maschera della ricerca delle autorizzazioni la possibilità di ordinare per 

a) Numero, data, registro 

b) Data, numero, registro 

Aggiunto sulla maschera della ricerca delle concessioni la possibilità di

ordinare per

a) Numero, data rilascio 

b) Data rilascio , numero 

Nei vari casi è possibile la scelta crescente/decrescente

Le impostazioni di ordinamento selezionate durante una ricerca verranno salvate nella configurazione utente.

## **Implementati sulle mail e testi tipo i segnalibri**


` `- [RIC\_CF] codice fiscale del richiedente.

` `- [RIC\_PIVA] partita iva del richiedente.
## **Aggiunta di modelli dinamici a livello di procedura**


E'  possibile legare dei modelli dinamici anche alla procedura in maniera analoga a quanto accade nell'albero degli interventi o negli endoprocedimenti.

# **Versione 2.11 – Area Riservata**
## **Modifiche area riservata**
` `Nello step di inserimento dei dati dinamici vengono letti anche tutti i modelli dinamici collegati alla procedura.
## **Riepilogo endo**


Nello step di riepilogo endo (dove vengono visualizzati gli endo selezionati e quelli da selezionare) bisogna visivamente distinguere le sezioni:

Procedimento principale (se c'è)

Procedimenti attivati

Procedimenti attivabili

Altro:

1-i testi "Procedimento principale", "Procedimenti attivati" e "Procedimenti attivabili" devono poter essere configurati (la parola procedimenti non è sempre corretta)

2-deve essere possibile, tramite un flag nel workflow.xml, disabilitare la visualizzazione di famiglia e tipo endo mostrando solo la descrizione dell'endo.

3-All'interno delle sezioni la lista va ordinata per "famiglia","tipo endo","endoprocedimento" tenendo conto che famiglia e tipo endo potrebbero non comparire.
## **Privacy**


Creare uno step per la dichiarazione della privacy

Step creato, dal file xml di workflow sono gestibili:

- il testo da mostrare nell'informativa (TestoPrivacy)

- il testo dell'accettazione dell'informativa (TestoAccettazionePrivacy)
## **Firma pdf a blocchi**


E' possibile firmare una form dinamica (scheda dinamica), compilata e trasformata in pdf, sia nella sua interezza che a blocchi. La distinzione avviene quando si associa una form dinamica all'albero degli interventi, ad un procedimento o ad una procedura.
## **Configurazione dei testi dopo il click su "procedi" nell'ultimo step.**


Vanno parametrizzati il titolo della pagina e il testo informativo che vengono mostrati dopo il click sul bottone "procedi" dell'ultimo step 

Vanno resi parametrizzati anche il nome del documento da scaricare (il testo a fianco dell'icona del pdf), il testo del bottone "Trasferisci l'istanza al comune" e il testo che sta sopra alla griglia dei soggetti sottoscrittori

**Estremi atti/autorizzazioni**


Mostrare la possibilità di indicare i riferimenti Atto/Autorizzazione solo per gli endoprocedimenti che hanno almeno un record nella nuova tabella INVENTARIOPROC\_TIPITITOLO
## **Procedimenti e/o presupposti di legge**


1) torno indietro allo step 8 "Individuazione dell'attività e dell'evento

2) non cambio intervento e riprocedo in avanti

problemi:

- mi toglie la spunta ai procedimenti che avevo segnato in precedenza allo step "Procedimenti e/o presupposti di legge", 

- quindi perdo anche i dati che avevo inserito nello step successivo "Estremi atti/autorizzazioni" relativi al num protocollo, data degli atti legati a quei procedimenti
## **Allegati generali**


Visualizzare il testo "altri allegati" solo se sono presenti altre categorie di allegati
## **Inserimento Anagrafiche**
204

Verificare che il codicefiscale dell'utente connesso sia presente tra le anagrafiche inserite nella domanda, se non è presente non si può proseguire ed il messaggio "di errore" sarà quello censito nel tag "MessaggioUtenteNonPresente".

Il tag "MessaggioUtenteNonPresente" deve poter contenere i segnaposto:

{0} nominativo utente connesso

{1} codice fiscale utente connesso
## **Delega a trasmettere**
205

Modificare lo step con controllo "GestioneDelegaaTrasmettere":

- se il codicefiscale connesso non è un richiedente ("R") allora il controllo si deve attivare

- il controllo conterrà uno sfoglia per caricare la delega

- nel testo di descrizione dello step va aggiunto {0} nominativo utente connesso e {1} codice fiscale utente connesso

- aggiungere tag "RichiedeFirma" come boolean che se true obbliga l'inserimento di allegato firmato digitalmente
## **Modifiche alla visura archivio istanze**
211

La visura relativa all'archivio istanze, quella relativa ai soggetti portatori di interesse, è ridotta rispetto alla visura di una pratica alla quale si ha accesso. Vengono rispettati i criteri di privacy.
# **Versione 2.12**
05/03/2012
## **Famiglie, categorie endoprocedimenti: visualizzare le informazioni collegate**
389

Nella gestione delle famiglie degli endoprocedimenti ( Tipifamiglieendo ) deve essere aggiunto un pulsante che visualizza le categorie endo collegate.

Nella gestione delle categorie degli endoprocedimenti ( Tipiendo ) deve essere modificato (label endoprocedimenti) il pulsante che visualizza gli endo collegati.

In entrambe le liste va aggiunto un bottone NUOVO che permette di inserire nel caso di famiglieendo una nuova categoriaendo con la famiglia settata, nel caso di categoriaendo deve permettere di inserire un nuovo endoprocedimento con la categoria (tipoendo) settato.
## **Modifiche funzionalità Configurazione tipibando**
400

È stato chiesto di rendere più agevole la funzionalità di configurazione delle tipologie di bandi:

è stata aggiunta la possibilità di inserire dalla lista nuovi record. Lista ordinata per descrizione.
## **MIGLIORIA - Aggiungere una colonna con l'occupante nel dettaglio della concessione**
405

Quando si visualizza il dettaglio della concessione vengono mostrati a fondo pagina anche eventuali passaggi fatti nel tempo da questa concessione. Tra i dati riportati mostrare una colonna "Occupante" ( mettendola tra "Titolare" e "Causale Acquisizione" ) in cui riportare la ragione sociale o, qualora non fosse valorizzata, il richiedente della pratica relativa a quel passaggio.

MIGLIORIA - Aggiungere una colonna con l'occupante nel dettaglio della concessione

Inoltre va reso cliccabile il numero dell'istanza per permettere la navigazione
## **Funzionalità copia in locale per allegati provenienti STC**


- aggiungere la funzionalità "copia in locale" (Sia in lista che in form) degli allegati provenienti da STC (DOCUMENTIISTANZA- ISTANZEALLEGATI, MOVIMENTIALLEGATI). la funzionalità deve chiamare il metodo esposto da STC allegatobinario, salvare il file nella tabella oggetti e legare il riferimento alla rispettiva tabella di provenienza (DOCUMENTIISTANZA- ISTANZEALLEGATI, MOVIMENTIALLEGATI).

- documenti istanza: Funzionalità "copia tutti gli allegati provenienti da STC". La funzionalità deve prendere tutti gli allegati (DOCUMENTIISTANZA- ISTANZEALLEGATI, MOVIMENTIALLEGATI) provenienti da STC e chiamare la funzionalità "copia in locale" per ognuno di essi. Tale funzionalità deve poter essere invocata per ogni istanza solamente da un operatore per volta e se in corso deve essere visualizzato elaborazione in corso o nascosto il bottone. Se non fosse possibile recuperare gli allegati bisogna scrivere su istanze eventi il motivo.

L'operazione di copia allegati può essere fatta solo per una istanza alla volta dell'installazione. Se un altro utente prova ad attivare l'operazione su un altra istanza o su un altro allegato della stessa istanza per la stessa installazione verrà rediretto su una pagina che notifica che l'operazione è già attiva. Se l'operatore che ha lanciato l'operazione vuole continuare a lavorare dovrà chiudere tutti i browers aperti e accedere nuovamente all'applicativo (questo viene notificato all'operatore prima di lanciare l'operazione).

Se si usa la funzionalità che permette di fare la copia di tutti gli allegati dell'istanza, ogni qual volta un allegato è stato copiato facendo il refresh della pagina o entrando nella pagine sarà possibile accedere all' allegato direttamente dal DB dell'installazione (Tabella OGGETTI).

Se per motivi  eccezionali  il sistema rimane nello stato di "attività copia allegati attiva" anche se invece questa è finita o ha riportato degli errori, nel pannello di controllo di amministratore è stata implementata una funzione che forza lo stato a disattivo.
## **Salvataggio oggetti su Filesystem**


Quando è attivo il salvataggio degli oggetti su Filesystem occorre verificare il nome della directory e aggiungere uno "/" se assente.
## **BATCHSCADENZARIO: filtro per software su TAB MOVIMENTI DA VISIONARE", "MOVIMENTI NON NOTIFICATI", "EVENTI NON LETTI"**


Nel pannello dello scadenzario nei TAB "MOVIMENTI DA VISIONARE", "MOVIMENTI NON NOTIFICATI", "EVENTI NON LETTI" deve essere aggiunto il filtro per Software come avviene per "MOVIMENTI DA EFFETTUARE". Se ORMHelper.software = 'TT'  allora la ricerca deve avvenire per tutti i software abilitati per l'utente loggato.

Nel caso di software specifico : viene usato come filtro tale software

Nel caso di software TT         : come filtro vengono utilizzati i software abilitati per l'utente scelto (o loggato in caso di scadenzario all'avvio) o i software abilitati per il comune se non è stato scelto un operatore specifico.
## **Aggiornamento del dettaglio di "Convocato"**


Quando si aggiorna un convocato nelle commissioni edilizie, non salva i cambiamenti fatti sul tipo di carica
## **Visualizzazioni e ricerche istanze su dati delle anagrafiche storicizzate**


1) Visualizzazione istanza: è richiesto di visualizzare in prima istanza le informazioni del richiedente/i storico e su richiesta quelle dell'anagrafica attuale.

2) Ricerca istanze: deve essere possibile indicando dati di un'anagrafica storicizzata risalire all'anagrafica attuale ed alle pratiche a questa legate.

1) Non viene mostrata la sezione richiedente storico se codicerichiedentestorico.datafinevalidita == null

2) Controllo su descrizione richiedente=descrizione richiedente storico

3) organizzata snippet anagrafica per riutilizzo

4) nel dialog delle informazioni anagrafica storicizzata vengono visualizzate le differenze con la scheda anagrafica attuale
## **Possibilità di legare il certificato di invio (ricevuta dpr 160) alla procedura**


Scenario: 

Come utente di backoffice 

voglio poter collegare un riepilogo domanda ad una procedura

in modo da mostrare al richiedente di una domanda online riepiloghi diversi a seconda del tipo di domanda che si sta presentando

Modifiche al db:

modificare la tabella tipiprocedure, aggiungere il campo CODICEOGGETTO\_CERTINVIO number(10) in fk verso la tabella oggetti

modifiche alla pagina:

Gestire il nuovo campo del db (deve permettere l'upload di un file)

Il nuovo campo deve avere come etichetta "Certificato di invio" e come help "E' il documento contenente espressioni xpath che viene visualizzato come risultato della presentazione di una domanda online (es. ricevuta telematica DPR 160 art.6). Se non specificato verrà utilizzato quello presente nella configurazione dell'area riservata"
## **Riorganizzazione dei campi della pagina di gestione delle procedure**


- Spostare il campo "Codice natura" sotto "Info Web"

- Raggruppare i campi "Nome Modello", "Nome modello per domanda online", "Nome modello diagramma" e "Nome modello precompilato" sotto l'intestazione "Parametri frontoffice - Area informativa"

- Inserire il campo "Certificato di invio tramite sottoscrizione" (vd bug452) sotto l'intestazione "Parametri frontoffice - Area riservata"

Il nome del campo da riportare sotto l'intestazione "Parametri frontoffice - Area riservata" è "Certificato di invio" e non "Certificato di invio tramite sottoscrizione"

**Gestione del flag FO\_UTENTETESTER nella pagina delle anagrafiche**


E'stato aggiunto al db un nuovo campo nella tabella ANAGRAFE, il campo si chiama FO\_UTENTETESTER e serve ad identificare una particolare tipologia di utente del FO.

Quando infatti un utente ha questo flag impostato nel momento in cui può visualizzare l'intero albero degli interventi che vengono mostrati senza utilizzare il flag PUBBLICA di ALBEROPROC.

Modifiche alla JSP

- Gestire il nuovo flag che deve essere visualizzabile solo in caso di persona fisica. 
- Il flag va aggiunto in fondo alla pagina in una sezione intitolata "Parametri frontoffice" e si deve chiamare "Utente tester"
- Il testo dell'help deve essere: "Se impostato l'utente può visualizzare l'albero degli interventi completo, ignorando eventuali impostazioni di pubblicazione dei rami o delle foglie"
# **Versione 2.12 – Area Riservata**
## **Criteri di individuazione degli stili del portalino SUAP + area riservata**


Situazione attuale:

nel web.config c'è la sezione <add key="file-configurazione-contenuti" value="xxxx"/> per indicare quali stili utilizzare, la variabile xxxx viene sostituita da valori che poi fanno riferimento ai rispettivi file xml presenti nella cartella "Contenuti".

Situazione ottimale:

visto che in una stessa installazione dobbiamo servire comuni con differente layout grafico bisogna aggiungere nel back la possibilità di indicare i valori degli stili (per capirci variabile xxxx).

Possiamo aggiungere un parametro nella configurazione dell'area riservata, se il parametro è null possiamo continuare ad avere la logica del web.config.

E' preferibile che il parametro sia una textbox con un help (?) che indica "Per i possibili valori contattare un referente tecnico."

Lato DB

Aggiungere nel la colonna NOME\_CONFIGURAZIONE\_CONTENUTI varchar2(50) alla tabella FO\_ARCONFIGURAZIONE

Lato Area riservata

` `Rivedere a logica di gestione delle configurazioni (c'è confusione nel codice, va implementata una soluzione coerente per accedere ai vari parametri di configurazione)

` `Sostituire tutte le letture del parametro dal web.config con chiamate al nuovo sistema di configurazione

Lato JAVA

Gestire il nuovo campo nella tabella FO\_ARCONFIGURAZIONE
## **Ordinamento su schede dinamiche di intervento, endo e procedure**


Le schede dinamiche visualizzate nell'area riservata devono essere ordinate in base al campo "ordine" delle tabelle alberoproc\_dyn2modellit, tipiprocedure\_dyn2modellit, inventarioprocdyn2modellit

Note:

l'ordinamento delle schede degli interventi è assoluto e non dipende dalla "posizione" nell'albero. Ad esempio una scheda associata ad un ramo padre con ordine = 10 apparirà dopo una scheda di un nodo figlio con ordine= 5, le schede con ordine non specificato devono apparire prima delle schede con ordinamento specificato come se fosse ordine 0.
## **Gestire il certificato di invio anche in base alla procedura**


Nel bo il certificato di invio può essere collegato anche alla procedura (vd bug452 ). Nello step di visualizzazione del certificato di invio il certificato va prima cercato nella procedura e se non trovato nella configurazione.
## **Modificare la logica con cui vengono visualizzate le icone di help nello step di gestione interventi nel caso di CART attivo**


Nel caso in cui sia attiva la verticalizzazione CART l'icona di help che permette di mostrare il popup contenente i dettagli dell'intervento va mostrata solamente se nella tabella STP\_ENDO\_TIPO2 esiste una riga collegata all'intervento con STP\_ENDO\_TIPO2.CODICEOGGETTO not null.
## **Gestire il flag FO\_UTENTETESTER della tabella ANAGRAFE**


Il flag della tabella anagrafe indica che l'utente può visualizzare l'intero albero degli interventi indipendentemente dalle impostazioni del flag pubblica.

Scenario:

Come utente tester

Voglio poter visualizzare l'intero albero degli interventi nello step corrispondente

In modo da poter testare il funzionamento di voci non ancora pubblicate.
## **Mostrare la lista di "endo eventuali" già aperta**


Scenario:

Come cittadino che visualizza l'area informativa nei dettagli dell'intervento voglio poter visualizzare la lista completa di endoprocedimenti eventuali in modo da avere una navigazione più semplice e fare meno click
## **Voci endoprocedimenti cliccabili nella stampa in pdf dell'intervento**


scenario:

come cittadino che accede all'area contenuti voglio poter stampare i dettagli di un endo dalla stampa dei dati dell'intervento in modo da poter accedere ad un maggior numero di informazioni
## **Modifiche all'interfaccia "Effettua movimento" (le mie scadenze) dell'area riservata**


L'interfaccia non tiene conto dei flag "pubblica movimento", "pubblica parere" e "pubblica allegato"

- se nel Bo "pubblica movimento" è impostato a false allora nel FO la sezione relativa al "movimento precedente" non deve essere visibile;
- se nel BO "pubblica parere" è impostato a false allora nel FO il parere non deve essere visibile;
- se nel BO un allegato ha il flag "pubblica allegato" impostato a false allora quell'allegato non deve essere visibile nel FO;
## **Modifica alla gestione procure**


Come utente dell'area riservata, procuratore del richiedente non devo caricare il documento attestante la delega a trasmettere per non ripetere due volte la stessa operazione in quanto la procura può anche avere valore di delega

Dettagli implementazione

- inserire nel file xml di workflow allo step "Verifica presenza della delega a trasmettere" il parametro "IgnoraDelegaSeProcuratoreDelRichiedente"
  - se il parametro è impostato a false o non presente va utilizzata la logica attuale
    (la delega è richiesta se utente non è richiedente)
  - altrimenti utilizzare la nuova logica
    (la delega è richiesta se utente non è richiedente e non ha già indicato di essere procuratore)
## **Modifica all'ordine di visualizzazione delle schede dinamiche**


Durante la presentazione della domanda quando mostra le schede dinamiche deve visualizzare nell'ordine: le schede dinamiche dell'istanza, poi del procedimento principale, poi dei procedimenti obbligatori e infine di quelli non obbligatori.
## **Modifiche allo step degli oneri**


Nello step dell'area riservata riferito alla tabellina degli oneri bisogna visualizzare le note, poi in caso di un totale oneri pari a 0 non deve essere obbligatorio l'allegato della prova di pagamento.
# **Versione 2.13**
04/06/2012
## **Funzionalità in evidenza**
### ***Possibilità di salvare i documenti senza doverli scaricare in locale per poi ricaricarli in upload***
E' stata aggiunta la possibilità di modificare un documento utilizzando il tasto salva dell'edito che ha aperto il documento (Es. Ms-Word per i file con estensione .doc) e di ricaricarlo velocemente sul server senza dover fare le macchinose operazioni di salva sul desktop e "upload" sul server.

La funzionalità è accedibile dal tasto "MODIFICA" presente nel dettaglio di qualsiasi tipo di allegato.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.001.png)

Al modifica il documento viene aperto e dopo le modifiche sarà sufficiente pigiare il tasto "RICARICA IL DOCUMENTO MODIFICATO SUL SERVER" per aggiornare il documento.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.002.png)
### ***Possibilità di firma digitale senza uscire dal back office***
E' stata integrata la firma digitale da back office, l'utente può firmare utilizzando il pulsante "FIRMA DIGITALE" presente nel dettaglio di qualsiasi allegato o dalla lista degli allegati attraverso l'icona a forma di "chiave".

Viene visualizzata l'anteprima del documento da firmare e pigiando firma viene richiesto il pin della smart card inserita, il processo si conclude ed il file nel back office risulterà firmato.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.003.png)

La firma è presente su tutti i documenti caricati anche se non sarebbe necessaria nei documenti di configurazione come ad esempio le lettere tipo.
### ***Modifiche ad alberoproc per la gestione degli endo da aggiungere nell'area riservata***


In fase di presentazione domanda on-line, allo step di selezione degli endo-procedimenti da attivare, viene data la possibilità di aggiungere altri endoprocedimenti oltre a quelli indicati come necessari e a quelli indicati come proposti (o ricorrenti).

La possibilità viene visualizzata nell'area riservata come pulsante "Altri Endo" e la lista che viene visualizzata dipende dalla configurazione specificata per quella voce dell'albero selezionata e dipende dalle famiglie e/o tipologie endo indicate nella sezione "Area Riservata – aggiunta di endo/procedimenti durante la domanda on-line".

Dettagli:

DATABASE:

creare la tabella ALBEROPROC\_ARENDO:

- IDCOMUNE varchar2(6) primary key not null
- ID number(10) primary key not null	-- Letto da sequencetable
- FK\_SCID number(10) not null -- FK su ALBEROPROC.SC\_ID
- FK\_FAMIGLIAENDO number(10) not null -- FK su tipifamiglieendo.codice
- FK\_CATEGORIAENDO number(10) -- FK su tipiendo.codice

PAGINA JSP:

- Aggiungere le sezioni "Area Riservata – aggiunta di endo/procedimenti durante la domanda on-line" (ereditate e non)
- la sezione "Area Riservata – aggiunta di endo/procedimenti durante la domanda [ereditati]" deve essere visibile solo se non ci sono record nella tabella ALBEROPROC\_ARENDO (vd documento allegato)
- la sezione "Area Riservata – aggiunta di endo/procedimenti durante la domanda" deve essere visibile solo se ci sono record nella tabella ALBEROPROC\_ARENDO (vd documento allegato)

AREA RISERVATA:

- Nello step degli endoprocedimenti verificare se vanno mostrati altri endo. In caso positivo mostrare il bottone "Aggiungi endoprocedimenti"(parametrizzabile nel file di configurazione).
  Per verificare se il bottone va mostrato occorre risalire l'albero degli interventi a partire dall'intervento selezionato. se viene trovato almeno un record con riferimenti su ALBEROPROC\_ARENDO allora il bottone va mostrato.
- Al click del bottone mostrare una view contenente tutti gli endo che corrispondono ai criteri specificati in ALBEROPROC\_ARENDO nella quale è possibile selezionare uno o più endoprocedimenti.
- Gli eventuali endoprocedimenti trovati vanno mostrati nella view principale in una sezione separata "Altri endoprocedimenti" (titolo parametrizzabile)

La sezione Area Riservata – aggiunta di endo/procedimenti durante la domanda on-line [ereditati] viene mostrata solo se la lista alberoproc.alberoprocArendos è vuota. E' stato aggiunto un ulteriore controllo che mostra tale sezione secondo questa logica:

${alberoprocArendoEreditate!=null and not empty alberoprocArendoEreditate && (alberoproc.alberoprocArendos==null || empty alberoproc.alberoprocArendos)}
### ***Nuova regola sulla cancellazione di un allegato di un'istanza***


Con l'introduzione dei campi dinamici di tipo upload che salvano il file allegato tra i documenti dell'istanza si è resa necessaria l'aggiunta di una nuova regola da applicare alla cancellazione di un documento dell'istanza per poter preservare l'integrità dei dati.

La cancellazione di un documento dell'istanza non deve essere possibile se l'oggetto allegato proviene dai dati dinamici (vedi "note cancellazione documento istanza.txt" per le query da effettuare). Inoltre non deve essere possibile modificare l'oggetto tramite la schermata di dettaglio documento.

La situazione ideale sarebbe:

- In documentiistanza/list.htm?codiceIstanza=XXX non mostrare la checkbox che permette di effettuare la cancellazione se il documento in base alla regola sopra descritta non può essere cancellato
- In documentiistanza/view.htm?codice=XXX non mostrare il bottone che permette di cancellare l'oggetto se il documento in base alla regola sopra descritta non può essere cancellato
- In documentiistanza/view.htm?codice=XXX non mostrare il bottone che permette di eliminare il documento se questo in base alla regola sopra descritta non può essere cancellato

E' stato creato il flag "FLG\_DA\_MODELLO\_DINAMICO" in documentiistanza, tale flag deve essere settato a 1 se il documento è stato caricato da un modello dinamico. Tale modifica è stata effettuata per semplificare l'implementazione della logica di individuazione dei documenti caricati da un modello dinamico.
### ***Modifica dello stato dell'istanza da un movimento: TIPIMOVIMENTO.FKSTATOISTANZA***


Bisogna poter creare un tipomovimento che possa essere configurato in maniera da spostare lo stato di un'istanza ISTANZE.CHIUSURA.

In fase di inserimento di un movimento (non in update e non in elaborazione) se MOVIMENTI.TIPOMOVIMENTO è collegato ad un tipomovimento che ha TIPIMOVIMENTO.FKSTATOISTANZA impostato allora il campo ISTANZE.CHIUSURA=TIPIMOVIMENTO.FKSTATOISTANZA.

Se il movimento viene cancellato ISTANZE.CHIUSURA non cambia e non torna allo stato precedente.

1) modifiche alla base dati: aggiungere campo su tipimovimento e chiave esterna con tabella statiistanza questo tipo di configurazione non deve essere prevista per i tipimovimento con software = 'TT'.

2) modifiche a funzionalità gestione tipimovimento:

2.1) modificare oggetto di dominio

2.2) modificare jsp della gestione  di tipimovimento. 

Il campo sarà posizionato sotto quello "Fine sospensione/interruzione", avrà come etichetta "Assegna all'istanza il seguente stato" ed help "Se impostato allora in fase di inserimento di un movimento (non in update e non in elaborazione) verrà modificato lo stato dell'istanza con quello selezionato. Se il movimento viene cancellato lo stato dell'istanza non cambierà e non tornerà allo stato precedente.";

3) modifiche a gestione movimenti:

3.1) nella pagina di inserimento di un movimento con lo stato impostato nascondere la combo dello stato istanza o mettere a READONLY lo stato definito dal movimento;

3.2) nel metodo insert, nel blocco childDataIntegration aggiornare lo stato dell'istanza con la chiamata a istanzeService.updateStatoIstanza(Istanze istanza, String nuovoStato);

3.3) In MovimentiController nel metodo insert inibire la chiamata a stanzeService.updateStatoIstanza() nel caso che il tipomovimento abbia configurato questo comportamento.
### ***NlaBackoffice: Gestione tag procure in ricezione/invio***


1. Creazione della tabella istanzeprocure:
   idcomune
   codiceistanza
   fk\_anag\_proc (anagrafe del procuratore)
   fk\_anag\_proc\_storico (anagrafe del procuratore)
   fk\_anag\_rapp (anagrafe rappresentata)
   fk\_anag\_rapp\_storico (storico anagrafe rappresentata)
   codiceoggettoprocura (codiceoggetto del file che contiene la procura
   stc\_id\_documento (riferimenti stc)
   stc\_id\_allegato (riferimenti stc)
1. Creazione chiavi esterne verso istanze, anagrafe, anagrafestorico, oggetti
1. gestione funzionalità lato operatore
   1. Nella pagina di gestione dei documentiistanza prevedere una sezione procure con la lista delle procure e la possibilità di aggiungere una nuova procura. La nuova procura presenterà per i richiedenti (rappresentato e procuratore) due ricerche ajax con la lista delle anagrafiche presenti nell'istanza. In inserimento deve essere calcolato lo storico dell'anagrafe per entrambe. Verifica che le anagrafiche scelte abbiano il CF impostato e siano PF.
   1. Se sono presenti procure e vengono modificati i richiedenti dell'istanza inserire degli eventi che segnalano l'anomalia.
1. NlaHelper
   1. In ricezione (Quando arriva una richiesta di inserimeno pratica) inserire per ogni tag procure una riga nella tabella istanzeprocure. Se CF non trovati nelle anagrafiche dell'istanza rilanciare eccezione
   1. In Invio (Quando si deve popolare la response di Richiestapratica) popolare tanti tag procure quante sono le procure a disposizione.
   1. In Notifica attività prevedere un controllo per verificare che le anagrafiche presenti in procure abbiano il CF impostato e siano tra i richiedenti dell'istanza.
### ***ADD: ISTANZE Aggiungere campo "domicilio elettronico***


- Aggiungere nella tabella istanze la colonna domicilio elettronico (lunghezza simile a quella presente in anagrafe o amministrazioni).
- Il nuovo campo va messo dopo il campo del Tecnico.
- nella pagina di invio mail va proposto di default, se presente, nel campo destinatario

IL CAMPO VA GESTITO ANCHE NEI MESSAGGI STC. (NLAHELPERSERVICEIMPL) DOPO MODIFICHE TASK 411
### ***ADD: Modifiche allo scadenzario***


- Aggiungere tabella RESPONSABILI\_TM\_SCA che permette di configurare a livello di operatore su quali tipimovimenti limitare la ricerca dei movimenti "Da eseguire"
  - dalla lista dello scadenzario sarà possibile spuntare una checkbox "Solo le scadenze importanti" [Su impostazione utente] che permette di applicare o meno questo filtro aggiuntivo. 
  - Dalla lista dello scadenzario deve essere possibile configurare per l'operatore loggato i parametri del proprio scadenzario (Stessa funzionalità presente in "responsabili/viewParametriScadenzario.htm")
- Aggiungere tabella responsabili RESPONSABILI\_TM\_AVV che permette di configurare a livello di operatore su quali tipimovimenti limitare la ricerca dei movimenti "Da Visionare"
- Aggiungere campo in TIPIMOVIMENTO chiamato DIS\_ALERTDAVISIONARE che inibisce in fase di inserimento da sistemi esterni (STC) il settaggio a 1 di MOVIMENTI.flagDaLeggere durante una notifica.
  - gestire campo in Tipimovimento
  - Modificare il valore di flagDaLeggere su data integration di movimentiServiceImpl.
- Aggiungere colonna su TAB "Movimenti da Visionare"-Letto, "Movimenti non notificati"-Notificato che si deve comportare come letto di "Eventi non letti"
  - Aggiungere un tasto "Aggiorna la visualizzazione" vicino a chiudi per ricaricare la pagina (deve mantenere il TAB corrente)
### ***UPDATE: FUNZIONALITA' CREA PDF DA MOVIMENTIALLEGATO***


- nella lista degli allegati del movimenti aggiungere una funzionalità che permette di generare, a partire da una riga una nuova riga con l'allegato trasformato in pdf/a. (Il pdf sarà pdf/a se la componente open office installata sel server è configurata per produrre pdf/a).
  - aggiungere su colonna dettaglio un icona pdf. l'icona sarà colorata se il file è di tipo .doc, .rtf, .docx, .odt altrimenti icona pdf in grigetto con spiegazione che il tipo di file non è supportato.
  - la funzionalità prende l'oggetto della riga dalla quale si vuole produrre il pdf lo passa al fileconverter e inserisce una nuova riga con il nuovo oggetto tornato dalla conversione.

- modificare la snippet degli oggetti in modo tale da mostrare l'icona del tipo di file da scaricare. Le icone possibili saranno di file .zip, .doc, .rtf, .docx, .odt, .pdf. se estensione file non è tra queste allora va bene icona attuale.
  - cercare di aggiungere alla snippet della lista anche la possibilità di firmare digitalmente il file.
### ***UPDATE: FLAG\_CANCELLAZIONE ISTANZE e DOCUMENTI ISTANZA PROVENIENTI DA STC***


Modificare la possibilità di cancellare le istanze ed i documenti da interfaccia web aggiungendo due flag nella configurazione di un operatore.

- TABELLA RESPONSABILI AGGIUNGERE CAMPI:
  FLAG\_CANCELLAISTANZE
  FLAG\_CANCELLADOCUMENTISTC
- gestire i flag nella pagina degli operatori
  mettere come testo di help per
  FLAG\_CANCELLAISTANZE "Abilita l'operatore a cancellare le istanze e tutte le informazioni collegate"
  FLAG\_CANCELLADOCUMENTISTC "Abilita l'operatore a cancellare gli allegati provenienti da sistemi esterni (STC) e nel caso dei movimenti anche le email inviate".
- pagina delle istanze
  - se operatore non ha il flag attivo allora il bottone cancella deve diventare grigetto.
  - se operatore ha il flag attivo allora il bottone cancella deve forzare l'apertura di un jDialog come da immagine "cancellazione\_istanza.png" allegata.
  - Alla conferma si esegue la normale cancellazione della pratica al termine della quale si scrive una riga di log in cui si dice "in data .... l'operatore .... ha cancellato l'istanza .....". il log deve andare in un file specifico per le cancellazioni chiamato backend\_cancellazioni.log
- cancellazione dei documenti STC.
  se un documento (movimentiallegati, istanzeallegati, istanzeprocure, documentiistanza) proviene da STC allora la cancellazione può esser fatta solamente se l'operatore ha FLAG\_CANCELLADOCUMENTISTC=true.
  prevedere sempre un jdialog per la cancellazione di un documento proveniente da STC (stc\_id\_documento e STC\_ID\_ALLEGATO non vuoti) da immagine "cancellazione\_documento.png" allegata.
  - Alla conferma si esegue la normale cancellazione della pratica al termine della quale si scrive una riga di log in cui si dice "in data .... l'operatore .... ha cancellato il documento ..... dell'istanza ...". il log deve andare in un file specifico per le cancellazioni chiamato backend\_cancellazioni.log
  - cancellazione di un movimento
    se il movimento contiene documenti provenienti da STC dare un messaggio all'operatore che ci sono file o email che devono essere cancellati manualmente prima di eliminare il movimento.
  - cancellazione di una mail dall'archivio mail
    allora la cancellazione può esser fatta solamente se l'operatore ha FLAG\_CANCELLADOCUMENTISTC=true.

Prevedere sempre un jdialog per la cancellazione di una mail.

Tutti i controlli non devono essere fatti nel layer service ma nel layer web in modo da non coinvolgere funzionalità di cancellazione business. È solamente una modifica per la visualizzazione utente.
### ***Integrazione con Parix***
E' stata messa a punto l'integrazione con Parix Gate, funzionalità già presente nelle versioni rpecedenti.

La funzionalità permette di estrarre dal Registro Imprese i dati relativi ad un'impresa.

A partire da codice fiscale impresa o partita iva si hanno le seguenti funzionalità:

- Nel back-office
  - durante l'inserimento di una nuova anagrafica vengono recuperati i dati dal Registro Imprese che poi possono essere integrati prima del salvataggio;
  - in modifica di una anagrafica è possibile utilizzare il tasto "Controlla aggiornamenti", posto in relazione al codice fiscale impresa e alla partita iva, che permette di evidenziale le differenze tra l'anagrafica presente nel back-office e quella recuperata dal Registro Imprese.
- Nell'area riservata
  - durante la presentazione della domanda on-line allo step dell'inserimento anagrafiche vengono recuperati i dati dal Registro Imprese che poi possono essere integrati prima del salvataggio;

Per maggiori dettagli richiedere l'allegato "AnagrafePARIX.doc" (o pdf) e 
### ***Gestione dei nuovi campi per scheda dinamica cittadino extracomunitario nella configurazione dell'area riservata***


è necessario gestire i nuovi campi della tabella FO\_ARCONFIGURAZIONE.

- il campo FKID\_SCHEDA\_EC deve essere un cerca verso DYN2\_MODELLIT che permette di ricercare tra le schede dinamiche del software TT e del software corrente. Il campo non deve comparire nel caso in cui la pagina di gestione venga a perta per il software TT
- il campo FLG\_SCHEDA\_EC\_RICHIEDEFIRMA deve essere una checkbox il cui valore deve essere 1 se spuntato e 0 se non spuntato. Il campo non deve comparire nel caso in cui la pagina di gestione venga a perta per il software TT
### ***Gestione della colonna CODICEOGGETTO\_WORKFLOW della tabella alberoproc***


L'utilizzo serve per cambiare la definizione degli step della domanda on-line in base alla selezione dell'intervento (albero). Lo step relativo alla selezione dell'intervento può essere anche il primo nel workflow della presentazione domanda, il seguente è quello presente nella configurazione della voce dell'albero selezionata (se presente).

Nella tabella ALBEROPROC è stata aggiunta la colonna CODICEOGGETTO\_WORKFLOW (vd bug555 ), la nuova colonna va gestita nella pagina di dettaglio degli interventi.

Il campo deve permettere il caricamento di un file (e la ricerca nella libreria dei files). L'etichetta per il campo è "Workflow area riservata"
## **Altre funzionalità e bug risolti**
***Modifica alla modellazione di STC***


- Modificare la modellazione di STC relativa alla localizzazione dell'istanza togliendo l'obbligatorietà del campo "Codice viario".
- In fase di inserimento pratica da STC, lato VBG andrà implementata la funzionalità di ricerca della via in base alle altre informazioni contenute nel tag "denominazione" e nel caso non sia possibile ricondurre univocamente ad uno stradario, tale informazione va inserita nel campo note della localizzazione di default.
- bugzilla id 450  ISTANZERICHIEDENTI.CODICETIPOSOGGETTO non deve essere obbligatorio di conseguenza AltriSoggettiType tipoRapporto non deve essere obbligatorio
- Aggiungere campo DETTAGLIOPRATICA.DOMICILIO\_ELETTRONICO tipo Stringa (Non obbligatorio)
- PersonaGiuridicaType rimuovere obbligatorietà PIVA
### ***Pagina di gestione delle mappature: aggiungere links "Altre schede" e "Altre mappature***


Casi d'uso

- Come utente configuratore di mappature voglio poter visualizzare tutte le schede in cui un campo è presente
- Come utente configuratore di mappature voglio poter visualizzare tutte le schede in cui un campo è presente nelle quali esistono mappature configurate

Dettagli sull'implementazione

- Nella pagina di gestione delle mappature in corrispondenza di ciascun campo occorre mettere due bottoni (vd figura 1): il primo deve mostrare tutte le schede che utilizzano quel campo e il secondo tutte le schede con mappature esistenti sempre relative allo stesso campo.

Gestione del click sul bottone "Altre schede"

- Il click sul primo bottone deve aprire un popup che mostra la lista delle schede che utilizzano quel campo. 
- La selezione di uno degli elementi della lista mostra una richiesta di salvataggio dati. 

Se l'utente conferma il salvataggio dati vengono applicate tutte le modifiche apportate, a seguito del salvataggio oppure se l'utente non richiede il salvataggio delle modifiche la scheda corrente viene sostituita da quella selezionata dall'utente e la lista dei campi e delle mappature viene ricaricata

- Il click sul secondo bottone deve aprire un popup che mostra la lista delle schede che utilizzano quel campo e per le quali esistono mappature (vd figura 4). 
- La selezione di uno degli elementi della lista mostra una richiesta di salvataggio dati. 

Se l'utente conferma il salvataggio dati vengono applicate tutte le modifiche apportate, a seguito del salvataggio oppure se l'utente non richiede il salvataggio delle modifiche la scheda corrente viene sostituita da quella selezionata dall'utente e la lista dei campi e delle mappature viene ricaricata

Note

- Se un campo non è usato in altre schede il bottone "Altre schede" non deve essere visualizzato
- Se un campo è usato in altre schede ma non ha nessuna mappatura associata nelle altre schede a cui appartiene il bottone "Altre mappature" non deve essere visualizzato
### ***COPPARO: scadenzario oneri***


Sono un'associazione di comuni ed hanno la necessità di effettuare statistiche sugli oneri divise per comuni ma la pagina Interrogazioni >> Pratiche Edilizie >> Scadenziario non presenta la combo in alto per la scelta.
### ***INVIOMAIL: Modifiche visualizzazione allegati***


Nell'invio MAIL la lista degli allegati legati agli endo deve essere suddivisa per endo (ordine alfabetico all'interno dell'endo). Mostrare anche gli allegati degli altri movimenti suddivisi per movimento. (ordine alfabetico all'interno del movimento)
### ***DOCUMENTI ANAGRAFICA SU SOGGETTI COLLEGATI DELL'ISTANZA***


Sulla lista dei soggetti collegati dell'istanza devono essere visualizzati anche i documenti dell'azienda e del tecnico. Ora fa vedere solamente quelli del richiedente.
### ***Nuova funzionalità aggiorna data validità istanze***


Nella pagina di gestione delle procedure va aggiunto un bottone aggiorna, così come fatto per il campo determinazioneinizioistanza, per il campo determinazioneefficacia (data validità).

Il comportamento è simile alla funzionalità aggiorna sviluppata per l'alrto ma deve chiamare istanzeService.calcolaDataValidità.

Implemetata la funzionalità che recupera tutte le istanze collegate alla procedura e aggiorma la data di validità dell'istanza in base al campo "determinazioneefficacia" richiamamndo il metodo calcolaDataValidita(Integer codiceIstanza). Le istanze recuperate saranno paginate in modo da evitare problemi di OOM se la procedure avesse un numero elevato di istanze collegate.
### ***Ricerca istanze e attività per i campi della localizzazione***


Nella maschera di selezione istanze, attività, statistiche aggiungere e gestire nella ricerca i campi aggiunti nella 2.12 per la localizzazione ossia:

- Esponente
- Scala
- Piano
- Interno
- Esponente interno
- Fabbricato
- Frazione
- CAP
- Quartiere

Il filtro della query si deve comportare come quello di civico ossia 

lower(istr.CAMPO) like valoreImmesso.toLowerCase
### ***UPDATE: Possibilità di eliminare le richieste di frontend dallo scadenzario***


Nello scadenzario è presente una sezione richieste da frontend dove vengono visualizzate le richieste di modifica anagrafica per un utente censito nel front o per le richieste di registrazione, in questa sezione deve essere data la possibilità di eliminare le richieste.

L'eliminazione dovrà essere logica.

Aggiungere una colonna FLAG\_PROCESSATA (default 0 nel data integration se presente).

DALLA lista devono essere escluse quelle con FLAG\_PROCESSATA = 1

Controllare se gestita nelle classi delle anagrafiche (soprattutto cancellazioni se esiste una chiave esterna).

Possiamo fare due case:

` `- Elimina la richiesta

`   `la riga può essere rimossa fisicamente.

` `- Processa

`   `in questo caso deve mostrare l'anagrafica modificata (con evidenza di cosa è modificato in maniera simile all'allineamento del WS anagrafe) con la possibilità di salvare.
### ***ADD: Aggiungere note su INVENTARIOPROCEDIMENTIONERI e ALBEROPROC\_ONERI***


Aggiungere una colonna NOTE su tabelle 

- Alberoproc\_oneri

- Inventarioprocedimentioneri

Bisogna aggiungere un help (?) con indicato: "le note verranno visualizzate durante la presentazione della domanda on-line al passaggio relativo al riepilogo oneri"
### ***UPDATE: Inserire link a istanze da ricerca ajax altre istanze stradario***


La funzoinalità altre istanze stradario presente in Dettaglio Istanze e Dettaglio istanze stradario deve far vedere il link all'istanza presente nella lista (con history), l'operatore potrà accedere direttamente all'istanza se ne ha i permessi.
### ***ADD: Nuova funzionalità export SI-VBG***


La funzionalità SI-VBG serve per esportare e reimportare le configurazioni di back office nel rispetto delle specifiche definite dal progetto RI-Umbria "Sottoattività A4.1 – Ampliamento con  nuove funzioni di amministrazione" .

- Aggiungere due voci di menù negli archivi <software> - Utilità
  - EXPORT SI-VBG
  - IMPORT SI-VBG

Compaiono solo se attiva la verticalizzazione (configurazione regole) IMPORT-EXPORT-SIVBG
### ***FIX: Gestione campo importoistruttoria in oneri alberoproc e inventarioproc***


Nelle tabelle inventarioproconeri e alberoproc\_oneri viene gestito un campo "importo istruttoria".

La visibilità e la gestione, da parte dell'operatore, di questo campo dipendono da una serie di regole derivate o meno dall'attivazione delle verticalizzazioni "DIS\_ONERIIMPORTOISTRUTTORIA" e "VIS\_ONERIIMPORTOISTRUTTORIA".

- Situazione normale (senza attivazione delle verticalizzazioni):
  - Quando attivo un onere con causale che non prevede endoprocedimento allora importo istruttoria non deve essere visualizzato
  - Quando attivo un onere con causal che prevede endoprocedimento allora importo istruttoria deve essere visualizzato.
- Verticalizzazione "DIS\_ONERIIMPORTOISTRUTTORIA" attivata:
  - Solo negli oneri con causale che prevede endoprocedimenti non deve essere visualizzata l'importo istruttoria.
- Verticalizzazione "VIS\_ONERIIMPORTOISTRUTTORIA" attivata:
  - Solo negli oneri che non prevedono endo procedimenti deve essere visualizzato il campo importo istruttoria.

Nel metodo del service di tipicausalioneri prevedere un metodo che torna a seconda di queste regole se visualizzare o meno il campo istruttoria ed utilizzarlo nelle pagine di gestione modificando la logica attuale.
### ***STC: Modifiche Marzo 2012 V\_1\_3***


- Eliminare Obbligatorietà del campo PARTITAIVA in PersonaGiuridicaType
- aggiungere campo DOMICILIO\_ELETTRONICO di tipo string su DettagliopraticaType
  <element name="domicilioElettronico" type="string" minOccurs="0" maxOccurs="1">
  <annotation>
  <documentation>E' il domicilio elettronico di riferimento (tipicamente PEC) per ricevere comunicazioni in merito alla pratica da parte dei richiedenti/professionisti.
  </documentation>
  </annotation>
  </element>
- Aggiornare gli schemi e le classi degli applicativi che utilizzano STC
- Aggiungere indirizzo corrispondenza su anagrafiche --> ANAGRAFE.DATI CORRISPONDENZA
- aggiungere annotazioni pratica -->ISTANZE.NOTE
- aggiungere tipo note di versione. Per documentare le modifiche / cambiamenti di ogni versione
- Aggiungere su modellazione STC sul tipo complesso OneriType:
- DettaglioPraticaBreveType
  modificata minoccurence di richiedente da 1 (obbligatoria) a 0 non obbligatoria
  <element name="richiedente" type="tns:RichiedenteType" minOccurs="0">
- DettaglioPraticaType
  modificata minoccurence di richiedente da 1 (obbligatoria) a 0 non obbligatoria
  <element name="richiedente" type="tns:RichiedenteType" minOccurs="0">
- LocalizzazioneNelComuneType
  aggiunti
  <element name="fabbricato" type="string" minOccurs="0" maxOccurs="1"></element>
  <element name="km" type="string" minOccurs="0" maxOccurs="1"></element>
### ***STC - MODIFICHE MARZO 2012 2***


Aggiornare alla versione 1\_3 stc le applicazioni nla-aida, nla-enti, AreaRiservata
### ***Introduzione di un nuovo tipo di mappatura "Espressione regolare"***


Pagina di configurazione delle mappature (People, Aida…)

Va aggiunto un nuovo tipo di mappatura a quelli già esistenti: "Espressione regolare".

Il comportamento in fase di configurazione sarà simile alla mappatura di tipo "decodifica": alla selezione del tipo di mappatura "espressione regolare" verranno mostrati i campi relativi al valore di confronto e al valore di decodifica (il valore immesso nel campo "valore di confronto" sarà un'espressione regolare di match e cattura mentre il valore immesso in "valore di decodifica" sarà un'espressione di sostituzione).

Aggiungere la seguente logica nella parte relativa alla gestione delle mappature people:

- se la mappature è di tipo "Espressione regolare"
  - Effettua un match sul valore people utilizzando l'espressione in "valore di confronto"
    - Se il match è negativo nel campo dinamico verrà riportato il valore "" (string a vuota)
    - Se il match è positivo applica l'espressione di sostituzione presente in "valore di decodifica" al match precedentemente trovato per ricavare il valore del dato dinamico

Es.

valorePeople = "2012-03-30"

valoreConfronto = "^(\d{4})-(\d{2})-(\d{2})$"

valoreDecodifica = "$1/$2/$3"  // verificare se java usa la forma $n o %n

valoreDatoDinamico = ""

regEx = new EspressioneRegolare( valoreConfronto )

se( regEx.match( valorePeople ) )

`   `valoreDatoDinamico = regEx.sostituisci( valorePeople,valoreDecodifica )

// valoreDatoDinamico diventa 2012/03/30
### ***MIGLIORIE GESTIONE CQAP***


FUNZIONALITA' GESTIONE COMMISSIONI E CONFERENZE

1. gestione presenze dei commissari
   Quando si discute una nuova pratica "commissioniediliziet/createEsitoCommissioniedilizieR.htm?codiceCommissioneR=" si devono riprendere le configurazioni delle presenze (flag presente) dei commissari dalla pratica discussa in precedenza.
1. voto unanime
   Mettere una combo che setta via javascript per tutti i presenti il voto selezionato.
1. filtro per data nella creazione dell'ordine del giorno
   eliminare il filtro obbligatorio per data. (se non causa OOM) tentare con una GenerateTable.
### ***Visualizzazione della sezione parametri del protocollo nella scheda delle amministrazioni***


Nella scheda delle amministrazioni i parametri "Unità organizzativa" e "Ruolo" dovrebbero essere messi sotto una sezione dal titolo "Parametri per la protocollazione" e devono comparire solo se la verticalizzazione protocollo è attiva.
### ***INVIO NOTIFICA: Modifiche visualizzazioni allegati***


Nell'invio NOTIFICA la lista degli allegati legati agli endo deve essere suddivisa per endo (ordine alfabetico all'interno dell'endo). Mostrare anche gli allegati degli altri movimenti suddivisi per movimento. (ordine alfabetico all'interno del movimento). stessa modifica fatta per L'INVIO MAIL

Soluzione:

- Gli allegati dei movimenti sono divisi per movimento e ordinati per descrizione all'iterno di ogni sezione, quelli del movimento che genera la notifica sono già selezionati per l'invio
- Gli allegati degli endo procedimenti sono divisi per endo e ordinati per descrizione all'interno di ogni sezione, quelli dell'endo legati al movimento che genera la notifica sono già selezionati per l'invio.
### ***FIX: migliorie funzionamento integrazione con SIT***


Errori

- jdialog rimane appeso dopo validazione (non funziona ESC)
- Al posto del messaggio "Nei risultati non è stato trovato il valore ricercato" bisogna indicare "In base ai filtri specificati {lentina} non è stato trovato il valore ricercato{?}" la {lentina} visualizza la lista dei filtri usati, il tasto {?} indica "Se si ritiene corretto il dato inserito è possibile ripetere la ricerca rimuovendo i filtri superflui oppure confermare il dato inserito che verrà salvato come non valido. ".

Nuova funzionalità

- Quando un dato non è validato ma si intende salvarlo va segnato su un nuovo campo che la riga non è validata.
  La validazione avviene a livello di stradario anche se è uno solo dei mappali che non è valido.

SU UPGR METTERE A 0 DI DEFAULT TUTTE LE RIGHE DI ISTANZESTRADARIO.

implementare su dataIntegration default 0.

Soluzione:

- All'evento on\_change effettuare la validazione anche se il valore del campo è vuoto
- Nella pagina di gestione dello stradario istanza: istanzestradario/create.htm?codiceIstanza=1634
  aggiungere in sola lettura il campo CODICECIVICO con title "Identificativo univoco del SIT, la sua nomenclatura dipende dal sit in uso (id, codicecivico…)"
- Campi gestiti
  Componente SIT: esporre un metodo "getCampiGestiti" che ritorna la lista dei campi che il SIT è in grado di gestire.
  Pagina Web: mostrare il simbolo di ricerca (mondino) e fare la validazione solo per i campi presenti nella lista tornata da "getCampiGestiti". Non mostrare il CODICECIVICO se non è presente nella lista "getCampiGestiti".
- Validazione formale
  Componente SIT: esporre un metodo "validazioneFormale" che fa la validazione formale dei dati inseriti e cioè controlla la presenza dei campi che poi verranno utilizzati dalla cartografica per disegnare un punto in mappa, tornerà true/false. Es. per tutti i SIT sarà la presenza del campo CODICECIVICO  mentre per il SIT Nautilus sarà la presenza di VIA+CIVICO oppure FOGLIO+PARTICELLA+SUB
  Pagina Web: al salvataggio, se il campo ISTANZESTRADARIO.VALIDO =1 (valido), invocare il metodo "validazioneFormale" e se torna false allora impostare ISTANZESTRADARIO.VALIDO =0. A video si vedrà il messaggio di stradario non validato. 
- Aggiungere la visualizzazione della colonna ISTANZESTRADARIO.KM sotto all'attuale campo "fabbricato".
- In  presenza di SIT non è possibile mettere più mappali legati ad uno stradario, ci sarebbero problemi per indicare l'id univoco del SIT legato ad un singolo mappale e problemi di validazione della riga (sorgerebbe il dubbio di validare i dati del SIT a livello di stradario o a livello di mappale?).
  Pagina Web: in presenza di SIT dare la possibilità di gestire un solo mappale (il primario), se dovessero essere presenti più mappali collegati ad uno stradario (situazione precedente all'attivazione del SIT) allora i mappali non primari dovranno essere visibili in sola lettura ed in corrispondenza  dovrà essere presente un pulsantino che permette di "separare" il mappale dallo stradario originale ricollegando ad una nuova riga di ISTANZESTRADARIO con gli stessi dati della precedente (non validata).
- Mettere i titoli nelle finestre dei risultati di ricerca e/o validazione del SIT:
  Es. "Lista risultati per il campo PARTICELLA" oppure "Errore di validazione"
- Nella pagina istanze stradario "istanzestradario/view.htm?codice=1992"  mettere il pulsante "duplica" che copia i dati su un nuovo stradario e mappale. Il "duplica" salva prima le modifiche poi duplica e quindi visualizza la nuova riga.
### ***Gestione del campo I\_ATTIVITAORDINE sull ' Istanze***


A partire dalla pagina di dettaglio di un attività deve essere possibile gestire il campo I\_ATTIVITAORDINE secondo la logica:

- Se due istanze appartenenti alla stessa attività hanno data validità uguale, deve essere possibile salvare il valore I\_ATTIVITAORDINE in modo che vengano ordinate per questo valore oltre che per dataValidità. 

Questa operazione deve essere fatta all'interno della pagina del dettaglio di una Attività sulla lista che riporta tutte le istanze associate all'attività.

Se due istanze hanno la stessa data di validità sulla colonna ordine compare un campo di input che permette di inserire un valore numerico.

Vicino all' header della colonna "ordine" ci sarà un icona che permetterà di aggiornare questi campi ordine una volta inseriti.
### ***Anagrafe storico - possibilità di eliminare un'anagrafica storicizzata***


L'elimina non può essere fatto nell'anagrafica più recente

La cancellazione deve verificare che non sia utilizzata

La "Fine Validità" dell'anagrafica storica cancelata deve impostare la "Fine Validità" dello storico precedente (ordinato come al punto sopra)

Soluzione:

Aggiunta la possibilità di eliminare un record di anagrafe storico. La cancellazione verrà effettuta con questa logica:

- Preventivamente viene fatto un controllo ed escludere dalla cancellazione il primo record (rappresenta l'angrafica attuale) e l'ultimo record (rappresenta la prima versione dell'anagrafica) 
- Controllo che l'anagrafe storico non sia utilizzato in Istanze come professionista o richiedente o titolare legale 
  (Istanzeservice.findProfessionistaOrRichiedenteOrTitLegaleStorico(Anagrafestorico anagrafeStorico) )  e Istanzerichiedenti
  come AnagrafeColl o richiedente o Procuratore (IstanzerichiendentiService.findAnagrafeCollOrRichiedenteOrProcuratoreStorico(Anagrafestorico anagrafeStorico))
- Ricalcolo la data di validità secondo la logica stabilita (ved descrizione BUG) nel medoto AngrafeSoticoDAO.ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico)
- Cancello l'anagrafe Storico selezionato.

I punti 3 e 4 sono effettuati all'interno dello stesso metodo del service AngarafeService.deleteAnagrafeStorico(Integer codiceAnagrafeStorico) e sono sotto transazione in modo che, se la cancellazione

provochi un eccezione inaspettata l'effetto dell'aggiornamento della data vada perduto e sia ristabilita la situazione iniziale
### ***Invio mail - nuovi segnaposto***


Aggiungere i seguenti segnaposto:

[MOVIMENTI.FKIDPROTOCOLLO] identificativo univoco del sistema di protocollazione integrato.

[MOVIMENTI.NUMPROT] numero del protocollo (senza /anno)

[MOVIMENTI.ANNOPROT] anno del protocollo

verranno utilizzati nel caso di integrazione con protocollo Iride per inviare mail in modo che iride ricongiunga in automatico le ricevute di inoltro e accettazione PEC al protocollo.

Es. di invio PEC:

oggetto: PROVA INTEGRAZIONE IRIDE - INSERIMENTO CONSEGNE [iride]962292[/iride] [prot]2012/24871[/prot]

dove 962292 sarà il segnaposto [MOVIMENTI.FKIDPROTOCOLLO] e 2012/24871 saranno i segnaposti [MOVIMENTI.ANNOPROT]/[MOVIMENTI.NUMPROT]
### ***Funzionalità copia stradario e separa mappale***


- implementare il metodo su istanzestradariocontroller.separaMappale:
  permette di "separare" il mappale dallo stradario originale ricollegando ad una nuova riga di ISTANZESTRADARIO con gli stessi dati della precedente (non validata) ISTANZESTRADARIO.VALIDO=0 (FALSE).
- implementare il metodo su istanzestradariocontroller.duplicaMappale:
  copia i dati su un nuovo stradario e mappale. Il "duplica" salva prima le modifiche poi duplica e quindi visualizza la nuova riga.

Soluzione:

Funzionalità presente quando la verticalizzazione SIT attiva e non è possibile gestire  più mappali (VEDI BUGZILLA 532). E' stata inserita la possibilità di inserire un nuovo stradario (Istanze stradario) a partire da un mappale associato a uno stradario (Istanzestradario) già esiste.

Implementato il metodo IstanzeStradarioController.separaMappale(), il metodo recupere il mappale dal codicemappale che viene passato al metodo e crea il nuovo stradario chiamando il metodo

istanzestradarioService.insertReplicaStradario(istanzemappaleSorgente).

Implementazione di istanzestradarioService.insertReplicaStradario(istanzemappaleSorgente):

Il metodo a partire da un mappale scelto crea una nuova riga di istanze stradario con le seguenti caratteristiche:    

1. Istanza			: quella a cui era collegata il mappale scelto (istanzestradario.istanza)
1. IstanzeStradario		: quello collegato al mappale scelto (istanzestradario);
1. List<Istanzemappali>	: un solo record che è l' Istanzemappale scelto (istanzemappaleSorgente)

Inoltre allo stradario originale verrà tolto il collegamento al mappale selezionato.     

**DUPLICA STRADARIO**

Crea uno stradario identico (Istanzstradario) a quello orginale 

Implementato il metodo String duplicaStradario(...) {...}:

1. Recupera le informazioni dal comand in modo da poter eventualmente fare l'update dello stradario prima di duplicarlo
1. Invoca il metodo  istanzestradarioService.duplicaStradario(istanzeStradarioSorgente);
1. Ritorna alla pagina di dettaglio del nuovo stradario

` `Implementato il metodo istanzestradarioService.duplicaStradario(istanzeStradarioSorgente):

1. Aggiorna lo stradario sorgente 
1. Fa una copia dello stradario sorgente e la inserisce
1. Fa una copia dei mappali dello stradario sorgente e associa ad essi il nuovo stradario prima di salvarli
### ***Nuova funzionalità - download documenti protocollati dell'istanza***


Nella pagina leggiProtocollo nella sezione riferita agli allegati aggiungere funzionalità per copiare allegati nella sezione documenti dell'istanza.

Problemi aperti:

1. Ogni qual volta si salva il file prima viene controllato che su documenti istanza non sia già presente un file con la stessa descrizione. Se esiste, il file non viene salvato e viene notifica all'utente.
1. La funzionalità di salvataggio dei documenti dal protocollo a documenti istanza è stata replicata anche sui movimenti. I file verranno salvati su movimenti allegati. Prima dell'operazione di inserimento controllerà che non esista un file con la stessa descrizione; in tal caso non farà l'inserimento e lo notificherà all'operatore.
### ***NOTIFICA STC: invio schede dinamiche da notifica Attività***


- AGGIUNGERE UNA COLONNA FLAG\_INVIASCHEDEISTANZA NUMERIC(1,0) SULLA TABELLA TIPIMOV\_STC\_MAPPING
  commento 'Se selezionato nella maschera di notifica attivita'' risulteranno selezionate tutte le schede dinamiche dell''istanza indipendentemente da quanto specificato in TIPI\_MOV\_STC\_MODELLI'
- Gestire il campo nell'oggetto di dominio TipimovStcMapping e nelle pagine 'parametristc/list.jsp' e 'parametristc/formmapping.jsp'
- StcNotificaBean modificare il recupero della lista dei modelli secondo questa regola se TIPIMOV\_STC\_MAPPING.FLAG\_INVIASCHEDEISTANZA=0 allora logica precedente, se TIPIMOV\_STC\_MAPPING.FLAG\_INVIASCHEDEISTANZA=1 allora vengono selezionate di default tutte le schede dell'istanza.
- In caso di notifica automatica, se possibile, va mantenuto lo stesso comportamento: si inviano le schede che sarebbero state flaggate durante l'interazione con l'utente.
  Se TIPIMOV\_STC\_MAPPING.FLAG\_INVIASCHEDEISTANZA=0 allora notifica automatica saranno inviati sono le schede dell'istanza e configurate nel mapping stc del movimento che genera la notifica, TIPIMOV\_STC\_MAPPING.FLAG\_INVIASCHEDEISTANZA=1 verranno inviate tutte le schede dell'istanza.
### ***UPDATE: Visualizzazione delle autorizzazioni delle attività***


Nella pagina della lista delle attività aggiungere una colonna contrassegnata con "A" come nella lista delle istanze che fa vedere le autorizzazioni legate alle pratiche dell'attività.
### ***UPDATE: Interessi di mora applicate agli Oneri dell'istanza***


Modifiche gestione oneri:

configurazione:

Tabella tipicausalioneri aggiunti campi:

- FLG\_TIPICAUSALIINTERESSI NUMBER(1) flag che indica che la riga di tipicausalioneri deve gestire la tabella TIPICAUSALIONINTERESSI '1: Identifica che la causale è una causale di interesse ( con tanto di configurazione ); 0 o NULL: è una causale standard. Applicativamente non può essere impostato a 1 quando TIPICAUSALIONERI.FK\_TIPICAUSALIINTERESSI <> null';
- FK\_TIPICAUSALIINTERESSI NUMBER(8), 'FK con TIPICAUSALIONERI.CO\_ID; indica se la causale ha una causale di interesse collegata. Applicativamente non può essere valorizzato quando TIPICAUSALIONERI.FLG\_TIPICAUSALIINTERESSI = 1' ;

Tabella TIPICAUSALIONINTERESSI:			

oggetto di dominio istanzeoneri

- aggiungere riferimento a FK\_IDPADRE NUMBER(10) 'FK con ISTANZEONERI.ID; indica la riga di ISTANZEONERI padre da cui è stata generata la riga di ISTANZEONERI figlia'
- aggiungere nuova voce di menù "interessi di mora" che porta alla gestione della configurazione degli interessi per quella causale (FLG\_TIPICAUSALIINTERESSI==1);
- modificare il menù di gestione di tipicausalioneri per visualizzare i tipicausalioneri con uno o altro flag. in pratica il flag FLG\_TIPICAUSALIINTERESSI viene impostato da menu'

REGOLE di validazione:

- se la causale TIPICAUSALIONERI.FLG\_TIPICAUSALIINTERESSI = 1 allora non può essere settata TIPICAUSALIONERI.FK\_TIPICAUSALIINTERESSI e viceversa.
- non si può associare in TIPICAUSALIONERI.FK\_TIPICAUSALIINTERESSI la stessa causale.
- la ricerca ajax della causale da associare deve prendere quelle con TIPICAUSALIONERI.FLG\_TIPICAUSALIINTERESSI = 1.
### ***STC - Notifica attività in caso di mancanza dati Foglio, Particella o Tipo Catasto***


Se nella riga di ISTANZEMAPPALI non sono presenti i dati FOGLIO, PARTICELLA e TIPOCATASTO allora, durante la richiesta pratica response, non va compilato l'elemento LocalozzazioneNelComuneType.riferimentoCatastale e va messo un evento (ISTANZEVENTI) "E' stata richiesta la visura dell'istanza e non sono stati inviati i riferimenti catastali in quanto non completi. I dati minimi sono tipo catasto,foglio e particella. [dettaglio: via xx, yy Sezione, foglio, particella, sub]"
# **Versione 2.13 – Area Riservata**
## **Funzionalità in evidenza**
### ***Integrazione documentale on-line***
E' stata messa a punto la possibilità di integrare una pratica utilizzando i servizi on-line.

In generale è possibile che alcuni passaggi del workflow (flusso) di back-office possano essere configurati in modo tale che possano essere effettuati dal cittadino e/o intermediario da on-line.

Il caso più esplicito è quello di richiedere una integrazione documentale per una pratica, il cittadino e/o intermediario può vedere la scadenza nella sezione "le mie scadenze" o in fondo alla visura stato avanzamento pratica e può integrare la pratica da on-line allegando gli opportuni file e/o informazioni, l'inoltro della documentazione viene protocollato nel caso di protocollo integrato al back-office.

La ricevuta dell'avvenuta integrazione può essere prodotta e scaricata da on-line come tutti i documenti presentati e tutti i documenti prodotti dal back-office e pubblicati.
### ***Separare codice fiscale e partita iva nelle anagrafiche di tipo PG***


Attualmente nella scheda delle anagrafiche esiste un solo campo per codice fiscale e partita iva.

Occorre introdurre un nuovo campo per immettere la partita iva
### ***Nuovo step: domicilio elettronico***


Nella domanda on-line va fatto uno step chiamato "domicilio elettronico" che chiede solo l'indirizzo pec.
### ***Gestione di una scheda per l'immissione dei dati dei cittadini extracomunitari***


Scenario:

Nel caso in cui il richiedente di una pratica sia un cittadino extracomunitario è necessario compilare una ulteriore scheda dinamica. La scheda dinamica può o meno richiedere il download e la firma.

Implementazione:

Database 

- Aggiungere alla tabella FO\_ARCONFIGURAZIONE la colonna FKID\_SCHEDA\_EC number(10) -> fk con idcomune su DYN2\_MODELLIT
- Aggiungere alla tabella FO\_ARCONFIGURAZIONE la colonna FLG\_SCHEDA\_EC\_RICHIEDEFIRMA number(1) -> flag 1 = si, 0 o null = no
- Aggiungere alla tabella CITTADINANZA la colonna flg\_paese\_comunitario number(1)

NOTE PER L'UPGR: i valori di flg\_paese\_comunitario vanno riportati nello script dell'upgr

**Area riservata**

All'ingresso nello step delle schede dinamiche viene effettuata la verifica della cittadinanza del richiedente. Se presente la scheda deve apparire prima di tutte le altre schede con un'intestazione configurabile nel file di workflow
### ***Workflow dipendente dall'intervento***


Scenario:

Alla selezione dell'inrvento il workflow della domanda può subire modifiche in quanto interventi diversi potrebbero non richiedere lo stesso tipo di steps.

Implementazione:

A seguito della selezione dell'intervento nel FO vado a verificare se nel ramo selezionato (o nei nodi padre) è valorizzato il campo CODICEOGGETTO\_WORKFLOW.

Se il campo è valorizzato allora creo un nuovo oggetto Workflow in cui gli steps iniziali sono gli steps già eseguiti (step di selezione intervento incluso) e gli steps successivi sono gli steps dell'oggetto Workflow letto dall'albero.

Esempio:

esguo gli steps

- Benvenuto
- Privacy
- Selezione intervento

Carico un nuovo workflow che contiene gli steps

- Endo
- Invio domanda

Genero un nuovo workflow che contiene

- Benvenuto
- Privacy
- Selezione intervento
- Endo
- Invio domanda

ATTENZIONE! Durante l'importazione dei dati CART il codice oggetto workflow deve essere impostato con un codice file presente nella libreria oggetti.
## **Altre funzionalità e bug risolti**
### ***AREA RISERVATA .NET Modifiche STC Marzo 2012***


Bisogna adeguare le interfaccie STC alla modellazione della Versione V\_1\_3.

DettagliPratica-->annotazioni

PersonaFisicaType-->Corrispondenza

Modifiche agli oneri

LocalizzazioneNelComuneType
### ***Step degli allegati: evidenziare se un allegato richiede la presenza della firma digitale***


Occorre evidenziare all'utente se un allegato richiede la presenza della firma digitale
### ***INVIO INDIRIZZO CORRISPIONDENZA DA AREA RISERVATA***


Durante l'invio STC da Area Riservata a STC e Backoffice sembra che non vengano inviate le informazioni dell'anagrafica relative alla corrispondenza
### ***Visualizzazione immediata dello Step di selezione endo "Altri endo-procedimenti"***


se non sono presenti endo configurati ma sono presenti altri endo mostrare direttamente la lista degli altri endo
### ***Possibilità di configurare il numero di righe visibili nei campi dinamici di tipo "multi lista valori"***


La modifica riguarda anche i campi dinamici utilizzati dall'area riservata
### ***Nuovo flag richiede firma digitale su allegati liberi***


Va aggiunto un nuovo flag nel file di workflow che specifichi se l'invio di allegati liberi richieda la verifica della firma digitale.

Il flag va aggiunto nello step degli allegati dell'intervento e nello step degli allegati degli endo**Versione 2.14**
# Possibilità di disabilitare AMMINISTRAZIONI, PROCEDURE, MOVIMENTI, LETTERE TIPO
Per le tabelle in oggetto è stata aggiunta la possibilità di disabilitarle quando non è possibile utilizzarle perché utilizzate da vecchie istanze.
## **Segnaposto MAIL / TESTI TIPO**
Nella funzionalità di configurazione Mail/testi tipo è stata aggiunta una voce chiamata [loc\_estesa] descrizione "localizzazione estesa" con le seguenti caratteristiche:

- Indirizzo, civico / esponente colore (Esponente e colore potrebbero non esserci), se c‘è il km: "km xx" al posto di civico

Sono stati aggiunti gli altri campi di istanzestradario:

- esponente
- colore
- scala
- piano 
- interno
- esponente interno
- fabbricato
- km 
- CAP
- frazione
- circoscrizione
- quartiere
- note
## **Annotazioni negli allegati necessari per la presentazione della domanda on-line**
Nella configurazione degli allegati necessari o ricorrenti per la domanda on-line è stato aggiunta la possibilità di dettagliare meglio, attraverso delle note, le indicazioni per il richiedente:

- Allegati specificati negli endo-procedimenti;
- Allegati specificati nell'alberatura degli interventi;
- Allegati specificati nelle procedure;
## **Scadenzario**
Ogni operatore ha la possibilità di configurare lo scadenzario all'avvio e di impostare i filtri che ritiene più appropriati per avere una lista di scadenze adeguata a se stesso.
Dalla funzionalità di scadenzario

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.004.png)

Dopo la visualizzazione della lista

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.005.png)

Può essere utilizzato il pulsante riquadrato in rosso nella figura precedente per impostare i parametri di scadenzario legati all'utente connesso.
## **Cooperazione con il protocollo**
E' stata aggiunta la possibilità di selezionare "Mezzi" e "Modalità invio" per i protocolli che gestiscono queste funzionalità.
## **Integrazione Back-Office REA (come da specifiche DPR n. 160/2010)**
In base al DPR n. 160/2010 il SUAP ha l'obbligo di aggiornare il registro imprese in caso di avvii, sub-ingressi, trasferimenti ed altre tipologie di pratiche, l'aggiornamento avviene in cooperazione applicativa secondo le specifiche pubblicate sul portale <http://www.impresainungiorno.gov.it> , per i dettagli richiedere la documentazione ad In.I.T.
## **NLA\_RICEZIONE\_PEC**
E' stata introdotta una componente che permette di interpretare una PEC pervenuta alla casella postale dell'ente per crearne un automatismo nel back-office:

- Pec ComUnica pervenuta dalla camera di commercio (telemaco)à genera automaticamente un'istanza
- Pec di presentazione domanda strutturate come da DPR n. 160 à genera automaticamente un'istanza
- Pec di presentazione domanda non strutturate à genera automaticamente un'istanza
- Pec di rientro parere enti terzi strutturate come da DPR n. 160 à genera automaticamente il movimento di rientro parere
- Pec di consegna e accettazione à riconcilia le ricevute di accettazione e consegna nell'archivio mail del back-office dal quale sono state inviate le PEC

(vedi allegato "Le-comunicazioni-PEC.pdf")
## **CART - Cooperazione Applicativa Regione Toscana**
Implementazione completa delle specifiche RFC riferite ai SUAP per la cooperazione CART e relative comunicazioni ASL.
## **Nodo NLA People – Integrazione con MyPage**
E' stata sviluppata l'integrazione con MyPage di People come da specifiche di qualificazione di Regione Emilia Romagna.
## **People – Configurazione regole**
- E' stata introdotta la possibilità di non aggiornare automaticamente le anagrafiche pervenute da SUAP-ER;
- E' stata introdotta la possibilità di mappare i dati relativi alla localizzazione delle domande;
- E' stata introdotta la possibilità di mappare tutte le anagrafiche collegate alle domande;
## **Statistiche**
Sono state introdotte migliorie per la configurazione delle esportazioni ad uso statistico e per le esportazioni utilizzate dagli osservatori regionali o per l'anagrafe tributaria.

Contattare In.I.T. per i dettagli.
# **Versione 2.14 – Area Riservata**
## **Migliorie sui "movimenti" da effettuare on-line (Es. integrazioni)**
E' stata migliorata l'interfaccia utente per le integrazioni da presentare on-line;
## **Presentazione domanda nel rispetto delle regole CART - Cooperazione Applicativa Regione Toscana**
Implementazione completa delle specifiche RFC riferite alla presentazione della domanda on-line.
## **Firma digitale integrata**
E' stata integrata l'apposizione della firma digitale sull'area riservata e sulla presentazione della domanda CART.
# **Versione 2.15**
## **Scadenzario**
Sono state introdotte due nuove sezioni:

- "Nuove domande STC" 
  che mostra la lista delle domande pervenute da on-line o da un sistema esterno (Es. altro modulo)
- "Eventi di sistema"
  che mostra la lista degli eventi che rappresentano errori e/o avvertimenti verificatisi durante le operazioni batch (non supervisionate da un operatore).
## **Elaborazione: visualizzazione allegati**
Nell'elaborazione è ora visibile la lista degli allegati per ogni movimento senza dover entrare nella sezione allegati del dettaglio del movimento.
## **Contabilità mercati**
Gestione posteggi assegnati ai consorzi.
## **Pagina centrale personalizzata per utente**
Dalla gestione degli utenti (funzionalità amministrativa) è possibile personalizzare la prima pagina dopo l'accesso al back-office, tale pagina non sarà visibile se è attivo lo scadenzario all'avvio.
## **Contabilità mercati: funzionalità di adeguamento iva**
Funzionalità per aggiornare tutti gli importi contabili relativi a registrazioni non incassate.
# Protocollazione: documento principale
E' stata introdotta la possibilità di selezionare quale è il documento principale tra i documenti inviati al protocollo, la funzionalità è attiva per i protocolli che utilizzano questa informazione.
# **Versione 2.16**
## **ATTIVITA' - Schede dinamiche collegate all'attività**
Sono state introdotte le schede dinamiche collegate all'attività, la gestione della scheda avviene sempre dagli archivi dove sono attualmente gestite le schede delle istanze e delle anagrafiche.

Se nelle schede con contesto="ATTIVITA'" viene utilizzato lo stesso campo utilizzato in una scheda istanza questo viene automaticamente popolato nelle operazioni di collegamento dell'istanza ad una attività.

La funzionalità ha due scopi:

1. Agganciare all'attività un numero illimitato di informazioni;
1. I campi condivisi con le schede dinamiche delle istanze collegate all'attività vengono variati dinamicamente attualizzando di fatto le informazioni a corredo dell'attività. Ad esempio la dimensione della superficie alimentare iniziale di un attività (es. 120Mq) può diminuire in caso di aumento/diminuzione superficie di vendita (100Mq) o può variare in caso di trasferimento, se il dato Superficie Mq è un campo dinamico condiviso tra una scheda dinamica dell'istanza e una scheda dinamica dell'attività allora l'attività cambia nel corso del tempo in base alle istanze ad essa collegate.
## **ATTIVITA' – Possibilità di estrarre la situazione attività ad una determinata data**
Nella gestione dell'attività, in relazione alla modifica delle schede dinamiche legate all'attività, è possibile vedere in che modo l'attività cambia nel tempo e in una determinata data quali erano le sue caratteristiche.
![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.006.png)
## **Integrazione Protocollo "Tipo smistamento"**
Per i protocolli che gestiscono il "Tipo smistamento" (Per conoscenza, per competenza) è possibile configurare la funzionalità.
## **Mail - Rubrica amministrazioni**
Nell'invio della mail è stata gestita la rubrica delle amministrazioni per ricercare l'indirizzo a cui inviare la mail.
## **AIDA-Aggiornamento sul portale  del numero protocollo assegnato alla pratica dal VBG**
E' possibile configurare un movimento del workflow del back-office in modo che aggiorni sul portale AIDA il numero protocollo assegnato alla pratica così diventerà visibile agli utenti del portale che hanno presentato una domanda.
## **AIDA-Aggiornamento sul portale  dello stato avanzamento della pratica**
E' possibile configurare dei movimenti del workflow del back-office in modo che aggiornino sul portale AIDA lo stato avanzamento della pratica così diventerà visibile agli utenti del portale che hanno presentato una domanda.
## **Nuove variabili segnaposto**
- [-TITOLORERESPONSABILEPROC-]
  stampa il titolo (dott., ing…) del responsabile del procedimento censito nell'istanza.
- DATA SCADENZA DEL MOVIMENTO: [-MOV\_DATASCADENZA-] riporta la data di scadenza del movimento da cui si sta facendo la stampa
- DATA SCADENZA DEL MOVIMENTO ESEGUITO (CodMovimento): [-MOV\_DATASCADENZA\_FATTO(InsCod)-] riporta la data di scadenza del tipomovimento specificato dentro le parentesi, cercandolo solamente tra i movimenti fatti ( quelli in verde )
- DATA SCADENZA DEL MOVIMENTO DA ESEGUIRE (CodMovimento): [-MOV\_DATASCADENZA\_DAFARE(InsCod)-] riporta la data di scadenza del tipomovimento specificato dentro le parentesi, cercandolo solamente tra i movimenti da fare ( quelli in blu )
## **Aggiornamento ISTAT su canoni posteggio**
E' stata introdotta la funzionalità in oggetto per adeguare i canoni dei posteggi.
## **Commercio su aree pubbliche – registrazioni per consorzio**
Durante la creazione delle registrazioni contabili se il mercato è gestito dai consorzi allora deve essere chiesto all'operatore se le registrazioni contabili devono essere individuali per concessionari o singola per l'anagrafe del consorzio.

Nel primo caso viene usato il solito algoritmo, nel secondo caso invece di creare n registrazioni ne va creata una sola con anagrafe consorzio.
## **Commercio su aree pubbliche – wizard nuova registrazione contabile posteggio**
Funzionalità che permette di creare velocemente una registrazione contabile per il singolo posteggio, utilizzata in caso di rilascio nuova concessione.

La funzionalità parte da un posteggio dove sono individuati già mercato, giorno, posteggio, anagrafe (della concessione), costo annuale / mensile.

L'operatore deve indicare il mese dal quale deve partire il pagamento (dipende da quando è stata rilasciata la concessione).
## **Commercio su aree pubbliche – Concessioni revocate**
Nella pagina di ricerca registrazioni contabili è stato inserito un pannellino che permette di visualizzare la lista delle concessioni cessate negli ultimi 30 gg dalla data odierna.
## **Gestione ruoli**
E' stata revisionata la gestione dei ruoli legata agli operatori di back-office, ad esempio l'operatore appartenente ad un ruolo che può gestire l'istanza ma non può gestire l'istruttoria della pratica vedrà i movimento del workflow di back office relativi all'istruttoria in grigio e non potrà gestirli. Per i dettagli sulla configurazione dei ruoli chiedere ad In.I.T.
## **Funzionalità link preferiti**
Per poter accedere direttamente alle funzionalità più utilizzate ogni operatore ha la possibilità di configurare i suoi link preferiti attraverso il pulsante (stellina)posto in alto a destra del back-office:

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.007.png)

I link su preferiti può essere anche una funzionalità esterna al back-office.

**Protocollazione da movimento, visualizzazione degli allegati di altri movimenti**

Durante la protocollazione di un movimento è possibile selezionare ed inviare anche gli allegati di altri movimenti oltre a quelli dell'istanza e degli endo-procedimenti.
# **Versione 2.17**
## **Endo-procedimenti legati a più amministrazioni**
Negli endo-procedimenti gestiti dagli archivi di base (TT) è possibile gestire che siano legati a più amministrazioni, pulsante "ATTIVA IN".

La funzionalità può essere utilizzata per generare, nell'elaborazione, più movimenti di notifica verso diverse amministrazioni legati allo stesso endo-procedimento (notifica per competenza, per conoscenza…).
## **Integrazione Protocollo**
Durante il "Leggi" protocollo, per i protocolli che permettono tale funzionalità, è possibile selezionare i documenti che devono essere scaricati nella sezione documenti del back-office, tornando nella lista dei documenti è possibile vedere quali documenti sono già scaricati e quali possono essere ancora scaricati.
## **Gestione attività: aggiunta la tipologia dell'attività**
Nella gestione dell'attività è possibile fare una ulteriore suddivisione per tipologia, ad esempio all'interno del modulo commercio è possibile gestire tipologie quali "Esercizi di vicinato", "Medie strutture"…
## **Ricerca attività: aggiunti filtri**
Aggiunta la possibilità di ricercare le attività per endo-procedimento e per campi dinamici.
## **Integrazione pagamenti PAYER**
E' stato integrato il sistema di pagamenti di Regione Emilia Romagna PAY-ER.

E' ancora in fase di collaudo.
## **Commercio su aree pubbliche: assegnazione automatica concessioni**
In base alla graduatoria è possibile assegnare automaticamente le concessioni sui posteggi ordinati in base a criteri di "importanza".
## **Inserimento rapido istanza**
Per la funzionalità in oggetto è possibile stabilire se far vedere tutti i campi dell'istanza o solo un sottoinsieme.
## **Nuovi segnaposto per le stampe**
- Richiedente
  [-COMUNEDINASCITA-]: COMUNE DI NASCITA, descrizione del comune di nascita
  [-PROVINCIADINASCITA-]: PROVINCIA DI NASCITA, sigla della provincia del comune di nascita
- Tecnico
  [-TEC\_LUOGODINASCITA-]: LUOGO DI NASCITA, comune di nascita e sigla della provincia separati da uno spazio
  [-TEC\_COMUNEDINASCITA-]: COMUNE DI NASCITA comune di nascita
  [-TEC\_PROVINCIADINASCITA-] : PROVINCIA DI NASCITA sigla della provincia del comune di nascita
- Dati Soggetto per stampa documenti anagrafe:
  [-SOGCOL\_COMUNENASCITA(CodTipoSoggetto)-]: COMUNE DI NASCITA, comune di nascita
# **Versioni prossime**
- Metti alla firma
- PEC – Protocollo – Back
- Modulo Servizi generico
# **Versione 2.18**
Gennaio 2014
## **Visualizzazione estremi atto e riferimenti protocollo mittente**
Nel dettaglio di un movimento dell'elaborazione, nel caso in cui il movimento sia legato ad un endo-procedimento, sarà possibile gestire gli estremi del parere/atto rilasciato dall'ente e/o visualizzare gli estremi dell'atto già in possesso del richiedente e dichiarato in fase di presentazione domanda.

Vedi pulsante "ESTREMI ATTO" della figura sotto.

Nei movimenti legati ad un endo è possibile specificare i riferimenti del protocollo mittente, vedi sezione "Riferimenti protocollo del mittente" della figura sotto.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.008.png)

E' stato aggiunto un segnaposto nelle **mail tipo** riferito agli estremi atto.

Casi d'uso:

- Quando è il richiedente che comunica di possedere un atto/autorizzazione gli estremi dello stesso vengono memorizzati nella sezione degli endo-procedimenti spuntando la spunta della colonna "acquisito" e compilando la colonna "Dettaglio"
  ![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.009.png)
  ora tali informazioni possono essere viste e/o gestite, tramite il pulsante "ESTREMI ATTO", anche dal movimento di "trasmissione" all'ente/ufficio interessato dall'endo;
- Quando l'atto/autorizzazione/parere è rilasciato dall'ente/ufficio interessato dall'endo è possibile registrare l'informazione utilizzando il pulsante "ESTREMI ATTO" nel movimento di rientro legato all'endo e/o è possibile specificare gli estremi del protocollo del mittente.
## **Selezione tecnico/intermediario dall'archivio anagrafiche**
Nella ricerca di un tecnico da associare ad un'istanza è possibile indicare che la ricerca venga fatta anche sulle anagrafiche che non sono censite come tecnico, vedi spunta aggiunta di fianco all'intermediario.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.010.png)
## **Stato istanza, chiusura istanza**
Per evitare problemi nel fare statistiche sono stati messi dei vincoli alla chiusura delle istanze: una chiusura istanza può essere fatta solo in presenza di una data di chiusura.

Nei movimenti dell'elaborazione, nella tendina relativo allo stato attuale dell'istanza, non sono più visibili gli stati che indicano l'istanza chiusa, tale chiusura deve essere fatta con il movimento di chiusura specificato nella sezione "Registro provvedimento autorizzativo" della procedura 

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.011.png)

`   `Se il movimento di chiusura prevede un atto allora si configureranno gli altri parametri necessari.

E' possibile chiudere l'istanza utilizzando il pulsante "INFO" presente nella scheda "ALTRE FUNZIONI" dell'istanza, in questo caso dopo aver selezionato uno stato di chiuso nella tendina e salvato l'informazione vengono chiesti i dati necessari per generare il movimento di chiusura (che risulterà nell'elaborazione) e verranno chiesti eventuali estremi dell'atto rilasciato.
## **Salvataggio ricerche**
Nelle pagine dove è possibile salvare delle ricerche (ricerca istanze, ricerca attività e scadenzario) ora è possibile per ogni operatore salvare i criteri di ricerca che più utilizza:

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.012.png)

Una volta inseriti i criteri di ricerca, prima di selezionare il cerca, è possibile dare un nome al criterio e salvarlo, in questo modo è possibile utilizzare tali criteri per future ricerche.

Nelle versioni precedenti questi criteri potevano essere salvati solo da un amministratore.
## **Parametri di protocollazione e fascicolazione specificati in base all'albero degli interventi**
La sezione è ora raggiungibile tramite i nuovi bottoni "PARAMETRI PROTOCOLLAZIONE", "PARAMETRI FASCICOLAZIONE".
## **Spostamento di un'istanza da un modulo all'altro**
E' stata aggiunta questa utilità dal pannello amministrativo in uso agli operatori In.I.T..
## **Gestione domande STC con errore**
Nel pannello di gestione delle domande con errore è possibile visualizzare l'XML della domanda, ritentare l'inserimento (dopo aver apportato alla configurazione le modifiche necessarie per caricare la domanda) e modificare l'xml arrivato da parte di un utente amministratore.
## **Gestione attività / esercizi (nell'edilizia edifici / fascicolo)**
Nella lista delle attività sono state apportate migliorie alla funzionalità "ESPORTA IN DATA", il tipo di report che è possibile selezionare come output sono quelli di DEFAULT, per esportare i dati secondo uno specifico tracciato deve essere richiesto ad In.I.T. e quindi costruito ad-hoc.
## **Visura camerale Parix Gate**
In presenza dell'integrazione con PArix Gate ora è possibile richiedere una visura camerale aggiornata. E' possibile richiedere la visura del pulsante "VISURA CAMERALE" presente nella sezione "Soggetti collegati" dell'istanza (in questa sezione è possibile gestire i documenti legati ad ogni anagrafica dell'istanza compresi quelli presenti in prima pagina) o dal pulsante documenti della scheda anagrafica (Archivi/Archivi di base/Richiedenti e tecnici).
## **Scadenzario: domande STC con errore**
Nello scadenzario è stata aggiunta la scheda relativa alle domande STC arrivate con errore.
## **Generazione del Codice Pratica Telematica**
In ogni singolo modulo è possibile attivare la generazione automatica del Codice Pratica Telematica in fase di inserimento istanza (anche tramite STC), l'attivazione della funzionalità può avvenire da "Configurazione / Tutti i back-office / Configurazione regole" e la regola da attivare per lo specifico modulo è GENERA\_COD\_PRATICA\_TELEMATICA

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.013.png)

Il codice viene generato se non presente e con le specifiche SUAP indicate nell'allegato tecnico del DPR n. 160/2010: <codice-fiscale>-<GGMMAAAA-HHMM>.
## **Tipi Titolo**
Quando è presente almeno una riga nella lista dei Tipi Titolo collegati ad un endo-procedimento significa che durante la presentazione della domanda on-line è possibile indicare che si è già in possesso di uno dei titoli autorizzativi presenti sulla lista e che non è necessario compilare e allegare la modulistica legata all'endo.
Nei tipi titolo è possibile indicare quale dati chiedere per ogni titolo, la figura che segue è il dettaglio di un tipo titolo configurato per richiedere obbligatoriamente:

- La data
- Il numero
- L'ente che l'ha rilasciato

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.014.png)

E' possibile richiedere un allegato da caricare contestualmente alla dichiarazione di possedere il titolo.

Le informazioni inserite on-line le ritroviamo collegato alla sezione endo-procedimenti attivati del back-office, vedi paragrafo "Visualizzazione estremi atto e riferimenti protocollo mittente".
## **Invio mail dopo la protocollazione**
Dopo aver protocollato è possibile utilizzare il bottone "INVIA MAIL" per preparare una mail che utilizza gli stessi dati che sono serviti a fare il protocollo.
## **Filtri per la ricerca delle attività / esercizi (Nell'edilizia: Edificio e/o Fascicolo edilizio)**
E' stata aggiunta la possibilità di filtrare le attività aggiungendo condizioni relative ai campi dinamici.

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.015.png)

La scheda non fa filtro, è utilizzata per ricercare più velocemente i campi.
## **Tipi soggetto collegati all'albero degli interventi**
E' ora possibile collegare i tipi soggetto (Richiedente, Proprietario, Direttore lavori, Responsabile sicurezza…) all'albero degli interventi così nella domanda online chi sta presentando vedrà solo i tipi soggetto pertinenti al tipo di intervento selezionato.
## **Gestione delle comunicazioni legate ad una graduatoria**
Una volta generata la graduatoria relativa ad una manifestazione (fiera/mercato) è possibile gestire le comunicazioni verso chi ha preso parte alla graduatoria (assegnatario o meno), tali comunicazioni prevedono:

- La registrazione di un movimento nell'istanza riferita alla domanda di partecipazione alla manifestazione;
- La protocollazione del movimento creato (opzionale)
- La creazione di una lettera (opzionale)
- Il metti alla firma della lettera creata (opzionale)
- L' invio della PEC/mail al domicilio elettronico dell'istanza comprensivo di lettera allegata (opzionale)

Per i dettagli richiedere ad In.I.T..

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.016.png)
## **Pannello Gestione Protocollazione PEC**
E' stata aggiunta una funzionalità che permette di leggere le PEC da una casella di posta, visualizzarle e decidere come gestirle:

- Generando dalla PEC una nuova istanza;
- Generando dalla PEC un nuovo movimento di un'istanza presente;
- Generando dalla PEC un protocollo;

Per i dettagli della funzionalità richiedere il manuale specifico.
## **Accesso alle funzionalità istanze e movimenti**
E' stata rivista e potenziata la funzionalità che permette di gestire Ruoli legati agli operatori che permettono di gestire o meno i dati dell'istanza o i passaggi (movimenti) del flusso di back-office.
## **Modifica dell'intervento  dell'istanza**
E' stato aggiunto un bottone che permette la modifica dell'intervento dell'istanza, vengono mostrate due colonne che visualizzano le configurazioni relative all'intervento attuale e le configurazioni relative all'intervento nuovo. Le configurazioni sono relative a:

- Documenti
- Endoprocedimenti
- Schede dinamiche
- Ruoli e operatori che hanno accesso all'istanza
- Procedura e movimento di avvio

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.017.png)

Sarà possibile selezionare quali di queste configurazioni lasciare e quali modificare in base al vecchio o al nuovo intervento, alcune configurazioni non potranno essere modificate, saranno gestite nell'istanza dopo la modificazione dell'intervento.

#
# **Versione 2.19**
Marzo 2014
## **MASTER – cancellazioni**
In alcuni territori, ove è presente una comunità di pratica o un tavolo tecnico, è presente una installazione MASTER dove vengono fatte le configurazioni degli ambienti di back-office (attività produttive, edilizia, etc…) che poi vengono dispiegate sugli enti del territorio, in questa versione i record del MASTER e quelli dello SLAVE provenienti dal MASTER non sono cancellabili per evitare disallineamenti.
## **Domanda OnLine – PDF compilabili**
Tra gli allegati da presentare è possibile mettere PDF compilabili, con la nuova funzionalità il flusso logico risulta il seguente:

1. Durante la compilazione della domanda OnLine, in caso di PDF compilabili, sarà possibile scaricare il PDF precompilato con i dati della domanda già inseriti nelle pagine (form) precedenti, ad esempio il PDF compilabile verrà scaricato con il nominativo, l'eventuale azienda, la localizzazione ed il tipo di intervento già copilati;
1. Il richiedente finisce di compilare il PDF, lo salva e lo rimanda al server attraverso l'apposito pulsante;
1. Il PDF compilato può essere firmato digitalmente;
1. Il PDF viene inoltrato assieme agli altri della domanda al back-office;
1. Nel back-office possono essere definite delle mappature che permetteranno di recuperare una qualsiasi informazione presente nel PDF compilabile e di metterla in una scheda dinamica;

Per i dettagli sulla funzionalità richiedere informazioni.

La funzionalità al momento non è presente nella modalità di compilazione di Regione Toscana.
## **Calcolo interessi legali**
Sono state apportate delle correzione al calcolo.
## **Metti alla firma**
E' stata introdotta la funzionalità che permette di mettere alla firma un documento presente negli allegati dei movimenti, la logica è la seguente:

1. Viene preparato il documento / atto;
1. Viene indicato che viene messo alla firma del responsabile (deve essere un operatore di back-office)
1. I documenti da firmare saranno raggruppati in una scrivania virtuale e personale dalla quale sarà possibile firmare più documenti contemporaneamente o non firmare e motivare il diniego; 
1. Il documento torna a chi ha richiesto la firma;

Per i dettagli sulla funzionalità richiedere informazioni.
# **Versione 2.20**
Aprile 2014
## **Integrazioni documentali provenienti da AIDA**
Ad oggi quando il nodo NLA-AIDA processa un'integrazione documentale la gestisce come notifica attività verso il backoffice, il backoffice cerca di individuare tra i movimenti della pratica se ne esiste uno da eseguire con  flag "Front Office Richiedenti" e se non lo trova rilancia un errore.

Nella "Configurazione Regole" è stato aggiunto il parametro **TIPOMOV\_DEFAULT\_SOGG\_ESTERNI** alla regola "**STC**" dove può essere inserito il codice del tipo movimento da utilizzare nelle integrazioni nel caso in cui il controllo precedente fallisca.
## **Gestione fiere**
Nelle  fiere della durata di più giorni c'è ora la possibilità di decidere se i concessionari ottengono una sola presenza per l'intera fiera o se ottengono una presenza per ogni giornata in cui sono presenti.
## **Pannello gestione PEC**
Ora il pannello può essere attivato trasversalmente in modo da leggere le PEC da una casella per creare istanze e movimenti su più moduli.
## **STC – aggiunte info alla visura interna**
Nelle comunicazioni tra backoffice dopo aver notificato una pratica attraverso STC, se il destinatario ha attivo il servizio di visura, è possibile vedere ("computerini") lo stato avanzamento pratica in modo più completo rispetto a prima:

- Richiedente, azienda, responsabile del procedimento, nuova numerazione
- Movimenti effettuati
- Allegati prodotti

Il servizio di visura per default è attivo per tutti i moduli dei backoffice di In.I.T.

Ad esempio la funzionalità permette ad un SUAP di vedere la visura dello stato di avanzamento della pratica edilizia generata con la notifica di un procedimento e viceversa.
## **STC – possibilità di inviare una pratica intera**
Nelle notifiche con STC è ora possibile configurare l'invio per mandare al destinatario l'intera pratica. Nelle versioni precedenti era possibile inviare un solo procedimento.
## **Localizzazioni nella domanda online**
Nella costruzione della presentazione della domanda online è possibile aggiungere più STEP indicanti la localizzazione. Il caso d'uso è stato la configurazione dell' Autorizzazione Unica Ambientale dove vengono imputati:

- La localizzazione dell'impianto produttivo
- La localizzazione degli eventuali impianti di depurazione (con coordinate XY)
- La localizzazione degli eventuali scarichi (con coordinate XY)

Ogni step di localizzazione è associato tramite TAG nell'xml della domanda online ad una tipologia di localizzazione (gestita dalla tabella negli archivi). Nella prima pagina dell'istanza è possibile vedere tutte le localizzazioni inserite in fase di presentazione.
## **Interfaccia utente delle istanze** 
Nella pagina di dettaglio dell'istanza viene evidenziata in verde la sezione "ISTANZE COLLEGATE" nel caso in cui ci siano istanze collegate.
## **Endo-procedimento – Attiva-In**
Il pulsante "ATTIVA IN" presente negli endo-procedimenti di base serve per poter indicare in quali moduli di backoffice l'endo-procedimento è attivabile. Una volta attivato un endo viene proposto in elaborazione il rispettivo movimento,  in questa versione è stata aggiunta la possibilità di rendere facoltativo il movimento.
## **Verifica qualitativa dei documenti**
Nella sezione dei documenti dell'istanza e degli endo-procedimenti è stata potenziata per tenere traccia delle verifiche documentali, per ogni documento è possibile verificare se era richiesto (in base al tipo di intervento") se è presente e se è valido (da verificare, valido, non valido).

Nei documenti tipo la variabile segnaposto "lista dei documenti presenti e non validi" (e le altre variabili relative al controllo documentale) tengono conto delle nuove modifiche.
#
# **Versione 2.21**
Settembre 2014

## **Front end - visualizzazione schede dinamiche in base alla tipologia di localizzazione**
Durante la compilazione della domanda online è stata introdotta la possibilità di associare, a ciascuna localizzazione della pratica, una tipologia (es."Centro storico", "Periferia"), scegliendola da una lista predefinita e modificabile a livello di configurazione. 

E' possibile subordinare la visualizzazione di una scheda dinamica collegata ad un endoprocedimento alla presenza di un localizzazione di un certo tipo. 

## **Master - Permettere la cancellazione dei record MASTER nei comuni SLAVE**
Dal pannello di controllo da amministratore di un'installazione SLAVE è ora possibile attivare una sessione durante la quale è abilitata  la cancellazione di record provenienti dall'installazione MASTER.

Durante la sessione è attivo e chiaramente visibile un messaggio che avvisa l'utente dell'attivazione della modalità e un bottone per disattivarla.

## **Master - Permettere la cancellazione dei record MASTER**
Dal pannello di controllo da amministratore di un'installazione MASTER è ora possibile attivare una sessione durante la quale è abilitata  la cancellazione di record MASTER.

Durante la sessione è attivo e chiaramente visibile un messaggio che avvisa l'utente dell'attivazione della modalità e un bottone per disattivarla.

## **Pannello di protocollazione PEC - Ricerca delle istanze**
All'interno del pannello di protocollazione di una PEC è ora possibile aprire un pop-up per ricercare una determinata pratica e visualizzarne i dati principali. 

La modifica è stata introdotta per venire incontro alle esigenze degli operatori che, ad esempio, hanno necessità di protocollare una integrazione pervenuta per mezzo PEC ma che, dalla PEC stessa, non riescono a estrapolare tutti i dati necessari: ora è possibile effettuare tutte le ricerche senza uscire dal pannello.


## **CART (Regione Toscana) - Possibilità di pubblicare un intervento in modalità CART non proveniente dal catalogo regionale**
Nelle installazioni dove è attiva la verticalizzazione CART è possibile ampliare il set degli interventi (i.e. aggiungere cartelle/foglie all'albero dei procedimenti) e pubblicarli secondo le logiche del CART.

## **Gestione attività - Migliorata la gestione delle attività create con localizzazione/denominazione uguale ad una esistente**
Fino ad oggi, in fase di creazione di un'attività, il sistema effettuava il controllo se esisteva già una o più attività nel medesimo indirizzo o con la stessa denominazione, permettendone l'eventuale forzatura nella creazione.

Ora viene visualizzato un pannello molto più completo che visualizza, in forma di lista, il dettaglio della attività presenti nella stessa localizzazione o con uguale denominazione permettendo:

- Di forzare la creazione dell'attività
- Di collegare l'istanza in esame ad una della attività presenti in lista

## **Azioni protocollo**
E' stata sviluppata una nuova funzionalità, disponibile in tutte le installazioni nelle quali è stato integrato un protocollo informatico, presente a menu (se attivato per l'operatore) sotto la voce "Azioni protocollo" (attivabile sia in un modulo software specifico che negli archivi di base).

La nuova funzionalità permette di ricercare un protocollo, per numero e anno, e di visualizzarne i dettagli messi a disposizione del protocollo informatico in uso nell'ente. Per ciascun protocollo sarà inoltre possibile generare una nuova istanza o un movimento, in maniera del tutto analoga a quanto avviene nel "Pannello PEC", ma con origine dei dati (allegati, anagrafiche, etc.) proveniente dal protocollo invece che dalla PEC. E' lasciata all'operatore la scelta se copiare o meno gli allegati del protocollo nell'istanza/movimento e, in caso di copia, se scompattarli nel caso siano compressi.

## **Modifica del comune di una pratica in installazione multi-comune**
In caso di installazione multi-comune (es. unioni di comuni) è ora possibile, dal pannello di amministrazione, modificare il comune dell'istanza nel caso la localizzazione sia stata effettuata in maniera errata.

## **Allinea documenti**
Nella sezione "DOCUMENTI" di un'istanza è ora presente il pulsante "ALLINEA I DOCUMENTI" (che sostituisce il precedente "AGGIORNA I DOCUMENTI") che permette di aggiornare i documenti presenti nella pratica in esame rispetto a quelli previsti dalla configurazione dell'albero degli interventi.

La pressione del pulsante "ALLINEA I DOCUMENTI" apre una schermata riepilogativa che mostra all'operatore la situazione attuale dei documenti e quella prevista dalla configurazione, guidandolo nella scelta delle modifiche da effettuare.

## **Gestione attività - Ricerca per campo dinamico** 
E' stata potenziata la ricerca di un'attività in base a filtri sui dati dinamici: è ora possibile ricercare quelle per le quali un determinato campo dinamico non è valorizzato (è ora presente il criterio di confronto "è vuoto")

## **Scadenzario - Visualizzazione azienda nella lista dei movimenti da fare**
Nello scadenzario utente, nel pannello "Movimenti da effettuare", è ora mostrata anche l'azienda ed "in qualità di".


## **Pannello Gestione PEC - Funzionalità "Sblocca PEC"**
Viene data la possibilità di sbloccare una PEC in uso da un utente mediante l'apposizione di una password.

## **Pannello Gestione PEC - Verifiche e controlli**
Sono stati implementati controlli più stringenti per evitare l'apertura contemporanea di una PEC da parte di operatori differenti.

## **Pannello Gestione PEC - Visualizzazione colonna "Copia conoscenza"**
Nel pannello di gestione PEC viene ora visualizzata anche la colonna con i richiedenti in copia conoscenza. 

## **Pannello Gestione PEC - Funzionalità "Metti in evidenza"**
Nel pannello di gestione PEC è stata aggiunta la colonna "Evid." che permette di evidenziare le PEC di particolare interesse. 

## **Lista istanze - visualizzazione dell'intermediario**
Nella lista delle istanze è possibile visualizzare anche la colonna "Intermediario"

#
#
# **Versione 2.22**
Ottobre 2014

## **Portocollazione multiente e multisportello**
Fino alla versione 2.21 era possibile integrare un solo protocollo per installazione, sia nel caso di comuni singoli che di installazione multi-ente.

Ora è possibile integrare protocolli differenti per software differenti (SUAP, Commercio, Edilizia, etc..) e, nel caso di installazioni multi-ente, anche per differenti comuni dell'unione.

## **Modifiche alla funzionalità "Metti alla firma"**
E' stata completata la funzionalità di "Metti alla firma" con alcune pagine complementari che ne rendono più semplice e chiaro l'utilizzo:

- Istanze >> [UFFICIO] >> Documenti da firmare: la pagina, analoga a quella presente nello scadenzario, mostra la lista dei documenti da firmare da parte del responsabile del procedimento che ha acceduto al sistema.
- Istanze >> [UFFICIO] >> Documenti messi alla firma: mostra i documenti messi alla firma dall'operatore che ha acceduto al sistema ed il loro stato (stati possibili: da firmare, firmati, firma negata)

Inoltre, quando un documento viene firmato, viene visualizzato un "Evento" (sezione "Eventi non letti") legato al movimento.

## **Nuova "Informazione aggiuntiva" nella sezione "Elaborazione"**
A fianco di ogni movimento viene ora visualizzato il simbolo ![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.018.png) se presenti "Eventi non letti". La selezione del link permette di settare l'evento come "letto" senza necessità di accedere al dettaglio del movimento stesso.

## **Ereditarietà gerarchica della "Tipologia Registro Autorizzativo" associato all'albero degli interventi**
Fino ad ora, quando si registrava una nuova autorizzazione in un'istanza, se la foglia dell'albero degli interventi scelta non aveva una Tipologia di Registro configurata, il campo "Tipologia registro" veniva presentato vuoto.

Ora, per individuare l'eventuale registro da pre-impostare, il sistema risalire la gerarchia dell'albero per verificare che una delle cartelle superiori abbia tale voce configurata. 

## **Modifica alla funzionalità "Servizi di messaggistica"**
Nella sezione "SERVIZIO DI MESSAGGISTICA" della pagina Configurazione >> Frontoffice [UFFICIO] >> Area riservata è possibile impostare l'invio di comunicazioni a determinati soggetti, contestuale al verificarsi di determinati eventi (es. ricevimento di una pratica da Front Office, registrazione di un nuovo utente nell'Area Riservata, etc..).

Sono stati aggiunti i seguenti soggetti ai quali poter inviare le comunicazioni:

- Responsabile del procedimento (se presente)
- Responsabile dell'istruttoria (se presente)
- Operatore
##
## **Versione 2.23**

### ***Modifica pannello gestione PEC, aggiunta funzionalità per sbloccare una pec che risulta cancellata.***
Può succedere che una PEC sul pannello della gestione PEC risulti cancellata anche se questo non è vero. Aggiungere un icona sulla lista delle PEC, solo nel caso che risulti cancellata, che permette di fare un update sulla tabella  PEC\_INBOX e aggiornare il campo "flagCancellata" da 1 a 0.

### ***INTEGRAZIONE CON SISTEMA DOCER***
Integrare le funzionalità previste per l'adeguamento di backoffice alle specifiche DOC-ER.

## **Versione 2.24**
### ***Funzionalità "Riversa pratiche in Dossier"***
Deve essere sviluppata una funzionalità che riversa le pratiche di VBG in Dossier. La funzionalità deve richiamare il WS riversaIstanze. 

La funzionalità deve essere richiamata dal menù.

Salvataggio informazioni di dettaglio dell' istanza durante l'eliminazione di una pratica.

Durante l'eliminazione di una pratica si deve andare a popolare la tabella ISTANZEDELETE con i seguenti campi:

1- Codiceistanza

2- idcomune

3- software

4- datacancellazione

5- campi\_istanza

Il valore di \*campi\_istanza\* sarà una stringa che dovrà essere popolata concatenando:

[comune]-[NumeroIstanza]-[Codice pratica telematica]-[operatore]-[richiedente]-[In qualità di]-[Ragione sociale]-[intermediario]-

[Data presentazione]-[Data protocollo]-[Numero protocollo]-[intervento]-[Oggetto della pratica]

### ***Aggiungere log audit per cancellazione record domande\_stc***

Aggiungere alla cancellazione dei record su domande\_stc la logica utilizzata per l'eliminazione dell'istanza. 

Possibilità di introdurre la cancellazione tramite password e loggare l'operazione tramite i log audit.

E' capitato a Firenze di avere una segnalazione di una pratica inviata con errore, ma non c'era traccia di questa sul backoffice, ma era presente su STC, come se fosse stata cancellata manualmente da interfaccia web, ma questo non poteva essere verificato.

## **Versione 2.25**
### ***VALIDAZIONE SIT All'inserimento pratica da STC***
All'inserimento pratica da STC non viene correttamente salvate le informazioni relative al SIT.

### ***Interfaccia di protocollazione: caricamento dell'amministrazione di default***
Ad oggi la maschera di protocollazione precarica l'amministrazione secondo questo flusso:

A) cerca le amministrazioni abilitate per l'operatore e se trovata una per quell'operatore con prot\_uo o prot\_ruolo (TABELLA AMM\_PROTOCOLLO) settato allora sceglie questa

B) se non è stata trovata la cerca tra le verticalizzazioni.

Sarà modificata in questo modo:

A) cerca le amministrazioni abilitate per l'operatore e se trovata una per quell'operatore con prot\_uo o prot\_ruolo (TABELLA AMM\_PROTOCOLLO) settato allora sceglie questa

A.1) se non trovata e provenienza da ISTANZA o MOVIMENTO la cerca sulla tabella ALBEROPROC\_PROTOCOLLO

B) se non è stata trovata la cerca tra le verticalizzazioni.

### ***ALLEGATI PEC***
Funzionalità di base:

1. Durante l'invio di una PEC da VBG potrà essere indicato se gli allegati dovranno essere mandati in attach o come riferimento (link).'La scelta (flag) potrà essere pre-impostata in base alla configurazione del movimento di VBG (o meglio in base all'amministrazione coinvolta nel movimento);

2. Se verrà configurato l'invio dei riferimenti (link) sarà presente nel corpo della mail tipo un segnaposto che verrà sostituito, prima dell'invio, dalla lista dei documenti selezionati come da inviare.

Sarà possibile anche generare un documento contenente i link dei documenti selezionati come da mandare (andrà configurata la lettera tipo con il template del documento contenente il segnaposto per la lista dei link);

3. I link della mail (o del documento allegato), una volta ricevuta la mail, saranno cliccabili (o visualizzabili facendo copia incolla in un browser), all'interno del link MD5 del file in modo che sia univoco e che sia praticamente impossibile generarne uno a caso;

4. Prima di visualizzare il file verrà richiesto un PIN anche esso riportato nella mail di fianco al nome file ed al link;

5. L'MD5 del file verrà calcolato al momento dell'inserimento del file in VBG e/o al momento dell'invio della mail per i file caricati precedentemente al dispiegamento della nuova funzionalità

Funzionalità estensive:

6. Ove le installazioni prevedano che l'invio della PEC venga fatta dal protocollo occorre che durante la fase di protocollazione si possa generare un documento contenente i link dei documenti che devono essere inviati e che questo documento sia allegato al protocollo. In configurazione di un movimento (o nell'amministrazione) deve essere possibile indicare il template del documento che conterrà il segnaposto da sostituire con i link.

7. L'interfaccia html di protocollazione può prevedere la creazione del documento con i link anche in mancanza di una configurazione a monte.


### ***Gestione Endoprocedimenti legati all'albero degli interventi***
Occorre prevedere un check che permetterà di inserire un endoprocedimento nell'istanza in fase di presentazione domanda anche se non è tra quelli pervenuti da on-line.

Il nuovo check deve valere sempre anche se l'istanza viene inserita tramite PEC o a mano.

Il check è già spuntato e non modificabile se l'endoprocedimento è principale o proposto.

Il nome sulla colonna può essere Usa nel back (?)="se spuntato l'endo-procedimento verrà automaticamente inserito durante la creazione della pratica nel back-office qualsiasi sia la sua provenienza (on-line, PEC, cartaceo)"

Oltre alla gestione del nuovo flag è necessario:

- provvedere all'upgr del campo (ALBEROPROC\_ENDO) mettendo a 1 dove proposto o principale sono uguali a 1

- all'inserimento della pratica se presenti nella configurazione di alberoporc\_endo inserire le voci con questo flag impostato a 1.

- durante l'inserimento manuale gli endo che hanno questo flag impostato a 1 devono essere readonly 

### ***INTEGRAZIONE con sistema INFODURC***
Funzionalità

- Validazione di un DURC esistente

- Richiesta di un nuovo DURC

-Recupero del DURC richiesto dalla casella PEC- (sarà implementato in futuro in quanto al momento non abbiamo indicazioni valide per capire il meccanismo di riconciliazione della PEC)


## **Versione 2.26**


### ***Gestione delle attività: Integrazione di informazioni su pagina di lista***
Nella pagina dei risultati di una ricerca delle attività vanno mostrate le seguenti informazioni:

- la colonna posizione archivio presente nell'istanza principale dell'attività

- delle icone che permettano di visualizzare

àla lista degli stradari (vedi figura 1)

à il contenuto delle schede dinamiche dell'attività (vedi figura 2)

### ***Aumentare dimensione del campo INVENTARIOPROCEDIMENTI.PROCEDIMENTO***
La dimensione deve essere portata a 500 caratteri

### ***Salvataggio dei movimenti. Salvare anche l'ora di esecuzione***
Modificare il campo MOVIMENTI.DATAINSERIMENTO in modo che possa accettare l'orario corrente.

Nell'interfaccia far visualizzare anche l'orario

Scadenzario: mostrare l'azienda nelle schermate "Documenti da firmare"

Nella maschera  "Documenti da firmare" non c'è il campo richiedente.

Gestirlo come nella maschera movimenti da effettuare ossia "Richiedente in qualità di".


## **Versione 2.28**
### ***PROTOCOLLAZIONE: Nuovo metodo fascicolaXml da richiamare su protocolla da PEC***
- Generare lo stub con il nuovo metodo

- gestire la chiamata per il nuovo metodo in protocolla se provenienza <> istanza e movimento

### ***Estrapolare l'elenco dei dati relativi alle ATTIVITA'***

La query che estrae le informazioni delle attività deve estrarre anche le mail/pec di 

Richiedente (Email, PEC), Azienda (Email, PEC), domicilio elettronico dell'istanza.

I dati vanno aggiunti solamente nell'esportazione jmesa



### ***Funzionalità lista documenti messi alla firma: modifica all'interfaccia***
Mettere su una preferenza la scelta dei filtri o implementare una ricerca minimale.

La soluzione di ricordare il filtro non è buono in quanto a loro serve di verificare anche le firme appena fatte o negate.

Per ora sarebbe già sufficiente invertire l'ordine: prima la data più prossima e poi a scendere.

### ***Interfaccia di protocollazione: FLUSSO DI DEFAULT in caso di protocollazione da movimento***
Nel metodo create se provenienza = movimento e l'operatore ha tra i suoi flussi attivi PARTENZA allora sarà quello impostato come default.

Verificare se utilizzare nuova verticalizzazione.

"Attualmente se dal gestionale provo a protocollare un movimento, il sistema propone come flusso "ARRIVO"; 

Vorrei che il sistema invece proponesse "PARTENZA" come default in quanto la maggior parte dei movimenti protocollati dall'operatore saranno movimenti in partenza."

## **Versione 2.29**

### ***NUOVO WEB SERVICE INSERIMENTO ONERI***
.NET non ha più codice per inserire oneri ed è necessara una porta applicativa per inserire gli oneri.

Il metodo Ws inserimento onere accetterà in input:

- token 1..1

- riferimentoIstanza 1..1

- importo 1..1 > 0

- riferimentoCausale 1..1

- riferimentoEndoprocedimento 0..1

- note 0..1

- codiceresponsabile 0..1

- datascadenza 0..1

- ModalitaPagamentoType 0..1

- datapagamento 0..1

- importopagato 0..1

### ***Aggiungere la gestione del comune di una localizzazione***
E' possibile che esista un installazione di tipo singolo comune che abbia però lo stradario di più comuni ( caso delle fattorie didattiche con un'installazione regionale che contiene gli stradari di tutti i comuni).

Occorre quindi aggiungere un modo per filtrare le vie in base al comune. 


### ***Data e ora inizio e fine validità intervento***
E'necessario fare in modo che un intervento abbia una data di inizio e fine validità al di fuori della quale l'intervento non sia presentabile nel frontoffice.

La compilazione dei due campi nella funzionalità albero degli interventi diventa obbligatoria nel momento in cui almeno uno dei due sia stato compilato.

La data di validità di un intervento va a sovrascrivere l'eventuale data specificata in un intervento padre. Per questa ragione nel caso in cui un intervento più in alto nell'alberatura definisse un intervallo di validità sarebbe il caso di mostrarlo a fianco dei campi di immissione (magari con un link che permettesse di saltare all'intervento in cui è stato definito).
### ***Export colonne su commissione edilizia***
Sulla lista delle istanze della commissione edilizia vengono visualizzate delle colonne che non vengono esportate: Parere.

Con l'occasione visualizzare anche la colonna comune nel caso di installazione comuni associati.
###
### ***Salvataggio metadati su sistema documentale CMIS***
Se il sistema è integrato con un sistema CMIS ( ad oggi Alfresco ) le informazioni salvate su oggetti metadati andranno a popolare le metainformazioni del sistema documentale.

MODIFICHE DB:

Nuova tabella 

CFG\_METADATI\_CMIS (la tabella indica quali metadati vengono gestiti per installazione)

- IDCOMUNE

- FK\_METADATO\_BASE

- MAPPING\_CMIS

La colonna MAPPING\_CMIS riporta l'identificativo dell'attributo nel sistema CCMIS eventualmente integrato: esempio vbg:numeroPratica. Questo serve per definire quali metadati inviare (nel caso il comune ne gestisca solamente alcuni).

Nuovo parametro di verticalizzazione 

FILESYSTEM\_CMIS.TIPO\_DOCUMENTO\_CMIS

ci va salvato l'object\_type\_id se diverso da cmis:document è importante perché ogni ente potrebbe avere il proprio namespace

Nuovo parametro di verticalizzazione 

FILESYSTEM\_CMIS.GESTISCI\_METADATI

se vanno impostate le funzionalità di salvataggio dei metadati (basterebbe un query su tabella CFG\_METADATI\_CMIS)

salvataggio metadati documenti istanza

Salvare le informazioni sulla tabella oggetti\_metadati a seconda del contesto:

se presenti dei METADATI FUNZIONE  allora sulla tabella metadati verranno salvati le informazioni relativi a quanto configurato in METADATI\_DIZ\_BASE.

I METADATI FUNZIONE sono: 

#CREA\_MD\_ISTANZA#

#CREA\_MD\_MOVIMENTO#

#CREA\_MD\_ENDO#

#CREA\_MD\_ARCHIVI#

Questi metadati non verranno salvati nella tabella oggetti\_metadati ma la loro presenza attiverà una funzione di recupero metadati (METADATI\_DIZ\_BASE  recuperati per contesto + valore del metadato [codiceistanza, codicemovimento]) e relativo inserimento.

MODIFICHE DB:

tabella METADATI\_DIZ\_BASE nuova colonna Contesto:

I: Istanze

M: Movimenti

P: Istanzeprocedimenti

A: Archivi

Se un metadato è già presente non andrà modificato/sovrascritto: Se una pratica viene dal SUAP avrà come riferimento quella pratica (anche se viene notificata all'edilizi e potrebbe avere anche questi riferimenti)

### ***GESTIONE METADATI DEI DOCUMENTI SU SISTEMA CMIS(ALFRESCO)***
La funzionalità permette di salvare una serie di metadati sui documenti salvati sulla tabella oggetti.

Nel caso che il backoffice sia integrato con sistemi CMIS (Ad oggi Alfresco) la funzionalità permetterà, al salvataggio degli oggetti, di inviare al sistema documentale il corredo di metadati

Miglioramento della funzionalità metti alla firma

attualmente la funzionalità "metti alla firma" risulta di difficile uso perché coinvolge diversi passaggi:

1) selezione dei file

2) accesso alla maschera (A) dove vengono presentate le note

3) uso del bottone di firma

4) interfaccia di firma (B)

5) Al termine della firma rientro nella maschera di firma (A) salvataggio dell'avvenuta firma, con indicazione di eventuali annotazioni

Questi passaggi vanno semplificati unificando i passaggi 2,3,4,5



### ***SALVATAGGIO INFORMAZIONI OPERATORE CHE HA MODIFICATO LE ANAGRAFICHE***
Inserire l'operatore operatore ha creato la riga di storico
##
## **Versione 2.30**
### ***PRATICHE COLLEGATE: Possibilità di copiare dati localizzazione dalle istanze collegate***
All'interno della localizzazione deve essere presente una funzionalità che permette di copiare i dati della localizzazione da istanze collegate, se esistono.

La funzionalità deve seguire la logica utilizzata per copiare i soggetti collegati.

### ***MASCHERA DI PROTOCOLLAZIONE, INVIOMAIL, STC***
LA MODIFICA VA FATTA SU LISTA DEGLI ALLEGATI:

A) GLI ALLEGATI DEL MOVIMENTO DEVONO RIPORTARE LA DESCRIZIONE DELLA COLONNA MOVIMENTI.MOVIMENTO (ORA RIPORTA MOVIMENTI.TIPIMOVIMENTO.MOVIMENTO)

B) DEVE ESSERE RIPORTATA ANCHE LA DATA DEL MOVIMENTO (OGGI E' PRESENTE SOLAMENTE LA DATA DI PROTOCOLLAZIONE)

### ***Protocollo Movimento: Aggiungere e gestire configurazione dell'oggetto del protocollo da movimento.***
Deve essere implementata una funzionalità che permette di personalizzare l'oggetto del protocollo di un movimento per il tipo movimento scelto. Fino ad ora l'oggetto veniva creato a partire da una lettera tipo salvata in configurazione. Ora il sistema dovrà prima verificare se esiste la configurazione sul tipo movimento e se non trovata prendere quella in configurazione.
###
### ***Collegamento istanze : Al collegamento di un istanza all'altra associare, se esiste, anche l'attività***

Oltre al normale collegamento delle istanze, se presente va fatto anche il collegamento tra le attività

### ***Scadenzario: Ordinamento delle domande STC per campo data***
Dare la possibilità sullo dello scadenzario relativamente al pannello "NUOVE DOMANDE STC" di ordinare per la colonna data.


### ***Funzionalità di pannello PEC: INVIARE LE INFORMAZIONI DELLO STRADARIO***
Quando si crea una pratica dal pannello PEC vanno inviate anche le informazioni dello stradario.

Non va portato il codice per il collegamento con il SIT.

### ***INSERIMENTO PRATICA STC: recupero dati da file excel***
Quando nella domanda sono allegati dei file excel va prevista una logica che permette di recuperare le informazioni di alcune celle ed inserite in schede dinamiche.

I file excel potrebbero essere firmati (nel caso devono essere verificati col dss-webapp e recuperato il file in chiaro da processare).

La logica va applicata se nelle mappature frontend sono presenti dei record che iniziano con un segnaposto che dobbiamo stabilire (es: #XLS#).

Se presenti allora il blocco di codice va attivato. Nelle diverse mappature saranno inserite le coordinate delle celle da recuperare (es: #XLS#Foglio 1!A4 ).

Per ogni file excel va creata un oggetto scheda type che va con i valori recuperati dalle mappature.

La scheda va poi attaccata all'oggetto schede di inserimentopraticarequest che verrà elaborata al momento dell'applicazione delle mappature.

### ***MAIL TIPO NUOVO SEGNAPOSTO [LOCALIZZAZIONE\_VIA]***
vogliono un segnaposto per le MailTipo (class MailtipoServiceImpl) che riporti solamente

i campi STRADARIO.PREFISSO + ' '  + STRADARIO.DESCRIZIONE. 

Verificare che il prefisso non sia vuoto altrimenti riportare solamente la descrizione.

il segnaposto sarà chiamato 

[LOCALIZZAZIONE\_VIA] nella jsp riportare l'help che indica che verrannno riportati solamente prefisso e localizzazione dello stradario
### ***Livelli di autenticazione utente: modifiche backoffice***
Modifiche DB:

Aggiungere e gestire in ALBEROPROC una colonna che contiene il valore.

La colonna deve essere di tipo numerico.

Per mostrare a video il valore usare l'enumeration della security o farne una collegata.

### ***Chiusura automatica dell'Istanza***
Prevedere un meccanismo di chiusura automatica delle pratiche.

I requisiti richiesti sono:

- una volta scaduti i tempi previsti da una procedura l'istanza deve essere chiusa;

- è necessario indicare lo stato di chiusura di default.

### ***Gestione dell'attività schedulata:***
è già presente e sviluppato un meccanismo che lancia dei JOB schedulati (sviluppato per archiviazioni documentali, e allineamento stradario). Va creato un job specifico nel package (it.gruppoinit.pal.gp.core.jobs) che possa venir configurato e lanciato il corpo del job esegue la funzionalità di istanzeService descritta nella logica di chiusura istanza.


### ***MASCHERA DI INVIO ALLEGATI STC, MAIL, PROTOCOLLO: Aggiungere i documenti delle anagrafiche, impresa, tecnico, soggetti collegati***
nelle maschere di invio mail (quindi STC e protocollazione) non sono presenti gli allegati delle altre anagrafiche (es azienda, tecnico, soggetti collegati).

Fare in modo di farli comparire come per il richiedente.

Mettiamo anche i documenti (ricevute di pagamento) allegate agli oneri.

### ***Modifica Lista storico attività***
Si richiede che nella maschera di storico delle attività venga visualizzato, per ogni colonna, l'intervento da albero CF, in modo che, visivamente si possa meglio comprendere l'evoluzione dell'attività.

Inoltre, il campo Tipologia attività, potrebbe essere messo al di fuori della Tabella (sotto il nome dell'attività) perché tanto è uguale per tutte le colonne.
##
## **Versione 2.31**
### ***Implementazioni funzionalità addDocumento nella maschera leggi protocollo***
Si deve implementare una funzionalità che permetta di all'interno della maschera leggi protocollo di aggiungere dei documenti al protocollo.

I documenti devono essere la lista che viene presentata quando si effettua una protocollazione.



### ***CANCELLAZIONI DI MOVIMENTI: proporre la mascherina di assunzione responsabilità***

Proporre la mascherina di assunzione di responsabilità se movimento ha documenti o mail allegate.

In caso di richiesta di cancellazione di movimenti con documenti e mail far apparire l'avviso di conferma cancellazione con assunzione di responsabilità.

### ***INSERIMENTO PRATICA: Popolamento automatico campo oggetto pratica***
All'inserimento pratica se il campo oggetto della pratica è vuoto di default ci va inserito il tipo intervento.

Questo deve essere un comportamento legato ad un flag dell'intervento che spiega che durante l'inserimento della pratica se l'oggetto è vuoto ci viene scritta la voce dell'intervento scelto.

### ***SCADENZARIO: personalizzazione colonne da visualizzare***

` `Visualizzare la colonna procedura

` `Visualizzare posizione in archivio

Dare la possibilità di personalizzare le colonne che l'operatore vuole visualizzare

## **Versione 2.32**
### ***Modifiche su allineamento stradario automatico tramite la webapp wssit***
L'allineamento dello stradario schedulato deve essere invocato tramite il job scheduler.

### ***GESTIONE DEI TIPISOGGETTO NELLA PAGINA PRINCIPALE DELL'ISTANZA***
Nella pagina principale dell'istanza va prevista la possibilità di mostrare dopo il campo dell'intermediario anche una sezione dei soggetti collegati.

MODIFICHE INTERFACCIA

Nella pagina di view dell'istanza mostrare una sezione con la lista dei tipisoggetto che hanno il flag impostato.

Deve essere possibile modificare i dati / aggiungerli. se tra i tipi soggetto del software ce n'è almeno uno con il flag impostato va mostrata la sezione con il pulsante aggiungi (+).

### ***GESTIONE DELLE LABEL DA DB***
Abilitare la gestione delle Label da DB.

(flag su operatore che può abilitare le label o su pannello di amministrazione pulsante che abilita la gestione per una sessione).

### ***MAIL TIPO: SEGNAPOSTO responsabile istruttoria***
Sulla pagina di configurazione delle mail tipo aggiungere sulla sezione dell'istanza un segnaposto per il responsabile dell'istruttoria.

Il segnaposto va gestito nel service che fa le sostituzioni.

Gestione del flag pubblica su TABELLE ENDOPROCEDIMENTI, FAMIGLIEENDO, TIPIENDO

Aggiungere FlagPubblica:

- FAMIGLIE ENDO

- TIPIENDO

- INVENTARIOPROCEDIMENTI


### ***METTI ALLA FIRMA: possibilità di inserire in automatico un movimento a seguito dell'evento firma***

L'esigenza di inviare in automatico la mail/pec al domicilio elettronico della pratica – se presente - dopo l'evento di firma del documento.

La mail inviata viene legata al movimento che ha generato dove è salvato il movimento firmato.

La configurazione va fatta per singolo movimento.

C'è un bottone ![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.019.png) che permette di accedere alla pagina di configurazione di questa attività

![](./immagini/Aspose.Words.75b679ed-c90e-4de6-b110-c04d65d511eb.020.png)

Dove viene permesso di scegliere la lettera tipo template da inviare.


### ***ALBEROPROCEDIMENTI: TIPISOGGETTO PREVISTI NEL BACKOFFICE***
Al termine dell'inserimento di una pratica se tra i soggetti collegati non è presente un tipo soggetto ben definito va generato uno warning.


MODIFICHE INTERFACCIA

nella gestione dell'albero dei procedimenti una nuova sezione:

"Tipisoggetto necessari nel back"che gestisce l'inserimento di un tipo soggetto

Nella pagina di visualizzazione dell'istanza va messo uno warningn (ES: <h3>Attenzione!! Tra i soggetti collegati dell'istanza non è presente nessuno di tipo "Procuratore"</h3> ). Questa operazione farà in modo di rendere visibile il messaggio agli operatori dell'istanza.


### ***MOVIMENTI: creazione di un movimento con i riferimenti del protocollo dell'istanza.***
Nella configurazione di un tipomovimento aggiungere un flag che indica "Riporta il numero di protocollo della pratica".

Se il flag è spuntato allora durante l'elaborazione i campi numero protocollo / data protocollo / idprotocollo (se presenti) vanno popolati con i riferimenti della pratica.

### ***NOTIFICA STC AUTOMATICA: ALLEGARE I DOCUMENTI DI UN ALTRO MOVIMENTO***
Nel caso delle notifiche in automatico bisogna provvedere ad un meccanismo che permetta di allegare i documenti di un movimento dell'istanza che non sia quello che si va a notificare.

Configurare un altro dato che serve a attivare questo tipo di logica.

Il dato potrebbe essere $NOT.STC.ALLEGADOC.MOV$ che accetta come valori o il tipo movimento Es: "SS0001" oppure il valore MOVPADRE.

Nel primo caso:

- si fa una ricerca nei movimenti dell'istanza con quel tipo movimento. 

- si prende l'ultimo in ordine cronologico, e per ordine

- si trovano i doc del movimento e si allegano alla notifica automatica

Nel secondo caso:

- si prende dalla tabella movimenti\_contromovimenti il contromovimento padre.

- si recuperano gli allegati

- si allegano alla notifica automatica


### ***DOCUMENTI ISTANZA: Mostrare il numero protocollo dei movimenti***
Nella pagina di visualizzazione dei documenti dell'istanza va mostrato il numero di protocollo/data del movimento cui l'allegato fa riferimento.

Va mostrato anche il numero protocollo/data dell'istanza (se presente)

### ***METTI ALLA FIRMA***
Nella funzionalità ‘metti alla firma' come destinatari della richiesta non vengono visualizzati solo i responsabili del procedimento ma tutti gli operatori configurati in SIGePro.

La ricerca dei responsabili presente nella pagina con la label "Responsabile firmatario" sarà filtrata inizialmente per quelli configurati come tipologia di operatore "Responsabile del procedimento". 

Sarà presente a fianco della ricerca del campo una checkbox che permette la ricerca di tutti i responsabili.

### ***Scheda anagrafica: in modifica mettere in readonly il campo CodiceFiscale e partita IVA se presenti.***
Succede che dalla maschera di backoffice sia possibile modificare i dati sensibili quali nome/cognome codice fiscale.

E' possibile quindi per un anagrafe ROSSI MARIO RSSMRA... sovrascriverla facendola diventare VERDI GIUSEPPE VRDGSP.....

In caso di modifica dell'anagrafe da interfaccia quindi i campi codice fiscale / partita iva saranno visualizzati come readonly (LABEL) se valorizzati. 

A fianco del campo sarà messo un bottone abilita che permette di editare il campo corrispettivo.

Sarà presente la dicitura "L'operazione sarà riportata nei log"

### ***Smistamento multiplo protocollo con Flusso  "Interno" E "Arrivo"***
Deve essere implementata la possibilità effettuare lo smistamento multiplo all'interno di un protocollo con flusso Interno e Arrivo.

Smistamento multiplo significa poter impostare più destinatari.

Modifiche:

1. Aggiungere un parametro in PROTOCOLLO\_ATTIVO : IS\_SMISTAMENTO\_MULTIPLO, se 1 attiva nuova modalità, 0 o null mantiene vecchio comportamento

2. Modificare l'interfaccia : se IS\_SMISTAMENTO\_MULTIPLO=1 al deve essere presentata una maschera del protocollo che permette di inserire più destinatatri. La lista dei destinatari ammissibili sono le amministrazioni interne.

Un' amministrazione è interna  esiste un record di \*amministrazione\* collegato \*amministrProtocollo\* ed è popolato almeno uno dei campi:

a. protUo;

b. protRuolo;

La ricerca del record su amministrProtocollo deve essere fatta per amministrazione, comune e software

### ***Visualizzazione dei tab delle funzionalità dell'istanza***
Così come avviene per i soggetti collegati vanno colorati i tab per le altre funzionalità.

verificare se possibile farlo con chiamate ajax dopo il caricamento della pagina delle istanze.

### ***ALL'ELIMINAZIONE DEL MOVIMENTO NON FUNZIONA LA PASSWORD CANCELLAZIONI***
La verticalizzazione gest\_cancellazioni definisce una password per la cancellazione dell'istanza.

Verificare se l a password cancellazioni possa essere richiesta nel blocco dell'assunzione di responsabilità ( magari con chiamata ajax per la verifica).

## **Versione 2.33**
### ***Ricerca pratiche per data protocollo***
Nella maschera di ricerca istanze si richiede di inserire i campi di ricerca dalla data : alla data:  per il campo data protocollo. In automatico quando si inserisce il campo dalla data deve popolare il campo alla data con lo stesso valore


## **Versione 2.34**
### ***RICERCA ISTANZE - implementazione nuovi filtri di ricerca***
DATA PROTOCOLLO: MODIFICARE DA SINGOLA DATA A RANGE (DALLA DATA ALLA DATA)

` `RICERCA PER RANGE CIVICI.( DAL CIVICO AL CIVICO )

### ***LISTA ISTANZE: Colonna per il link a documenti istanza***
Nella lista delle istanze aggiungere una colonna che serve per raggiungere la funzionalità documenti istanza.

La colonna deve essere simile a quella dell'elaborazione, come etichetta avrà D con testo alternativo "Documenti istanza".

### ***LISTA ISTANZE: esportazione dei codici su xls*** 
Su lista istanze esportazione, esportare il codicestradario e il codiceviario dello stradario primario, il codiceanagrafe richiedente, codiceanagrafetecnico, codiceanagrafeazienda


## **Versione 2.35**
###
### ***PROTOCOLLAZIONE: in caso di passaggio di domanda STC da Ente a Ente il mittente della protocollazione deve essere l'ente mittente***
In caso di passaggio di domanda STC da Ente a Ente il mittente della protocollazione deve essere l'ente mittente.

Attualmente è il mittente della pratica.

Segnalazione pervenuta da Cesena dove lo sportello SUAP associato invia la pratica al commercio. Allo sportello commercio risulta come mittente il richiedente mentre dovrebbe essere lo sportello associato



## **Versione 2.36**

### ***FUNZIONE DOWNLOAD ALLEGATI SENZA PIN***
La funzionalità ALLEGATI\_PEC viene modificata permettendo mediante un parametro di verticalizzazione di generare i LINK senza dover inputare un PIN e quindi scaricandoli direttamente.
###
### ***DOWNLOAD DEGLI ALLEGATI DELLAPRATICA COME ZIP***
Dalla pagina dei documenti dell'istanza aggiungere una nuova funzionalità che permette di fare il download dei documenti in un archivio zip

### ***PECINBOX: visualizzare la colonna A che visualizza i destinatari della mail***
"mi segnalano che sarebbe molto importante nella visualzzazione delle pec ( da pannello pec) avere la possibilità di vedere anche i destinatari della pec . Viene mostrato  il mittente, i destinatari in cc ma non viene mostrato  i destinatari  "a" presupponendo forse che l'unico destinatario  sia  l'indirizzo pec  ( apribile da pannello pec ). in realtà spessissimo i mittenti mandano la pec oltre al suap ( indirizzo usato da pannello pec ) anche ad altri enti ( per esempio Arpa )."

### ***MODIFICA DOCUMENTI E FIRMA SOSTITUZIONE APPLETS CON TECNOLOGIA JWS***
A causa del mancato supporto dei maggiori browser alla tecnologia applet si è reso necessario modificare i componenti di firma / modifica documenti sostituendo le applets con applicazioni JWS.

Per le modalità d'uso si veda i documenti allegati

## **Versione 2.38**
### ***SOSTITUZIONE DOCUMENTALE DA FRONTOFFICE***
Serve per inoltrare al back una serie di documenti che vanno in sostituzione di altri già presentati e che l'utente intende sostituire.

"In fase di integrazione delle pratiche gli utenti di front-office dovranno avere la possibilità di specificare per ciascun allegato se sia possibile definire se esso costituisce l'integrazione di allegati della pratica già presentata".

Possono essere sostituiti solamente i documenti dell'istanza ( tabella DOCUMENTIISTANZA ) o degli endo ( tabella ISTANZEALLEGATI) e NON gli allegati dei movimenti (MOVIMENTIALLEGATI).

La funzionalità è simile a quella dell'integrazione documentale ma con un passaggio in più dove l'utente indica quali documenti sostituire. La funzionalità viene attivata dalla configurazione del tipo movimento in modo tale da poterla attivare per ogni flusso e non sempre

## **Versione 2.40**
### ***MAILTIPO, DOCUMENTI TIPO: nuovi segnaposto per amministrazione***
Sono stati implementati i nuovi segnaposto delle mail tipo e documenti tipo per recuperare i valori dell'amministrazione specificati dal codice **InsCod**

[-AMMINISTRAZIONE(InsCod)-] --> riporta il nome

[-AMMINISTRAZIONE\_PEC(InsCod)-] --> il campo PEC

[-AMMINISTRAZIONE\_MAIL(InsCod)-] --> il campo MAIL

[-AMMINISTRAZIONE\_PIVA(InsCod)-] --> riporta il CAMPO PARTITA IVA

[-AMMINISTRAZIONE\_REFERENTE(InsCod)-] --> IL CAMPO REFERENTE

### ***FUNZIONALITA' COMUNICAZIONI DEI BANDI: possibilità di convertire direttamente in PDF la comunicazione***
Quando si crea una nuova comunicazione delle graduatorie viene data la possibilità, mediante apposito flag, di convertire direttamente gli allegati generati in PDF.

### ***FUNZIONALITA' METTI ALLA FIRMA: possibilità di modifica del documento se rtf o odt o doc. Generazione contestuale del PDF***
Nella funzionalità metti alla firma se il documento ha estensione rtf o odt o doc viene data la possibilità di poterlo modificare. Inoltre c'è la possibilità di poterlo convertire direttamente in PDF.
### ***TIPOLOGIA REGISTRO ATTIVABILE PER COMUNE***
IN AMBIENTE MULTICOMUNE DEVE ESSER DATA LA POSSIBILITA' di specificare un registro per comune.

- aggiungere la colonna codicecomune per registro (può essere nulla ed in questo caso vale per tutti i comuni);
- gestire la chiave esterna e l'interfaccia per la tabella tipologia registri.

in fase di rilascio autorizzazione / concessione la lista dei registri dovrà essere filtrata per comune dell'istanza o comune nullo
### ***DOMANDE IN SOSPESO DELL'AREA RISERVATA***
Nuovo menù domande in sospeso che permette di visualizzare il conteggio e la lista delle domande in compilazione.

` `Il menù è attivabile sul percorso **Frontoffice--><modulo software>-->domande frontend** **in compilazione**
### ***UPDLOAD MULTIPLO SU DOCUMENTI ISTANZA, ISTANZE ALLEGATI, MOVIMENTI ALLEGATI***
Sulla sezione di upload di un allegato dare la possibilita' di caricare piu' file.

il risultato sara' piu allegati con la stessa descrizione del documento con suffisso [nn] che e' il progressivo del caricamento es [01]

la funzionalità sarà presente in documenti istanza, istanzeallegati, movimenti allegati e sarà presentata in fase di creazione di un nuovo allegato, documento con la dicitura **caricamento multiplo**.
### ***GESTIONE VISUALIZZAZIONE STORICO DOCUMENTI***
Nel caso di oggetto storicizzato mediante la funzionalità di sostituzione documentale deve essere data la possibilità di visualizzare lo storico delle modifiche.

1. gestire nella snippet di oggetti la possibilità di vedere lo storico di un oggetto e dare la possibilità di scaricare il documento storicizzato

2. gestire nella snippet di oggetti nelle liste la possibilità di vedere lo storico di un oggetto e dare la possibilità di scaricare il documento storicizzato
### ***POSSIBILITÀ DI DISABILITARE LA NOTIFICA STC DIRETTAMENTE DA ELABORAZIONE O DALL'INTERNO DEL MOVIMENTO.***
Attualmente per disabilitare la notifica STC si opera dallo scadenzario sul TAB movimenti non notificati. Ci viene richiesto (Trieste) di poterlo fare direttamente dall'elaborazione e dalla schermata del movimento.
### ***PROTOCOLLO PADOC NOTIFICA STC A SEGUITO DI AVVENUTA PROTOCOLLAZIONE***
A seguito di protocollazione da parte di padoc se configurato un parametro di verticalizzazione va inviato una notifica stc verso il backend.

Mettere un parametro in vert P@DOC MOV\_DA\_EFFETTUARE\_QUANDO\_PROT\_ISTANZA (si configurerà per il solo SUAP)

- All'evento di avvenuta protocollazione la componente java che colloquia con P@DOC effettuerà il movimento MOV\_DA\_EFFETTUARE\_QUANDO\_PROT\_ISTANZA tramite STC

- Va trasformato il componente java che colloquia con P@DOC in un NLA (client STC)

- Nel back va configurata la notifica della ricevuta da inviare all'utente

- Potrebbe essere necessario scatenare la notifica solo in presenza del protocollo istanza (aggiungere un flag nella configurazione delle notifiche FLAG\_NOTIFICA\_SOLOSE\_ISTANZA\_PROTOCOLLATA)
### ***INVIO MAIL TRAMITE NLA MAIL SERVICE: AGGIUNTO ALTRO DATO PER VERIFICARE CHE L'ISTANZA SIA PROTOCOLLATA***

Gestito un nuovo segnaposto ( $MAILTIPO\_CHECK\_PROT\_IST$ ) da aggiungere agli altri dati della notifica per la verifica di protocollazione dell'istanza in caso di notifica tramite mail service - invio mail.

Il valore del segnaposto DEVE essere impostato a **S** per poter funzionare.
## **Versione 2.41**
### ***INTEGRAZIONE PROTOCOLLO DOCER: Modalità Multiente***
Il backoffice è in grado, in questo momento, di integrarsi solamente con un solo ente di DocEr.

La problematica dell'ente è principalmente quella di poter protocollare su più enti di DocEr, oltre tutto questo dovrebbe avvenire in situazioni di Backoffice differenti, ossia, le comunicazioni in arrivo, quindi la creazione

delle istanze devono essere protocollate sull'ambiente dell'Unione di DocEr / Protocollo, mentre le comunicazioni in partenza, quindi le protocollazioni dei movimenti, devono essere protocollate sui singoli ambienti DocEr

/ Protocollo dei comuni.
### ***Conversione in pdf dei file generati durante la notifica automatica***
E 'stata configurata la creazione automatica della ricevuta di presentazione di una pratica online ed il successivo invio per PEC. La ricevuta è però in formato .rtf mentre la vorrebbero in .pdf. Bisognerebbe quindi aggiungere la conversione in pdf al processo automatico di invio della ricevuta. Il pdf dovrebbe essere l'unico file pubblicato sul portale, non l'rtf.

1.Creazione e Gestione di un nuovo flag DB tipimov\_stc\_mapping.flag\_convertipdf (in caso di notifica automatica i documenti generati in automatico verranno convertiti in PDF)

2. Gestione della logica che in una notifica se flag = true allora chiama il servizio di conversione in PDF e sostituisce il doc originario con quello PDF.
### ***ORDINAMENTO lista dei documenti in invio mail per data di esecuzione del movimento***
Viene richiesto che nella visualizzazione degli allegati nella pagina di invio email i movimenti fossero ordinati per data (come in elaborazione) e non in ordine alfabetico
### ***Possibilità di stampare/creare un allegato dalla graduatoria***
Dare la possibilità di stampare/creare un allegato dalla graduatoria. La possibilità viene visualizzata solamente se sono state create comunicazioni per la graduatoria
### ***Gestione delle comunicazioni delle graduatorie: SCELTA DI PROTOCOLLARE PRIMA O DOPO LA CREAZIONE DELL'ALLEGATO***
Deve essere data la possibilità di protocollare la comunicazione prima o dopo la creazione (e quindi eventuale conversione PDF) dell'allegato. A livello di comunicazione va impostato un FLAG (attivo se scelta la protocollazione) che permette di scegliere quale delle due opzioni scegliere.
### ***VISUALIZZAZIONE DELLA PEC IN CASO DI MOVIMENTO CREATO DA PEC***
Nel caso che un movimento sia stato generato da PEC ( esiste cioè un collegamento con PEC\_INBOX.CODICEMOVIMENTO ) deve essere visualizzato nel dettaglio del movimento ( verificare se anche nell'elaborazione ) un link che porta al dettaglio della PEC con una DialogBox ajax che riporta il dettaglio della MAIL.

Stessa cosa deve essere fatta sulla pagina dell'istanza.
### ***INVIO PRATICHE CON STC: PASSAGGIO DELLA DATA DEI DOCUMENTI***
Quando una pratica viene inviata da un modulo ad un altro non vengono mantenute le date dei documenti dell'istanza di origine.
## **Versione 2.42**
### ***MODIFICHE PER OSSERVATORIO REGIONE FRIULI VENEZIA GIULIA***
Gestione del codice progressivo delle attività. Lo sviluppo serve per identificare con un progressivo univoco le attività che poi sarà utilizzato per l'esportazione dell'osservatorio regionale FVG.

E' stato aggiunto un flag in albero proc che permette di incrementare il progressivo di una attività a determinati passaggi (esempio subingresso)

## **Versione 2.43**
### ***Modifica pagina di lista istanze***
la colonna stato istanza sulla lista delle istanze presenta un link che porta alla funzionalità "INFO" dove è presente l'informazione dello stato.
### ***Possibilità di indicare se pubblicare o meno le schede nei movimenti dell'area riservata.***
In caso di integrazione spontanea da Frontend se il flag per il tipomovimento è spuntato, allora verranno proposte le schede configurate nella configurazione di questo e non le schede del movimento che ha generato il contro movimento.
### ***Altri dati di Notifica STC: possibilità di definire un altro dato che viene visualizzato come HELP e non come campo di input***
Il campo verrà renderizzato sulla pagina di notifica come testo invece come classica Etichetta e Campo.
### ***Possibilità di inviare una mail a seguito della ricezione di eventi***
La funzionalità di gestione degli eventi viene essere estesa prevedendo di mandare mail in determinate circostanze. 
### ***Sorteggi istanza (visualizzazione della colonna comune)***
Nella lista delle pratiche sorteggiate mettere anche il riferimento al comune dell'istanza.
### ***FUNZIONALITA' DI EXPORT: implementazioni per lanciare l'export con strumenti Pentaho***
Ottimizzazioni per velocizzare gli attuali strumenti di export. Va prevista attività di installazione strumenti pentaho e conversione delle vecchie esportazioni. 
## **Versione 2.44**
### ***Gestione link a portale DRUPAL (Comune di Livorno)***
Il frontend espone dei link al portale DRUPAL del comune di livorno. Nel backoffice ALBEROPROCEDIMENTI) viene gestito il collegamento al riferimento. La funzionalità va attivata da opportuna configurazione
### ***Protocollazione PEC in uscita***
Il CAD obbliga, in caso di comunicazioni PEC in uscita (es. richiesta pareri, richieste integrazioni, etc..), a protocollare la PEC. La modifica serve per permettere la protocollazione dei messaggi PEC in uscita dal Backoffice. La funzionalità va attivata da opportuna configurazione.
## **Versione 2.45**
### ***Modifica pagina dettaglio istanze: in caso di assegnazione dell'istruttore con la funzionalità anti corruzione la modifica successiva dell'istruttore deve essere tracciata***
In caso di assegnazione dell'istruttore con la funzionalità anti corruzione la modifica successiva dell'istruttore deve essere tracciata da opportuni log:

1. La modifica deve essere fatta tramite una funzionalità a parte (in questo caso il campo deve essere readonly) che fa un log sulla funzionalità di audit

2. Deve salvare un evento sull'istanza.
### ***MODIFICA AI MOVIMENTI: possibilità di accedere direttamente alla scheda dinamica da un movimento da effettuare***
Dare la possibilità dalla elaborazione istanza di accedere alla sezione schede dinamiche del movimento.
### ***NOTIFICA STC: NUOVO FLAG CHE IN CASO DI MOVIMENTO CHE CREA LA PRATICA SPOSTA I DOCUMENTI DELLA NOTIFICA NEL MESSAGGIO DI PRESENTAZIONE PRATICA***
La modifica serve per protocollare i documenti che vengono da una notifica attività, e che attualmente vengono inseriti nel movimento di destinazione, direttamente nell'istanza. 

## **Versione 2.46**
### ***Modifiche varie***
Statistiche: sostituire la colonna Archivio (pratiche) con COMUNE

- Inserire la colonna COMUNE nello scadenzario

- ARCHIVIO PRATICHE, inserire il nome della DITTA al posto di quello del richiedente

- Mostrare in elaborazione i tempi al netto delle sospensioni

- Ingrandire finestra per stampare PEC

- Mail tipo inserire i campi automatici di: responsabile dell'istruttoria, telefono del responsabile dell' istruttoria, posizione in archivio

- ISTANZE/COMMERCIO/GESTIONE ATTIVITÀ, possibilità di cercare una certa attività inserendone il numero AZIONE e visualizzarne subito il numero

- Nei DOCUMENTI delle singole pratiche, riguardo alla procura: come per qualsiasi documento allegato alla pratica, inserire i campi VALIDO/NON VALIDO E NOTE, ECC.

- Nello scadenzario, sotto "EVENTI NON LETTI" fare comparire anche richiedente e ditta, come nelle altre pagine dello scadenzario

### ***COLLEGAMENTO PRATICHE: possibilità di collegare più pratiche contemporaneamente***
Sulla pagina di collegamento pratiche dare la possibilità di collegare più pratiche contemporaneamente (con checkbox di scelta).

## **Versione 2.47**
### ***Gestire configurazione che non permette di inserire automaticamente oneri associati all' endo nella nuova istanza in fase di notifica STC***
ES.

Quando si fa la notifica STC da Suap ad Edilizia non inviare gli oneri Suap.

Il problema si verifica perché viene notificato l'endo e per configurazione di default all'inserimento di un endo vengono inseriti nell'istanza tutti gli oneri associati all'endo stesso.

Si prevede di mettere un nuovo parametro sulla veriticalizzazione STC che, contenga una lista di nodi mittenti, separati da virgole, per i quali le righe degli oneri non devono essere create in automatico in caso di pratica

creata da STC
## **Versione 2.48**
### ***TIMBRO DIGITALE SU QRCODE***
TIMBRO DIGITALE apposizione di un QRCODE su un segnaposto specifico ws di backend per la generazione del qrcode (codiceistanza, codiceautorizzazione)
## **Versione 2.51**
### ***Gestione Area riservata redirect***
Gestione del FLAG area riservata redirect che permette sulla nostra area riservata di fare una redirect al termine della domanda presentata.

## **Versione 2.52**
### ***RICERCA CONCESSIONI*** 
L'esigenza è di poter ricercare le concessioni e verificare se erano attive ad una certa data.

Al subentro devono essere storicizzati anche i seguenti campi:

- Codice mercato

- Codice uso

- Codice posteggio

- Tipo concessione

- Data inizio concessione stagionale

- Data fine concessione stagionale

17/11/2017 2/3

Modificare la pagina di ricerca in modo che venga visualizzato il attive dallaData - allaData in caso sia selezionato il flag solo attive
### ***OCCUPAZIONI TEMPORANEE***
Gestione delle occupazioni temporanee
### ***POSSIBILITÀ DI NASCONDERE LA FUNZIONALITÀ INVIA EMAIL IN MOVIMENTI***
Aggiungere parametro in verticalizzazione "COMPORTAMENTO\_ISTANZE" per gestire la visualizzazione o no della funzionalità "Invia Email".

Creare il parametro NON\_MOSTRARE\_INVIOMAIL

` `1: non mostrare

` `0: mostra

se non configurato viene mostrato.

### ***SVILUPPO SEMAFORI SU DOCUMENTI IN FASE DI INVIO***
In fase di invio documenti

1. STC

2. Protocollazione

3. Invio Email

Accanto al documento deve comparire un icona diversa a secondo che sia :

1. Valido : verde (success)

2. Non valido : rosso

3. Non verificato : giallo (warnig)

Ad oggi è presente solo in caso sia non valido

### ***Integrazione con il nodo NLA SUAP INRETE (regione FVG)***
Si deve integrare il backoffice con il nodo NLA SUAP INRETE.

La funzionalità deve permettere di creare:

1. Una pratica

2. un movimento di una pratica

a partire dalla funzionalità azioni protocollo.

### ***Nuovi segna posto per email***
[RIC\_TEL]

[RIC\_MAIL]

[RIC\_PEC]

[INTERMEDIARIO]

[INTERM\_CF]

[INTERM\_TEL]

[INTERM\_MAIL]

[INTERM\_PEC]

## **Versione 2.53**
### ***Integrazione con servizio ADRIER***
Connettore di configurazione per ADRIER.

Nuovo ws per la l'integrazione con il servizio di visura aziendale dell'emilia romagna ADRIER

1. Nuova verticalizzazione per attivare e configurare i parametri

2. Implementare ws in VBG

3. Logica di gestione delle due integrazioni di visura PARIX (Nazionale) e ADRIER (Emilia Romagna). Può funzionare solo uno

### ***Nascondere icona "A" su lista istanze se le autorizzazioni/concessioni non sono attive***
Sulla lista delle istanze non deve comparire la "A" nel caso le concessione/autorizzazione legati all'istanza non sono attive.

la funzionalità deve essere retrocompatibile, tale comportamente sarà gestito all'interno della VERTICALIZZAZIONE COMPORTAMENTI\_ISTANZE

1. Aggiungere un nuovo parametro : COMPORTAMENTO\_VIS\_ICONA\_A\_IN\_LISTAISTANZE

1.1 Non attivo o vuoto : icona "A" sempre presente se autorizzazione per istanza > 0

22/12/2017 1/3

1.2 SOLO\_ATTIVE : icona "A" compare solo se autorizzazione per istanza attive > 0

### ***OTTIMIZZAZIONE SCAMBIO POSTEGGIO***
Sviluppare funzionalità per fare uno scambio di posteggio.

La modifica prevede:

1. siano presenti le nuove colonne previste nella sviluppo di ricerca delle concessioni per data (#1471).

2. Un interfaccia nel dettaglio delle concessioni "scambio posteggio",

3. Allo scambio di un posteggio i dati verranno salvati nella solita struttura tabellare aut, aut\_sub, aut\_conc.

Particolarità: Se scambio due posteggi occupati allora scrivo i subentri di entrambe le concessioni. Se scambio con posteggio vuoto allora scrivo il subentro della concessione che sto utilizzando al momento.
### ***FUNZIONALITA' SUBENTRO CONCESSIONI : Aggiungere flag che sovrascrive la l'impostazione di far ricalcolare il numero dalla configurazione***
Sviluppo da fare sulla gestione dei subentri

Nella schermata che gestisce i subentri di una concessione va aggiunto un FLAG che permette all' operatore di sovrascrivere l'impostazione di generare un altro numero (vedi immagine). Il flag sarà attivo solamente sulle

concessioni e sarà visibile solo nel caso in cui il registro preveda la assegnazione automatica da registro o protocollazione.

Va aggiornata anche la logica applicativa dei subentri per gestire la nuova impostazione.
### ***Modificare la pagina java di dettaglio oneri con nuovo campo importo\_interesse***
Nella pagina di dettaglio aggiunge il campo importo\_interesse in modalità solo lettura, sempre visibile
### ***Applicare un layer ad un allegato pdf di un movimento contenente data e numero protocollo***
Dalla lista degli allegati di un movimento, dal dettaglio di un allegato di un movimento applicare una funzione che permette di associare al un file pdf un layer contenete data e numero protocollo.
### ***Modifiche per la nuova gestione delle rateizzazioni***
Aggiungere modifica sulla funzionalità rateizzazione. Si deve tenere separata l'informazione dell'importo e dell'eventuale interesse. Questo per ricostruire il valore iniziale in caso di derateizzazione

## **Versione 2.54**
### ***C-5775-2017 - UTI DEL NONCELLO : Funzionalità esportazione csv documentazione allegata pratica***
Nuovo bottone nella sezione documenti che permette di creare un file csv in formato excel.
### ***C-5775-2017 - UTI DEL NONCELLO : modifiche sezione allegati del movimento***
1. fare un tasto cumulativo di messa alla firma (seleziona/deseleziona tutti) nella sezione allegati del movimento sarà aggiunta una colonna per selezionare e mettere alla firma più file. 
1. Nella sezione "allegati" di un movimento fare comparire anche la colonna con il nome del file 
1. nella sezione "invio mail" fare comparire anche la colonna con le note del file a disposizione per essere allegati
### ***Introduzione configurazione rateizzazioni con ammortamento alla francese***
1. Configurazione rateizzazioni con ammortamento alla francese
1. Algoritmo per eseguire la nuova logica
### ***5757-2017 - Integrazione SUAP/EDILIZIA mediante canale protocollo - GENERA XML PRATICA SUAP***

Definire tre nuovi parametri nella verticalizzazione COMPORTAMENTO\_ISTANZE:

GENERA\_PRATICA\_SUAP Valori (S o N) attiva la funzionalità

GENERA\_PRATICA\_SUAP\_URL url del servizio genera pratica SUAP

GENERA\_PRATICA\_SUAP\_VALIDA (S o N) se validare o meno lo schema prima di creare il file

Nella maschera di protocollo di un movimento se GENERA\_PRATICA\_SUAP == S deve comparire un nuovo bottone "GENERA XML PRATICA SUAP"

La funzionalità deve associare un nuovo documento al movimento:

Il documento dovrà avere :

nome documento : pratica\_suap\_[yyyddgg]

nome file : pratica\_suap\_[yyyddgg].xml

il file salvato dovrà rispettare le specifiche xsd di pratica\_suap-1.0.1.xsd e impresainungiorno.gov\_tipi\_elementari-1.0.0.xsd

Il file non deve essere creato dal backoffice, ma recuperato da un servizio messo a disposizione dall NLA-PDD-RI, quindi il backoffice deve:

1. Invocare il servizio

2. Recuperare la stringa ritornata:

2.1 Stringa di errore : ritorno l'errore

2.2 Stringa corretta vado al passo 3

3. Creare un file

4. inserire un documento nel movimento con il file creato

### ***LIVORNO - 5043-2016 [LegalDoc]: Modifiche algoritmo documenti in conservazione e modifiche all'interfaccia web***

- Aggiungere la possibilità di mostrare solo le archiviazioni con errore
- Aggiungere sulle istanze escluse, l'oggetto della pratica
- Implementare la possibilità di schedulare il processo per uno o più software
- Aggiungere algoritmo che crea una cartella per ogni documento
- Aggiungere prefisso dipendente dal software per cui vengono mandati in conservazioni i documenti

## **Versione 2.55**
### ***NUOVO WEBSERVICE SCHEDEDINAMICHE WS***
Gestione schede dinamiche FVG
### ***C-4729-2015 - Modifiche per la configurazione della gestione allegati pesanti***
Per frontoffice, serve per gestire la dimensione massima degli allegati che l'utente può caricare nell'area riservata.

## **Versione 2.57**
### ***Aggiornamento SUPERFISH Menù***
Aggiornato il menù di backend per risolvere alcuni problemi di usabilità
### ***Auditing della cancellazione dei documenti della pratica***
Alla cancellazione di un documento dell'istanza, movimenti allegati, istanzeprocure, ecc... viene riportata una riga di log sui file di auditing
### ***Storico dei passaggi di stato dell'istanza***
Salva lo storico dei passaggi di stato dell'istanza con i dati della modifica/passaggio di stato e di chi ha effettuato l'operazione.
### ***Funzionalità di presa in carico della pratica***
La funzionalità permette di settare un attributo dell'operatore che ha in carico la pratica. La presa in carico sarà fatta mediante una operazione manuale intervenendo su un bottone SEGNA COME PRESA IN CARICO.

La presa in carico non escluderà l'accesso di altri operatori alla pratica ma servirà a far capire a chi accede la pratica che la sta lavorando un operatore specifico.

## **Versione 2.58**
### ***PROTOCOLLAZIONE DELLA DOMANDA ONLINE PRIMA DELL'ARRIVO NEL BACK***
La modifica deve implementare una nuova logica del flusso di invio della domanda da frontoffice andando ad applicare un controllo che permetta di garantire la ricezione, nel backoffice, di sole domande correttamente protocollate. La domanda, in caso di mancata protocollazione dovrà essere rigettata e rimanere memorizzata nella scrivania virtuale del tecnico con la possibilità di tentare il rinvio in una fase successiva.

Questo comportamento sarà attivato solamente se il frontend è l'area riservata nodo interno.
## **Versione 2.59**
### ***SVILUPPI PER INTEGRAZIONE SUAPER: LOGICA DI INSTRADAMENTO INTERVENTI***
Una pratica SUAP che nell'Ente acquisirà un unico n.ro di PG ma contenente più richieste è detta "procedimento unico".

Lo smistamento nell'albero dei procedimenti di VBG di un "procedimento unico", risulta critico.

A seguito della richiesta di adeguamento VBG per smistare agilmente qualsiasi pratica proveniente dal nuovo SUAPER, si propongono i seguenti sviluppi:

- Gestione di una serie di tabelle di configurazione:
  - Dizionario dei gruppi/endo procedimenti,
  - Associazione dei gruppi agli interventi,
- Realizzazione di una componente di business che effettua lo smistamento utilizzando le configurazioni applicative.

L'idea alla base di questo sviluppo è di ridurre la complessità delle esigenze selezionabili dall'utente, categorizzandole in gruppi. Ciascun gruppo conterrà 1 o più esigenze; ciascuna esigenza appartiene a un solo gruppo. Per esempio il gruppo "interni" potrebbe contenere tutte le esigenze gestite tramite procedimenti dell'Ente, mentre "esterni" contenere tutte le esigenze gestite da Enti terzi.

Così una pratica "procedimento unico" contenente 10 esigenze, nel caso migliore potrebbero appartenere tutti ad unico gruppo e lo smistamento avverrebbe agevolmente nella cartella di VBG assegnata al medesimo gruppo.

Nel caso peggiore cioè VBG non riesce a determinare dove smistare la pratica, essa verrà smistata in una cartella (bidone) che ne implica la protocollazione automatica, invio pec di ricevuta e segnalazione all'operatore SUAP.
### ***PROTOCOLLAZIONE DELLA INTEGRAZIONE ONLINE PRIMA DELL'ARRIVO NEL BACK***
La modifica deve implementare una nuova logica del flusso di invio della integrazionr da frontoffice andando ad applicare un controllo che permetta di garantire la ricezione, nel backoffice, di sole integrazioni/comunicazioni correttamente protocollate. La domanda, in caso di mancata protocollazione dovrà essere rigettata e rimanere memorizzata nella scrivania virtuale del tecnico con la possibilità di tentare il rinvio in una fase successiva.

Questo comportamento sarà attivato solamente se il frontend è l'area riservata nodo interno
### ***INTEGRAZIONE VBG - FIRMA REMOTA ARUBA***
Introdurre possibilità di firmare documenti in modalità CAdES e PAdES (solo PDF) utilizzando il servizio del provider di firma Aruba Remote Sign.

## **Versione 2.60**
### ***QRCODE SU DOCUMENTI PDF***
Possibilità di apporre un QRCODE sui documenti PDF. il QRCODE esporrà un link per poter scaricare il documento. Prerequisiti: per scaricare il documento è necessaria una url pubblica.
### ***VISURA AZIENDA DA LISTA ANAGRAFICHE E GESTIONE PRESENZE MERCATI***
Nel caso sia attiva la visura PARIX / ADRIER allora nella lista delle anagrafiche e nella gestione presenze del mercato viene visualizzato un bottone "VISURA CAMERALE" che permette, inputando il cf impresa, di visualizzare le info dell'azienda mediante la chiamata ai servizi PARIX/ADRIER.

## **Versione 2.61**
### ***FUNZIONALITA' ACCOUNT MULTIPLI***
Possibilità di gestire più account mail per modulo applicativo

## **Versione 2.63**
### ***Funzionalità accesso agli atti***
Modifiche per permettere di gestire mediante pratica l'accesso a pratiche documenti – atti.
## **Versione 2.64**
### ***INTEGRAZIONE VBG - FIRMA REMOTA ARUBA*** 
Aggiunta possibilità di richiedere OTP tramite token virtuale

### ***Dare la possibilità di assegnare a una o più istanze (nella funzionalità ISTANZE COLLEGATE) uno stato***
Dare la possibilità di assegnare a una o più istanze (nella funzionalità ISTANZE COLLEGATE) uno stato. Non sarà possibile assegnare uno stato di chiusura alle pratiche perché genererebbe anche il movimento di chiusura. La lista degli stati visibile sarà popolata con i soli stati di apertura. Non sarà possibile modificare lo stato di una istanza chiusa
### ***Documenti autorizzazioni***
Creare una funzionalità che permetta di associare i documenti dell'istanza all'autorizzazione legata ad essa.

## **Versione 2.66**
### ***Tipologia dei soggetti anagrafici del front che possono modificare le pratiche***
Modifica alla gestione dei soggetti anagrafica.

È stato aggiunto un flag che permette di indicare le tipologie di soggetto che possono modificare le pratiche.

Il flag ha label Modifica la pratica e descrizione Se spuntato le pratiche nell'area riservata saranno modificabili (ovvero esecuzione di movimenti/ visibilità dello scadenzario) dai soggetti collegati definiti da questa tipologia.

### ***Procedimento unico***
Nell'ottica della gestione del servizio di procedimento unico è stata aggiunta la Gestione dei procedimenti collegati ad altri procedimenti.

All'interno della gestione dei procedimenti è stata aggiunta la funzionalità GESTIONE SUB-ENDO che permette di collegare tra loro endoprocedimenti.
## **Versione 2.67**
### ***Documenti messi alla firma***
Nella funzionalità "documenti messi alla firma" visualizzare la colonna "**messo alla firma da**"
### ***Documenti Istanza movimenti allegati***
Possibilità di nascondere i file con estensione xml dalla lista dei documenti dell'istanza. Una impostazione utente gestisce questa visualizzazione.
### ***Istanze***
In caso di associazione comuni nelle info della istanza viene visualizzata l'informazione del comune
### ***Gestione subentri***
Visualizzate le informazioni della localizzazione

## **Versione 2.68**
### ***ESPORTAZIONE TRACCIATI EQUITALIA***
Possibilità di esportare in formato tracciato 450 equitalia alcune tipologie di istanza
## **Versione 2.69**

### ***Integrazione con Pentaho BI***
Possibilità di agganciare reportistica verso la suite pentaho BI. E' possibile lanciare anche report generici
### ***Integrazione con PROTOCOLLO AURIGA***
Implementato il connettore del protocollo AURIGA
### ***GESTIONE SPUNTISTI MERCATO: Possibilità di modificare nella giornata la tipologia di concessione uso***
La modifica serve per la spunta digitale, per poter calcolare il valore da pagare per lo spuntista in base al fatto che sia intera giornata o mezza giornata. Introdotta nuova logica di calcolo
### ***Nodo pagamenti***
Introdotto progetto di integrazione con più sottosistemi di pagamento. Il nodo si occuperà di fornire una interfaccia standard verso il frontend e backend e si occuperà di implementare i vari connettori verso i sistemi di pagamento (es PiemontePay, IRIS, PagoUmbria, ecc..)
## **Versione 2.71**
### ***Funzionalità Black list pagamenti spuntisti***
Per la spunta digitale se lo spuntista non effettua pagamenti entro un intervallo di tempo viene messo in blacklist e così visualizzato nell'app di spunta digitale
## **Versione 2.72**
### ***Schede dinamiche dei Mercati / Posteggi***
Aggiunta la possibilità di configurare schede dinamiche dei posteggi e dei mercati
## **Versione 2.73**
### ***INTRODOTTO PROGETTO API-BACKEND***
Il progetto serve per erogare delle API da esporre mediante sistemi tipo WSO2 e non direttamente
### ***Invio pratica con ZIP***
Possibilità di inviare una pratica da uno zip. Lo zip viene generato dal frontend mediante API e questo viene inviato ad un back mediante interfaccia apposita

## **Versione 2.74**
### ***COLLEGA DOCUMENTI ALL'AUTORIZZAZIONE***
Possibilità di collegare all'autorizzazione creata tutti gli allegati afferenti all'autorizzazione e permette di recuperarli in modo veloce e da un unico punto. I documenti da poter collegare all'autorizzazione sono:

1. Documenti dell'istanza

2. Documenti dei movimenti

3. Nuovi documenti afferenti solo all'autorizzazione e non alla pratica

4. Altri documenti (endoprocedimenti, procure, anagrafe)

I documenti non devono essere duplicati, ma deve essere creato un legame logico, tranne per i documenti dell'autorizzazione per i quali dovrà essere inserito il file fisico
### ***Modifiche alla sezione trasparenza del portale pubblico***
Implementare una serie di modifiche per migliorare l'applicazione "trasparenza". Tra queste: la possibilità di visualizzare o meno alcuni campi, la paginazione dei risultati, l'introduzione di nuovi campi di ricerca.
### ***GESTIONE DEGLI ACCESSI PORTUALI***
Modifiche alle autorizzazioni per gestire le pratiche di richiesta accesso ai porti e le richieste di transiti ai varchi portuali
### ***Possibilità di inserire un logo personalizzato nell'interfaccia di backoffice***
Sarà possibile fa comparire un logo sull'intestazione delle pagine del backoffice
### ***NUOVO STEP AREA RISERVATA GESTIONE ANAGRAFICHE SEMPLIFICATA***
Lo step permette di inserire i soggetti della domanda secondo un workflow, non modificabile, basato sul "ruolo" dell'utente loggato rispetto alla domanda che sta compilando. Punto iniziale obbligatorio è l'inserimento dell'utente loggato persona fisica e poi, in base alla qualifica indicata procede chiedendo altri soggetti se richiesto. Le scelte automatiche vengono gestite attraverso la configurazione delle tipologie di soggetto previste a livello di backoffice. Generalizzando potrebbero essere previsti al massimo 4 soggetti:

Intermediario

Azienda dell'intermediario

Richiedente

Azienda del richiedente
### ***SORTEGGI: attivata la possibilità di scegliere a quali pratiche inserire il movimento nella funzionalità sorteggi***
attivata la possibilità di scegliere a quali pratiche inserire il movimento nella funzionalità sorteggi 

1. Tutte 

2. Sorteggiate

3. Non sorteggiate
### ***Archiviazione documentale LEGAL DOC***
Modifica del connettore di archiviazione documentale LEGAL DOC per usare le API WebService
### ***Ricezione pratiche SUAPER: modifica comportamento per ripristinare la logica di lookup intervento - smistamento gruppi - da parametro configurazione***
Introdotto un parametro di configurazione che permette di bypassare il recupero dell'intervento da smistamento gruppi nel caso che la pratica sia composta da un solo endoprocedimento. MODIFICA LOGICA GRUPPI SMISTAMENTO BASATA SU VERTICALIZZAZIONE 

Il metodo verifica se la verticalizzazione VERTICALIZZAZIONE\_SUAPER\_VECCHIA\_LOGICA\_SE\_UN\_ENDO sia a 1 o S e nel caso controlla se la domanda contiene un solo endo allora va applicata la vecchia logica di recupero intervento per endo principale. In questo modo viene sovrascritta la modalità prevista dai gruppi\_intervento
### ***Possibilità di riprotocollare mediante job le pratiche pervenute da online che hanno avuto errori in protocollazione***
La funzionalità permette di attraverso il job schedulato di recuperare le pratiche marcate come "non protocollate – protocollazione fallita" e tentare una nuova protocollazione. Contestualmente verrà ricreata la ricevuta in modo che il richiedente potrà visionare la nuova ricevuta con numero e data protocollo.
### ***SPUNTISTI: NUOVO CALCOLO COEFFICIENTI PER PAGAMENTI SPUNTA DIGITALE***
Introdotto il nuovo calcolo per coefficienti come da documenti di specifiche. Introdotta la categoria di mercato e il settore del posteggio nel calcolo.

## **Versione 2.75**
### ***NOTIFICA STC: Possibilità di creare la pratica destinataria con i soli endoprocedimenti collegati a quello del movimento***
Aggiunto flag in configurazione notifiche che permette di indicare di inviare gli endo collegati (tabella INVENTARIOPROC\_ENDO) per la creazione della pratica destinataria.
### ***RICERCHE PRATICHE SU SISTEMI PUBBLICI***
Da alcuni enti è venuta l'esigenza di non mostrare dai risultati delle ricerche pubbliche, es. Area Riservata o Portale della Trasparenza, alcune pratiche di determinati interventi.
### ***Creazione ZIP logico nei movimenti***
Lo scopo della modifica è quello di semplificare la procedura per scegliere i documenti della pratica da mandare in protocollazione ed evitare la duplicazione degli stessi andando ad occupare memoria. Il flusso del lavoro sarà così diviso: 

1. Possibilità da parte di un operatore, all'interno di un movimento, di scegliere tra i documenti della pratica un sotto set il cui riferimento sarà salvato in una nuova tabella (zip logico). I documenti scelti saranno visionabili da chi accederà al movimento 

2. In fase di protocollazione sarà possibile protocollare questo set di documenti, andando a selezionare un flag (protocolla zip logico)

3. Verrà creato un nuovo tag che creerà un link, da stampare su documento, per permetterà tramite l'app "download app" di scaricare fisicamente gli alleati come accade oggi.

## **Versione 2.76**
### ***Gestione bollettazione mercati / istanze***
L'esigenza della bollettazione nasce da diverse realtà, ad esempio pagamento su occupazione di posteggi di mercato, canoni demaniali, sanzioni amministrative, occupazione suolo pubblico, ecc.… e quella che si va a descrivere è una funzionalità che dovrebbe servire a gestire svariate esigenze.

La bollettazione di solito esita nell'invio di informazioni strutturate a sottosistemi di pagamento che poi sono delegati a produrre avvisi e file e notifiche tramite diversi canali. Al momento gli oggetti principali oggetto di bollettazione sono gli oneri delle istanze, e le occupazioni dei posteggi da parte dei concessionari. In futuro potrebbero essere altri oggetti da modellare (es. autorizzazioni, concessioni demaniali). Gran parte comunque delle informazioni presenti nell'applicativo sono rappresentate dagli oneri delle istanze
## **Versione 2.77**
### ***Modifica allo zip logico***
Possibilità di condividere la funzionalità zip logico tra moduli
## **Versione 2.78**
### ***Condivisione documentale***
La funzionalità, a fronte della protocollazione di una istanza o di un movimento, permette di copiare in un percorso condiviso ( ad oggi FTP ) i documenti inviati al protocollo
### ***Pratiche con errore distinte per comune***
Nel caso di installazione multicomune Il pannello delle pratiche con errore presenta le pratiche per i comuni configurati per l'operatore.
### ***Gestione dei mercati domenicali***
Modifica nella gestione delle presenze dei mercati

All'interno della gestione dei mercati è possibile specializzare per ogni giornata di mercato alcune caratteristiche che la definiscono quali ad esempio: - la data in cui si svolge la manifestazione - la concessione uso o fascia (es. giornata intera, mezza giornata ) configurate in < ARCHIVI SOFTWARE > ==>TABELLE ==> MANIFESTAZIONI ==> CONCESSIONI USO - Se gestire le presenze/assenze. Se impostato a "Sì" la gestione delle presenze viene fatta secondo le regole classiche. Se impostato a "No" in ogni caso la presenza non viene impostata. - Se popolare i concessionari (ovvero gestire tutti i soggetti della giornata come spuntisti e quindi come soggetti che pagano). Questo è il caso di svolgimento mercato non in una giornata di mercato quali ad esempio feste patronali. Se impostato a "Sì" allora si tratta di una gestione "standard" nella quale i posteggi vengo assegnati ai relativi concessionari e valgono le regole di validazione per l'assegnazione dei posteggi. Se impostato a "No" allora tutti i soggetti che partecipano vengono trattati come spuntisti e sono tenuti all'eventuale pagamento dell'occupazione. In questo caso non valgono le regole di verifica di accesso ai posteggi. È il caso, ad esempio, della gestione di una giornata di mercato per le feste di quartiere che vengono svolte in quel mercato ma in giorni, ad esempio di Domenica, dove non sono presenti concessioni.
### ***Pannello statistiche per accesso agli atti***
Sarà possibile interrogare il sistema per due criteri principali:

dato un nominativo, conoscere a quali Pratiche Edilizie ha avuto accesso (e quando) e con quale pratica di accesso agli atti è stata effettuata (numero/anno/VIS)

dato un numero di Pratica Edilizia, da chi e quando è stata visionata e con quale pratica di accesso agli atti è stata effettuata (numero/anno/VIS).

Per poter accedere a questa funzionalità bisognerà seguire il percorso "Statistiche->Pratiche Edilizie->Accesso agli atti", si aprirà allora una maschera di ricerca 

Per poter effettuare la ricerca sarà sufficiente selezionare almeno un'anagrafica che ha acceduto alla pratica oppure un numero istanza. Inoltre è possibile filtrare anche per un dato intervallo temporale impostando le date nel campo Periodo.

E' possibile ordinare la lista sia per "Nominativo" che per "Numero istanza", e in ordine crescente o descrescente.

La lista ottenuta si potrà esportare nei formati csv, xml o PDF selezionando le relative icone nella barra in alto alla tabella.
### ***Account email multicomune***
Nelle installazioni multicomune sarà possibile impostare un account specifico per ente. 

## **Versione 2.79**
### ***Impostazione dell'account sulle comunicazioni dei movimenti***
Nelle comunicazioni dei movimenti sarà possibile specificare un account mail con il quale inviare le comunicazioni
### ***Domande online***
Nelle domande online di un determinato modulo software, nel caso di comuni associati sarà possibile escludere determinati comuni nello step di benvenuto.
Vedi note: [documenti / contributi degli enti riusatori / README.md](https://github.com/RegioneUmbria/VBG/blob/master/documenti/contributi%20degli%20enti%20riusatori/README.md)	 PAGE   \\* MERGEFORMAT 5
