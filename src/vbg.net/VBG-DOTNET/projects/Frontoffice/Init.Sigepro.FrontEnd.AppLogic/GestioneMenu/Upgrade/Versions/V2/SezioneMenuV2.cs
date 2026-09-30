using System.Collections.Generic;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2
{
    public class SezioneMenuV2 : IMenuItemConUrl
    {
        public string Titolo { get; set; }

        public string Url { get; set; } = "#";

        public bool HaLink
        {
            get
            {
                return this.Url != "#";
            }
        }

        [XmlAttribute(AttributeName = "target")]
        public string Target { get; set; } = "_self";

        [XmlAttribute(AttributeName = "completa-url")]
        public bool CompletaUrl { get; set; } = true;

        [XmlArrayItem(ElementName = "MenuItem")]
        public List<MenuItemV2> Items { get; set; } = new List<MenuItemV2>();
    }
}
