using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using SIGePro.Net;
using Init.SIGePro;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;

namespace Sigepro.net
{
    public partial class ErroriSistema : BasePage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

            Exception ex = Session["EXCEPTION"] as Exception;

            LayoutTestiBaseMgr mgr = new LayoutTestiBaseMgr(Database);

            lblMailSupport.Text = mgr.GetValoreTesto("APPLICATION(MAILSUPPORT)", IdComune, Software);
            lblNumeroVerde.Text = mgr.GetValoreTesto("APPLICATION(NUMEROVERDE)", IdComune, Software);

            lblDescrizione.Text = ex.Message;
            lblSorgente.Text = ex.Source;
            lblPagina.Text = Session["QUERYSTRING"] != null ? Session["QUERYSTRING"].ToString() : String.Empty;
            lblStack.Text = ex.StackTrace;
            lblTarget.Text = ex.TargetSite != null ? ex.TargetSite.ToString() : String.Empty;
        }

        public ErroriSistema()
        {
            VerificaPermessi = false;
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            if (Session["EXCEPTION"] is AccessoNegatoException)
                CloseCurrentPage();
            else
                Response.Redirect(lblPagina.Text);
        }

    }
}
