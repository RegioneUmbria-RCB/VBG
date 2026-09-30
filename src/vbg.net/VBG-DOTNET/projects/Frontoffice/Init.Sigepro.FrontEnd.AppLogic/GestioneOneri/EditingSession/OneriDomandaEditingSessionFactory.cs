using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession
{
    public class OneriDomandaEditingSessionFactory
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public OneriDomandaEditingSessionFactory(ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public IOneriDomandaEditingSession StartEditingSession(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return new OneriDomandaEditingSession(domanda, this);
        }

        internal void TerminaSessioneModifica(OneriDomandaEditingSession editingSession)
        {
            this._salvataggioDomandaStrategy.Salva(editingSession.Domanda);
        }
    }
}
