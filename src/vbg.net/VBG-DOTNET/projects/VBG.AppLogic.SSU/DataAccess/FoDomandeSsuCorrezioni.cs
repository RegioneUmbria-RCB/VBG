namespace VBG.AppLogic.SSU.DataAccess
{
    public class FoDomandeSsuCorrezioni
    {
        public string IdComune { get; set; } = string.Empty;
        public int Id { get; set; }
        public int FkIdDomanda { get; set; }
        public int ProcedimentoId { get; set; }
        public string CorrezioneRichiesta { get; set; } = string.Empty;
        public DateTime CreataIl { get; set; }
        public DateTime? CorrettaIl { get; set; }
    }
}
