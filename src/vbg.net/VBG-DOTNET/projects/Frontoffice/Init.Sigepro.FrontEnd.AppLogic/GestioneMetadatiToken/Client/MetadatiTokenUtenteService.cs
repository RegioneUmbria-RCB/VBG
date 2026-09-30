using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.Threading;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text.Json;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMetadatiToken.Client
{
    public class MetadatiTokenUtenteService : IMetadatiTokenUtenteService
    {
        private readonly ITokenResolver _tokenResolver;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly IConfigurazione<ParametriSigeproSecurity> _parametriSecurity;
        private readonly ILogger _logger;

        public MetadatiTokenUtenteService(ITokenResolver tokenResolver, IHttpClientFactory httpClientFactory, IConfigurazione<ParametriSigeproSecurity> parametriSecurity, ILoggerFactory loggerFactory)
        {
            this._tokenResolver = tokenResolver;
            this._httpClientFactory = httpClientFactory;
            this._parametriSecurity = parametriSecurity;
            this._logger = loggerFactory.CreateLogger(nameof(MetadatiTokenUtenteService));
        }

        public IEnumerable<MetadatoToken> GetMetadatiTokenUtente()
        {
            var jtf = new JoinableTaskFactory(new JoinableTaskContext());

            return jtf.Run(this.GetMetadatiTokenUtenteAsync);
        }

        public async Task<IEnumerable<MetadatoToken>> GetMetadatiTokenUtenteAsync()
        {
            using var scope = this._logger.BeginScope("MetadatiTokenClient.GetMetadatiTokenUtenteAsync");

            if (!this._parametriSecurity.Parametri.ServiziMetadataTokenAttivi)
            {
                return Enumerable.Empty<MetadatoToken>();
            }

            using var httpClient = this._httpClientFactory.CreateClient("SigeproMetadatiToken");

            var userToken = this._tokenResolver.Token;
            var url = $"{this._parametriSecurity.Parametri.UrlBaseServiziToken}metadati/{userToken}";

            this._logger.LogDebug("Invocazione del servizio di lettura metadati token {UserToken} all'URL {Url}", userToken, url);

            httpClient.DefaultRequestHeaders.Add("Authorization", $"Bearer {userToken}");

            var response = await httpClient.GetAsync(url);

            if (!response.IsSuccessStatusCode)
            {
                // TODO: gestire l'errore in modo appropriato
                this._logger.LogError("Errore durante la chiamata al servizio di lettura metadati token, verrà restituito un set di metadati vuoto. Codice errore={StatusCode}", response.StatusCode);
                return Enumerable.Empty<MetadatoToken>();
            }

            var responseBody = await response.Content.ReadAsStringAsync();
            var deserialized = JsonSerializer.Deserialize<LetturaMetadatiResponse>(responseBody);

            return deserialized?.Metadati ?? Enumerable.Empty<MetadatoToken>();
        }
    }
}
