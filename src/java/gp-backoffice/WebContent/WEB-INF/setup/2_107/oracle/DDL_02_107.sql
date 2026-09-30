ALTER TABLE ISTANZE MODIFY NUMEROPROTOCOLLO VARCHAR2(30 CHAR);
ALTER TABLE MOVIMENTI MODIFY NUMEROPROTOCOLLO VARCHAR2(30 CHAR);

alter table tipimov_stc_mapping add  flg_sovrascrivi_amm_dest number(1,0) DEFAULT 0;

ALTER TABLE tipimov_stc_mapping ADD  flg_crea_zip_logico number(1,0) DEFAULT 0 ;

ALTER TABLE tipimov_stc_mapping ADD  flg_protocolla_documenti number(1,0) DEFAULT 0;


