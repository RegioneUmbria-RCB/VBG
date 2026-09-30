using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Halley.Builders;
using Init.SIGePro.Protocollo.Halley.Interfaces;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Halley.Adapters;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Halley.Factories
{
    public class HalleyFascicoloFactory
    {
        public static IFascicoloHalleyBuilder Create(ResolveDatiProtocollazioneService datiProtocollazioneService, HalleyVerticalizzazioneParametriAdapter vert, string tokenDizionario, ProtocolloLogs log, string proxy)
        {
            if (datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new HalleyFascicoloIstanzaBuilder(datiProtocollazioneService.Istanza.NUMEROISTANZA, datiProtocollazioneService.IdComune, datiProtocollazioneService.Software);
            else if (datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO) || !datiProtocollazioneService.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    log.WarnFormat("L'ISTANZA CODICE {0}, NUMERO {1} E RELATIVA AL MOVIMENTO CODICE {2} DESCRIZIONE {3} NON HA TUTTI I PARAMETRI RELATIVI AL PROTOCOLLO VALORIZZATI, NUMERO PROTOCOLLO {4}, DATA PROTOCOLLO: {5}, IL PROTOCOLLO DEL MOVIMENTO QUINDI NON VERRA' FASCICOLATO", datiProtocollazioneService.CodiceIstanza, datiProtocollazioneService.NumeroIstanza, datiProtocollazioneService.CodiceMovimento, datiProtocollazioneService.Movimento.MOVIMENTO, datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO, datiProtocollazioneService.Istanza.DATAPROTOCOLLO);
                    return null;
                }

                return new HalleyFascicoloMovimentoBuilder(datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO, datiProtocollazioneService.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy"), log, vert, tokenDizionario, proxy);
            }
            else if (datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.NESSUNO || datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
                return null;
            else
                throw new Exception("AMBITO NON TROVATO");
        }
    }
}
