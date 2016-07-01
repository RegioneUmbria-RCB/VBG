// -----------------------------------------------------------------------
// <copyright file="ProcedimentoTypeExtensions.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.StcService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public partial class ProcedimentoType
	{
		public bool ContieneAllegati
		{
			get { return this.documenti != null && this.documenti.Length > 0; }
		}
	}
}
