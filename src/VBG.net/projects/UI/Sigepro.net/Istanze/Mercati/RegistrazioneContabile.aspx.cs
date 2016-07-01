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
using PersonalLib2.Sql;
using System.Collections.Generic;
using Init.Utils.Web.UI;

namespace Sigepro.net.Istanze.Mercati
{
    public partial class RegistrazioneContabile : BasePage
    {
        protected int? IdTestata
        {
            get 
            { 
                if(String.IsNullOrEmpty(Request.QueryString["IdTestata"]))
                    return null;

                return Convert.ToInt32(Request.QueryString["IdTestata"]);
            }
        }


        protected void Page_Load(object sender, EventArgs e)
        {
            if (!Page.IsPostBack)
            {
                BindDettaglio();
                this.cmdChiudi.Attributes.Add("OnClick", "self.close();");
            }
        }

        private void BindDettaglio()
        {
            MercatiPresenzeT m = new MercatiPresenzeTMgr(Database).GetById(IdComune, IdTestata.GetValueOrDefault(int.MinValue), useForeignEnum.Yes);

            this.rplMercato.Value = m.Fkcodicemercato.ToString();
            this.rplMercato.Text = m.Mercato.Descrizione;

            this.rplMercatiUso.Value = m.Fkidmercatiuso.ToString();
            this.rplMercatiUso.Text = m.MercatiUso.Descrizione;

            MercatiPresenzeD filtro = new MercatiPresenzeD();
            filtro.Idcomune = IdComune;
            filtro.Fkidtestata = IdTestata;
            filtro.Spuntista = 1;
            filtro.OthersWhereClause.Add("MERCATIPRESENZE_D.FKIDPOSTEGGIO IS NOT NULL");
            filtro.UseForeign = useForeignEnum.Yes;

            this.rptPresenze.DataSource = new MercatiPresenzeDMgr(Database).GetList(filtro);
            this.rptPresenze.DataBind();
        }

        protected void rptPresenze_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.AlternatingItem || e.Item.ItemType == ListItemType.Item)
            {

                MercatiPresenzeD m = (MercatiPresenzeD)e.Item.DataItem;

                ((IntTextBox)e.Item.FindControl("txIdPosteggio")).ValoreInt = m.Fkidposteggio;
                ((Label)e.Item.FindControl("lblCodicePosteggio")).Text = m.Posteggio.CODICEPOSTEGGIO;
                ((IntTextBox)e.Item.FindControl("txCodiceAnagrafe")).Text = m.Codiceanagrafe;
                ((Label)e.Item.FindControl("lblOccupante")).Text = m.Anagrafe.ToString();
                ((Label)e.Item.FindControl("lblPresenze")).Text = m.Numeropresenze.ToString();

                FloatTextBox impUnitario = ((FloatTextBox)e.Item.FindControl("txImportoUnitario"));
                FloatTextBox impTotale = ((FloatTextBox)e.Item.FindControl("txImportoTotale"));

                impUnitario.ValoreFloat = 0;
                impUnitario.Attributes.Add("onChange", "RicalcolaTotale(" + m.Numeropresenze.ToString() + ",this,'" + impTotale.ClientID + "');");

                impTotale.ValoreFloat = (m.Numeropresenze.GetValueOrDefault(0) * impUnitario.ValoreFloat);

            }
        }
    }
}
