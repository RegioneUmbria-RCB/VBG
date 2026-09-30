namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class InterventoPubblicato : IInterventoPubblicato
    {
        private readonly IInterventiEnumerator<IIntervento> _enumerator;
        private readonly IVerificaaAlbero _analizzaAlbero;

        protected InterventoPubblicato(IInterventiEnumerator<IIntervento> enumerator, IVerificaaAlbero analizzaAlbero)
        {
            this._enumerator = enumerator;
            this._analizzaAlbero = analizzaAlbero;
        }

        public bool IsTrue()
        {
            while (this._enumerator.MoveNext())
            {
                var item = this._enumerator.Current;

                if (this._analizzaAlbero.PuoAnalizzare(item))
                    return this._analizzaAlbero.GetRisultato(item);
            }

            return this.GetValoreDefault();
        }

        protected virtual bool GetValoreDefault()
        {
            return false;
        }

        public virtual string GetMessaggioErrore() => "L'intervento selezionato non è attivabile tramite domanda online";

    }
}
