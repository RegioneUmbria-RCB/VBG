// -----------------------------------------------------------------------
// <copyright file="MascheraSolaLetturaDaId.cs" company="">
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
	public class MascheraSolaLetturaDaId : IMascheraSolaLettura
	{
		IEnumerable<int> _listaIdInSolaLettura;

		public MascheraSolaLetturaDaId(IEnumerable<int> listaIdInSolaLettura)
		{
			this._listaIdInSolaLettura = listaIdInSolaLettura;
		}

		#region IMascheraSolaLettura Members

		public bool ContieneCampo(CampoDinamicoBase campo)
		{
			return this._listaIdInSolaLettura.Contains(campo.Id);
		}

		public bool ContieneAlmenoUnCampo(IEnumerable<CampoDinamicoBase> listaCampi)
		{
			foreach (var campo in listaCampi)
				if (ContieneCampo(campo))
					return true;

			return false;
		}

		#endregion
	}
}
