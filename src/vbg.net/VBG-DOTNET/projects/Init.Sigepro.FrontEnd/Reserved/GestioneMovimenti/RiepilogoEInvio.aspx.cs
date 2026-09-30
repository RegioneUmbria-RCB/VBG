using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneMovimento.GestioneMovimentoDaEffettuare;
using Init.Sigepro.FrontEnd.GestioneMovimenti.GestioneWorkflowMovimento;
using Init.Sigepro.FrontEnd.GestioneMovimenti.ViewModels;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using log4net;
using Ninject;
using System;
using System.Linq;
using System.Threading;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti
{
    public partial class RiepilogoEInvio : MovimentiBasePage
    {
        [Inject]
        protected RiepilogoMovimentoDaEffettuareViewModel _viewModel { get; set; }
        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }
        [Inject]
        protected IConfigurazione<ParametriIntegrazioniDocumentali> _parametriIntegrazione { get; set; }
        [Inject]
        protected IScrivaniaEntiTerziService _scrivaniaEntiTerziService { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(RiepilogoEInvio));


        protected bool MostraBottoniAllegato = false;
        protected bool SostituzioniDocumentaliPresenti = false;

        protected MovimentoDaEffettuare MovimentoDaEffettuare { get; set; }

        protected bool PermettiModificaNote
        {
            get { return !this._parametriIntegrazione.Parametri.MovimentoDaEffettuare.InibisciNoteMovimento; }
        }



        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            this.MovimentoDaEffettuare = this._viewModel.GetMovimentoDaEffettuare();
            this.Title = this.MovimentoDaEffettuare.NomeAttivita;


            this.rptSchedeCompilate.DataSource = this._viewModel.GetListaSchedeCompilate();
            this.rptSchedeCompilate.DataBind();

            var sostituzioniDocumentali = this._viewModel.GetListaSostituzioni();

            this.rptSostituzioniDocumentali.DataSource = sostituzioniDocumentali;
            this.rptSostituzioniDocumentali.DataBind();

            this.SostituzioniDocumentaliPresenti = sostituzioniDocumentali.Count() > 0;

            base.DataBind();
        }

        protected void cmdConferma_Click(object sender, EventArgs e)
        {
            try
            {
                try
                {
                    var erroriValidazione = this._viewModel.ValidaPerInvio();

                    if (erroriValidazione.Count() > 0)
                    {
                        this.Errori.AddRange(erroriValidazione);

                        this.DataBind();

                        return;
                    }

                    this._viewModel.Invia();
                }
                catch (Exception ex)
                {
                    this.Errori.Add("Si è verificato un errore durante la trasmissione dei dati al comune: " + ex.Message);

                    // Forzo il rebind per evitare errori nella visualizzazione del riepilogo
                    this.DataBind();

                    return;
                }

                var isScrivaniaEntiTerzi = this._scrivaniaEntiTerziService.ModuloAttivo(this.Software);
                var redirUrl = "";
                var uuidIstanza = this._viewModel.GetUUidIstanza();

                if (isScrivaniaEntiTerzi)
                {
                    // redirect al dettaglio pratica della scrivania enti terzi
                    redirUrl = UrlBuilder.Url("~/reserved/enti-terzi/et-dettaglio-pratica.aspx", qs =>
                    {
                        qs.Add(new QsAliasComune(this.IdComune));
                        qs.Add(new QsSoftware(this.Software));
                        qs.Add(new QsUuidIstanza(uuidIstanza));
                    });
                }
                else
                {
                    redirUrl = UrlBuilder.Url("~/Reserved/GestioneMovimenti/DatiInviatiConSuccesso.aspx", mp =>
                    {
                        mp.Add(new QsAliasComune(this.IdComune));
                        mp.Add(new QsSoftware(this.Software));
                        mp.Add(new QsUuidIstanza(uuidIstanza));
                    });
                }

                this.Response.Redirect(redirUrl);

                return;

                //var returnTo = this.Session[RedirConstants.ReturnToSessionKey]?.ToString();

                //if (!String.IsNullOrEmpty(returnTo))
                //{
                //    this.Session.Remove(RedirConstants.ReturnToSessionKey);

                //    this.Response.Redirect(returnTo);

                //    return;
                //}
                /*
                var uuidIstanza = this._viewModel.GetUUidIstanza();
                var redirUrl = UrlBuilder.Url("~/Reserved/DettaglioIstanzaEx.aspx", mp =>
                {
                    mp.Add(new QsAliasComune(this.IdComune));
                    mp.Add(new QsSoftware(this.Software));
                    mp.Add(new QsUuidIstanza(uuidIstanza));
                    mp.Add("movimento", this._viewModel.GetMovimentoDaEffettuare().Id);
                });

                this.Response.Redirect(redirUrl);
                */
                //this.GoToNextStep();
            }
            catch (ThreadAbortException)
            {

            }
            catch (Exception ex)
            {
                this._log.Error($"Errore nel redirect alla pagina di visura {ex}. L'utente verrà reindirizzato verso il messaggio di successo.");

                this.GoToNextStep();
            }
        }


        protected void cmdSalvaNote_Clinck(object sender, EventArgs e)
        {
            try
            {
                var note = this.txtNote.Text;

                this._viewModel.AggiornaNoteMovimento(note);

                this.DataBind();
            }
            catch (Exception ex)
            {
                this.Errori.Add("Si è verificato un errore durante l'aggiornamento delle note: " + ex.Message);
            }
        }

        protected void cmdTornaIndietro_Click(object sender, EventArgs e)
        {
            this.GoToPreviousStep();
        }

        protected override IStepViewModel GetViewmodel()
        {
            return this._viewModel;
        }
    }
}