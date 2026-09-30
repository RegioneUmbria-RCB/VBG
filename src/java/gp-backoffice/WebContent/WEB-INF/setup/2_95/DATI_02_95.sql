INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_URBI', 'USA_UFFICIO_DEST_PARTENZA', 'Serve solo per parametrizzare un comportamento della protocollazione in partenza, in particolare indica se deve essere associato l''ufficio mittente anche tra gli uffici destinatari e relativi utenti recuperati dal parametro DEST_UTENTI_CO_AUTOMATICI. Questo parametro si è reso necessario in quanto in alcune installazioni è obbligatorio indicare gli uffici destinatari in una protocollazione in partenza');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('AREA_RISERVATA', 'ISTANZEPRES_POSIZIONEARCHIVIO', 'Valori ammessi 0/1. Se impostato a 1, nella sezione "Le mie pratiche" verrà mostrata anche la colonna della posizione archivio. Se non impostato o valorizzato a 0 la posizione archivio non verrà mostrata');

INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO, IDRISORSA) VALUES ('81', 'Posizione in archivio', 'ISTANZE.POSIZIONEARCHIVIO', 'ISI-LIS','visura.posizione_in_archivio');
INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, CHIAVE, FK_CONTESTO, IDRISORSA) VALUES ('82', 'Posizione in archivio', 'ISTANZE.POSIZIONEARCHIVIO', 'ISI-FIL','visura.posizione_in_archivio');

