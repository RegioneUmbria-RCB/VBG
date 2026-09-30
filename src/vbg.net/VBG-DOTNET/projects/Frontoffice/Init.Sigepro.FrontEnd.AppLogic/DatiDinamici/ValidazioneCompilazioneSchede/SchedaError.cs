namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.ValidazioneCompilazioneSchede
{
    public class SchedaError
    {
        private readonly int _idModello;
        private readonly string _messaggio;
        public SchedaError(int idModello, string messaggio)
        {
            this._idModello = idModello;
            this._messaggio = messaggio;
        }
    }
}