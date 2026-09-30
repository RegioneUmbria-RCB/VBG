namespace VBG.AppLogic.SSU.DataAccess.Dto
{
    public class ElementoListaDomandeSsuDto
    {
        public required string IdComune { get; set; }
        public required int Id { get; set; }
        public required string NumeroDomandaSsu { get; set; }
        public required string IdentificativoDomanda { get; set; } = "";
        public required DateTime DataInvio { get; set; }
        public required int Stato { get; set; }
        public required string Alias { get; set; }

        public StatiDomandaSsuEnum StatoAsEnum => (StatiDomandaSsuEnum)this.Stato;


        public required int? CodiceOggetto { get; set; }
        public required System.DateTime? DataUltimaModifica { get; set; }
        public required string Richiedente { get; set; } = "";
        public required string Intervento { get; set; } = "";
        public required int? CodiceIntervento { get; set; }
        public required string Oggetto { get; set; } = "";
        public required string CodiceEnte { get; set; } = "";
    }
}
