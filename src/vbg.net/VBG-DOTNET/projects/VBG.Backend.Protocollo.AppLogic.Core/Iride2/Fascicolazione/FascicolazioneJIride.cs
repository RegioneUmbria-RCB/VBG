using FascicolazioneIride2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Iride2.Fascicolazione
{
    public class FascicolazioneJIride : IFascicolazione
    {
        ProtocolloLogs _logs;
        FascicolazioneClientServiceCreator _fascicolazioneClientServiceCreator;

        public FascicolazioneJIride(ProtocolloLogs logs, FascicolazioneClientServiceCreator serviceCreator)
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
                    _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ID: {0}", idFascicolo.ToString());
                    fascicoloOut = ws.Service.LeggiFascicolo(idFascicolo.ToString(), "", "", utente, ruolo, codiceAmministrazione, codiceAoo, "");
                }
                else
                {
                    _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE, ANNO FASCICOLO: {0}, NUMERO FASCICOLO: {1}, UTENTE: {2}, RUOLO: {3}, CODICE AMMINISTRAZIONE: {4}, CODICE AOO: {5}, CLASSIFICA: {6}", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo, classifica);
                    fascicoloOut = ws.Service.LeggiFascicolo("", anno, numero, utente, ruolo, codiceAmministrazione, codiceAoo, classifica);
                }
            }

            _logs.InfoFormat("CHIAMATA A LEGGI FASCICOLO J_IRIDE AVVENUTA CORRETTAMENTE, ID FASCICOLO: {0}", fascicoloOut.Id);

            return fascicoloOut;
        }
    }
}
