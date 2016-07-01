using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Sigepro.FrontEnd.WebControls.Visura.Controls;
using log4net;
using Ninject;


namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	/// <summary>
	/// Descrizione di riepilogo per FiltriVisuraControl.
	/// </summary>
	[ToolboxData("<{0}:FiltriVisuraControl runat=server></{0}:FiltriVisuraControl>")]
	public abstract class FiltriVisuraControlBase : WebControl, IDatabaseSoftwareControl
	{
		ILog m_logger = LogManager.GetLogger(typeof(FiltriVisuraControl));

		[Inject]
		public IConfigurazione<ParametriVisura> _configurazione { get; set; }

		// Filtri di ricerca
		protected VisuraTextBoxControl m_annoIstanza = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_meseIstanza = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_oggetto = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_codiceIstanza = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_civico = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_numAutorizzazione = new VisuraTextBoxControl();
		protected VisuraTextBoxControl m_numProtocollo = new VisuraTextBoxControl();
		protected StradarioControl m_stradario = new StradarioControl();
		protected StatoIstanzaControl m_statoIstanza = new StatoIstanzaControl();
		protected DateControl m_dataProtocollo = new DateControl();
		protected DatiCatastaliControl m_datiCatasto = new DatiCatastaliControl();
		protected RichiedenteControl m_richiedente = new RichiedenteControl();
		protected AlberoProcControl m_intervento = new AlberoProcControl();

		private Dictionary<int, BaseVisuraControl> m_dictionary = new Dictionary<int, BaseVisuraControl>();

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
			m_annoIstanza.ID = "anno";
			m_annoIstanza.Columns = 5;
			m_annoIstanza.MaxLength = 4;

			// meseIstanza
			m_meseIstanza.ID = "mese";
			m_meseIstanza.Columns = 3;
			m_meseIstanza.MaxLength = 2;

			// oggetto
			m_oggetto.ID = "oggetto";
			m_oggetto.Columns = 80;
			m_oggetto.MaxLength = 80;

			// civico
			m_civico.ID = "civico";
			m_civico.Columns = 8;
			m_civico.MaxLength = 8;

			// codiceIstanza
			m_codiceIstanza.ID = "codiceIstanza";
			m_codiceIstanza.Columns = 15;
			m_codiceIstanza.MaxLength = 15;

			// numAutorizzazione
			m_numAutorizzazione.ID = "numAutorizzazione";
			m_numAutorizzazione.Columns = 8;
			m_numAutorizzazione.MaxLength = 8;

			// numProtocollo
			m_numProtocollo.ID = "numProtocollo";
			m_numProtocollo.Columns = 8;
			m_numProtocollo.MaxLength = 8;

			// stradario
			m_stradario.ID = "stradario";

			// statoIstanza
			m_statoIstanza.ID = "statoIstanza";

			// dataProtocollo
			m_dataProtocollo.ID = "dataProtocollo";

			// datiCatasto
			m_datiCatasto.ID = "datiCatasto";

			// richiedente
			m_richiedente.ID = "richiedente";

			// tipoIntervento
			m_intervento.ID = "tipoIntervento";

			m_dictionary.Add( m_provider.IdCodiceIstanza, m_codiceIstanza);
			m_dictionary.Add(m_provider.IdAnnoIstanza, m_annoIstanza);
			m_dictionary.Add(m_provider.IdMeseIstanza, m_meseIstanza);
			m_dictionary.Add(m_provider.IdOggetto, m_oggetto);
			m_dictionary.Add(m_provider.IdCivico, m_civico);
			m_dictionary.Add(m_provider.IdNumeroAutorizzazione, m_numAutorizzazione);
			m_dictionary.Add(m_provider.IdNumProtocollo, m_numProtocollo);
			m_dictionary.Add(m_provider.IdStradario, m_stradario);
			m_dictionary.Add(m_provider.IdStatoIstanza, m_statoIstanza);
			m_dictionary.Add(m_provider.IdDataProtocollo, m_dataProtocollo);
			m_dictionary.Add(m_provider.IdDatiCatasto, m_datiCatasto);
			m_dictionary.Add(m_provider.IdRichiedente, m_richiedente);
			m_dictionary.Add(m_provider.IdIntervento, m_intervento);
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

			var filtri = m_provider.GetCampiFiltro(IdComune, Software);

			this.Controls.Clear();

			for (int i = 0; i < filtri.Length; i++)
			{
				var campo = filtri[i];

				if (!m_dictionary.ContainsKey(campo.Codice))
					Debug.WriteLine("Il dizionario non contiene l'id " + campo.Codice);
				else
				{
					BaseVisuraControl bvc = m_dictionary[campo.Codice];
					bvc.Title = campo.Etichetta;

					m_logger.DebugFormat("Creato controllo -> Idcomune: {0}, Software: {1}, Descrizione: {2}, Tipo: {3}, Id: {4}",
											bvc.IdComune, bvc.Software, bvc.Title, bvc.GetType(), campo.Codice);

					this.Controls.Add(bvc);
				}
			}

			m_logger.Debug("Fine creazione controlli di ricerca");
		}

		public override void RenderBeginTag(HtmlTextWriter writer)
		{
			writer.RenderBeginTag(HtmlTextWriterTag.Fieldset);
		}


	}
}
