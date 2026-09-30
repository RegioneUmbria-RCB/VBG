namespace VBG.Backend.SIT.AppLogic.ValidazioneFormale
{
    internal class NullValidazioneFormaleService : IValidazioneFormaleService
    {
        #region IValidazioneFormaleService Members

        public bool Valida(VBG.Backend.SIT.AppLogic.Data.Sit sit)
        {
            return true;
        }

        #endregion
    }
}
