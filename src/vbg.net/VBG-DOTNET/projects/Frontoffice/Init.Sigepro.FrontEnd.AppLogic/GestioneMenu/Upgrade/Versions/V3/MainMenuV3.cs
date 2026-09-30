using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Model;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V3
{
    [XmlRoot(ElementName = "MainMenu")]
    public class MainMenuV3
    {
        public static class Constants
        {
            public const int ExpectedVersion = 3;
        }

        public int Versione { get; set; } = Constants.ExpectedVersion;

        public string Descrizione { get; set; }

        [XmlArrayItem(ElementName = "SezioneMenu")]
        public List<SezioneMenuV3> Sezioni { get; set; } = new List<SezioneMenuV3>();

        [XmlArrayItem(ElementName = "MenuItem")]
        public List<MenuItemV3> MenuUtente { get; set; } = new List<MenuItemV3>();

        [XmlArrayItem(ElementName = "SezioneMenu")]
        public List<SezioneMenuV3> MenuDestra { get; set; } = new List<SezioneMenuV3>();


        public static MainMenuV3 FromMenuFile(MenuFile menu)
        {
            if (menu.Versione != Constants.ExpectedVersion)
            {
                throw new InvalidOperationException($"La versione del menu passato non è corretta. Atteso {Constants.ExpectedVersion}, ricevuto {menu.Versione}");
            }
            // File.WriteAllBytes(@"c:\temp\menu.xml", menu.BinaryContent);
            using (var ms = new MemoryStream(menu.BinaryContent))
            {
                var xs = new XmlSerializer(typeof(MainMenuV3));
                return (MainMenuV3)xs.Deserialize(ms);
            }
        }

        public IEnumerable<MenuItemV3> GetVociHomePage()
        {
            return this.Sezioni
                        .SelectMany(x => x.Items)
                        .Where(x => x.MostraInHomePage)
                        .Union(this.MenuUtente.Where(x => x.MostraInHomePage));
        }

        public MenuFile ToMenuFile()
        {
            using (var ms = new MemoryStream())
            {
                /*
                var settings = new XmlWriterSettings
                {
                    Encoding = Encoding.UTF8,
                    Indent = true,

                };
                using (var writer = XmlWriter.Create(ms, settings))
                {*/
                XmlSerializer serializer = new XmlSerializer(this.GetType());
                serializer.Serialize(ms, this);
                //}

                return new ByteArrayMenuFile(ms.ToArray());
            }
        }

        public MenuModel ToMenuModel(IMenuUrlBuilder urlBuilder, IGlyphiconToFontAwesomeIconMapper iconMapper)
        {
            return new MenuModel
            {
                Descrizione = this.Descrizione,
                Sezioni = this.Sezioni.Select(sezione => new SezioneMenuModel
                {
                    Url = new V3MenuLinkBuilder(sezione).GetMenuLink(urlBuilder),
                    Target = sezione.Target,
                    Titolo = sezione.Titolo,
                    SubMenu = sezione.Items.Select(item => new MenuItemModel
                    {
                        Descrizione = item.Descrizione,
                        Icona = this.IconaMenu(item, iconMapper),
                        Target = item.Target,
                        Titolo = item.Titolo,
                        Url = new V3MenuLinkBuilder(item).GetMenuLink(urlBuilder),
                        MostraInHomePage = item.MostraInHomePage
                    })
                }),
                MenuUtente = this.MenuUtente.Select(item => new MenuItemModel
                {
                    Descrizione = item.Descrizione,
                    Icona = this.IconaMenu(item, iconMapper),
                    Target = item.Target,
                    Titolo = item.Titolo,
                    Url = new V3MenuLinkBuilder(item).GetMenuLink(urlBuilder),
                    MostraInHomePage = item.MostraInHomePage
                }),
                MenuDestra = this.MenuDestra.Select(sezione => new SezioneMenuModel
                {
                    Url = new V3MenuLinkBuilder(sezione).GetMenuLink(urlBuilder),
                    Target = sezione.Target,
                    Titolo = sezione.Titolo,
                    SubMenu = sezione.Items.Select(item => new MenuItemModel
                    {
                        Descrizione = item.Descrizione,
                        Icona = this.IconaMenu(item, iconMapper),
                        Target = item.Target,
                        Titolo = item.Titolo,
                        Url = new V3MenuLinkBuilder(item).GetMenuLink(urlBuilder),
                        MostraInHomePage = item.MostraInHomePage
                    })
                })
            };
        }

        private IMenuIcon IconaMenu(MenuItemV3 item, IGlyphiconToFontAwesomeIconMapper iconMapper)
        {
            if (!String.IsNullOrEmpty(item.IconaFontAwesome))
            {
                return new MenuIconModel(item.IconaFontAwesome);
            }

            if (!String.IsNullOrEmpty(item.IconaBootstrap))
            {
                string newIcon = iconMapper.TryMapIcon(item.IconaBootstrap); // convert glyphicon to FontAwesome

                if (string.IsNullOrEmpty(newIcon))
                    return new MenuIconModel($"glyphicon {item.IconaBootstrap}");
                else
                    return new MenuIconModel($"fa {newIcon}");
            }

            return null;
        }
    }
}
