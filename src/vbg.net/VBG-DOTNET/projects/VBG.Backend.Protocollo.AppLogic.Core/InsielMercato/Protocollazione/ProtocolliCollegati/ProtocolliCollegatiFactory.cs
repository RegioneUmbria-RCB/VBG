using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Services;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.LeggiProtocollo.Identificativo;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.InsielMercato.Protocollazione.ProtocolliCollegati
{
    public class ProtocolliCollegatiFactory
    {
        public static IProtocolliCollegati Create(ResolveDatiProtocollazioneService dati, ProtocollazioneService wrapper)
        {
            if (dati.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new ProtocolliCollegatiIstanza();
            else if (dati.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                if(String.IsNullOrEmpty(dati.Istanza.NUMEROPROTOCOLLO) ||  !dati.Istanza.DATAPROTOCOLLO.HasValue)
                    return null;

                var identificativo = IdentificativoFactory.Create(dati.Istanza.FKIDPROTOCOLLO, Convert.ToInt32(dati.Istanza.NUMEROPROTOCOLLO), dati.Istanza.DATAPROTOCOLLO.Value.Year, dati);

                return new ProtocolliCollegatiMovimenti(identificativo, wrapper);
            }
            else
                throw new Exception("AMBITO NON GESTITO");
        }
    }
}
