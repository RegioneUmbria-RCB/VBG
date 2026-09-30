using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V1;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions.V2
{
    public class V1ToV2MenuUpgrader : IMenuVersionUpgrader
    {
        public MenuFile Upgrade(MenuFile oldFile)
        {
            var legacyMenu = MenuNavigazioneFlyweight.LoadFrom(oldFile.BinaryContent);
            var newMenu = new MainMenuV2()
            {
                Descrizione = legacyMenu.DescrizionePagina
            };

            newMenu.MenuUtente = legacyMenu.GetMenuUtente()
                                        .Select(x => new MenuItemV2
                                        {
                                            Titolo = x.Descrizione,
                                            Descrizione = x.GetDescrizioneEstesa2(),
                                            IconaBootstrap = x.GlyphIcon,
                                            MostraInHomePage = true,
                                            Target = x.Target,
                                            Url = this.UpgradeMenuUrl(x.Url)
                                        }).ToList();

            newMenu.Sezioni.Add(new SezioneMenuV2
            {
                Titolo = "Scrivania virtuale"
            });

            newMenu.Sezioni[0].Items = legacyMenu.VociMenu
                                                .Where(x => !x.IsVoceMenuUtente)
                                                .Select(x => new MenuItemV2
                                                {
                                                    Titolo = x.Descrizione,
                                                    Descrizione = x.GetDescrizioneEstesa2(),
                                                    IconaBootstrap = x.GlyphIcon,
                                                    MostraInHomePage = true,
                                                    Target = x.Target,
                                                    Url = this.UpgradeMenuUrl(x.Url)
                                                }).ToList();

            return newMenu.ToMenuFile();

        }

        private string UpgradeMenuUrl(string oldUrl)
        {
            // Elimino le parti di url che fanno riferimento al token
            // TODO...
            return oldUrl;
        }
    }
}
