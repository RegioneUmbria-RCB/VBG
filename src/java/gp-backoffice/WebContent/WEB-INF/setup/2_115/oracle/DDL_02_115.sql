UPDATE mercatipresenze_storico SET mercatipresenze_storico.FK_AUTORIZZAZIONI_ID = (SELECT FK_IDAUT_ATTUALE FROM autorizzazioni_concessioni
      WHERE 
	autorizzazioni_concessioni.idcomune=mercatipresenze_storico.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_storico.FK_AUTORIZZAZIONI_ID  ) WHERE  EXISTS (SELECT 1
             FROM autorizzazioni_concessioni
      WHERE 
	autorizzazioni_concessioni.idcomune=mercatipresenze_storico.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_storico.FK_AUTORIZZAZIONI_ID);	
	
	
ALTER TABLE pay_pos_deb_massive ADD MESSAGGIO VARCHAR2(4000);

ALTER TABLE DOCUMENTIISTANZA MODIFY ID_BASE VARCHAR2(600 CHAR);

CREATE TABLE BOLL_CFG_CONTI (
  IDCOMUNE VARCHAR2(6) NOT NULL,
  FK_BOLLCFGTIPO_ID NUMBER(4,0) NOT NULL,
  FK_CONTO_ID NUMBER(6,0) NOT NULL
);
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT PK_BOLL_CFG_CONTI PRIMARY KEY (IDCOMUNE,FK_BOLLCFGTIPO_ID,FK_CONTO_ID);
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT FK_CFGCONTI_BLLCFGTIPO FOREIGN KEY (IDCOMUNE, FK_BOLLCFGTIPO_ID) REFERENCES BOLL_CFG_TIPO (IDCOMUNE, ID); 
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT FK_CFGCONTI_CONTI FOREIGN KEY (IDCOMUNE, FK_CONTO_ID) REFERENCES CONTI (IDCOMUNE, ID);

ALTER TABLE TIPIMOVIMENTODOCTIPO ADD FLG_GENERAAUT NUMBER(1,0) DEFAULT 0 NOT NULL;

ALTER TABLE TIPIMOVIMENTODOCTIPO ADD FASE_ESECUZIONE VARCHAR2(30 CHAR) NULL;

INSERT INTO segnaposti (SEGNAPOSTO, TEMPLATEBASE, TEMPLATEBASERTF) VALUES('LINKALLEGATI_NO_HYPERLINK', '<TABLE BORDER="1" BORDERCOLOR="black" CELLPADDING="1" CELLSPACING="1"><THEAD><TR><TH><CENTER>URL</CENTER></TH><TH><CENTER>NOME FILE</CENTER></TH><TH><CENTER>PIN</CENTER></TH></TR></THEAD><TBODY><TR><TD>@URL@</TD><TD>@NOMEFILE@</TD><TD>@PIN@</TD></TR></TBODY></TABLE>',to_clob ('\\\\trowd 
\\\\irow0\\\\irowband0\\\\ts15\\\\trgaph70\\\\trleft5\\\\trbrdrt\\\\brdrs\\\\brdrw10 
\\\\trftsWidth1\\\\trftsWidthB3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid14097743\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 
\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx3214\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx6423\\\\clvertalt
\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3210\\\\clshdrawnil 
\\\\cellx9633\\\\pard\\\\plain 
\\\\ltrpar
\\\\ql 
\\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\yts15 
\\\\rtlch\\\\fcs1 
\\\\af31507\\\\afs22\\\\alang1025 
\\\\ltrch\\\\fcs0 
\\\\f31506\\\\fs22\\\\lang1040\\\\langfe1033\\\\cgrid\\\\langnp1040\\\\langfenp1033 
{\\\\rtlch\\\\fcs1 
\\\\af31507 
\\\\ltrch\\\\fcs0 
\\\\insrsid14097743 
\\\\b 
URL\\\\cell 
NOME FILE\\\\cell 
PIN\\\\cell 
\\\\b0
}\\\\pard\\\\plain 
\\\\ltrpar\\\\ql 
\\\\li0\\\\ri0\\\\sa160\\\\sl259\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 
\\\\rtlch\\\\fcs1 \\\\af31507\\\\afs22\\\\alang1025 
\\\\ltrch\\\\fcs0 
\\\\f31506\\\\fs22\\\\lang1040\\\\langfe1033\\\\cgrid\\\\langnp1040\\\\langfenp1033 
{\\\\rtlch\\\\fcs1 
\\\\af31507 
\\\\ltrch\\\\fcs0 
\\\\insrsid14097743 
\\\\trowd 
\\\\irow0\\\\irowband0\\\\ts15\\\\trgaph70\\\\trleft5\\\\trbrdrt\\\\brdrs\\\\brdrw10 
\\\\trftsWidth1\\\\trftsWidthB3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid14097743\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 
\\\\clvertalt\\\\clbrdrt
\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx3214\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx6423\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3210\\\\clshdrawnil 
\\\\cellx9633\\\\row 
}\\\\pard\\\\plain 
\\\\ltrpar
\\\\ql '));


update segnaposti set templatebasertf=templatebasertf  || to_clob(' 
\\\\li0\\\\ri0\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0\\\\yts15 
\\\\rtlch\\\\fcs1 
\\\\af31507\\\\afs22\\\\alang1025 
\\\\ltrch\\\\fcs0 
\\\\f31506\\\\fs22\\\\lang1040\\\\langfe1033\\\\cgrid\\\\langnp1040\\\\langfenp1033 
{\\\\rtlch\\\\fcs1 
\\\\af31507 
\\\\ltrch\\\\fcs0 
\\\\insrsid14097743 
{\\\\colortbl ;\\\\red0\\\\green0\\\\blue238;}
{\\\\field{\\\\*\\\\fldinst HYPERLINK "@URL@"}{\\\\fldrslt{\\\\ul\\\\cf1@DESCRIZIONE@}}}\\\\cell 
@NOMEFILE@\\\\cell 
@PIN@\\\\cell 
}\\\\pard\\\\plain 
\\\\ltrpar\\\\ql 
\\\\li0\\\\ri0\\\\sa160\\\\sl259\\\\slmult1\\\\widctlpar\\\\intbl\\\\wrapdefault\\\\aspalpha\\\\aspnum\\\\faauto\\\\adjustright\\\\rin0\\\\lin0 
\\\\rtlch\\\\fcs1 
\\\\af31507\\\\afs22\\\\alang1025 
\\\\ltrch\\\\fcs0 
\\\\f31506\\\\fs22\\\\lang1040\\\\langfe1033\\\\cgrid\\\\langnp1040\\\\langfenp1033 
{\\\\rtlch\\\\fcs1 
\\\\af31507 
\\\\ltrch\\\\fcs0 
\\\\insrsid14097743 
\\\\trowd 
\\\\irow1\\\\irowband1\\\\lastrow 
\\\\ts15\\\\trgaph70\\\\trleft5\\\\trbrdrt\\\\brdrs\\\\brdrw10 
\\\\trftsWidth1\\\\trftsWidthB3\\\\trautofit1\\\\trpaddl108\\\\trpaddr108\\\\trpaddfl3\\\\trpaddft3\\\\trpaddfb3\\\\trpaddfr3\\\\tblrsid14097743\\\\tbllkhdrrows\\\\tbllkhdrcols\\\\tbllknocolband\\\\tblind0\\\\tblindtype3 
\\\\clvertalt\\\\clbrdrt
\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx3214\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 
\\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3209\\\\clshdrawnil 
\\\\cellx6423\\\\clvertalt\\\\clbrdrt\\\\brdrs\\\\brdrw10 
\\\\clbrdrl\\\\brdrs\\\\brdrw10 \\\\clbrdrb\\\\brdrs\\\\brdrw10 
\\\\clbrdrr\\\\brdrs\\\\brdrw10 
\\\\cltxlrtb\\\\clftsWidth3\\\\clwWidth3210\\\\clshdrawnil 
\\\\cellx9633\\\\row 
}') where segnaposto='LINKALLEGATI_NO_HYPERLINK';


ALTER TABLE PAY_CONNECTOR_CONFIG ADD FK_WS_CARICAMENTO_MASSIVO NUMERIC(9,0); 

ALTER TABLE PAY_CONNECTOR_CONFIG ADD CONSTRAINT PAYCONN_FK_CARIC_MASSIVO FOREIGN KEY (CODICE,FK_WS_CARICAMENTO_MASSIVO) REFERENCES pay_connector_ws_endpoint (CODICE_CONNETTORE, ID) ;
