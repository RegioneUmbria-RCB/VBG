using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using System.Text;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter
{
    public abstract class HtmlToPdfFileConverterBase : IHtmlToPdfFileConverter
    {
        protected HtmlToPdfFileConverterBase()
        {
        }

        public abstract Task<BinaryFile> ConvertiAsync(string nomeFile, string html);

        public async Task<BinaryFile> TrasformaEConvertiAsync(string nomeFile, string xml, string xsl)
        {
            var html = this.TrasformaXSl(xml, xsl);

            return await this.ConvertiAsync(nomeFile, html);
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

                return Encoding.UTF8.GetString(ms.ToArray());
            }
        }
    }
}