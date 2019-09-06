using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Sigepro.FrontEnd.WebControls.Visura.Controls;
using log4net;
using Ninject;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per FiltriVisuraControl.
	/// </summary>
	[ToolboxData("<{0}:FiltriVisuraControl runat=server></{0}:FiltriVisuraControl>")]
	public abstract class FiltriVisuraControlBase : System.Web.UI.WebControls.WebControl, IDatabaseSoftwareControl
	{
		ILog m_logger = LogManager.GetLogger(typeof(FiltriVisuraControl));

		[Inject]
		public IConfigurazione<ParametriVisura> _configurazione { get; set; }

		// Filtri di ricerca
        protected TextBox _annoIstanza = new TextBox();
        protected VisuraMeseControl _meseIstanza = new VisuraMeseControl();
        protected TextBox _oggetto = new TextBox();
        protected TextBox _codiceIstanza = new TextBox();
        protected TextBox _civico = new TextBox();
        protected TextBox _numAutorizzazione = new TextBox();
        protected TextBox _numProtocollo = new TextBox();
        protected ComuneLocalizzazioneControl _comuneLocalizzazione = new ComuneLocalizzazioneControl();
        protected Autocomplete _stradario = new Autocomplete();
		protected StatoIstanzaControl _statoIstanza = new StatoIstanzaControl();
        protected DateTextBox _dataProtocollo = new DateTextBox();
		protected DatiCatastaliControl _datiCatasto = new DatiCatastaliControl();
		//protected RichiedenteControl m_richiedente = new RichiedenteControl();
        protected TextBox _richiedente = new TextBox();
        protected Autocomplete _intervento = new Autocomplete();
        

        private Dictionary<int, System.Web.UI.WebControls.WebControl> m_dictionary = new Dictionary<int, System.Web.UI.WebControls.WebControl>();

		//protected virtual int ID_CODICEISTANZA { get { return 41; } }
		//protected virtual int ID_ANNOISTANZA { get { return 43; } }
		//protected virtual int ID_MESEISTANZA { get { return 44; } }
		//protected virtual int ID_OGGETTO { get { return 48; } }
		//protected virtual int ID_CIVICO { get { return 62; } }
		//protected virtual int ID_NUMEROAUTORIZZAZIONE { get { return 66; } }
		//protected virtual int ID_NUMPROTOCOLLO { get { return 59; } }
		//protected virtual int ID_STRADARIO { get { return 46; } }
		//protected virtual int ID_STATOISTANZA { get { return 45; } }
		//protected virtual int ID_DATAPROTOCOLLO { get { return 60; } }
		//protected virtual int ID_DATICATASTO { get { return 70; } }
		//protected virtual int ID_RICHIEDENTE { get { return 47; } }
		//protected virtual int ID_INTERVENTO { get { return 42; } }

		IFiltriVisuraControlProvider m_provider;

		public string IdComune
		{
			get
			{
				object o = this.ViewState["IdComune"];
				return o == null ? "" : o.ToString();
			}
			set
			{
				EnsureChildControls();
				this.ViewState["IdComune"] = value;
			}
		}

		public string Software
		{
			get
			{
				object o = this.ViewState["Software"];
				return o == null ? "" : o.ToString();
			}
			set
			{
				this.ViewState["Software"] = value;
			}
		}

		public string CodiceComune
		{
			get
			{
				object o = this.ViewState["CodiceComune"];
				return o == null ? "" : o.ToString();
			}
			set { this.ViewState["CodiceComune"] = value; }
		}

		internal FiltriVisuraControlBase(IFiltriVisuraControlProvider provider)
		{
			FoKernelContainer.Inject(this);

			m_provider = provider;

			FillDictionary();

			this.Init += new EventHandler(FiltriVisuraControl_Init);
			this.Load += new EventHandler(FiltriVisuraControl_Load);
		}

		private void FillDictionary()
		{
			// annoIstanza
			_annoIstanza.ID = "anno";
			_annoIstanza.MaxLength = 4;
            _annoIstanza.BtSize = BootstrapSize.Col3;

			// meseIstanza
			_meseIstanza.ID = "mese";
            _meseIstanza.BtSize = BootstrapSize.Col3;

			// oggetto
			_oggetto.ID = "oggetto";
			_oggetto.MaxLength = 80;
            _oggetto.BtSize = BootstrapSize.Col4;

			// civico
			_civico.ID = "civico";
			_civico.MaxLength = 8;
            _civico.BtSize = BootstrapSize.Col3;

			// codiceIstanza
			_codiceIstanza.ID = "codiceIstanza";
			_codiceIstanza.MaxLength = 15;
            _codiceIstanza.BtSize = BootstrapSize.Col3;

			// numAutorizzazione
			_numAutorizzazione.ID = "numAutorizzazione";
			_numAutorizzazione.MaxLength = 8;
            _numAutorizzazione.BtSize = BootstrapSize.Col3;

			// numProtocollo
			_numProtocollo.ID = "numProtocollo";
			_numProtocollo.MaxLength = 8;
            _numProtocollo.BtSize = BootstrapSize.Col3;

			// stradario
			_stradario.ID = "stradario";
            _stradario.CssClass = "ricerca-stradario";
            _stradario.BtSize = BootstrapSize.Col4;

            _comuneLocalizzazione.ID = "comuneLocalizzazione";
            _comuneLocalizzazione.BtSize = BootstrapSize.Col3;
            _comuneLocalizzazione.Inner.CssClass += " codice-comune";


			// statoIstanza
			_statoIstanza.ID = "statoIstanza";
            _statoIstanza.BtSize = BootstrapSize.Col3;

			// dataProtocollo
			_dataProtocollo.ID = "dataProtocollo";
            _dataProtocollo.BtSize = BootstrapSize.Col3;

			// datiCatasto
			_datiCatasto.ID = "datiCatasto";

			// richiedente
			_richiedente.ID = "richiedente";
            _richiedente.BtSize = BootstrapSize.Col3;

			// tipoIntervento
			_intervento.ID = "tipoIntervento";
            _intervento.CssClass = "ricerca-intervento";
            _intervento.BtSize = BootstrapSize.Col4;

			m_dictionary.Add( m_provider.IdCodiceIstanza, _codiceIstanza);
			m_dictionary.Add(m_provider.IdAnnoIstanza, _annoIstanza);
			m_dictionary.Add(m_provider.IdMeseIstanza, _meseIstanza);
			m_dictionary.Add(m_provider.IdOggetto, _oggetto);
			m_dictionary.Add(m_provider.IdCivico, _civico);
			m_dictionary.Add(m_provider.IdNumeroAutorizzazione, _numAutorizzazione);
			m_dictionary.Add(m_provider.IdNumProtocollo, _numProtocollo);
			m_dictionary.Add(m_provider.IdStradario, _stradario);
			m_dictionary.Add(m_provider.IdStatoIstanza, _statoIstanza);
			m_dictionary.Add(m_provider.IdDataProtocollo, _dataProtocollo);
			m_dictionary.Add(m_provider.IdDatiCatasto, _datiCatasto);
			m_dictionary.Add(m_provider.IdRichiedente, _richiedente);
			m_dictionary.Add(m_provider.IdIntervento, _intervento);
		}


		private void FiltriVisuraControl_Init(object sender, EventArgs e)
		{
			EnsureChildControls();
			//FillDictionary();

		}

		public abstract RichiestaListaPratiche GetRichiestaListaPratiche(AnagraficaUtente dettagliUtente);


		private void FiltriVisuraControl_Load(object sender, EventArgs e)
		{
			CostruisciControlli();
		}


		private void CostruisciControlli()
		{
			m_logger.Debug("Inizio creazione controlli di ricerca");

            this._comuneLocalizzazione.EnsureInitialized();

			var filtri = m_provider.GetCampiFiltro(IdComune, Software);

			this.Controls.Clear();

			for (int i = 0; i < filtri.Length; i++)
			{
				var campo = filtri[i];

				if (!m_dictionary.ContainsKey(campo.Codice))
					Debug.WriteLine("Il dizionario non contiene l'id " + campo.Codice);
				else
				{
                    var bvc = (IControlWithLabel)m_dictionary[campo.Codice];
					bvc.Label = campo.Etichetta;

					// m_logger.DebugFormat("Creato controllo -> Idcomune: {0}, Software: {1}, Descrizione: {2}, Tipo: {3}, Id: {4}",
					// 						bvc.IdComune, bvc.Software, bvc.Title, bvc.GetType(), campo.Codice);

                    if (bvc.ID == "stradario" && _comuneLocalizzazione.ContieneComuniAssociati)
                    {
                        this._comuneLocalizzazione.Label = "Comune";
                        AddInnerControl(new []{
                            this._comuneLocalizzazione,
                            bvc as Control
                        });
                    }
                    else
                    {
                        AddInnerControl(bvc as Control);
                    }                    
				}
			}

			m_logger.Debug("Fine creazione controlli di ricerca");
		}

        private void AddInnerControl(Control c)
        {
            var div = new System.Web.UI.WebControls.Panel();
            div.CssClass = "row";

            div.Controls.Add(c);

            this.Controls.Add(div);
        }

        private void AddInnerControl(IEnumerable<Control> ctrls)
        {
            var div = new System.Web.UI.WebControls.Panel();
            div.CssClass = "row";

            foreach (var c in ctrls)
                div.Controls.Add(c);

            this.Controls.Add(div);
        }

		public override void RenderBeginTag(HtmlTextWriter writer)
		{
			writer.RenderBeginTag(HtmlTextWriterTag.Fieldset);
		}


	}
}
