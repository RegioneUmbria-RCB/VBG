using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public class IndirizzoStradario
	{
		public int Id { get; private set; }
		public int CodiceStradario { get; private set; }
		public string Indirizzo { get; private set; }
		public string Civico { get; private set; }
		public string Esponente { get; private set; }
		public string Colore { get; private set; }
		public string Scala { get; private set; }
		public string Interno { get; private set; }
		public string EsponenteInterno { get; private set; }
		public string Piano { get; private set; }
		public string Fabbricato { get; private set; }
		public string Km { get; private set; }
		public string Note { get; private set; }
		public string Circoscrizione { get; private set; }
		public string Cap { get; private set; }
		public string Uuid { get; private set; }
		public string Longitudine { get; private set; }
		public string Latitudine { get; private set; }
		public string TipoLocalizzazione { get; private set; }
		public string CodiceCivico { get; private set; }
		public string CodiceViario { get; private set; }

        public string AccessoTipo { get; private set; }
        public string AccessoNumero { get; private set; }
        public string AccessoDescrizione { get; private set; }


        public IEnumerable<RiferimentoCatastale> RiferimentiCatastali { get; private set; }
		public RiferimentoCatastale PrimoRiferimentoCatastale { get { return this.RiferimentiCatastali.FirstOrDefault(); } }

		protected IndirizzoStradario()
		{
		}

		public static IndirizzoStradario FromStradarioRow(PresentazioneIstanzaDbV2.ISTANZESTRADARIORow row, PresentazioneIstanzaDbV2.DATICATASTALIDataTable datiCatastali)
		{
			var rowId = (int)row.ID;

			return new IndirizzoStradario 
			{ 
				Id = rowId,
				CodiceStradario = row.CODICESTRADARIO,
				Civico = row.CIVICO,
				Colore = row.COLORE,
				Esponente = row.Esponente,
				EsponenteInterno = row.EsponenteInterno,
				Indirizzo = row.STRADARIO,
				Fabbricato = row.Fabbricato,
				Interno = row.Interno,
				Km = row.Km,
				Piano = row.Piano,
				Scala = row.Scala,
				Note = row.NOTE,
				Circoscrizione = row.Circoscrizione,
				Cap = row.Cap,
				Uuid = row.Uuid,
				Longitudine = row.Longitudine,
				Latitudine = row.Latitudine,
				TipoLocalizzazione = row.TipoLocalizzazione,
				CodiceCivico = row.CodCivico,
				CodiceViario = row.CodViario,
                AccessoTipo = row.AccessoTipo,
                AccessoNumero = row.AccessoNumero,
                AccessoDescrizione = row.AccessoDescrizione,
				RiferimentiCatastali = datiCatastali.Where(x => !x.IsIdLocalizzazioneNull() && x.IdLocalizzazione == rowId)
													.Select(x => RiferimentoCatastale.FromDatiCatastaliRow(x))
													.ToList()
			};
		}

		public LocalizzazioneNelComuneType ToLocalizzazioneNelComuneType()
		{
			var riferimentiCatastali = this.RiferimentiCatastali == null ? null : this.RiferimentiCatastali.Select(x => x.ToRiferimentoCatastaleType()).ToArray();

			return new LocalizzazioneNelComuneType
			{
				id = this.CodiceStradario.ToString(),
				codiceViario = String.Empty,
				denominazione = this.Indirizzo,
				civico = this.Civico,
				esponente = this.Esponente,
				colore = this.Colore,
				scala = this.Scala,
				interno = this.Interno,
				esponenteInterno = this.EsponenteInterno,
				piano = this.Piano,
				fabbricato = this.Fabbricato,
				km = this.Km,
				uuid = this.Uuid,
				accessoTipo = this.AccessoTipo,
                accessoNumero = this.AccessoNumero,
                accessoDescrizione = this.AccessoDescrizione,
				tipo = String.IsNullOrEmpty(this.TipoLocalizzazione) ? null : new TipoLocalizzazioneType { descrizione = this.TipoLocalizzazione },
				coordinate = String.IsNullOrEmpty(this.Latitudine) ? null : new CoordinateType { latitudine = this.Latitudine, longitudine = this.Longitudine },
				riferimentoCatastale = riferimentiCatastali
			};
		}

		public override string ToString()
		{
			var sb = new StringBuilder();

			sb.Append(Indirizzo);

			if (!String.IsNullOrEmpty(Civico))
				sb.AppendFormat(" {0}", Civico);

			if (!String.IsNullOrEmpty(Esponente))
				sb.AppendFormat("/{0}", Esponente);

			if (!String.IsNullOrEmpty(Colore))
				sb.AppendFormat("/{0}", Colore);

			return sb.ToString();
		}
	}
}
