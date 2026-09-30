using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class InterventoAttivoSuComune : InterventoPubblicato
    {
        internal InterventoAttivoSuComune(IEnumerable<IIntervento> alberaturaInterventi, DataBase db, string idComune, string codiceComune)
            : base(
                  new InterventiReverseEnumerator<IIntervento>(alberaturaInterventi),
                  new VerificaAttivazionePerComune(db, idComune, codiceComune))
        {
        }

        protected override bool GetValoreDefault() => true;

        public override string GetMessaggioErrore() => "L'intervento selezionato non è attivabile tramite domanda online per il comune selezionato";

    }
}