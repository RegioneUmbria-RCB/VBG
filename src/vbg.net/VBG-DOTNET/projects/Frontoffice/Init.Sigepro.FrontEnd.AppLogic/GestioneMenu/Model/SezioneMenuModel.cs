using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model
{
    public class SezioneMenuModel
    {
        public string Titolo { get; set; }
        public string Url { get; set; }
        public string Target { get; set; }
        public IEnumerable<MenuItemModel> SubMenu { get; set; } = new List<MenuItemModel>();
        public bool HaLink => !string.IsNullOrEmpty(this.Url) && this.Url != "#";
    }
}