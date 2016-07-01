using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Commands;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ReadInterface;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class EffettuaMovimento : MovimentiBasePage
	{
		private static class Constants
		{
			public const string TestoIntestazione = "In riferimento alla pratica numero {numeroPratica} del {dataPratica}" +
													"{datiProtocollo} relativamente all'attività istruttoria in seguito riportata";
			public const string SegnapostoNumeroPratica = "{numeroPratica}";
			public const string SegnapostoDataPratica = "{dataPratica}";
			public const string SegnapostoDatiProtocollo = "{datiProtocollo}";
		}

		[Inject]
		protected RiepilogoMovimentoDiOrigineViewModel _viewModel { get; set; }

		protected DatiMovimentoDaEffettuare MovimentoDaEffettuare { get; set; }
		protected DatiMovimentoDiOrigine DataSource{get;set;}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();
			this.DataSource = MovimentoDaEffettuare.MovimentoDiOrigine;

			ltrDescrizioneStep.Text = GeneraDescrizioneStep(MovimentoDaEffettuare);					

			base.DataBind();
		}

		private static string GeneraDescrizioneStep(DatiMovimentoDaEffettuare movimentoDaEffettuare)
		{
			var numeroPratica = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.NumeroIstanza;
			var dataPratica = movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.DataIstanza.ToString("dd/MM/yyyy");
			var datiprotocollo = String.Empty;

			if (movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.Protocollo.DatiPresenti)
				datiprotocollo = String.Format(" (prot n.{0} del {1}) ",
												movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.Protocollo.Numero,
												movimentoDaEffettuare.MovimentoDiOrigine.DatiIstanza.Protocollo.Data.Value.ToString("dd/MM/yyyy"));

			var str = Constants.TestoIntestazione.Replace(Constants.SegnapostoNumeroPratica, numeroPratica);
			str = str.Replace(Constants.SegnapostoDataPratica, dataPratica);
			str = str.Replace(Constants.SegnapostoDatiProtocollo, datiprotocollo);

			return str;
		}

		protected void cmdProcedi_Click(object sender, EventArgs e)
		{
            if(this._viewModel.ContieneSchedeDinamichedaCompilare())
				Redirect("~/Reserved/GestioneMovimenti/CompilaSchedeDinamiche.aspx", qs => qs.Add("IdMovimento", IdMovimento));
            else
				Redirect("~/Reserved/GestioneMovimenti/RiepilogoEInvio.aspx", qs => qs.Add("IdMovimento", IdMovimento));
		}

	}
}