using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public class VisuraTabList
    {
        public List<VisuraTabListItem> Tabs { get; private set; }

        public VisuraTabList(List<VisuraTabListItem> tabs)
        {
            Tabs = tabs;
        }

        public void SetBadgeValue(string tabName, string value)
        {
            var tab = Tabs.Where(x => x.Id == tabName).FirstOrDefault();

            if (tab != null)
            {
                tab.ValoreBadge = value;
            }
        }

        internal void RimuoviTab(string idDaRimuovere)
        {
            Tabs.RemoveAll(x => x.Id == idDaRimuovere);
        }

        public static VisuraTabList Default => new VisuraTabList(new List<VisuraTabListItem> {
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