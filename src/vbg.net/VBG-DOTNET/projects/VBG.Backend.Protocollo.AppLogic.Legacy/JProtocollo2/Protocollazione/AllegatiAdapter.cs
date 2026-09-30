using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
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
