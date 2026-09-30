using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2
{
    [XmlRoot(ElementName = "MainMenu")]
    public class MainMenuV2
    {
        public static class Constants
        {
            public const int ExpectedVersion = 2;
        }

        public int Versione { get; set; } = 2;

        public string Descrizione { get; set; }

        [XmlArrayItem(ElementName = "SezioneMenu")]
        public List<SezioneMenuV2> Sezioni { get; set; } = new List<SezioneMenuV2>();

        [XmlArrayItem(ElementName = "MenuItem")]
        public List<MenuItemV2> MenuUtente { get; set; } = new List<MenuItemV2>();

        [XmlArrayItem(ElementName = "SezioneMenu")]
        public List<SezioneMenuV2> MenuDestra { get; set; } = new List<SezioneMenuV2>();


        public static MainMenuV2 FromMenuFile(MenuFile menu)
        {
            if (menu.Versione != Constants.ExpectedVersion)
            {
                throw new InvalidOperationException($"La versione del menu passato non è corretta. Atteso {Constants.ExpectedVersion}, ricevuto {menu.Versione}");
            }

            using (var ms = new MemoryStream(menu.BinaryContent))
            {
                var xs = new XmlSerializer(typeof(MainMenuV2));
                return (MainMenuV2)xs.Deserialize(ms);
            }
        }

        public static MainMenuV2 FromXmlFile(byte[] xmlFile)
        {
            using (var ms = new MemoryStream(xmlFile))
            {
                var xs = new XmlSerializer(typeof(MainMenuV2));
                return (MainMenuV2)xs.Deserialize(ms);
            }
        }

        public IEnumerable<MenuItemV2> GetVociHomePage()
        {
            return this.Sezioni
                        .SelectMany(x => x.Items)
                        .Where(x => x.MostraInHomePage)
                        .Union(this.MenuUtente.Where(x => x.MostraInHomePage))
                        .ToArray();
        }

        public MenuFile ToMenuFile()
        {
            using (var ms = new MemoryStream())
            {
                var xs = new XmlSerializer(typeof(MainMenuV2));
                xs.Serialize(ms, this);

                return new ByteArrayMenuFile(ms.GetBuffer());
            }
        }

        public MenuModel ToMenuModel(IMenuUrlBuilder urlBuilder)
        {
            return new MenuModel
            {
                Descrizione = this.Descrizione,
                Sezioni = this.Sezioni.Select(sezione => new SezioneMenuModel
                {
                    Url = urlBuilder.ParseMenuUrl(sezione),
                    Target = sezione.Target,
                    Titolo = sezione.Titolo,
                    SubMenu = sezione.Items.Select(item => new MenuItemModel
                    {
                        Descrizione = item.Descrizione,
                        Icona = this.IconaMenu(item),
                        Target = item.Target,
                        Titolo = item.Titolo,
                        Url = urlBuilder.ParseMenuUrl(item),
                        MostraInHomePage = item.MostraInHomePage
                    })
                }),
                MenuUtente = this.MenuUtente.Select(item => new MenuItemModel
                {
                    Descrizione = item.Descrizione,
                    Icona = this.IconaMenu(item),
                    Target = item.Target,
                    Titolo = item.Titolo,
                    Url = urlBuilder.ParseMenuUrl(item),
                    MostraInHomePage = item.MostraInHomePage
                }),
                MenuDestra = this.MenuDestra.Select(sezione => new SezioneMenuModel
                {
                    Url = urlBuilder.ParseMenuUrl(sezione),
                    Target = sezione.Target,
                    Titolo = sezione.Titolo,
                    SubMenu = sezione.Items.Select(item => new MenuItemModel
                    {
                        Descrizione = item.Descrizione,
                        Icona = this.IconaMenu(item),
                        Target = item.Target,
                        Titolo = item.Titolo,
                        Url = urlBuilder.ParseMenuUrl(item),
                        MostraInHomePage = item.MostraInHomePage
                    })
                })
            };
        }

        private IMenuIcon IconaMenu(MenuItemV2 item)
        {
            if (!String.IsNullOrEmpty(item.IconaFontAwesome))
            {
                return new MenuIconModel(item.IconaFontAwesome);
            }

            if (!String.IsNullOrEmpty(item.IconaBootstrap))
            {
                return new MenuIconModel($"glyphicon {item.IconaBootstrap}");
            }

            return null;
        }
    }
}
