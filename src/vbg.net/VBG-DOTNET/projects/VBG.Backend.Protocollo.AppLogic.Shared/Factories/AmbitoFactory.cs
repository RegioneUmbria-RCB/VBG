using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Factories
{
    public class AmbitoFactory
    {
        public static IAmbito Create(ResolveDatiProtocollazioneService dati)
        {
            if (dati.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new AmbitoIstanza(dati.Istanza);

            if (dati.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new AmbitoMovimento(dati.Movimento);

            if (dati.TipoAmbito == AmbitoProtocollazioneEnum.DA_PANNELLO_PEC)
                return new AmbitoPec(dati.DatiPec);

            if (dati.TipoAmbito == AmbitoProtocollazioneEnum.NESSUNO)
                return new AmbitoNessuno();

            return new AmbitoDefault();
        }
    }
}
