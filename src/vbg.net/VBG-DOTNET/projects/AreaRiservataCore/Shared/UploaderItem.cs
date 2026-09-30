namespace VBG.AreaRiservataCore.Controls
{
    public class UploaderItem
    {
        public int? Id { get; set; }
        public string NomeFile { get; set; } = string.Empty;
        public int? CodiceOggetto { get; set; }
        public bool RichiedeFirma { get; set; }
    }
}
