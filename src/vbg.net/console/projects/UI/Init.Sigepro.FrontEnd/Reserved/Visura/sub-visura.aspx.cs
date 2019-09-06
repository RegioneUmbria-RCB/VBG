using Init.Sigepro.FrontEnd.QsParameters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class sub_visura : ReservedBasePage
    {

        QsUuidIstanza IdIstanza
        {
            get { return new QsUuidIstanza(Request.QueryString); }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                DataBind();
            }
        }

        public override void DataBind()
        {
            this.visuraExCtrl.EffettuaVisuraIstanza(IdComune, Software, IdIstanza.Value);
        }
    }
}