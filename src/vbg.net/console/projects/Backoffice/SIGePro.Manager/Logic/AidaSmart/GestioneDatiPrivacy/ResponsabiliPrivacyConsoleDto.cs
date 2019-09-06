using Newtonsoft.Json;

namespace Init.SIGePro.Manager.Logic.AidaSmart.GestioneDatiPrivacy
{
    public class ResponsabiliPrivacyConsoleDto
    {
        [JsonProperty(PropertyName = "denominazioneComune")]
        public string DenominazioneComune { get; set; }

        [JsonProperty(PropertyName = "responsabileTrattamento")]
        public string ResponsabileTrattamento { get; set; }

        [JsonProperty(PropertyName = "dataProtectionOfficer")]
        public string DataProtectionOfficer { get; set; }
    }

    public class ResponsabiliPrivacyConsoleDtoWrapper
    {
        public ResponsabiliPrivacyConsoleDto items { get; set; }
    }
}