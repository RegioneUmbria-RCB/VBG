<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="modelloDatiDomanda">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=windows-1252"/>
	<title></title>
	<meta name="generator" content="LibreOffice 4.4.3.2 (Windows)"/>
	<meta name="created" content="2015-07-10T12:48:36.429000000"/>
	<meta name="changed" content="2015-07-10T12:55:12.500000000"/>
	<style>
	body{
	font-family: Arial, Verdana;
	font-size: 12px;
	}
	</style>
</head>
<body lang="it-IT" dir="ltr">

<table width="100%" cellpadding="0" cellspacing="0" style="page-break-before: always">
	<col width="256*"/>
	<tr>
		<td width="100%" valign="top" style="font-weight: bold;	background-color: #c0c0c0; border: 1px solid black;">
			<p align="center">AVVISO DI RICEVIMENTO</p>
		</td>
	</tr>
	<tr>
		<td width="100%" valign="top" style="border: none; padding: 0cm">
			<p align="right">IDENTIFICATIVO PRATICA: @NUMERO@</p>
		</td>
	</tr>
	<tr>
		<td width="100%" valign="top" style="border: none; padding: 0cm">
			<p>Il SUAP dell'Unione dei Comuni Colline Metallifere (Comune di @COMUNE@ - @IDENTIFICATIVOSUAPRFC53@)</p>
		</td>
	</tr>
	<tr>
		<td width="100%" valign="top" style="border: none; padding: 0cm">
			<p>Ha ricevuto in data @DATAORATRASMISSIONE@ la documentazione trasmessa</p>
		</td>
	</tr>
</table>


<table width="100%" cellpadding="4" cellspacing="0">
	<col width="66*" />
	<col width="190*" />
	<tr valign="top">
		<td width="20%" style="border-top: 1px double #808080; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p>OGGETTO DELLA SEGNALAZIONE</p>
		</td>
		<td width="80%" style="border-top: 1px double #808080; border-bottom: 1px double #808080; border-left: none; border-right: 1px double #808080; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0cm; padding-right: 0.1cm">
			<p>Codice regionale: <xsl:value-of select='//idSemantico[@nome="CODICE_ATTIVITA_REGIONALE"]/dato/@valore'/></p>
			<p><xsl:value-of select='//idSemantico[@nome="DESCR_ATTIVITA_REGIONALE"]/dato/@valore'/></p>
		</td>
	</tr>
</table>
<p><br/>
<br/>

</p>

<p align="center"><b>INFORMAZIONI ANAGRAFICHE DELL'IMPRESA</b></p>
<table width="100%" cellpadding="4" cellspacing="0">
	<col width="64*" />
	<col width="64*" />
	<col width="64*" />
	<col width="64*" />
	<tr>
		<td colspan="4" width="100%" valign="top" style="border: 1px double #808080; padding: 0.1cm">
			<p align="left">DENOMINAZIONE <xsl:value-of select='//idSemantico[@nome="IMPRESA.DENOMINAZIONE"]/dato/@valore'/></p>
		</td>
	</tr>
	<tr valign="top">
		<td width="25%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p align="left">Con sede legale nel comune di 
			</p>
		</td>
		<td width="25%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p align="left"><xsl:value-of select='//idSemantico[@nome="IMPRESA.SEDE_LEGALE.COMUNE"]/dato/@valore'/></p>
		</td>
		<td width="25%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p align="left">PROVINCIA DI</p>
			
		</td>
		<td width="25%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: 1px double #808080; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm">
			<p align="left"><xsl:value-of select='//idSemantico[@nome="IMPRESA.SEDE_LEGALE.PROVINCIA"]/dato/@valore'/></p>
		</td>
	</tr>
	<tr valign="top">
		<td width="25%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p align="left">IN (VIA, P.ZZA)</p>
		</td>
		<td colspan="3" width="75%" style="border-top: none; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: 1px double #808080; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm">
			<p align="left">
			<xsl:value-of select='//idSemantico[@nome="IMPRESA.SEDE_LEGALE.VIA"]/dato/@valore'/>
				<xsl:variable name="presenza_civico_impresa" select='//idSemantico[@nome="IMPRESA.SEDE_LEGALE.PRESENZA_CIVICO"]/dato/@valore' />
				<xsl:choose>
					<xsl:when test="$presenza_civico_impresa = 'Numero'">
						n. <xsl:value-of select='//idSemantico[@nome="IMPRESA.SEDE_LEGALE.CIVICO"]/dato/@valore'/>		
				</xsl:when>
			</xsl:choose>	
			</p>
		</td>
	</tr>
</table>

<p align="left"><br/>
<br/>

</p>
<table width="100%" cellpadding="4" cellspacing="0">
	<col width="128*" />
	<col width="128*" />
	<tr valign="top">
		<td width="50%" style="border-top: 1px double #808080; border-bottom: 1px double #808080; border-left: 1px double #808080; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm">
			<p align="left">ELENCO DEI DOCUMENTI INFORMATICI ALLEGATI</p>
		</td>
		<td width="50%" style="border: 1px double #808080; padding: 0.1cm">
			<p align="left">@ELENCOALLEGATI@</p>
		</td>
	</tr>
</table>

</body>
</html>
</xsl:template>
</xsl:stylesheet>