namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneDatiDinamici
{
    public class SchedeEndoBindingItem
    {
        public string Descrizione { get; set; } = "";
        public IEnumerable<SchedeBindingItem> Schede { get; set; } = Enumerable.Empty<SchedeBindingItem>();
    }
}
