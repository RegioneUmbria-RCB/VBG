using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Protocollazione
{
    public class ProtocollazioneFactory
    {

        public static IProtocollazioneUrbi Create(IDatiProtocollo datiProtocollo, VerticalizzazioniWrapper vert, string operatore, ProtocolloLogs logs, ProtocolloSerializer serializer, IEnumerable<IAnagraficaAmministrazione> mittDest, ResolveDatiProtocollazioneService datiGenerali)
        {
            if (vert.AttivaFascicolazioneContestuale && datiGenerali.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (datiProtocollo.Flusso == ProtocolloConstants.COD_ARRIVO)
                    return new ProtocollazioneArrivoFascContestuale(datiProtocollo, vert, operatore, logs, serializer, mittDest, datiGenerali);
                else if (datiProtocollo.Flusso == ProtocolloConstants.COD_PARTENZA)
                    return new ProtocollazionePartenzaFascContestuale(datiProtocollo, vert, operatore, logs, serializer, mittDest, datiGenerali);
                else if (datiProtocollo.Flusso == ProtocolloConstants.COD_INTERNO)
                    return new ProtocollazioneInternaFascContestuale(datiProtocollo, vert, operatore, logs, serializer, datiGenerali);
                else
                    throw new Exception(String.Format("FLUSSO {0} NON TROVATO", datiProtocollo.Flusso));
            }
            else
            {
                if (datiProtocollo.Flusso == ProtocolloConstants.COD_ARRIVO)
                    return new ProtocollazioneArrivo(datiProtocollo, vert, operatore, logs, serializer, mittDest);
                else if (datiProtocollo.Flusso == ProtocolloConstants.COD_PARTENZA)
                    return new ProtocollazionePartenza(datiProtocollo, vert, operatore, logs, serializer, mittDest);
                else if (datiProtocollo.Flusso == ProtocolloConstants.COD_INTERNO)
                    return new ProtocollazioneInterna(datiProtocollo, vert, operatore, logs, serializer);
                else
                    throw new Exception(String.Format("FLUSSO {0} NON TROVATO", datiProtocollo.Flusso));
            }
        }
    }
}
