using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class PhantomjsFileConverter : IHtmlToPdfFileConverter
    {
        string _phantomjsPath;

        public PhantomjsFileConverter(IConfigurazione<ParametriPhantomjs> config)
        {
            this._phantomjsPath = config.Parametri.PhantomjsPath;
        }

        public BinaryFile Converti(string nomeFile, string html)
        {
            var sw = Stopwatch.StartNew();

            try
            {
                var renderer = new PhantomjsRenderer(this._phantomjsPath);
                var fileContent = renderer.RenderHtml(html);

                return new BinaryFile(nomeFile, "application/pdf", fileContent);
            }
            finally
            {
                sw.Stop();

                Debug.WriteLine("Generazione del file \"{0}\" effettuata in {1} ms", nomeFile, sw.ElapsedMilliseconds);
            }
        }

        public BinaryFile TrasformaEConverti(string nomeFile, string xml, string xsl)
        {
            var html = TrasformaXSl(xml, xsl);

            return Converti(nomeFile, html);
        }

        private string TrasformaXSl(string xml, string xsl)
        {
            var sr = new StringReader(xsl);
            var trXml = new StringReader(xml);
            var xmlDocument = new XPathDocument(trXml);
            var xslDocument = new XPathDocument(sr);

            var transform = new XslCompiledTransform();
            transform.Load(xslDocument);

            using (var ms = new MemoryStream())
            {
                transform.Transform(xmlDocument, null, ms);

                return Encoding.UTF8.GetString( ms.ToArray());
            }
        }
    }
}
