using System;
using System.Linq;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Services.ConversioneDomanda;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneAllegatiDatiDinamici : IstanzeStepPage
	{
		[Inject]
		public IAllegatiDomandaFoRepository _allegatiDomandaFoRepository { get; set; }

		[Inject]
		public ModelliDinamiciService ModelliDinamiciService { get; set; }

		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

		[Inject]
		public RiepilogoModelloInHtmlFactory _riepilogoModelloInHtmlFactory { get; set; }

		[Inject]
		public FileConverterService _fileConverterService { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

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
		/*
		/// <summary>
		/// Restituisce un link da cui scaricare il modello precompilato della scheda
		/// </summary>
		/// <param name="objIdModello">Id della scheda</param>
		/// <param name="objIndiceMolteplicita">indice di molteplicità della scheda</param>
		/// <returns>url da cui scaricare il modello precompilato della scheda</returns>
		protected string GetUrlDownload(int idModello, int indiceMolteplicita)
		{
			var url = GetUrlDownloadInternal(idModello, indiceMolteplicita, _configurazione.Parametri.VerificaHashFilesFirmati);

			var sb = new StringBuilder();

			sb.AppendFormat("<a target='_blank' href='{0}'><img src='", url);
			sb.Append(ResolveClientUrl("~/Images/pdf16x16.gif"));
			sb.Append("' alt='Scarica modello in PDF' align='absbottom' /></a>");

			return sb.ToString();
		}
		*/
		///// <summary>
		///// Genera l'url da cui scaricare il modello precompilato della scheda
		///// </summary>
		///// <param name="idModello"></param>
		///// <param name="indiceMolteplicita"></param>
		///// <param name="generaMd5"></param>
		///// <returns></returns>
		//protected string GetUrlDownloadInternal(int idModello, int indiceMolteplicita, bool generaMd5)
		//{
		//    var req = HttpContext.Current.Request;
		//    var downloadUrl = req.Url.Scheme + "://" + req.Url.Host + ":" + req.Url.Port;

		//    if (!String.IsNullOrEmpty(req.ApplicationPath))
		//        downloadUrl += req.ApplicationPath;

		//    if (!downloadUrl.EndsWith("/"))
		//        downloadUrl += "/";

		//    downloadUrl += "Reserved/InserimentoIstanza/GestioneDatiDinamiciStampaRiepilogo.aspx";

		//    var fmtStr = "{0}?IdComune={1}&Software={2}&Token={3}&IdPresentazione={4}&IdModello={5}&IndiceMolteplicita={6}";

		//    var url =  String.Format(fmtStr, downloadUrl, IdComune, Software, UserAuthenticationResult.Token, IdDomanda, idModello, indiceMolteplicita);

		//    if (generaMd5)
		//        url += "&md5=true";

		//    return url;
		//}

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
			var ib = (ImageButton)sender;
			var lbl = (Literal)ib.NamingContainer.FindControl("ltrNomeFile");

			var cmdArgs = ib.CommandArgument.Split('$');

			var idModello = Convert.ToInt32(cmdArgs[0]);
			var nomeRiepilogo = lbl.Text;
			var indiceMolteplicita = Convert.ToInt32(cmdArgs[1]);

			var riepilogo = _riepilogoModelloInHtmlFactory.FromIdDomanda(IdDomanda, idModello, indiceMolteplicita);
			var result = riepilogo.ConvertiInPdf(_fileConverterService, nomeRiepilogo);

			Response.Clear();
			Response.ContentType = result.MimeType;
			Response.AddHeader("content-disposition", "attachment;filename=\"" + result.FileName + ".pdf\"");
			Response.BinaryWrite(result.FileContent);
			Response.End();
		}

		protected void gvRiepiloghiDatiDinamici_RowCommand(object sender, GridViewCommandEventArgs e)
		{
			if (e.CommandName == "Firma")
			{
				var codiceOggetto = Convert.ToInt32(e.CommandArgument);

				this.Master.Redirect.ToFirmaDigitale(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, codiceOggetto);
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
		}

		protected void gvRiepiloghiDatiDinamici_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			var idModello = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IdModello"]);
			var indiceMolteplicita = Convert.ToInt32(gvRiepiloghiDatiDinamici.DataKeys[e.RowIndex]["IndiceMolteplicita"]);

			ModelliDinamiciService.EliminaOggettoRiepilogo(IdDomanda, idModello, indiceMolteplicita);

			DataBind();
		}
		#endregion
	}
}
