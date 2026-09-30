using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class AziendaService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti;

        public AziendaService(ILog logger, ProtocolloAnagrafe richiedente, ProtocolloAnagrafe? azienda)
        {
            if (azienda != null)
            {
                logger.Info("Valorizzazione dei dati anagrafici leggendoli dall'azienda");
                this._soggetti = new List<ProtocolloAnagrafe>();
                this._soggetti.Add(azienda);
            }
            else
            {
                this._soggetti = new RichiedenteService(logger, richiedente).GetSoggetti();
            }
        }

        public List<ProtocolloAnagrafe> GetSoggetti()
        {
            return this._soggetti;
        }
    }
}