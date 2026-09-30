using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche.Backend;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Drawing;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.Frontend.AppLogic.WsAnagraficheService;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
    /// <summary>
    /// Descrizione di riepilogo per RicercaRichiedente.
    /// </summary>
    [ToolboxData("<{0}:RicercaRichiedente runat=server></{0}:RicercaRichiedente>")]
    public class RicercaRichiedente : WebControl, IDatabaseControl
    {
        [Inject]
        public IAnagraficheBackendService _anagrafeRepository { get; set; }

        private readonly TextBox m_textBox = new TextBox();
        private readonly Button m_button = new Button();
        private readonly Button m_cancelButton = new Button();
        private readonly Label m_label = new Label();
        private readonly Label m_errorLabel = new Label();

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
            this.m_textBox.Text = "";
            this.m_textBox.Columns = 20;
            this.m_textBox.MaxLength = 16;
            this.m_label.Text = "";
            this.m_button.Text = "Cerca";
            this.m_errorLabel.ForeColor = Color.Red;
            this.m_errorLabel.Text = "Richiedente non trovato";
            this.m_cancelButton.Text = "Nuova ricerca";


            // textbox di ricerca
            this.m_textBox.TextChanged += new EventHandler(this.m_button_Click);
            this.m_textBox.AutoPostBack = true;

            this.Init += new EventHandler(this.RicercaStradario_Init);
            this.Load += new EventHandler(this.RicercaStradario_Load);
        }


        protected override void CreateChildControls()
        {
            base.CreateChildControls();

            this.Controls.Add(this.m_textBox);
            this.Controls.Add(this.m_button);
            this.Controls.Add(this.m_label);
            this.Controls.Add(this.m_errorLabel);
            this.Controls.Add(this.m_cancelButton);
        }


        private void RicercaStradario_Init(object sender, EventArgs e)
        {
            this.EnsureChildControls();
            this.m_button.Click += new EventHandler(this.m_button_Click);
            this.m_cancelButton.Click += new EventHandler(this.m_cancelButton_Click);
        }

        private void m_button_Click(object sender, EventArgs e)
        {
            string codiceFiscale = this.m_textBox.Text;

            if (codiceFiscale.Length == 0)
            {
                this.Value = "";
                return;
            }

            Anagrafe anagrafe = this._anagrafeRepository.RicercaAnagraficaBackoffice(codiceFiscale.Length == 16 ? TipoPersonaEnum.Fisica : TipoPersonaEnum.Giuridica, codiceFiscale);

            if (anagrafe != null)
            {
                this.SetAnagrafe(anagrafe);
            }
            else
            {
                this.m_errorLabel.Visible = true;
            }
        }

        protected void SetAnagrafe(Anagrafe anagrafe)
        {
            this.Value = anagrafe.CODICEFISCALE;
            this.m_label.Text = anagrafe.NOMINATIVO + " " + anagrafe.NOME + "( cf:" + anagrafe.CODICEFISCALE + " - pi:" + anagrafe.PARTITAIVA + ")";

            this.m_textBox.Visible = false;
            this.m_button.Visible = false;
            this.m_errorLabel.Visible = false;
            this.m_label.Visible = true;
            this.m_cancelButton.Visible = true;
        }

        private void m_cancelButton_Click(object sender, EventArgs e)
        {
            this.m_textBox.Visible = true;
            this.m_label.Visible = false;
            this.m_cancelButton.Visible = false;
            this.m_button.Visible = true;
            this.m_errorLabel.Visible = false;

            this.Value = "";
        }

        private void RicercaStradario_Load(object sender, EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                this.m_textBox.Visible = true;
                this.m_label.Visible = false;
                this.m_cancelButton.Visible = false;
                this.m_button.Visible = true;
                this.m_errorLabel.Visible = false;
            }
        }

        protected override void Render(HtmlTextWriter writer)
        {
            this.RenderChildren(writer);
        }

    }
}