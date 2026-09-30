// -----------------------------------------------------------------------
// <copyright file="CampiServiceBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.DatiDinamici
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class CampiServiceBase
	{
		IEnumerable<CampoDinamicoBase> _campi;

		protected IEnumerable<CampoDinamicoBase> Campi { get { return _campi; } }

		public CampiServiceBase(IEnumerable<CampoDinamicoBase> campi)
		{
			this._campi = campi;
		}

		/// <summary>
		/// Ricerca un campo in base all'id
		/// </summary>
		/// <param name="id">id del campo da cercare</param>
		/// <returns>Campo corrispondente all'id specificato o null se il campo non viene trovato</returns>
		public CampoDinamicoBase TrovaCampoDaId(int id)
		{
			// if (id < 0) return null;
			return this._campi.Where(campo => campo.Id == id)
								.FirstOrDefault();
		}

		public CampoDinamicoBase TrovaCampo(string nomeCampo, bool ignoraEccezioni)
		{
			var campoTrovato = this._campi.Where(x => x.NomeCampo == nomeCampo)
										 .FirstOrDefault();

			if (campoTrovato == null && !ignoraEccezioni)
				throw new Exception("Impossibile trovare il campo \"" + nomeCampo + "\" nel modello corrente");

			return campoTrovato;
		}
	}
}
