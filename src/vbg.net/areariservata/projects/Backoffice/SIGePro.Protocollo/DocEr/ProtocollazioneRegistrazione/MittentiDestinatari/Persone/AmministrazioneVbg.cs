using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari.Persone
{
    public class AmministrazioneVbg  : IAmministrazioneAnagraficaVbg
    {
        Amministrazioni _amministrazione;

        public AmministrazioneVbg(Amministrazioni amministrazione)
        {
            _amministrazione = amministrazione;
        }

        public string CodiceFiscalePartitaIva
        {
            get { return _amministrazione.PARTITAIVA; }
        }

        public string Nominativo
        {
            get { return _amministrazione.AMMINISTRAZIONE; }
        }

        public string Tipo
        {
            get { return "AMMINISTRAZIONE"; }
        }

        public string CodiceVbg
        {
            get { return _amministrazione.CODICEAMMINISTRAZIONE; }
        }
    }
}
