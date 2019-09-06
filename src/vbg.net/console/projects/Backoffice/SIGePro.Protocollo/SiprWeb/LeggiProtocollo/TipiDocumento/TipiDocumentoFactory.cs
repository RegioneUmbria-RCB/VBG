using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.SiprWeb.TipiDocumento;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.SiprWeb.LeggiProtocollo.TipiDocumento
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
