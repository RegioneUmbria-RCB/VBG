namespace AreaRiservataCore.Shared.Localizzazioni
{
    public class AutocompleteStradarioResult
    {
        public int ItemCount { get; internal set; }
        public AutocompleteStradarioResultItem[] Items { get; internal set; } = Array.Empty<AutocompleteStradarioResultItem>();
    }

    public class AutocompleteStradarioResultItem
    {
        public int Codice { get; set; }
        public string Descrizione { get; set; } = "";
        public string CodViario { get; set; } = "";
    }
}
