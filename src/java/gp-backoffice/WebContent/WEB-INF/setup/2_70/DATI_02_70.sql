INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'COD_MOV_PRATICA_CONFORME', 'Codice movimento che indica nel flusso il fatto che una pratica è conforme alla zona in cui è stata presentata');

INSERT INTO  verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('NLA_INFOCAMERE', 'COD_MOV_PRATICA_NON_CONFORME', 'Codice movimento che indica nel flusso il fatto che una pratica non è conforme alla zona in cui è stata presentata');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'LOGICA_MITT_MULTIPLI', 'Valori: 0 (default) e 1. Se impostato a 1 allora imposta la logica di calcolare il mittente della protocollazione dall''amministrazione impostata nella configurazione dell''albero della pratica mittente. Serve a superare i problemi dell''amministrazione mappata piu'' volte con la tupla IDNODO_IDENTE_IDSPORTELLO usato nel parametro di verticalizzazione LISTA_NODI_SOSTIT_MITTENTI');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione )VALUES ('CONSOLE','SC_CODICE_PARTENZA','IL CODICE (CAMPO SC_CODICE) DELL''INTERVENTO VOCE DI PARTENZA DALLA QUALE COPIARE LE INFORMAZIONI. ES: SOLO VOCE DELL''ALBERO AMBIENTE ''0105''');
