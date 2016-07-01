using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
	public partial class CaricamentoRiepiloghiSchede : MovimentiBasePage
	{
		[Inject]
		protected CaricamentoRiepiloghiSchedeViewModel _viewModel{get;set;}
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			this.Title = this._viewModel.GetNomeAttivitaDaEffettuare();

			gvRiepiloghi.DataSource = this._viewModel.GetListaRiepiloghi();
			gvRiepiloghi.DataBind();
		}

		protected void gvRiepiloghi_RowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == "DownloadModello")
			{
				var idScheda = Convert.ToInt32( e.CommandArgument );

				var file = this._viewModel.GeneraHtmlScheda(idScheda);

				Response.Clear();
				Response.ContentType = file.MimeType;
				Response.AddHeader("content-disposition", "attachment;filename=\"RiepilogoScheda" + idScheda + ".pdf\"");
				Response.BinaryWrite(file.FileContent);
				Response.End();
			}

			if (e.CommandName == "Firma")
			{
				var codiceOggetto = e.CommandArgument;

				Redirect("~/Reserved/InserimentoIstanza/FirmaDigitale/FirmaAllegatoMovimento.aspx", qs => 
				{
					qs.Add( "IdMovimento" , IdMovimento );
					qs.Add( "CodiceOggetto" , codiceOggetto );
					qs.Add( "ReturnTo" , Request.Url.ToString() );
				});
			}
		}

		protected void gvRiepiloghi_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			var gridRow = gvRiepiloghi.Rows[ e.RowIndex ];
			var idScheda = Convert.ToInt32(gvRiepiloghi.DataKeys[ e.RowIndex ][ 0 ]);

			try
			{
				this._viewModel.EliminaRiepilogoScheda(idScheda);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante l'eliminazione del file: " + ex.Message);
			}
		}

		protected void gvRiepiloghi_RowUpdating(object sender, GridViewUpdateEventArgs e)
		{
			var gridRow = gvRiepiloghi.Rows[ e.RowIndex ];
			var fileUpload = (FileUpload)gridRow.FindControl("fuAllegato");
			var idScheda = Convert.ToInt32(gvRiepiloghi.DataKeys[ e.RowIndex ][ 0 ]);

			try
			{
				this._viewModel.CaricaRiepilogoScheda( idScheda, new BinaryFile(fileUpload, this._validPostedFileSpecification));

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add("Si è verificato un errore durante il caricamento del file: " + ex.Message);
			}			
		}

		protected void cmdProcedi_Click(object sender, EventArgs e)
		{
			var schedeNonCompilate = this._viewModel.GetListaRiepiloghi()
														  .Where(x => !x.CodiceOggetto.HasValue);

			if (schedeNonCompilate.Count()> 0)
			{
				Errori.Add("Per poter proseguire è necessario caricare il riepilogo di tutte le schede che sono state compilate");

				foreach (var scheda in schedeNonCompilate)
					Errori.Add(String.Format("Scaricare, firmare e ricaricare il riepilogo della scheda \"{0}\"", scheda.NomeScheda));

				return;
			}


			Redirect("~/Reserved/GestioneMovimenti/RiepilogoEInvio.aspx", qs => qs.Add("IdMovimento" , IdMovimento));
		}

		protected void cmdTornaIndietro_Click(object sender, EventArgs e)
		{
			Redirect("~/Reserved/GestioneMovimenti/CompilaSchedeDinamiche.aspx", qs => qs.Add("IdMovimento", IdMovimento));
		}
	}
}