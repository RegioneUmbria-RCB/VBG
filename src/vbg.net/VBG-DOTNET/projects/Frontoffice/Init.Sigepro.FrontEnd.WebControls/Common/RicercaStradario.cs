using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.Common.RicercheStradario;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Ninject;
using System;
//using Init.Sigepro.FrontEnd.AppLogic.Readers;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
    public static class StradarioExtensions
    {

        public static StradarioDto ToStradarioDto(this StradarioEstesoDto x)
        {
            if (x == null)
            {
                return null;
            }

            var sb = new StringBuilder(x.Descrizione);

            if (!String.IsNullOrEmpty(x.LocFraz))
            {
                sb.Append(" - ").AppendFormat(x.LocFraz);
            }

            if (!String.IsNullOrEmpty(x.Cap))
            {
                sb.Append(" (").Append(x.Cap).Append(")");
            }

            return new StradarioDto
            {
                CodiceStradario = x.CodiceStradario,
                NomeVia = sb.ToString()
            };
        }
    }

    /// <summary>
    /// Descrizione di riepilogo per RicercaStradario.
    /// </summary>
    [ToolboxData("<{0}:RicercaStradario runat=server></{0}:RicercaStradario>")]
    public class RicercaStradario : SearchWebControl
    {
        [Inject]
        public IStradarioRepository _stradarioRepository { get; set; }


        private readonly TextBox m_textBox = new TextBox();
        private readonly DataGrid m_dataGrid = new DataGrid();
        private readonly Button m_button = new Button();
        private readonly Button m_cancelButton = new Button();
        private readonly Label m_label = new Label();
        private readonly Label m_errorLabel = new Label();


        public string CodiceComune
        {
            get
            {
                object o = this.ViewState["CodiceComune"];
                return o == null ? "" : o.ToString();
            }
            set { this.ViewState["CodiceComune"] = value; }
        }

        public int Value
        {
            get
            {
                object o = this.ViewState["Value"];
                return o == null ? -1 : (int)o;
            }
            set { this.ViewState["Value"] = value; }
        }

        public RicercaStradario()
        {
            FoKernelContainer.Inject(this);

            // inizializzazione dei controlli
            this.m_textBox.Text = "";
            this.m_textBox.Attributes.Add("placeholder", "Inserire il nome della via e fare click su \"Cerca via\"");
            this.m_textBox.Columns = 50;
            this.m_label.Text = "";
            this.m_button.Text = "Cerca via";
            this.m_errorLabel.ForeColor = Color.Red;
            this.m_errorLabel.Text = "Indirizzo non trovato";
            this.m_cancelButton.Text = "Nuova ricerca";

            // inizializzazione della datagrid
            this.m_dataGrid.Attributes.Add("width", "100%");
            this.m_dataGrid.AutoGenerateColumns = false;
            this.m_dataGrid.DataKeyField = "CodiceStradario";

            BoundColumn descrStradarioColumn = new BoundColumn();
            descrStradarioColumn.DataField = "NomeVia";
            descrStradarioColumn.HeaderText = "Via";

            //BoundColumn localitaColumn = new BoundColumn();
            //localitaColumn.DataField = "LocFraz";
            //localitaColumn.HeaderText = "Localit&agrave;/Frazione";

            //BoundColumn capColumn = new BoundColumn();
            //capColumn.DataField = "Cap";
            //capColumn.HeaderText = "Cap";

            ButtonColumn btncolumn = new ButtonColumn();
            btncolumn.Text = "Seleziona";
            btncolumn.ButtonType = ButtonColumnType.LinkButton;
            btncolumn.CommandName = "Select";

            this.m_dataGrid.Columns.Add(descrStradarioColumn);
            //m_dataGrid.Columns.Add(localitaColumn);
            //m_dataGrid.Columns.Add(capColumn);
            this.m_dataGrid.Columns.Add(btncolumn);


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
            this.Controls.Add(this.m_dataGrid);
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
            this.m_dataGrid.SelectedIndexChanged += new EventHandler(this.m_dataGrid_SelectedIndexChanged);
        }

        private void m_button_Click(object sender, EventArgs e)
        {
            // TODO: modificare le ricerche
            var searchProvider = new CompositeRicercaIndirizzo(new List<IRicercaIndirizzo>
            {
                new RicercaEsatta(this._stradarioRepository, this.IdComune, this.CodiceComune),
                new RicercaEsattaConCodiceComune(this._stradarioRepository, this.IdComune),
                new RicercaParziale(this._stradarioRepository, this.IdComune, this.CodiceComune),
                new RicercaParzialeConCodiceComune(this._stradarioRepository, this.IdComune)
            });
            var testoStradario = this.m_textBox.Text;

            if (testoStradario.Length == 0) return;

            var result = searchProvider.Cerca(testoStradario);

            if (result.Count() == 0)
            {
                this.m_errorLabel.Visible = true;
                return;
            }


            if (result.Count() == 1)
            {
                this.SetStradario(result.First());
            }
            else
            {

                this.m_dataGrid.DataSource = result;
                this.m_dataGrid.DataBind();

                this.m_dataGrid.Visible = true;
                this.m_cancelButton.Visible = true;
                this.m_errorLabel.Visible = false;
                this.m_errorLabel.Visible = false;
                this.m_textBox.Visible = false;
                this.m_button.Visible = false;
            }
        }

        protected void SetStradario(StradarioDto indirizzo)
        {
            this.Value = Convert.ToInt32(indirizzo.CodiceStradario);
            this.m_label.Text = indirizzo.NomeVia;
            this.m_label.Text += "&nbsp;";

            this.m_textBox.Visible = false;
            this.m_button.Visible = false;
            this.m_errorLabel.Visible = false;
            this.m_dataGrid.Visible = false;
            this.m_label.Visible = true;
            this.m_cancelButton.Visible = true;
        }

        private void m_dataGrid_SelectedIndexChanged(object sender, EventArgs e)
        {
            var idStradario = Convert.ToInt32(this.m_dataGrid.DataKeys[this.m_dataGrid.SelectedIndex]);

            var indirizzo = this._stradarioRepository.GetByCodiceStradario(this.IdComune, idStradario);

            this.SetStradario(indirizzo == null ? null : new StradarioDto
            {
                CodiceStradario = indirizzo.CodiceStradario,
                CodViario = indirizzo.CodViario,
                NomeVia = indirizzo.Descrizione
            });
        }

        private void m_cancelButton_Click(object sender, EventArgs e)
        {
            this.m_textBox.Visible = true;
            this.m_dataGrid.Visible = false;
            this.m_label.Visible = false;
            this.m_cancelButton.Visible = false;
            this.m_button.Visible = true;
            this.m_errorLabel.Visible = false;

            this.Value = -1;
        }

        private void RicercaStradario_Load(object sender, EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                this.m_textBox.Visible = true;
                this.m_dataGrid.Visible = false;
                this.m_label.Visible = false;
                this.m_cancelButton.Visible = false;
                this.m_button.Visible = true;
                this.m_errorLabel.Visible = false;
            }

            this.m_button.OnClientClick = "(function () {document.getElementById(\"" + this.m_button.ClientID + "\").value=\'Ricerca in corso ...\'; return true;}())";
        }

        protected override void Render(HtmlTextWriter writer)
        {
            this.RenderChildren(writer);
        }

    }
}