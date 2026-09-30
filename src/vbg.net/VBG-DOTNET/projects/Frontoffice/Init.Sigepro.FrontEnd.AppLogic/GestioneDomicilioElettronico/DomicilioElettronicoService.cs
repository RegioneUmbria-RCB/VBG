using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneDomicilioElettronico
{
    public class DomicilioElettronicoService : IDomicilioElettronicoService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(DomicilioElettronicoService));
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;

        public DomicilioElettronicoService(ISalvataggioDomandaStrategy salvataggioStrategy)
        {
            if (salvataggioStrategy == null)
                throw new System.ArgumentNullException(nameof(salvataggioStrategy));
            //Condition.Requires(salvataggioStrategy, "salvataggioStrategy").IsNotNull();

            this._salvataggioStrategy = salvataggioStrategy;
        }

        public void ImpostaDomicilioElettronico(int idPresentazione, string indirizzo)
        {
            var domanda = this._salvataggioStrategy.GetById(idPresentazione);

            domanda.WriteInterface.AltriDati.ImpostaDomicilioElettronico(indirizzo);

            this._salvataggioStrategy.Salva(domanda);
        }
    }
}
