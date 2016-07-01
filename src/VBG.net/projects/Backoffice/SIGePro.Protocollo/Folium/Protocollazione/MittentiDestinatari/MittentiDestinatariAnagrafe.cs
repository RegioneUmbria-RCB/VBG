using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariAnagrafe : IMittentiDestinatari
    {
        ProtocolloAnagrafe _anagrafe;
        public MittentiDestinatariAnagrafe(ProtocolloAnagrafe anagrafe)
        {
            _anagrafe = anagrafe;
        }


        #region IMittentiDestinatariBuilder Members

        public string Nome
        {
            get { return _anagrafe.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? _anagrafe.NOME : ""; }
        }

        public string Cognome
        {
            get { return _anagrafe.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAFISICA ? _anagrafe.NOMINATIVO : ""; }
        }

        public string Denominazione
        {
            get { return _anagrafe.TIPOANAGRAFE == ProtocolloConstants.COD_PERSONAGIURIDICA ? _anagrafe.NOMINATIVO : ""; }
        }

        public string CodiceMezzoSpedizione
        {
            get { return _anagrafe.Mezzo; }
        }

        public string EMail
        {
            get { return _anagrafe.EMAIL; }
        }

        public string Citta
        {
            get { return _anagrafe.CITTA; }
        }

        public string Tipo
        {
            get { return _anagrafe.TIPOANAGRAFE; }
        }

        public string Indirizzo
        {
            get { return _anagrafe.INDIRIZZO; }
        }

        public bool IsPerConoscenza(string codicePerConoscenza)
        {
            return (_anagrafe.ModalitaTrasmissione == codicePerConoscenza);
        }

        #endregion
    }
}
