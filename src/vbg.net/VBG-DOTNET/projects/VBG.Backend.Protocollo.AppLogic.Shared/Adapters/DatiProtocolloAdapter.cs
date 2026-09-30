using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Adapters
{
    public class DatiProtocolloAdapter : IDatiProtocollo
    {
        protected string uo;
        protected string ruolo;
        protected string descrizioneAmministrazione;
        protected string flusso;
        protected bool isAmministrazioneInterna = false;
        protected List<ProtocolloAnagrafe> anagraficheProtocollo;
        protected List<ProtocolloAmministrazioni> amministrazioniProtocollo;
        protected List<ProtocolloAmministrazioni> amministrazioniInterne;
        protected List<ProtocolloAmministrazioni> amministrazioniEsterne;
        protected ProtocolloAmministrazioni amministrazione;
        protected DatiProtocolloIn protocolloIn;
        protected List<ProtocolloAmministrazioni> altriDestinatariInterni;

        public string Uo
        {
            get { return this.uo; }
        }

        public string Ruolo
        {
            get { return this.ruolo; }
        }

        public string DescrizioneAmministrazione
        {
            get { return this.descrizioneAmministrazione; }
        }

        public string Flusso
        {
            get { return this.flusso; }
        }

        public List<ProtocolloAnagrafe> AnagraficheProtocollo
        {
            get { return this.anagraficheProtocollo; }
        }

        public List<ProtocolloAmministrazioni> AmministrazioniProtocollo
        {
            get { return this.amministrazioniProtocollo; }
        }

        public List<ProtocolloAmministrazioni> AmministrazioniInterne
        {
            get { return this.amministrazioniInterne; }
        }

        public List<ProtocolloAmministrazioni> AmministrazioniEsterne
        {
            get { return this.amministrazioniEsterne; }
        }

        public bool IsAmministrazioneInterna
        {
            get { return this.isAmministrazioneInterna; }
        }

        public ProtocolloAmministrazioni Amministrazione
        {
            get { return this.amministrazione; }
        }

        public DatiProtocolloIn ProtoIn
        {
            get { return this.protocolloIn; }
        }

        public List<ProtocolloAmministrazioni> AltriDestinatariInterni
        {
            get { return this.altriDestinatariInterni; }
        }
    }
}
