using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model
{
    public class MenuModel
    {
        public string Descrizione { get; set; }
        public IEnumerable<SezioneMenuModel> Sezioni { get; set; } = new List<SezioneMenuModel>();
        public IEnumerable<MenuItemModel> MenuUtente { get; set; } = new List<MenuItemModel>();
        public IEnumerable<SezioneMenuModel> MenuDestra { get; set; } = new List<SezioneMenuModel>();

        public IEnumerable<MenuItemModel> VociInHomepage => this.Sezioni.SelectMany(x => x.SubMenu)
                                                                .Union(this.MenuUtente)
                                                                .Union(this.MenuDestra.SelectMany(x => x.SubMenu))
                                                                .Where(x => x.MostraInHomePage);
    }
}
