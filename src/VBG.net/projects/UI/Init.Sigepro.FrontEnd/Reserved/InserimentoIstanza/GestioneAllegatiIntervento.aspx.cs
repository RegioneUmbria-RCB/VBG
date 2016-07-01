namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
	using System.Collections.Generic;
	using System.Data;
	using System.Linq;
	using System.Web.UI.WebControls;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
	using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
	using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati;
	using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;
	using Ninject;

	public partial class GestioneAllegatiIntervento : IstanzeStepPage
	{
		[Inject]
		public AllegatiInterventoService AllegatiInterventoService { get; set; }

		[Inject]
		public PathUtils _pathUtils { get; set; }

		[Inject]
		public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

		#region Parametri letti dal file di workflow

		public bool SoloFirma
		{
			get { object o = this.ViewState["SoloFirma"]; return o == null ? false : (bool)o; }
			set { this.ViewState["SoloFirma"] = value; }
		}


		public bool RichiediFirmaSuAllegatiLiberi
		{
			get
			{
				var o = this.ViewState["RichiediFirmaSuAllegatiLiberi"];
				return o == null ? false : (bool)o;
			}

			set
			{
				this.ViewState["RichiediFirmaSuAllegatiLiberi"] = value;
			}
		}

		#endregion

		public class DatiCategoriaAllegato
		{
			public int Id { get; set; }

			public string Descrizione { get; set; }

			public int Ordine { get; set; }

			public DataTable DataTable { get; set; }
		}

		public int NumeroCategorie
		{
			get 
			{ 
				object o = this.ViewState["NumeroCategorie"]; 
				return o == null ? 0 : (int)o; 
			}

			set 
			{ 
				this.ViewState["NumeroCategorie"] = value; 
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			Master.IgnoraSalvataggioDati = true;

			if (!this.IsPostBack)
			{
				this.DataBind();
			}
		}

		#region ciclo di vita dello step

		public override void OnInitializeStep()
		{
			AllegatiInterventoService.Sincronizza(IdDomanda);
		}

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.Documenti.Intervento.Documenti.Where(x => !x.RiepilogoDomanda).Count() > 0;
		}

		public override bool CanExitStep()
		{
			var listaNomiFilesNonPresenti = ReadFacade.Domanda.Documenti.Intervento.GetNomiDocumentiRichiestiENonPresenti();

			foreach (var file in listaNomiFilesNonPresenti)
			{
				Errori.Add("L'allegato \"" + file + "\" è obbligatorio");
			}

			var listaFilesNonFirmati = ReadFacade.Domanda.Documenti.Intervento.Documenti.Where(x => x.RichiedeFirmaDigitale && x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

			foreach (var allegato in listaFilesNonFirmati)
			{
				Errori.Add("L'allegato \"" + allegato.Descrizione + "\" deve essere firmato digitalmente");
			}

			return listaNomiFilesNonPresenti.Count() == 0 && listaFilesNonFirmati.Count() == 0;
		}

		#endregion

		#region binding dei dati

		public class CategoriaBindingItem
		{
			public int Id { get; set; }

			public string Descrizione { get; set; }

			public List<GrigliaAllegatiBindingItem> Allegati { get; set; }

			public CategoriaBindingItem()
			{
				this.Allegati = new List<GrigliaAllegatiBindingItem>();
			}
		}

		public override void DataBind()
		{
			var q = ReadFacade.Domanda
							  .Documenti
							  .Intervento
							  .GetListaCategorie()
							  .Select(r => new CategoriaBindingItem
							  {
								  Id = r.Codice,
								  Descrizione = r.Descrizione
							  }).ToList();

			foreach (var categoria in q)
			{
				var res = from r in ReadFacade.Domanda.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(categoria.Id)
						  where !r.RiepilogoDomanda
						  select new GrigliaAllegatiBindingItem
						  {
							  Id = r.Id,
							  CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
							  Descrizione = r.Descrizione.Replace("\n", "<br />"),
							  LinkDoc = GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.DOC),
							  LinkOO = GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.OPN),
							  LinkPdf = GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.PDF),
							  LinkPdfCompilabile = GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.PDFC),
							  LinkRtf = GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.RTF),
							  LinkDownloadFile = GetLinkDownloadFile(r),
							  LinkDownloadSenzaPrecompilazione = GetLinkSenzaPrecompilazione(r),
							  NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
							  Richiesto = r.Richiesto,
							  RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
							  FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
							  Ordine = r.Ordine,
							  Note = r.Note,
							  SoloFirma = this.SoloFirma
						  };

				categoria.Allegati.AddRange(res);
			}

			NumeroCategorie = q.Count();

			rptCategorie.DataSource = q;
			rptCategorie.DataBind();

			ddlTipoAllegato.Item.DataSource = q;
			ddlTipoAllegato.Item.DataBind();

			if (q.Where(x => x.Id == -1).Count() == 0)
			{
				ddlTipoAllegato.Item.Items.Insert(0, new ListItem("Altri allegati", "-1"));
			}

			if (this.SoloFirma)
			{
				this.ltrLegendaAttributi.Visible = false;
				this.cmdNuovoAllegato.Visible = false;
			}
		}

		protected void OnItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				var grigliaAllegati = (GrigliaAllegati)e.Item.FindControl("grigliaAllegati");

				grigliaAllegati.SoloFirma = this.SoloFirma;
				grigliaAllegati.DataSource = ((CategoriaBindingItem)e.Item.DataItem).Allegati.OrderBy( x => x.Descrizione);
				grigliaAllegati.DataBind();
			}
		}

		private string GetLinkDownloadFile(DocumentoDomanda documento)
		{
			if (documento.AllegatoDellUtente == null)
			{
				return string.Empty;
			}

			return _pathUtils.Reserved.GetUrlMostraOggettoFo(IdComune, UserAuthenticationResult.Token, Software, documento.AllegatoDellUtente.CodiceOggetto, IdDomanda);
		}

		private string GetLinkPrecompilazione(DocumentoDomanda documento, DocumentoDomanda.TipoFormatoConversione formato)
		{
			if (!documento.SupportaPrecompilazioneNelFormato(formato))
			{
				return string.Empty;
			}

			if (formato == DocumentoDomanda.TipoFormatoConversione.PDFC)
			{
				return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadPdfCompilabile(IdComune, UserAuthenticationResult.Token, Software, documento.CodiceOggettoModello.Value, IdDomanda);
			}

			return _pathUtils.Reserved.InserimentoIstanza.GetUrlDownloadOggettoCompilabile(IdComune, UserAuthenticationResult.Token, Software, documento.CodiceOggettoModello.Value, IdDomanda, formato.ToString());
		}

		private string GetLinkSenzaPrecompilazione(DocumentoDomanda documento)
		{
			if (!documento.CodiceOggettoModello.HasValue || documento.SupportaPrecompilazione())
			{
				return string.Empty;
			}

			return _pathUtils.GetUrlMostraOggetto(IdComune, documento.CodiceOggettoModello.Value);
		}

		#endregion

		#region Gestione degli eventi delle gridview

		protected void OnAllegaDocumento(object sender, AllegaDocumentoEventArgs e)
		{
			try
			{
				AllegatiInterventoService.Salva(IdDomanda, e.IdAllegato, e.File);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void OnRimuoviDocumento(object sender, RimuoviDocumentoEventArgs e)
		{
			try
			{
				AllegatiInterventoService.EliminaOggettoUtente(IdDomanda, e.IdAllegato);

				DataBind();
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void OnCompilaDocumento(object sender, CompilaDocumentoEventArgs e)
		{
			this.Master.Redirect.RedirectToPaginaCompilazioneOggetti(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, e.IdAllegato, PathUtils.UrlParametersValues.TipoAllegatoIntervento);
		}

		protected void OnFirmaDocumento(object sender, FirmaDocumentoEventArgs e)
		{
			this.Master.Redirect.ToFirmaDigitale(IdComune, Software, UserAuthenticationResult.Token, IdDomanda, e.CodiceOggetto);
		}

        protected void ErroreGriglia(object sender, string messaggioErrore)
        {
            this.Errori.Add(messaggioErrore);
        }

		#endregion

		#region inserimento di un nuovo allegato
		protected void cmdNuovoAllegato_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 1;

			if (ddlTipoAllegato.Item.Items.Count > 0)
			{
				ddlTipoAllegato.Item.SelectedIndex = 0;
			}

			txtDescrizioneAllegato.Value = String.Empty;

			Master.MostraPaginatoreSteps = false;
		}

		protected void cmdAggiungi_Click(object sender, EventArgs e)
		{
			if (string.IsNullOrEmpty(ddlTipoAllegato.Value))
			{
				Errori.Add("Selezionare una categoria di allegato");
				return;
			}

			if (string.IsNullOrEmpty(txtDescrizioneAllegato.Value))
			{
				Errori.Add("Specificare una descrizione per l'allegato");
				return;
			}

			try
			{
				BinaryFile file = new BinaryFile(fuUploadNuovo, this._validPostedFileSpecification);

				var idDomanda = this.IdDomanda;
				var descrizione = txtDescrizioneAllegato.Value;
				var tipoAllegato = Convert.ToInt32(ddlTipoAllegato.Value);
				var descrizioneTipoAllegto = ddlTipoAllegato.Item.SelectedItem.Text;
				var richiedeFirma = this.RichiediFirmaSuAllegatiLiberi;

				AllegatiInterventoService.AggiungiAllegatoLibero(idDomanda, descrizione, file, tipoAllegato, descrizioneTipoAllegto, richiedeFirma);

				DataBind();

				cmdCancel_Click(this, EventArgs.Empty);
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}

		protected void cmdCancel_Click(object sender, EventArgs e)
		{
			Master.MostraPaginatoreSteps = true;
			multiView.ActiveViewIndex = 0;
		}
		#endregion
	}
}
