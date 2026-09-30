using System;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd
{
    public partial class AreaRiservataPopupMaster : BaseAreaRiservataMaster
    {
        public bool MostraTitoloPagina
        {
            get { return lblTitoloPagina.Visible; }
            set { lblTitoloPagina.Visible = value; }
        }

        protected override void OnInit(EventArgs e)
        {
            base.OutputErrori = rptErrori;
            base.OutputMessaggiInformativi = rptMessaggi;
            base.OutputMessaggiSuccesso = rptMessaggiSuccesso;

            base.OnInit(e);
        }

        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected override void OnPreRender(EventArgs e)
        {
            if (!IsPostBack)
                lblTitoloPagina.Text = this.Page.Title;

            if (String.IsNullOrEmpty(lblTitoloPagina.Text))
                pnlTitolo.Visible = false;
        }
    }
}
