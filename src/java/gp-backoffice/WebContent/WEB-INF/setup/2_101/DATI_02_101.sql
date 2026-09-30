INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES  ('NODO_PAGAMENTI', 'AR_PAGO_DOPO_GG_SCADENZA', 'Giorni da utilizzare per calcolare la scadenza di una posizione debitoria aperta tramite "paga dopo". Se lasciato vuoto verrà impostato a 30 giorni');

update  massive_parametri set valore ='1' where chiave in ('RICHIEDE_PROTOCOLLAZIONE', 'TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF',
'ALLEGA_AVVISI_PAGAMENTO',
'FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE',
'ESCLUDI_DESTINATARI_SENZA_MAIL') and lower(valore)='true';


update  massive_parametri set valore ='0' where chiave in ('RICHIEDE_PROTOCOLLAZIONE', 'TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF',
'ALLEGA_AVVISI_PAGAMENTO',
'FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE',
'ESCLUDI_DESTINATARI_SENZA_MAIL') and lower(valore)='false';

UPDATE VERTICALIZZAZIONIPARAMETRIBASE SET DESCRIZIONE = 'Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario. Può assumere i seguenti valori: non valorizzato o se valorizzato a 0 il sistema indicherà come mittente / destinatario il Richiedente e l''Azienda, se valorizzato a 1 verrà proposto solo il Richiedente, se valorizzato a 2 il sistema indicherà come Mittenti solo l''Azienda, se, in questo caso l''Azienda non è presente indicherà solamente il Richiedente,se valorizzato a 3 il sistema indicherà l''azienda e il tecnico, se l''azienda non è presente indicherà il richiedente, se valorizzato a 4 il sistema indicherà solo il tecnico, se non presente il richiedente.' WHERE modulo = 'PROTOCOLLO_ATTIVO' AND parametro = 'TIPO_MITTDEST_AUTO';

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'DISABILITA_VALIDAZ_CAP_ITA', 'Valore 1 o 0, se valorizzato a 1 indica che deve essere disabilitato il controllo, da parte del componente che si interfaccia col protocollo, relativo alla validazione di un cap con formato italiano, quindi con solo caratteri numerici e con lunghezza da 4 a 6 caratteri.');
insert into comuni (CODICECOMUNE, COMUNE, SIGLAPROVINCIA, PROVINCIA, REGIONE, CAP, CF, CODICEISTAT, CODICEISTATREGIONE, CODICESTATOESTERO) values('M422','PIEVE DEL GRAPPA','TV','TREVISO','VENETO','31017','M422','026096','05',NULL);

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'MITTENTE_PEC', 'E'' il mittente da utilizzare per fare in modo che il sistema di protocollo determini da chi venga inviata una pec . Tale dato deve necessariamente essere presente anche nel sistema di protocollo. Non è un dato obbligatorio in quanto se non individuato il protocollo userà il proprio principale ma serve nel caso in cui l''ente decida di inviarlo da un mittente dedicato specifico.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_CIVILIANEXT','URL_WSINVIAPROTOCOLLO','Endpoint del web service che notifica la protocollazione ai destinatari in caso di protocollazione in uscita');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_CIVILIANEXT','IDCASELLAEMAIL','Identificativo della casella email da utilizzare per l''invio mail da parte del protocollo. Se non specificato verrà presa quella configurata di default nel protocollo CiviliaNext per l''operatore');
