using Gotenberg.Sharp.API.Client;
using Gotenberg.Sharp.API.Client.Application.Builders;
using Gotenberg.Sharp.API.Client.Domain.Pages;
using Gotenberg.Sharp.API.Client.Domain.PdfFormat;



//using Gotenberg.Sharp.API.Client.Domain.Builders;
//using Gotenberg.Sharp.API.Client.Domain.Builders.Faceted;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.Extensions.Logging;
using System;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF
{
    public class GotenbergFileConverter : IHtmlToPdfAsyncFileConverter
    {
        private readonly GotenbergSharpClient _sharpClient;
        private readonly ILogger<GotenbergFileConverter> _logger;

        public GotenbergFileConverter(GotenbergSharpClient sharpClient, ILogger<GotenbergFileConverter> logger)
        {
            this._sharpClient = sharpClient;
            this._logger = logger;
        }


        public async Task<BinaryFile> ConvertiAsync(string nomeFile, string html, RenderingFlags? renderingFlags = null)
        {
            try
            {
                var builder = new HtmlRequestBuilder()
                          .AddDocument(doc =>
                              doc.SetBody(html)//.SetFooter(GetFooter())
                          )
                          .WithPageProperties(pp =>
                          {
                              pp.SetPaperSize(PaperSizes.A4)
                                  .SetMargins(Margins.Normal)
                                  .SetScale(.99);
                          })
                          .SetPdfOutputOptions(cb =>
                          {
                              cb.SetPdfUa(true)
                                .SetPdfFormat(PdfFormat.A3b);
                          });

                var req = await builder.BuildAsync();

                var result = await this._sharpClient.HtmlToPdfAsync(req);

                using var memorystream = new MemoryStream();
                await result.CopyToAsync(memorystream);

                return BinaryFile.FromFileData(nomeFile, "application/pdf", memorystream.ToArray());
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore nell'invocazione del servizio di gotenberg: {@ex}", ex);

                throw;
            }
        }


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
