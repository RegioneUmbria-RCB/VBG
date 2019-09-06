// -----------------------------------------------------------------------
// <copyright file="ListaIdOneriContiene.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
	using Init.Sigepro.FrontEnd.Infrastructure;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class ListaIdOneriContiene:ISpecification<PresentazioneIstanzaDbV2.OneriDomandaRow>
	{
		IEnumerable<IdentificativoOnereSelezionato> _listaId;

		public ListaIdOneriContiene(IEnumerable<IdentificativoOnereSelezionato> listaId)
		{
			this._listaId = listaId;
		}

		public bool IsSatisfiedBy(PresentazioneIstanzaDbV2.OneriDomandaRow item)
		{
			foreach (var e in this._listaId)
			{
				if (e.IdCausale == item.CodiceCausale && e.TipoOnere == item.TipoOnere && e.IdInterventoOEndo.ToString() == item.InterventoOEndoOrigine)
				{
					return true;
				}
			}
			
			return false;
		}
	}
}
