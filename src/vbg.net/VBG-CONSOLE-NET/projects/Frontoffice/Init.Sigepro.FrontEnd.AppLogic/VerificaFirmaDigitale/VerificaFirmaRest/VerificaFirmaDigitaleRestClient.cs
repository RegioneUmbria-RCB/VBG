using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using log4net;
using RestSharp;
using System;
using System.Linq;
using System.Text.Json;
using System.Text.Json.Serialization;


namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest
{
    public class VerificaFirmaDigitaleRestClient
    {
        private readonly ILog _logger = LogManager.GetLogger(typeof(VerificaFirmaDigitaleRestClient));
        private readonly IConfigurazione<ParametriSigeproSecurity> _configurazione;
        public VerificaFirmaDigitaleRestClient(IConfigurazione<ParametriSigeproSecurity> configurazione)
        {
            this._configurazione = configurazione;

            if (String.IsNullOrEmpty(this._configurazione.Parametri.UrlVerificaFirmaRestService))
            {
                throw new InvalidOperationException("L'URL del servizio di verifica firma REST non è configurato.");
            }
        }

        public class EsitoFirmaDTO
        {
            [JsonPropertyName("esitofirma")]
            public bool EsitoFirma { get; set; }
        }


        public BinaryFile ScaricaFileNonFirmato(BinaryFile file)
        {
            var baseUrl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"api/checkfirma/scaricaFileNonFirmato";

            using (var client = new RestSharp.RestClient(baseUrl))
            {
                var request = new RestSharp.RestRequest(url, RestSharp.Method.Post);
                request.AddFile("documento", file.FileContent, file.FileName, "application/octet-stream");
                this._logger.Debug($"ScaricaFileNonFirmatoAsync: invio della richiesta {request}");

                var response = client.Execute(request);

                if (!response.IsSuccessful)
                {
                    this._logger.Error($"Errore durante l'estrazione del file: corpo della risposta ({response.StatusCode}): {response.Content}");
                    throw new InvalidOperationException($"Errore durante l'estrazione del file: {response.StatusCode}");
                }
                var content = response.RawBytes;
                var fileName = response.Headers.FirstOrDefault(h => h.Name.Equals("Content-Disposition", StringComparison.OrdinalIgnoreCase))?.Value?.ToString()?.Split('=')?.Last()?.Trim('"');
                var mimeType = response.ContentType;
                this._logger.Debug("ScaricaFileNonFirmatoAsync completato con successo.");
                return new BinaryFile(fileName, mimeType, content);
            }

        }

        public StatoVerificaFirma VerificaFirmaDigitale(BinaryFile file, bool verificaAllaData = true)
        {
            var baseUrl = this._configurazione.Parametri.UrlVerificaFirmaRestService;
            var url = $"api/checkfirma/verificaFirma";

            using (var client = new RestSharp.RestClient(baseUrl))
            {
                var request = new RestSharp.RestRequest(url, RestSharp.Method.Post);
                request.AddFile("documento", file.FileContent, file.FileName, "application/octet-stream");

                var parameter = new GetOrPostParameter("verificaAllaData", JsonSerializer.Serialize(verificaAllaData))
                {
                    ContentType = "application/json"
                };

                request.AddParameter(parameter);

                this._logger.Debug($"VerificaFirmaDigitaleAsync: invio della richiesta {request}");

                var response = client.Execute(request);

                if (!response.IsSuccessful)
                {
                    this._logger.Error($"Errore durante la verifica della firma: corpo della risposta ({response.StatusCode}): {response.Content}");
                    throw new InvalidOperationException($"Errore durante la verifica della firma: {response.StatusCode} - {response.ErrorMessage}");
                }

                var result = System.Text.Json.JsonSerializer.Deserialize<EsitoFirmaDTO>(response.Content);

                if (result is null)
                    throw new InvalidOperationException("Risposta non valida.");

                this._logger.Debug("VerificaFirmaDigitaleAsync: firma digitale verificata con successo");
                return result.EsitoFirma ? StatoVerificaFirma.FirmaValida : StatoVerificaFirma.FirmaNonValida;
            }
        }

    }
}