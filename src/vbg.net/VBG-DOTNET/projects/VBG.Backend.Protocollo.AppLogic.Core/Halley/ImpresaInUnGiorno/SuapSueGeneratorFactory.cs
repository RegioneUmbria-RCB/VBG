using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno.Sue;
using VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno.Suap;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.Halley.ImpresaInUnGiorno
{
    public class SuapSueGeneratorFactory
    {
        public static ISuapSueGenerator Create(ResolveDatiProtocollazioneService datiProtocollazioneService, List<ProtocolloAllegati> allegati, string radiceEdilizia, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            var alberoProcMgr = new AlberoProcMgr(datiProtocollazioneService.Db);
            var albero = alberoProcMgr.GetById(Convert.ToInt32(datiProtocollazioneService.Istanza.CODICEINTERVENTOPROC), datiProtocollazioneService.IdComune);

            if (albero.SC_CODICE.StartsWith(radiceEdilizia))
                return new SueGenerator(datiProtocollazioneService, logs, allegati, serializer);
            else
                return new SuapGenerator(datiProtocollazioneService, logs, allegati, serializer);
        }
    }
}
