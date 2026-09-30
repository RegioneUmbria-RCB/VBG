namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.ValidazioneCompilazioneSchede
{

    public class SchedaNonCompilataError : SchedaError
    {
        private readonly int _idModello;

        public SchedaNonCompilataError(int idModello) : base(idModello, "Per proseguire è necessario compilare questa scheda")
        {
            this._idModello = idModello;
        }
    }
}