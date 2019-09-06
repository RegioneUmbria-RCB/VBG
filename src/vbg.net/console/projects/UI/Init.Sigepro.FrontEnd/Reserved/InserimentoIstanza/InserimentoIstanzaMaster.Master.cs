using System;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Web;
using System.Web.UI.WebControls;
using System.Xml.Serialization;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System.Collections.Generic;
using System.Text;
using System.Web.UI;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.QsParameters;

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
        public DomandeOnlineService _domandeOnlineService { get; set; }
        [Inject]
        public IWorkflowService _workflowService { get; set; }
        [Inject]
        public IDatiDomandaFoRepository _datiDomandaFoRepository { get; set; }
        [Inject]
        public InvioDomandaService _invioDomandaService { get; set; }
        [Inject]
        public RedirectService _redirectService { get; set; }

        ILog m_logger = LogManager.GetLogger(typeof(InserimentoIstanzaMaster));
        
        /// <summary>
        /// Delegate invocato al momento della verifica della possibilità di uscire dalla pagina
        /// </summary>
        /// <returns>True se è possibile uscire dalla pagina corrente</returns>
        public delegate bool ValidatePageDelegate(object sender, EventArgs e);
        public event ValidatePageDelegate CanExitPage;

        public bool MostraDescrizioneStep
        {
            get { object o = this.ViewState["MostraDescrizioneStep"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraDescrizioneStep"] = value; }
        }

        public bool ResetValidatorsOnLoad
        {
            get { return this.Master.ResetValidatorsOnLoad; }
            set { this.Master.ResetValidatorsOnLoad = value; }
        }

        public bool ClearSession
        {
            get { return !String.IsNullOrEmpty(Request.QueryString["clearSession"]); }
        }

        /// <summary>
        /// Imposta o legge l'indice dello step corrente
        /// </summary>
        //		[RegExValidate("^[0-9]{1,2}$")]
        protected int StepId
        {
            get
            {
                string stepId = Request.QueryString["StepId"];
                return String.IsNullOrEmpty(stepId) ? 0 : Convert.ToInt32(stepId);
            }
        }

        /// <summary>
        /// Imposta o legge se il paginatore degli steps è visibile
        /// </summary>
        public bool MostraPaginatoreSteps
        {
            get { return rptSteps.Visible; }
            set { rptSteps.Visible = value; }
        }

        public bool MostraBottoneAvanti
        {
            get { object o = this.ViewState["MostraBottoneAvanti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraBottoneAvanti"] = value; }
        }


        /// <summary>
        /// imposta o legge se il titolo dello step è visibile
        /// </summary>
        public bool MostraTitoloStep
        {
            get { return ltrDescrizioneStep.Visible; }
            set { ltrDescrizioneStep.Visible = value; }
        }

        /// <summary>
        /// Imposta o restituisce se la descrizione dello step è visibile
        /// </summary>
        public bool DescrizioneStepVisibile
        {
            get { return ltrDescrizioneStep.Visible; }
            set { ltrDescrizioneStep.Visible = value; }
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
            get { object o = this.ViewState["ForzaTitoloStep"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ForzaTitoloStep"] = value; }
        }



        public InserimentoIstanzaMaster() : base()
        {
            this.Init += new EventHandler(InserimentoIstanzaMaster_Init);
        }

        void Page_InitComplete(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                AssociaProprietaStepDaWorkflow();

                VerificaSeIstanzaPresentata();

                VerificaAccessoAllaPagina();
            }

        }

        public void AssociaProprietaStepDaWorkflow()
        {
            var proprietaStep = _workflowService.GetCurrentWorkflow().GetProprietaStep(StepId);

            foreach (var prop in proprietaStep)
            {
                var propName = prop.Nome;
                var propValue = prop.Valore;

                PropertyInfo pi = this.Page.GetType().GetProperty(propName);

                if (pi != null)
                    pi.SetValue(this.Page, Convert.ChangeType(propValue, pi.PropertyType), null);
            }
        }

        private void VerificaSeIstanzaPresentata()
        {
            if (_datiDomandaFoRepository.DomandaPresentata(IdComune, IdDomanda))
            {
                m_logger.ErrorFormat("L'utente {0} sta cercando di accedere alla domanda {1} ma la domanda risulta essere già presentata (url={2})", UserAuthenticationResult.DatiUtente.Codicefiscale, IdDomanda, HttpContext.Current.Request.RawUrl);

                _invioDomandaService.MarcaDomandaComePresentata(IdDomanda);

                var url = UrlBuilder.Url("~/Reserved/ErrorPages/DomandaGiaPresentata.aspx", x => {
                    x.Add(new QsAliasComune(IdComune));
                    x.Add(new QsSoftware(Software));
                });

                Response.Redirect(url);
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
            int stepId = Convert.ToInt32(lb.CommandArgument);

            RedirectToStep(stepId - 1, IdDomanda);
        }

        void InserimentoIstanzaMaster_Init(object sender, EventArgs e)
        {
            this.Page.InitComplete += new EventHandler(Page_InitComplete);
        }

        private void VerificaAccessoAllaPagina()
        {
            // Verifico se è possibile accedere alla pagina
            if (this.Page is IStepPage && !((IStepPage)this.Page).CheckIfCanEnterPage())
            {
                int nextstep = LastStep < StepId ? StepId + 1 : StepId - 1;

                m_logger.DebugFormat("La pagina {0} ha negato l'accesso allo step, l'esecuzione proseguirà allo step {1}. Step corrente {2}", this.Page.GetType(), StepId, LastStep);

                RedirectToStep(nextstep, IdDomanda);
            }
            else
            {
                LastStep = StepId;
            }
        }

        /// <summary>
        /// Imposta o legge l'indice dell'ultimo step eseguito
        /// </summary>
        public int LastStep
        {
            get
            {
                object ls = Session["LAST_STEP"];
                return ls == null ? -1 : (int)ls;
            }
            private set { Session["LAST_STEP"] = value; }
        }


        protected override void OnInit(EventArgs e)
        {
            FoKernelContainer.Inject(this);

            if (this.IdDomanda < 0)
            {
                var nuovoId = _domandeOnlineService.GetProssimoIdDomanda();
                var selezionaIntervento = Request.QueryString["SelezionaIntervento"];
                var star = Request.QueryString["star"];
                var bookmark = Request.QueryString["bookmark"];
                var copiaDa = new QsCopiaDa(Request.QueryString);
                //var idInterventoPreselezionato = 0;
                var urlBuilder = new UrlBuilder();



                Response.Redirect(urlBuilder.Build(Request.CurrentExecutionFilePath, qs =>
                {
                    qs.Add(new QsSoftware(Software));
                    qs.Add(new QsAliasComune(IdComune));
                    qs.Add(new QsStepId(StepId));
                    qs.Add(new QsIdDomandaOnline(nuovoId));

                    if (!String.IsNullOrEmpty(bookmark))
                    {
                        qs.Add("bookmark", bookmark);
                    }

                    if (!String.IsNullOrEmpty(selezionaIntervento) && Int32.TryParse(selezionaIntervento, result: out int idInterventoPreselezionato))
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
            if (!IsPostBack)
            {
                if (ClearSession)
                    Session.Clear();
            }

            BindPaginatore();

            var titoloStep = _workflowService.GetCurrentWorkflow().GetTitoloStep(StepId);

            this.Page.Title = String.IsNullOrEmpty(ForzaTitoloStep) ? titoloStep : ForzaTitoloStep;
        }

        public int TrovaIndiceStepDaUrlParziale(string testoParziale)
        {
            return this._workflowService.GetCurrentWorkflow().TrovaIndiceStepDaUrlParziale(testoParziale);
        }

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

                    if (!Enabled)
                    {
                        sb.Append("disabled ");
                    }

                    if (CurrentStep)
                    {
                        sb.Append("active ");
                    }

                    return sb.ToString();
                }
            }
        }

        private void BindPaginatore()
        {
            this.Page.Title = _workflowService.GetCurrentWorkflow().GetTitoloStep(StepId);
            this.ltrDescrizioneStep.Text = _workflowService.GetCurrentWorkflow().GetDescrizioneStep(StepId);

            var indice = 0;
            var titoliSteps = _workflowService.GetCurrentWorkflow().GetTitoliSteps();
            var dataSource = titoliSteps.Select(x => new PaginatoreItem
            {
                NomeStep = x,
                IndiceStep = ++indice,
                Enabled = (indice - 1) <= StepId,
                CurrentStep = (indice - 1) == StepId
            });

            StepsPaginatore = dataSource.ToArray();
            rptSteps.DataSource = StepsPaginatore;
            rptSteps.DataBind();

            //// Paginatore nuovo
            //rptTornaAStepPrecedente.DataSource = StepsPaginatore.Where(x => x.IndiceStep <= StepId);
            //rptTornaAStepPrecedente.DataBind();
        }

        public void RebindPaginatore()
        {
            BindPaginatore();
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

            RedirectToStep(StepId + 1, IdDomanda);
        }

        public void cmdPrevStep_Click(object sender, EventArgs e)
        {
            RedirectToStep(StepId - 1, IdDomanda);
        }

        /// <summary>
        /// Effettua il redirect ad un altro step
        /// </summary>
        /// <param name="nextStepIdx"></param>
        /// <param name="idPresentazione"></param>
        public void RedirectToStep(int nextStepIdx, int idPresentazione)
        {
            var stepUrl = _workflowService.GetCurrentWorkflow().GetStepUrl(nextStepIdx);

            var url = UrlBuilder.Url(stepUrl, x =>
            {
                x.Add(new QsAliasComune(IdComune));
                x.Add(new QsSoftware(Software));
                x.Add(new QsStepId(nextStepIdx));
                x.Add(new QsIdDomandaOnline(idPresentazione));
            });
            
            Response.Redirect(url);
        }

        /// <summary>
        /// Legge se lo step corrente è il primo step della procedura di presentazione
        /// </summary>
        /// <returns></returns>
        public bool IsFirstStep()
        {
            return _workflowService.GetCurrentWorkflow().IsFirstStep(StepId);
        }

        /// <summary>
        /// Legge se lo step corrente è l'ultimo step della procedura di presentazione
        /// </summary>
        /// <returns></returns>
        public bool IsLastStep()
        {
            return _workflowService.GetCurrentWorkflow().IsLastStep(StepId);
        }

        #region gestione della data source globale

        /// <summary>
        /// Effettua il salvataggio del viewstate e della domanda corrente
        /// </summary>
        /// <returns></returns>
        protected override object SaveViewState()
        {
            DumpSession();

            return base.SaveViewState();
        }

        #endregion

        private void DumpSession()
        {
            if (!String.IsNullOrEmpty(Request.QueryString["dumpSession"]))
            {
                var logger = LogManager.GetLogger("dumpSession");
                var outputPath = @"c:\temp\sessionDump";

                foreach (string key in Session.Keys)
                {
                    try
                    {
                        var obj = Session[key];

                        using (FileStream fs = File.Open(Path.Combine(outputPath, key + ".xml"), FileMode.Create))
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
            get { object o = this.ViewState["TestoBottoneInviaDomanda"]; return o == null ? "Invia domanda" : (string)o; }
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
