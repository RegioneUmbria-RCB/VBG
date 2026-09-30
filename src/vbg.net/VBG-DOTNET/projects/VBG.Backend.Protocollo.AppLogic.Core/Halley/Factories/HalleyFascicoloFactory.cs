using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Adapters;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Builders;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.Factories
{
    public class HalleyFascicoloFactory
    {
        public static IFascicoloHalleyBuilder Create(ResolveDatiProtocollazioneService datiProtocollazioneService, HalleyVerticalizzazioneParametriAdapter vert, IBindingFactory bindingFactory, string tokenDizionario, ProtocolloLogs log, string proxy)
        {
            if (datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new HalleyFascicoloIstanzaBuilder(datiProtocollazioneService.Istanza.NUMEROISTANZA, datiProtocollazioneService.IdComune, datiProtocollazioneService.Software);

            if (datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if (String.IsNullOrEmpty(datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO) || !datiProtocollazioneService.Istanza.DATAPROTOCOLLO.HasValue)
                {
                    log.WarnFormat("L'ISTANZA CODICE {0}, NUMERO {1} E RELATIVA AL MOVIMENTO CODICE {2} DESCRIZIONE {3} NON HA TUTTI I PARAMETRI RELATIVI AL PROTOCOLLO VALORIZZATI, NUMERO PROTOCOLLO {4}, DATA PROTOCOLLO: {5}, IL PROTOCOLLO DEL MOVIMENTO QUINDI NON VERRA' FASCICOLATO", datiProtocollazioneService.CodiceIstanza, datiProtocollazioneService.NumeroIstanza, datiProtocollazioneService.CodiceMovimento, datiProtocollazioneService.Movimento.MOVIMENTO, datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO, datiProtocollazioneService.Istanza.DATAPROTOCOLLO);
                    return null;
                }

                return new HalleyFascicoloMovimentoBuilder(datiProtocollazioneService.Istanza.NUMEROPROTOCOLLO, datiProtocollazioneService.Istanza.DATAPROTOCOLLO.Value.ToString("yyyy"), log, vert, tokenDizionario, proxy, bindingFactory);
            }

            if (datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO || datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
                return null;

            throw new Exception("AMBITO NON TROVATO");
        }
    }
}
