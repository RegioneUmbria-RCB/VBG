using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using Gotenberg.Sharp.API.Client;
using Gotenberg.Sharp.API.Client.Application.Builders;
using Gotenberg.Sharp.API.Client.Domain.Pages;
using Gotenberg.Sharp.API.Client.Domain.PdfFormat;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter
{
    public class GotenbergFileConverter : IHtmlToPdfFileConverter
    {
        private readonly GotenbergSharpClient _sharpClient;
        private readonly ILogger<GotenbergFileConverter> _logger;

        public GotenbergFileConverter(GotenbergSharpClient sharpClient, ILogger<GotenbergFileConverter> logger)
        {
            this._sharpClient = sharpClient;
            this._logger = logger;
        }

        public async Task<BinaryFile> ConvertiAsync(string nomeFile, string html)
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
                          .SetPdfOutputOptions(options =>
                          {
                              options.SetPdfUa(true)
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
    }
}
