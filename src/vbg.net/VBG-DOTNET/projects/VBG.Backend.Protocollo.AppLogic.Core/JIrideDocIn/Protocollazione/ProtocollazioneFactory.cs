using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIrideDocIn.Protocollazione
{
    public class ProtocollazioneFactory
    {
        public static IProtocollazioneJIrideDocIn Create(ProtocollazioneConfiguration conf, IBindingFactory bindingFactory)
        {
            if (conf.TipoDoc == conf.Vert.TipoDocumentoDocIn)
            {
                return new ProtocollazioneDocIn(conf.Vert, conf.Logs, conf.Serializer, bindingFactory);
            }
            else
            {
                if (conf.Flusso == ProtocolloConstants.COD_ARRIVO)
                    return new ProtocollazioneArrivo(conf, bindingFactory);
                else if (conf.Flusso == ProtocolloConstants.COD_PARTENZA)
                    return new ProtocollazionePartenza(conf, bindingFactory);
                else if (conf.Flusso == ProtocolloConstants.COD_INTERNO)
                    return new ProtocollazioneInterna(conf, bindingFactory);
                else
                    throw new Exception(String.Format("FLUSSO {0} NON TROVATO", conf.Flusso));
            }
        }
    }
}
