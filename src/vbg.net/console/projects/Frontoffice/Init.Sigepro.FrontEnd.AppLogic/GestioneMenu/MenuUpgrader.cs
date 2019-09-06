using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu
{
    public class MenuUpgrader
    {
        IAliasSoftwareResolver _aliasResolver;

        public MenuUpgrader(IAliasSoftwareResolver aliasResolver)
        {
            this._aliasResolver = aliasResolver;
        }

        public MainMenu Upgrade(byte[] menuFile)
        {
            var versione = ExtractVersion(menuFile);
            MainMenu mainMenu = null;

            if (versione == 1)
            {
                mainMenu = UpgradeFromLegacy(menuFile);
            }
            else
            {
                mainMenu = MainMenu.FromXmlFile(menuFile);
            }


            // Todo: sostituire i placeholders
            //mainMenu.ReplacePlaceholders(_aliasResolver.AliasComune, this._aliasResolver.Software, );

            return mainMenu;
        }

        private MainMenu UpgradeFromLegacy(byte[] menuFile)
        {
            var legacyMenu = MenuNavigazioneFlyweight.LoadFrom(menuFile);
            var newMenu = new MainMenu()
            {
                Descrizione = legacyMenu.DescrizionePagina
            };

            newMenu.MenuUtente = legacyMenu.GetMenuUtente()
                                        .Select(x => new MenuItem
                                        {
                                            Titolo = x.Descrizione,
                                            Descrizione = x.GetDescrizioneEstesa2(),
                                            IconaBootstrap = x.GlyphIcon,
                                            MostraInHomePage = true,
                                             Target = x.Target,
                                            Url = UpgradeMenuUrl(x.Url )
                                        }).ToList();

            newMenu.Sezioni.Add(new SezioneMenu
                {
                    Titolo = "Scrivania virtuale"
                });

            newMenu.Sezioni[0].Items = legacyMenu.VociMenu
                                                .Where(x => !x.IsVoceMenuUtente)
                                                .Select(x => new MenuItem
                                                {
                                                    Titolo = x.Descrizione,
                                                    Descrizione = x.GetDescrizioneEstesa2(),
                                                    IconaBootstrap = x.GlyphIcon,
                                                    MostraInHomePage = true,
                                                    Target = x.Target,
                                                    Url = UpgradeMenuUrl(x.Url)
                                                }).ToList();

            //File.WriteAllText(@"c:\temp\menu-upgraded.xml", newMenu.ToString());

            return newMenu;
        }

        private string UpgradeMenuUrl(string oldUrl)
        {
            // Elimino le parti di url che fanno riferimento al token
            // TODO...
            return oldUrl;
        }

        private int ExtractVersion(byte[] menuFile)
        {
            var selector = "//MainMenu/Versione";

            using (var ms = new MemoryStream(menuFile))
            {
                var xmlDoc = new XmlDocument();
                xmlDoc.Load(ms);

                var node = xmlDoc.SelectSingleNode(selector);

                if (node == null || String.IsNullOrEmpty(node.InnerText))
                {
                    return 1;
                }

                return Convert.ToInt32(node.InnerText);
            }
        }
    }
}
