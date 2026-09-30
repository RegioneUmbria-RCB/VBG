using Init.Sigepro.FrontEnd.AppLogic;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.VisuraV2
{
    [ToolboxData("<{0}:ListaPraticheVisuraV2 runat=server></{0}:ListaPraticheVisuraV2>")]
    public class ListaPraticheVisuraV2 : GridView
    {
        public delegate void IstanzaSelezionataDelegate(string codiceIstanza);
        public event IstanzaSelezionataDelegate IstanzaSelezionata;

        private const int RECORS_PER_PAGINA = 20;
        private const string CODICE_ISTANZA = "CODICE_ISTANZA";// Codice Istanza               
        private const string DATA_ISTANZA = "DATA_ISTANZA";  //Data Istanza                 
        private const string NUMERO_PROTOCOLLO = "NUMERO_PROTOCOLLO";    // Numero protocollo            
        private const string DATA_PROTOCOLLO = "DATA_PROTOCOLLO";    // Data protocollo              
        private const string OGGETTO = "OGGETTO";    // Oggetto dell'istanza         
        private const string CIVICO = "CIVICO";  // Civico                       
        private const string NUMERO_AUTORIZZAZIONE = "NUMERO_AUTORIZZAZIONE";    // Numero autorizzazione        
        private const string INDIRIZZO = "INDIRIZZO";    // Indirizzo                    
        private const string STATO_ISTANZA = "STATO_ISTANZA";    // Stato istanza                
        private const string DATI_CATASTALI = "DATI_CATASTALI";  // Dati catastali               
        private const string RICHIEDENTE = "RICHIEDENTE";    // Richiedente                  
        private const string INTERVENTO = "INTERVENTO";  // Intervento                   
        private const string RESPONSABILE_PROCEDIMENTO = "RESPONSABILE_PROCEDIMENTO";   // Responsabile del procedimento

        [Inject]
        public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

        private readonly ILog _log = LogManager.GetLogger("ListaPraticheVisuraV2");
        private readonly Dictionary<string, BoundField> _dizionarioControlli = new Dictionary<string, BoundField>();
        private readonly BoundField codiceIstanzaColumn = new BoundField();
        private readonly BoundField dataIstanzaColumn = new BoundField();
        private readonly BoundField numeroProtocolloColumn = new BoundField();
        private readonly DataProtocolloColumn dataProtocolloColumn = new DataProtocolloColumn();
        private readonly BoundField oggettoColumn = new BoundField();
        private readonly CivicoColumn civicoColumn = new CivicoColumn();
        private readonly BoundField numeroAutorizzazioneColumn = new BoundField();
        private readonly LocalizzazioneColumn indirizzoColumn = new LocalizzazioneColumn();
        private readonly BoundField statoIstanzaColumn = new BoundField();
        private readonly RiferimentiCatastaliColumn datiCatastaliColumn = new RiferimentiCatastaliColumn();
        private readonly BoundField richiedenteColumn = new BoundField();
        private readonly BoundField interventoColumn = new BoundField();
        private readonly BoundField responsabileProcedimentoColumn = new BoundField();
        private readonly ButtonField selezionaColumn = new ButtonField();

        public new IEnumerable<DettaglioPraticaBreveType> DataSource
        {
            get { return (IEnumerable<DettaglioPraticaBreveType>)base.DataSource; }
            set
            {
                base.DataSource = value;
                this.SessionDataSource = (IEnumerable<DettaglioPraticaBreveType>)value;
            }
        }

        protected IEnumerable<DettaglioPraticaBreveType> SessionDataSource
        {
            get { return (IEnumerable<DettaglioPraticaBreveType>)this.Context.Session["ListaPraticheVisuraV2"]; }
            set { this.Context.Session["ListaPraticheVisuraV2"] = value; }
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

        private List<string> _listaControlliVisualizzati;
        private List<string> ListaControlliVisualizzati
        {
            get
            {
                if (this._listaControlliVisualizzati == null)
                {
                    this._listaControlliVisualizzati = new List<string>();

                    var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), this.ContestoVisura, true);
                    var campiVisura = this._istanzePresentateRepository.GetFiltri(this.IdComune, this.Software, contesto);

                    foreach (var campo in campiVisura)
                    {
                        this._listaControlliVisualizzati.Add(campo.Fkidcampo);
                    }
                }

                return this._listaControlliVisualizzati;
            }
            set
            {
                this._listaControlliVisualizzati = value;
            }
        }

        public ListaPraticheVisuraV2()
        {
            FoKernelContainer.Inject(this);

            this.DataBinding += new EventHandler(this.ListaPraticheVisuraV2_DataBinding);
            this.PageIndexChanging += new GridViewPageEventHandler(this.ListaPraticheVisuraV2_PageIndexChanging);
            this.SelectedIndexChanged += new EventHandler(this.ListaPraticheVisuraV2_SelectedIndexChanged);

            this.InizializzaDictionary();
            this.InizializzaControlli();
        }

        private void ListaPraticheVisuraV2_SelectedIndexChanged(object sender, EventArgs e)
        {
            var codiceIstanza = this.DataKeys[this.SelectedIndex].Value;

            if (this.IstanzaSelezionata != null)
                IstanzaSelezionata(codiceIstanza.ToString());
        }

        private void ListaPraticheVisuraV2_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            this.PageIndex = e.NewPageIndex;
            this.DataSource = this.SessionDataSource;

            this.DataBind();
        }

        private void ListaPraticheVisuraV2_DataBinding(object sender, EventArgs e)
        {
            this.RenderControlli();
        }

        private void RenderControlli()
        {
            try
            {
                var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), this.ContestoVisura, true);
                var campiVisura = this._istanzePresentateRepository.GetFiltri(this.IdComune, this.Software, contesto);

                this.Columns.Clear();

                foreach (var campo in campiVisura)
                {
                    this.Columns.Add(this._dizionarioControlli[campo.Fkidcampo]);
                }

                this.Columns.Add(this.selezionaColumn);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore in ListaPraticheVisuraV2.RenderControlli: {0}", ex.ToString());
            }
        }


        private void InizializzaControlli()
        {
            this.DataKeyNames = new string[] { "idPratica" };
            this.AutoGenerateColumns = false;
            this.RowStyle.CssClass = "ItemStyle";
            this.AllowPaging = true;
            this.PageSize = RECORS_PER_PAGINA;
            this.PagerSettings.Mode = PagerButtons.NumericFirstLast;
            //this.PagerSettings.NextPageText = "Pagina successiva";
            //this.PagerSettings.PreviousPageText = "Pagina precedente";

            this.GridLines = GridLines.None;

            this.codiceIstanzaColumn.HeaderText = "Numero pratica";
            this.codiceIstanzaColumn.DataField = "numeroPratica";

            this.dataIstanzaColumn.HeaderText = "Data Istanza";
            this.dataIstanzaColumn.DataField = "dataPratica";
            this.dataIstanzaColumn.DataFormatString = "{0:dd/MM/yyyy}";
            this.dataIstanzaColumn.HtmlEncode = false;

            this.numeroProtocolloColumn.HeaderText = "Numero protocollo";
            this.numeroProtocolloColumn.DataField = "numeroProtocolloGenerale";

            this.dataProtocolloColumn.HeaderText = "Data protocollo";
            this.dataProtocolloColumn.DataField = "dataProtocolloGenerale";
            this.dataProtocolloColumn.DataFormatString = "{0:dd/MM/yyyy}";
            this.dataProtocolloColumn.HtmlEncode = false;

            this.oggettoColumn.HeaderText = "Oggetto dell'istanza";
            this.oggettoColumn.DataField = "oggetto";

            this.civicoColumn.HeaderText = "Civico";
            this.civicoColumn.DataField = "localizzazione";

            this.numeroAutorizzazioneColumn.HeaderText = "Numero autorizzazione";
            this.numeroAutorizzazioneColumn.DataField = "";

            this.indirizzoColumn.HeaderText = "Indirizzo";
            this.indirizzoColumn.DataField = "localizzazione";

            this.statoIstanzaColumn.HeaderText = "Stato istanza";
            this.statoIstanzaColumn.DataField = "statoPratica";

            this.datiCatastaliColumn.HeaderText = "Dati catastali";
            this.datiCatastaliColumn.DataFormatString = "Tipo catasto: {0}<br />Foglio: {1}<br />Particella: {2}<br/>Subalterno: {3}";

            this.richiedenteColumn.HeaderText = "Richiedente";
            this.richiedenteColumn.DataField = "richiedente";
            this.richiedenteColumn.HtmlEncode = false;

            this.interventoColumn.HeaderText = "Intervento";
            this.interventoColumn.DataField = "intervento";

            this.selezionaColumn.Text = "Seleziona";
            this.selezionaColumn.CommandName = "Select";

            this.responsabileProcedimentoColumn.HeaderText = "Responsabile del procedimento";
            //responsabileProcedimentoColumn.DataField = "intervento";
        }

        private void InizializzaDictionary()
        {
            this._dizionarioControlli.Add(CODICE_ISTANZA, this.codiceIstanzaColumn);
            this._dizionarioControlli.Add(DATA_ISTANZA, this.dataIstanzaColumn);
            this._dizionarioControlli.Add(NUMERO_PROTOCOLLO, this.numeroProtocolloColumn);
            this._dizionarioControlli.Add(DATA_PROTOCOLLO, this.dataProtocolloColumn);
            this._dizionarioControlli.Add(OGGETTO, this.oggettoColumn);
            this._dizionarioControlli.Add(CIVICO, this.civicoColumn);
            this._dizionarioControlli.Add(NUMERO_AUTORIZZAZIONE, this.numeroAutorizzazioneColumn);
            this._dizionarioControlli.Add(INDIRIZZO, this.indirizzoColumn);
            this._dizionarioControlli.Add(STATO_ISTANZA, this.statoIstanzaColumn);
            this._dizionarioControlli.Add(DATI_CATASTALI, this.datiCatastaliColumn);
            this._dizionarioControlli.Add(RICHIEDENTE, this.richiedenteColumn);
            this._dizionarioControlli.Add(INTERVENTO, this.interventoColumn);
            this._dizionarioControlli.Add(RESPONSABILE_PROCEDIMENTO, this.responsabileProcedimentoColumn);
        }

        protected void Rebind()
        {
            base.DataSource = this.SessionDataSource;
            base.DataBind();
        }

        protected override object SaveViewState()
        {
            var p = new Pair();
            p.First = this.ListaControlliVisualizzati;
            p.Second = base.SaveViewState();

            return p;
        }

        protected override void LoadViewState(object savedState)
        {
            var p = (Pair)savedState;

            this.ListaControlliVisualizzati = (List<string>)p.First;
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

                if (dataItem.localizzazione != null && dataItem.localizzazione.Count() >= 1)
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
