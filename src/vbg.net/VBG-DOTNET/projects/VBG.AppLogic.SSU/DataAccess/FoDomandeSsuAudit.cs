
namespace VBG.AppLogic.SSU.DataAccess
{
    public class FoDomandeSsuAudit
    {
        public required string IdComune { get; set; }
        public required int FkIdDomanda { get; set; }
        public required DateTime Data { get; set; }
        public required int Stato { get; set; }
        public required string Errore { get; set; }

        public StatiDomandaSsuEnum StatoAsEnum => (StatiDomandaSsuEnum)this.Stato;
    }
}
