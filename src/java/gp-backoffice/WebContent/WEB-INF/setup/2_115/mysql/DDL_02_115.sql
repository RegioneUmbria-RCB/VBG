UPDATE mercatipresenze_storico
        INNER JOIN
    autorizzazioni_concessioni
      ON 
	autorizzazioni_concessioni.idcomune=mercatipresenze_storico.idcomune AND
	autorizzazioni_concessioni.FK_IDAUT_COLLEGATA=mercatipresenze_storico.FK_AUTORIZZAZIONI_ID 
 SET    mercatipresenze_storico.FK_AUTORIZZAZIONI_ID = autorizzazioni_concessioni.FK_IDAUT_ATTUALE
    WHERE mercatipresenze_storico.FK_AUTORIZZAZIONI_ID=autorizzazioni_concessioni.FK_IDAUT_COLLEGATA;

ALTER TABLE pay_pos_deb_massive ADD MESSAGGIO VARCHAR(4000);

ALTER TABLE DOCUMENTIISTANZA MODIFY ID_BASE VARCHAR(600);


CREATE TABLE BOLL_CFG_CONTI (
  IDCOMUNE VARCHAR(6) NOT NULL,
  FK_BOLLCFGTIPO_ID NUMERIC(4,0) NOT NULL,
  FK_CONTO_ID NUMERIC(6,0) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=UTF8;
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT PK_BOLL_CFG_CONTI PRIMARY KEY (IDCOMUNE,FK_BOLLCFGTIPO_ID,FK_CONTO_ID);
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT FK_CFGCONTI_BLLCFGTIPO FOREIGN KEY (IDCOMUNE, FK_BOLLCFGTIPO_ID) REFERENCES BOLL_CFG_TIPO (IDCOMUNE, ID); 
ALTER TABLE BOLL_CFG_CONTI ADD CONSTRAINT FK_CFGCONTI_CONTI FOREIGN KEY (IDCOMUNE, FK_CONTO_ID) REFERENCES CONTI (IDCOMUNE, ID);

ALTER TABLE TIPIMOVIMENTODOCTIPO ADD COLUMN FLG_GENERAAUT DECIMAL(1,0) DEFAULT 0 NOT NULL;

ALTER TABLE TIPIMOVIMENTODOCTIPO ADD COLUMN FASE_ESECUZIONE VARCHAR(30) NULL;


INSERT INTO segnaposti (SEGNAPOSTO, TEMPLATEBASE, TEMPLATEBASERTF) VALUES('LINKALLEGATI_NO_HYPERLINK', '<TABLE BORDER="1" BORDERCOLOR="black" CELLPADDING="1" CELLSPACING="1"><THEAD><TR><TH><CENTER>URL</CENTER></TH><TH><CENTER>NOME FILE</CENTER></TH><TH><CENTER>PIN</CENTER></TH></TR></THEAD><TBODY><TR><TD>@URL@</TD><TD>@NOMEFILE@</TD><TD>@PIN@</TD></TR></TBODY></TABLE>', '\\\\trowd 
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
}');



ALTER TABLE PAY_CONNECTOR_CONFIG ADD FK_WS_CARICAMENTO_MASSIVO DECIMAL(9,0);

ALTER TABLE PAY_CONNECTOR_CONFIG ADD CONSTRAINT PAYCONN_FK_CARIC_MASSIVO FOREIGN KEY (CODICE,FK_WS_CARICAMENTO_MASSIVO) REFERENCES pay_connector_ws_endpoint (CODICE_CONNETTORE, ID) ;
