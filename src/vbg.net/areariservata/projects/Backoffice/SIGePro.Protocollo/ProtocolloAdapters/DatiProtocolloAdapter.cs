using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;

namespace Init.SIGePro.Protocollo.ProtocolloAdapters
{
    public class DatiProtocolloAdapter : IDatiProtocollo
    {
        protected string uo;
        protected string ruolo;
        protected string descrizioneAmministrazione;
        protected string flusso;
        protected bool isAmministrazioneInterna = false;
        protected List<ProtocolloAnagrafe> anagraficheProtocollo;
        protected List<Amministrazioni> amministrazioniProtocollo;
        protected List<Amministrazioni> amministrazioniInterne;
        protected List<Amministrazioni> amministrazioniEsterne;
        protected Amministrazioni amministrazione;
        protected DatiProtocolloIn protocolloIn;
        protected List<Amministrazioni> altriDestinatariInterni;

        public string Uo
        {
            get { return uo; }
        }

        public string Ruolo
        {
            get { return ruolo; }
        }

        public string DescrizioneAmministrazione
        {
            get { return descrizioneAmministrazione; }
        }

        public string Flusso
        {
            get { return flusso; }
        }

        public List<ProtocolloAnagrafe> AnagraficheProtocollo
        {
            get { return anagraficheProtocollo; }
        }

        public List<Amministrazioni> AmministrazioniProtocollo
        {
            get { return amministrazioniProtocollo; }
        }

        public List<Amministrazioni> AmministrazioniInterne
        {
            get { return amministrazioniInterne; }
        }

        public List<Amministrazioni> AmministrazioniEsterne
        {
            get { return amministrazioniEsterne; }
        }

        public bool IsAmministrazioneInterna
        {
            get { return isAmministrazioneInterna; }
        }

        public Amministrazioni Amministrazione
        {
            get { return amministrazione; }
        }

        public DatiProtocolloIn ProtoIn
        {
            get { return protocolloIn; }
        }

        public List<Amministrazioni> AltriDestinatariInterni
        {
            get { return altriDestinatariInterni; }
        }
    }
}
