using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariAmministrazione : IMittentiDestinatari
    {
        Amministrazioni _amministrazione;

        public MittentiDestinatariAmministrazione(Amministrazioni amministrazione)
        {
            _amministrazione = amministrazione;
        }

        public string Nome
        {
            get
            {
                return "";
            }
        }

        public string Cognome
        {
            get
            {
                return "";
            }
        }

        public string Denominazione
        {
            get
            {
                return String.IsNullOrEmpty(_amministrazione.PROT_UO) ? _amministrazione.AMMINISTRAZIONE : _amministrazione.PROT_UO;
            }
        }

        public string CodiceMezzoSpedizione
        {
            get
            {
                return _amministrazione.Mezzo;
            }
        }

        public string EMail
        {
            get
            {
                return _amministrazione.EMAIL;
            }
        }

        public string Citta
        {
            get
            {
                return _amministrazione.CITTA;
            }
        }

        public string Tipo
        {
            get
            {
                return ProtocolloConstants.COD_PERSONAGIURIDICA;
            }
        }

        public string Indirizzo
        {
            get { return _amministrazione.INDIRIZZO; }
        }

        public bool IsPerConoscenza(string codicePerConoscenza)
        {
            return (_amministrazione.ModalitaTrasmissione == codicePerConoscenza);
        }
    }
}
