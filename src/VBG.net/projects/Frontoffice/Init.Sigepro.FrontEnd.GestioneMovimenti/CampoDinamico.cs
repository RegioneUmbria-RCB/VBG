// -----------------------------------------------------------------------
// <copyright file="CampoDinamico.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CampoDinamico : Entity
	{
		Dictionary<int , ValoreDatoDinamico> _valoriCampo = new Dictionary<int, ValoreDatoDinamico>();


		internal CampoDinamico(int id):base( id )
		{

		}

		public ValoreDatoDinamico GetValoreAllIndice(int indice)
		{
			ValoreDatoDinamico valore = null;

			if (!this._valoriCampo.TryGetValue(indice, out valore))
				return ValoreDatoDinamico.Empty;

			return valore;
		}

		public void ImpostaValore(int indiceMolteplicita, ValoreDatoDinamico valore )
		{
			this._valoriCampo[indiceMolteplicita] = valore;
		}

		public void EliminaValori()
		{
			this._valoriCampo.Clear();
		}

		public bool ContieneValori()
		{
			return this._valoriCampo.Count > 0;
		}
	}
}
