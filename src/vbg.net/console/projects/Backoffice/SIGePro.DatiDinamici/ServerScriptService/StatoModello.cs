// -----------------------------------------------------------------------
// <copyright file="StatoModello.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici.ServerScriptService
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class StatoModello
	{
		public CampoModificato[] modifiche { get; set; }
		public ErroreCampo[] errori { get; set; }
		public UidCampo[] campiVisibili { get; set; }
		public UidCampo[] campiNonVisibili { get; set; }
		public int[] gruppiVisibili { get; set; }
		public int[] gruppiNonVisibili { get; set; }
		public string[] messaggiDebug { get; set; }

		internal StatoModello()
		{

		}

		
	}
}