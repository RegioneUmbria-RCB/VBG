<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes" encoding="utf-8" />
  <xsl:template match="pratica">
	<html>
		<body>
			<center style="font-family: Verdana; font-size: 14px">
				<!-- 
				<div style="font-size: 28px; padding: 20px; text-align: center; margin-bottom: 20px;">
					Comune di 
				</div>
				 -->
				<div style="font-size: 28px; padding: 20px; text-align: center; ">
					CERTIFICATO DI INVIO (CON ERRORE)
				</div>

				<div style="margin-bottom: 20px;">
					(Ricezione Pratica Telematica, ai sensi dell'art. 5, comma 4, del DPR 160/2010)
				</div>

				<div style="padding: 5px; margin-top: 20px; text-align: justify; margin-bottom: 20px;">
					La presente ricevuta telematica attesta l'avvenuta ricezione della pratica.
				</div>

				<div>
					<table style="text-align: left;" width="100%">
						<!-- 
						<tr>
							<td valign="top">Ufficio ricevente:</td>
							<td valign="top">
								<b>
									Commercio
								</b>
							</td>
						</tr>
						 -->
						<tr>
							<td valign="top">N. Istanza Telematica:</td>
							<td valign="top">
								<b>
									<xsl:value-of select="numero"/>
								</b>
							</td>
						</tr>
						<!-- 
						<tr>
							<td valign="top">Errore:</td>
							<td valign="top">
								<b>
									<xsl:value-of select="errore"/>
								</b>
							</td>
						</tr>
						 -->
					</table>
				</div>	

				<div style="padding: 5px; text-align: center; font-size: 18px">
					SEZIONE INFORMATIVA
				</div>

				<div style="margin-bottom: 20px; text-align: justify;">
					<p>
						L'amministrazione competente, in caso di accertata carenza dei requisiti e dei presupposti di cui al
						comma 1 L. 30 luglio 2010 , n. 122, nel termine di sessanta giorni dal ricevimento della segnalazione di cui al medesimo comma,
						ha facoltà di adottare motivati provvedimenti di divieto di prosecuzione dell'attività e di rimozione degli eventuali effetti dannosi di essa,
						salvo che, ove ciò sia possibile, l'interessato provveda a conformare alla normativa vigente detta attività ed i suoi effetti entro un termine
						fissato dall'amministrazione, in ogni caso non inferiore a trenta giorni.
					</p>
					<p>
						E' fatto comunque salvo il potere dell'amministrazione competente di assumere determinazioni in via di autotutela,
						ai sensi degli articoli 21-quinquies e 21-nonies L. 30 luglio 2010 , n. 122.
						In caso di dichiarazioni sostitutive di certificazione e dell'atto di notorieta' false o mendaci,
						l'amministrazione, ferma restando l'applicazione delle sanzioni penali di cui al comma 6,
						nonché di quelle di cui al capo VI del testo unico di cui al decreto del Presidente della Repubblica 28 dicembre 2000, n. 445,
						può sempre e in ogni tempo adottare i provvedimenti di cui al primo periodo.
					</p>
					<p>
						Rimedi esperibili in caso di provvedimento negativo di divieto di prosecuzione dell'attività e/o di rimozione
						degli eventuali effetti dannosi di essa e/o determinazioni in via di autotutela: <b>ricorso al TAR</b>
					</p>
				</div>
			</center>
		</body>
	</html>
  </xsl:template>
  <xsl:template name="FormatDate">
    <xsl:param name="DateTime"/>
    <xsl:variable name="dd">
      <xsl:value-of select="substring($DateTime,9,2)"/>
    </xsl:variable>
    <xsl:variable name="mm">
      <xsl:value-of select="substring($DateTime,6,2)"/>
    </xsl:variable>
    <xsl:variable name="yyyy">
      <xsl:value-of select="substring($DateTime,1,4)"/>
    </xsl:variable>
    <xsl:value-of select="$dd"/>
    <xsl:value-of select="'/'"/>
    <xsl:value-of select="$mm"/>
    <xsl:value-of select="'/'"/>
    <xsl:value-of select="$yyyy"/>
  </xsl:template>
</xsl:stylesheet>