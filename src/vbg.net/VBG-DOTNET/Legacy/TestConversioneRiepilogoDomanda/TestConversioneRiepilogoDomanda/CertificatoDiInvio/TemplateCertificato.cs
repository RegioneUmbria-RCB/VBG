using System;
using System.IO;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace TestConversioneRiepilogoDomanda.CertificatoDiInvio
{
    public class TemplateCertificato
    {
        private const string xslContainer = @"<xsl:stylesheet xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"" version=""1.0"" xmlns:types=""http://sigepro.init.it/rte/types"">
<xsl:output method=""html"" />		
	<xsl:template match=""/""><xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>{0}</xsl:template>

	<xsl:template name=""FormatDate"">
		<xsl:param name=""DateTime"" />

		<xsl:variable name=""dd"">
			<xsl:value-of select=""substring($DateTime,9,2)"" />
		</xsl:variable>

		<xsl:variable name=""mm"">
			<xsl:value-of select=""substring($DateTime,6,2)"" />
		</xsl:variable>

		<xsl:variable name=""yyyy"">
			<xsl:value-of select=""substring($DateTime,1,4)"" />
		</xsl:variable>

		<xsl:value-of select=""$dd"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$mm"" />
		<xsl:value-of select=""'/'"" />
		<xsl:value-of select=""$yyyy"" />
	</xsl:template>

</xsl:stylesheet>";

        private readonly string _xslString;
        private readonly string _estensioneFileModello;

        public TemplateCertificato(string xslString, string estensioneFileModello)
        {
            this._xslString = xslString;
            this._estensioneFileModello = estensioneFileModello;
        }


        internal byte[] ApplicaA(DatiCertificato dati)
        {
            var xslTemplate = String.Format(xslContainer, this._xslString);
            var transformedResult = TrasformaXml(dati.AsXmlString(), new StringReader(xslTemplate));

            return transformedResult;
        }

        internal string GetEstensione()
        {
            return this._estensioneFileModello;
        }

        private static byte[] TrasformaXml(string xml, TextReader xsl)
        {
            TextReader trXml = new StringReader(xml);

            XPathDocument xmlDocument = new XPathDocument(trXml);
            XPathDocument xslDocument = new XPathDocument(xsl);

            XslCompiledTransform transform = new XslCompiledTransform();
            transform.Load(xslDocument);

            var ms = new MemoryStream();

            transform.Transform(xmlDocument, null, ms);

            return ms.ToArray();
        }
    }
}
