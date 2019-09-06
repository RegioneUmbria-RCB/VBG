using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices;
using PersonalLib2.Data;

namespace Init.SIGePro.Protocollo.SiprWebTest.LeggiProtocollo.TipiDocumento
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
            _resolveDatiProtocollazione = resolveDatiProtocollazione;
            _codiceTipoDocumento = codiceTipoDocumento;
            _log = log;
        }

        public string GetDescrizioneTipoDocumento()
        {
            var mgr = new ProtocolloTipiDocumentoMgr(_db);

            var tipiDoc = mgr.GetById(_resolveDatiProtocollazione.IdComune, _codiceTipoDocumento, _resolveDatiProtocollazione.Software, _resolveDatiProtocollazione.CodiceComune);
            if (tipiDoc == null)
                _log.WarnFormat("Il tipo documento con codice {0} restituito dal servizio di protocollazione, non è stato trovato in archivio", _codiceTipoDocumento);

            return tipiDoc.Descrizione;
        }
    }
}
