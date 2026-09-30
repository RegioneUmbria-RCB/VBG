using Init.SIGePro.DatiDinamici.WebControls.RenderersRigheModelloDinamico;
using System;
using System.Diagnostics;
using System.Linq;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Interfaces.WebControls;
using VBG.DatiDinamici.Web;

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
                return new ReadWriteControlsFactory(false).CreaControllo(campo);
            }

            var ctrl = this.CreaControlloDaCampo(campo);


            if (campo.ListaValori.Count == 0)
            {
                campo.ListaValori.IncrementaMolteplicita();
            }

            return ctrl;
        }

        private IDatiDinamiciControl CreaControlloDaCampo(CampoDinamicoBase campo)
        {
            Debug.WriteLine(campo.TipoCampo.ToString());

            switch (campo.TipoCampo)
            {
                case TipoControlloEnum.Checkbox:
                    return new DatiDinamiciCheckBoxReadOnly(campo);

                case TipoControlloEnum.CampoNascosto:
                case TipoControlloEnum.Bottone:
                case TipoControlloEnum.LocalizzazioneEagle:
                    return new DatiDinamiciReadOnlyHidden(campo);

                case TipoControlloEnum.Lista:
                    {
                        if (AccumulatoreNoteModello.GetContextInstance() != null)
                        {
                            if (campo.ProprietaControlloWeb.Where(x => x.Key == DatiDinamiciListBox.Constants.NascondiValoriSuRiepilogo && string.Equals(x.Value, "TRUE", StringComparison.OrdinalIgnoreCase)).Any())
                            {
                                return new DatiDinamiciLabel(campo);
                            }

                            var listBoxControl = new DatiDinamiciReadOnlyListBox(campo);

                            listBoxControl.IdRiferimentoNote = campo.IdRiferimentoNote;

                            return listBoxControl;
                        }

                        return new DatiDinamiciLabel(campo);
                    }

                default:
                    return new DatiDinamiciLabel(campo);
            }
        }

        #endregion
    }
}
