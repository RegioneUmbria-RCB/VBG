ALTER TABLE ALBEROPROC ADD FLAG_UNICA_DOMANDA NUMBER(1,0) DEFAULT 0;

COMMENT ON COLUMN ALBEROPROC.FLAG_UNICA_DOMANDA IS 'se spuntato sarà possibile presentare dall''online solamente una pratica per questo intervento, se viene impostata anche una data di scadenza dell''intervento allora per questa tipologia di pratiche non saranno visualizzate le scadenze con data successiva a quella indicata';
ALTER TABLE ONERITIPIRATEIZZAZIONE ADD (SPESE_RATEIZZAZIONE NUMBER(8,2) );
COMMENT ON COLUMN ONERITIPIRATEIZZAZIONE.SPESE_RATEIZZAZIONE IS 'Spesa da aggiungere alla prima rata';
