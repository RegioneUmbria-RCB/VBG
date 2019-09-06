using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;

namespace Init.SIGePro.DatiDinamici.GestioneLocalizzazioni
{
	public interface IQueryLocalizzazioni
	{
		IEnumerable<RiferimentiLocalizzazione> Execute(string tipoLocalizzazione, string espressioneFormattazioneDati);
	}

	public abstract class QueryLocalizzazioniBase : IQueryLocalizzazioni
	{
		public IEnumerable<RiferimentiLocalizzazione> Execute(string tipoLocalizzazione, string espressioneFormattazioneDati)
		{
			var localizzazioni = GetLocalizzazioni(tipoLocalizzazione);

			return localizzazioni.Select(x => new RiferimentiLocalizzazione { Codice = x.Uuid, Descrizione = x.ToString(espressioneFormattazioneDati) });
		}

		public abstract IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione);
		
	}

}
