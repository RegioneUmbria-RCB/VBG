using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using log4net;
using ProtocollazioneLegacy;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.Configuration;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.WebApp.Core.ProtocolliRemoti
{
    public class ProtocolloStoricoRemoto : AppLogic.Shared.Managers.IProtocolloStorico
    {
        private readonly ConfigurazioneProtocolloLegacy _config;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly ClientProtocollazioneLegacyServiceCreator _clientProtocollazioneLegacyServiceCreator;
        private VerticalizzazioneProtocolloStorico? _verticalizzazioneProtocolloStorico;
        private ResolveDatiProtocollazioneService _datiProtocollazione;

        public ProtocolloStoricoRemoto(ILog log, IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory, ConfigurazioneProtocolloLegacy config)
        {
            this._config = config;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._clientProtocollazioneLegacyServiceCreator = new ClientProtocollazioneLegacyServiceCreator(log, bindingFactory, config.Url);
        }

        public bool ProtocollazioneStoricaAttiva => this._verticalizzazioneProtocolloStorico?.Attiva ?? throw new ProtocolloStoricoException("Il protocollo storico remoto non è inizializzato");

        public DateTime DataUltimaProtocollazione => this.GetDataUltimaProtocollazione();

        public void Initialize(AuthenticationInfo authInfo, string software, string codiceComune = "", AmbitoProtocollazioneEnum ambito = AmbitoProtocollazioneEnum.NESSUNO, Istanze istanza = null, Movimenti movimento = null, PecInbox datiPec = null)
        {
            if (String.IsNullOrEmpty(software))
                throw new ArgumentNullException(nameof(software));

            if (authInfo == null)
                throw new ArgumentNullException(nameof(authInfo));

            if (String.IsNullOrEmpty(authInfo.IdComune))
                throw new ArgumentNullException("authInfo.IdComune");

            var dataBase = authInfo.CreateDatabase();

            var idComune = authInfo.IdComune;
            var idComuneAlias = authInfo.Alias;
            var codOperatore = authInfo.CodiceResponsabile;
            var token = authInfo.Token;

            this._datiProtocollazione = new ResolveDatiProtocollazioneService(idComune, idComuneAlias, software, dataBase, istanza, movimento, codOperatore, ambito, token, codiceComune, datiPec);
            this._verticalizzazioneProtocolloStorico = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStorico>(idComuneAlias, software, codiceComune);
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloLettoResponseType LeggiProtocolloStorico(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var request = new LeggiProtocolloRequest
                {
                    Token = this._datiProtocollazione.Token,
                    Software = this._datiProtocollazione.Software,
                    CodiceComune = this._datiProtocollazione.CodiceComune,
                    IdProtocollo = idProtocollo,
                    AnnoProtocollo = annoProtocollo,
                    NumeroProtocollo = numeroProtocollo
                };

                var response = ws.Service.LeggiProtocollo(request);
                return response.First();
            }
        }

        public AppLogic.Shared.WsDataClass.AllegatoResponseType StoricoLeggiAllegato(string idProtocollo, string numProtocollo, string annoProtocollo, string idAllegato)
        {
            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var idBase = $"{idProtocollo}|{numProtocollo}|{annoProtocollo}|{idAllegato}";
                var response = ws.Service.LeggiAllegatoStorico(this._datiProtocollazione.Token, idBase, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
                return response;
            }
        }

        public AppLogic.Shared.WsDataClass.DatiProtocolloLettoResponseType StoricoLeggiProtocolloConData(string idProtocollo, int annoProtocollo, string numeroProtocollo)
        {

            using (var ws = this._clientProtocollazioneLegacyServiceCreator.CreateClient())
            {
                var dataProtocollo = new DateTime(Convert.ToInt32(annoProtocollo), 1, 1);
                var response = ws.Service.LeggiProtocolloConData(this._datiProtocollazione.Token, idProtocollo, dataProtocollo, numeroProtocollo, this._datiProtocollazione.Software, this._datiProtocollazione.CodiceComune);
                return response.First();
            }
        }

        private DateTime GetDataUltimaProtocollazione()
        {
            if (this._verticalizzazioneProtocolloStorico == null)
            {
                throw new ProtocolloStoricoException("Il protocollo storico non è inizializzato");
            }

            if (!this._verticalizzazioneProtocolloStorico.DataUltimaProtocollazione.HasValue)
            {
                throw new ProtocolloStoricoException("E' ATTIVA LA VERTICALIZZAZIONE DEL PROTOCOLLO STORICO MA NON E' IMPOSTATO IL PARAMETRO DATAULTIMAPROTOCOLLAZIONE. CONTROLLARE LA CONFIGURAZIONE!!");
            }
            return this._verticalizzazioneProtocolloStorico.DataUltimaProtocollazione.Value;
        }

    }
}
