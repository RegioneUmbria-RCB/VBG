using VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class GestioneAnagraficheFactory
    {
        public static IGestioneAnagrafiche Create(InsielVerticalizzazioniConfiguration vert, ProtocolloLogs logs)
        {
            if (vert.TipoGestionePec == TipoGestioneAnagraficaEnum.TipoGestione.MONFALCONE)
            {
                return new AnagraficheMONFALCONE(logs, vert);
            }
            else if (vert.TipoGestionePec == TipoGestioneAnagraficaEnum.TipoGestione.RICERCA_CODICE_FISCALE)
            {
                return new AnagraficheRICERCACF(logs, vert);
            }
            else
            {
                return new AnagraficheRICERCADESC(logs, vert);
            }
        }
    }
}
