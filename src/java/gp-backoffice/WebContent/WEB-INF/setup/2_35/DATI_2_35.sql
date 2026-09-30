INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE (MODULO, PARAMETRO, DESCRIZIONE) VALUES ('FILESYSTEM', 'ESEGUI_CHMOD_FILE', 'Questo parametro indica se deve essere fatta al salvataggio del file (in caso di file system linux) la chmod 777 sulla cartella padre. Questa operazione è necessaria solamente nel caso che sia valorizzato il parametro SHAREDPATH e si vuol fare modificare il file direttamente dal browser. Non serve se si usa la funzionalità MODIFICA (che lancia l''applet)');
INSERT
INTO VERTICALIZZAZIONIPARAMETRIBASE
  (
    MODULO,
    PARAMETRO,
    DESCRIZIONE
  )
  VALUES
  (
    'PROTOCOLLO_ATTIVO',
    'LISTA_NODI_SOSTIT_MITTENTI',
    'La lista separata da '','' dei riferimenti STC per i quali in protocollazione automatica dell''istanza va ricalcolato il mittente. ES di VALORE 400_E256_SS (Ogni riferimento è composto da IDNODO_IDENTE_IDSPORTELLO)'
  );

