using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_oneri : System.Web.UI.UserControl
    {

        public class VisuraOneriListItem
        {
            public string Causale { get; set; }
            public float Importo { get; set; }
            public DateTime? DataScadenza { get; set; }
            public DateTime? DataPagamento { get; set; }
        }

        public IEnumerable<VisuraOneriListItem> DataSource { get; set; }

        public bool DaArchivio
        {
            get { return !this.Visible; }
            set { this.Visible = !value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            this.dgOneri.DataSource = this.DataSource;
            this.dgOneri.DataBind();
        }
    }
}