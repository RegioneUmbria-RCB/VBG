namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.ValidazioneCompilazioneSchede
{
    public class SchedaConErroriError : SchedaError
    {
        public SchedaConErroriError(int idModello) : base(idModello, "La scheda contiene errori, verificare la compilazione")
        {
        }
    }
}