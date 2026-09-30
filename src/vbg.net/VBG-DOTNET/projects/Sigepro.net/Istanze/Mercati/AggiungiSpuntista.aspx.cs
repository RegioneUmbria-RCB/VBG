using Init.SIGePro;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.Mercati
{
    public partial class AggiungiSpuntista : BasePage
    {
        protected string CodiceMercato
        {
            get
            {
                return this.Request.QueryString["CodiceMercato"];
            }
        }
        protected string CodiceUso
        {
            get
            {
                return this.Request.QueryString["CodiceUso"];
            }
        }
        protected int? IdTestata
        {
            get
            {
                return String.IsNullOrEmpty(this.Request.QueryString["IdTestata"]) ? (int?)null : Convert.ToInt32(this.Request.QueryString["IdTestata"]);
            }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
        }

        protected void cmdCerca_Click(object sender, EventArgs e)
        {
            List<Spuntisti> ls = new MercatiPresenzeDMgr(this.Database).GetSpuntisti(this.IdComune, this.txNominativo.Value, this.txPresenze.Item.ValoreInt.GetValueOrDefault(int.MinValue));
            this.rptSpuntisti.DataSource = ls;
            this.rptSpuntisti.DataBind();

            this.cmdRiportaSupuntisti.Visible = (this.rptSpuntisti.Items.Count > 0);
        }

        protected void lnkSelectAll_Click(object sender, EventArgs e)
        {
            foreach (RepeaterItem r in this.rptSpuntisti.Items)
            {
                (r.FindControl("chkSpuntista") as CheckBox).Checked = true;
            }
        }

        protected void cmdRiportaSupuntisti_Click(object sender, EventArgs e)
        {
            foreach (RepeaterItem r in this.rptSpuntisti.Items)
            {
                if ((r.FindControl("chkSpuntista") as CheckBox).Checked)
                {
                    MercatiPresenzeD m = new MercatiPresenzeD();
                    m.Idcomune = this.IdComune;
                    m.Fkidtestata = this.IdTestata;
                    m.Codiceanagrafe = (r.FindControl("txSpuntista") as TextBox).Text;
                    m.Numeropresenze = 1;
                    m.Spuntista = 1;

                    try
                    {
                        MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(this.Database);
                        mgr.Insert(m, true);
                    }
                    catch (ExistingRecordException)
                    {
                        //lo spuntista è gia stato inserito
                    }
                }
            }

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "chiudi", "window.opener.location.replace('RegistraPresenzeMercato.aspx?Token=" + this.AuthenticationInfo.Token + "&Software=" + this.Software + "&IdTestata=" + this.IdTestata.ToString() + "'); self.close();", true);
        }
    }
}
