using System;
using System.Drawing;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per RicercaRichiedente.
	/// </summary>
	[ToolboxData("<{0}:RicercaRichiedente runat=server></{0}:RicercaRichiedente>")]
	public class RicercaRichiedente : WebControl , IDatabaseControl
	{
		[Inject]
		public IAnagraficheService _anagrafeRepository { get; set; }

		private TextBox m_textBox = new TextBox();
		private Button m_button = new Button();
		private Button m_cancelButton = new Button();
		private Label m_label = new Label();
		private Label m_errorLabel = new Label();

		public string IdComune
		{
			get
			{
				object o = this.ViewState["IdComune"];
				return o == null ? "" : o.ToString();
			}
			set { this.ViewState["IdComune"] = value; }
		}

		public string Value
		{
			get
			{
				object o = this.ViewState["Value"];
				return o == null ? "" : o.ToString();
			}
			set { this.ViewState["Value"] = value; }
		}

		public RicercaRichiedente()
		{
			FoKernelContainer.Inject(this);


			// inizializzazione dei controlli
			m_textBox.Text = "";
			m_textBox.Columns = 20;
			m_textBox.MaxLength = 16;
			m_label.Text = "";
			m_button.Text = "Cerca";
			m_errorLabel.ForeColor = Color.Red;
			m_errorLabel.Text = "Richiedente non trovato";
			m_cancelButton.Text = "Nuova ricerca";


			// textbox di ricerca
			m_textBox.TextChanged += new EventHandler(m_button_Click);
			m_textBox.AutoPostBack = true;

			this.Init += new EventHandler(RicercaStradario_Init);
			this.Load += new EventHandler(RicercaStradario_Load);
		}


		protected override void CreateChildControls()
		{
			base.CreateChildControls();

			this.Controls.Add(m_textBox);
			this.Controls.Add(m_button);
			this.Controls.Add(m_label);
			this.Controls.Add(m_errorLabel);
			this.Controls.Add(m_cancelButton);
		}


		private void RicercaStradario_Init(object sender, EventArgs e)
		{
			EnsureChildControls();
			m_button.Click += new EventHandler(m_button_Click);
			m_cancelButton.Click += new EventHandler(m_cancelButton_Click);
		}

		private void m_button_Click(object sender, EventArgs e)
		{
			string codiceFiscale = m_textBox.Text;

			if (codiceFiscale.Length == 0)
			{
				this.Value = "";
				return;
			}

			Anagrafe anagrafe = _anagrafeRepository.RicercaAnagraficaBackoffice(IdComune, codiceFiscale.Length == 16 ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica, codiceFiscale);

			if (anagrafe != null)
			{
				SetAnagrafe(anagrafe);
			}
			else
			{
				m_errorLabel.Visible = true;
			}
		}

		protected void SetAnagrafe(Anagrafe anagrafe)
		{
			Value = anagrafe.CODICEFISCALE;
			m_label.Text = anagrafe.NOMINATIVO + " " + anagrafe.NOME + "( cf:" + anagrafe.CODICEFISCALE + " - pi:" + anagrafe.PARTITAIVA + ")";

			m_textBox.Visible = false;
			m_button.Visible = false;
			m_errorLabel.Visible = false;
			m_label.Visible = true;
			m_cancelButton.Visible = true;
		}

		private void m_cancelButton_Click(object sender, EventArgs e)
		{
			m_textBox.Visible = true;
			m_label.Visible = false;
			m_cancelButton.Visible = false;
			m_button.Visible = true;
			m_errorLabel.Visible = false;

			this.Value = "";
		}

		private void RicercaStradario_Load(object sender, EventArgs e)
		{
			if (!this.Page.IsPostBack)
			{
				m_textBox.Visible = true;
				m_label.Visible = false;
				m_cancelButton.Visible = false;
				m_button.Visible = true;
				m_errorLabel.Visible = false;
			}
		}

		protected override void Render(HtmlTextWriter writer)
		{
			RenderChildren(writer);
		}

	}
}