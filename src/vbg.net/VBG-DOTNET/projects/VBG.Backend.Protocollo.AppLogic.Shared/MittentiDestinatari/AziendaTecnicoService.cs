using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.MittentiDestinatari
{
    internal class AziendaTecnicoService : IMittenteDestinatarioService
    {
        private readonly List<ProtocolloAnagrafe> _soggetti;

        public AziendaTecnicoService(ILog logger, ProtocolloAnagrafe richiedente, ProtocolloAnagrafe? azienda, ProtocolloAnagrafe? tecnico)
        {
            this._soggetti = new AziendaService(logger, richiedente, azienda).GetSoggetti();

            if (tecnico != null)
            {
                logger.Info("Valorizzazione dei dati anagrafici leggendoli dal tecnico");
                this._soggetti.Add(tecnico);
            }
        }

        public List<ProtocolloAnagrafe> GetSoggetti()
        {
            return this._soggetti;
        }
    }
}