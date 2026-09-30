using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneAllegatiIntervento : IstanzeStepPage
    {
        [Inject]
        public AllegatiInterventoService AllegatiInterventoService { get; set; }
        [Inject]
        public PathUtils _pathUtils { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }
        [Inject]
        public IConfigurazione<ParametriDimensioneAllegatiLiberi> _parametriAllegatiLiberi { get; set; }

        [Inject]
        public IUrlDownloadOggettiService _urlDownloadOggettiService { get; set; }

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

        public bool PermettiAllegatiMultipli
        {
            get { object o = this.ViewState["PermettiAllegatiMultipili"]; return o == null ? false : (bool)o; }
            set { this.ViewState["PermettiAllegatiMultipili"] = value; }
        }

        public bool NascondiLegendaAttributi
        {
            get { return this.ltrLegendaAttributi.Visible; }
            set { this.ltrLegendaAttributi.Visible = !value; }
        }

        public bool NascondiAggiungiAllegato
        {
            get { return this.cmdNuovoAllegato.Visible; }
            set { this.cmdNuovoAllegato.Visible = !value; }
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
            this.Master.IgnoraSalvataggioDati = true;
            this.Master.ResetValidatorsOnLoad = false;

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        #region ciclo di vita dello step

        public override void OnInitializeStep()
        {
            this.AllegatiInterventoService.Sincronizza(this.IdDomanda);
        }

        public override bool CanEnterStep()
        {
            return this.ReadFacade.Domanda.Documenti.Intervento.Documenti.Any(x => !x.RiepilogoDomanda);
        }

        public override bool CanExitStep()
        {
            var listaNomiFilesNonPresenti = this.ReadFacade.Domanda.Documenti.Intervento.GetNomiDocumentiRichiestiENonPresenti();

            foreach (var file in listaNomiFilesNonPresenti)
            {
                this.Errori.Add("L'allegato \"" + file + "\" è obbligatorio");
            }

            var listaFilesNonFirmati = this.ReadFacade.Domanda.Documenti.Intervento.Documenti.Where(x => x.RichiedeFirmaDigitale && x.AllegatoDellUtente != null && !x.AllegatoDellUtente.FirmatoDigitalmente);

            foreach (var allegato in listaFilesNonFirmati)
            {
                this.Errori.Add("L'allegato \"" + allegato.Descrizione + "\" deve essere firmato digitalmente");
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
            var q = this.ReadFacade.Domanda
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
                var res = from r in this.ReadFacade.Domanda.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(categoria.Id)
                          where !r.RiepilogoDomanda
                          select new GrigliaAllegatiBindingItem
                          {
                              Id = r.Id,
                              CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
                              Descrizione = r.Descrizione.Replace("\n", "<br />"),
                              LinkDoc = this.GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.DOC),
                              LinkOO = this.GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.ODT),
                              LinkPdf = this.GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.PDF),
                              LinkPdfCompilabile = this.GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.PDFC),
                              LinkRtf = this.GetLinkPrecompilazione(r, DocumentoDomanda.TipoFormatoConversione.RTF),
                              LinkDownloadFile = this.GetLinkDownloadFile(r),
                              LinkDownloadSenzaPrecompilazione = this.GetLinkSenzaPrecompilazione(r),
                              NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
                              Richiesto = r.Richiesto,
                              RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
                              FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
                              Ordine = r.Ordine,
                              Note = r.Note,
                              SoloFirma = this.SoloFirma,
                              DimensioneMassima = r.DimensioneMassima * 1024,
                              EstensioniAmmesse = r.EstensioniAmmesse
                          };

                res = res.OrderBy(x => x.Ordine);

                categoria.Allegati.AddRange(res);
            }

            this.NumeroCategorie = q.Count();

            this.rptCategorie.DataSource = q;
            this.rptCategorie.DataBind();

            this.ddlTipoAllegato.DataSource = q;
            this.ddlTipoAllegato.DataBind();

            if (q.Where(x => x.Id == -1).Count() == 0)
            {
                this.ddlTipoAllegato.Items.Insert(0, new ListItem("Altri allegati", "-1"));
            }

            if (this.SoloFirma)
            {
                this.ltrLegendaAttributi.Visible = false;
                this.cmdNuovoAllegato.Visible = false;
            }

            this.ddlNumeroPagine.Items.AddRange(Enumerable.Range(1, 30)
                                                     .Select(x => new ListItem(x.ToString()))
                                                     .ToArray());

            this.ddlFormato.DataSource = this._parametriAllegatiLiberi.Parametri.FormatiAllegatiLiberi;
            this.ddlFormato.DataBind();
            this.ddlFormato.Items.Insert(0, new ListItem());

            this.pnlDimensioniFile.Visible = this._parametriAllegatiLiberi.Parametri.FormatiAllegatiLiberi.Length > 0;
        }

        protected void OnItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var grigliaAllegati = (GrigliaAllegati)e.Item.FindControl("grigliaAllegati");

                grigliaAllegati.PermettiAllegatiMultipili = this.PermettiAllegatiMultipli;
                grigliaAllegati.SoloFirma = this.SoloFirma;
                grigliaAllegati.DataSource = ((CategoriaBindingItem)e.Item.DataItem).Allegati.OrderBy(x => x.Ordine).ThenBy(x => x.Descrizione);
                grigliaAllegati.DataBind();
            }
        }

        private string GetLinkDownloadFile(DocumentoDomanda documento)
        {
            if (documento.AllegatoDellUtente == null)
            {
                return string.Empty;
            }

            return this._urlDownloadOggettiService.GetUrlDownload(documento.AllegatoDellUtente.CodiceOggetto);
        }

        private string GetLinkPrecompilazione(DocumentoDomanda documento, DocumentoDomanda.TipoFormatoConversione formato)
        {
            if (!documento.SupportaPrecompilazioneNelFormato(formato))
            {
                return string.Empty;
            }

            if (formato == DocumentoDomanda.TipoFormatoConversione.PDFC)
            {
                return this._urlDownloadOggettiService.GetUrlDownloadPdfCompilabile(documento.CodiceOggettoModello.Value, this.IdDomanda, this.Software);
            }


            var formatoEnum = (FormatoConversioneEnum)Enum.Parse(typeof(FormatoConversioneEnum), formato.ToString());

            return this._urlDownloadOggettiService.GetUrlDownloadCompilato(documento.CodiceOggettoModello.Value, this.IdDomanda, formatoEnum);
        }

        private string GetLinkSenzaPrecompilazione(DocumentoDomanda documento)
        {
            if (!documento.CodiceOggettoModello.HasValue || documento.SupportaPrecompilazione())
            {
                return string.Empty;
            }

            return this._urlDownloadOggettiService.GetUrlDownload(documento.CodiceOggettoModello.Value);
        }

        #endregion

        #region Gestione degli eventi delle gridview

        protected void AllegaDocumentiMultipli(object sender, AllegaDocumentiMultipliEventArgs e)
        {
            this._redirectService.ToUploadAllegatiMultipli(this.IdDomanda, "I", e.IdAllegato);
        }

        protected void OnAllegaDocumento(object sender, AllegaDocumentoEventArgs e)
        {
            try
            {
                this.AllegatiInterventoService.Salva(this.IdDomanda, e.IdAllegato, e.File);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void OnRimuoviDocumento(object sender, RimuoviDocumentoEventArgs e)
        {
            try
            {

                this.AllegatiInterventoService.EliminaOggettoUtente(this.IdDomanda, e.IdAllegato);

                // Se è un allegato libero lo elimino


                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void OnCompilaDocumento(object sender, CompilaDocumentoEventArgs e)
        {
            this._redirectService.RedirectToPaginaCompilazioneOggetti(this.IdDomanda, e.IdAllegato, PathUtils.UrlParametersValues.TipoAllegatoIntervento);
        }

        protected void OnFirmaDocumento(object sender, FirmaDocumentoEventArgs e)
        {
            this._redirectService.ToFirmaDigitale(this.IdDomanda, e.CodiceOggetto);
        }

        protected void ErroreGriglia(object sender, string messaggioErrore)
        {
            this.Errori.Add(messaggioErrore);
        }

        #endregion

        #region inserimento di un nuovo allegato
        protected void cmdNuovoAllegato_Click(object sender, EventArgs e)
        {
            if (this.ddlTipoAllegato.Items.Count > 0)
            {
                this.ddlTipoAllegato.SelectedIndex = 0;
            }

            this.txtDescrizioneAllegato.Value = String.Empty;

            this.Master.MostraPaginatoreSteps = false;
        }

        protected void cmdAggiungi_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(this.ddlTipoAllegato.Value))
            {
                this.Errori.Add("Selezionare una categoria di allegato");
                return;
            }

            if (string.IsNullOrEmpty(this.txtDescrizioneAllegato.Value))
            {
                this.Errori.Add("Specificare una descrizione per l'allegato");
                return;
            }

            try
            {
                var specification = (IValidPostedFileSpecification)this._validPostedFileSpecification;

                if (this._parametriAllegatiLiberi.Parametri.FormatiAllegatiLiberi.Length > 0)
                {
                    var formato = this._parametriAllegatiLiberi.Parametri.FormatiAllegatiLiberi.Where(x => x.Id == Convert.ToInt32(this.ddlFormato.Value)).FirstOrDefault();
                    var numeroPagine = Convert.ToInt32(this.ddlNumeroPagine.Value);
                    var dimensioneMassima = formato.DimensioneMaxPagina * 1024 * numeroPagine;

                    specification = new SizeBasedValidPostedFileSpecification(dimensioneMassima);
                }

                BinaryFile file = new WebFormsBinaryFile(this.fuUploadNuovo.PostedFile, specification);

                var idDomanda = this.IdDomanda;
                var descrizione = this.txtDescrizioneAllegato.Value;
                var tipoAllegato = Convert.ToInt32(this.ddlTipoAllegato.Value);
                var descrizioneTipoAllegto = this.ddlTipoAllegato.SelectedItem.Text;
                var richiedeFirma = this.RichiediFirmaSuAllegatiLiberi;

                this.AllegatiInterventoService.AggiungiAllegatoLibero(idDomanda, descrizione, file, tipoAllegato, descrizioneTipoAllegto, richiedeFirma);

                this.DataBind();

                this.cmdCancel_Click(this, EventArgs.Empty);
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        protected void cmdCancel_Click(object sender, EventArgs e)
        {
            this.Master.MostraPaginatoreSteps = true;
        }

        #endregion

        protected IValidPostedFileSpecification grigliaAllegati_GetValidationSpecification(object sender, int idAllegato)
        {
            var allegato = this.ReadFacade.Domanda.Documenti.Intervento.Documenti.Where(x => x.Id == idAllegato).FirstOrDefault();

            var specification = (IValidPostedFileSpecification)this._validPostedFileSpecification;

            if (allegato.DimensioneMassima > 0)
            {
                specification = new SizeBasedValidPostedFileSpecification(allegato.DimensioneMassima * 1024);
            }

            return specification.And(new EstensioneValidaPostedFileSpecification(allegato.EstensioniAmmesse));
        }
    }
}
