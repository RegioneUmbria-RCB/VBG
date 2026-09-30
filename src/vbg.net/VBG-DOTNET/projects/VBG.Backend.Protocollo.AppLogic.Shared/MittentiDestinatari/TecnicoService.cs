using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class TecnicoService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti = new List<ProtocolloAnagrafe>();

        public TecnicoService(ILog logger, ProtocolloAnagrafe richiedente, ProtocolloAnagrafe? tecnico)
        {
            if (tecnico != null)
            {
                logger.Info("Valorizzazione dei dati anagrafici leggendoli dal tecnico");
                this._soggetti.Add(tecnico);
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