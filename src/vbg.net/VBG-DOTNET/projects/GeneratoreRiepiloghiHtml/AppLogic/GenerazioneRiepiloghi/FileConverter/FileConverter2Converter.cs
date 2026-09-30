using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using VbgFileConverter;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter
{
    public class FileConverter2Converter : HtmlToPdfFileConverterBase
    {
        private readonly FileConverterServiceCreator _serviceCreator;
        private readonly ILogger<FileConverter2Converter> _logger;

        public FileConverter2Converter(FileConverterServiceCreator serviceCreator, ILogger<FileConverter2Converter> logger)
        {
            this._serviceCreator = serviceCreator;
            this._logger = logger;
        }

        public override async Task<BinaryFile> ConvertiAsync(string nomeFile, string html)
        {
            try
            {
                return await this._serviceCreator.CallAsync(async (ws) =>
                {
                    var convertRequest = new ConvertRequest
                    {
                        content = html,
                        contentType = "HTML",
                        conversionType = "PDF",
                        token = ws.Token
                    };

                    this._logger.LogDebug("Invocazione del fileConverter con i parametri {convertRequest}", convertRequest);

                    var response = await ws.Service.ConvertAsync(convertRequest);

                    this._logger.LogDebug("Invocazione del fileConverter completata con successo");

                    if (response.ConvertResponse is null)
                    {
                        throw new Exception("Il file converter ha restituito un risultato nullo");
                    }

                    return BinaryFile.FromFileData(nomeFile, response.ConvertResponse.mimeType, response.ConvertResponse.binaryData);

                });
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante l'invocazione del fileconverter: {@ex}", ex);

                throw;
            }
        }


    }
}