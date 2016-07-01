using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebControls.VisuraV2
{
	[ToolboxData("<{0}:ListaPraticheVisuraV2 runat=server></{0}:ListaPraticheVisuraV2>")]
	public class ListaPraticheVisuraV2 : GridView
	{
		public delegate void IstanzaSelezionataDelegate(string codiceIstanza);
		public event IstanzaSelezionataDelegate IstanzaSelezionata;


		const int RECORS_PER_PAGINA = 20;

		const string CODICE_ISTANZA = "CODICE_ISTANZA";// Codice Istanza               
		const string DATA_ISTANZA = "DATA_ISTANZA";	//Data Istanza                 
		const string NUMERO_PROTOCOLLO = "NUMERO_PROTOCOLLO";	// Numero protocollo            
		const string DATA_PROTOCOLLO = "DATA_PROTOCOLLO";	// Data protocollo              
		const string OGGETTO = "OGGETTO";	// Oggetto dell'istanza         
		const string CIVICO = "CIVICO";	// Civico                       
		const string NUMERO_AUTORIZZAZIONE = "NUMERO_AUTORIZZAZIONE";	// Numero autorizzazione        
		const string INDIRIZZO = "INDIRIZZO";	// Indirizzo                    
		const string STATO_ISTANZA = "STATO_ISTANZA";	// Stato istanza                
		const string DATI_CATASTALI = "DATI_CATASTALI";	// Dati catastali               
		const string RICHIEDENTE = "RICHIEDENTE";	// Richiedente                  
		const string INTERVENTO = "INTERVENTO";	// Intervento                   
		const string RESPONSABILE_PROCEDIMENTO = "RESPONSABILE_PROCEDIMENTO";	// Responsabile del procedimento

		

		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

		ILog _log = LogManager.GetLogger("ListaPraticheVisuraV2");


		Dictionary<string, BoundField> _dizionarioControlli = new Dictionary<string, BoundField>();

		BoundField codiceIstanzaColumn = new BoundField();
		BoundField dataIstanzaColumn = new BoundField();
		BoundField numeroProtocolloColumn = new BoundField();
		DataProtocolloColumn dataProtocolloColumn = new DataProtocolloColumn();
		BoundField oggettoColumn = new BoundField();
		CivicoColumn civicoColumn = new CivicoColumn();
		BoundField numeroAutorizzazioneColumn = new BoundField();
		LocalizzazioneColumn indirizzoColumn = new LocalizzazioneColumn();
		BoundField statoIstanzaColumn = new BoundField();
		RiferimentiCatastaliColumn datiCatastaliColumn = new RiferimentiCatastaliColumn();
		BoundField richiedenteColumn = new BoundField();
		BoundField interventoColumn = new BoundField();
		BoundField responsabileProcedimentoColumn = new BoundField();
		ButtonField selezionaColumn = new ButtonField();

		public new IEnumerable<DettaglioPraticaBreveType> DataSource
		{
			get { return (IEnumerable<DettaglioPraticaBreveType>)base.DataSource; }
			set
			{
				base.DataSource = value;
				SessionDataSource = (IEnumerable<DettaglioPraticaBreveType>)value;
			}
		}

		protected IEnumerable<DettaglioPraticaBreveType> SessionDataSource
		{
			get { return (IEnumerable<DettaglioPraticaBreveType>)Context.Session["ListaPraticheVisuraV2"]; }
			set { Context.Session["ListaPraticheVisuraV2"] = value; }
		}


		public string IdComune
		{
			get
			{
				var o = HttpContext.Current.Request.QueryString["IdComune"];

				if (String.IsNullOrEmpty(o))
					throw new Exception("Parametro idComune non impostato");

				return o.ToString();
			}
			
		}

		public string Software
		{
			get
			{
				var o = HttpContext.Current.Request.QueryString["Software"];

				if (String.IsNullOrEmpty(o))
					throw new Exception("Parametro Software non impostato");

				return o.ToString();
			}

		}

		public string ContestoVisura
		{
			get { object o = this.ViewState["ContestoVisura"]; return o == null ? "FiltriVisura" : o.ToString(); }
			set { this.ViewState["ContestoVisura"] = value; }
		}


		List<string> _listaControlliVisualizzati;
		private List<string> ListaControlliVisualizzati
		{
			get 
			{
				if (_listaControlliVisualizzati == null)
				{
					_listaControlliVisualizzati = new List<string>();

					var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), ContestoVisura, true);
					var campiVisura = _istanzePresentateRepository.GetFiltri(IdComune, Software, contesto);

					foreach (var campo in campiVisura)
					{
						_listaControlliVisualizzati.Add(campo.Fkidcampo);
					}
				}

				return _listaControlliVisualizzati;
			}
			set 
			{
				_listaControlliVisualizzati = value;
			}
		}

		public ListaPraticheVisuraV2()
		{
			FoKernelContainer.Inject(this);

			this.DataBinding += new EventHandler(ListaPraticheVisuraV2_DataBinding);
			this.PageIndexChanging += new GridViewPageEventHandler(ListaPraticheVisuraV2_PageIndexChanging);
			this.SelectedIndexChanged += new EventHandler(ListaPraticheVisuraV2_SelectedIndexChanged);

			InizializzaDictionary();
			InizializzaControlli();
		}

		void ListaPraticheVisuraV2_SelectedIndexChanged(object sender, EventArgs e)
		{
			var codiceIstanza = this.DataKeys[this.SelectedIndex].Value;

			if (this.IstanzaSelezionata != null)
				IstanzaSelezionata(codiceIstanza.ToString());
		}

		void ListaPraticheVisuraV2_PageIndexChanging(object sender, GridViewPageEventArgs e)
		{
			this.PageIndex = e.NewPageIndex;
			this.DataSource = SessionDataSource;

			DataBind();
		}

		void ListaPraticheVisuraV2_DataBinding(object sender, EventArgs e)
		{
			RenderControlli();
		}

		private void RenderControlli()
		{
			try
			{
				var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), ContestoVisura, true);
				var campiVisura = _istanzePresentateRepository.GetFiltri(IdComune, Software, contesto);

				this.Columns.Clear();

				foreach (var campo in campiVisura)
				{
					this.Columns.Add(_dizionarioControlli[campo.Fkidcampo]);
				}

				this.Columns.Add(selezionaColumn);
			}
			catch (Exception ex)
			{
				_log.ErrorFormat("Errore in ListaPraticheVisuraV2.RenderControlli: {0}", ex.ToString());
			}
		}

		
		private void InizializzaControlli()
		{
			this.DataKeyNames = new string[]{"idPratica"};
			this.AutoGenerateColumns = false;
			this.RowStyle.CssClass = "ItemStyle";
			this.AllowPaging = true;
			this.PageSize = RECORS_PER_PAGINA;
			this.PagerSettings.Mode = PagerButtons.NumericFirstLast;
			//this.PagerSettings.NextPageText = "Pagina successiva";
			//this.PagerSettings.PreviousPageText = "Pagina precedente";

			this.GridLines = GridLines.None;

			codiceIstanzaColumn.HeaderText = "Codice Istanza";
			codiceIstanzaColumn.DataField = "numeroPratica";

			dataIstanzaColumn.HeaderText = "Data Istanza";
			dataIstanzaColumn.DataField = "dataPratica";
			dataIstanzaColumn.DataFormatString = "{0:dd/MM/yyyy}";
			dataIstanzaColumn.HtmlEncode = false;

			numeroProtocolloColumn.HeaderText = "Numero protocollo";
			numeroProtocolloColumn.DataField = "numeroProtocolloGenerale";

			dataProtocolloColumn.HeaderText = "Data protocollo";
			dataProtocolloColumn.DataField = "dataProtocolloGenerale";
			dataProtocolloColumn.DataFormatString = "{0:dd/MM/yyyy}";
			dataProtocolloColumn.HtmlEncode = false;

			oggettoColumn.HeaderText = "Oggetto dell'istanza";
			oggettoColumn.DataField = "oggetto";

			civicoColumn.HeaderText = "Civico";
			civicoColumn.DataField = "localizzazione";

			numeroAutorizzazioneColumn.HeaderText = "Numero autorizzazione";
			numeroAutorizzazioneColumn.DataField = "";

			indirizzoColumn.HeaderText = "Indirizzo";
			indirizzoColumn.DataField = "localizzazione";

			statoIstanzaColumn.HeaderText = "Stato istanza";
			statoIstanzaColumn.DataField = "statoPratica";

			datiCatastaliColumn.HeaderText = "Dati catastali";
			datiCatastaliColumn.DataFormatString = "Tipo catasto: {0}<br />Foglio: {1}<br />Particella: {2}<br/>Subalterno: {3}";

			richiedenteColumn.HeaderText = "Richiedente";
			richiedenteColumn.DataField = "richiedente";
			richiedenteColumn.HtmlEncode = false;

			interventoColumn.HeaderText = "Intervento";
			interventoColumn.DataField = "intervento";

			selezionaColumn.Text = "Seleziona";
			selezionaColumn.CommandName = "Select";

			responsabileProcedimentoColumn.HeaderText = "Responsabile del procedimento";
			//responsabileProcedimentoColumn.DataField = "intervento";
		}

		private void InizializzaDictionary()
		{
			_dizionarioControlli.Add(CODICE_ISTANZA,codiceIstanzaColumn);
			_dizionarioControlli.Add(DATA_ISTANZA,dataIstanzaColumn);
			_dizionarioControlli.Add(NUMERO_PROTOCOLLO,numeroProtocolloColumn);
			_dizionarioControlli.Add(DATA_PROTOCOLLO,dataProtocolloColumn);
			_dizionarioControlli.Add(OGGETTO,oggettoColumn);
			_dizionarioControlli.Add(CIVICO,civicoColumn);
			_dizionarioControlli.Add(NUMERO_AUTORIZZAZIONE,numeroAutorizzazioneColumn);
			_dizionarioControlli.Add(INDIRIZZO,indirizzoColumn);
			_dizionarioControlli.Add(STATO_ISTANZA,statoIstanzaColumn);
			_dizionarioControlli.Add(DATI_CATASTALI,datiCatastaliColumn);
			_dizionarioControlli.Add(RICHIEDENTE,richiedenteColumn);
			_dizionarioControlli.Add(INTERVENTO,interventoColumn);
			_dizionarioControlli.Add(RESPONSABILE_PROCEDIMENTO, responsabileProcedimentoColumn);
		}

		protected void Rebind()
		{
			base.DataSource = SessionDataSource;
			base.DataBind();
		}

		protected override object SaveViewState()
		{
			var p = new Pair();
			p.First = ListaControlliVisualizzati;
			p.Second = base.SaveViewState();

			return p;
		}

		protected override void LoadViewState(object savedState)
		{
			var p = (Pair)savedState;

			ListaControlliVisualizzati = (List<string>)p.First;
			base.LoadViewState(p.Second);			
		}


		public class CivicoColumn : BoundField
		{
			protected override void OnDataBindField(object sender, EventArgs e)
			{
				var cell = sender as DataControlFieldCell;
				var row = cell.NamingContainer as GridViewRow;
				var dataItem = row.DataItem as DettaglioPraticaBreveType;

				cell.Text = String.Empty;

				if (dataItem.localizzazione != null && dataItem.localizzazione.Count() >= 1)
					cell.Text = dataItem.localizzazione[0].civico;
				
			}
		}

		public class DataProtocolloColumn : BoundField
		{
			protected override void OnDataBindField(object sender, EventArgs e)
			{
				var cell = sender as DataControlFieldCell;
				var row = cell.NamingContainer as GridViewRow;
				var dataItem = row.DataItem as DettaglioPraticaBreveType;

				cell.Text = String.Empty;

				if (dataItem.dataProtocolloGeneraleSpecified)
					cell.Text = dataItem.dataProtocolloGenerale.ToString("dd/MM/yyyy");

			}
		}

		public class RiferimentiCatastaliColumn : BoundField
		{
			protected override void OnDataBindField(object sender, EventArgs e)
			{
				var cell = sender as DataControlFieldCell;
				var row = cell.NamingContainer as GridViewRow;
				var dataItem = row.DataItem as DettaglioPraticaBreveType;

				cell.Text = String.Empty;

				if (dataItem.localizzazione != null && dataItem.localizzazione.Count() >=1)
					cell.Text = dataItem.localizzazione[0].GetRiferimentiCatastali(this.DataFormatString);

			}
		}

		public class LocalizzazioneColumn : BoundField
		{
			protected override void OnDataBindField(object sender, EventArgs e)
			{
				var cell = sender as DataControlFieldCell;
				var row = cell.NamingContainer as GridViewRow;
				var dataItem = row.DataItem as DettaglioPraticaBreveType;

				cell.Text = String.Empty;

				if (dataItem.localizzazione != null && dataItem.localizzazione.Count() >= 1)
					cell.Text = dataItem.localizzazione[0].ToString();

			}
		}

		
	}
}
