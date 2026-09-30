using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class AnagraficaVbg : IAmministrazioneAnagraficaVbg
    {
        ProtocolloAnagrafe _anagrafe;

        public AnagraficaVbg(ProtocolloAnagrafe anagrafe)
        {
            _anagrafe = anagrafe;
        }

        public string CodiceFiscalePartitaIva
        {
            get { return !String.IsNullOrEmpty(_anagrafe.CODICEFISCALE) ? _anagrafe.CODICEFISCALE : _anagrafe.PARTITAIVA; }
        }

        public string Nominativo
        {
            get { return _anagrafe.GetNomeCompleto(); }
        }

        public string Tipo
        {
            get { return "ANAGRAFICA"; }
        }

        public string CodiceVbg
        {
            get { return _anagrafe.CODICEANAGRAFE; }
        }
    }
}
