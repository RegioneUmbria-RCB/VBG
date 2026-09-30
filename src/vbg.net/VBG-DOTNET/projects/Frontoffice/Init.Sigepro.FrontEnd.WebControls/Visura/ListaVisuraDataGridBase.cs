using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza.V1;
using VBG.Shared.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.Common;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
    public abstract class ListaVisuraDataGridBase : GridView, IDatabaseSoftwareControl
    {
        [Inject]
        public ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; }

        [Inject]
        protected IConfigurazione<ParametriPresentazioneDomanda> _parPresentazioneDomanda { get; set; }

        [Inject]
        protected IRisorseTestualiService _risorseTestualiService { get; set; }

        [Inject]
        protected ISessionCache _sessionCache { get; set; }

        public string NavigateUrlFormatString
        {
            get { return this.m_selectColumn.DataNavigateUrlFormatString; }
            set { this.m_selectColumn.DataNavigateUrlFormatString = value; }
        }

        public bool PermettiUsaComeModello
        {
            get { var o = this.ViewState["PermettiUsaComeModello"]; return o == null ? false : (bool)o; }
            set { this.ViewState["PermettiUsaComeModello"] = value; }
        }



        public class CachedDataSource
        {
            public int LastPage { get; set; }
            public IEnumerable<VisuraListItem> Dati { get; set; }
        }

        public delegate void IstanzaSelezionataDelegate(object sender, string idComune, string software, string idIstanza);
        // Ignorare lo warning, l'evento è utilizzato
        public event IstanzaSelezionataDelegate IstanzaSelezionata;

        /// <summary>
        /// Restituisce liindice della colonna "Data presentazione". 
        /// Utilizzare il valore per forzare il parsing di tipi "date" nell'eventuale DataTables utilizzato nella visualizzazione a lista
        /// </summary>
        public int IndiceColonnaDataIstanza { get; private set; } = -1;

        /// <summary>
        /// Restituisce liindice della colonna "Data protocollo". 
        /// Utilizzare il valore per forzare il parsing di tipi "date" nell'eventuale DataTables utilizzato nella visualizzazione a lista
        /// </summary>
        public int IndiceColonnaDataProtocollo { get; private set; } = -1;

        private readonly ILog _log = LogManager.GetLogger(typeof(ListaVisuraDataGridBase));

        private readonly Dictionary<int, BoundField> m_columns = new Dictionary<int, BoundField>();

        private readonly BoundField m_oggetto = new BoundField();
        private readonly BoundField m_progressivo = new BoundField();
        private readonly BoundField m_tipoIntervento = new BoundField();
        private readonly BoundField m_numeroProtocollo = new BoundField();
        private readonly BoundField m_operatore = new BoundField();
        private readonly BoundField m_codiceArea = new BoundField();
        private readonly BoundField m_civico = new BoundField();
        private readonly BoundField m_stato = new BoundField();
        private readonly BoundField m_dataPresentazione = new BoundField();
        private readonly BoundField m_subalterno = new BoundField();
        private readonly BoundField m_numeroIstanza = new BoundField();
        private readonly BoundField m_tipoProcedura = new BoundField();
        private readonly BoundField m_localizzazione = new BoundField();
        private readonly BoundField m_dataProtocollo = new BoundField();
        private readonly BoundField m_particella = new BoundField();
        private readonly BoundField m_software = new BoundField();
        private readonly BoundField m_richiedente = new BoundField();
        private readonly BoundField m_foglio = new BoundField();
        private readonly BoundField m_tipoCatasto = new BoundField();
        private readonly BoundField _ragioneSociale = new BoundField();
        private readonly BoundField m_posizioneArchivio = new BoundField();
        private readonly HyperLinkField m_selectColumn = new HyperLinkField();
        private readonly IFiltriVisuraControlProvider m_provider = null;

        public new IEnumerable<VisuraListItem> DataSource
        {
            get { return (IEnumerable<VisuraListItem>)base.DataSource; }
            set
            {
                base.DataSource = value.ToList();
                this.SessionDataSource.Dati = value.ToList();
                this.PageIndex = 0;
            }
        }

        public override int PageIndex
        {
            get
            {
                return this.SessionDataSource.LastPage;
            }
            set
            {
                this.SessionDataSource.LastPage = value;
                base.PageIndex = value;
            }
        }

        protected CachedDataSource SessionDataSource
        {
            get
            {
                return this._sessionCache.GetOrAdd("VisuraDataGrid.DataSource", () => new CachedDataSource());
            }
            set { this._sessionCache.Set("VisuraDataGrid.DataSource", value); }
        }


        protected void Rebind()
        {
            base.DataSource = this.SessionDataSource.Dati;
            base.DataBind();
        }


        public string IdComune
        {
            get
            {
                var o = this.ViewState["IdComune"];
                return o == null ? "" : o.ToString();
            }
            set
            {
                this.EnsureChildControls();
                this.ViewState["IdComune"] = value;
            }
        }

        public string Software
        {
            get
            {
                var o = this.ViewState["Software"];
                return o == null ? "" : o.ToString();
            }
            set { this.ViewState["Software"] = value; }
        }

        internal ListaVisuraDataGridBase(IFiltriVisuraControlProvider provider)
        {
            FoKernelContainer.Inject(this);

            this.AutoGenerateColumns = false;

            this.m_provider = provider;

            Init += this.VisuraDataGrid_Init;
            Load += this.OnLoad;
            RowCreated += this.OnRowCreated;

            this.GridLines = GridLines.None;
            this.CssClass = "table";

            this.EmptyDataText = "";
        }

        private void OnRowCreated(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow &&
                this.PermettiUsaComeModello &&
                this._parPresentazioneDomanda.Parametri.AbilitaTemplateDomanda)
            {
                var cell = e.Row.Cells.Cast<TableCell>().Last();

                var ul = new HtmlGenericControl("ul");
                var li1 = new HtmlGenericControl("li");
                var li2 = new HtmlGenericControl("li");
                var link = new HyperLink
                {
                    Text = "Usa come modello",
                    ID = "hlUsaComeModello"
                };

                ul.Attributes.Add("class", "azioni-tabella");
                ul.Controls.Add(li1);
                ul.Controls.Add(li2);


                li1.Controls.Add(cell.Controls[cell.Controls.Count - 1]);
                li2.Controls.Add(link);

                cell.Controls.Add(ul);
            }
        }


        private void OnLoad(object sender, EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                try
                {
                    var recPerPagina = this._campiRicercaVisuraRepository.GetRecordPerPagina(this.IdComune, this.Software);
                }
                catch (Exception)
                { /*potrebbe dare errori nel designer*/
                }
            }


            try
            {
                this.Columns.Clear();
                // l'indice della colonna data istanza e data protocollo è utilizzato su DataTables 
                // per forzare un ordinamento di tipo data
                this.IndiceColonnaDataIstanza = -1;
                this.IndiceColonnaDataProtocollo = -1;

                var campiLista = this.m_provider.GetCampiTabella(this.IdComune, this.Software);

                for (var i = 0; i < campiLista.Length; i++)
                {
                    var campo = campiLista[i];

                    if (!this.m_columns.ContainsKey(campo.Codice))
                    {
                        Debug.WriteLine("Lista pratiche: Il dizionario non contiene l'id " + campo.Codice);
                    }
                    else
                    {
                        var col = this.m_columns[campo.Codice];

                        if (col == this.m_dataPresentazione)
                        {
                            this.IndiceColonnaDataIstanza = i;
                        }

                        if (col == this.m_dataProtocollo)
                        {
                            this.IndiceColonnaDataProtocollo = i;
                        }

                        var bc = col;

                        bc.HeaderText = this._risorseTestualiService.GetRisorsa(campo.IdRisorsa, campo.Etichetta);
                        this.Columns.Add(bc);
                    }
                }
                this.m_selectColumn.ItemStyle.HorizontalAlign = HorizontalAlign.Right;
                this.m_selectColumn.Text = "Mostra dettagli";
                this.m_selectColumn.DataNavigateUrlFields = new string[] { "Uuid" };
                this.m_selectColumn.ItemStyle.CssClass = "colonna-azioni vbg-show-spinner";

                this.Columns.Add(this.m_selectColumn);

                this.Rebind();
            }
            catch (Exception)
            { /*potrebbe dare errori nel designer*/
            }
        }


        private void VisuraDataGrid_Init(object sender, EventArgs e)
        {
            this.AutoGenerateColumns = false;

            this.m_oggetto.DataField = "Oggetto";
            this.m_progressivo.DataField = "Progressivo";
            this.m_tipoIntervento.DataField = "TipoIntervento";
            this.m_numeroProtocollo.DataField = "NumeroProtocollo";
            this.m_operatore.DataField = "Operatore";
            this.m_codiceArea.DataField = "CodiceArea";
            this.m_civico.DataField = "Civico";
            this.m_stato.DataField = "Stato";
            this.m_dataPresentazione.DataField = "DataPresentazione";
            this.m_dataPresentazione.DataFormatString = "{0:dd/MM/yyyy}";
            this.m_subalterno.DataField = "Subalterno";
            this.m_numeroIstanza.DataField = "NumeroIstanza";
            this.m_tipoProcedura.DataField = "TipoProcedura";
            this.m_localizzazione.DataField = "LocalizzazioneConCivico";
            this.m_dataProtocollo.DataField = "DataProtocollo";
            this.m_dataProtocollo.DataFormatString = "{0:dd/MM/yyyy}";
            this.m_particella.DataField = "Particella";
            this.m_software.DataField = "Software";
            this.m_richiedente.DataField = "Richiedente";
            this.m_foglio.DataField = "Foglio";
            this.m_tipoCatasto.DataField = "TipoCatasto";
            this._ragioneSociale.DataField = "Azienda";
            this.m_posizioneArchivio.DataField = "PosizioneArchivio";

            this.m_columns.Add(this.m_provider.ListaIdOperatore, this.m_operatore);
            this.m_columns.Add(this.m_provider.ListaIdRichiedente, this.m_richiedente);
            this.m_columns.Add(this.m_provider.ListaIdTipoprocedura, this.m_tipoProcedura);
            this.m_columns.Add(this.m_provider.ListaIdOggetto, this.m_oggetto);
            this.m_columns.Add(this.m_provider.ListaIdParticella, this.m_particella);
            this.m_columns.Add(this.m_provider.ListaIdSubalterno, this.m_subalterno);
            this.m_columns.Add(this.m_provider.ListaIdProgressivo, this.m_progressivo);
            this.m_columns.Add(this.m_provider.ListaIdLocalizzazione, this.m_localizzazione);
            this.m_columns.Add(this.m_provider.ListaIdCodicearea, this.m_codiceArea);
            this.m_columns.Add(this.m_provider.ListaIdFoglio, this.m_foglio);
            this.m_columns.Add(this.m_provider.ListaIdNumeroistanza, this.m_numeroIstanza);
            this.m_columns.Add(this.m_provider.ListaIdDatapresentazione, this.m_dataPresentazione);
            this.m_columns.Add(this.m_provider.ListaIdTipointervento, this.m_tipoIntervento);
            this.m_columns.Add(this.m_provider.ListaIdStato, this.m_stato);
            this.m_columns.Add(this.m_provider.ListaIdNumeroprotocollo, this.m_numeroProtocollo);
            this.m_columns.Add(this.m_provider.ListaIdDataprotocollo, this.m_dataProtocollo);
            this.m_columns.Add(this.m_provider.ListaIdTipocatasto, this.m_tipoCatasto);
            this.m_columns.Add(this.m_provider.ListaIdRagioneSociale, this._ragioneSociale);
            this.m_columns.Add(this.m_provider.ListaIdPosizioneArchivio, this.m_posizioneArchivio);

            RowDataBound += this.ListaVisuraDataGridBase_RowDataBound;
        }

        private void ListaVisuraDataGridBase_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var dataItem = e.Row.DataItem as VisuraListItem;
                var uuid = dataItem.Uuid;

                e.Row.Attributes.Add("data-uuid", uuid);

                var hlUsaComeModello = (HyperLink)e.Row.FindControl("hlUsaComeModello");

                if (hlUsaComeModello != null)
                {
                    hlUsaComeModello.NavigateUrl = $"~/copia-da-istanza-presentata/{this.IdComune}/{this.Software}/{uuid}";
                }
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            if (this.Rows.Count > 0)
            {
                //This replaces <td> with <th> and adds the scope attribute
                this.UseAccessibleHeader = true;

                //This will add the <thead> and <tbody> elements
                this.HeaderRow.TableSection = TableRowSection.TableHeader;

                //This adds the <tfoot> element. 
                //Remove if you don't have a footer row
                this.FooterRow.TableSection = TableRowSection.TableFooter;
            }

            base.OnPreRender(e);
        }
    }
}
