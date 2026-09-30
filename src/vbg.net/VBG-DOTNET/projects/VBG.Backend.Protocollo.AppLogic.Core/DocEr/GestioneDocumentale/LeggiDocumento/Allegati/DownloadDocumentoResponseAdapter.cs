using ProtocolloDocErGestioneDocumentaleService;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public class DownloadDocumentoResponseAdapter
    {
        GestioneDocumentaleService _wrapper;
        string _idUnitaDocumentale;
        string _indice = "";
        bool _estraiEml;
        string[] _filesDaEscludere;
        bool _estraiZip;
        string[] _zipExtensions;

        public DownloadDocumentoResponseAdapter(GestioneDocumentaleService wrapper, string idUnitaDocumentale, bool estraiEml, string[] filesDaEscludere, bool estraiZip, string[] zipExtensions)
        {
            this._wrapper = wrapper;
            this._estraiEml = estraiEml;
            this._filesDaEscludere = filesDaEscludere;
            this._estraiZip = estraiZip;
            this._zipExtensions = zipExtensions;

            var arrId = idUnitaDocumentale.Split('.');
            _idUnitaDocumentale = arrId[0];

            if (arrId.Length > 1)
            {
                this._indice = arrId[1];
            }
        }

        public AllegatoResponseType Adatta()
        {
            var download = _wrapper.DownloadDocument(_idUnitaDocumentale);
            return _idUnitaDocumentale.ToAllOutToDownload(_wrapper, this._indice, download.handler, this._estraiEml, this._filesDaEscludere, this._estraiZip, this._zipExtensions);
        }
    }
}
