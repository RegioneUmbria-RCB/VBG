using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.SiprWeb.LeggiProtocollo.TipiDocumento
{
    public class TipiDocumentoDb : ITipiDocumento
    {
        DataBase _db;
        ResolveDatiProtocollazioneService _resolveDatiProtocollazione;
        string _codiceTipoDocumento;
        ProtocolloLogs _log;

        ProtocolloTipiDocumento _tipiDocumento;

        public TipiDocumentoDb(string codiceTipoDocumento, ProtocolloLogs log, DataBase db, ResolveDatiProtocollazioneService resolveDatiProtocollazione)
        {
            _db = db;
            _resolveDatiProtocollazione = resolveDatiProtocollazione;
            _codiceTipoDocumento = codiceTipoDocumento;
            _log = log;
        }

        public string GetDescrizioneTipoDocumento()
        {
            var mgr = new ProtocolloTipiDocumentoMgr(_db);

            var tipiDoc = mgr.GetById(_resolveDatiProtocollazione.IdComune, _codiceTipoDocumento, _resolveDatiProtocollazione.Software, _resolveDatiProtocollazione.Software);
            if (tipiDoc == null)
                _log.WarnFormat("Il tipo documento con codice {0}, comune {1}, restituito dal servizio di protocollazione, non è stato trovato in archivio", _codiceTipoDocumento, _resolveDatiProtocollazione.CodiceComune);

            return tipiDoc.Descrizione;
        }
    }
}
