using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Livorno.PortaleCittadino;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati;
using Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Allegati.EventArguments;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public static class GrigliaAllegatiExtensions
    {
        public static bool Bind(this GrigliaAllegati grid, IDomandaOnlineReadInterface readFacade, int idTipoAllegato)
        {
            var res = from r in readFacade.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(idTipoAllegato)
                      where !r.RiepilogoDomanda
                      select new GrigliaAllegatiBindingItem
                      {
                          Id = r.Id,
                          CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
                          Descrizione = r.Descrizione.Replace("\n", "<br />"),
                          LinkDoc = String.Empty,
                          LinkOO = String.Empty,
                          LinkPdf = String.Empty,
                          LinkPdfCompilabile = String.Empty,
                          LinkRtf = String.Empty,
                          LinkDownloadFile = r.LinkInformazioni,
                          LinkDownloadSenzaPrecompilazione = r.LinkInformazioni,
                          NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
                          Richiesto = r.Richiesto,
                          RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
                          FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
                          Ordine = r.Ordine,
                          Note = r.Note,
                          SoloFirma = false
                      };

            grid.DataSource = res;
            grid.DataBind();

            return res.Count() > 0;
        }
    }

    public partial class LivornoSchedaPortaleCittadino : IstanzeStepPage
    {
        [Inject]
        public IPortaleCittadinoService PortaleCittadinoService { get; set; }

        [Inject]
        public AllegatiInterventoService AllegatiInterventoService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        public bool ModelliPresenti
        {
            get { object o = this.ViewState["ModelliPresenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ModelliPresenti"] = value; }
        }

        public bool AllegatiPresenti
        {
            get { object o = this.ViewState["AllegatiPresenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["AllegatiPresenti"] = value; }
        }

        public bool AllegatiLiberiPresenti
        {
            get { object o = this.ViewState["AllegatiLiberiPresenti"]; return o == null ? false : (bool)o; }
            set { this.ViewState["AllegatiLiberiPresenti"] = value; }
        }
        

        public string TitoloLinkScheda
        {
            get { return hlVisualizzaScheda.Text; }
            set { hlVisualizzaScheda.Text = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            var codiceIntervento = ReadFacade.Domanda.AltriDati.Intervento.Codice;

            var scheda = this.PortaleCittadinoService.GetSchedaDaIdIntervento(codiceIntervento);

            this.Master.ForzaTitoloStep = scheda.Titolo;

            this.hlVisualizzaScheda.NavigateUrl = scheda.Link;
            this.hlVisualizzaScheda.Visible = !String.IsNullOrEmpty(scheda.Link);

            //var res = from r in ReadFacade.Domanda.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(PortaleCittadinoConstants.CategorieFiles.Modello)
            //          where !r.RiepilogoDomanda
            //          select new GrigliaAllegatiBindingItem
            //          {
            //              Id = r.Id,
            //              CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
            //              Descrizione = r.Descrizione.Replace("\n", "<br />"),
            //              LinkDoc = String.Empty,
            //              LinkOO = String.Empty,
            //              LinkPdf = String.Empty,
            //              LinkPdfCompilabile = String.Empty,
            //              LinkRtf = String.Empty,
            //              LinkDownloadFile = r.LinkInformazioni,
            //              LinkDownloadSenzaPrecompilazione = r.LinkInformazioni,
            //              NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
            //              Richiesto = r.Richiesto,
            //              RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
            //              FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
            //              Ordine = r.Ordine,
            //              Note = r.Note,
            //              SoloFirma = false
            //          };

            //this.ModelliPresenti = res.Count() > 0;

            //this.grigliaModelli.DataSource = res;
            //this.grigliaModelli.DataBind();


            //res = from r in ReadFacade.Domanda.Documenti.Intervento.GetByIdCategoriaNoDatiDinamici(PortaleCittadinoConstants.CategorieFiles.Allegato)
            //      where !r.RiepilogoDomanda
            //      select new GrigliaAllegatiBindingItem
            //      {
            //          Id = r.Id,
            //          CodiceOggetto = r.AllegatoDellUtente == null ? (int?)null : r.AllegatoDellUtente.CodiceOggetto,
            //          Descrizione = r.Descrizione.Replace("\n", "<br />"),
            //          LinkDoc = String.Empty,
            //          LinkOO = String.Empty,
            //          LinkPdf = String.Empty,
            //          LinkPdfCompilabile = String.Empty,
            //          LinkRtf = String.Empty,
            //          LinkDownloadFile = r.LinkInformazioni,
            //          LinkDownloadSenzaPrecompilazione = r.LinkInformazioni,
            //          NomeFile = r.AllegatoDellUtente == null ? string.Empty : r.AllegatoDellUtente.NomeFile,
            //          Richiesto = r.Richiesto,
            //          RichiedeFirmaDigitale = r.RichiedeFirmaDigitale,
            //          FirmatoDigitalmente = r.AllegatoDellUtente == null ? false : r.AllegatoDellUtente.FirmatoDigitalmente,
            //          Ordine = r.Ordine,
            //          Note = r.Note,
            //          SoloFirma = false
            //      };

            //this.AllegatiPresenti = res.Count() > 0;

            //this.grigliaAllegati.DataSource = res;
            //this.grigliaAllegati.DataBind();

            this.ModelliPresenti = this.grigliaModelli.Bind(ReadFacade.Domanda, PortaleCittadinoConstants.CategorieFiles.Modello);
            this.AllegatiPresenti = this.grigliaAllegati.Bind(ReadFacade.Domanda, PortaleCittadinoConstants.CategorieFiles.Allegato);
            this.AllegatiLiberiPresenti = this.grigliaAllegatiLiberi.Bind(ReadFacade.Domanda, PortaleCittadinoConstants.CategorieFiles.AllegatoLibero);

        }

        public override void OnInitializeStep()
        {
            this.PortaleCittadinoService.SincronizzaAllegati(IdDomanda);
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

            var modelliCaricati = ReadFacade.Domanda
                                                    .Documenti
                                                    .Intervento
                                                    .GetByIdCategoriaNoDatiDinamici(PortaleCittadinoConstants.CategorieFiles.Modello)
                                                    .Where(x => x.AllegatoDellUtente != null);

            if (modelliCaricati.Count() == 0)
            {
                Errori.Add("Per proseguire è necessario caricare almeno un modello");
            }

            if (modelliCaricati.Count() > 1)
            {
                Errori.Add("Per proseguire è necessario un solo modello");
            }

            return (modelliCaricati.Count() == 1) && listaNomiFilesNonPresenti.Count() == 0 && listaFilesNonFirmati.Count() == 0;
        }

        protected void grigliaModelli_AllegaDocumento(object sender, AllegaDocumentoEventArgs e)
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

        protected void grigliaModelli_FirmaDocumento(object sender, FirmaDocumentoEventArgs e)
        {
            this._redirectService.ToFirmaDigitale(IdDomanda, e.CodiceOggetto);
        }

        protected void grigliaModelli_RimuoviDocumento(object sender, RimuoviDocumentoEventArgs e)
        {
            try
            {

                AllegatiInterventoService.EliminaOggettoUtente(IdDomanda, e.IdAllegato);

                // Se è un allegato libero lo elimino


                DataBind();
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }

        protected void grigliaModelli_Errore(object sender, string messaggioErrore)
        {
            this.Errori.Add(messaggioErrore);
        }

        protected void cmdAggiungiAllegato_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(txtDescrizioneAllegato.Value))
            {
                Errori.Add("Specificare una descrizione per l'allegato");
                return;
            }

            try
            {
                var file = new BinaryFile(fuUploadNuovo, this._validPostedFileSpecification);

                var idDomanda = this.IdDomanda;
                var descrizione = txtDescrizioneAllegato.Value;
                var tipoAllegato = PortaleCittadinoConstants.CategorieFiles.AllegatoLibero;
                var descrizioneTipoAllegto = "Allegato libero";
                var richiedeFirma = false;

                AllegatiInterventoService.AggiungiAllegatoLibero(idDomanda, descrizione, file, tipoAllegato, descrizioneTipoAllegto, richiedeFirma);

                DataBind();
            }
            catch (Exception ex)
            {
                Errori.Add(ex.Message);
            }
        }
    }
}