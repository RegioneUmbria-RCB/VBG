ALTER TABLE DYN2_MASSIVE ADD FLG_ELIMINATA NUMBER(1,0);
CREATE TABLE RESPONSABILI_SCADENZARIO (
IDCOMUNE VARCHAR2(6 char) NOT NULL,
ID NUMBER(10, 0) NOT NULL,
CODICERESPONSABILE NUMBER(8,0),
AMBITO VARCHAR2(20 char),
CHIAVE VARCHAR2(25 char),
VALORE VARCHAR2(50 char)
);
ALTER TABLE RESPONSABILI_SCADENZARIO ADD CONSTRAINT PK_RESPONSABILI_SCADENZARIO PRIMARY KEY (IDCOMUNE,ID);
ALTER TABLE RESPONSABILI_SCADENZARIO ADD CONSTRAINT FK_RESPSCAD_RESP FOREIGN KEY(IDCOMUNE,CODICERESPONSABILE) REFERENCES RESPONSABILI (IDCOMUNE,CODICERESPONSABILE);
CREATE INDEX IDX_RESPONSABILISCAD_001 ON RESPONSABILI_SCADENZARIO(IDCOMUNE,CODICERESPONSABILE,AMBITO);

ALTER TABLE SEGNAPOSTI ADD TEMPLATEBASERTF_CLOB CLOB; 
UPDATE SEGNAPOSTI SET TEMPLATEBASERTF_CLOB=TEMPLATEBASERTF;
ALTER TABLE SEGNAPOSTI DROP COLUMN TEMPLATEBASERTF; 
ALTER TABLE SEGNAPOSTI ADD TEMPLATEBASERTF CLOB; 
UPDATE SEGNAPOSTI SET TEMPLATEBASERTF=TEMPLATEBASERTF_CLOB;
ALTER TABLE SEGNAPOSTI DROP COLUMN TEMPLATEBASERTF_CLOB; 
ALTER TABLE SEGNAPOSTI MODIFY TEMPLATEBASERTF  NOT NULL;

UPDATE segnaposti SET 
templatebase='<TABLE BORDER="1" BORDERCOLOR="black" CELLPADDING="1" CELLSPACING="1"><THEAD><TR><TH><CENTER>DESCRIZIONE</CENTER></TH><TH><CENTER>NOME FILE</CENTER></TH><TH><CENTER>IMPRONTA HASH FORMATO SHA256</CENTER></TH></TR></THEAD><TBODY><TR><TD>@DESCRIZIONE@</TD><TD>@NOMEFILE@</TD><TD>@SHA256@</TD></TR></TBODY></TABLE>',
templatebasertf='\\trowd 
\\irow0\\irowband0\\ts15\\trgaph70\\trleft5\\trbrdrt\\brdrs\\brdrw10 
\\trftsWidth1\\trftsWidthB3\\trautofit1\\trpaddl108\\trpaddr108\\trpaddfl3\\trpaddft3\\trpaddfb3\\trpaddfr3\\tblrsid14097743\\tbllkhdrrows\\tbllkhdrcols\\tbllknocolband\\tblind0\\tblindtype3 
\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx3214\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx6423\\clvertalt
\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3210\\clshdrawnil 
\\cellx9633\\pard\\plain 
\\ltrpar
\\ql 
\\li0\\ri0\\widctlpar\\intbl\\wrapdefault\\aspalpha\\aspnum\\faauto\\adjustright\\rin0\\lin0\\yts15 
\\rtlch\\fcs1 
\\af31507\\afs22\\alang1025 
\\ltrch\\fcs0 
\\f31506\\fs22\\lang1040\\langfe1033\\cgrid\\langnp1040\\langfenp1033 
{\\rtlch\\fcs1 
\\af31507 
\\ltrch\\fcs0 
\\insrsid14097743 
\\b 
DESCRIZIONE\\cell 
NOME FILE\\cell 
IMPRONTA HASH FORMATO SHA256\\cell 
\\b0
}\\pard\\plain 
\\ltrpar\\ql 
\\li0\\ri0\\sa160\\sl259\\slmult1\\widctlpar\\intbl\\wrapdefault\\aspalpha\\aspnum\\faauto\\adjustright\\rin0\\lin0 
\\rtlch\\fcs1 \\af31507\\afs22\\alang1025 
\\ltrch\\fcs0 
\\f31506\\fs22\\lang1040\\langfe1033\\cgrid\\langnp1040\\langfenp1033 
{\\rtlch\\fcs1 
\\af31507 
\\ltrch\\fcs0 
\\insrsid14097743 
\\trowd 
\\irow0\\irowband0\\ts15\\trgaph70\\trleft5\\trbrdrt\\brdrs\\brdrw10 
\\trftsWidth1\\trftsWidthB3\\trautofit1\\trpaddl108\\trpaddr108\\trpaddfl3\\trpaddft3\\trpaddfb3\\trpaddfr3\\tblrsid14097743\\tbllkhdrrows\\tbllkhdrcols\\tbllknocolband\\tblind0\\tblindtype3 
\\clvertalt\\clbrdrt
\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx3214\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx6423\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3210\\clshdrawnil 
\\cellx9633\\row 
}\\pard\\plain 
\\ltrpar
\\ql 
\\li0\\ri0\\widctlpar\\intbl\\wrapdefault\\aspalpha\\aspnum\\faauto\\adjustright\\rin0\\lin0\\yts15 
\\rtlch\\fcs1 
\\af31507\\afs22\\alang1025 
\\ltrch\\fcs0 
\\f31506\\fs22\\lang1040\\langfe1033\\cgrid\\langnp1040\\langfenp1033 
{\\rtlch\\fcs1 
\\af31507 
\\ltrch\\fcs0 
\\insrsid14097743 
@DESCRIZIONE@\\cell 
@NOMEFILE@\\cell 
@SHA256@\\cell 
}\\pard\\plain 
\\ltrpar\\ql 
\\li0\\ri0\\sa160\\sl259\\slmult1\\widctlpar\\intbl\\wrapdefault\\aspalpha\\aspnum\\faauto\\adjustright\\rin0\\lin0 
\\rtlch\\fcs1 
\\af31507\\afs22\\alang1025 
\\ltrch\\fcs0 
\\f31506\\fs22\\lang1040\\langfe1033\\cgrid\\langnp1040\\langfenp1033 
{\\rtlch\\fcs1 
\\af31507 
\\ltrch\\fcs0 
\\insrsid14097743 
\\trowd 
\\irow1\\irowband1\\lastrow 
\\ts15\\trgaph70\\trleft5\\trbrdrt\\brdrs\\brdrw10 
\\trftsWidth1\\trftsWidthB3\\trautofit1\\trpaddl108\\trpaddr108\\trpaddfl3\\trpaddft3\\trpaddfb3\\trpaddfr3\\tblrsid14097743\\tbllkhdrrows\\tbllkhdrcols\\tbllknocolband\\tblind0\\tblindtype3 
\\clvertalt\\clbrdrt
\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx3214\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 
\\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3209\\clshdrawnil 
\\cellx6423\\clvertalt\\clbrdrt\\brdrs\\brdrw10 
\\clbrdrl\\brdrs\\brdrw10 \\clbrdrb\\brdrs\\brdrw10 
\\clbrdrr\\brdrs\\brdrw10 
\\cltxlrtb\\clftsWidth3\\clwWidth3210\\clshdrawnil 
\\cellx9633\\row 
}'
WHERE segnaposto='ZIPLOGICO_TABELLA_HASH';

