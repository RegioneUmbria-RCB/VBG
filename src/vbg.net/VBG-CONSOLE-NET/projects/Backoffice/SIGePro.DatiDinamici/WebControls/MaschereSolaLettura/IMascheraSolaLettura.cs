// -----------------------------------------------------------------------
// <copyright file="IMascheraSolaLettura.cs" company="">
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
	public interface IMascheraSolaLettura
	{
		bool ContieneCampo(CampoDinamicoBase campo);
		bool ContieneAlmenoUnCampo(IEnumerable<CampoDinamicoBase> listaCampi);
	}
}
