using Init.Utils.Web.UI;
using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SIGePro.WebControls.Bootstrap
{


    public class ModalBodyItem : Panel
    {

    }

    [ParseChildren(true)]
    public class BootstrapModal : Div
    {
        // Proprietà del controllo
        public string Title
        {
            get { var o = this.ViewState["Title"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Title"] = value; }
        }

        public bool ShowOkButton
        {
            get { var o = this.ViewState["ShowOkButton"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowOkButton"] = value; }
        }

        public string ExtraCssClass
        {
            get { var o = this.ViewState["ExtraCssClass"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ExtraCssClass"] = value; }
        }

        public bool ShowKoButton
        {
            get { var o = this.ViewState["ShowKoButton"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowKoButton"] = value; }
        }

        public bool ShowFooter
        {
            get { var o = this.ViewState["ShowFooter"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowFooter"] = value; }
        }

        public bool AlwaysVisible
        {
            get { var o = this.ViewState["AlwaysVisible"]; return o == null ? false : (bool)o; }
            set { this.ViewState["AlwaysVisible"] = value; }
        }

        public string OkCssClass
        {
            get { var o = this.ViewState["OkCssClass"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["OkCssClass"] = value; }
        }


        public string OkText
        {
            get { var o = this.ViewState["OkTextx"]; return o == null ? "Conferma" : (string)o; }
            set { this.ViewState["OkTextx"] = value; }
        }

        public string KoText
        {
            get { var o = this.ViewState["KoText"]; return o == null ? "Annulla" : (string)o; }
            set { this.ViewState["KoText"] = value; }
        }

        public bool NoValidate
        {
            get { var o = this.ViewState["NoValidate"]; return o == null ? false : (bool)o; }
            set { this.ViewState["NoValidate"] = value; }
        }


        // Eventi
        public event EventHandler OkClicked;
        public event EventHandler KoClicked;
        private Button _okButton;
        private ModalBodyItem _modalBody = null;

        [Browsable(false)]
        [PersistenceMode(PersistenceMode.InnerProperty)]
        public ModalBodyItem ModalBody
        {
            get
            {
                if (this._modalBody == null)
                {
                    this._modalBody = new ModalBodyItem();
                }

                return this._modalBody;
            }
            set
            {
                this._modalBody = value;
            }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            this.EnsureChildControls();
        }

        protected override void CreateChildControls()
        {
            base.CreateChildControls();

            this.InizializzaAttributi();
            this.InizializzaControlliFiglio();
        }

        private void InizializzaControlliFiglio()
        {
            this.CssClass = $"vbg-modal {this.ExtraCssClass}";


            var mainDiv = new Div();
            var headerDiv = new Div();
            var bodyDiv = new Div();
            var footerDiv = new Div();
            var title = new Literal();
            var closeIcon = new Literal();
            this._okButton = new Button();
            var buttonKo = new Button();

            mainDiv.CssClass = "vbg-modal-body";

            // HEader
            closeIcon.Text = "<i class=\"fa fa-close\" data-role='toggle-popup'></i>";

            headerDiv.CssClass = "vbg-modal-header";
            headerDiv.Controls.Add(closeIcon);

            title.Text = String.Format("<h1>{0}</h3>", this.Title);
            title.Visible = !String.IsNullOrEmpty(this.Title);

            //bodyDiv.Controls.Add(title);
            headerDiv.Controls.Add(title);

            var contentDiv = new Div();
            contentDiv.CssClass = "vbg-modal-body-content";
            contentDiv.Controls.Add(bodyDiv);

            bodyDiv.Controls.Add(this.ModalBody);

            mainDiv.Controls.Add(headerDiv);
            mainDiv.Controls.Add(contentDiv);

            // Footer
            footerDiv.CssClass = "vbg-modal-footer";

            this._okButton.CssClass = "";
            this._okButton.Text = this.OkText;
            this._okButton.Visible = this.ShowOkButton;
            this._okButton.CausesValidation = !this.NoValidate;

            if (this.NoValidate)
            {
                this._okButton.Attributes.Add("formnovalidate", "formnovalidate");
            }

            if (this.OkClicked != null)
            {
                this._okButton.Click += OkClicked;
            }

            buttonKo.UseSubmitBehavior = false;
            buttonKo.Attributes.Add("role", "button");
            buttonKo.Attributes.Add("data-role", "toggle-popup");
            buttonKo.CssClass = "cancel-button";
            buttonKo.Text = this.KoText;
            buttonKo.Visible = this.ShowKoButton;

            if (KoClicked != null)
            {
                buttonKo.Click += this.KoClicked;
            }

            footerDiv.Visible = this.ShowFooter;
            footerDiv.Controls.Add(buttonKo);
            footerDiv.Controls.Add(this._okButton);

            mainDiv.Controls.Add(footerDiv);



            // Fine
            this.Controls.Add(mainDiv);
        }

        private void ButtonKo_Click(object sender, EventArgs e)
        {
            throw new NotImplementedException();
        }

        private void InizializzaAttributi()
        {
            if (this.AlwaysVisible)
            {
                this.Attributes.Add("data-always-visible", "true");
            }
            this.Attributes.Add("data-no-auto-hide", "1");
            /*
                        if (!this.OpenOnStart)
                        {
                            style += "display: none;";
                        }

                        this.Attributes.Add("style", style);
            */
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (this.Visible)
            {
                base.Render(writer);
            }
        }
    }
}
