// -----------------------------------------------------------------------
// <copyright file="ParametriRicercaLocalizzazione.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ParametriRicercaLocalizzazione : IParametriRicercaLocalizzazione
	{
		public string CodViario
		{
			get;
			set;
		}

		public string Civico
		{
			get;
			set;
		}

		public string Esponente
		{
			get;
			set;
		}

		public string TipoCatasto
		{
			get;
			set;
		}

		public string Sezione
		{
			get;
			set;
		}

		public string Foglio
		{
			get;
			set;
		}

		public string Particella
		{
			get;
			set;
		}

		public string Sub
		{
			get;
			set;
		}

        public string AccessoTipo { get; set; }

        public string AccessoNumero { get; set; }

        public string AccessoDescrizione { get; set; }

        public SigeproSitWebService.Sit ToSit()
		{
			return new SigeproSitWebService.Sit
			{
				CodVia = CodViario,
				Civico = this.Civico,
				Esponente = this.Esponente,
				TipoCatasto = this.TipoCatasto,
				Sezione = this.Sezione,
				Foglio = this.Foglio,
				Particella = this.Particella,
				Sub = this.Sub,
                AccessoTipo = this.AccessoTipo,
                AccessoNumero = this.AccessoNumero,
                AccessoDescrizione = this.AccessoDescrizione
			};
		}
	}
}
