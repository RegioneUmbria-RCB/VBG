INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STUDIOK', 'DENOMINAZIONE_ENTE', 'Indicare in questo parametro la denominazione dell''ente. Questo parametro di fatto non viene utilizzato in fase di protocollazione e potrebbe anche essere omesso, serve solamente per visualizzare in modo più completo le informazioni in fase di lettura protocollo, quello che sarà scritto qui dentro infatti sarà poi visualizzato nell''interfaccia di protocollazione in fase di lettura nella fase dei destinatari (PROTOCOLLO INARRIVO) o mittenti (PROTOCOLLO IN PARTENZA).');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_STUDIOK', 'ASSEGNATO_DA', 'Indicare in questo parametro l''ufficio da cui parte l''assegnazione in fase di protocollazione in ARRIVO. Può essere omesso in quel caso il protocollo risulterà assegnato da un settore amministrativo di default configurato direttamente dal sistema di protocollo.');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M367', 'POLESINE ZIBELLO', 'PR', 'PARMA', 'EMILIA ROMAGNA', '43010', 'M367', '034050', '08');
INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('H742', 'SSAMBIASE', 'CZ', 'CATANZARO', 'CALABRIA', '88048', 'H742', '079105', '18');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('G422', 'PELLARO', 'RC', 'REGGIO CALABRIA', 'CALABRIA', 'G422', '080811', '18');

INSERT INTO COMUNI(CODICECOMUNE, COMUNE, CF, CODICEISTAT) VALUES ('I323', 'SANT'' ELIA REATINO', 'II323', '057811');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_ATTIVO', 'CREACOPIA_DESCR_FILE', 'Valorizzare questo parametro a 1 nel momento in cui si renda necessario fare in modo che le descrizioni dei file, recuperate dal campo Descrizione, dei documenti dell''istanza, non debbano essere uguali. Qualche protocollo (i DocArea di ADS e MAGGIOLI ad esempio), non accettano doppioni sulle descrizioni dei file.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_PADOC', 'MOV_DA_EFFET_QUANDO_PROT_ISTAN', 'Deve essere inserito un codice movimento valido per il modulo corrente. Nel caso di campo popolato, nel momento in cui P@DOC chiama il servizio di inserimento del protocollo nell''istanza, verrà contestualmente inserito il movimento tramite STC');
INSERT INTO COMUNI(CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE) VALUES ('M298', 'STATTE', 'TA', 'TARANTO', 'PUGLIA', '74010', 'M298', '073029', '16');

UPDATE CLMENU_JAVA SET MENULINK='0AZO' WHERE ID=1028;

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('PAGAMENTI_MIP_RPCSUAP', 'INTESTAZIONE_RICEVUTA', 'Testo che deve apparire nell''intestazione della ricevuta di pagamento');

INSERT INTO verticalizzazioniparametribase (modulo,parametro,descrizione) VALUES ('AREA_RISERVATA','JSON_URL_SERVIZI_CONDIVISI','In caso di modulistica e faq condivise (servizi JSON del frontoffice) va indicato nella seguente forma (<ALIAS>#http://<server_raggiungibile>:<porta>/<contesto>/public_json esempio B819#http://localhost:8080/areariservata2/public_json) l''indirizzo dei servizi ai quali richiedere le informazioni.');
