using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Sicraweb.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariVersioneFactory
    {
        public readonly IEnumerable<IMittentiDestinatariVersioneProtocollo> Mittenti;
        public readonly IEnumerable<IMittentiDestinatariVersioneProtocollo> Destinatari;

        public MittentiDestinatariVersioneFactory(Segnatura.Segnatura response)
        {
            if (response.Intestazione.MittentiMulti != null)
                Mittenti = response.Intestazione.MittentiMulti.Select(x => new MittenteVersioneMulti(x));
            else
            {
                var mittenteList = new List<MittenteVersioneMono>();
                mittenteList.Add(new MittenteVersioneMono(response.Intestazione.Mittente));
                Mittenti = mittenteList;
            }

            if (response.Intestazione.DestinatariMulti != null)
                Mittenti = response.Intestazione.DestinatariMulti.Select(x => new DestinatarioVersioneMulti(x));
            else
            {
                var destinatarioList = new List<DestinatarioVersioneMono>();
                destinatarioList.Add(new DestinatarioVersioneMono(response.Intestazione.Destinatario));
                Destinatari = destinatarioList;
            }
        }
    }
}
