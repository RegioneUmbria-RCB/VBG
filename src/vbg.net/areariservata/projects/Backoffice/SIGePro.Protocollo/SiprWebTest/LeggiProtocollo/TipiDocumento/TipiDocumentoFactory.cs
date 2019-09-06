using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.SiprWebTest.TipiDocumento;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.TipiDocumento
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
