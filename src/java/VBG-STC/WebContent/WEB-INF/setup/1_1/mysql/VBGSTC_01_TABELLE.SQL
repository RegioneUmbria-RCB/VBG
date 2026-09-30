ALTER TABLE PRATICHE MODIFY IDPRATICA VARCHAR(60);
ALTER TABLE PRATICHE MODIFY NUMPRATICA VARCHAR(60);


CREATE OR REPLACE ALGORITHM=TEMPTABLE VIEW VW_MESSAGGIATTIVITA (ID, MITT_IDENTE, MITT_IDSPORTELLO, MITT_IDPRATICA, MITT_NUMPRATICA, ATT_MITT_ID, ATT_MITT_TIPO, ATT_MITT_IDPROC, DEST_IDENTE, DEST_IDSPORTELLO, DEST_IDPRATICA, DEST_NUMPRATICA, ATT_DEST_ID, ATT_DEST_TIPO, ATT_DEST_IDPROC)
AS
  SELECT messaggiattivita.id,
    richieste.idente mitt_idente ,
    richieste.idsportello mitt_idsportello,
    richieste.idpratica mitt_idpratica ,
    richieste.numpratica mitt_numpratica ,
    att_richieste.idattivita att_mitt_id ,
    att_richieste.tipoattivita att_mitt_tipo ,
    att_richieste.idprocedimento att_mitt_idproc ,
    risposte.idente dest_idente ,
    risposte.idsportello dest_idsportello ,
    risposte.idpratica dest_idpratica ,
    risposte.numpratica dest_numpratica,
    att_risposte.idattivita att_dest_id ,
    att_risposte.tipoattivita att_dest_tipo ,
    att_risposte.idprocedimento att_dest_idproc
  FROM messaggiattivita
  INNER JOIN attivita att_richieste
  ON att_richieste.id= messaggiattivita.fkidrichiesta
  INNER JOIN pratiche richieste
  ON richieste.id=att_richieste.fkidpratiche
  INNER JOIN attivita att_risposte
  ON att_risposte.id= messaggiattivita.fkidrisposta
  INNER JOIN pratiche risposte
  ON risposte.id=att_risposte.fkidpratiche
  ORDER BY messaggiattivita.id;
  
  
  
CREATE OR REPLACE ALGORITHM=TEMPTABLE VIEW VW_MESSAGGIPRATICHE (ID, MITT_IDENTE, MITT_IDSPORTELLO, MITT_IDPRATICA, MITT_NUMPRATICA, DEST_IDENTE, DEST_IDSPORTELLO, DEST_IDPRATICA, DEST_NUMPRATICA)
AS
  SELECT messaggipratiche.id,
    richieste.idente mitt_idente ,
    richieste.idsportello mitt_idsportello,
    richieste.idpratica mitt_idpratica,
    richieste.numpratica mitt_numpratica,
    risposte.idente dest_idente ,
    risposte.idsportello dest_idsportello,
    risposte.idpratica dest_idpratica,
    risposte.numpratica dest_numpratica
  FROM messaggipratiche
  INNER JOIN pratiche richieste
  ON richieste.id= messaggipratiche.fkidrichiesta
  INNER JOIN pratiche risposte
  ON risposte.id= messaggipratiche.fkidrisposta
  ORDER BY messaggipratiche.id;
  
ALTER TABLE PRATICHE MODIFY IDENTE VARCHAR(20); 