using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo
{
    public class DatiConfigurazioneProtocolloInfo
    {
        public string Classifica { get; set; }
        public string Uo { get; set; }
        public string Ruolo { get; set; }
        public string TipoDocumento { get; set; }
        public VerticalizzazioniServiceWrapper Regole;

        public DatiConfigurazioneProtocolloInfo()
        {

        }
    }
}
