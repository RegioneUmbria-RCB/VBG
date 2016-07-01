// -----------------------------------------------------------------------
// <copyright file="Dyn2TestoModello.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.DTO.DatiDinamici
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.SIGePro.DatiDinamici.Interfaces;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class Dyn2TestoModello : IDyn2TestoModello
	{
		public string Idcomune
		{
			get;
			set;
		}

		public int? Id
		{
			get;
			set;
		}

		public int? IdNelModello
		{
			get;
			set;
		}

		public string IdTipoTesto
		{
			get;
			set;
		}

		public string Testo
		{
			get;
			set;
		}

		public Dyn2TestoModello(string idComune, int? id, int? idNelModello, string idTipoTesto, string testo)
		{
			this.Idcomune = idComune;
			this.Id = id;
			this.IdNelModello = idNelModello;
			this.IdTipoTesto = idTipoTesto;
			this.Testo = testo;
		}

		public Dyn2TestoModello()
		{

		}
	}
}
