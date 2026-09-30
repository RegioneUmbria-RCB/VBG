using System.Web;

namespace Init.SIGePro.Manager.Logic.ServiziConsole
{
    public class UrlServiziConsole
    {
        private static class Constants
        {
            public const string ListaStradario = "services/rest/backend/generici/{0}/stradario";
            public const string StradarioDaId = "services/rest/backend/generici/{0}/stradario/id/";
            public const string ComuniAssociati = "services/rest/backend/generici/{0}/comuniassociati/{1}";
            public const string ComuniAssociatiPec = "services/rest/backend/generici/{0}/comuniassociati/{1}/{2}/pec";
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

        public string ListaStradario => this._baseUrl + string.Format(Constants.ListaStradario, this._aliasWsServizi);
        public string StradarioDaId => this._baseUrl + string.Format(Constants.StradarioDaId, this._aliasWsServizi);
        public string ComuniAssociati(string software) => this._baseUrl + string.Format(Constants.ComuniAssociati, this._aliasWsServizi, this.UrlEncode(software));
        public string ComuniAssociatiPec(string codiceComune, string software) => this._baseUrl + string.Format(Constants.ComuniAssociatiPec, this._aliasWsServizi, this.UrlEncode(codiceComune), this.UrlEncode(software));
        public string DatiPrivacy => this._baseUrl + string.Format(Constants.DatiPrivacy, this._aliasWsServizi);
        public string ModalitaPagamento => this._baseUrl + string.Format(Constants.ModalitaPagamento, this._aliasWsServizi);

        public string ContiDaCodiceCausaleOnere(string codiceComune, string software, int codiceCausale, string descrizioneCausale)
        {
            // http://devel9:8081/areariservata2/services/rest/backend/generici/E256/SS/conti/E256/bycausaleonere/128/Oneri%20Accessori
            return this._baseUrl + $"services/rest/backend/generici/{this._aliasWsServizi}/{this.UrlEncode(software)}/conti/{this.UrlEncode(codiceComune)}/bycausaleonere/{this.UrlEncode(codiceCausale.ToString())}/{this.UrlEncode(descrizioneCausale)}";
        }

        private string UrlEncode(string val)
        {
            var str = HttpUtility.UrlPathEncode(val);
            //str = str.Replace("/", "%2F");
            str = str.Replace("/", "_"); // TODO: Non va bene e potrebbe generare errori ma risolve un problema sulle chiamate ad esempio:
            // http://172.29.2.41:8080/areariservata2/services/rest/backend/generici/F844/SS/conti/F844/bycausaleonere/91000008/Sanzione%20Art.%20154%20L.R.1%2F2015


            return str;
            //return Uri.EscapeUriString(val);
        }
    }
}
