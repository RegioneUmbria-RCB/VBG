using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V1.Protocollazione
{
    public class ProtocollazioneV1Factory
    {
        public static IKibernetesProtocollazioneV1 Create(IParametriService parametriService, List<IAnagraficaAmministrazione> anagrafiche, IDatiProtocollo datiProto, string funzionario)
        {
            if (datiProto.Flusso.Equals(ProtocolloConstants.COD_ARRIVO))
                return new ProtocollazioneArrivo(parametriService, anagrafiche, datiProto, funzionario);
            else if (datiProto.Flusso.Equals(ProtocolloConstants.COD_PARTENZA))
                return new ProtocollazionePartenza(parametriService, anagrafiche, datiProto, funzionario);
            else if (datiProto.Flusso.Equals(ProtocolloConstants.COD_INTERNO))
                return new ProtocollazioneInterna(parametriService, datiProto, funzionario);
            else
                throw new Exception(String.Format("FLUSSO {0} NON CODIFICATO", datiProto.Flusso));
        }
    }
}
