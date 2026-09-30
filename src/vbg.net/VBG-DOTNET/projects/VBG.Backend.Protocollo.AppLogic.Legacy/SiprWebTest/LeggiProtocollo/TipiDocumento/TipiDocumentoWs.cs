using VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.SiprWebTest.LeggiProtocollo.TipiDocumento
{
    public class TipiDocumentoWs : ITipiDocumento
    {
        TipiDocumentoService _wrapper;
        string _codiceTipoDocumento;
        ProtocolloLogs _log;
        Documento_Type _tipoDocumentoResponse;

        public TipiDocumentoWs(TipiDocumentoService wrapper, string codiceTipoDocumento, ProtocolloLogs log)
        {
            _wrapper = wrapper;
            _codiceTipoDocumento = codiceTipoDocumento;
            _log = log;
        }

        public string GetDescrizioneTipoDocumento()
        {
            var response = _wrapper.GetTipiDocumento(new TipoDocDocumentoRequest { Chiave = _codiceTipoDocumento });

            if (response.Documento == null || response.Documento.Length == 0)
                _log.WarnFormat("Il tipo documento con codice {0} restituito dal servizio di protocollazione, non è stato restituito dal web service di lettura tipi documento per estrapolarne la descrizione", _codiceTipoDocumento);

            return response.Documento[0].TipoDocumento;
        }
    }
}
