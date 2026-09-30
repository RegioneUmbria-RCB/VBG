using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.ComponentModel;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
	public partial class ReadWriteControlsFactory : IDatiDinamiciControlsFactory
	{
		#region IDatiDinamiciControlsFactory Members

		public IDatiDinamiciControl CreaControllo( CampoDinamicoBase campo )
		{
			var tipoControllo = ControlliDatiDinamiciDictionary.Items[campo.TipoCampo].TipoRuntime;

			return (IDatiDinamiciControl)Activator.CreateInstance(tipoControllo, campo);
		}

		#endregion
	}
}
