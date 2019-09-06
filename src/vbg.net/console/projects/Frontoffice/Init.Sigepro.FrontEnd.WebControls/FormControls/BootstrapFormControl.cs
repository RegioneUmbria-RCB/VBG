//using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorse;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.FormControls
{
    public enum BootstrapSize
    {
        None,
        Col1,
        Col2,
        Col3,
        Col4,
        Col5,
        Col6,
        Col7,
        Col8,
        Col9,
        Col10,
        Col11,
        Col12
    }

    public abstract class BootstrapFormControl<T> : WebControl, IControlWithLabel, IBootstrapFormControl where T : WebControl
    {
        //[Inject]
        //public IRisorseService _risorseService { get; set; }

        protected T _innerControl;

        public string Label
        {
            get { object o = this.ViewState["Label"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Label"] = value; }
        }

        public bool Required
        {
            get { object o = this.ViewState["Required"]; return o == null ? false : (bool)o; }
            set { this.ViewState["Required"] = value; }
        }

        protected string GlyphType
        {
            get { object o = this.ViewState["GlyphType"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["GlyphType"] = value; }
        }

        public bool HasFeedback
        {
            get { object o = this.ViewState["HasFeedback"]; return o == null ? true : (bool)o; }
            set { this.ViewState["HasFeedback"] = value; }
        }

        public string Placeholder
        {
            get
            {
                return this._innerControl.Attributes["placeholder"];
            }

            set { this._innerControl.Attributes["placeholder"] = value; }
        }

        public BootstrapSize BtSize
        {
            get { object o = this.ViewState["BtSize"]; return o == null ? BootstrapSize.None : (BootstrapSize)o; }
            set { this.ViewState["BtSize"] = value; }
        }

        public bool HasTooltip
        {
            get { object o = this.ViewState["HasTooltip"]; return o == null ? false : (bool)o; }
            set { this.ViewState["HasTooltip"] = value; }
        }

        public override string ID
        {
            get
            {
                return base.ID;
            }
            set
            {
                base.ID = value;
                this._innerControl.ID = value + "_inner";
            }
        }

        public string HelpText
        {
            get { object o = this.ViewState["HelpText"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["HelpText"] = value; }
        }

        public T Inner
        {
            get
            {
                return this._innerControl;
            }
        }

        public override bool Visible
        {
            get { object o = this.ViewState["Visible"]; return o == null ? true : (bool)o; }
            set { this.ViewState["Visible"] = value; }
        }

        public string InnerAttributes
        {
            get { object o = this.ViewState["InnerAttributes"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["InnerAttributes"] = value; }
        }



        public BootstrapFormControl()
        {
            EnsureChildControls();

            FoKernelContainer.Inject(this);
        }

        protected override void CreateChildControls()
        {
            this._innerControl = CreateInnerControl();

            this.Controls.Add(this._innerControl);

            this._innerControl.CssClass = "form-control";

            base.CreateChildControls();
        }

        protected abstract T CreateInnerControl();
        public abstract string Value { get; set; }


        protected override void Render(System.Web.UI.HtmlTextWriter writer)
        {
            if (!this.Visible)
            {
                return;
            }

            var cssClass = "form-group";

            if (this.HasFeedback)
            {
                cssClass += " has-feedback";
            }

            if (!String.IsNullOrEmpty(this.CssClass))
            {
                cssClass += " " + this.CssClass;
            }

            var size = GetBtSize();

            if (!String.IsNullOrEmpty(size))
            {
                cssClass += " " + size;
            }

            writer.WriteBeginTag("div");
            writer.WriteAttribute("class", cssClass);




            writer.Write(System.Web.UI.HtmlTextWriter.TagRightChar);




            // Label
            if (!String.IsNullOrEmpty(this.Label))
            {
                writer.WriteBeginTag("label");
                writer.WriteAttribute("for", this._innerControl.ClientID);
                writer.WriteAttribute("class", "control-label");
                writer.Write(System.Web.UI.HtmlTextWriter.TagRightChar);

                writer.Write(this.Label);

                if (this.Required)
                {
                    writer.Write("<sup>*</sup>");
                }

                writer.WriteEndTag("label");
            }

            if (!String.IsNullOrEmpty(this.GlyphType))
            {
                writer.Write(@"<div class='input-group'><div class=""input-group-addon"">
                        <span class=""glyphicon glyphicon-" + this.GlyphType + @"""></span>
                    </div>");
            }

            if (this.Required)
            {
                this.Inner.Attributes.Add("required", "true");
            }

            if (this.HasTooltip)
            {
                this._innerControl.CssClass += " has-tooltip";
            }

            if (!String.IsNullOrEmpty(this.InnerAttributes))
            {
                ApplyAttributesToInnerControl();
            }

            RenderChildren(writer);

            if (!String.IsNullOrEmpty(this.GlyphType))
            {
                writer.Write("</div>");
            }

            if (this.HasTooltip)
            {
                var tooltipId = GetTooltipId();
                writer.Write("<span class='glyphicon glyphicon-question-sign control-tooltip' data-control-tooltip='true' data-tooltip-id='" + tooltipId + "'></span>");
            }

            // Testo di Help
            if (!String.IsNullOrEmpty(this.HelpText))
            {
                writer.Write(String.Format("<p class='help-block'>{0}</p>", this.HelpText));
            }
            writer.Write("<div class='help-block with-errors'></div>");



            writer.WriteEndTag("div");
        }

        private void ApplyAttributesToInnerControl()
        {
            var attrList = this.InnerAttributes.Split(';');

            foreach (var attr in attrList)
            {
                var parts = attr.Split('=');

                this.Inner.Attributes.Add(parts[0], parts[1]);
            }
        }

        private string GetTooltipId()
        {
            return this.Page.AppRelativeVirtualPath.ToUpperInvariant().Replace("~/RESERVED/", "") + "." + this.ID;
        }

        private string GetBtSize()
        {
            if (this.BtSize == BootstrapSize.None)
            {
                return String.Empty;
            }


            return "col-md-" + ((int)this.BtSize).ToString();
        }

        public string ErroreCampoObbligatorio
        {
            get { return String.Format("Il campo \"{0}\" deve essere compilato", this.Label); }
        }
    }
}
