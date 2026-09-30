using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneEndoPresenti_Allegati : IstanzeStepPage
    {

        public class AllegatiEndoPresentiBindingItem
        {
            public int Id { get; set; }
            public string Descrizione { get; set; }
            public bool RichiedeFirmaDigitale { get; set; }
            public bool HaFile { get { return this.CodiceOggetto.HasValue; } }
            public string LinkDownloadFile { get; set; }
            public string NomeFile { get; set; }
            public string NumeroDocumento { get; set; }
            public int? CodiceOggetto { get; set; }
            public bool MostraBottoneFirma { get { return this.RichiedeFirmaDigitale && this.CodiceOggetto.HasValue && !this.FirmatoDigitalmente; } }
            public bool FirmatoDigitalmente { get; set; }
            public bool Obbligatorio { get; set; }
            public string RiferimentiDocumento { get { return this.GetRiferimenti(); } }
            public string TipoDocumento { get; set; }
            public string DataDocumento { get; set; }
            public string DocumentoRilasciatoDa { get; set; }

            private string GetRiferimenti()
            {
                var sb = new StringBuilder(this.TipoDocumento);

                if (!String.IsNullOrEmpty(this.NumeroDocumento))
                    sb.AppendFormat(" numero {0} ", this.NumeroDocumento);

                if (!String.IsNullOrEmpty(this.DataDocumento))
                    sb.AppendFormat(" del {0} ", this.DataDocumento);

                if (!String.IsNullOrEmpty(this.DocumentoRilasciatoDa))
                    sb.AppendFormat(" rilasciato da {0} ", this.DocumentoRilasciatoDa);

                return sb.ToString();
            }
        }

        [Inject]
        public IEndoAcquisitiService _endoAcquisitiService { get; set; }
        [Inject]
        public IEndoprocedimentiService _endoService { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        public IRedirectService _redirectService { get; set; }

        #region Parametri dello step

        public bool VerificaFirmeDigitaliAllegatiSeRichiesto
        {
            get { object o = this.ViewState["VerificaFirmeDigitaliAllegatiSeRichiesto"]; return o == null ? true : (bool)o; }
            set { this.ViewState["VerificaFirmeDigitaliAllegatiSeRichiesto"] = value; }
        }

        #endregion


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        public override bool CanEnterStep()
        {
            return this.ReadFacade
                    .Domanda
                    .Endoprocedimenti
                    .Acquisiti
                    .CheRichiedonoAllegato(this._endoAcquisitiService)
                    .Count() > 0;
        }

        public override bool CanExitStep()
        {
            var filesRichiesti = this.ReadFacade
                                    .Domanda
                                    .Endoprocedimenti
                                    .Acquisiti
                                    .CheRichiedonoAllegato(this._endoAcquisitiService);


            var filesNonAllegati = filesRichiesti
                                        .ConAllegatoObbligatorio(this._endoAcquisitiService)
                                        .Where(x => x.Riferimenti.Allegato == null);

            var tuttiIFilesSonoAllegati = filesNonAllegati.Count() == 0;

            if (!tuttiIFilesSonoAllegati)
            {
                foreach (var endoSenzafile in filesNonAllegati)
                {
                    this.Errori.Add(String.Format("Per poter proseguire è necessario allegare il file comprovante il possesso del titolo: {0}", endoSenzafile.Descrizione));
                }

                return false;
            }

            var filesSenzaFirma = filesRichiesti.Where(x => x.Riferimenti.Allegato != null && x.RichiedeFirmaDigitale(this._endoAcquisitiService) && !x.Riferimenti.Allegato.FirmatoDigitalmente);

            var tuttiIFilesSonoFirmati = filesSenzaFirma.Count() == 0;

            if (this.VerificaFirmeDigitaliAllegatiSeRichiesto && !tuttiIFilesSonoFirmati)
            {
                this.Errori.AddRange(filesSenzaFirma.Select(x => String.Format("Per poter proseguire è necessario firmare digitalmente il file \"{0}\"", x.Riferimenti.Allegato.NomeFile)));

                return false;
            }

            return true;
        }

        public override void DataBind()
        {
            var allegatiEndo = from r in this.ReadFacade.Domanda.Endoprocedimenti.Acquisiti.CheRichiedonoAllegato(this._endoAcquisitiService)
                               select new AllegatiEndoPresentiBindingItem
                               {
                                   Id = r.Codice,
                                   Descrizione = r.Descrizione,
                                   RichiedeFirmaDigitale = this.VerificaFirmeDigitaliAllegatiSeRichiesto && r.RichiedeFirmaDigitale(this._endoAcquisitiService),
                                   Obbligatorio = r.HaAllegatoObbligatorio(this._endoAcquisitiService),
                                   LinkDownloadFile = String.Empty,
                                   NomeFile = r.Riferimenti.Allegato == null ? String.Empty : r.Riferimenti.Allegato.NomeFile,
                                   CodiceOggetto = r.Riferimenti.Allegato == null ? (int?)null : r.Riferimenti.Allegato.CodiceOggetto,
                                   FirmatoDigitalmente = r.Riferimenti.Allegato == null ? false : r.Riferimenti.Allegato.FirmatoDigitalmente,
                                   TipoDocumento = r.Riferimenti.TipoTitolo.Descrizione,
                                   DataDocumento = r.Riferimenti.DataAtto.HasValue ? r.Riferimenti.DataAtto.Value.ToString("dd/MM/yyyy") : String.Empty,
                                   DocumentoRilasciatoDa = r.Riferimenti.RilasciatoDa,
                                   NumeroDocumento = r.Riferimenti.NumeroAtto
                               };

            this.gvAllegati.DataSource = allegatiEndo;
            this.gvAllegati.DataBind();
        }

        protected void OnRowCommand(object sender, GridViewCommandEventArgs e)
        {
            if (e.CommandName == "Firma")
            {
                var codiceOggetto = Convert.ToInt32(e.CommandArgument);

                this._redirectService.ToFirmaDigitale(this.IdDomanda, codiceOggetto);
            }

        }

        protected void OnRowUpdating(object sender, GridViewUpdateEventArgs e)
        {
            try
            {
                var codiceInventario = Convert.ToInt32(e.Keys[0]);
                var postedFile = (FileUpload)this.gvAllegati.Rows[e.RowIndex].FindControl("EditPostedFile");
                var file = new WebFormsBinaryFile(postedFile, this._validPostedFileSpecification);

                this._endoAcquisitiService.AllegaFileAEndoAcquisito(this.IdDomanda, codiceInventario, file, false);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante il caricamento del file: " + ex.Message);
            }
        }

        protected void OnRowDeleting(object sender, GridViewDeleteEventArgs e)
        {
            this.DataBind();

            try
            {
                var codiceInventario = Convert.ToInt32(e.Keys[e.RowIndex]);

                this._endoService.RimuoviAllegatoDaEndo(this.IdDomanda, codiceInventario);

                this.DataBind();

            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante la rimozione del file: " + ex.Message);
            }
        }
    }


    public static class EndoprocedimentiExtensions
    {
        public static IEnumerable<Endoprocedimento> CheRichiedonoAllegato(this IEnumerable<Endoprocedimento> listaEndo, IEndoAcquisitiService svc)
        {
            return listaEndo.Where(endo =>
            {
                var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);
                return tipoTitolo.Flags.RichiedeAllegato;
            });
        }

        public static IEnumerable<Endoprocedimento> ConAllegatoObbligatorio(this IEnumerable<Endoprocedimento> listaEndo, IEndoAcquisitiService svc)
        {
            return listaEndo.Where(endo =>
            {
                var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);
                return tipoTitolo.Flags.AllegatoObbligatorio;
            });
        }

        public static bool RichiedeFirmaDigitale(this Endoprocedimento endo, IEndoAcquisitiService svc)
        {
            var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);

            return tipoTitolo.Flags.VerificaFirmaAllegato;
        }

        public static bool HaAllegatoObbligatorio(this Endoprocedimento endo, IEndoAcquisitiService svc)
        {
            var tipoTitolo = svc.GetTipoTitoloById(endo.Riferimenti.TipoTitolo.Codice);

            return tipoTitolo.Flags.AllegatoObbligatorio;
        }

    }
}