using AreaRiservataCore.Pages.InserimentoIstanza.Allegati;
using AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero;

namespace AreaRiservataCore.Pages.InserimentoIstanza.AllegatiIntervento
{
    public class AllegatiInterventoBindingItem : CategoriaBindingItem
    {
        public List<GrigliaAllegatiBindingItem> Allegati { get; set; }

        public AllegatiInterventoBindingItem()
        {
            this.Allegati = new List<GrigliaAllegatiBindingItem>();
        }
    }
}
