using System;
using System.Collections.Generic;

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