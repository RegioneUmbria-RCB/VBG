namespace Vbg.CoreControls.EditFormControls
{
    public class VbgInputFileUploadedEventArgs
    {
        public int CodiceOggetto { get; }
        public string NomeFile { get; }

        internal VbgInputFileUploadedEventArgs(int codiceOggetto, string nomeFile)
        {
            this.CodiceOggetto = codiceOggetto;
            this.NomeFile = nomeFile;
        }
    }
}
