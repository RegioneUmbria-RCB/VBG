using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneProcure : IstanzeStepPage
	{
		[Inject]
		public ProcureService ProcureService { get;set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		public bool RichiedeFirmaDigitale
		{
			get { object o = this.ViewState["RichiedeFirmaDigitale"]; return o == null ? true : (bool)o; }
			set { this.ViewState["RichiedeFirmaDigitale"] = value; }
		}



		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggio dati viene effettuato dal service
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();

		}

		#region Gestione eventi della DataGrid
		protected void gvProcure_RowUpdating(object sender, GridViewUpdateEventArgs e)
		{
			var gridRow = gvProcure.Rows[e.RowIndex];

			var fuProcura = (FileUpload)gridRow.FindControl("fuProcura");

			var codiceAnagrafe = gvProcure.DataKeys[e.RowIndex]["CodiceAnagrafe"].ToString();
			var codiceProcuratore = gvProcure.DataKeys[e.RowIndex]["CodiceProcuratore"].ToString();

			try
			{
				var file = new BinaryFile(fuProcura, this._validPostedFileSpecification);

				ProcureService.CaricaOggettoProcura(IdDomanda, codiceAnagrafe, codiceProcuratore, file);

				gvProcure.EditIndex = -1;

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void gvProcure_RowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName != "Firma")
				return;

			var codiceOggetto = Int32.Parse(e.CommandArgument.ToString());

			this.Master.Redirect.ToFirmaDigitale(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
		}

		protected void gvProcure_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
		{
			gvProcure.EditIndex = -1;

			DataBind();
		}

		protected void gvProcure_RowEditing(object sender, GridViewEditEventArgs e)
		{
			gvProcure.EditIndex = e.NewEditIndex;

			DataBind();
		}

		protected void gvProcure_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			var codiceAnagrafe = gvProcure.DataKeys[e.RowIndex]["CodiceAnagrafe"].ToString();
			var codiceProcuratore = gvProcure.DataKeys[e.RowIndex]["CodiceProcuratore"].ToString();

			ProcureService.EliminaOggettoProcura(IdDomanda, codiceAnagrafe, codiceProcuratore);

			DataBind();
		}
		#endregion

		#region Ciclo della vita dello step

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.Procure.Procure.Where(x => x.Procuratore != null ).Count() > 0;
		}

		public override bool CanExitStep()
		{
			// Si può uscire dallo step solo se per tutti i richiedenti procurati è stato caricato il file che ne attesta la procura
			// e se i files caricati sono firmati digitalmente
			var numeroProcuratoriNonValidi = ReadFacade.Domanda
														.Procure
														.Procure
														.Where( x => x.Allegato == null && x.Procuratore != null )
														.Count();

			if (numeroProcuratoriNonValidi > 0)
			{
				Errori.Add("Per proseguire è necessario caricare tutti i documenti comprovanti la procura speciale");

				return false;
			}

			if (!RichiedeFirmaDigitale)
				return true;

			var procureNonFirmate = ReadFacade.Domanda.Procure
														.Procure
														.Where(x => x.Allegato != null && x.Procuratore != null && !x.Allegato.FirmatoDigitalmente);

			if (procureNonFirmate.Count() == 0)
				return true;

			foreach (var procura in procureNonFirmate)
			{
				var errStr = String.Format("Il file attestante la procura di {0} relativamente a {1} non è firmato digitalmente", procura.Procuratore.Nominativo, procura.Procurato.Nominativo);

				this.Errori.Add(errStr);
			}
			
			return false;
		}

		#endregion

		public override void DataBind()
		{
			var downloadFmtString = "~/MostraOggetto.ashx?IdComune=" + IdComune + "&codiceOggetto={0}";

			gvProcure.DataSource = ReadFacade.Domanda
											 .Procure
											 .Procure
											 .Where(  x => x.Procuratore != null)
											 .Select( x => new{
												 CodiceProcuratore	= x.Procuratore.CodiceFiscale,
												 CodiceAnagrafe		= x.Procurato.CodiceFiscale,
												 NomeAnagrafe		= x.Procurato.Nominativo,
												 NomeProcuratore	= x.Procuratore.Nominativo,
												 CodiceOggetto		= x.Allegato == null ? String.Empty : x.Allegato.CodiceOggetto.ToString(),
												 AllegatoPresente	= x.Allegato != null,
												 PathDownload		= x.Allegato != null ? String.Format( downloadFmtString , x.Allegato.CodiceOggetto ) : String.Empty,
												 NomeFile			= x.Allegato != null ? x.Allegato.NomeFile : String.Empty,
												 IsFirmatoDigitalmente = x.Allegato != null ? x.Allegato.FirmatoDigitalmente : false,
												 RichiedeFirmaDigitale = RichiedeFirmaDigitale
											 });
			gvProcure.DataBind();
		}
	}
}
