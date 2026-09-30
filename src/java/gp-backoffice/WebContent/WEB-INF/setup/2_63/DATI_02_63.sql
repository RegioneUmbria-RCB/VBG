UPDATE CLMENU_JAVA SET PAGINA='mailconfig/list.htm?software=SOFTWARE', LINK_STANDARD='mailconfig/list.htm?software=SOFTWARE' WHERE ID=990;
UPDATE CLMENU_JAVA SET PAGINA='mailconfig/list.htm?software=TT', LINK_STANDARD='mailconfig/list.htm?software=TT'  WHERE ID= 854;
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('NLA-RICEZIONE-PEC','LISTA_ACCOUNT_DA_PROCESSARE', 'Lista account da processare per recupero automatico delle pec da importare. (Pannello PEC)');

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) values ('TRIESTE_ACCESSO_ATTI', 'Parametri di configurazione per l''accesso agli atti del comune di Trieste', 0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('TRIESTE_ACCESSO_ATTI', 'AR_URL_TRASFERIMENTO_CONTROLLO', 'Url verso cui trasferire il controllo durante la presentazione della domanda');
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('TRIESTE_ACCESSO_ATTI', 'AR_URL_WEB_SERVICE', 'Url del web service da cui recuperare i dati immessi dall''utente');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'TIPOMOV_RICEVUTA', 'Indicare in questo parametro il tipo movimento relativo alla ricevuta automatica che genera un protocollo in partenza, in caso di utilizzo di tale sistema, ossia quindi della protocollazione automatica in partenza della ricevuta generata da un movimento che viene eseguito all''avvio dell''istanza, queseto parametro è fondamentale per verificare se la protocollazione automatica in arrivo è andata a buon fine, in caso negativo infatti va bloccata anche la protocollazione automatica in partenza.');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M411', 'SORBOLO MEZZANI', 'PR', 'PARMA', 'EMILIA ROMAGNA', 'M411', '034051', '08');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M409', 'TRESIGNANA', 'FE', 'FERRARA', 'EMILIA ROMAGNA', 'M409', '038030', '08');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M410', 'RIVA DEL PO', 'FE', 'FERRARA', 'EMILIA ROMAGNA', 'M410', '038029', '08');                            

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M408', 'BARBERINO TAVARNELLE', 'FI', 'FIRENZE', 'TOSCANA', 'M408', '048054', '09');              
