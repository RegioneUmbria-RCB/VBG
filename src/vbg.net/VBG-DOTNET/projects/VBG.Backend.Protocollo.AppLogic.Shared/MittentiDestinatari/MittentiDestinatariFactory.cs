using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    public class MittentiDestinatariFactory
    {
        private readonly ILog _logger;
        private readonly Dictionary<TipoMittenteEnum, IMittenteDestinatarioService> _services;
        public MittentiDestinatariFactory(ILog logger, IQualificaDittaIndividualeService qualificaService, ProtocolloAnagrafe richiedente, ProtocolloAnagrafe? azienda, ProtocolloAnagrafe? tecnico)
        {
            this._logger = logger;
            this._services = new Dictionary<TipoMittenteEnum, IMittenteDestinatarioService>
            {
                { TipoMittenteEnum.AZIENDA, new AziendaService(this._logger, richiedente, azienda) },
                { TipoMittenteEnum.AZIENDA_RICHIEDENTE, new AziendaRichiedenteService(this._logger, richiedente, azienda) },
                { TipoMittenteEnum.AZIENDA_TECNICO, new AziendaTecnicoService(this._logger, richiedente, azienda, tecnico) },
                { TipoMittenteEnum.DITTA_INDIVIDUALE, new DittaIndividualeService(this._logger, richiedente, qualificaService, azienda) },
                { TipoMittenteEnum.RICHIEDENTE, new RichiedenteService(this._logger, richiedente) },
                { TipoMittenteEnum.TECNICO, new TecnicoService(this._logger, richiedente, tecnico) }
            };
        }
        public List<ProtocolloAnagrafe> GetSoggetti(TipoMittenteEnum tipoMittente)
        {
            this._logger.InfoFormat("TIPO MITTENTE: {0}", tipoMittente);

            return this._services[tipoMittente].GetSoggetti();
        }
    }
}
