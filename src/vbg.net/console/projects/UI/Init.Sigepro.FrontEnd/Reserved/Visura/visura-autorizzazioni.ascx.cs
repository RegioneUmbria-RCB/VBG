using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_autorizzazioni : System.Web.UI.UserControl
    {
        public class VisuraAutorizzazioniListItem
        {
            public string Descrizione { get; set; }
            public DateTime? Data { get; set; }
            public string Note { get; set; }
            public string Numero { get; set; }
        }

        public IEnumerable<VisuraAutorizzazioniListItem> DataSource { get; set; }

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
            this.dgAutorizzazioni.DataSource = this.DataSource;
            this.dgAutorizzazioni.DataBind();
        }
    }
}