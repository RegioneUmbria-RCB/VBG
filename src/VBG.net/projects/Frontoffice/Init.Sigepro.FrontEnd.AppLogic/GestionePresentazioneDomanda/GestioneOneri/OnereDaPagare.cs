using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
	public class OnereDaPagare
	{
		public class CausaleOnere
		{
			public int Codice { get; private set; }
			public string Descrizione { get; private set; }

			public CausaleOnere(int codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}

			public override string ToString()
			{
				return this.Descrizione;
			}
		}

		public class EndoOInterventoOrigineOnere
		{
			public int Codice { get; private set; }
			public string Descrizione { get; private set; }

			public EndoOInterventoOrigineOnere(int codice, string descrizione)
			{
				this.Codice = codice;
				this.Descrizione = descrizione;
			}

			public override string ToString()
			{
				return this.Descrizione;
			}
		}

		public enum ProvenienzaOnereEnum
		{
			Endoprocedimento,
			Intervento
		}

		public class DatiEstremiPagamento
		{
			public class TipoPagamento
			{
				public string Codice{get;private set;}
				public string Descrizione{get;private set;}

				public TipoPagamento(string codice, string descrizione)
				{
					this.Codice = codice;
					this.Descrizione = descrizione;
				}
			}

			public TipoPagamento ModalitaPagamento{ get; private set; }
			public string DataPagamento { get; private set; }
			public string Riferimento { get; private set; }

			public DatiEstremiPagamento(TipoPagamento modalitaPagamento, string data, string riferimento)
			{
				this.ModalitaPagamento = modalitaPagamento;
				this.DataPagamento = data;
				this.Riferimento = riferimento;
			}
		}

		public CausaleOnere Causale { get; private set; }
		public EndoOInterventoOrigineOnere EndoOInterventoOrigine { get; private set; }
		public ProvenienzaOnereEnum Provenienza { get; private set; }
		//public string TipoOnere { get; private set; }
		public float Importo{ get; private set; }
		public float ImportoPagato { get; private set; }
		public string Note { get; private set; }
		public DatiEstremiPagamento EstremiPagamento { get; private set; }
		

		public static OnereDaPagare FromOneriRow(PresentazioneIstanzaDbV2.OneriDomandaRow row)
		{
			var causale = new CausaleOnere(row.CodiceCausale, row.Causale);
			var endoOIntervento = new OnereDaPagare.EndoOInterventoOrigineOnere(row.CodiceInterventoOEndoOrigine, row.InterventoOEndoOrigine);
			var provenienza = row.TipoOnere == "E" ? ProvenienzaOnereEnum.Endoprocedimento : ProvenienzaOnereEnum.Intervento;
			var importo = row.Importo;
			var importoPagato = row.ImportoPagato;
			var note = row.Note;

			var onere = new OnereDaPagare(provenienza, causale, endoOIntervento, importo, importoPagato, note);

			if (row.IsNonPagatoNull() || !row.NonPagato)
			{
				var tipoPagamento = new DatiEstremiPagamento.TipoPagamento(row.CodiceTipoPagamento, row.DescrizioneTipoPagamento);
				var dataPagamento = row.DataPagmento;
				onere.EstremiPagamento = new DatiEstremiPagamento(tipoPagamento, dataPagamento, row.NumeroPagamento);
			}

			return onere;
		}

		protected OnereDaPagare(ProvenienzaOnereEnum provenienza, CausaleOnere causale, EndoOInterventoOrigineOnere endoOInterventoOrigine, float importo, float importoPagato, string note)
		{
			this.Provenienza = provenienza;
			this.Causale = causale;
			this.EndoOInterventoOrigine = endoOInterventoOrigine;
			this.Importo = importo;
			this.Note = note;
			this.ImportoPagato = importoPagato;
		}

		public OneriType ToOneriType()
		{
			return new OneriType
			{
				causale = new CausaleOnereType
				{
					id		= this.Causale.Codice.ToString(),
					causale = this.Causale.Descrizione
				},
				codiceProcedimento = this.Provenienza == OnereDaPagare.ProvenienzaOnereEnum.Endoprocedimento ? this.EndoOInterventoOrigine.Codice.ToString() : String.Empty,
				annotazioni = this.Note,
				scadenze = new OneriScadenzeType[]{
					new OneriScadenzeType{
						dataScadenza = DateTime.Now,
						dataScadenzaSpecified = true,
						importoRata = Math.Round( Double.Parse( this.ImportoPagato.ToString("N2")), 2),
						numeroRata = "1",
						pagamenti = this.EstremiPagamento == null ? 
										null :					
										new OneriPagamentiType[]{
											new OneriPagamentiType
											{
												data = DateTime.ParseExact( this.EstremiPagamento.DataPagamento, "dd/MM/yyyy",null),
												importo = Math.Round( Double.Parse( this.ImportoPagato.ToString("N2")), 2),
												modalita = this.EstremiPagamento.ModalitaPagamento.Descrizione,
												rifDocumento = this.EstremiPagamento.Riferimento
											}
										}
					}
				},
				importo = Math.Round(Double.Parse(this.ImportoPagato.ToString("N2")), 2)
			};
		}
	}
}
