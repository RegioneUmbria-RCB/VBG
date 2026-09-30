using System.Collections.Generic;

namespace VBG.Backend.SIT.AppLogic.ValidazioneFormale
{
    internal class CompositeValidazioneFormaleService : IValidazioneFormaleService
    {
        private readonly IEnumerable<IValidazioneFormaleService> _serviziValidazione;

        public CompositeValidazioneFormaleService(IEnumerable<IValidazioneFormaleService> serviziValidazione)
        {

            this._serviziValidazione = serviziValidazione ?? throw new System.ArgumentNullException(nameof(serviziValidazione));
        }

        #region IValidazioneFormaleService Members

        public bool Valida(VBG.Backend.SIT.AppLogic.Data.Sit sit)
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
