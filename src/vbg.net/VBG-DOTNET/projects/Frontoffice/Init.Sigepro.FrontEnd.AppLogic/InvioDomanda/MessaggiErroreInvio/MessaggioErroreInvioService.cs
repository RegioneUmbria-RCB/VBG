// -----------------------------------------------------------------------
// <copyright file="MessaggioErroreInvioService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio
{
    using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;

    public interface IMessaggioErroreInvioService
    {
        string GeneraMessaggioErrore(int idDomanda);
    }


    public class MessaggioErroreInvioService : IMessaggioErroreInvioService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly MessaggioInvioFallito _messaggioInvioFallito;

        public MessaggioErroreInvioService(ISalvataggioDomandaStrategy salvataggioDomandaStrategy, MessaggioInvioFallito messaggioInvioFallito)
        {
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._messaggioInvioFallito = messaggioInvioFallito;
        }

        public string GeneraMessaggioErrore(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return this._messaggioInvioFallito.Get(domanda.DataKey.ToSerializationCode());
        }
    }
}
