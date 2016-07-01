// -----------------------------------------------------------------------
// <copyright file="DatiMovimentoDiOrigine.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using Init.Sigepro.FrontEnd.Infrastructure.ModelBase;

	/// <summary>
	/// Movimento che ha come contromovimento il movimento da effettuare nel frontoffice
	/// </summary>
	public class DatiMovimentoDiOrigine 
	{
		public RiferimentiIstanza DatiIstanza { get; set; }
		public int IdMovimento { get; set; }
		public string NomeAttivita { get; set; }
		public DateTime? DataAttivita { get; set; }
		public DatiProtocolloMovimento Protocollo{get;set;}
		public string Procedimento { get; set; }
		public string CodiceProcedimento { get; set; }
		public string Amministrazione { get; set; }
		public string Esito { get; set; }
		public string Oggetto { get; set; }
		public string Note { get; set; }

		public List<DatiAllegatoMovimento> Allegati { get; set; }
		public List<SchedaDinamicaMovimento> SchedeDinamiche { get; set; }

		public bool Pubblica { get; set; }
		public bool PubblicaOggetto { get; set; }
		public bool PubblicaEsito { get; set; }

		public bool HaProcedimento { get { return !String.IsNullOrEmpty(this.Procedimento); } }
		public bool HaAmministrazione { get { return !String.IsNullOrEmpty(this.Amministrazione); } }

		public DatiMovimentoDiOrigine()
		{
			this.Allegati = new List<DatiAllegatoMovimento>();
			this.SchedeDinamiche = new List<SchedaDinamicaMovimento>();
		}
	}
}
