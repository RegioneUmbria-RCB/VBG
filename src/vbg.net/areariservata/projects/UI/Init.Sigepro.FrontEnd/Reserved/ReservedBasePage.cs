using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved
{
    
    public class ReservedBasePage : BasePage
    {
        [Inject]
        public IAuthenticationDataResolver _authenticationDataResolver { get; set; }


        public UserAuthenticationResult UserAuthenticationResult
        {
            get { return _authenticationDataResolver.DatiAutenticazione; }
        }

        protected string CodiceUtente
        {
            get { return UserAuthenticationResult.DatiUtente.Codicefiscale; }
        }


        public ReservedBasePage()
        {

        }



        #region gestione della navigazione

        public string ResolvePlaceholders(string url)
        {
            if (url.IndexOf("{software}") >= 0)
            {
                url = url.Replace("{software}", Software);
            }

            if (url.IndexOf("{idcomune}") >= 0)
            {
                url = url.Replace("{idcomune}", IdComune);
            }

            return url;
        }

        public string BuildClientUrl(string url, Action<QuerystringArgumentsList> parametersBuilder = null, bool convertToClientUrl = true)
        {
            var ub = new UrlBuilder();
            var qsParts = url.IndexOf("?") == -1 ? Enumerable.Empty<string>() : url.Split('?')[1].Split('&').Select(x => x.Split('=')).Select(x => x[0].ToLowerInvariant());

            if (url.IndexOf("{software}") >= 0)
            {
                url = url.Replace("{software}", Software);
            }
            else
            {
                if (!qsParts.Contains("software"))
                {
                    ub.AddDefaultParameter("software", Software);
                }
            }

            if (url.IndexOf("{idcomune}") >= 0)
            {
                url = url.Replace("{idcomune}", IdComune);
            }
            else
            {
                if (!qsParts.Contains("idcomune"))
                {
                    ub.AddDefaultParameter("idcomune", IdComune);
                }
            }

            if (url.IndexOf("{token}") >= 0)
            {
                url = url.Replace("{token}", _authenticationDataResolver.DatiAutenticazione.Token);
            }

            var newUrl = ub.Build(url, parametersBuilder);

            return convertToClientUrl ? ResolveClientUrl(newUrl) : newUrl;
        }

        protected void Redirect(string url, Action<QuerystringArgumentsList> parametersBuilder)
        {
            var ub = new UrlBuilder();

            ub.AddDefaultParameter("Software", Software);
            ub.AddDefaultParameter("IdComune", IdComune);

            var newUrl = ub.Build(url, parametersBuilder);

            Response.Redirect(newUrl);
        }

        #endregion

    }
}
