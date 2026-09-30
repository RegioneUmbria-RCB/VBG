using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using PersonalLib2.Sql;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class Tabella2 : BasePage
    {
        private CCICalcoli m_calcolo = null;
        private Init.SIGePro.Data.Istanze m_istanza = null;

        private CCICalcoli Calcolo
        {
            get
            {
                if (this.m_calcolo == null)
                    this.m_calcolo = new CCICalcoliMgr(this.Database).GetById(this.IdComune, Convert.ToInt32(this.Request.QueryString["IdCalcolo"]));

                return this.m_calcolo;
            }
        }

        private Init.SIGePro.Data.Istanze Istanza
        {
            get
            {
                if (this.m_istanza == null)
                    this.m_istanza = new IstanzeMgr(this.Database).GetById(this.IdComune, this.Calcolo.Codiceistanza.Value);

                return this.m_istanza;
            }
        }

        public override string Software
        {
            get
            {
                return this.Istanza.SOFTWARE;
            }
        }

        public Tabella2()
        {
            //VerificaSoftware = false;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
            {
                this.VerificaRighe();
                this.DataBind();
            }
        }

        private void VerificaRighe()
        {
            var righeInTabella2 = new CCICalcoliMgr(this.Database).VerificaEsistenzaRigheInTabella2(this.IdComune, this.Istanza.SOFTWARE, this.Calcolo.Id.GetValueOrDefault(int.MinValue), this.Calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));
        }

        public override void DataBind()
        {
            var cls = new CCITabella2();
            cls.Idcomune = this.IdComune;
            cls.FkCcicId = this.Calcolo.Id;
            cls.UseForeign = useForeignEnum.Yes;
            cls.OrderBy = "Id asc";

            this.gvDettagli.DataSource = new CCITabella2Mgr(this.Database).GetList(cls);
            this.gvDettagli.DataBind();
        }

        protected void cmdProsegui_Click(object sender, EventArgs e)
        {
            var mgr = new CCITabella2Mgr(this.Database);

            foreach (GridViewRow r in this.gvDettagli.Rows)
            {
                var dtbSuperficie = (DecimalTextBox)r.FindControl("dtbSuperficie");

                int id = Convert.ToInt32(this.gvDettagli.DataKeys[r.RowIndex].Value);

                var cls = mgr.GetById(this.IdComune, id);

                cls.Superficie = dtbSuperficie.ValoreDecimal;

                mgr.Update(cls);
            }

            string redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";
            this.Response.Redirect(String.Format(redirUrl, this.Token, this.Calcolo.Id));
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            string fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella1.aspx?Token={0}&IdCalcolo={1}";
            this.Response.Redirect(String.Format(fmtUrl, this.Token, this.Calcolo.Id), true);
        }
    }
}
