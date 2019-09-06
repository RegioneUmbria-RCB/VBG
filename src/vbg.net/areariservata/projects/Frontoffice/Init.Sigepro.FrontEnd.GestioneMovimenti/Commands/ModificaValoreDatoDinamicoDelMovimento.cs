// -----------------------------------------------------------------------
// <copyright file="ModificaValoreDatoDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;

	/// <summary>
	/// Comando da utilizzare per modificare il valore di un dato dinamico in un movimento di frontoffice
	/// </summary>
	public class ModificaValoreDatoDinamicoDelMovimento : Command
	{
		public readonly string IdComune;
		public readonly int IdMovimento;
		public readonly int IdCampoDinamico;
		public readonly int IndiceMolteplicita;
		public readonly string Valore;
		public readonly string ValoreDecodificato;

		public ModificaValoreDatoDinamicoDelMovimento(string idComune, int idMovimento, int idCampoDinamico, int indiceMolteplicita, string valore, string valoreDecodificato)
		{
			this.IdComune = idComune;
			this.IdMovimento = idMovimento;
			this.IdCampoDinamico = idCampoDinamico;
			this.IndiceMolteplicita = indiceMolteplicita;
			this.Valore = valore;
			this.ValoreDecodificato = valoreDecodificato;
		}
	}
}
