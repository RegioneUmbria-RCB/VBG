using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    public class MenuItemV3 : IV3MenuLink
    {
        public MenuItemV3() { }

        public MenuItemV3(MenuItemV2 x, IFrameworkToCoreUrlMapper urlMapper)
        {
            this.CompletaUrl = x.CompletaUrl;
            this.Descrizione = x.Descrizione;
            this.IconaBootstrap = x.IconaBootstrap;
            this.IconaFontAwesome = x.IconaFontAwesome;
            this.IdIcona = x.IdIcona;
            this.MostraInHomePage = x.MostraInHomePage;
            this.Target = x.Target;
            this.Titolo = x.Titolo;
            this.UrlIcona = x.UrlIcona;
            this.UrlFramework = null;
            this.UrlCore = null;
            this.UrlEsterno = null;

            if (x.Url != "#")
            {

                if (x.Url?.StartsWith("http") ?? false)
                {
                    this.UrlEsterno = x.Url;
                }
                else
                {
                    this.UrlFramework = x.Url;
                    this.UrlCore = urlMapper.TryMapUrl(x.Url);
                }
            }
        }

        [XmlElement]
        public string Titolo { get; set; }

        [XmlElement]
        public string Descrizione { get; set; }

        //string _url;
        [XmlElement]
        public string UrlFramework { get; set; }

        [XmlElement]
        public string UrlCore { get; set; }

        [XmlElement]
        public string UrlEsterno { get; set; }


        [XmlAttribute(AttributeName = "id-icona")]
        public string IdIcona { get; set; }

        [XmlAttribute(AttributeName = "completa-url")]
        public bool CompletaUrl { get; set; } = true;

        [XmlAttribute(AttributeName = "url-icona")]
        public string UrlIcona { get; set; }

        [XmlAttribute(AttributeName = "mostra-in-home-page")]
        public bool MostraInHomePage { get; set; } = true;

        [XmlAttribute(AttributeName = "target")]
        public string Target { get; set; } = "_self";

        [XmlAttribute(AttributeName = "icona-bs")]
        public string IconaBootstrap { get; set; }

        [XmlAttribute(AttributeName = "icona-fa")]
        public string IconaFontAwesome { get; set; } = "";

    }
}
