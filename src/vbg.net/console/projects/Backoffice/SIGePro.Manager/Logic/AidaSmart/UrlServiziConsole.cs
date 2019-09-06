using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AidaSmart
{
    public class UrlServiziConsole
    {
        private static class Constants
        {
            public const string ListaStradario = "services/rest/backend/generici/{0}/stradario";
            public const string StradarioDaId = "services/rest/backend/generici/{0}/stradario/id/";
            public const string ComuniAssociati = "services/rest/backend/generici/{0}/comuniassociati/";
            public const string DatiPrivacy = "public_json/orariecontatti/{0}/SS";
            public const string ModalitaPagamento = "services/rest/backend/generici/{0}/modalitapagamento";
        }

        private readonly string _baseUrl;
        private readonly string _aliasWsServizi;

        public UrlServiziConsole(string baseUrl, string aliasWsServizi)
        {
            this._baseUrl = baseUrl;
            this._aliasWsServizi = aliasWsServizi;

            if (!this._baseUrl.EndsWith("/"))
            {
                this._baseUrl += "/";
            }
        }

        public string ListaStradario => this._baseUrl + String.Format(Constants.ListaStradario, this._aliasWsServizi);
        public string StradarioDaId => this._baseUrl + String.Format(Constants.StradarioDaId, this._aliasWsServizi);
        public string ComuniAssociati => this._baseUrl + String.Format(Constants.ComuniAssociati, this._aliasWsServizi);
        public string DatiPrivacy => this._baseUrl + String.Format(Constants.DatiPrivacy, this._aliasWsServizi);
        public string ModalitaPagamento => this._baseUrl + String.Format(Constants.ModalitaPagamento, this._aliasWsServizi); 
    }
}
