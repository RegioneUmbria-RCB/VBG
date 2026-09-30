using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.Extensions.Logging;
using System;
using System.IO;
using System.Text;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public abstract class HtmlToPdfFileConverterBase : IHtmlToPdfFileConverter
    {
        protected HtmlToPdfFileConverterBase()
        {
        }

        public abstract BinaryFile Converti(string nomeFile, string html, RenderingFlags? renderingFlags = null);

        public BinaryFile TrasformaEConverti(string nomeFile, string xml, string xsl)
        {
            var html = this.TrasformaXSl(xml, xsl);

            return this.Converti(nomeFile, html);
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


    public class FileConverter2Converter : HtmlToPdfFileConverterBase
    {
        private readonly FileConverterServiceCreator _serviceCreator;
        private readonly ILogger<FileConverter2Converter> _logger;

        public FileConverter2Converter(FileConverterServiceCreator serviceCreator, ILogger<FileConverter2Converter> logger)
        {
            this._serviceCreator = serviceCreator;
            this._logger = logger;
        }


        public override BinaryFile Converti(string nomeFile, string html, RenderingFlags? renderingFlags = null)
        {
            try
            {
                return this._serviceCreator.Call(ws =>
                {
                    var convertRequest = new WsFileConverterService.ConvertRequest
                    {
                        content = html,
                        contentType = "HTML",
                        conversionType = "PDF",
                        token = ws.Token
                    };

                    this._logger.LogDebug("Invocazione del fileConverter con i parametri {convertRequest}", convertRequest);

                    var response = ws.Service.Convert(convertRequest);

                    this._logger.LogDebug("Invocazione del fileConverter completata con successo");

                    return new BinaryFile(nomeFile, response.mimeType, response.binaryData);

                });
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante l'invocazione del fileconverter: {ex}", ex);

                throw;
            }
        }


    }
}
