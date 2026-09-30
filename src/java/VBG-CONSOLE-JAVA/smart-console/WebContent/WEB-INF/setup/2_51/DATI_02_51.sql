INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('ASSEGNAZIONE_OPERATORI', 'La regola attiva il comportamento che permette di scegliere manualmente nella pratica gli operatori, istruttori, responsabili del procedimento da dei gruppi configurati nell''intervento della pratica.',0);

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('ASSEGNAZIONE_OPERATORI', 'ESCLUDI_ASSEGNAZIONE_AUTO', 'VALORI S O N. In caso di S non viene eseguita la logica della funzionalita'' ANTICORRUZIONE che in automatico calcola l''istruttore.');

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('AREARISERVATA_REDIRECT',
'Se attiva gestisce la redirezione dell''utente ad una nuova domanda al termine della presentazione din una domanda online',0);

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES (
'AREARISERVATA_REDIRECT',
'NOME_FILE',
'Indica il nome del file di risorse contentnte i testi da visualizzare al termine della presentazione della domanda. Se omesso verrà usato il file redirect-default.xml che si trova nella root dell''area riservata');


INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES (
'AREARISERVATA_REDIRECT',
'URL_REDIRECT',
'Contiene l''url (relativo all''area riservata o url assoluto) verso cui redirigere l''utente al termine della presentazione della domanda, Può contenere i seguenti segnaposto: {alias}, {software}, {token} e {idDomanda} ');

insert into MAPOGGETTI (NOMETABELLA,NOMECAMPO) VALUES('ISTANZEPROCURE','CODICEOGGETTO_DOCIDE');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_ISTANZE','NON_MOSTRARE_INVIOMAIL','Parametro per gestire la visualizzazione della funzionalità invia email in movimenti. Può assumere i valori 1: Non mostrare funzionalità e 0: Mostra funzionalità');