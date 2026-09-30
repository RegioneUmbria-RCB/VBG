<?xml version="1.0" ?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:func="http://exslt.org/functions"
	xmlns:gruppoinit="http://gruppoinit.it/functions"
    extension-element-prefixes="func"
	>
<xsl:decimal-format name="european" decimal-separator=',' grouping-separator='.' />
<xsl:template match="RISPOSTA">
<html>
<head>
<style>
body{
	font-family: tahoma, verdana, arial;
	font-size: 12px;
}
td{
	vertical-align:top;
}
.hr {
	background-color: #FFFFFF;
	border-bottom: 1px solid #000000;
	height: 10px;
	margin: 10px 0;
	width: 760px;
}
.label{
	font-weight: bold;
	font-variant:small-caps;
}
.note {
	font-size: 8px;
}
.tablecaption {
	font-size: 16px;
	margin: 10px 0;
	text-align: left;
	font-weight: bold;
	font-style: italic;
}

</style>
</head>
<body>
<h3>Visura Infocamere <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DENOMINAZIONE'/></h3>
<br />
<br />
<table border="1" cellspacing="0" cellpadding="4" width="100%">
<caption class="tablecaption">ESTREMI IMPRESA</caption>
<tr>
	<td class="label" width="100px">DENOMINAZIONE</td>
	<td><xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DENOMINAZIONE'/></td>
</tr>
<tr>
	<td class="label">CODICE FISCALE</td>
	<td><xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/CODICE_FISCALE'/></td>
</tr>
<tr>
	<td class="label">PARTITA IVA</td>
	<td><xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/PARTITA_IVA'/></td>
</tr>
<tr>
	<td class="label">FORMA GIURIDICA</td>
	<td><xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/FORMA_GIURIDICA/DESCRIZIONE'/></td>
</tr>

<tr>
	<td class="label">DATI ISCRIZIONE REA</td>
	<td>
		NUMERO: <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/NREA'/>
		<br />
		PROVINCIA: <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/CCIAA'/>
	
		<br />
		DATA ISCRIZIONE: <xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/DATA)'/>		
		<br />
		
			CAUSALE CESSAZIONE:  <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/CESSAZIONE/CAUSALE'/>
			<br />
			DATA CANCELLAZIONE: 
				<xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/CESSAZIONE/DT_CANCELLAZIONE)' /> 					
			<br />
			DATA CESSAZIONE:  
				<xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/CESSAZIONE/DT_CESSAZIONE)' /> 					
			<br />
			DATA DENUNCIA CESSAZIONE:  
				<xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/ESTREMI_IMPRESA/DATI_ISCRIZIONE_REA/CESSAZIONE/DT_DENUNCIA_CESS)' />
	</td>
</tr>
</table>

<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/OGGETTO_SOCIALE">
<br />
<br />
<table border="1" cellspacing="0" cellpadding="4" width="100%">
<caption class="tablecaption">OGGETTO SOCIALE</caption>
<tr>	
	<td colspan="2"><xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/OGGETTO_SOCIALE'/></td>
</tr>
</table>
</xsl:if>

<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/DURATA_SOCIETA">
	<br />
	<br />
	<table border="1" cellspacing="0" cellpadding="4" width="100%">
	<caption class="tablecaption">DURATA SOCIETA'</caption>
	<tr>
		<td   width="100px" class="label">DATA COSTITUZIONE</td>
		<td>
			<xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/DURATA_SOCIETA/DT_COSTITUZIONE)' /> 	
			</td>
	</tr>
	<xsl:choose>
		<xsl:when test="boolean(//RISPOSTA/DATI/DATI_IMPRESA/DURATA_SOCIETA/DT_TERMINE)">
			<tr>
				<td class="label">DATA TERMINE</td>
				<td>
				<xsl:value-of select='gruppoinit:printdate(//RISPOSTA/DATI/DATI_IMPRESA/DURATA_SOCIETA/DT_TERMINE)' /> 	
				
				</td>
			</tr>
		</xsl:when>
	</xsl:choose>
	</table>
</xsl:if>

<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI">
	<br />
	<br />
	<table border="1" cellspacing="0" cellpadding="4" width="100%">
	<caption class="tablecaption">CAPITALI</caption>
	<tr>
		<td colspan="2" class="label">TOTALE QUOTE</td>
	</tr>
<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/TOTALE_QUOTE/NUMERO_AZIONI">	
	<tr>
		<td width="100px" class="label">NUMERO AZIONI</td>
		<td>
			<xsl:value-of select="gruppoinit:format-number(//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/TOTALE_QUOTE/NUMERO_AZIONI)"/>
		</td>
	</tr>
</xsl:if>
<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/TOTALE_QUOTE/AMMONTARE">	
	<tr>
		<td class="label">COSTO PER AZIONE</td>
		<td>
			<xsl:value-of select="gruppoinit:format-number(//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/TOTALE_QUOTE/AMMONTARE)"/>
			&#160;
			<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/TOTALE_QUOTE/VALUTA'/>
		</td>
	</tr>
</xsl:if>	
	<tr>
		<td colspan="2" class="label">CAPITALE SOCIALE</td>
	</tr>
<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/DELIBERATO">	
	<tr>
		<td class="label">DELIBERATO</td>
		<td>
			<xsl:value-of select="gruppoinit:format-number(//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/DELIBERATO)"/>
			&#160;
			<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/VALUTA'/>
		</td>
	</tr>
</xsl:if>	
<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/SOTTOSCRITTO">
	<tr>
		<td class="label">SOTTOSCRITTO</td>
		<td>
			<xsl:value-of select="gruppoinit:format-number(//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/SOTTOSCRITTO)"/>
			&#160;
			<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/VALUTA'/>
		</td>
	</tr>
</xsl:if>	
<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/VERSATO">	
	<tr>
		<td class="label">VERSATO</td>
		<td>
			<xsl:value-of select="gruppoinit:format-number(//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/VERSATO)"/>			
			&#160;
			<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/CAPITALI/CAPITALE_SOCIALE/VALUTA'/>
		</td>
	</tr>
</xsl:if>	
	</table>
</xsl:if>
<br />
<br />
<table border="1" cellspacing="0" cellpadding="4" width="100%">
<caption class="tablecaption">INFORMAZIONI SEDE</caption>
<tr>
	<td width="100px" class="label">INDIRIZZO</td>
	<td>
		<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/TOPONIMO">
			<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/TOPONIMO'/>
			&#160;
		</xsl:if>
		<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/VIA'/>
		<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/N_CIVICO">
			,&#160;N.CIV.&#160;<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/N_CIVICO'/>
		</xsl:if>		
		<br />
		<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/CAP'/>
		&#160;
		<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/COMUNE'/>
		<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/PROVINCIA">
			&#160;(<xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/PROVINCIA'/>)
		</xsl:if>		
	</td>
</tr>
<tr>
	<td class="label">RIFERIMENTI</td>
	<td>
		TELEFONO: <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/TELEFONO'/>
		<br />
		PEC: <xsl:value-of select='//RISPOSTA/DATI/DATI_IMPRESA/INFORMAZIONI_SEDE/INDIRIZZO/INDIRIZZO_PEC'/>
	</td>
</tr>
</table>


<br />
<br />
<table border="1" cellspacing="0" cellpadding="4" width="100%">
<caption class="tablecaption">PERSONE</caption>
<xsl:for-each select="//RISPOSTA/DATI/DATI_IMPRESA/PERSONE_SEDE/PERSONA">
	<tr>
		<td width="100px" class="label" valign="top">
			<ul>
			<xsl:for-each select="CARICHE/CARICA">
				<li><xsl:value-of select='DESCRIZIONE'/></li>
			</xsl:for-each>
			</ul>
		</td>
		<td>
			<xsl:if test="PERSONA_FISICA">
				NOMINATIVO: <xsl:value-of select='PERSONA_FISICA/NOME'/>&#160;<xsl:value-of select='PERSONA_FISICA/COGNOME'/>
				<br />
				<xsl:if test="PERSONA_FISICA/SESSO">
					SESSO: <xsl:value-of select='PERSONA_FISICA/SESSO'/>
				</xsl:if>
				<br />
				NATO\A 
				<xsl:if test="PERSONA_FISICA/ESTREMI_NASCITA/STATO">
					IN <xsl:value-of select='PERSONA_FISICA/ESTREMI_NASCITA/STATO' /> 
				</xsl:if>
				NEL COMUNE DI <xsl:value-of select='PERSONA_FISICA/ESTREMI_NASCITA/COMUNE' /> 
				<xsl:if test="PERSONA_FISICA/ESTREMI_NASCITA/PROVINCIA">
					(<xsl:value-of select='PERSONA_FISICA/ESTREMI_NASCITA/PROVINCIA' />)
				</xsl:if>
				&#160;
				<xsl:if test="PERSONA_FISICA/ESTREMI_NASCITA/DATA">
					IL <xsl:value-of select='gruppoinit:printdate(PERSONA_FISICA/ESTREMI_NASCITA/DATA)' /> 		
				</xsl:if>
				<br />
				CODICE FISCALE: <xsl:value-of select='PERSONA_FISICA/CODICE_FISCALE' />
				<br />
				INDIRIZZO:
				<xsl:if test="PERSONA_FISICA/INDIRIZZO/TOPONIMO">
					<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/TOPONIMO'/>
					&#160;
				</xsl:if>			
				<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/VIA'/>
				<xsl:if test="PERSONA_FISICA/INDIRIZZO/N_CIVICO">
					,&#160;N.CIV.&#160;<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/N_CIVICO'/>
				</xsl:if>		
				<br />
				<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/CAP'/>
				&#160;
				<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/COMUNE'/>
				<xsl:if test="PERSONA_FISICA/INDIRIZZO/PROVINCIA">
					&#160;(<xsl:value-of select='PERSONA_FISICA/INDIRIZZO/PROVINCIA'/>)
				</xsl:if>
			</xsl:if>	
			<xsl:if test="PERSONA_GIURIDICA">
				DENOMINAZIONE: <xsl:value-of select='PERSONA_GIURIDICA/DENOMINAZIONE'/>
				<br />
				CODICE FISCALE: <xsl:value-of select='PERSONA_GIURIDICA/CODICE_FISCALE' />
				<br />
				DATI ISCRIZIONE REA: NR# <xsl:value-of select='PERSONA_GIURIDICA/N_ISCRIZIONE_REA' />, CCIAA DI  <xsl:value-of select='PERSONA_GIURIDICA/CCIAA' />
				<br />
				DATA DI COSTITUZIONE: 
					<xsl:value-of select='gruppoinit:printdate(PERSONA_GIURIDICA/DT_COSTITUZIONE)' /> 		
				<br />
				INDIRIZZO:
				<xsl:if test="PERSONA_GIURIDICA/INDIRIZZO/TOPONIMO">
					<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/TOPONIMO'/>
					&#160;
				</xsl:if>			
				<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/VIA'/>
				<xsl:if test="PERSONA_GIURIDICA/INDIRIZZO/N_CIVICO">
					,&#160;N.CIV.&#160;<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/N_CIVICO'/>
				</xsl:if>		
				<br />
				<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/CAP'/>
				&#160;
				<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/COMUNE'/>
				<xsl:if test="PERSONA_GIURIDICA/INDIRIZZO/PROVINCIA">
					&#160;(<xsl:value-of select='PERSONA_GIURIDICA/INDIRIZZO/PROVINCIA'/>)
				</xsl:if>
			</xsl:if>	
		</td>
	</tr>
</xsl:for-each>
</table>

<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/LOCALIZZAZIONI">
	<br />
	<br />
	<table border="1" cellspacing="0" cellpadding="4" width="100%">
	<caption class="tablecaption">UNITA' LOCALI</caption>
	<tr>
		<td width="100px">PROVINCIA</td>
		<td>DETTAGLIO</td>	
	</tr>
	<xsl:for-each select="//RISPOSTA/DATI/DATI_IMPRESA/LOCALIZZAZIONI">
		<tr>
			<td class="label" valign="top">
				<xsl:value-of select='@provincia'/>
			</td>
			<td valign="top">
				<xsl:for-each select="LOCALIZZAZIONE">
				<ul>
					<li>
						<xsl:if test="//RISPOSTA/DATI/DATI_IMPRESA/LOCALIZZAZIONI">
							ATTIVITA': <xsl:value-of select='ATTIVITA'/>
						</xsl:if>
						<div>
							DATA APERTURA: <xsl:value-of select='gruppoinit:printdate(DT_APERTURA)' />
						</div>
						<div>
							TIPOLOGIA: <xsl:value-of select='NUMERO_TIPO/TIPO_1'/>
							<br/>
							INDIRIZZO:
									<xsl:if test="INDIRIZZO/TOPONIMO">
										<xsl:value-of select='INDIRIZZO/TOPONIMO'/>
										&#160;
									</xsl:if>
									
									<xsl:value-of select='INDIRIZZO/VIA'/>
									<xsl:if test="INDIRIZZO/N_CIVICO">
										,&#160;N.CIV.&#160;<xsl:value-of select='INDIRIZZO/N_CIVICO'/>
									</xsl:if>		
									<br />
									<xsl:value-of select='INDIRIZZO/CAP'/>
									&#160;<xsl:value-of select='INDIRIZZO/COMUNE'/>
									<xsl:if test="INDIRIZZO/PROVINCIA">
										&#160;(<xsl:value-of select='INDIRIZZO/PROVINCIA'/>)
									</xsl:if>	
						</div>
						<div>
							ATTIVITA ISTAT
							<ul>
							<xsl:for-each select="CODICE_ATECO_UL/ATTIVITA_ISTAT">
								<li>
									<div>
										CODICE: <xsl:value-of select='C_ATTIVITA'/>
										<br />
										CODIFICA: <xsl:value-of select='T_CODIFICA'/>
										<br />
										DESCRIZIONE: <xsl:value-of select='DESC_ATTIVITA'/>
										<br />
										<xsl:if test="DT_INIZIO_ATTIVITA">
											ATTIVO DAL: <xsl:value-of select='gruppoinit:printdate(DT_INIZIO_ATTIVITA)' />
										</xsl:if>
									</div>
								</li>
							</xsl:for-each>
							</ul>
						</div>
						<xsl:if test="CESSAZIONE_LOC">
							<div>
								CAUSALE CESSAZIONE:  <xsl:value-of select='CESSAZIONE_LOC/CAUSALE'/>
								<br />
								DATA CESSAZIONE:  
								<xsl:value-of select='gruppoinit:printdate(CESSAZIONE_LOC/DT_CESSAZIONE)' /> 					
								<br />
								DATA DENUNCIA CESSAZIONE:  
								<xsl:value-of select='gruppoinit:printdate(CESSAZIONE_LOC/DT_DENUNCIA_CESS)' />
							</div>	
						</xsl:if>
						</li>
					</ul>	
				</xsl:for-each>
			</td>
		</tr>
	</xsl:for-each>
	</table>
</xsl:if>

</body>
</html>
</xsl:template>
	<func:function name="gruppoinit:printdate">
        <xsl:param name="param1" />        
        <func:result>
			<xsl:if test="boolean($param1)">
          		<xsl:value-of select="substring($param1,7,2)"/>/<xsl:value-of select="substring($param1,5,2)"/>/<xsl:value-of select="substring($param1,1,4)"/>
			</xsl:if>	
        </func:result>
    </func:function>
	<func:function name="gruppoinit:format-number">
        <xsl:param name="param1" />        		
        <func:result>
			<xsl:if test="boolean($param1)">
				<xsl:variable name="theText" select="translate($param1,',','.')"/>				
				<xsl:value-of select="format-number($theText, '#.##0,##','european')"/>
			</xsl:if>	
        </func:result>
    </func:function>  
</xsl:stylesheet>