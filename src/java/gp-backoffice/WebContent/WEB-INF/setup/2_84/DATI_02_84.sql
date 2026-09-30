INSERT INTO FO_CONFIGURAZIONEBASE (CODICE, ETICHETTA, FK_CONTESTO) VALUES (80, 'Fabbricato', 'AIP-FIL');

INSERT INTO verticalizzazioniparametribase (modulo, parametro, descrizione) VALUES ('COMPORTAMENTI_MERCATI', 'SETTORI_NON_SALVA_MERCEOLOGIE', 'contiene la lista dei codicesettore posteggi_settori.codicesettore per i quali escludere l''inserimento della categoria merceologica nella presenza di mercato e nel calcolo del coefficente di mercato');
INSERT INTO TIPICONTESTOESPORTAZIONE (CODICE, DESCRIZIONE) VALUES ('BOL','Esportazione bollettazione');

INSERT INTO VERTICALIZZAZIONIPARAMETRIBASE(MODULO, PARAMETRO, DESCRIZIONE) VALUES ('SIT_LDP', 'LDP_COMPONENTE', 'Nome del componente dal quale recuperare le informazioni di WSDL. Al momento censiti LIVORNO_AREE_PUBBLICHE(DEFAULT) e SIENA_EDILIZIA');


INSERT INTO CLMENU_JAVA(ID, DESCRIZIONE, PAGINA, MENULINK, SOFTWARE, JSP, SOFTWAREESCLUSI, LINK_STANDARD, TIPO_FUNZIONALITA, MENULINK_V2) VALUES (1048,'Gestione decodifiche','decodifiche/view.htm','00BT','TT','JAVA','AB','decodifiche/view.htm','S','00BT');
