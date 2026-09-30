namespace Vbg.CoreControls.EditFormControls
{
    public class VbgInputFileDeletedEventArgs : EventArgs
    {
        public int CodiceOggetto { get; }

        public VbgInputFileDeletedEventArgs(int codiceOggetto)
        {
            this.CodiceOggetto = codiceOggetto;
        }
    }
}
