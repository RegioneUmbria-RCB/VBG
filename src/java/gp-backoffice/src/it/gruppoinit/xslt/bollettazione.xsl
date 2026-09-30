<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    
    <xsl:param name="tiporeport" />
    <xsl:param name="idbollettazione" />
    <xsl:param name="codiceanagrafe" />

  <!-- Output in HTML (non XML) -->
  <xsl:output method="html" indent="yes" encoding="UTF-8"/>

  <xsl:template match="/records">
    <xsl:choose>
    <xsl:when test="$tiporeport = 'pdf'">
    <style>
	
	div {
	font-family: "Helvetica Neue", Arial, sans-serif;
	}
	
	table {
	border-collapse: collapse;
	font-size: 15px;
	}

	thead th{
	text-align: left;
	padding: 10px 6px;
	font-size: 12px;
	color: var(--muted);
	font-weight: 700;
	letter-spacing: 0.6px;
	border-bottom: 1px solid;
	text-transform: uppercase;
	width:150px;
	}

	tbody td{
	padding: 12px 6px;
	vertical-align: middle;
	border-bottom: 1px solid rgba(0,0,0,0.04);
	text-transform: uppercase;
	}

	tbody tr:last-child td{
	border-bottom: none;
	}
    </style>
    </xsl:when>
    <xsl:otherwise>
    </xsl:otherwise>
    </xsl:choose>
    <div slot="body" style="position: relative">
	<h1 id="titolo" class="warning"><xsl:value-of select="nomeutente"/><br/><small><xsl:value-of select="descrizionebollettazione"/></small></h1>
    <div id="bollform" class="vbg-form">
    <span>Di seguito il dettaglio della composizione del pagamento di <b><xsl:value-of select="totale"/></b></span>
    <table id="tabella" class="vbg-table">
		<thead>
          <tr>
            <!-- Intestazioni dinamiche -->
            <xsl:for-each select="record[1]/*">
              <th><xsl:value-of select="name()"/></th>
            </xsl:for-each>
          </tr>
		</thead>		
          <tbody>
          <xsl:for-each select="record">
            <tr>
              <xsl:for-each select="*">
                <td><xsl:value-of select="."/></td>
              </xsl:for-each>
            </tr>
          </xsl:for-each>
		  </tbody>
        </table>		
		<xsl:choose>
		<xsl:when test="$tiporeport = 'html'">
		<div id="form-buttons">
				<a class="btn btn-primary" onclick="scaricaPDF({$idbollettazione},{$codiceanagrafe})">PDF</a>
				<a class="btn btn-secondary" onclick="document.getElementById('pop-up-boll').close();">Chiudi</a>
		</div>
		</xsl:when>
		<xsl:otherwise>
		</xsl:otherwise>
		</xsl:choose>
	</div>
	</div>
  </xsl:template>

</xsl:stylesheet>
