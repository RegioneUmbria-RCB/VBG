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



namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
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


		ILog m_logger = LogManager.GetLogger(typeof(InserimentoIstanzaMaster));

		RedirectService _redirectService;

		public RedirectService Redirect { get { return _redirectService; } }



		/// <summary>
		/// Delegate invocato al momento della verifica della possibilità di uscire dalla pagina
		/// </summary>
		/// <returns>True se è possibile uscire dalla pagina corrente</returns>
		public delegate bool ValidatePageDelegate(object sender, EventArgs e);
		public event ValidatePageDelegate CanExitPage;


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
				return String.IsNullOrEmpty(stepId) ? 0 : Convert.ToInt32( stepId );
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
		public bool IgnoraSalvataggioDati{get;set;}

		/// <summary>
		/// Imposta o legge se utilizzare un titolo alternativo da mostrare nello step
		/// </summary>
		public string ForzaTitoloStep
		{
			get { object o = this.ViewState["ForzaTitoloStep"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["ForzaTitoloStep"] = value; }
		}


		
		public InserimentoIstanzaMaster():base()
		{
			this.Init += new EventHandler(InserimentoIstanzaMaster_Init);
		}

		void Page_InitComplete(object sender, EventArgs e)
		{
			_redirectService = new RedirectService(HttpContext.Current);

			if (!IsPostBack)
			{
				var proprietaStep = _workflowService.GetCurrentWorkflow().GetProprietaStep( StepId );

				foreach(var prop in proprietaStep)
				{
					var propName	= prop.Nome;
					var propValue	= prop.Valore;

					PropertyInfo pi = this.Page.GetType().GetProperty(propName);

					if (pi != null)
						pi.SetValue(this.Page, Convert.ChangeType(propValue, pi.PropertyType), null);
				}

				VerificaSeIstanzaPresentata();

				VerificaAccessoAllaPagina();
			}

		}

		private void VerificaSeIstanzaPresentata()
		{
			if (_datiDomandaFoRepository.DomandaPresentata(IdComune, IdDomanda))
			{
				m_logger.ErrorFormat("L'utente {0} sta cercando di accedere alla domanda {1} ma la domanda risulta essere già presentata (url={2})", UserAuthenticationResult.DatiUtente.Codicefiscale, IdDomanda, HttpContext.Current.Request.RawUrl);

				_invioDomandaService.MarcaDomandaComePresentata(IdDomanda);

				Redirect.RedirectToReservedAddress("~/Reserved/ErrorPages/DomandaGiaPresentata.aspx", IdComune, Software, UserToken);
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

			RedirectToStep(stepId-1, IdDomanda);
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
				var idInterventoPreselezionato = 0;

				if (!String.IsNullOrEmpty(selezionaIntervento) && Int32.TryParse(selezionaIntervento, out idInterventoPreselezionato))
				{
					selezionaIntervento = "&selezionaIntervento=" + idInterventoPreselezionato.ToString();
				}
				else
				{
					selezionaIntervento = String.Empty;
				}

				Response.Redirect(String.Format("{0}?Token={1}&Software={2}&IdComune={3}&StepId={4}&IdPresentazione={5}{6}",
													Request.CurrentExecutionFilePath,
													UserToken,
													Software,
													IdComune,
													StepId,
													nuovoId,
													selezionaIntervento));

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

				DataBind();
			}
			var titoloStep = _workflowService.GetCurrentWorkflow().GetTitoloStep(StepId);

			this.Page.Title = String.IsNullOrEmpty(ForzaTitoloStep) ? titoloStep : ForzaTitoloStep;
		}

        public int TrovaIndiceStepDaUrlParziale(string testoParziale)
        {
            return this._workflowService.GetCurrentWorkflow().TrovaIndiceStepDaUrlParziale(testoParziale);
        }

		public override void DataBind()
		{
			this.Page.Title				 = _workflowService.GetCurrentWorkflow().GetTitoloStep(StepId);
			this.ltrDescrizioneStep.Text = _workflowService.GetCurrentWorkflow().GetDescrizioneStep(StepId);

			var indice = 0;
			var titoliSteps = _workflowService.GetCurrentWorkflow().GetTitoliSteps();
			var dataSource = titoliSteps.Select(x => new
			{
				NomeStep = x,
				IndiceStep = ++indice,
				MostraLinkVaiA = (indice - 1) < StepId,
				MostraLabelStep = (indice - 1) >= StepId,
				CssClass = (indice - 1) == StepId ? "stepCorrente" : "step"
			});

			rptSteps.DataSource = dataSource;
			rptSteps.DataBind();
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

			RedirectToStep( StepId + 1, IdDomanda);
		}

		public void cmdPrevStep_Click(object sender, EventArgs e)
		{
			RedirectToStep( StepId - 1, IdDomanda);
		}

		/// <summary>
		/// Effettua il redirect ad un altro step
		/// </summary>
		/// <param name="nextStepIdx"></param>
		/// <param name="idPresentazione"></param>
		public void RedirectToStep( int nextStepIdx, int idPresentazione)
		{
			string url =  _workflowService.GetCurrentWorkflow().GetStepUrl(nextStepIdx);

			url += "?IdComune=" + IdComune + "&Token=" + UserAuthenticationResult.Token + "&Software=" + Software + "&StepId=" + nextStepIdx.ToString() + "&IdPresentazione=" + idPresentazione.ToString();

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
					catch(Exception ex)
					{
						logger.DebugFormat("Impossibile effettuare il dump dell'oggetto di sessione alla chiave {0}: {1}", key, ex.ToString());
					}
				}
			}
		}

	}
}
