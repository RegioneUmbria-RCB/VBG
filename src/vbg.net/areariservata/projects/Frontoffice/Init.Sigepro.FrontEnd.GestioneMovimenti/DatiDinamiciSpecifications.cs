// -----------------------------------------------------------------------
// <copyright file="DatiDinamiciSpecification.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure;

	/// <summary>
	/// Vera se il campo dinamico non esiste o se contiene un valore vuoto all'indice specificato
	/// </summary>
	public class CampoNonHaUnValoreAllIndiceSpecification : ISpecification<CampoDinamico>
	{
		int _indiceMolteplicita = -1;

		public CampoNonHaUnValoreAllIndiceSpecification(int indiceMolteplicita)
		{
			this._indiceMolteplicita = indiceMolteplicita;
		}

		#region ISpecification<CampoDinamico> Members

		public bool IsSatisfiedBy(CampoDinamico item)
		{
			if (item == null)
				return true;

			if (item.GetValoreAllIndice(this._indiceMolteplicita) == ValoreDatoDinamico.Empty)
				return true;

			return false;
		}

		#endregion
	}


	/// <summary>
	/// Vera se il campo esiste e ha un valore diverso da quello passato come riferimento
	/// </summary>
	public class CampoHaUnValoreDiversoAllIndiceSpecification : ISpecification<CampoDinamico>
	{
		int _indiceMolteplicita;
		ValoreDatoDinamico _valoreRiferimento;

		public CampoHaUnValoreDiversoAllIndiceSpecification(int indiceMolteplicita , ValoreDatoDinamico valoreRiferimento)
		{
			this._indiceMolteplicita = indiceMolteplicita;
			this._valoreRiferimento = valoreRiferimento;
		}

		#region ISpecification<CampoDinamico> Members

		public bool IsSatisfiedBy(CampoDinamico item)
		{
			if (item == null)
				return false;

			return item.GetValoreAllIndice(this._indiceMolteplicita) != this._valoreRiferimento;
		}

		#endregion
	}
}
