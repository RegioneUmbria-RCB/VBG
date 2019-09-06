using System;
using System.Linq;
using System.Collections.Generic;
using System.Diagnostics;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.Infrastructure.Caching;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
	public abstract class ListaVisuraDataGridBase : GridView, IDatabaseSoftwareControl
	{
		[Inject]
		public ICampiRicercaVisuraRepository _campiRicercaVisuraRepository { get; set; }

        public class CachedDataSource
        {
            public int LastPage { get; set; }
            public IEnumerable<VisuraListItem> Dati { get; set; }
        }


        public delegate void IstanzaSelezionataDelegate(object sender, string idComune, string software, string idIstanza);

		public event IstanzaSelezionataDelegate IstanzaSelezionata;

		private Dictionary<int, BoundField> m_columns = new Dictionary<int, BoundField>();

		private BoundField m_oggetto = new BoundField();
		private BoundField m_progressivo = new BoundField();
		private BoundField m_tipoIntervento = new BoundField();
		private BoundField m_numeroProtocollo = new BoundField();
		private BoundField m_operatore = new BoundField();
		private BoundField m_codiceArea = new BoundField();
		private BoundField m_civico = new BoundField();
		private BoundField m_stato = new BoundField();
		private BoundField m_dataPresentazione = new BoundField();
		private BoundField m_subalterno = new BoundField();
		private BoundField m_numeroIstanza = new BoundField();
		private BoundField m_tipoProcedura = new BoundField();
		private BoundField m_localizzazione = new BoundField();
		private BoundField m_dataProtocollo = new BoundField();
		private BoundField m_particella = new BoundField();
		private BoundField m_software = new BoundField();
		private BoundField m_richiedente = new BoundField();
		private BoundField m_foglio = new BoundField();
		private BoundField m_tipoCatasto = new BoundField();
        private BoundField _ragioneSociale = new BoundField();
		private ButtonField m_selectColumn = new ButtonField();


		IFiltriVisuraControlProvider m_provider = null;

		public new IEnumerable<VisuraListItem> DataSource
		{
			get { return (IEnumerable<VisuraListItem>)base.DataSource; }
			set
			{
				base.DataSource = value.ToList();
				SessionDataSource.Dati = value.ToList();
                PageIndex = 0;
            }
		}

        public override int PageIndex
        {
            get
            {
                return SessionDataSource.LastPage;
            }
            set
            {
                SessionDataSource.LastPage = value;
                base.PageIndex = value;
            }
        }

        protected CachedDataSource SessionDataSource
		{
			get
            {
                return SessionHelper.GetEntry("VisuraDataGrid.DataSource", () => new CachedDataSource());
            }
			set { SessionHelper.AddEntry("VisuraDataGrid.DataSource", value); }
		}


		protected void Rebind()
		{
			base.DataSource = SessionDataSource.Dati;
			base.DataBind();
		}


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
			set { this.ViewState["Software"] = value; }
		}


		internal ListaVisuraDataGridBase(IFiltriVisuraControlProvider provider)
		{
			FoKernelContainer.Inject(this);

			this.AutoGenerateColumns = false;

			m_provider = provider;

			this.Init += new EventHandler(VisuraDataGrid_Init);
			this.Load += new EventHandler(VisuraDataGrid_Load);

			this.GridLines = GridLines.None;
            this.CssClass = "table";
		}

		private void VisuraDataGrid_Load(object sender, EventArgs e)
		{
			if (!Page.IsPostBack)
			{
				try
				{
					int recPerPagina = _campiRicercaVisuraRepository.GetRecordPerPagina( IdComune , Software);
					if (recPerPagina > 0)
					{
						this.AllowPaging = true;
						this.PageSize = recPerPagina;
						this.PagerSettings.Mode = PagerButtons.Numeric;
					}
					else
					{
						this.AllowPaging = false;
					}
				}
				catch (Exception)
				{ /*potrebbe dare errori nel designer*/
				}
			}


			try
			{
				this.Columns.Clear();

				var campiLista = m_provider.GetCampiTabella(IdComune, Software);

				for (int i = 0; i < campiLista.Length; i++ )
				{
					var campo = campiLista[i];

					if (!m_columns.ContainsKey(campo.Codice))
						Debug.WriteLine("Lista pratiche: Il dizionario non contiene l'id " + campo.Codice);
					else
					{
						BoundField bc = (BoundField)m_columns[campo.Codice];
						bc.HeaderText = campo.Etichetta;
						this.Columns.Add(bc);
					}
				}
				m_selectColumn.ItemStyle.HorizontalAlign = HorizontalAlign.Center;
				m_selectColumn.ButtonType = ButtonType.Link;
				m_selectColumn.Text = "Seleziona";
				m_selectColumn.CommandName = "Select";
				this.Columns.Add(m_selectColumn);
				this.Rebind();
			}
			catch (Exception)
			{ /*potrebbe dare errori nel designer*/
			}
		}

		private void VisuraDataGrid_PageIndexChanged(object source, GridViewPageEventArgs e)
		{
			this.PageIndex = e.NewPageIndex;
			Rebind();
		}

		private void VisuraDataGrid_SelectedIndexChanged(object sender, EventArgs e)
		{
			string idPraticaSelezionata = this.DataKeys[this.SelectedIndex].Values[0].ToString();

			if (this.IstanzaSelezionata != null)
				this.IstanzaSelezionata(this, IdComune, Software, idPraticaSelezionata);
		}

		private void VisuraDataGrid_Init(object sender, EventArgs e)
		{
			this.AutoGenerateColumns = false;
			this.DataKeyNames = new string[]{"Uuid"};

			m_oggetto.DataField = "Oggetto";
			m_progressivo.DataField = "Progressivo";
			m_tipoIntervento.DataField = "TipoIntervento";
			m_numeroProtocollo.DataField = "NumeroProtocollo";
			m_operatore.DataField = "Operatore";
			m_codiceArea.DataField = "CodiceArea";
			m_civico.DataField = "Civico";
			m_stato.DataField = "Stato";
			m_dataPresentazione.DataField = "DataPresentazione";
            m_dataPresentazione.DataFormatString = "{0:dd/MM/yyyy}";
            m_subalterno.DataField = "Subalterno";
			m_numeroIstanza.DataField = "NumeroIstanza";
			m_tipoProcedura.DataField = "TipoProcedura";
			m_localizzazione.DataField = "LocalizzazioneConCivico";
			m_dataProtocollo.DataField = "DataProtocollo";
            m_dataProtocollo.DataFormatString = "{0:dd/MM/yyyy}";
            m_particella.DataField = "Particella";
			m_software.DataField = "Software";
			m_richiedente.DataField = "Richiedente";
			m_foglio.DataField = "Foglio";
			m_tipoCatasto.DataField = "TipoCatasto";
            _ragioneSociale.DataField = "Azienda";

			m_columns.Add( m_provider.ListaIdOperatore, m_operatore);
			m_columns.Add( m_provider.ListaIdRichiedente, m_richiedente);
			m_columns.Add( m_provider.ListaIdTipoprocedura, m_tipoProcedura);
			m_columns.Add( m_provider.ListaIdOggetto, m_oggetto);
			m_columns.Add( m_provider.ListaIdParticella, m_particella);
			m_columns.Add( m_provider.ListaIdSubalterno, m_subalterno);
			m_columns.Add( m_provider.ListaIdProgressivo, m_progressivo);
			m_columns.Add( m_provider.ListaIdLocalizzazione, m_localizzazione);
			m_columns.Add( m_provider.ListaIdCodicearea, m_codiceArea);
			m_columns.Add( m_provider.ListaIdFoglio, m_foglio);
			m_columns.Add( m_provider.ListaIdNumeroistanza, m_numeroIstanza);
			m_columns.Add( m_provider.ListaIdDatapresentazione, m_dataPresentazione);
			m_columns.Add( m_provider.ListaIdTipointervento, m_tipoIntervento);
			m_columns.Add( m_provider.ListaIdStato, m_stato);
			m_columns.Add( m_provider.ListaIdNumeroprotocollo, m_numeroProtocollo);
			m_columns.Add( m_provider.ListaIdDataprotocollo, m_dataProtocollo);
			//m_columns.Add( m_provider.ListaIdCivico, m_civico);
			m_columns.Add( m_provider.ListaIdTipocatasto, m_tipoCatasto);
            m_columns.Add( m_provider.ListaIdRagioneSociale, _ragioneSociale);


			this.PageIndexChanging += new GridViewPageEventHandler(VisuraDataGrid_PageIndexChanged);
			this.SelectedIndexChanged += new EventHandler(VisuraDataGrid_SelectedIndexChanged);

		}

	}
}
