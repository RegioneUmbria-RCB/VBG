namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore
{
    public class Step
    {
        public string NomeStep { get; set; } = "";
        public string DescrizioneStep { get; set; } = "";
        public int IndiceStep => this.IndiceStepZeroBased + 1;
        public int IndiceStepZeroBased { get; set; }
        public bool IsEnabled { get; set; }
        public bool IsCurrent { get; set; }
        public bool IsLast { get; set; }
        public bool IsFirst { get; set; }
    }
}
