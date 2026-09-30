using Init.Sigepro.FrontEnd.QsParameters;
using System;

namespace Init.Sigepro.FrontEnd.Reserved
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
            this.visuraExCtrl.EffettuaVisuraIstanza(IdIstanza.Value);
        }
    }
}