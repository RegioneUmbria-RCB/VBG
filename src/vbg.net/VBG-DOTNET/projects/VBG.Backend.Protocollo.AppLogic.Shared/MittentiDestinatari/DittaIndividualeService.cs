using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class DittaIndividualeService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti;

        public DittaIndividualeService(ILog logger, ProtocolloAnagrafe richiedente, IQualificaDittaIndividualeService qualificaService, ProtocolloAnagrafe? azienda)
        {
            this._soggetti = qualificaService.DittaIndividuale()
                                ? new RichiedenteService(logger, richiedente).GetSoggetti()
                                : new AziendaService(logger, richiedente, azienda).GetSoggetti();

        }
        public List<ProtocolloAnagrafe> GetSoggetti()
        {
            return this._soggetti;
        }
    }
}