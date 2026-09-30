
INSERT INTO CLMENU_JAVA(ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES (1051, 'Verifica Tracciati Anagrafe Tributaria', 'anagrafetributaria/list.htm?software=SOFTWARE', '785', '*', 'JAVA', 'PR,FI', 'anagrafetributaria/list.htm?software=SOFTWARE', 'S', '785');

insert into helpbase (software,contenttype,helptext,tab) values (
'TT',
'/anagrafetributaria/view.htm',
'<h2>Analisi tracciati anagrafe tributaria</h2>
<p>La funzionalità è stata sviluppata per permettere una verifica dell''esito dei tracciati da interfaccia grafica.</p>
<h3>Uso della funzionalità</h3>
Per poter proseguire viene richiesto di inserire il file di tracciato, il file degli esiti e di indicare una descrizione della verifica che si intende fare (per eventualmente accedervi in secondo momento dalla pagina di lista).<br />
Di solito il file di esiti prodotto dall''applicativo esterno può essere restituito in formato pdf.
Per poter essere processato nella funzionalità il file deve essere trasformato in txt.
Per far questo l''operatore deve selezionare tutto il testo dal PDF, incollarlo in un file di testo, senza fare ulteriori modifiche sul testo incollato, e salvarlo nel suo dispositivo locale (PC, notebook, laptop).<br />
Una volta inseriti nei campi di interfaccia specifici il file txt del tracciato e il file txt degli esiti l''operatore dovrà cliccare su <b>OK</b> ed attendere l''esito del caricamento.<br />
La funzionalità realizzata processa il file di esito, verifica tutte le anomalie e presenta all''utente una serie di gruppi che permettono all''operatore di prendere delle azioni correttive.<br />
La procedura durante l''analisi tenta di associare una pratica alla riga di anomalia tenendo presente i dati prodotti nel tracciato. Ove riesce ad associare l''istanza nell''interfaccia per ogni blocco prodotto presenterà il collegamento con la pratica individuata. In caso di errore l''operatore potrà comunque modificare la pratica.<br />
Nel caso invece che la procedura non riesca a recuperare la pratica di pertinenza allora l''operatore potrà cercare in autonomia la pratica secondo le informazioni presenti nel blocco presentato.<br />
Il link alla pratica permette di accedere alla pratica individuata e correggere a seconda dell''errore indicato nella tabella degli errori l''anomalia.
Una volta verificato l''errore e corretto l''operatore dovrà cliccare sulla casella <b>[X] completato</b>.<br />
Questo permette di nascondere le operazioni completate e concentrarsi su quelle da finire.<br />
Il link <b>Cambia pratica</b> permette di associare un''altra pratica a quella riga di blocco.<br />
Il link <b>Cerca</b> permette di associare una pratica a quella riga di blocco.<br />
Entrambe i link portano l''operatore alla maschera di ricerca delle istanze.<br />
Una volta definiti i filtri di ricerca l''operatore dovrà selezionare la pratica cercata e confermare l''operazione.<br />
Il link <b>Dettaglio righe tracciato</b> permette di visualizzare le righe originali del tracciato che hanno generato l''errore.<br />
Nel dettaglio viene riportata anche il numero di linea ovvero la posizione del record in errore.
Al termine della verifica sarebbe bene eliminare l''analisi mediante il pulsante <b>ELIMINA</b>.</p>'
,
0
);
