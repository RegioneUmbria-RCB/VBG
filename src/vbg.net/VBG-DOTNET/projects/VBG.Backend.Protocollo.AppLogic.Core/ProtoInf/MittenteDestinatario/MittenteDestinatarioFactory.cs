using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.MittenteDestinatario
{
    public static class MittenteDestinatarioFactory
    {
        public static IMittenteDestinatario Create(RequestInfo info)
        {
            info.Logs.InfoFormat("FLUSSO: {0}", info.Metadati.Flusso);

            if (info.Metadati.Flusso == ProtocolloConstants.COD_ARRIVO)
            {
                return new MittenteDestinatarioArrivo(info.Anagrafiche.ToList()[0], info.Serializer, info.AmministrazioneDestinatario);
            }
            else if (info.Metadati.Flusso == ProtocolloConstants.COD_PARTENZA)
            {
                return new MittenteDestinatarioPartenza(info.Metadati.Uo, info.Anagrafiche, info.Serializer);
            }
            else if (info.Metadati.Flusso == ProtocolloConstants.COD_INTERNO)
            {
                return new MittenteDestinatarioInterno(info.AmministrazioneDestinatario, info.Metadati.Amministrazione, info.Serializer);
            }
            else
            {
                throw new Exception($"FLUSSO {info.Metadati.Flusso} NON GESTITO");
            }
        }
    }
}
