INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_MODELLIT_RIPET',
    'NOME DELLA SCHEDA DINAMICA CHE CONTIENE I DATI CHE TORNANO DAL SERVIZIO OCCUPAZIONE SUOLO PUBBLICO CON FREQUENZA RIPETUTA'
  );
INSERT INTO verticalizzazioniparametribase
  (
    modulo,
    parametro,
    descrizione
  )
  VALUES
  (
    'SIT_LDP',
    'OSP_DYN2_OSP_FREQ_OCCUPAZ',
    'NOME DEL CAMPO DIMAMICO CHE CONTIENE LA FREQUENZA DELL'' OCCUPAZIONE SUOLO PUBBLICO'
  );

Insert into VERTICALIZZAZIONIPARAMETRIBASE (MODULO,PARAMETRO,DESCRIZIONE) values ('ANTI_CORRUZIONE','MODIFICA_ISTR_DA_LISTA_COMPL','Il parametro permette di modificare un istruttore assegnato dalla funzionalità scegliendolo anche dalla lista completa e non solo tra quelli presenti nel gruppo associato all''istanza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE VALUES   ('COMPORTAMENTI_ISTANZE','ELAB_MOVIMENTI_PREC_CHIUS','Se attivato durante l''elaborazione non processa i movimenti con data antecedente la chiusura istanza, Valori possibili S, N (default N)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_IRIDE', 'WARNING_PEC', 'Se valorizzato a 1 consente di visualizzare sempre un warning relativo all''invio PEC di una protocollazione in partenza, sia che vada a buon fine che vada in errore.');

INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('BP', 'Vario', '1', 'Vario', '0', '99');
INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('MC', 'Vario', '1', 'Vario', '0', '99');
INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('OT', 'Vario', '1', 'Vario', '0', '99');
INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('RI', 'Vario', '1', 'Vario', '0', '99');
INSERT INTO SOFTWARE (CODICE, DESCRIZIONE, MODULOOPZIONALE, DESCRIZIONELUNGA, ACCESSORAPIDO, ORDINE) VALUES ('SF', 'Vario', '1', 'Vario', '0', '99');


update naturaendo set naturabase='scia' where 
( lower(natura) like 'autocertificabile' or 
lower(natura) like 'scia' or 
lower(natura) like 's.c.i.a.' or 
lower(natura) like 'dia' or 
lower(natura) like 'd.i.a.' ) and naturabase is null;

update naturaendo set naturabase='ordinario' where 
( lower(natura) like 'autocertificabile' or 
lower(natura) like 'ordinario' or 
lower(natura) like 'autorizzativa' or 
lower(natura) like 'autorizzativo'  ) and naturabase is null;

update naturaendo set naturabase='comunicazione' where 
( lower(natura) like 'comunicazione'  ) and naturabase is null;

Insert into CLMENU_JAVA (ID,DESCRIZIONE,PAGINA,MENULINK,SOFTWARE,JSP,VERTICALIZZAZIONE,SOFTWAREESCLUSI,LINK_STANDARD,TIPO_FUNZIONALITA,LAYOUTTESTI,MENULINK_V2) values ('1036','Configurazione della Natura delle procedure','natureprocedure/list.htm?software=SOFTWARE','0AZP','*','JAVA',null,'AB','natureprocedure/list.htm?software=SOFTWARE','S','0','0AZP');

