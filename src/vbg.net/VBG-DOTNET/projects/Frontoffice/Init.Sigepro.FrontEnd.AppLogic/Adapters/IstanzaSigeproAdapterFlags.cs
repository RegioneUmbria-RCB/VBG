namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
{
    public class IstanzaSigeproAdapterFlags
    {
        public static readonly IstanzaSigeproAdapterFlags Default = new IstanzaSigeproAdapterFlags();

        public bool AggiungiPdfSchedeAListaAllegati { get; internal set; } = true;
        public bool CalcolaMD5Oggetti { get; internal set; } = true;
        public bool RecuperaMetadatiToken { get; internal set; } = false;
    }
}
