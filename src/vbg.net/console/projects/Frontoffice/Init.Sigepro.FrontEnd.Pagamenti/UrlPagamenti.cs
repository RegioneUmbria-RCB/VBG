using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.Pagamenti
{
    internal class UrlPagamenti
    {
        private static class Segnaposto
        {
            public const string IdComune = "idComune";
            public const string Software = "software";
            public const string IdDomanda = "idPresentazione";
            public const string StepId = "stepId";
            public const string Token = "token";
        }

        string _url;
        RiferimentiDomanda _riferimentiDomanda;
        Dictionary<string, string> _segnaposto;
        IUrlEncoder _urlEncoder = new HttpContextUrlEncoder();
        IResolveUrl _resolveUrl = new HttpContextResolveUrl();

        public IUrlEncoder UrlEncoder 
        { 
            set { this._urlEncoder = value; } 
        }

        public IResolveUrl ResolveUrl
        {
            set { this._resolveUrl = value; }
        }

        public UrlPagamenti(string url, RiferimentiDomanda riferimentiDomanda, IUrlEncoder urlEncoder, IResolveUrl resolveUrl)
        {
            this._url = url;
            this._riferimentiDomanda = riferimentiDomanda;
            this._urlEncoder = urlEncoder;
            this._resolveUrl = resolveUrl;

            this._segnaposto = new Dictionary<string, string>
            {
                {Segnaposto.IdComune, riferimentiDomanda.IdComune},
                {Segnaposto.Software, riferimentiDomanda.Software},
                {Segnaposto.IdDomanda, riferimentiDomanda.IdDomanda.ToString()},
                {Segnaposto.StepId, riferimentiDomanda.StepId.ToString()},
                {Segnaposto.Token, riferimentiDomanda.Token}
            };
        }

        private string GeneraUrl()
        {
            var url = this._url;

            foreach (var segnaposto in this._segnaposto)
            {
                url = url.Replace(segnaposto.Key, this._urlEncoder.UrlEncode(segnaposto.Value));
            }

            foreach (var segnaposto in this._segnaposto)
            {
                url += String.Format("&{0}={1}", segnaposto.Key, this._urlEncoder.UrlEncode(segnaposto.Value));
            }

            return this._resolveUrl.ToAbsoluteUrl(url);
        }

        public override string ToString()
        {
            return GeneraUrl();
        }
    }
}
