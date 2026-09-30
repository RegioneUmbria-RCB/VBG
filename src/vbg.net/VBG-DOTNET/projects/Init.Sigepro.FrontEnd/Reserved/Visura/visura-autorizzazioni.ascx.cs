using System;
using System.Collections.Generic;

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
            public DateTime? DataScadenza { get; set; }
            public DateTime? DataCessazione { get; set; }
            public bool Attiva { get; internal set; }

            public string Stato => this.Attiva ? "Attiva" : "Cessata";
        }

        public IEnumerable<VisuraAutorizzazioniListItem> DataSource { get; set; }


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