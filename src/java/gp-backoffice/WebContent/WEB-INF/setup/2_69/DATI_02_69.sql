INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO) VALUES ('PAY_POSIZIONI_DEBITORIE','FK_OGGETTO_FATTURA');
INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO) VALUES ('PAY_POSIZIONI_DEBITORIE','FK_OGGETTO_RICEVUTA');
INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO) VALUES ('PAY_POSIZIONI_DEBITORIE','FK_OGGETTO_AVVISO');


INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('NODO_PAGAMENTI', 'Attiva la gestione dei pagamenti mediante il nodo pagamenti', 0);

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NODO_PAGAMENTI', 'URL_WS', 'indirizzo del wsdl del webservice  del nodo dei pagamenti es: http://devel9:8084/nodo-pagamenti/services/pagamentiSOAP?wsdl');


INSERT INTO MAPOGGETTI (NOMETABELLA, NOMECAMPO) VALUES ('CONSENSI_INFORMATIVI','CODICEOGGETTO');


INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('NLA_INFOCAMERE', 'Attiva la cooperazione tra il backoffice e il portale di Impresa in un giorno (Infocamere)', 1);

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'URL_WS', 'indirizzo del wsdl del webservice di impresainungiorno comunicazione Ente - SUAP');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'USER_WS', 'User per autenticazione wsdl Ente - SUAP');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'PSW_WS', 'password per autenticazione wsdl Ente - SUAP');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'INFO_SCHEMA_VERSIONE', 'Versione schema messaggio impresainungiorno');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'COD_MOV_RICHIESTA_INTEGRAZIONE', 'Codice movimento che rappresenta la richiesta di integrazione documentale');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'CODICE_AMMINISTRAZIONE', 'Indica il codice dell''amministrazione a cui inviamo la comunicazione (Ente destinatario)');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'CODICE_AOO', 'Indica il codice AOO a cui inviamo la comunicazione (Ente destinatario)');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'IDENTIFICATIVO_SUAP', 'Indica identificativo del suap a cui inviamo la comunicazione (Ente destinatario)');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'DESCRIZIONE_SUAP', 'Stringa di descrizione del suap a cui inviamo la comunicazione (Ente destinatario)');


INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'CODICE_AMMINISTRAZIONE_DEST', 'Indica il codice dell''amministrazione da cui inviamo la comunicazione (Ente mittente)');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'CODICE_AOO_DEST', 'Indica il codice AOO da cui inviamo la comunicazione (Ente mittente)');
INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'DESCRIZIONE_SPORTELLO_DEST', 'Stringa di descrizione dello sportello da cui inviamo la comunicazione (Ente mittente)');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'PEC_SPORTELLO_DEST', 'PEC  dello sportello da cui inviamo la comunicazione (Ente mittente)');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'COD_MOV_RIENTRO_INTEGRAZ_DOC', 'Codice movimento che rappresenta il rientro di un integrazione documentale');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'SALVA_XML_COMUNICAZIONE', 'Può assumere i valori 1: Attivo, 0(NULL): Non attivo. Se attivo sui documenti della pratica/movimento verrà salvato l''xml della comunicazione');

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('STC','NLA_IDNODO_INFOCAMERE','Identificativo - registrato su STC - del nodo di integrazione con impresainungiorno (INFOCAMERE)') ;


INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('AREA_RISERVATA', 'NASCONDI_RIGENERA_RIEPILOGO', 'Se impostato a 1 non mostra il bottone che permette di rigenerare il riepilogo nell''area riservata (default: 0)');

INSERT INTO VERTICALIZZAZIONIBASE(MODULO, DESCRIZIONE, FLAG_GESTCOMUNE) VALUES ('PROTOCOLLO_AURIGA', 'Integrazione con il documentale di protocollazione AURIGA', '0');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'URL', 'Specificare in questo parametro l''end point del web service Auriga; nell''endpoint bisogna sostituire il servizio con {servicename} es: http://aurigatest.comune.genova.it:8080/AurigaBusiness/soap/{servicename}?wsdl');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'PROXY_URL', 'Specificare in questo parametro l''end point del web service Java che fa da proxy verso Auriga');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'USERNAME', 'Username relativo alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'PASSWORD', 'Password relativa alle credenziali per autenticarsi al web service, da non confondere con gli utenti del protocollo');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'CODAPPLICAZIONE', 'Codice identificativo dell’applicazione che chiama il WS (obbligatorio): se la chiamata è dall’interno dello stesso catalogo servizi va valorizzata come "AURIGA"');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_AURIGA', 'ISTANZAAPPLICAZIONE', 'Codice identificativo dell’istanza dell’applicazione esterna che chiama il WS (se applicazione multi-istanza)');

