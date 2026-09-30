using System;
using System.ComponentModel;
using System.Security.Permissions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Utils.Web.UI
{
    [ToolboxData("<{0}:GridViewEx runat=server />")]
    [SupportsEventValidation, Designer("System.Web.UI.Design.WebControls.GridViewDesigner, System.Design, Version=2.0.0.0, Culture=neutral, PublicKeyToken=b03f5f7f11d50a3a"), DefaultEvent("SelectedIndexChanged"), ControlValueProperty("SelectedValue"), AspNetHostingPermission(SecurityAction.LinkDemand, Level = AspNetHostingPermissionLevel.Minimal), AspNetHostingPermission(SecurityAction.InheritanceDemand, Level = AspNetHostingPermissionLevel.Minimal)]
    public partial class GridViewEx : GridView
    {
        #region gestione del sorting di default
        public string DefaultSortExpression
        {
            get { object o = this.ViewState["DefaultSortExpression"]; return o == null ? String.Empty : o.ToString(); }
            set { this.ViewState["DefaultSortExpression"] = value; }
        }

        public bool DatabindOnFirstLoad
        {
            get { object o = this.ViewState["DatabindOnFirstLoad"]; return o == null ? false : (bool)o; }
            set { this.ViewState["DatabindOnFirstLoad"] = value; }
        }

        protected override void PerformSelect()
        {
            if (!this.Page.IsPostBack && !this.DatabindOnFirstLoad) return;

            base.PerformSelect();
        }


        public SortDirection DefaultSortDirection
        {
            get { object o = this.ViewState["DefaultSortDirection"]; return o == null ? SortDirection.Ascending : (SortDirection)o; }
            set { this.ViewState["DefaultSortDirection"] = value; }
        }

        protected override void OnPreRender(EventArgs e)
        {
            base.OnPreRender(e);

            if (this.HeaderRow?.TableSection != null)
            {
                this.HeaderRow.TableSection = TableRowSection.TableHeader;
            }
        }

        protected override DataSourceSelectArguments CreateDataSourceSelectArguments()
        {
            if (this.DesignMode)
                return base.CreateDataSourceSelectArguments();

            DataSourceSelectArguments dataSourceSelectArguments = base.CreateDataSourceSelectArguments();

            if (string.IsNullOrEmpty(dataSourceSelectArguments.SortExpression) && !string.IsNullOrEmpty(this.DefaultSortExpression))
            {
                dataSourceSelectArguments.SortExpression = this.DefaultSortExpression;

                if (this.DefaultSortDirection == SortDirection.Descending)
                {
                    dataSourceSelectArguments.SortExpression += " DESC";
                }
                else
                {
                    dataSourceSelectArguments.SortExpression += " ASC";
                }
            }

            return dataSourceSelectArguments;
        }

        protected override void OnInit(EventArgs e)
        {
            base.OnInit(e);

            if (!this.DesignMode && String.IsNullOrEmpty(this.SortExpression) && !String.IsNullOrEmpty(this.DefaultSortExpression) && this.AllowSorting)
                this.Sort(this.DefaultSortExpression, this.DefaultSortDirection);

            this.RowDataBound += new GridViewRowEventHandler(this.GridViewEx_RowDataBound);
            this.UseAccessibleHeader = true;
            this.CssClass = "vbg-table";
        }

        private void GridViewEx_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.Header)
            {
                for (int i = 0; i < e.Row.Cells.Count; i++)
                {
                    if (this.Columns[i] == null) continue;

                    if (this.AllowSorting && this.Columns[i].SortExpression == this.SortExpression)
                    {
                        string fmtText = "<span style=\"font-family:webdings\">{0}</span>";
                        string val = this.SortDirection == SortDirection.Ascending ? "5" : "6";

                        if (e.Row.Cells[i].Controls.Count > 0)
                            (e.Row.Cells[i].Controls[0] as LinkButton).Text += string.Format(fmtText, val);
                    }
                }
            }

        }

        #endregion
    }
}
