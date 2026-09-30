using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    public class SezioneMenuV3 : IV3MenuLink
    {
        public SezioneMenuV3()
        {

        }

        public SezioneMenuV3(SezioneMenuV2 x, IFrameworkToCoreUrlMapper urlMapper)
        {
            this.CompletaUrl = x.CompletaUrl;
            this.Items = x.Items.Select(y => new MenuItemV3(y, urlMapper)).ToList();
            this.Target = x.Target;
            this.Titolo = x.Titolo;

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

        public string Titolo { get; set; }
        public string UrlFramework { get; set; } = null;
        public string UrlCore { get; set; } = null;
        public string UrlEsterno { get; set; } = null;

        [XmlAttribute(AttributeName = "target")]
        public string Target { get; set; } = "_self";

        [XmlAttribute(AttributeName = "completa-url")]
        public bool CompletaUrl { get; set; } = true;

        [XmlArrayItem(ElementName = "MenuItem")]
        public List<MenuItemV3> Items { get; set; } = new List<MenuItemV3>();
    }
}
