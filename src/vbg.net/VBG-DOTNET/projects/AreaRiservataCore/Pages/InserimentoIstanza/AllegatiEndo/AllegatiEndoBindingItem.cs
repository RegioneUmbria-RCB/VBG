using AreaRiservataCore.Pages.InserimentoIstanza.Allegati;
using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;

namespace AreaRiservataCore.Pages.InserimentoIstanza.AllegatiEndo
{
    public class AllegatiEndoBindingItem : CategoriaBindingItem
    {

        public List<GrigliaAllegatiBindingItem> Allegati { get; set; }

        public AllegatiEndoBindingItem()
        {
            this.Allegati = new List<GrigliaAllegatiBindingItem>();
        }
    }
}

