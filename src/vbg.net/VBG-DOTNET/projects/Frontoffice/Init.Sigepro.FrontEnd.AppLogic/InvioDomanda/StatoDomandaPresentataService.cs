using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda
{
    public class StatoDomandaPresentataService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;

        public StatoDomandaPresentataService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
        }

        public void MarcaDomandaComePresentata(int idPresentazione)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idPresentazione);

            domanda.ImpostaComePresentata();

            this._salvataggioDomandaStrategy.Salva(domanda);
        }
    }
}
