namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    public class V3MenuLinkBuilder
    {
        private readonly IV3MenuLink _sezioneMenu;

        public V3MenuLinkBuilder(IV3MenuLink sezioneMenu)
        {
            this._sezioneMenu = sezioneMenu;
        }

        public string GetMenuLink(IMenuUrlBuilder urlBuilder)
        {
            var parsedUrl = this.PreparaUrl(urlBuilder);

            return urlBuilder.RisolviSegnaposto(parsedUrl);
        }

        private string PreparaUrl(IMenuUrlBuilder urlBuilder)
        {
            var parsedUrl = "";

            if (!string.IsNullOrEmpty(this._sezioneMenu.UrlCore))
            {
                parsedUrl = $"{urlBuilder.SegnapostoBaseUrlCore}{this._sezioneMenu.UrlCore}";

                // Se sono nell'applicazione FW accodo il token e risolvo alias e software
                // Se sono già nell'applicazione Core risolvo solo alias e software
#if NET48
                parsedUrl += $"?token={urlBuilder.SegnapostoToken}";
#endif
                return parsedUrl;
            }

            if (!string.IsNullOrEmpty(this._sezioneMenu.UrlFramework))
            {
                parsedUrl = $"{urlBuilder.SegnapostoBaseUrlFramework}{this._sezioneMenu.UrlFramework.Substring(1)}";

                if (this._sezioneMenu.CompletaUrl)
                {
                    parsedUrl = urlBuilder.AggiungiAliasESoftwareAQuerystring(parsedUrl);
                }

#if NET9_0_OR_GREATER
                // Se sono nell'applicazione Core accodo il token e risolvo alias e software
                // Se sono già nell'applicazione FW risolvo solo alias e software
                parsedUrl = urlBuilder.AggiungiTokenAQuerystring(parsedUrl);
#endif
                return parsedUrl;
            }

            if (!string.IsNullOrEmpty(this._sezioneMenu.UrlEsterno))
            {
                parsedUrl = $"{this._sezioneMenu.UrlEsterno}";

                if (this._sezioneMenu.CompletaUrl)
                {
                    parsedUrl = urlBuilder.AggiungiAliasESoftwareAQuerystring(parsedUrl);
                }
            }

            return parsedUrl;
        }
    }
}
