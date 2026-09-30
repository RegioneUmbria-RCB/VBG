
namespace Init.SIGePro.Manager.Authentication.Core
{
    public class ConfigurazioneSigeproSecurityOptions
    {
        public static string SectionName => "ConfigurazioneSigeproSecurity";

        public string LoginServiceUrl { get; set; } = "";
        public string Username { get; set; } = "";
        public string Password { get; set; } = "";
    }
}
