using System.Collections.Generic;

namespace Init.SIGePro.Sit.ValidazioneFormale
{
    internal class CompositeValidazioneFormaleService : IValidazioneFormaleService
    {
        private readonly IEnumerable<IValidazioneFormaleService> _serviziValidazione;

        public CompositeValidazioneFormaleService(IEnumerable<IValidazioneFormaleService> serviziValidazione)
        {

            this._serviziValidazione = serviziValidazione ?? throw new System.ArgumentNullException(nameof(serviziValidazione));
        }

        #region IValidazioneFormaleService Members

        public bool Valida(Init.SIGePro.Sit.Data.Sit sit)
        {
            foreach (var servizioValidazione in this._serviziValidazione)
            {
                if (servizioValidazione.Valida(sit))
                    return true;
            }

            return false;
        }

        #endregion
    }
}
