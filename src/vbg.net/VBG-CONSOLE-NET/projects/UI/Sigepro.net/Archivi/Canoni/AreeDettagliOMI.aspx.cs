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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Exceptions;

namespace Sigepro.net.Archivi.Canoni
{
    public partial class AreeDettagliOMI : BasePage
    {

        protected int? Anno
        {
            get 
            {
                if(String.IsNullOrEmpty(Request.QueryString["Anno"]))
                    return null;

                return  Convert.ToInt16(Request.QueryString["Anno"]);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
            this.cmdChiudi.Attributes.Add("onClick","self.close();");
        }

        protected void cmdSalva_Click(object sender, EventArgs e)
        {
            try
            {
                CanoniConfigAree c = new CanoniConfigAree();
                c.Anno = Anno;
                c.Codicearea = null;
                
                if(!String.IsNullOrEmpty(this.rplAree.Value))
                    c.Codicearea = Convert.ToInt32(this.rplAree.Value);

                c.CoefficienteOMI = this.txCoefficienteOMI.Item.ValoreDouble;
                c.Idcomune = IdComune;

                CanoniConfigAreeMgr mgr = new CanoniConfigAreeMgr(Database);
                mgr.Insert(c);

                ClientScript.RegisterStartupScript(this.GetType(), "chiudi", "<script language='javascript' type='text/javascript'>ClosePopUp();</script>");
            }
            catch (RequiredFieldException rfe)
            {
                MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
            }
            catch (Exception ex)
            {
                MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
            }
        }
    }
}
