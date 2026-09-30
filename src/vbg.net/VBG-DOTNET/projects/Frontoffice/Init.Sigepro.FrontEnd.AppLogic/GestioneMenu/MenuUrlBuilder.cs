using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public partial class MenuUrlBuilder : IMenuUrlBuilder
    {
        private readonly IAliasSoftwareResolver _aliasSoftwareResolver;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;
        private readonly IUrlEncoder _urlEncoder;
        private readonly IBaseUrl _baseUrl;

        public string SegnapostoAlias => "{alias}";

        public string SegnapostoSoftware => "{software}";

        public string SegnapostoToken => "{token}";

        public string SegnapostoBaseUrlCore => "{core-base-url}";

        public string SegnapostoBaseUrlFramework => "{framework-base-url}";

        public MenuUrlBuilder(IAliasSoftwareResolver aliasSoftwareResolver, IAuthenticationDataResolver authenticationDataResolver, IUrlEncoder urlEncoder, IBaseUrl baseUrl)
        {
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._authenticationDataResolver = authenticationDataResolver;
            this._urlEncoder = urlEncoder;
            this._baseUrl = baseUrl;
        }

        private string ParseMenuUrl(string url, bool completaUrl = false)
        {
            var tokenized = new TokenizedUrl(url);

            if (completaUrl)
            {
                tokenized.AggiungiAlias(this._aliasSoftwareResolver.AliasComune);
                tokenized.AggiungiSoftware(this._aliasSoftwareResolver.Software);
            }

            tokenized.SostituisciSoftware(this._aliasSoftwareResolver.Software);
            tokenized.SostituisciAlias(this._aliasSoftwareResolver.AliasComune);
            tokenized.SostituisciToken(this._authenticationDataResolver.DatiAutenticazione.Token);

            return tokenized.Rebuild(this._urlEncoder);
        }

        public string ParseMenuUrl(IMenuItemConUrl item)
        {
            return this.ParseMenuUrl(item.Url, item.CompletaUrl);
        }

        public string RisolviSegnaposto(string parsedCoreUrl)
        {
            parsedCoreUrl = parsedCoreUrl.Replace(this.SegnapostoAlias, this._aliasSoftwareResolver.AliasComune);
            parsedCoreUrl = parsedCoreUrl.Replace(this.SegnapostoSoftware, this._aliasSoftwareResolver.Software);
            parsedCoreUrl = parsedCoreUrl.Replace(this.SegnapostoToken, this._authenticationDataResolver.IsAuthenticated ? this._authenticationDataResolver.DatiAutenticazione.Token : null);
            parsedCoreUrl = parsedCoreUrl.Replace(this.SegnapostoBaseUrlCore, this._baseUrl.BaseUrlCore);
            parsedCoreUrl = parsedCoreUrl.Replace(this.SegnapostoBaseUrlFramework, this._baseUrl.BaseUrlFramework);

            return parsedCoreUrl;
        }

        public string AggiungiAliasESoftwareAQuerystring(string parsedUrl)
        {
            if (string.IsNullOrEmpty(parsedUrl))
            {
                return parsedUrl;
            }

            if (parsedUrl.IndexOf("?") == -1)
            {
                parsedUrl += "?";
            }

            if (parsedUrl.IndexOf("idcomune=") == -1)
            {
                parsedUrl += (parsedUrl.EndsWith("?") ? "" : "&") + $"idcomune={this._aliasSoftwareResolver.AliasComune}";
            }

            if (parsedUrl.IndexOf("software=") == -1)
            {
                parsedUrl += (parsedUrl.EndsWith("?") ? "" : "&") + $"software={this._aliasSoftwareResolver.Software}";
            }

            return parsedUrl;
        }

        public string AggiungiTokenAQuerystring(string parsedUrl)
        {
            if (string.IsNullOrEmpty(parsedUrl))
            {
                return parsedUrl;
            }

            if (parsedUrl.IndexOf("?") == -1)
            {
                parsedUrl += "?";
            }

            if (parsedUrl.IndexOf("token=") == -1)
            {
                parsedUrl += (parsedUrl.EndsWith("?") ? "" : "&") + $"token={(this._authenticationDataResolver.IsAuthenticated ? this._authenticationDataResolver.DatiAutenticazione.Token : string.Empty)}";
            }

            return parsedUrl;
        }
    }
}
