ALTER TABLE ISTANZE MODIFY NUMEROPROTOCOLLO VARCHAR(30); 
ALTER TABLE MOVIMENTI MODIFY NUMEROPROTOCOLLO VARCHAR(30);

alter table tipimov_stc_mapping add column flg_sovrascrivi_amm_dest numeric(1,0) DEFAULT 0;

ALTER TABLE tipimov_stc_mapping ADD COLUMN flg_crea_zip_logico NUMERIC(1,0) DEFAULT 0;

ALTER TABLE tipimov_stc_mapping ADD COLUMN flg_protocolla_documenti NUMERIC(1,0) DEFAULT 0;
