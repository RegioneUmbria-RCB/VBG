using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
{
    public class AllegatiAdapter
    {
        List<ProtocolloAllegati> _allegati;

        public AllegatiAdapter(List<ProtocolloAllegati> allegati)
        {
            _allegati = allegati;
        }

        public documento Adatta()
        {
            documento retVal = null;

            if(_allegati.Count > 0)
            {
                var allegatoPrincipale = _allegati.First();

                retVal = new documento
                {
                    file = allegatoPrincipale.OGGETTO,
                    nomeFile = allegatoPrincipale.NOMEFILE,
                    titolo = allegatoPrincipale.NOMEFILE
                };
            }

            return retVal;
        }
    }
}
