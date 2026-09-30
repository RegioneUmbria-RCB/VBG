namespace AreaRiservataCore.Pages.Admin.Components
{
    public class FiltroIstanzeBloccate
    {
        public DateTime? startDate { get; set; }
        public DateTime? endDate { get; set; }
        public string? idComune { get; set; }

        public bool FiltroPerDataImpostato()
        {
            return startDate != null || endDate != null;
        }

        public bool FiltroPerComuneImpostato()
        {
            return !string.IsNullOrEmpty(idComune);
        }
    }
}
