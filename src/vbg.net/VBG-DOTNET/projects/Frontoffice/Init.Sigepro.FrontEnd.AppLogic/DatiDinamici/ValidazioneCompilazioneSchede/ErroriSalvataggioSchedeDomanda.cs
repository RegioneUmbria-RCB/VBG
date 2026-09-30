using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.ValidazioneCompilazioneSchede
{
    public class ErroriSalvataggioSchedeDomanda
    {
        private readonly List<SchedaError> _errori = new List<SchedaError>();

        internal void AddError(SchedaError error)
        {
            this._errori.Add(error);
        }
    }
}