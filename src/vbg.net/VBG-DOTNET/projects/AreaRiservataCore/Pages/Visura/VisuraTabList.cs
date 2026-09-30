namespace AreaRiservataCore.Pages.Visura
{
    public class VisuraTabList
    {
        public List<VisuraTabListItem> Tabs { get; private set; }

        public VisuraTabList(List<VisuraTabListItem> tabs)
        {
            this.Tabs = tabs;
        }

        public void SetBadgeValue(string TabName, int Value)
        {
            var tab = this.Tabs.Where(x => x.Id == TabName).FirstOrDefault();

            if (tab != null)
            {
                tab.Badge = Value;
            }
        }

        public static VisuraTabList Default => new(new List<VisuraTabListItem> {
            new VisuraTabListItem{ Descrizione= "Dati generali", Id=VisuraTabListNames.DatiGenerali, VisibileDaArchivio=true, IsActive=true },
            new VisuraTabListItem{ Descrizione= "Localizzazioni", Id=VisuraTabListNames.Localizzazioni, VisibileDaArchivio=true, HasBadge = true },
            new VisuraTabListItem{ Descrizione= "Schede", Id=VisuraTabListNames.Schede, VisibileDaArchivio=false, HasBadge = true },
            new VisuraTabListItem{ Descrizione= "Documenti", Id=VisuraTabListNames.Documenti, VisibileDaArchivio=false, HasBadge = true },
            new VisuraTabListItem{ Descrizione= "Endoprocedimenti", Id=VisuraTabListNames.Endoprocedimenti,  VisibileDaArchivio=false, HasBadge = true },
            new VisuraTabListItem{ Descrizione= "Oneri", Id=VisuraTabListNames.Oneri, VisibileDaArchivio=false, HasBadge = true },
            //new TabsListItem{ Descrizione= "Movimenti", Id="movimenti", VisibileDaArchivio=false },
            new VisuraTabListItem{ Descrizione= "Autorizzazioni", Id=VisuraTabListNames.Autorizzazioni, VisibileDaArchivio=true, HasBadge = true },
            new VisuraTabListItem{ Descrizione= "Scadenze", Id=VisuraTabListNames.Scadenze, VisibileDaArchivio=false, HasBadge = true }
        });
    }
}
