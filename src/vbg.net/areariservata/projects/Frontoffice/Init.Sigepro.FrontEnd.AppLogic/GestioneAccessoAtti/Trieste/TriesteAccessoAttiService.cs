using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;
using Newtonsoft.Json;
using System;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Trieste
{
    public class TriesteAccessoAttiService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly IAuthenticationDataResolver _authDataResolver;
        private readonly IConfigurazione<ParametriTriesteAccessoAtti> _configurazione;
        private readonly ILog _log = LogManager.GetLogger(typeof(TriesteAccessoAttiService));

        public TriesteAccessoAttiService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAuthenticationDataResolver authDataResolver, IConfigurazione<ParametriTriesteAccessoAtti> configurazione)
        {
            _salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            _authDataResolver = authDataResolver;
            _configurazione = configurazione;
        }

        public void SalvaDatiDinamici(int idDomanda, MappaturaCampiDinamici mappaturaCampi)
        {

            try
            {
                var domanda = _salvataggioDomandaStrategy.GetById(idDomanda);
                var datiDomanda = CaricaDatiDomanda(domanda.DataKey.ToSerializationCode());
                var api = domanda.WriteInterface;

                api.DatiDinamici.EliminaValoriCampo(mappaturaCampi.IdCampoPrimoStep);
                api.DatiDinamici.EliminaValoriCampo(mappaturaCampi.IdCampoSecondoStep);
                api.DatiDinamici.EliminaValoriCampo(mappaturaCampi.IdCampoProtocollo);
                api.DatiDinamici.EliminaValoriCampo(mappaturaCampi.IdCampoOggetto);

                api.DatiDinamici.AggiornaOCrea(mappaturaCampi.IdCampoPrimoStep, 0, 0, datiDomanda.PrimoStep, datiDomanda.PrimoStep, $"CAMPO_{mappaturaCampi.IdCampoPrimoStep}");
                api.DatiDinamici.AggiornaOCrea(mappaturaCampi.IdCampoSecondoStep, 0, 0, datiDomanda.SecondoStep, datiDomanda.SecondoStep, $"CAMPO_{mappaturaCampi.IdCampoSecondoStep}");

                var idx = 0;

                foreach (var protocollo in datiDomanda.Atti.Select(x => x.Protocollo))
                {
                    api.DatiDinamici.AggiornaOCrea(mappaturaCampi.IdCampoProtocollo, 0, idx, protocollo, protocollo, $"CAMPO_{mappaturaCampi.IdCampoProtocollo}");

                    idx++;
                }

                idx = 0;

                foreach (var oggetto in datiDomanda.Atti.Select(x => x.Oggetto))
                {
                    api.DatiDinamici.AggiornaOCrea(mappaturaCampi.IdCampoOggetto, 0, idx, oggetto, oggetto, $"CAMPO_{mappaturaCampi.IdCampoOggetto}");

                    idx++;
                }

                _salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante la mappatura dei dati della pratica sui dati dinamici. Id domanda: {idDomanda}, Errore: {ex.ToString()}");

                throw;
            }
        }

        private TAAStatoDomanda CaricaDatiDomanda(string idDomanda)
        {
            var url = PreparaUrlWebService(idDomanda);

            try
            {
                using (var wc = new WebClient())
                {
                    var fileContent = wc.DownloadData(url);
                    var json = Encoding.UTF8.GetString(fileContent);

                    var data = JsonConvert.DeserializeObject<TAAWrapper>(json);

                    return data.Data;
                }
            }
            catch (Exception ex)
            {
                _log.Error($"Errore durante la lettura di dati della domanda dal web service. Id domanda: {idDomanda}, Errore: {ex.ToString()}");

                throw;
            }
        }

        private string PreparaUrlWebService(string idDomanda)
        {
            try
            {
                var token = _authDataResolver.DatiAutenticazione.Token;

                return _configurazione.Parametri.UrlWebService.Replace("$token$", token).Replace("$iddomanda$", idDomanda);
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la preparazione dell'url per l'interrogazione dei dati delllla domanda. Id domanda: {idDomanda}, errore: {ex.ToString()}");

                throw;
            }
        }
    }
}
