// -----------------------------------------------------------------------
// <copyright file="DatiMovimentoDaEffettuare.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using System.Xml.Serialization;

	/// <summary>
	/// Dati relativi ad un movimento da effettuare nel frontoffice
	/// </summary>
	public class DatiMovimentoDaEffettuare
	{
		public class RiepilogoSchedaDinamica
		{
			public int IdScheda { get; set; }
			public string NomeScheda { get; set; }
			public string NomeFile { get; set; }
			public int? CodiceOggetto { get; set; }
			public bool FirmatoDigitalmente { get; set; }

			public RiepilogoSchedaDinamica(int idScheda, string nomeScheda, DatiRiepilogoSchedaDinamica datiRiepilogo)
			{
				this.IdScheda = idScheda;
				this.NomeScheda = nomeScheda;

				if (datiRiepilogo != null)
				{
					this.NomeFile = datiRiepilogo.NomeFile;
					this.CodiceOggetto = datiRiepilogo.IdAllegato;
					this.FirmatoDigitalmente = datiRiepilogo.FirmatoDigitalmente;
				}
				else
				{
					this.NomeFile = String.Empty;
					this.CodiceOggetto = (int?)null;
				}
			}
		}


		
		public int Id { get; set; }
		public string IdTipoAttivita { get; set; }
		public string NomeAttivita { get; set; }
		public string Note { get; set; }
		public List<DatiAllegatoMovimento> Allegati { get; set; }
		[XmlIgnore]
		public DatiMovimentoDiOrigine MovimentoDiOrigine { get; private set; }
		public List<DatiRiepilogoSchedaDinamica> RiepiloghiSchedeDinamiche { get; set; }
		public List<ValoreSchedaDinamicaMovimento> ValoriSchedeDinamiche { get; set; }
		public bool Trasmesso { get; set; }
		public List<int> ListaIdSchedeCompilate { get; set; }

		public DatiMovimentoDaEffettuare()
		{
			this.Allegati = new List<DatiAllegatoMovimento>();
			this.RiepiloghiSchedeDinamiche = new List<DatiRiepilogoSchedaDinamica>();
			this.ValoriSchedeDinamiche = new List<ValoreSchedaDinamicaMovimento>();
			this.ListaIdSchedeCompilate = new List<int>();
		}

		public IEnumerable<RiepilogoSchedaDinamica> GetRiepiloghiSchedeDinamiche()
		{
			return this.MovimentoDiOrigine.SchedeDinamiche.Select(x => new RiepilogoSchedaDinamica(
				x.IdScheda,
				x.NomeScheda,
				this.RiepiloghiSchedeDinamiche
						 .Where(riepilogo => riepilogo.IdScheda == x.IdScheda)
						 .FirstOrDefault()
			));
		}

		internal void ImpostaMovimentoDiOrigine(DatiMovimentoDiOrigine movimentoDiOrigine)
		{
			this.MovimentoDiOrigine = movimentoDiOrigine;
		}

		internal void SegnaSchedaComeCompilata(int idScheda)
		{
			if (!this.ListaIdSchedeCompilate.Contains(idScheda))
				this.ListaIdSchedeCompilate.Add(idScheda);
		}
	}
}
