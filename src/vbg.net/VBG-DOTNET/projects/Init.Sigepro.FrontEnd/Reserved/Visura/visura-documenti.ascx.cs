using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.UrlDownloadOggetti;
using Ninject;
using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_documenti : System.Web.UI.UserControl
    {
        [Inject]
        protected IAliasResolver _aliasResolver { get; set; }

        public class VisuraDocumentiListItem
        {
            string _descrizione = String.Empty;
            public string Descrizione
            {
                get { return this._descrizione; }
                set
                {
                    if (String.IsNullOrEmpty(value))
                    {
                        this._descrizione = String.Empty;
                    }
                    else
                        this._descrizione = value.Replace("\n", "<br />");
                }
            }

            public string NomeFile { get; set; }
            private string _md5;
            public string Md5
            {
                get => this._md5;
                set => this._md5 = value?.ToUpper();
            }
            public bool HasMd5 => !String.IsNullOrEmpty(this.Md5);
            public int CodiceOggetto { get; set; }
            public DateTime? Data { get; set; }

            public string UrlDownload { get; set; }
        }

        public IEnumerable<VisuraDocumentiListItem> DataSource { get; set; }

        public bool PermettiDownload
        {
            get { var o = this.ViewState["PermettiDownload"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiDownload"] = value; }
        }

        public bool DaArchivio
        {
            get { return !this.Visible; }
            set { this.Visible = !value; }
        }


        public override void DataBind()
        {
            this.gvAllegati.DataSource = this.DataSource;
            this.gvAllegati.DataBind();
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        //protected string ProcessaDescrizione(object descr)
        //{
        //    if (descr == null) return "";
        //    return descr.ToString().Replace("\n", "<br />");
        //}


        protected void gvAllegati_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                var hlDownloadAllegato = (HyperLink)e.Row.FindControl("hlDownloadAllegato");
                var documento = e.Row.DataItem as VisuraDocumentiListItem;

                hlDownloadAllegato.NavigateUrl = (this.Page as ReservedBasePage).ResolveUrl(documento.UrlDownload);
            }
        }

        protected override void OnPreRender(EventArgs e)
        {
            this.gvAllegati.Columns[this.gvAllegati.Columns.Count - 1].Visible = PermettiDownload;
        }
    }
}