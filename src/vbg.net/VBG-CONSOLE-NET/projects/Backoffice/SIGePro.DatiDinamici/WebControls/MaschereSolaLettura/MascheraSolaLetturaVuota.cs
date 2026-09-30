// -----------------------------------------------------------------------
// <copyright file="NullMascheraSolaLettura.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class MascheraSolaLetturaVuota : IMascheraSolaLettura
	{
		#region IMascheraSolaLettura Members

		public bool ContieneCampo(CampoDinamicoBase campo)
		{
			return false;
		}

		public bool ContieneAlmenoUnCampo(IEnumerable<CampoDinamicoBase> listaCampi)
		{
			return false;
		}

		#endregion
	}
}
