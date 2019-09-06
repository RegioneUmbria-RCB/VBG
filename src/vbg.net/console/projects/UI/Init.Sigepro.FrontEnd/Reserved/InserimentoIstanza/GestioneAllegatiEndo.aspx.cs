namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiEndoprocedimenti;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati;
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;
	using Ninject;

	public partial class GestioneAllegatiEndo : IstanzeStepPage
	{
		[Inject]
		public AllegatiEndoprocedimentiService AllegatiEndoprocedimentiService { get; set; }
		[Inject]
		protected PathUtils _pathUtils { get; set; }
		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        #region Parametri letti dal file di workflow

        public bool RichiediFirmaSuAllegatiLiberi
		{
			get 
			{ 
				object o = this.ViewState["RichiediFirmaSuAllegatiLiberi"]; 
				return o == null ? false : (bool)o; 
			}

			set 
			{ 
				this.ViewState["RichiediFirmaSuAllegatiLiberi"] = value; 
			}
		}

		#endregion

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il service si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;
            Master.ResetValidatorsOnLoad = false;

			if (!IsPostBack)
			{
				DataBind();
			}
		}

		#region Ciclo di vita dello step
		public override void OnInitializeStep()
		{
			AllegatiEndoprocedimentiService.SincronizzaAllegati(IdDomanda);
		}

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.Documenti.Endo.Documenti.Count() > 0;
		}

		public override bool CanExitStep()
		{
			var listaNomiFilesNonPresenti = ReadFacade.Domanda.Documenti.Endo.GetNomiDocumentiRichiestiENonPresenti();

			Errori.AddRange(listaNomiFilesNonPresenti.Select(x => string.Format("L'allegato \"{0}\" è obbligatorio", x)));
			
			var listaFilesNonFirmati = ReadFacade.Domanda.Documenti.Endo.Documenti.Where(x => x.RichiedeFirmaDigitale && x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

			Errori.AddRange(listaFilesNonFirmati.Select(x => string.Format("L'allegato \"{0}\" deve essere firmato digitalmente", x.Descrizione)));

			return listaNomiFilesNonPresenti.Count() == 0 && listaFilesNonFirmati.Count() == 0;
		}

		#endregion

		#region binding dei dati

		public class RepeaterEndoBindingItem
		{
			public int Codice { get; set; }

			public string Descrizione { get; set; }

			public IEnumerable<GrigliaAllegatiBindingItem> Allegati { get; set; }

			public RepeaterEndoBindingItem()
			{
				this.Allegati = new List<GrigliaAllegatiBindingItem>();
			}
		}

		/*
		public class TabellaAllegatiBindingItem
		{
			public int Id { get; set; }
			public bool Richiesto { get; set; }
			public bool RichiedeFirmaDigitale { get; set; }
			public string Descrizione { get; set; }
			public bool HaLinkInformazioni { get { return !String.IsNullOrEmpty(LinkInformazioni); } }
			public string LinkInformazioni { get; set; }
			public bool HaLinkPdf { get { return !String.IsNullOrEmpty(LinkPdf); } }
			public string LinkPdf { get; set; }
			public bool HaLinkPdfCompilabile { get { return !String.IsNullOrEmpty(LinkPdfCompilabile); } }
			public string LinkPdfCompilabile { get; set; }
			public bool HaLinkRtf { get { return !String.IsNullOrEmpty(LinkRtf); } }
			public string LinkRtf { get; set; }
			public bool HaLinkDoc { get { return !String.IsNullOrEmpty(LinkDoc); } }
			public string LinkDoc { get; set; }
			public bool HaLinkOO { get { return !String.IsNullOrEmpty(LinkOO); } }
			public string LinkOO { get; set; }
			public bool HaLinkDownloadSenzaPrecompilazione { get { return !String.IsNullOrEmpty(LinkDownloadSenzaPrecompilazione); } }
			public string LinkDownloadSenzaPrecompilazione { get; set; }
			public bool HaFile { get { return this.CodiceOggetto.HasValue; } }
			public string LinkDownloadFile { get; set; }
			public string NomeFile { get; set; }
			public int? CodiceOggetto { get; set; }
			public int Ordine { get; set; }
			public string Note { get; set; }
			public bool HaNote { get { return !String.IsNullOrEmpty(this.Note); } }
			public bool FirmatoDigitalmente { get; set; }
			public bool MostraBottoneFirma { get { return this.RichiedeFirmaDigitale && this.HaFile && !this.FirmatoDigitalmente; } }
		}
		*/
		public override void DataBind()
		{
			var dataSource = new List<RepeaterEndoBindingItem>();

			foreach (var endo in ReadFacade.Domanda.Endoprocedimenti.NonAcquisiti)
			{
				var allegatiEndo = ReadFacade.Domanda.Documenti.Endo.GetByIdEndo(endo.Codice);

				if (allegatiEndo.Count() == 0)
				{
					continue;
				}

				var endoBindingItem = new RepeaterEndoBindingItem
				{
					Codice = endo.Codice,
					Descrizione = endo.Descrizione
				};

				endoBindingItem.Allegati = allegatiEndo.Select(allegatoEndo => new GrigliaAllegatiBindingItem
																				{
																					CodiceOggetto = allegatoEndo.AllegatoDellUtente == null ? (int?)null : allegatoEndo.AllegatoDellUtente.CodiceOggetto,
																					Descrizione = allegatoEndo.Descrizione,
																					Id = allegatoEndo.Id,
																					LinkDoc = GetLinkPrecompilazione(allegatoEndo, DocumentoDomanda.TipoFormatoConversione.DOC),
																					////LinkInformazioni = allegatoEndo.LinkInformazioni,
																					LinkOO = GetLinkPrecompilazione(allegatoEndo, DocumentoDomanda.TipoFormatoConversione.OPN),
																					LinkPdf = GetLinkPrecompilazione(allegatoEndo, DocumentoDomanda.TipoFormatoConversione.PDF),
																					LinkPdfCompilabile = GetLinkPrecompilazione(allegatoEndo, DocumentoDomanda.TipoFormatoConversione.PDFC),
																					LinkRtf = GetLinkPrecompilazione(allegatoEndo, DocumentoDomanda.TipoFormatoConversione.RTF),
																					LinkDownloadFile = GetLinkDownloadFile(allegatoEndo),
																					LinkDownloadSenzaPrecompilazione = GetLinkSenzaPrecompilazione(allegatoEndo),
																					NomeFile = allegatoEndo.AllegatoDellUtente == null ? string.Empty : allegatoEndo.AllegatoDellUtente.NomeFile,
																					Richiesto = allegatoEndo.Richiesto,
																					RichiedeFirmaDigitale = allegatoEndo.RichiedeFirmaDigitale,
																					Ordine = allegatoEndo.Ordine,
																					Note = allegatoEndo.Note,
																					FirmatoDigitalmente = allegatoEndo.AllegatoDellUtente == null ? false : allegatoEndo.AllegatoDellUtente.FirmatoDigitalmente
																				})
														.OrderBy(x => x.Ordine);

				dataSource.Add(endoBindingItem);
			}

			dataSource.Sort((a, b) => a.Descrizione.CompareTo(b.Descrizione));

			rptEndo.DataSource = dataSource;
			rptEndo.DataBind();

			ddlTipoAllegato.DataSource = dataSource;
			ddlTipoAllegato.DataBind();
		}

		private string GetLinkDownloadFile(DocumentoDomanda allegatoEndo)
		{
			if (allegatoEndo.AllegatoDellUtente == null)
			{
				return string.Empty;
			}

			return _pathUtils.Reserved.GetUrlMostraOggettoFo(allegatoEndo.AllegatoDellUtente.CodiceOggetto, IdDomanda);
		}

		private string GetLinkPrecompilazione(DocumentoDomanda documento, DocumentoDomanda.TipoFormatoConversione formato)
		{
			if (!documento.SupportaPrecompilazioneNelFormato(formato))
			{
				return string.Empty;
			}

			if (formato == DocumentoDomanda.TipoFormatoConversione.PDFC)
			{
				return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(documento.CodiceOggettoModello.Value, IdDomanda);
			}

			return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadOggettoCompilabile(documento.CodiceOggettoModello.Value, IdDomanda, formato.ToString());
		}

		private string GetLinkSenzaPrecompilazione(DocumentoDomanda documento)
		{
			if (!documento.CodiceOggettoModello.HasValue || documento.SupportaPrecompilazione())
			{
				return string.Empty;
			}

			return _pathUtils.GetUrlMostraOggetto(documento.CodiceOggettoModello.Value);
		}

		protected void OnItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				var grigliaAllegati = (GrigliaAllegati)e.Item.FindControl("grigliaAllegati");

				grigliaAllegati.DataSource = ((RepeaterEndoBindingItem)e.Item.DataItem).Allegati;
				grigliaAllegati.DataBind();
			}
		}

		#endregion

		#region Gestione degli eventi delle gridview

		protected void OnAllegaDocumento(object sender, AllegaDocumentoEventArgs e)
		{
			try
			{
				AllegatiEndoprocedimentiService.AggiungiAllegatoAEndo(IdDomanda, e.IdAllegato, e.File);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

        protected void ErroreGriglia(object sender, string messaggioErrore)
        {
            this.Errori.Add(messaggioErrore);
        }

		protected void OnRimuoviDocumento(object sender, RimuoviDocumentoEventArgs e)
		{
			try
			{
				AllegatiEndoprocedimentiService.EliminaOggettoUtente(IdDomanda, e.IdAllegato);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void OnCompilaDocumento(object sender, CompilaDocumentoEventArgs e)
		{
			this._redirectService.RedirectToPaginaCompilazioneOggetti(IdDomanda, e.IdAllegato, PathUtils.UrlParametersValues.TipoAllegatoEndo);
		}

		protected void OnFirmaDocumento(object sender, FirmaDocumentoEventArgs e)
		{
			this._redirectService.ToFirmaDigitale(IdDomanda, e.CodiceOggetto);
		}
		#endregion

		#region inserimento di un nuovo allegato

		protected void cmdNuovoAllegato_Click(object sender, EventArgs e)
		{
			ddlTipoAllegato.SelectedIndex = 0;
			txtDescrizioneAllegato.Value = string.Empty;

			Master.MostraPaginatoreSteps = false;
		}

		protected void cmdAggiungi_Click(object sender, EventArgs e)
		{
			try
			{
				BinaryFile file = new BinaryFile(fuUploadNuovo.PostedFile, this._validPostedFileSpecification);

				var idDomanda = this.IdDomanda;
				var descrizione = txtDescrizioneAllegato.Value;
				var tipoAllegato = Convert.ToInt32(ddlTipoAllegato.Value);
				var richiedeFirma = this.RichiediFirmaSuAllegatiLiberi;

				AllegatiEndoprocedimentiService.AggiungiAllegatoLibero(idDomanda, tipoAllegato, descrizione, file, richiedeFirma);

                ddlTipoAllegato.Inner.SelectedIndex = 0;
                txtDescrizioneAllegato.Value = String.Empty;

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}
        
		#endregion
	}
}
