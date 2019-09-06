// -----------------------------------------------------------------------
// <copyright file="Scadenza.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Scadenzario
{
	using System.Text;
	using Init.Sigepro.FrontEnd.GestioneMovimenti.MovimentiWebService;
	using System;

	public class Scadenza
	{
		/// <remarks/>
		public string CodEnte { get; set; }
		public string CodSportello { get; set; }
		public string CodiceIstanza { get; set; }
		public string NumeroProtocollo { get; set; }
		public string DataProtocollo { get; set; }
		public string NumeroIstanza { get; set; }
		public string CodStatoIstanza { get; set; }
		public string DescrStatoIstanza { get; set; }
		public string CodMovimento { get; set; }
		public string DatiMovimento { get; set; }
		public string CodMovimentoDaFare { get; set; }
		public string DescrMovimentoDaFare { get; set; }
		public string DataScadenza { get; set; }
		public string Responsabile { get; set; }
		public string Procedimento { get; set; }
		public string Procedura { get; set; }
		public string ModuloSoftware { get; set; }
		public string AmmAmministrazione { get; set; }
		public string AmmPartitaiva { get; set; }
		public string DatiRichiedente { get; set; }
		public string DatiTecnico { get; set; }
		public string DatiAzienda { get; set; }
		public string CodiceScadenza { get; set; }
		public string CodiceAmministrazione { get; set; }
		public string CodiceInventario { get; set; }
        public string Uuid { get; set; }



        public Scadenza(ElementoListaScadenze elScadenze)
		{
			this.CodiceScadenza = elScadenze.CodScadenza;
			this.CodiceAmministrazione = elScadenze.CodiceAmministrazione;
			this.CodiceInventario = elScadenze.CodiceInventario;
			this.CodEnte = elScadenze.CodEnte;
			this.CodSportello = elScadenze.CodSportello;
			this.CodiceIstanza = elScadenze.CodiceIstanza;
			this.NumeroProtocollo = elScadenze.NumeroProtocollo;
			this.DataProtocollo = elScadenze.DataProtocollo;
			this.NumeroIstanza = elScadenze.NumeroIstanza;
			this.CodStatoIstanza = elScadenze.CodStatoIstanza;
			this.DescrStatoIstanza = elScadenze.DescrStatoIstanza;
			this.CodMovimento = elScadenze.CodMovimento;
			this.DatiMovimento = elScadenze.DataMovimento + " - " + elScadenze.DescrMovimento;

			this.CodMovimentoDaFare = elScadenze.CodMovimentoDaFare;
			this.DescrMovimentoDaFare = elScadenze.DescrMovimentoDaFare;
			this.DataScadenza = elScadenze.DataScadenza;
			this.Responsabile = elScadenze.Responsabile;
			this.Procedimento = elScadenze.Procedimento;
			this.Procedura = elScadenze.Procedura;
			this.ModuloSoftware = elScadenze.ModuloSoftware;
			this.AmmAmministrazione = elScadenze.AmmAmministrazione;
			this.AmmPartitaiva = elScadenze.AmmPartitaiva;

			this.DatiRichiedente = PopolaDatiRichiedente(elScadenze);
			this.DatiAzienda = PopolaDatiAzienda(elScadenze);
			this.DatiTecnico = PopolaDatiTecnico(elScadenze);
            this.Uuid = elScadenze.Uuid;
		}

		private string PopolaDatiRichiedente(ElementoListaScadenze elScadenze)
		{
			StringBuilder datiric = new StringBuilder();
			datiric.Append(elScadenze.RicNominativo);

			if (!string.IsNullOrEmpty(elScadenze.RicCodiceFiscale))
			{
				datiric.Append("<br>[CF: ");
				datiric.Append(elScadenze.RicCodiceFiscale);
				datiric.Append("]");
			}

			if (!string.IsNullOrEmpty(elScadenze.RicPartitaIva))
			{
				datiric.Append("<br>[P.IVA: ");
				datiric.Append(elScadenze.RicPartitaIva);
				datiric.Append("]");
			}

			datiric.Append("<br>");
			datiric.Append(elScadenze.RicIndirizzo);
			datiric.Append(", ");
			datiric.Append(elScadenze.RicLocalita);
			datiric.Append(" - ");
			datiric.Append(elScadenze.RicCap);
			datiric.Append(", ");
			datiric.Append(elScadenze.RicCitta);
			datiric.Append(" (");
			datiric.Append(elScadenze.RicProvincia);
			datiric.Append(")");

			return datiric.ToString();
		}

		private string PopolaDatiTecnico(ElementoListaScadenze elScadenze)
		{
			StringBuilder datiTec = new StringBuilder();
			datiTec.Append(elScadenze.TecNominativo);

			if (!String.IsNullOrEmpty(elScadenze.TecCodiceFiscale))
			{
				datiTec.Append(" [CF: ");
				datiTec.Append(elScadenze.TecCodiceFiscale);
				datiTec.Append("]");
			}

			if (!String.IsNullOrEmpty(elScadenze.TecPartitaIva))
			{
				datiTec.Append(" [P.IVA: ");
				datiTec.Append(elScadenze.TecPartitaIva);
				datiTec.Append("]");
			}

			return datiTec.ToString();
		}

		private string PopolaDatiAzienda(ElementoListaScadenze elScadenze)
		{
			StringBuilder datiAz = new StringBuilder();
			datiAz.Append(elScadenze.AzNominativo);

			if (!String.IsNullOrEmpty(elScadenze.AzCodiceFiscale))
			{
				datiAz.Append(" [CF: ");
				datiAz.Append(elScadenze.AzCodiceFiscale);
				datiAz.Append("]");
			}

			if (!String.IsNullOrEmpty(elScadenze.AzPartitaIva))
			{
				datiAz.Append(" [P.IVA: ");
				datiAz.Append(elScadenze.AzPartitaIva);
				datiAz.Append("]");
			}

			return datiAz.ToString();
		}
	}
}
