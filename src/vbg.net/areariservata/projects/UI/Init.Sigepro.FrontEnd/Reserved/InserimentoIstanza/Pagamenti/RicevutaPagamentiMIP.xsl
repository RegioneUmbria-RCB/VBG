<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xmlns:t="http://www.gruppoinit.it/RicevutaPagamentiMIP" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" indent="yes" />	
	<xsl:decimal-format name="european" decimal-separator=',' grouping-separator='.' />
	<xsl:template match="/">
		<html>
			<head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
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
					border-bottom: 1px solid
					#000000;
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
					margin: 10px 0;
					text-align: left;
					font-weight: bold;
					font-style: italic;
					}

					.intestazione{
					font-weight: bold;
					font-variant:small-caps;
					background-color: #dedede;
					}
				</style>
			</head>
			<body>
				<br />
				<table border="1" cellspacing="0" cellpadding="4" width="600">
					<tr>
						<td class="intestazione" colspan="3" style="text-align: center">
							<b><xsl:value-of select='/t:Ricevuta/t:IntestazioneRicevuta' /></b>
							<br />
							<i>Ricevuta pagamento effettuato</i>
						</td>
					</tr>
					<tr>
						<td class="label" width="30%">Codice domanda Online</td>
						<td colspan="2">
							<xsl:value-of select='/t:Ricevuta/t:codiceDomanda' />
						</td>
					</tr>					
					<tr>
						<td class="label">Informazioni anagrafiche</td>
						<td colspan="2">
							<b>
								<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:nome' />
								&#160;
								<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:cognome' />
								&#160;(
								<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:codiceFiscale' />
								)
							</b>
							<br />
							Provincia:
							<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:indirizzo/t:provincia' />
							<br />
							Comune:
							<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:indirizzo/t:comune' />
							<br />
							Cap:
							<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:indirizzo/t:cap' />
							<br />
							Via/P.za:
							<xsl:value-of select='/t:Ricevuta/t:anagrafe/t:indirizzo/t:indirizzo' />
						</td>
					</tr>
					
					<xsl:choose>
						<xsl:when test="boolean(/t:Ricevuta/t:datiPagamento/t:mipPaymentData)">						
							<!-- in caso che la ricevuta sia di tipo MIP -->
						    <tr>
								<td class="label" width="30%">Numero operazione</td>
								<td colspan="2">
									<xsl:value-of select='/t:Ricevuta/t:datiPagamento/t:mipPaymentData/t:NumeroOperazione' />
								</td>
							</tr>
							<tr>
								<td class="label">Identificativo ordine</td>
								<td colspan="2">
									<xsl:value-of select='/t:Ricevuta/t:datiPagamento/t:mipPaymentData/t:IDOrdine' />
								</td>
							</tr>
							<tr>
								<td class="label">Informazioni sul pagamento</td>
								<td colspan="2">
									Sistema di pagamento:
									<xsl:value-of select='/t:Ricevuta/t:datiPagamento/t:mipPaymentData/t:SistemaPagamentoD' />
									<br />
									Circuito autorizzativo:
									<xsl:value-of select='/t:Ricevuta/t:datiPagamento/t:mipPaymentData/t:CircuitoAutorizzativoD' />
								</td>
							</tr>						
						</xsl:when>					
						<xsl:otherwise>
	   						<tr>
								<td class="label">Informazioni sul pagamento</td>
								<td colspan="2">
									<xsl:value-of select='/t:Ricevuta/t:datiPagamento/t:nonDefinito' />
								</td>
							</tr>
						</xsl:otherwise>
					</xsl:choose>

				</table>
				<table border="1" cellspacing="0" cellpadding="4" width="600">
					<tr>
						<th colspan="2" class="intestazione">Lista degli oneri pagati</th>
					</tr>
					<xsl:for-each select="/t:Ricevuta/t:listaOneriPagati">
						<tr>
							<td>
								<xsl:value-of select='t:descrizione' />
							</td>
							<td style="text-align: right">
								<xsl:value-of select="format-number(t:importo, '#.##0,00','european')"/>
								&#160; &#8364;
							</td>
						</tr>
					</xsl:for-each>
					<tr>
						<th>Totale</th>
						<th style="text-align: right">
							<xsl:value-of select="format-number(sum(/t:Ricevuta/t:listaOneriPagati/t:importo), '#.##0,00','european')" />
							&#160; &#8364;
						</th>
					</tr>
					<xsl:choose>
						<xsl:when test="boolean(/t:Ricevuta/t:datiPagamento/t:mipPaymentData)">
							<tr>
								<th>Totale importo transato</th>
								<th style="text-align: right">
									<xsl:value-of select="format-number(((/t:Ricevuta/t:datiPagamento/t:mipPaymentData/t:ImportoTransato) div 100), '#.##0,00','european')" />
									&#160; &#8364;
								</th>
							</tr>
						</xsl:when>
					</xsl:choose>	
				</table>
				<span class="note">
					
				</span>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>