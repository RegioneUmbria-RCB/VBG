create table istoneri_dett_posizioni (
     idcomune                        varchar2(6) not null,                
     id                             number(10) not null,
     fk_istanzeoneri_id              number(10) not null,
     fk_dettposdebitoria_id          number(10) not null
);

ALTER TABLE istoneri_dett_posizioni
  ADD CONSTRAINT pk_istoneri_dett_pos PRIMARY KEY (
    idcomune,
    id
  );
  
  
ALTER TABLE istoneri_dett_posizioni
  ADD CONSTRAINT fk_dettposdeb FOREIGN KEY (
    idcomune,
    fk_dettposdebitoria_id
  ) REFERENCES dett_posizione_debitoria (
    idcomune,
    id
  );


ALTER TABLE istoneri_dett_posizioni
  ADD CONSTRAINT fk_istanzeoneri FOREIGN KEY (
    idcomune,
    fk_istanzeoneri_id
  ) REFERENCES istanzeoneri (
    idcomune,
    id
  );
  
  
INSERT INTO istoneri_dett_posizioni (idcomune,id,fk_istanzeoneri_id,fk_dettposdebitoria_id) SELECT idcomune, ROW_NUMBER() OVER (PARTITION BY idcomune ORDER BY idcomune) AS id, id AS fk_istanzeoneri_id, fk_posdebdettaglio_id AS fk_dettposdebitoria_id FROM istanzeoneri WHERE fk_posdebdettaglio_id IS NOT null ORDER BY idcomune;


ALTER TABLE PAY_CONNECTOR_CONFIG_PARAMS MODIFY DESCRIZIONE VARCHAR2(500);


CREATE INDEX IDX_ALBEROPROCENDO_001 ON ALBEROPROC_ENDO(IDCOMUNE,CODICEINVENTARIO);


DROP INDEX IDX_DETT_POS_DEBITORIA_001;

CREATE INDEX IDX_DETT_POS_DEBITORIA_001 ON DETT_POSIZIONE_DEBITORIA(IDCOMUNE,ID_POSIZIONE_DEBITORIA,CF_ENTE_CREDITORE);
