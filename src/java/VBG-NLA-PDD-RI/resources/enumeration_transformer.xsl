<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="text"/>
	<xsl:template match="/">
CODICE;DESCRIZIONE
<xsl:for-each select="restriction/enumeration">"<xsl:value-of select="normalize-space(@value)" />";"<xsl:value-of select="normalize-space(annotation/documentation)" />"&#xA;</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>