<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n="http://areariservata.gp.pal.gruppoinit.it/domain" xmlns:ns2="http://sigepro.init.it/rte/types" version="1.0">
  <xsl:output method="html" indent="yes" encoding="utf-8" />
  <xsl:decimal-format name="european" decimal-separator=',' grouping-separator='.' />
  <xsl:template match="n:NuovaIstanzaType">
    <html>
      <head>
		<style media="all">
			.etichetta{
				background-color: #efefef;
			}
			.dato{
				font-weight: bold;
			}
			.titoloSezione {
				font-size: 0.8em;
				color: #444369;
				font-weight: bold;
				text-decoration: none;
				text-transform: uppercase;
				text-align: left;
				margin-top: 5px;
				background-color: #efefef;
			}
			.testoesteso {
				font-size: 0.9em;
			}
			.etichettaControllo {
				
			}
        </style>
      </head>
      <body style="font-family: Verdana; font-size: 12px;background-color:#ffffff">
        <center style="font-size: 16px;font-weight:bold;">
          Presentazione Segnalazione certificata inizio attività / comunicazione
        </center>
        <table width="100%" border="1px" cellpadding="0" cellspacing="0" height="100px">
          <tr>
            <td width="50%">
              <span class="dato">
                Comune di Perugia<br />
                Sportello Unico
              </span>
            </td>
            <td width="50%">
              <span class="dato">Pratica n. <xsl:value-of select="n:dettaglioPratica/ns2:numeroPratica"/></span>
              <br/>
              <br/>
            </td>
          </tr>
        </table>
        <br/>
        <br/>
        <div style="width:100%;text-align:left;">
          <table width="100%" border="1px" cellpadding="2" cellspacing="0">
            <xsl:if test="(n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale != '')">
              <tr>
                <td width="25%" class="etichetta">Il sottoscritto (Cognome/Nome)</td>
                <td class="dato" width="25%"><xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:cognome"/>/<xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:nome"/>
					      </td>
                <td width="25%" class="etichetta">Codice Fiscale</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Nato/a a</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:comuneNascita/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Il</td>
                <td class="dato" width="25%">
                  <xsl:call-template name="FormatDate">
                    <xsl:with-param name="DateTime" select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:dataNascita"/>
                  </xsl:call-template>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Residente in</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:comune/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Prov.</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:provincia"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Via/Loc.</td>
                <td colspan="3" class="dato" width="75%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:indirizzo"/>/
                   <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:residenza/ns2:localita"/>
                </td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">E-mail</td>
                <td colspan="3" class="dato" width="75%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:email"/>
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
                  <xsl:when test="(n:dettaglioPratica/ns2:intermediario/ns2:personaFisica/ns2:codiceFiscale != '')">
                  <td width="25%" class="etichetta">Cognome/Nome</td>
                  </xsl:when>
                  <xsl:otherwise>
                  <td width="25%" class="etichetta">Il sottoscritto (Cognome/Nome)</td>
                  </xsl:otherwise>
              </xsl:choose> 
              <td class="dato" width="25%"><xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:cognome"/>/<xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:nome"/>
					    </td>
              <td width="25%" class="etichetta">Codice Fiscale</td>
              <td class="dato" width="25%">
                <xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:codiceFiscale"/>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Nato/a a</td>
              <td class="dato" width="25%">
                <xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:comuneNascita/ns2:comune"/>
              </td>
              <td width="25%" class="etichetta">Il</td>
              <td class="dato" width="25%">
                <xsl:call-template name="FormatDate">
                  <xsl:with-param name="DateTime" select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:dataNascita"/>
                </xsl:call-template>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Residente in</td>
              <td class="dato" width="75%" colspan="3">
                <xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:comune/ns2:comune"/>
              </td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">Via/Loc. </td>
              <td colspan="3" class="dato" width="75%"><xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:indirizzo"/>/

						<xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:residenza/ns2:localita"/>

					</td>
            </tr>
            <tr>
              <td width="25%" class="etichetta">E-mail </td>
              <td colspan="3" class="dato" width="75%">
                <xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:anagrafica/ns2:email"/>
              </td>
            </tr>
            <xsl:if test="(n:dettaglioPratica/ns2:aziendaRichiedente/ns2:ragioneSociale != '')">
              <tr>
                <td colspan="4" width="100%" class="etichetta">

							In qualità di <span class="dato"><xsl:value-of select="n:dettaglioPratica/ns2:richiedente/ns2:ruolo"/></span>

						</td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Di</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:aziendaRichiedente/ns2:ragioneSociale"/>
                </td>
                <td width="25%" class="etichetta">C.F./P.IVA</td>
                <td class="dato" width="25%"><xsl:value-of select="n:dettaglioPratica/ns2:aziendaRichiedente/ns2:codiceFiscale"/>/

							<xsl:value-of select="n:dettaglioPratica/ns2:aziendaRichiedente/ns2:partitaIva"/>

						</td>
              </tr>
              <tr>
                <td width="25%" class="etichetta">Con sede in </td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:aziendaRichiedente/ns2:sedeLegale/ns2:comune/ns2:comune"/>
                </td>
                <td width="25%" class="etichetta">Via/Loc.</td>
                <td class="dato" width="25%">
                  <xsl:value-of select="n:dettaglioPratica/ns2:aziendaRichiedente/ns2:sedeLegale/ns2:indirizzo"/>
                </td>
              </tr>
            </xsl:if>
          </table>
          <table width="100%" border="1px" cellpadding="0" cellspacing="0">
            <tr>
              <td width="25%" class="etichetta">Inoltra la domanda per </td>
              <td colspan="3" width="75%" style="font-weight:bold">
                <xsl:value-of select="n:dettaglioPratica/ns2:oggetto"/>
              </td>
            </tr>
          </table>
           <hr/>
          <xsl:if test="count(n:dettaglioPratica/ns2:procedimenti) &gt; 0">
              <div style="text-align:center">
                <h3>
						    Elenco degli endoprocedimenti attivati
					   	  <xsl:value-of select="Endoprocedimento/Procedimento"/>
					      </h3>
              </div>
              <ul>
              <xsl:for-each select="n:dettaglioPratica/ns2:procedimenti">
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
          SCHEDEDINAMICHE
          <br/>
          <br/>
          <xsl:if test="count(n:dettaglioPratica/ns2:oneri) &gt; 0">
	          <div style="text-align:center">
	            <h3>
							   Diritti/Oneri 
				 </h3>
	          </div>
	          <table  width="100%" border="1px" cellpadding="2" cellspacing="0" height="100px">
	            <xsl:for-each select="n:dettaglioPratica/ns2:oneri">
	              <tr>
	                <td class="etichetta">
		              	<xsl:if test="(ns2:codiceProcedimento!= '')">
		              		<xsl:variable name="codiceEndoProcedimento" select="ns2:codiceProcedimento"/>
		              		<xsl:for-each select="//n:dettaglioPratica/ns2:procedimenti">		              			
		              			<xsl:if test="ns2:codice = $codiceEndoProcedimento">
									<xsl:value-of select="ns2:descrizione"/>	&#160;	              		
		              			</xsl:if>
		              		</xsl:for-each>		              		
		              	</xsl:if>
		              	<xsl:if test="(ns2:codiceProcedimento = '')">
		              		Intervento selezionato<br/>(<xsl:value-of select="//n:dettaglioPratica/ns2:intervento/ns2:descrizione"/>)
		              	</xsl:if>
		              	- <xsl:value-of select="ns2:causale/ns2:causale" />
	              	</td>
	              	<td class="dato" style="text-align: right;"><xsl:value-of select="format-number(ns2:importo, '#.##0,00','european')"/>&#160;&#8364;</td>              	
	              </tr>
	            </xsl:for-each>
	            <tr>
	            	<td class="dato">Totale</td>
	            	<td class="dato" style="text-align: right;"><xsl:value-of select="format-number(sum(//n:dettaglioPratica/ns2:oneri/ns2:importo), '#.##0,00','european')"/>&#160;&#8364;</td>
	            </tr>
	          </table>
          </xsl:if>
          <br/>
          <br/>
          <xsl:if test="count(n:dettaglioPratica/ns2:procedimenti) &gt; 0">
              <xsl:for-each select="n:dettaglioPratica/ns2:procedimenti">
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
