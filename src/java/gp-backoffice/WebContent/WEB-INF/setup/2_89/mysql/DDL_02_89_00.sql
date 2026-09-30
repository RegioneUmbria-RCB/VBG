create table istoneri_dett_posizioni (
     idcomune                        varchar(6) not null,                 
     id                             numeric(10) not null,
     fk_istanzeoneri_id              numeric(10) not null,
     fk_dettposdebitoria_id          numeric(10) not null,
     
     PRIMARY KEY  (idcomune,id)
) ENGINE=INNODB DEFAULT CHARSET=UTF8;


  
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



INSERT INTO istoneri_dett_posizioni (id,idcomune,fk_istanzeoneri_id,fk_dettposdebitoria_id)  SELECT 
         @row_number:=CASE
          WHEN @idcomune = idcomune 
            THEN 
                @row_number + 1
            ELSE 
                 1
          END AS id,
     
          @idcomune:=idcomune idcomune,
         
          id AS fk_istanzeoneri_id, 
          fk_posdebdettaglio_id AS fk_dettposdebitoria_id 
FROM
    istanzeoneri,
    (SELECT @idcomune:=0,@row_number:=0) AS t
WHERE fk_posdebdettaglio_id IS NOT NULL 
ORDER BY 
    idcomune;

ALTER TABLE PAY_CONNECTOR_CONFIG_PARAMS MODIFY DESCRIZIONE VARCHAR(500);

CREATE INDEX IDX_ALBEROPROCENDO_001 ON ALBEROPROC_ENDO(IDCOMUNE,CODICEINVENTARIO);

DROP INDEX IDX_DETT_POS_DEBITORIA_001 on DETT_POSIZIONE_DEBITORIA;

CREATE INDEX IDX_DETT_POS_DEBITORIA_001 ON DETT_POSIZIONE_DEBITORIA(IDCOMUNE,ID_POSIZIONE_DEBITORIA,CF_ENTE_CREDITORE);
