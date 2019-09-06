using Init.SIGePro.Protocollo.Iride.Proxies;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Iride.Fascicolazione
{
    public class FascicolazioneJIride : IFascicolazione
    {
        FascicolazioneProxyJIride _proxy;
        ProtocolloLogs _logs;

        public FascicolazioneProxy Proxy { get { return _proxy; } }

        public FascicolazioneJIride(string url, string proxyAddress, ProtocolloLogs logs)
        {
            _proxy = new FascicolazioneProxyJIride(url, proxyAddress);
            _logs = logs;
        }

        public FascicoloOut LeggiFascicolo(string numero, string anno, string classifica, int id, string utente, string ruolo, string codiceAmministrazione, string codiceAoo)
        {
            FascicoloOut fascicoloOut;

            _logs.InfoFormat("ID FASCICOLO: {0}", id.ToString());
            if (id != 0)
            {
                _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ID: {0}", id.ToString());
                fascicoloOut = _proxy.LeggiFascicolo(id.ToString(), "", "", utente, ruolo, codiceAmministrazione, codiceAoo, "");
            }
            else
            {
                _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ANNO FASCICOLO: {0}, NUMERO FASCICOLO: {1}, UTENTE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}, CODICE AOO: {5}, CLASSIFICA: {6}", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo, classifica);
                fascicoloOut = _proxy.LeggiFascicolo("", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo, classifica);
            }

            _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE AVVENUTA CORRETTAMENTE, ID FASCICOLO: {0}", fascicoloOut.Id);

            return fascicoloOut;
        }
    }
}
