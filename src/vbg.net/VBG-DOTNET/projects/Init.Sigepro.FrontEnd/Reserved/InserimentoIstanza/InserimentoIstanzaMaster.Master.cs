using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda.Repositories;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.Infrastructure.PropertiesResolver;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml.Serialization;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public static class RepeaterExtensionMethods
    {
        public static Control FindControlInHeader(this Repeater repeater, string controlName)
        {
            return repeater.Controls[0].FindControl(controlName);
        }

        public static Control FindControlInFooter(this Repeater repeater, string controlName)
        {
            return repeater.Controls[repeater.Controls.Count - 1].FindControl(controlName);
        }
    }


    public partial class InserimentoIstanzaMaster : BaseAreaRiservataMaster
    {
        [Inject]
        public IWorkflowService _workflowService { get; set; }
        [Inject]
        public IDatiDomandaFoRepository _datiDomandaFoRepository { get; set; }
        [Inject]
        public InvioDomandaAreaRiservataService _invioDomandaService { get; set; }
        //[Inject]
        //public IRedirectService _redirectService { get; set; }

        [Inject]
        public StatoDomandaPresentataService _statoDomandaPresentataService { get; set; }

        private readonly ILog m_logger = LogManager.GetLogger(typeof(InserimentoIstanzaMaster));

        /// <summary>
        /// Delegate invocato al momento della verifica della possibilità di uscire dalla pagina
        /// </summary>
        /// <returns>True se è possibile uscire dalla pagina corrente</returns>
        public delegate bool ValidatePageDelegate(object sender, EventArgs e);
        public event ValidatePageDelegate CanExitPage;

        public bool MostraDescrizioneStep
        {
            get { var o = this.ViewState["MostraDescrizioneStep"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDescrizioneStep"] = value; }
        }

        public bool ResetValidatorsOnLoad
        {
            get { return this.Master.ResetValidatorsOnLoad; }
            set { this.Master.ResetValidatorsOnLoad = value; }
        }

        public bool ClearSession
        {
            get { return !String.IsNullOrEmpty(this.Request.QueryString["clearSession"]); }
        }

        /// <summary>
        /// Imposta o legge l'indice dello step corrente
        /// </summary>
        //		[RegExValidate("^[0-9]{1,2}$")]
        protected int StepId
        {
            get
            {
                var stepId = this.Request.QueryString["StepId"];
                return String.IsNullOrEmpty(stepId) ? 0 : Convert.ToInt32(stepId);
            }
        }

        /// <summary>
        /// Imposta o legge se il paginatore degli steps è visibile
        /// </summary>
        public bool MostraPaginatoreSteps
        {
            get { return this.rptSteps.Visible; }
            set { this.rptSteps.Visible = value; }
        }

        public bool MostraBottoneAvanti
        {
            get { var o = this.ViewState["MostraBottoneAvanti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraBottoneAvanti"] = value; }
        }


        /// <summary>
        /// imposta o legge se il titolo dello step è visibile
        /// </summary>
        public bool MostraTitoloStep
        {
            get { return this.ltrDescrizioneStep.Visible; }
            set { this.ltrDescrizioneStep.Visible = value; }
        }

        /// <summary>
        /// Imposta o restituisce se la descrizione dello step è visibile
        /// </summary>
        public bool DescrizioneStepVisibile
        {
            get { return this.ltrDescrizioneStep.Visible; }
            set { this.ltrDescrizioneStep.Visible = value; }
        }

        /// <summary>
        /// Imposta o legge se il salvataggio dei dati della domanda è disabilitato
        /// </summary>
        public bool IgnoraSalvataggioDati { get; set; }

        /// <summary>
        /// Imposta o legge se utilizzare un titolo alternativo da mostrare nello step
        /// </summary>
        public string ForzaTitoloStep
        {
            get { var o = this.ViewState["ForzaTitoloStep"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ForzaTitoloStep"] = value; }
        }



        public InserimentoIstanzaMaster() : base()
        {
            this.Init += new EventHandler(this.InserimentoIstanzaMaster_Init);
        }

        private void Page_InitComplete(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                this.AssociaProprietaStepDaWorkflow();

                this.VerificaSeIstanzaPresentata();

                this.VerificaAccessoAllaPagina();
            }

        }

        public void AssociaProprietaStepDaWorkflow()
        {
            var proprietaStep = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).GetProprietaStep(this.StepId);

            foreach (var prop in proprietaStep)
            {
                var propName = prop.Nome;
                var propValue = prop.Valore;

                var pi = this.Page.GetType().GetProperty(propName);

                if (pi != null)
                    pi.SetValue(this.Page, Convert.ChangeType(propValue, pi.PropertyType), null);
            }
        }

        private void VerificaSeIstanzaPresentata()
        {
            if (this._datiDomandaFoRepository.DomandaPresentata(this.IdDomanda))
            {
                this.m_logger.ErrorFormat("L'utente {0} sta cercando di accedere alla domanda {1} ma la domanda risulta essere già presentata (url={2})", this.UserAuthenticationResult.DatiUtente.Codicefiscale, this.IdDomanda, HttpContext.Current.Request.RawUrl);

                this._statoDomandaPresentataService.MarcaDomandaComePresentata(this.IdDomanda);

                var url = UrlBuilder.Url("~/Reserved/ErrorPages/DomandaGiaPresentata.aspx", x =>
                {
                    x.Add(new QsAliasComune(this.IdComune));
                    x.Add(new QsSoftware(this.Software));
                });

                this.Response.Redirect(url);
            }
        }




        /// <summary>
        /// Effettua il redirect ad una pagina diversa da quella corrente.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void RedirectTo(object sender, EventArgs e)
        {
            LinkButton lb = (LinkButton)sender;
            var stepId = Convert.ToInt32(lb.CommandArgument);

            this.RedirectToStep(stepId - 1, this.IdDomanda);
        }

        private void InserimentoIstanzaMaster_Init(object sender, EventArgs e)
        {
            this.Page.InitComplete += new EventHandler(this.Page_InitComplete);
        }

        private void VerificaAccessoAllaPagina()
        {
            // Verifico se è possibile accedere alla pagina
            if (this.Page is IStepPage && !((IStepPage)this.Page).CheckIfCanEnterPage())
            {
                var nextstep = this.LastStep < this.StepId ? this.StepId + 1 : this.StepId - 1;

                this.m_logger.DebugFormat("La pagina {0} ha negato l'accesso allo step, l'esecuzione proseguirà allo step {1}. Step corrente {2}", this.Page.GetType(), this.StepId, this.LastStep);

                this.RedirectToStep(nextstep, this.IdDomanda);
            }
            else
            {
                this.LastStep = this.StepId;
            }
        }

        /// <summary>
        /// Imposta o legge l'indice dell'ultimo step eseguito
        /// </summary>
        public int LastStep
        {
            get
            {
                var ls = this.Session["LAST_STEP"];
                return ls == null ? -1 : (int)ls;
            }
            private set { this.Session["LAST_STEP"] = value; }
        }


        protected override void OnInit(EventArgs e)
        {
            FoKernelContainer.Inject(this);

            if (this.IdDomanda < 0)
            {
                var nuovoId = this._domandeOnlineService.GetProssimoIdDomanda();
                var selezionaIntervento = this.Request.QueryString["SelezionaIntervento"];
                var star = this.Request.QueryString["star"];
                var bookmark = this.Request.QueryString["bookmark"];
                var copiaDa = new QsCopiaDa(this.Request.QueryString);
                //var idInterventoPreselezionato = 0;
                var urlBuilder = new UrlBuilder();



                this.Response.Redirect(urlBuilder.Build(this.Request.CurrentExecutionFilePath, qs =>
                {
                    qs.Add(new QsSoftware(this.Software));
                    qs.Add(new QsAliasComune(this.IdComune));
                    qs.Add(new QsStepId(this.StepId));
                    qs.Add(new QsIdDomandaOnline(nuovoId));

                    if (!String.IsNullOrEmpty(bookmark))
                    {
                        qs.Add("bookmark", bookmark);
                    }

                    if (!String.IsNullOrEmpty(selezionaIntervento) && Int32.TryParse(selezionaIntervento, result: out var idInterventoPreselezionato))
                    {
                        qs.Add("selezionaIntervento", selezionaIntervento);
                    }

                    if (!String.IsNullOrEmpty(star))
                    {
                        qs.Add("star", star);
                    }

                    if (copiaDa.HasValue)
                    {
                        qs.Add(copiaDa);
                    }
                }));

                return;
            }

            base.OnInit(e);
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
            {
                if (this.ClearSession)
                    this.Session.Clear();
            }

            this.BindPaginatore();

            var titoloStep = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).GetTitoloStep(this.StepId);

            this.Page.Title = String.IsNullOrEmpty(this.ForzaTitoloStep) ? titoloStep : this.ForzaTitoloStep;
        }

        //public int TrovaIndiceStepDaUrlParziale(string testoParziale)
        //{
        //    return this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).TrovaIndiceStepDaUrlParziale(testoParziale);
        //}

        protected IEnumerable<PaginatoreItem> StepsPaginatore { get; set; }

        public class PaginatoreItem
        {
            public string NomeStep { get; set; }
            public int IndiceStep { get; set; }
            public bool CurrentStep { get; set; }
            public bool Enabled { get; set; }
            public string CssClass
            {
                get
                {
                    var sb = new StringBuilder();

                    if (!this.Enabled)
                    {
                        sb.Append("disabled ");
                    }

                    if (this.CurrentStep)
                    {
                        sb.Append("active ");
                    }

                    return sb.ToString();
                }
            }
        }

        private void BindPaginatore()
        {
            var wf = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda);

            this.Page.Title = wf.GetTitoloStep(this.StepId);
            var descrizioneStep = wf.GetDescrizioneStep(this.StepId);

            //this.Domanda;
            //this.UserAuthenticationResult.DatiUtente.

            this.ltrDescrizioneStep.Text = new SostituisciStringaResolver(descrizioneStep).Risolvi(this);

            var indice = 0;
            var titoliSteps = wf.GetTitoliSteps();
            var dataSource = titoliSteps.Select(x => new PaginatoreItem
            {
                NomeStep = x,
                IndiceStep = ++indice,
                Enabled = (indice - 1) <= this.StepId,
                CurrentStep = (indice - 1) == this.StepId
            });

            this.StepsPaginatore = dataSource.ToArray();
            this.rptSteps.DataSource = this.StepsPaginatore;
            this.rptSteps.DataBind();

            //// Paginatore nuovo
            //rptTornaAStepPrecedente.DataSource = StepsPaginatore.Where(x => x.IndiceStep <= StepId);
            //rptTornaAStepPrecedente.DataBind();
        }

        public void RebindPaginatore()
        {
            this.BindPaginatore();
        }

        public void cmdNextStep_Click(object sender, EventArgs e)
        {
            if (this.Page is IstanzeStepPage)
                (this.Page as IstanzeStepPage).OnBeforeExitStep();

            if (this.CanExitPage == null && this.Page is IstanzeStepPage)
            {
                if (!(this.Page as IstanzeStepPage).CanExitStep())
                    return;
            }
            else
            {
                if (!CanExitPage(this, EventArgs.Empty))
                    return;
            }

            this.RedirectToStep(this.StepId + 1, this.IdDomanda);
        }

        public void cmdPrevStep_Click(object sender, EventArgs e)
        {
            this.RedirectToStep(this.StepId - 1, this.IdDomanda);
        }

        /// <summary>
        /// Effettua il redirect ad un altro step
        /// </summary>
        /// <param name="nextStepIdx"></param>
        /// <param name="idPresentazione"></param>
        public void RedirectToStep(int nextStepIdx, int idPresentazione)
        {
            var stepUrl = this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).GetStepUrl(nextStepIdx);

            var url = UrlBuilder.Url(stepUrl, x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
                x.Add(new QsStepId(nextStepIdx));
                x.Add(new QsIdDomandaOnline(idPresentazione));
            });

            this.Response.Redirect(url);
        }

        /// <summary>
        /// Legge se lo step corrente è il primo step della procedura di presentazione
        /// </summary>
        /// <returns></returns>
        public bool IsFirstStep()
        {
            return this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).IsFirstStep(this.StepId);
        }

        /// <summary>
        /// Legge se lo step corrente è l'ultimo step della procedura di presentazione
        /// </summary>
        /// <returns></returns>
        public bool IsLastStep()
        {
            return this._workflowService.GetWorkflowByIdDomanda(this.IdDomanda).IsLastStep(this.StepId);
        }

        #region gestione della data source globale

        /// <summary>
        /// Effettua il salvataggio del viewstate e della domanda corrente
        /// </summary>
        /// <returns></returns>
        protected override object SaveViewState()
        {
            this.DumpSession();

            return base.SaveViewState();
        }

        #endregion

        private void DumpSession()
        {
            if (!String.IsNullOrEmpty(this.Request.QueryString["dumpSession"]))
            {
                var logger = LogManager.GetLogger("dumpSession");
                var outputPath = @"c:\temp\sessionDump";

                foreach (string key in this.Session.Keys)
                {
                    try
                    {
                        var obj = this.Session[key];

                        using (var fs = File.Open(Path.Combine(outputPath, key + ".xml"), FileMode.Create))
                        {
                            XmlSerializer xs = new XmlSerializer(obj.GetType());
                            xs.Serialize(fs, obj);

                            logger.DebugFormat("Oggetto di sessione alla chiave {0}, dimensione {1} b", key, fs.Length);

                        }

                    }
                    catch (Exception ex)
                    {
                        logger.DebugFormat("Impossibile effettuare il dump dell'oggetto di sessione alla chiave {0}: {1}", key, ex.ToString());
                    }
                }
            }
        }


        #region Bottone invio domanda

        public class PagerButtons
        {
            public readonly LinkButton BottoneAvanti;
            public readonly LinkButton BottoneIndietro;
            public readonly LinkButton BottoneInviaDomanda;

            public PagerButtons(LinkButton bottoneAvanti, LinkButton bottoneIndietro, LinkButton bottoneInviaDomanda)
            {
                this.BottoneAvanti = bottoneAvanti;
                this.BottoneIndietro = bottoneIndietro;
                this.BottoneInviaDomanda = bottoneInviaDomanda;
            }
        }

        public PagerButtons BottoniPaginatore
        {
            get
            {
                var bottoneAvanti = (LinkButton)this.rptSteps.FindControlInFooter("cmdNextStep");
                var bottoneIndietro = (LinkButton)this.rptSteps.FindControlInHeader("cmdPrevStep");
                var bottoneInviaDomanda = (LinkButton)this.rptSteps.FindControlInFooter("cmdInviaDomanda");

                return new PagerButtons(bottoneAvanti, bottoneIndietro, bottoneInviaDomanda);
            }
        }

        public bool MostraBottoneInviaDomanda { get; set; }
        public event EventHandler InviaDomanda;

        public string TestoBottoneInviaDomanda
        {
            get { var o = this.ViewState["TestoBottoneInviaDomanda"]; return o == null ? "Invia domanda" : (string)o; }
            set { this.ViewState["TestoBottoneInviaDomanda"] = value; }
        }


        protected void cmdInviaDomanda_Click(object sender, EventArgs e)
        {
            if (this.InviaDomanda != null)
            {
                this.InviaDomanda(sender, e);
            }
        }

        #endregion
    }
}
