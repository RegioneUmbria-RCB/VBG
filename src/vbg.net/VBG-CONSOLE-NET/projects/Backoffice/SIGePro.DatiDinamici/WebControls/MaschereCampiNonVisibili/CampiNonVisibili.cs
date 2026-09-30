// -----------------------------------------------------------------------
// <copyright file="CampiNonVisibili.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.DatiDinamici.VisibilitaCampi;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CampiNonVisibili : ICampiNonVisibili
	{
		public class NullCampiNonVisibili : ICampiNonVisibili
		{
			public bool ValoreVisibile(int idCampo, int indiceCampo)
			{
				return true;
			}
		}

		private static ICampiNonVisibili _staticTuttiCampiVisibili;

		public static ICampiNonVisibili TuttiICampiVisibili
		{
			get
			{
				if (_staticTuttiCampiVisibili == null)
					_staticTuttiCampiVisibili = new NullCampiNonVisibili();

				return _staticTuttiCampiVisibili;
			}
		}

		IEnumerable<IdValoreCampo> _campiNonVisibili;

		public CampiNonVisibili(IEnumerable<IdValoreCampo> campiNonVisibili)
		{
			this._campiNonVisibili = campiNonVisibili;
		}

		public bool ValoreVisibile(int idCampo, int indice)
		{
			return this._campiNonVisibili.Count(x => x.Id == idCampo && x.IndiceMolteplicita == indice) == 0;
		}
	}
}
