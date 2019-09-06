using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
    public partial class ReadOnlyControlsFactory : IDatiDinamiciControlsFactory
    {
        public ReadOnlyControlsFactory()
        {
        }

        #region IDatiDinamiciControlsFactory Members

        public IDatiDinamiciControl CreaControllo(CampoDinamicoBase campo)
        {
            if (campo is CampoDinamicoTestuale)
            {
                return new ReadWriteControlsFactory().CreaControllo(campo);
            }

            var ctrl = CreaControlloDaCampo(campo);
            

            if (campo.ListaValori.Count == 0)
            {
                campo.ListaValori.IncrementaMolteplicita();
            }

            return ctrl;
        }

        private IDatiDinamiciControl CreaControlloDaCampo(CampoDinamicoBase campo)
        {
            Debug.WriteLine(campo.TipoCampo.ToString());

            if (campo.TipoCampo == TipoControlloEnum.Checkbox)
            {
                return new DatiDinamiciCheckBoxReadOnly(campo);
            }

            if (campo.TipoCampo == TipoControlloEnum.CampoNascosto)
            {
                return new DatiDinamiciHidden(campo);
            }

            return new DatiDinamiciLabel(campo);
        }

        #endregion
    }
}
