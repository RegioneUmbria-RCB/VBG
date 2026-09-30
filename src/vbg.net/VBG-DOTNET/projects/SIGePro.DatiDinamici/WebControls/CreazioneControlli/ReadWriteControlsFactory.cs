using System;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
    public partial class ReadWriteControlsFactory : IDatiDinamiciControlsFactory
    {
        private readonly bool _verticalizzazioneEagleAttiva = false;

        public ReadWriteControlsFactory(bool verticalizzazioneEagleAttiva)
        {
            this._verticalizzazioneEagleAttiva = verticalizzazioneEagleAttiva;
        }

        #region IDatiDinamiciControlsFactory Members

        public IDatiDinamiciControl CreaControllo(CampoDinamicoBase campo)
        {
            var dictionary = ControlliDatiDinamiciDictionary.GetCampiSupportati(this._verticalizzazioneEagleAttiva);

            if (dictionary.TryGetValue(campo.TipoCampo, out var tipoControllo))
            {
                return (IDatiDinamiciControl)Activator.CreateInstance(tipoControllo.TipoRuntime, campo);
            }

            return null;
        }

        #endregion
    }
}
