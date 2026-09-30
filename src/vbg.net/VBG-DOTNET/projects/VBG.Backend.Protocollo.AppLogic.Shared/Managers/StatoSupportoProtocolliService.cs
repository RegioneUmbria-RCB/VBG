using VBG.Shared.Infrastructure.ServiceModel;
using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public class StatoSupportoProtocolliService
    {
        public enum StatoSupportoProtocollazioneStoricaEnum
        {
            NonAttiva = 0,
            Supportato = 1,
            NonSupportato = 2,
        }

        public class StatoSupportoProtocolli
        {
            public bool ProtocolloSupportato { get; set; } = false;
            public StatoSupportoProtocollazioneStoricaEnum ProtocolloStoricoSupportato { get; set; } = StatoSupportoProtocollazioneStoricaEnum.NonAttiva;
        }


        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public StatoSupportoProtocolliService(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public StatoSupportoProtocolli GetStatoSupportoProtocolli(string idComuneAlias, string software, string codiceComune)
        {
            var verticalizzazioneProtocolloAttivo = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(idComuneAlias, software, codiceComune);
            var verticalizzazioneProtocolloStorico = this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloStorico>(idComuneAlias, software, codiceComune);

            var log = LogManager.GetLogger(typeof(ProtocolloMgr));

            var attivazioneService = new AttivazioneProtocolloService(log, this._verticalizzazioniFactory, this._bindingFactory);

            var protocolloAttivoSupportato = attivazioneService.IsProtocolloSupportato(verticalizzazioneProtocolloAttivo);
            var protocolloStoricoSupportato = StatoSupportoProtocollazioneStoricaEnum.NonAttiva;

            if (verticalizzazioneProtocolloStorico.Attiva)
            {
                protocolloStoricoSupportato = attivazioneService.IsProtocolloSupportato(verticalizzazioneProtocolloStorico)
                    ? StatoSupportoProtocollazioneStoricaEnum.Supportato
                    : StatoSupportoProtocollazioneStoricaEnum.NonSupportato;
            }

            return new StatoSupportoProtocolli
            {
                ProtocolloSupportato = protocolloAttivoSupportato,
                ProtocolloStoricoSupportato = protocolloStoricoSupportato
            };
        }
    }
}
