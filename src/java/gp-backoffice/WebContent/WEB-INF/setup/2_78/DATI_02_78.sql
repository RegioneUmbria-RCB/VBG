UPDATE MERCATIPRESENZE_T SET FLAG_CONTEGGIA_PRES_ASS=1 WHERE FLAG_CONTEGGIA_PRES_ASS IS NULL;
UPDATE MERCATIPRESENZE_T SET FLAG_POPOLA_CONCESSIONARI=1 WHERE FLAG_POPOLA_CONCESSIONARI IS NULL;

INSERT INTO verticalizzazionibase (modulo, descrizione, flag_gestcomune) VALUES ('CONDIVISIONE_DOCUMENTALE', 'Gestisce l''integrazione con sistemi documentali a seguito della protocollazione. Se attiva i documenti protocollati verranno contrassegnati come pronti all''invio verso il sistema documentale. Un''attività schedulata provvederà all''effettivo travaso dei dati verso il sistema esterno.', 0);
INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('CONDIVISIONE_DOCUMENTALE', 'ID_ACCOUNT_FTP', 'Riferimento all''id presente nella tabella ACCOUNT_FTP contenente i parametri per effettuare l''invio dei files e degli eventuali metadati');

INSERT INTO MAPOGGETTI(NOMETABELLA,NOMECAMPO) VALUES ('DOCUMENTI_CONDIVISI','CODICEOGGETTO');