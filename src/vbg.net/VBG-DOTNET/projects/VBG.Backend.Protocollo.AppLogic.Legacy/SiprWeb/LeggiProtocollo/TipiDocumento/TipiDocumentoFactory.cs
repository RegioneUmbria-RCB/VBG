using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWeb.LeggiProtocollo.TipiDocumento
{
    public class TipiDocumentoFactory
    {
        public static ITipiDocumento Create(bool usaWsTipiDoc, TipiDocumentoService wrapper, string codiceTipoDocumento, ProtocolloLogs log, ResolveDatiProtocollazioneService datiProtocollazioneService, DataBase db)
        { 
            ITipiDocumento rVal;

            if (usaWsTipiDoc)
                rVal = new TipiDocumentoWs(wrapper, codiceTipoDocumento, log);
            else
                rVal = new TipiDocumentoDb(codiceTipoDocumento, log, db, datiProtocollazioneService);

            return rVal;
        }
    }
}
