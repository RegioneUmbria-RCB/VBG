using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg
{
    public class VbgAccessoAttiService : IVbgAccessoAttiService
    {
        private readonly IVbgAccessoAttiProxy _proxy;
        private readonly IVisuraService _visuraService;

        public VbgAccessoAttiService(IVbgAccessoAttiProxy proxy, IVisuraService visuraService)
        {
            this._proxy = proxy;
            this._visuraService = visuraService;
        }

        public IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe)
        {
            return this._proxy.GetListaPratiche(codiceAnagrafe);
        }

        public void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, string uuidIstanza)
        {
            this._proxy.LogAccessoPratica(codiceAnagrafe, idAccessoAtti, uuidIstanza);
        }

        public int GetLivelloAccessoDocumenti(int idAccessoAtti, string uuidIstanza)
        {
            return this._proxy.GetLivelloAccessoDocumenti(idAccessoAtti, uuidIstanza);
        }

        public bool IsAllegatoValido(IDocumentoIstanzaOggettoDiVerifica doc, int? livelloAccessoDocumenti)
        {
            if (doc.ContieneDatiSensibili)
            {
                return false;
            }

            if (livelloAccessoDocumenti is null)
                return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
                        doc.EsitoVerifica == StatoVerificaDocumentoEnum.NonValido ||
                        doc.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;

            switch (livelloAccessoDocumenti)
            {
                case 0:
                    return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido;
                case 2:
                    return doc.EsitoVerifica == StatoVerificaDocumentoEnum.Valido ||
                            doc.EsitoVerifica == StatoVerificaDocumentoEnum.DaVerificare;
                default:
                    return true;
            }
        }

        public IEnumerable<int> GetCodiciOggettoScaricabiliComeZip(int idAccessoAtti, string uuidIstanza)
        {
            var istanza = this._visuraService.GetByUuid(uuidIstanza, false);
            var livelloAccesso = this.GetLivelloAccessoDocumenti(idAccessoAtti, uuidIstanza);
            return istanza.GetCodiciOggettoDocumentiPerAccessoAtti(livelloAccesso);
        }
    }
}
