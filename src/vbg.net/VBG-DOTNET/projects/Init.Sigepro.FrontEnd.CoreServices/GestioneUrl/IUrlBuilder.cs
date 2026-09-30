namespace Init.Sigepro.FrontEnd.CoreServices.GestioneUrl
{
    public interface IUrlBuilder
    {
        public string Build(params string[] parts);
        public string Build(params object[] parts);
    }
}
