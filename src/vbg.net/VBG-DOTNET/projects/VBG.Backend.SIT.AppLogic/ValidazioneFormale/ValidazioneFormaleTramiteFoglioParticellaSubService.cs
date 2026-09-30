using System;
namespace VBG.Backend.SIT.AppLogic.ValidazioneFormale
{
    internal class ValidazioneFormaleTramiteFoglioParticellaSubService : IValidazioneFormaleService
    {
        #region IValidazioneFormaleService Members

        public bool Valida(VBG.Backend.SIT.AppLogic.Data.Sit sit)
        {
            return !String.IsNullOrEmpty(sit.Foglio) &&
                    !String.IsNullOrEmpty(sit.Particella) &&
                    !String.IsNullOrEmpty(sit.Sub);
        }

        #endregion
    }
}
