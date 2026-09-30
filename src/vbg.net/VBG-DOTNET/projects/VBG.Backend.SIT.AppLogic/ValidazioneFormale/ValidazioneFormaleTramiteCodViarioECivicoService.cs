using System;
namespace VBG.Backend.SIT.AppLogic.ValidazioneFormale
{
    internal class ValidazioneFormaleTramiteCodViarioECivicoService : IValidazioneFormaleService
    {
        #region IValidazioneFormaleService Members

        public bool Valida(VBG.Backend.SIT.AppLogic.Data.Sit sit)
        {
            return !String.IsNullOrEmpty(sit.CodVia) && !String.IsNullOrEmpty(sit.Civico);
        }

        #endregion
    }
}
