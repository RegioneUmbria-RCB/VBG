// -----------------------------------------------------------------------
// <copyright file="MascheraSolaLetturaCompleta.cs" company="">
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
	public class MascheraSolaLetturaCompleta : IMascheraSolaLettura
	{
		#region IMascheraSolaLettura Members

		public bool ContieneCampo(CampoDinamicoBase campo)
		{
			return true;
		}

		public bool ContieneAlmenoUnCampo(IEnumerable<CampoDinamicoBase> listaCampi)
		{
			return true;
		}

		#endregion
	}
}
