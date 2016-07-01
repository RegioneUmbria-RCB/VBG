using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni;
using Init.SIGePro.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess
{
	public static class LocalizzazioneIstanzaExtensions
	{
		public static LocalizzazioneIstanza.RiferimentiCatastali ToD2RiferimentiCatastali(this RiferimentoCatastale rc)
		{
			if (rc == null)
				return null;

			var tipoCatasto = rc.TipoCatasto == RiferimentoCatastale.TipoCatastoEnum.Terreni ? "T" : "F";
			var foglio = rc.Foglio;
			var particella = rc.Particella;
			var sub = rc.Sub;

			return new LocalizzazioneIstanza.RiferimentiCatastali(tipoCatasto, foglio, particella, sub);
		}

		public static LocalizzazioneIstanza.RiferimentiCatastali ToD2RiferimentiCatastali(this IEnumerable<RiferimentoCatastale> rcList)
		{
			if (rcList == null)
				return null;

			return rcList.FirstOrDefault().ToD2RiferimentiCatastali();
		}

		public static LocalizzazioneIstanza ToD2LocalizzazioneIstanza(this IndirizzoStradario x)
		{
			return new LocalizzazioneIstanza { 
						Civico = x.Civico,
						Coordinate = String.IsNullOrEmpty(x.Longitudine) ? null : new LocalizzazioneIstanza.Coordinata(x.Longitudine, x.Latitudine), // TODO: gestire le coordinate
						Esponente = x.Esponente,
						EsponenteInterno = x.EsponenteInterno,
						Indirizzo = x.Indirizzo,
						Interno = x.Interno,
						Km = x.Km,
						Note = x.Note,
						Piano = x.Piano,
						Scala = x.Scala,
						Uuid = x.Uuid,
						Mappali = x.RiferimentiCatastali.ToD2RiferimentiCatastali()
					};
		}
	}


	public class QueryLocalizzazioni : QueryLocalizzazioniBase
	{
		IDomandaOnlineReadInterface _readInterface;

		public QueryLocalizzazioni(IDomandaOnlineReadInterface readInterface)
		{
			this._readInterface = readInterface;
		}

		public override IEnumerable<LocalizzazioneIstanza> GetLocalizzazioni(string tipoLocalizzazione)
		{
			return this
					._readInterface
					.Localizzazioni
					.Indirizzi
					.Where( x => x.TipoLocalizzazione.ToUpperInvariant() == tipoLocalizzazione.ToUpperInvariant())
					.Select(x => x.ToD2LocalizzazioneIstanza());
		}
	}
}
