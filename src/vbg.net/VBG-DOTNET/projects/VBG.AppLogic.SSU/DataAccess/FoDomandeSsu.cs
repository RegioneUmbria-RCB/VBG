namespace VBG.AppLogic.SSU.DataAccess
{
    public class FoDomandeSsu
    {
        public required string IdComune { get; set; }
        public required int FkIdDomanda { get; set; }
        public required string IdDomandaSsu { get; set; }
        public required string NumeroDomandaSsu { get; set; }
        public required DateTime DataInvio { get; set; }
        public required int Stato { get; set; }
        public required int? CodiceoggettoRicevuta { get; set; }
        public required bool NonElaborabile { get; set; }
        public required string Alias { get; set; }
        public required string IstatEnte { get; set; }

        public StatiDomandaSsuEnum StatoAsEnum => (StatiDomandaSsuEnum)this.Stato;
    }
}
