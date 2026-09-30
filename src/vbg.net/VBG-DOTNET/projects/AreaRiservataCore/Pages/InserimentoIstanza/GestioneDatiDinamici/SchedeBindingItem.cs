namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneDatiDinamici
{
    public class SchedeBindingItem
    {
        public int Codice { get; set; }
        public string Descrizione { get; set; } = "";
        public bool Facoltativa { get; set; } = false;
        public bool Compilata { get; set; } = false;
        public bool DaRicompilare { get; set; } = false;
    }
}
