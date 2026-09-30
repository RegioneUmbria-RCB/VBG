using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class visura_localizzazioni : System.Web.UI.UserControl
    {
        public class VisuraLocalizzazioniDataSource
        {
            public IEnumerable<IstanzeStradario> Stradario { get; set; }
            public IEnumerable<IstanzeMappali> Mappali { get; set; }
        }

        public VisuraLocalizzazioniDataSource DataSource { get; set; }

        public bool MostraDatiCatastaliEstesi
        {
            get { object o = this.ViewState["MostraDatiCatastaliEstesi"]; return o == null ? false : (bool)o; }
            set { this.ViewState["MostraDatiCatastaliEstesi"] = value; }
        }


        protected void Page_Load(object sender, EventArgs e)
        {

        }

        public override void DataBind()
        {
            dgLocalizzazioni.Columns[3].Visible = MostraDatiCatastaliEstesi;
            dgLocalizzazioni.Columns[4].Visible = MostraDatiCatastaliEstesi;
            dgLocalizzazioni.Columns[5].Visible = MostraDatiCatastaliEstesi;
            dgLocalizzazioni.Columns[6].Visible = MostraDatiCatastaliEstesi;

            dgLocalizzazioni.DataSource = this.DataSource.Stradario;
            dgLocalizzazioni.DataBind();

            dgDatiCatastali.DataSource = this.DataSource.Mappali;
            dgDatiCatastali.DataBind();
        }
    }
}