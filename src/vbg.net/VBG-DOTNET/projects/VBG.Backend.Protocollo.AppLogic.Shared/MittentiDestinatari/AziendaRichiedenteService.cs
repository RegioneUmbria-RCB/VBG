using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class AziendaRichiedenteService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti;

        public AziendaRichiedenteService(ILog logger, ProtocolloAnagrafe richiedente, ProtocolloAnagrafe? azienda)
        {
            this._soggetti = new RichiedenteService(logger, richiedente).GetSoggetti();

            if (azienda != null)
            {
                logger.Info("Valorizzazione dei dati anagrafici leggendoli dall'azienda");
                this._soggetti.Add(azienda);
            }
        }

        public List<ProtocolloAnagrafe> GetSoggetti()
        {
            return this._soggetti;
        }
    }
}