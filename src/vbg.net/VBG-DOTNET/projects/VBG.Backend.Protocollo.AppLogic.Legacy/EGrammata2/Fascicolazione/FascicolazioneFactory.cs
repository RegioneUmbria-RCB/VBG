using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Fascicolazione
{
    public class FascicolazioneFactory
    {
        public static IFascicolazione Create(ProtocollazioneRequestConfiguration conf)
        {
            if (conf.DatiProtoService.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
                return new FascicolazioneIstanza(conf.DatiProtoService);
            else if (conf.DatiProtoService.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
                return new FascicolazioneMovimento(conf);
            else
                return new FascicolazioneDefault();
        }
    }
}
