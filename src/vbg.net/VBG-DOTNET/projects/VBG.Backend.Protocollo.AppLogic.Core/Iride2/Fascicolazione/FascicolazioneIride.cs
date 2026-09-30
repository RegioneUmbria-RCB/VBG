

using FascicolazioneIride2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Fascicolazione
{
    public class FascicolazioneIride : IFascicolazione
    {
        ProtocolloLogs _logs;
        FascicolazioneClientServiceCreator _fascicolazioneClientServiceCreator;

        public FascicolazioneIride(ProtocolloLogs logs, FascicolazioneClientServiceCreator serviceCreator)
        {
            _logs = logs;
            _fascicolazioneClientServiceCreator = serviceCreator;
        }

        public FascicoloOut LeggiFascicolo(string numero, string anno, string classifica, int idFascicolo, string utente, string ruolo, string codiceAmministrazione, string codiceAoo)
        {
            FascicoloOut fascicoloOut;

            _logs.InfoFormat("ID FASCICOLO: {0}", idFascicolo.ToString());

            using (var ws = _fascicolazioneClientServiceCreator.CreateClient())
            {
                if (idFascicolo != 0)
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO IRIDE, ID: {0}", idFascicolo.ToString());
                    //fascicoloOut = ws.Service.LeggiFascicolo(idFascicolo.ToString(), "", "", utente, ruolo, codiceAmministrazione, codiceAoo);
                    // ATTENZIONE! per scommentare la riga sopra bisogna trovare il wsdl della fascicolazione di Iride2 versione "Iride"
                    //              al momento abbiamo soltanto il wsdl della fascicolazione di Iride2 versione "JIride"
                    //              Da quello che ho visto l'unica differenza tra i 2 metodi LeggiFascicolo è la presenza della classifica come ultimo parametro in JIride
                    throw new Exception("Bisogna trovare il wsdl della fascicolazione di Iride2 versione Iride ");
                }
                else
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO IRIDE, ANNO FASCICOLO: {0}, NUMERO FASCICOLO: {1}, UTENTE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}, CODICE AOO: {5}", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo);
                    //fascicoloOut = ws.Service.LeggiFascicolo("", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo);
                    // ATTENZIONE! per scommentare la riga sopra bisogna trovare il wsdl della fascicolazione di Iride2 versione "Iride"
                    //              al momento abbiamo soltanto il wsdl della fascicolazione di Iride2 versione "JIride"
                    //              Da quello che ho visto l'unica differenza tra i 2 metodi LeggiFascicolo è la presenza della classifica come ultimo parametro in JIride
                    throw new Exception("Bisogna trovare il wsdl della fascicolazione di Iride2 versione Iride ");
                }
            }

            _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO IRIDE AVVENUTA CORRETTAMENTE, ID FASCICOLO: {0}", fascicoloOut.Id);
            return fascicoloOut;
        }
    }
}
