namespace IntegrazioneCUnicoWS
{
    public class CUnicoConfigurazione
    {

        private static class Constants
        {
            public const string BindingName = "defaultHttpBinding"; 
        }

        public string CodBel { get; set; }

        public string CodUte { get; set; }

        public string Url { get; set; }
        public string BindingName { get; set; } = Constants.BindingName;

        public CUnicoConfigurazione()
        {

        }

        public CUnicoConfigurazione(string url, string codBel, string codUte)
        {
            this.Url = url;
            this.CodBel = codBel;
            this.CodUte = codUte;
        }
    }
}