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
using Init.SIGePro.Manager;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;

namespace Sigepro.net.Istanze.Mercati
{
    public partial class AggiungiSpuntista : BasePage
    {
        protected string CodiceMercato
        {
            get
            {
                return Request.QueryString["CodiceMercato"];
            }
        }
        protected string CodiceUso
        {
            get
            {
                return Request.QueryString["CodiceUso"];
            }
        }
        protected int? IdTestata
        {
            get
            {
                return String.IsNullOrEmpty(Request.QueryString["IdTestata"]) ? (int?)null : Convert.ToInt32(Request.QueryString["IdTestata"]);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
			List<Spuntisti> ls = new MercatiPresenzeDMgr(Database).GetSpuntisti(IdComune, this.txNominativo.Value, this.txPresenze.Item.ValoreInt.GetValueOrDefault(int.MinValue));
            this.rptSpuntisti.DataSource = ls;
            this.rptSpuntisti.DataBind();

            this.cmdRiportaSupuntisti.Visible = (this.rptSpuntisti.Items.Count > 0);
        }

        protected void lnkSelectAll_Click(object sender, EventArgs e)
        {
            foreach (RepeaterItem r in rptSpuntisti.Items)
            {
                (r.FindControl("chkSpuntista") as CheckBox).Checked = true;
            }
        }

        protected void cmdRiportaSupuntisti_Click(object sender, EventArgs e)
        {
            foreach (RepeaterItem r in rptSpuntisti.Items)
            {
                if ((r.FindControl("chkSpuntista") as CheckBox).Checked)
                {
                    MercatiPresenzeD m = new MercatiPresenzeD();
                    m.Idcomune = IdComune;
                    m.Fkidtestata = IdTestata;
                    m.Codiceanagrafe = (r.FindControl("txSpuntista") as TextBox).Text;
                    m.Numeropresenze = 1;
                    m.Spuntista = 1;

                    try
                    {
                        MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(Database);
                        mgr.Insert(m, true);
                    }
                    catch (RecordFoundedException)
                    {
                        //lo spuntista è gia stato inserito
                    }
                }
            }

            Page.ClientScript.RegisterStartupScript(this.GetType(), "chiudi", "window.opener.location.replace('RegistraPresenzeMercato.aspx?Token=" + AuthenticationInfo.Token + "&Software=" + Software + "&IdTestata=" + IdTestata.ToString() + "'); self.close();", true);
        }
    }
}
