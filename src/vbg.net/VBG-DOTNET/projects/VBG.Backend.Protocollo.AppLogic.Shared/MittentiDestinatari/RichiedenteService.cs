using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class RichiedenteService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti = new List<ProtocolloAnagrafe>();

        public RichiedenteService(ILog logger, ProtocolloAnagrafe richiedente)
        {
            logger.Info("Valorizzazione dei dati anagrafici leggendoli dal richiedente");
            this._soggetti.Add(richiedente);
        }

        public List<ProtocolloAnagrafe> GetSoggetti()
        {
            return this._soggetti;
        }
    }
}