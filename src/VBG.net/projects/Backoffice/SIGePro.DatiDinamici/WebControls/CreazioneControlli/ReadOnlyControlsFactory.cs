using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
	public partial class ReadOnlyControlsFactory : IDatiDinamiciControlsFactory
	{
		static readonly object _locker = new object();

		public ReadOnlyControlsFactory()
		{
			//lock (_locker)
			//{
			//    if (_dictionaryGenerazioneControlli == null)
			//    {
			//        _dictionaryGenerazioneControlli = new Dictionary<TipoControlloEnum, Func<CampoDinamicoBase, IDatiDinamiciControl>>();
			//        _dictionaryGenerazioneControlli.Add(TipoControlloEnum.Checkbox, x => new DatiDinamiciCheckBoxReadOnly(x));
			//        _dictionaryGenerazioneControlli.Add(TipoControlloEnum.Checkbox, x => new DatiDinamiciCheckBoxReadOnly(x));
			//        _dictionaryGenerazioneControlli.Add(TipoControlloEnum.Checkbox, x => new DatiDinamiciCheckBoxReadOnly(x));
			//    }
			//}
		}

		#region IDatiDinamiciControlsFactory Members

		public IDatiDinamiciControl CreaControllo( CampoDinamicoBase campo)
		{
			IDatiDinamiciControl ctrl = null;

			if (campo is CampoDinamicoTestuale)
			{
				ctrl = new ReadWriteControlsFactory().CreaControllo( campo );
			}
			else
			{
				if (campo.TipoCampo == TipoControlloEnum.Checkbox)
				{
					ctrl = new DatiDinamiciCheckBoxReadOnly(campo);
				}
				else
				{
					ctrl = new DatiDinamiciLabel(campo);
				}


				if (campo.ListaValori.Count == 0)
				{
					campo.ListaValori.IncrementaMolteplicita();

				}
			}
			return ctrl;
		}

		#endregion
	}
}
