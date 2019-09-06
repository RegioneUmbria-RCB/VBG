using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.Common
{
    public class XslFile
    {
        string _xsl;
        const string _xslContainer = @"<xsl:stylesheet xmlns:xsl=""http://www.w3.org/1999/XSL/Transform"" version=""1.0"" xmlns:types=""http://sigepro.init.it/rte/types"">
<xsl:output method=""html"" />		
	<xsl:template match=""/"">{0}</xsl:template>

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

        public XslFile(byte[] xsl)
        {
            this._xsl = Encoding.UTF8.GetString(xsl);
        }

        public XslFile(string xsl)
        {
            this._xsl = xsl;
        }

        public string Trasforma(string xml)
        {
            var xsl = this._xsl;
            var includiXslInContainer = !xsl.StartsWith("<?xml");

            var xslEx = xsl;

            if (includiXslInContainer)
            {
                xslEx = String.Format(_xslContainer, xsl);
            }
            var stringReader = new StringReader(xslEx);

            var result = TrasformaXml(xml, stringReader);
            /*
            if (result[0] == 239 &&
                result[1] == 187 &&
                result[2] == 191)
            {
                var tmpBuffer = new Byte[result.Length - 3];

                Array.Copy(result, 3, tmpBuffer, 0, result.Length - 3);

                result = tmpBuffer;
            }
            */
            return Encoding.UTF8.GetString(result);
        }

        private static byte[] TrasformaXml(string xml, TextReader xsl)
        {
            var trXml = new StringReader(xml);
            var xmlDocument = new XPathDocument(trXml);
            var xslDocument = new XPathDocument(xsl);

            var transform = new XslCompiledTransform();
            transform.Load(xslDocument);

            using (var ms = new MemoryStream())
            {
                transform.Transform(xmlDocument, null, ms);

                return ms.ToArray();
            }
        }
    }
}
