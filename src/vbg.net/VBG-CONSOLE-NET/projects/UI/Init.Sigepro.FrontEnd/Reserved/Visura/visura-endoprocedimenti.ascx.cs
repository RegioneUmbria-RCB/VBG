using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_endoprocedimenti : System.Web.UI.UserControl
    {
        public class VisuraEndoListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public int NumeroAllegati { get; set; }
            public string Endoprocedimento { get; set; }
            public bool HaAllegati { get { return NumeroAllegati > 0; } }
        }

        public bool DaArchivio
        {
            get { return !this.Visible; }
            set { this.Visible = !value; }
        }

        public string UrlAllegatiEndo
        {
            get
            {
                var page = this.Page as ReservedBasePage;
                var url = UrlBuilder.Url("~/Reserved/ListaAllegatiEndo.aspx", qs =>
                {
                    qs.Add(new QsAliasComune(page.IdComune));
                    qs.Add(new QsSoftware(page.Software));
                });

                return ResolveClientUrl(url);
            }
        }

        public IEnumerable<VisuraEndoListItem> DataSource { get; set; }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.dgProcedimenti.DataSource = this.DataSource;
            this.dgProcedimenti.DataBind();
        }
    }
}