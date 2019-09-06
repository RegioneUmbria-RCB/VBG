<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<fieldset>
					<legend>Anagrafiche</legend>
					<div>
						<table width="100%">
							<tr>
								<th>Codice Fiscale / P.Iva</th>
								<th>Nominativo</th>
								<th>In qualità di</th>
							</tr>
							<xsl:for-each select="Istanze/Richiedente">
								<tr>
									<td>
										<xsl:choose>
											<xsl:when test="string-length(PARTITAIVA)!=0">
												<xsl:value-of select="PARTITAIVA"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="CODICEFISCALE"/>
											</xsl:otherwise>
										</xsl:choose>
										</td>
									
									<td>
										<xsl:value-of select="NOMINATIVO"/> <xsl:value-of select="NOME"/>
									</td>

									<td>Richiedente</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="Istanze/AziendaRichiedente">
								<tr>
									<td>
										<xsl:choose>
											<xsl:when test="string-length(PARTITAIVA)!=0">
												<xsl:value-of select="PARTITAIVA"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="CODICEFISCALE"/>
											</xsl:otherwise>
										</xsl:choose>
									</td>

									<td>
										<xsl:value-of select="NOMINATIVO"/>
										<xsl:value-of select="NOME"/>
									</td>

									<td>Azienda Richiedente</td>
								</tr>
							</xsl:for-each>
							<xsl:for-each select="Istanze/Tecnico">
								<tr>
									<td>
										<xsl:choose>
											<xsl:when test="string-length(PARTITAIVA)!=0">
												<xsl:value-of select="PARTITAIVA"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="CODICEFISCALE"/>
											</xsl:otherwise>
										</xsl:choose>
									</td>

									<td>
										<xsl:value-of select="NOMINATIVO"/>
										<xsl:value-of select="NOME"/>
									</td>

									<td>Azienda Richiedente</td>
								</tr>
							</xsl:for-each>
						</table>
					</div>
				</fieldset>

				<fieldset>
					<legend></legend>
					
				</fieldset>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>