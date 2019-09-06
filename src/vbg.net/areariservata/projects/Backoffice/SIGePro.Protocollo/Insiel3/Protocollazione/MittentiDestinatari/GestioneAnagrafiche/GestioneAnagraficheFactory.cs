using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari.GestioneAnagrafiche
{
    public class GestioneAnagraficheFactory
    {
        public static IGestioneAnagrafiche Create(TipoGestioneAnagraficaEnum.TipoGestione tipo, TipoGestioneAnagraficaEnum.TipoAggiornamento tipoAggiornamento, ProtocolloLogs logs)
        {
            if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.MONFALCONE)
            {
                return new AnagraficheMONFALCONE(logs, tipoAggiornamento);
            }
            else if (tipo == TipoGestioneAnagraficaEnum.TipoGestione.RICERCA_CODICE_FISCALE)
            {
                return new AnagraficheRICERCACF(logs, tipoAggiornamento);
            }
            else
            {
                return new AnagraficheRICERCADESC(tipo, tipoAggiornamento, logs);
            }
        }
    }
}
