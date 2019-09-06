using System;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneAllegatiDatiDinamici : IstanzeStepPage
	{
		[Inject]
		public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

		[Inject]
		public IModelliDinamiciService ModelliDinamiciService { get; set; }

		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

		[Inject]
		public RiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory { get; set; }

		[Inject]
		public IHtmlToPdfFileConverter _fileConverter { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        ILog m_logger = LogManager.GetLogger(typeof(GestioneAllegatiDatiDinamici));


		public bool IgnoraObbligoFirmaDigitale
		{
			get { return ViewstateGet("IgnoraObbligoFirmaDigitale", false); }
			set { ViewStateSet("IgnoraObbligoFirmaDigitale",value); }
		}

		public bool GeneraRiepilogoSchedeCheNonRichiedonoFirma
		{
			get { return ViewstateGet("GeneraRiepilogoSchedeCheNonRichiedonoFirma", true); }
			set { ViewStateSet("GeneraRiepilogoSchedeCheNonRichiedonoFirma", value); }
		}




		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggio viene gestito dal service
			this.Master.IgnoraSalvataggioDati = true;
            this.Master.ResetValidatorsOnLoad = false;

			if (!IsPostBack)
				DataBind();
		}

		#region Ciclo di vita dello step

		public override void OnInitializeStep()
		{
			ModelliDinamiciService.RigeneraRiepiloghi(this.IdDomanda, this.GeneraRiepilogoSchedeCheNonRichiedonoFirma);
		}

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.RiepiloghiSchedeDinamiche.Count > 0;
		}

		public override bool CanExitStep()
		{
			var listaErrori = ReadFacade.Domanda.DatiDinamici.VerificaUploadModelliRichiesti();

			this.Errori.AddRange(listaErrori);

			return listaErrori.Count() == 0;
		}



		#endregion




		/// <summary>
		/// Elimina il riepilogo di una scheda dalla domanda
		/// </summary>
		/// <param name="row"></param>
		private void EliminaRigaRiepilogo(PresentazioneIstanzaDataSet.RiepilogoDatiDinamiciRow row)
		{
			if (!row.IsCodiceOggettoNull())
				_allegatiDomandaFoRepository.EliminaAllegato(IdDomanda, row.CodiceOggetto);

			row.Delete();
		}

		#region Binding dei dati
		public class AllegatiRiepiloghiBindingItem
		{
			public int IdModello { get; set; }
			public int IndiceMolteplicita { get; set; }
			public bool Richiesto { get; set; }
			public bool RichiedeFirmaDigitale { get; set; }
			public string NomeScheda { get; set; }
			public string LinkDownloadModello { get; set; }
			public int? CodiceOggetto { get; set; }
			public string NomeFile { get; set; }
			public bool FirmatoDigitalmente { get; set; }
			public bool MostraBottoneFirma { get { return this.CodiceOggetto.HasValue && this.RichiedeFirmaDigitale && !this.FirmatoDigitalmente; } }
			public string CommandArgument { get { return this.IdModello.ToString() + "$" + this.IndiceMolteplicita.ToString(); } }
		}

		public override void DataBind()
		{
			var dataSource = from r in ReadFacade.Domanda.RiepiloghiSchedeDinamiche.Riepiloghi
							 orderby OrdineModello(r.IdModello), (int)ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma, r.Descrizione
							 select new AllegatiRiepiloghiBindingItem
							 {
								 IdModello				= r.IdModello,
								 IndiceMolteplicita		= r.IndiceMolteplicita,
								 Richiesto				= ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
								 RichiedeFirmaDigitale	= ReadFacade.Domanda.DatiDinamici.GetModelloById(r.IdModello).TipoFirma != ModelloDinamico.TipoFirmaEnum.Nessuna,
								 NomeScheda				= r.Descrizione,
								 //LinkDownloadModello	= GetUrlDownload( r.IdModello , r.IndiceMolteplicita ),
								 CodiceOggetto			= r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
								 NomeFile				= r.AllegatoDellUtente == null ? String.Empty : r.AllegatoDellUtente.NomeFile,
								 FirmatoDigitalmente	= r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente
							 };


			

			gvRiepiloghiDatiDinamici.DataSource = dataSource;
			gvRiepiloghiDatiDinamici.DataBind();
		}

		private int OrdineModello(int idModello)
		{
			var ordine = ReadFacade.Domanda.DatiDinamici.ModelliIntervento.Where(x => x.Modello.IdModello == idModello).Select(x => x.Ordine).FirstOrDefault();

			return ordine;
		}

		#endregion

		#region Gestione eventi della DataGrid
		protected void gvRiepiloghiDatiDinamici_RowUpdating(object sender, GridViewUpdateEventArgs e)
		{
			var gridRow = gvRiepiloghiDatiDinamici.Rows[e.RowIndex];

			var fuAllegato = (FileUpload)gridRow.FindControl("fuAllegato");
			var idModello = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IdModello"]);
			var indiceMolteplicita = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IndiceMolteplicita"]);

			try
			{
				var file = new BinaryFile(fuAllegato, this._validPostedFileSpecification);

				ModelliDinamiciService.AggiungiOggettoRiepilogo(IdDomanda, idModello, indiceMolteplicita, file);

				gvRiepiloghiDatiDinamici.EditIndex = -1;

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void MostraModelloDinamico(object sender, EventArgs e)
		{
            var ib = (LinkButton)sender;
			var lbl = (Literal)ib.NamingContainer.FindControl("ltrNomeFile");

			var cmdArgs = ib.CommandArgument.Split('$');

			var idModello = Convert.ToInt32(cmdArgs[0]);
			var nomeRiepilogo = lbl.Text.Trim() + ".pdf";
			var indiceMolteplicita = Convert.ToInt32(cmdArgs[1]);

			var riepilogo = _riepilogoModelloInHtmlFactory.FromIdDomandaOnline(IdDomanda, idModello, indiceMolteplicita);
			var result = riepilogo.ConvertiInPdf(_fileConverter, nomeRiepilogo);

			Response.Clear();
			Response.ContentType = result.MimeType;
			Response.AddHeader("content-disposition", "attachment;filename=\"" + result.FileName +"\"");
			Response.BinaryWrite(result.FileContent);
			Response.End();
		}

		protected void gvRiepiloghiDatiDinamici_RowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == "Firma")
			{
				var codiceOggetto = Convert.ToInt32(e.CommandArgument);

				this._redirectService.ToFirmaDigitale(IdDomanda, codiceOggetto);
			}
		}

		protected void gvRiepiloghiDatiDinamici_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
		{
			gvRiepiloghiDatiDinamici.EditIndex = -1;

			DataBind();
		}

		protected void gvRiepiloghiDatiDinamici_RowEditing(object sender, GridViewEditEventArgs e)
		{
			gvRiepiloghiDatiDinamici.EditIndex = e.NewEditIndex;

            DataBind();

            var fuAllegato = (FileUpload)gvRiepiloghiDatiDinamici.Rows[e.NewEditIndex].FindControl("fuAllegato");

            if (e.NewEditIndex != -1)
            {
                this.ClientScript.RegisterStartupScript(this.GetType(), "browseFile", @"triggerOpenFile('" + fuAllegato.ClientID + "');", true);
            }
		}

		protected void gvRiepiloghiDatiDinamici_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			var idModello = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IdModello"]);
			var indiceMolteplicita = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IndiceMolteplicita"]);

			ModelliDinamiciService.EliminaOggettoRiepilogo(IdDomanda, idModello, indiceMolteplicita);

			DataBind();

            gvRiepiloghiDatiDinamici_RowEditing(this, new GridViewEditEventArgs(e.RowIndex));
		}
		#endregion
	}
}
