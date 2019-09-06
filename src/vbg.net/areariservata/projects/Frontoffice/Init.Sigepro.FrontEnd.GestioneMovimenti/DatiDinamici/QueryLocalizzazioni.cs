using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using AutoMapper;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.DatiDinamici
{
	public class QueryLocalizzazioni : QueryLocalizzazioniBase
	{
		Istanze _istanza;

		public QueryLocalizzazioni(Istanze istanza)
		{
			this._istanza = istanza;
		}

		public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
		{
			return this.
					_istanza.
					Stradario.
					Where(x =>
					{
						if (String.IsNullOrEmpty(tipoLocalizzazione))
							return x.TipoLocalizzazione == null;

						return x.TipoLocalizzazione.Descrizione.ToUpperInvariant() == tipoLocalizzazione.ToUpperInvariant();
					})
					.Select(x => Mapper.Map<LocalizzazioneIstanza>(x));
		}
	}
}
