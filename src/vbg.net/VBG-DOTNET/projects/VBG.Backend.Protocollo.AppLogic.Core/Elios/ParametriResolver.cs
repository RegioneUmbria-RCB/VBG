using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class ParametriResolver
    {
        private readonly VerticalizzazioneProtocolloAttivo _verticalizzazioneBase;
        private readonly VerticalizzazioneProtocolloElios _verticalizzazioneElios;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloLogs _logs;
        private readonly IBindingFactory _bindingFactory;

        public ParametriResolver(string idComuneAlias, string software, string codiceComune, ProtocolloSerializer protocolloSerializer, IVerticalizzazioniFactory verticalizzazioniFactory, ProtocolloLogs logs, IBindingFactory bindingFactory)
        {
            this._verticalizzazioneBase = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
            this._verticalizzazioneElios = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloElios>(idComuneAlias, software, codiceComune);

            this._serializer = protocolloSerializer;
            this._logs = logs;
            this._bindingFactory = bindingFactory;

        }

        public Parametri Resolve()
        {
            return new Parametri
            {
                AreaOmogenea = this._verticalizzazioneElios.AreaOmogenea,
                Ente = this._verticalizzazioneElios.Ente,
                Token = this.GetToken(),
                UrlConfigurazione = this._verticalizzazioneElios.UrlConfigurazione,
                UrlProtocollazione = this._verticalizzazioneElios.UrlProtocollazione,
                UrlFascicolazione = this._verticalizzazioneElios.UrlFascicolazione,
            };
        }

        private string GetToken()
        {
            return new EliosAutenticazioneService
                (
                    this._verticalizzazioneElios.UrlAutenticazione,
                    this._verticalizzazioneElios.Utente,
                    this._verticalizzazioneElios.Password,
                    this._verticalizzazioneElios.Ente,
                    this._serializer,
                    this._bindingFactory,
                    this._logs
                )
                .Login();
        }
    }
}
