using System;
namespace VBG.Backend.SIT.AppLogic.ValidazioneFormale
{
    internal class ValidazioneFormaleTramiteCodiceCivicoService : IValidazioneFormaleService
    {
        #region IValidazioneFormaleService Members

        public bool Valida(VBG.Backend.SIT.AppLogic.Data.Sit sit)
        {
            return !String.IsNullOrEmpty(sit.CodCivico);
        }

        #endregion
    }
}
