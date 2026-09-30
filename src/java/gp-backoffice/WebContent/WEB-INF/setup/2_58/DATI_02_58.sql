INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('PROTOCOLLO_INSIEL', 'USA_WS_CLASSIFICHE', 'Indica la modalità di recupero dati relativamente alle voci di titolario (classificazione). Se valorizzato a 1 saranno recuperate tramite apposito metodo del web service, altrimenti sarà utilizzato il metodo classico, ossia o da tabella protocollo_classifiche oppure tramite testo libero.');
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('FVG_SUAP_IN_RETE','COLL_SCHEDE_ISTAN_IN_ATTIVITA','Può assumere i valori 0(Null),1. Se 1 quando viene creata un''attività a partire dall''istanza o quando un istanza viene collegata ad una attivita le schede dinamiche dell''istanza saranno collegate anche all''attività.');
INSERT into SOFTWARE (CODICE,DESCRIZIONE,MODULOOPZIONALE,DESCRIZIONELUNGA,ACCESSORAPIDO,ORDINE) values ('S2','SUA','1','Sportello Unico Amministrativo','0','99');
INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'STC',
    'IPRA_PROT_PRIMA_DI_INSERIRE',
    'Può assumere i valori 0(Null),1. Al momento dell'' inserimento della pratica STC viene invocato il metodo di protocollazione.'
);

INSERT INTO verticalizzazioniparametribase (
    modulo,
    parametro,
    descrizione
) VALUES (
    'STC',
    'IATT_PROT_PRIMA_DI_INSERIRE',
    'Può assumere i valori 0(Null),1. Al momento dell'' inserimento di una attivita'' mediante STC viene invocato il metodo di protocollazione.'
);

INSERT INTO VERTICALIZZAZIONIBASE (MODULO, DESCRIZIONE) VALUES ('COMPORTAMENTO_COMPONENTE_FIRMA', 'Se attivata permette di modificare il comportamento standard (firma CADES) del componente di firma');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE   (modulo, parametro, descrizione ) VALUES ('COMPORTAMENTO_COMPONENTE_FIRMA','FIRMA_CADES_NO_EXT_MULTI_P7M','Può assumere i valori 0 (NULL),1. 0: Ad ogni firma (CADES) applicata al file il nome sarà modificato accodando l''estensione .p7m. (Es. Applico tre firme a nomeFile.pdf --> nomeFile.pdf.p7m.p7m.p7m ) 1: Al file firmato (CADES) sarà accodata l''estensione p7m solo alla prima firma; le successive firme non modificheranno il nome del file. (Es. Applico tre firme a nomeFile.pdf --> nomeFile.pdf.p7m)');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
) VALUES (
    'STC',
    'IPRA_PROT_MAILTIPOOGGETTO',
    'INDICARE IL CODICE DELLA MAIL TIPO PER LA COMPOSIZIONE DELL''OGGETTO DEL PROTOCOLLO DELLA PRATICA. E’’ OBBLIGATORIO CHE LA MAIL TIPO ABBIA COME AMBITO FRONTEND'
);
INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
) VALUES (
    'STC',
    'IATT_PROT_MAILTIPOOGGETTO',
    'INDICARE IL CODICE DELLA MAIL TIPO PER LA COMPOSIZIONE DELL''OGGETTO DEL PROTOCOLLO DEL MOVIMENTO'
);
