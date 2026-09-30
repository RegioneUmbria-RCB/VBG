using Init.Sigepro.FrontEnd.AppLogic.Common;

namespace Init.Sigepro.FrontEnd.CoreServices.Autenticazione
{
    public class AliasSoftwareProvider : IAliasSoftwareResolver
    {
        public string Software { get; set; }

        public string AliasComune { get; set; }
    }
}
