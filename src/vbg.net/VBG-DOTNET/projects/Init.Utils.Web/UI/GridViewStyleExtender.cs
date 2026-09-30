using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [DefaultProperty("AssociatedControl"),
   ToolboxData("<{0}:GridViewStyleExtender runat=server />")]
    public partial class GridViewStyleExtender : Control
    {
        private static class DefaultProperties
        {
            public const string GvStyle_HeaderCsscClass = "header";
            public const string GvStyle_RowCssClass = "odd";
            public const string GvStyle_AltRowCssClass = "even";
            public const string GvStyle_FooterCssClass = "footer";
            public const string GvStyle_EditRowCssClass = "edit";
            public const string GvStyle_SelectedRowCssClass = "selected";
            public const string GvStyle_GridCssClass = "table";
            public const string GvStyle_PagerCssClass = "pager";
        }

        [DefaultValue(""), IDReferenceProperty, TypeConverter(typeof(GridViewConverter))]
        public string AssociatedControl
        {
            get { object o = this.ViewState["AssociatedControlId"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["AssociatedControlId"] = value; }
        }


        protected override void OnLoad(EventArgs e)
        {
            base.OnLoad(e);

            if (!this.DesignMode && String.IsNullOrEmpty(this.AssociatedControl))
            {
                throw new ArgumentException("Il controllo " + this.ID + " non è associato ad una gridView");
            }

            if (!String.IsNullOrEmpty(this.AssociatedControl))
            {
                GridView gv = (GridView)this.NamingContainer.FindControl(this.AssociatedControl);
                gv.CssClass = DefaultProperties.GvStyle_GridCssClass;
                gv.RowStyle.CssClass = DefaultProperties.GvStyle_RowCssClass;
                gv.AlternatingRowStyle.CssClass = DefaultProperties.GvStyle_AltRowCssClass;
                gv.SelectedRowStyle.CssClass = DefaultProperties.GvStyle_SelectedRowCssClass;
                gv.HeaderStyle.CssClass = DefaultProperties.GvStyle_HeaderCsscClass;
                gv.FooterStyle.CssClass = DefaultProperties.GvStyle_FooterCssClass;
                gv.PagerStyle.CssClass = DefaultProperties.GvStyle_PagerCssClass;
            }
        }

        protected override void Render(HtmlTextWriter writer)
        {
            if (this.DesignMode)
            {
                writer.Write("GridViewExtender[" + this.AssociatedControl + "]");
            }

            base.Render(writer);
        }
    }
}
