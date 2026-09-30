<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n="http://sigepro.init.it/rte" xmlns:ns2="http://sigepro.init.it/rte/types" version="1.0">
  <xsl:output method="html" indent="yes" encoding="utf-8" />
  <xsl:template match="n:dettaglioPratica">
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		</head>
		<body>
			<center style="font-family: Verdana; font-size: 14px">
				<div style="font-size: 28px; padding: 20px; text-align: center; margin-bottom: 20px;">
					Comune di Perugia
				</div>
				<div style="font-size: 28px; padding: 20px; text-align: center; ">
					CERTIFICATO DI INVIO
				</div>

				<div style="margin-bottom: 20px;">
					(Ricezione Pratica Telematica, ai sensi dell'art. 5, comma 4, del DPR 160/2010)
				</div>

				<div style="padding: 5px; margin-top: 20px; text-align: justify; margin-bottom: 20px;">
					La presente ricevuta telematica attesta l'avvenuta ricezione della pratica presso il SUAP del Comune di Gubbio.
				</div>

				<div>
					<table style="text-align: left;" width="100%">
						<tr>
							<td valign="top">Ufficio ricevente:</td>
							<td valign="top">
								<b>
									Sportello Unico
								</b>
							</td>
						</tr>
						
						<tr>
							<td valign="top">Responsabile:</td>
							<td valign="top">
								<b>
									<xsl:value-of select="ns2:responsabileProcedimento"/>
								</b>
							</td>
						</tr>
						<tr>
							<td valign="top">Trasmissione dell'istanza: </td>
							<td valign="top">
								<b>
									<xsl:call-template name="FormatDate">
										<xsl:with-param name="DateTime" select="ns2:dettaglioPratica/ns2:dataPratica"/>
									</xsl:call-template>
								</b>
							</td>
						</tr>
						<tr>
							<td valign="top">N. Istanza Telematica:</td>
							<td valign="top">
								<b>
									<xsl:value-of select="ns2:dettaglioPratica/ns2:numeroPratica"/>
								</b>
							</td>
						</tr>
						<tr>
							<td valign="top">Protocollo</td>
							<td valign="top">
								N. <b>
									<xsl:value-of select="ns2:dettaglioPratica/ns2:numeroProtocolloGenerale"/>
								</b> Data. <b>
									<xsl:call-template name="FormatDate">
										<xsl:with-param name="DateTime" select="ns2:dettaglioPratica/ns2:dataProtocolloGenerale"/>
									</xsl:call-template>
								</b>
							</td>
						</tr>
						<tr>
							<td valign="top">Oggetto della SCIA:</td>
							<td valign="top">
													
								<xsl:value-of select="ns2:dettaglioPratica/ns2:oggetto"/>
							</td>
						</tr>
						<tr>
							<td valign="top" >Ubicazione dell'attività:</td>
							<td valign="top" >
								<span>
									<table width="100%">
										<xsl:for-each select="ns2:dettaglioPratica/ns2:localizzazione">
											<tr>
												<td valign="top">
													<b>
														<xsl:value-of select="ns2:denominazione"/>,&#160;
														<xsl:value-of select="ns2:civico"/>
													</b>
												</td>
											</tr>
										</xsl:for-each>
									</table>
								</span>
							</td>
						</tr>
						<tr>
							<td valign="top">Dati Catastali dell'immobile</td>
							<td valign="top" >
								<span>
									<table width="100%">
										<xsl:for-each select="ns2:dettaglioPratica/ns2:localizzazione">
											<xsl:for-each select="ns2:riferimentoCatastale">
											<tr>
												<td valign="top">Foglio:</td>
												<td valign="top">
													<b>
														<xsl:value-of select="ns2:foglio"/>
													</b>
												</td>
												<td valign="top">Particella:</td>
												<td valign="top">
													<b>
														<xsl:value-of select="ns2:particella"/>
													</b>
												</td>
												<td valign="top">Sub:</td>
												<td valign="top">
													<b>
														<xsl:value-of select="ns2:sub"/>
													</b>
												</td>
											</tr>
											</xsl:for-each>
										</xsl:for-each>
									</table>
								</span>
							</td>
						</tr>	
					</table>
				</div>
		  <xsl:for-each select="ns2:dettaglioPratica">
		  <div style="width:100%;text-align:left;">
          <table width="100%" border="1px" cellpadding="0" cellspacing="0">
            <xsl:if test="(ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale != '')">
              <tr>
                <td width="25%" class="etichetta">Il sottoscritto</td>
                <td class="dato" width="25%"><xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:cognome"/> 
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:nome"/>
					      </td>
                <td width="25%" class="etichetta">Codice Fiscale</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Nato/a a</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:comuneNascita/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Il</td>
                <td class="dato" width="25%">
                  <xsl:call-template name="FormatDate">
                    <xsl:with-param name="DateTime" select="ns2:intermediario/ns2:personaFisica/ns2:dataNascita"/>
                  </xsl:call-template>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Residente in</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:comune/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Prov.</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:provincia"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Via/Loc.</td>
                <td colspan="3" class="dato" width="75%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:indirizzo"/>/
                   <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:localita"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">E-mail</td>
                <td colspan="3" class="dato" width="75%">
                  <xsl:value-of select="ns2:intermediario/ns2:personaFisica/ns2:email"/>
                </td>
              </tr>
              <tr>
                <td colspan="4" width="100%" class="etichetta">
						    In qualità di <span class="dato">Intermediario </span> di
					      </td>
              </tr>
            </xsl:if>
            <tr>
              <xsl:choose>
                  <xsl:when test="(ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale != '')">
                  <td width="25%" class="etichetta">Cognome/Nome</td>
                  </xsl:when>
                  <xsl:otherwise>
                  <td width="25%" class="etichetta">Il sottoscritto</td>
                  </xsl:otherwise>
              </xsl:choose> 
              <td class="dato" width="25%"><xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:cognome"/> 
                <xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:nome"/>
					    </td>
              <td width="25%" class="etichetta">Codice Fiscale</td>
              <td class="dato" width="25%">
                <xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:codiceFiscale"/>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Nato/a a</td>
              <td class="dato" width="25%">
                <xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:comuneNascita/ns2:comune"/>
              </td>
              <td width="25%" class="etichetta">Il</td>
              <td class="dato" width="25%">
                <xsl:call-template name="FormatDate">
                  <xsl:with-param name="DateTime" select="ns2:richiedente/ns2:anagrafica/ns2:dataNascita"/>
                </xsl:call-template>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Residente in</td>
              <td class="dato" width="75%" colspan="3">
                <xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:comune/ns2:comune"/>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Via/Loc. </td>
              <td colspan="3" class="dato" width="75%"><xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:indirizzo"/>/

						<xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:localita"/>

					</td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">E-mail </td>
              <td colspan="3" class="dato" width="75%">
                <xsl:value-of select="ns2:richiedente/ns2:anagrafica/ns2:email"/>
              </td>
            </tr>
            <xsl:if test="(ns2:aziendaRichiedente/ns2:ragioneSociale != '')">
              <tr>
                <td colspan="4" width="100%" class="etichetta">

							In qualità di <span class="dato"><xsl:value-of select="ns2:richiedente/ns2:ruolo"/></span>

						</td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Di</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:aziendaRichiedente/ns2:ragioneSociale"/>
                </td>
                <td width="25%" class="etichetta">C.F./P.IVA</td>
                <td class="dato" width="25%"><xsl:value-of select="ns2:aziendaRichiedente/ns2:codiceFiscale"/>/

							<xsl:value-of select="ns2:aziendaRichiedente/ns2:partitaIva"/>

						</td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Con sede in </td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:aziendaRichiedente/ns2:sedeLegale/ns2:comune/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Via/Loc.</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="ns2:aziendaRichiedente/ns2:sedeLegale/ns2:indirizzo"/>
                </td>
              </tr>
            </xsl:if>
          </table>
          <table width="100%" border="1px" cellpadding="0" cellspacing="0">
            <tr>
              <td width="25%" class="etichetta">Inoltra la domanda per </td>
              <td colspan="3" width="75%" style="font-weight:bold">
                <xsl:value-of select="ns2:oggetto"/>
              </td>
            </tr>
          </table>
           <hr/>
          <xsl:if test="count(ns2:procedimenti) &gt; 0">
              <div style="text-align:center">
                <h3>
						    Elenco degli endoprocedimenti attivati
					   	  <xsl:value-of select="Endoprocedimento/Procedimento"/>
					      </h3>
              </div>
              <ul>
              <xsl:for-each select="ns2:procedimenti">
                    <li>
                      <div class="dato">
                        <b>
                          <xsl:value-of select="ns2:descrizione"/>
                        </b>
                      </div>
                    </li>
                </xsl:for-each>
              </ul>
          </xsl:if>
          <hr/>
          <br/>
          <br/>
          <div style="text-align:center">
            <h3>
						   Allegati dell'intervento 
			      </h3>
          </div>
          <ul>
            <xsl:for-each select="ns2:documenti">
              <li>
                <div class="dato"><xsl:value-of select="ns2:documento"/> 
						</div>
                <xsl:value-of select="ns2:allegati/ns2:allegato"/>
              </li>
            </xsl:for-each>
          </ul>
          <br/>
          <br/>
          <xsl:if test="count(ns2:procedimenti) &gt; 0">
              <xsl:for-each select="ns2:procedimenti">
              <xsl:if test="count(ns2:documenti) &gt; 0">
              <div class="dato">
                        <b>
                          <div style="text-align:center">
                            <h3>
                            Allegati dell'endoprocedimento 
                            <xsl:value-of select="ns2:descrizione"/>
                            </h3>
                          </div>
                        </b>
              </div>
              <ul>
                    <xsl:for-each select="ns2:documenti">
                    <li>
                      <div class="dato">
                        <xsl:value-of select="ns2:documento"/> 
						          </div>
                      <xsl:value-of select="ns2:allegati/ns2:allegato"/>
                    </li>
                    </xsl:for-each>
              </ul>
              </xsl:if>
              </xsl:for-each>
          </xsl:if>
        </div>
		</xsl:for-each>
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