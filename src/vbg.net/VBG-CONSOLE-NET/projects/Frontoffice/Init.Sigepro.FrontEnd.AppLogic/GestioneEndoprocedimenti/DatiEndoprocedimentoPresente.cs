// -----------------------------------------------------------------------
// <copyright file="DatiEndoprocedimentoPresente.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiEndoprocedimentoPresente
	{
		public readonly bool Presente;
		public readonly int Codice;
		public readonly string Descrizione;
		public readonly string CodiceTipoTitolo;
		public readonly string DescrizioneTipoTitolo;
		public readonly string NumeroAtto;
		public readonly string DataAtto;
		public readonly string RilasciatoDa;
		public readonly string Note;

		public DatiEndoprocedimentoPresente(bool presente,int codice, string descrizione, string codiceTipoTitolo, string descrizioneTipoTitolo, string numeroAtto, string dataAtto , string rilasciatoDa, string note)
		{
			this.Presente = presente;
			this.Codice = codice;
			this.Descrizione = descrizione;
			this.CodiceTipoTitolo = codiceTipoTitolo;
			this.DescrizioneTipoTitolo = descrizioneTipoTitolo;
			this.NumeroAtto = numeroAtto;
			this.DataAtto = dataAtto;
			this.RilasciatoDa = rilasciatoDa;
			this.Note = note;
		}
	}
}
