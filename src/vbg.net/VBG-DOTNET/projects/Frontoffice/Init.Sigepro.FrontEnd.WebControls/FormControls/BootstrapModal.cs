

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    using Init.Utils.Web.UI;
    using System;
    using System.Collections.Generic;
    using System.ComponentModel;
    using System.Linq;
    using System.Text;
    using System.Web.UI;
    using System.Web.UI.WebControls;

    public class ModalBody : Panel
    {

    }

    [ParseChildren(true)]
    public class BootstrapModal : Div
    {
        // Proprietà del controllo
        public string Title
        {
            get { object o = this.ViewState["Title"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Title"] = value; }
        }

        public bool ShowOkButton
        {
            get { object o = this.ViewState["ShowOkButton"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowOkButton"] = value; }
        }

        public string ExtraCssClass
        {
            get { object o = this.ViewState["ExtraCssClass"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["ExtraCssClass"] = value; }
        }

        public bool ShowKoButton
        {
            get { object o = this.ViewState["ShowKoButton"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowKoButton"] = value; }
        }

        public bool ShowFooter
        {
            get { object o = this.ViewState["ShowFooter"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ShowFooter"] = value; }
        }

        public bool OpenOnStart
        {
            get { object o = this.ViewState["OpenOnStart"]; return o == null ? false : (bool)o; }
            set { this.ViewState["OpenOnStart"] = value; }
        }

        public string OkCssClass
        {
            get { object o = this.ViewState["OkCssClass"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["OkCssClass"] = value; }
        }
        

        public string OkText
        {
            get { object o = this.ViewState["OkTextx"]; return o == null ? "Ok" : (string)o; }
            set { this.ViewState["OkTextx"] = value; }
        }

        public string KoText
        {
            get { object o = this.ViewState["KoText"]; return o == null ? "Chiudi" : (string)o; }
            set { this.ViewState["KoText"] = value; }
        }

        public string AppendToElement
        {
            get { object o = this.ViewState["AppendToElement"]; return o == null ? "$('#aspnetForm')" : (string)o; }
            set { this.ViewState["AppendToElement"] = value; }
        }

        public bool OkUseSubmitBehavior
        {
            get { object o = this.ViewState["OkUseSubmitBehavior"]; return o == null ? true : (bool)o; }
            set { this.ViewState["OkUseSubmitBehavior"] = value; }
        }

        public bool Fade
        {
            get { object o = this.ViewState["Fade"]; return o == null ? true : (bool)o; }
            set { this.ViewState["Fade"] = value; }
        }


        // Eventi
        public event EventHandler OkClicked;
        public event EventHandler KoClicked;
        private Button _okButton;

        ModalBody _modalBody = null;

        [Browsable(false)]
        [PersistenceMode(PersistenceMode.InnerProperty)]
        public ModalBody ModalBody {
            get
            {
                if (this._modalBody == null)
                {
                    this._modalBody = new ModalBody();
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

            EnsureChildControls();
        }

        protected override void CreateChildControls()
        {
            base.CreateChildControls();

            InizializzaAttributi();
            InizializzaControlliFiglio();
        }

        private void InizializzaControlliFiglio()
        {
            var modalDialog = new Div();
            var contentDiv = new Div();
            var headerDiv = new Div();
            var bodyDiv = new Div();
            var footerDiv = new Div();
            var title = new Literal();
            this._okButton = new Button();
            var buttonKo = new LinkButton();

            contentDiv.CssClass = "modal-content";

            // HEader
            title.Text = String.Format("<h3 style='margin: 0;'>{0}</h3>", this.Title);

            headerDiv.CssClass = "modal-header";
            headerDiv.Controls.Add(title);

            contentDiv.Controls.Add(headerDiv);

            // Body
            bodyDiv.CssClass = "modal-body";
            bodyDiv.Controls.Add(this.ModalBody);

            contentDiv.Controls.Add(bodyDiv);

            // Footer
            footerDiv.CssClass = "modal-footer";

            this._okButton.CssClass = "btn btn-primary modal-ok-button";
            this._okButton.Text = this.OkText;
            this._okButton.Visible = this.ShowOkButton;

            if (!OkUseSubmitBehavior)
            {
                this._okButton.UseSubmitBehavior = false;
            }

            if (this.OkClicked != null)
            {
                this._okButton.Click += OkClicked;
            }

            buttonKo.CssClass = "btn btn-default modal-ko-button";
            buttonKo.Text = this.KoText;
            buttonKo.Visible = this.ShowKoButton;

            if (this.KoClicked != null)
            {
                buttonKo.Click += KoClicked;
            }
            else
            {
                buttonKo.Attributes.Add("data-dismiss", "modal");
                buttonKo.Attributes.Add("data-disable", "false");
            }

            footerDiv.Visible = this.ShowFooter;
            footerDiv.Controls.Add(buttonKo);
            footerDiv.Controls.Add(this._okButton);

            contentDiv.Controls.Add(footerDiv);

            // Fine
            modalDialog.CssClass = "modal-dialog modal-m";
            modalDialog.Controls.Add(contentDiv);

            this.Controls.Add(modalDialog);
        }

        private void InizializzaAttributi()
        {
            this.Attributes.Add("data-backdrop", "static");
            this.Attributes.Add("data-keyboard", "false");
            this.Attributes.Add("role", "dialog");
            this.Attributes.Add("aria-hidden", "true");

            var style = "overflow-y: visible;";

            if (!this.OpenOnStart)
            {
                style += "display: none;";
            }

            this.Attributes.Add("style", style);
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.CssClass = $"modal {(Fade ? "fade ": "")}{this.ExtraCssClass}";


            if (!String.IsNullOrEmpty(this.OkCssClass))
            {
                this._okButton.CssClass += $" {OkCssClass}";
            }

            if (this.AppendToElement != null)
            {
                this.Page.ClientScript.RegisterStartupScript(this.GetType(), this.ClientID + "-modal-append-to", "$(function (){ $('#" + this.ClientID + "').appendTo(" + this.AppendToElement + ");});", true);
            }

            base.OnPreRender(e);
        }
    }
}
