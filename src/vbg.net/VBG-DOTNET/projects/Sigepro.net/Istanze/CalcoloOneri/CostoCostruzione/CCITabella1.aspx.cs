using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class Tabella1 : BasePage
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

        public Tabella1()
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

        /// <summary>
        /// Verifica che siano state inserite tutte le righe necessarie in tabella1
        /// </summary>
        private void VerificaRighe()
        {
            var righeInTabella1 = new CCICalcoliMgr(this.Database).VerificaEsistenzaRigheInTabella1(this.IdComune, this.Istanza.SOFTWARE, this.Calcolo.Id.GetValueOrDefault(int.MinValue), this.Calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));
        }

        public override void DataBind()
        {
            //var cls = new CCITabella1();
            //cls.Idcomune = this.IdComune;
            //cls.FkCcicId = this.Calcolo.Id;
            //cls.UseForeign = useForeignEnum.Yes;
            //cls.OrderBy = "Id asc";

            this.gvDettagli.DataSource = new CCITabella1Mgr(this.Database).GetListByIdCalcolo(this.IdComune, this.Calcolo.Id.Value);
            this.gvDettagli.DataBind();
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            var redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

            var contribMgr = new CCICalcoloTContributoMgr(this.Database);
            var contribt = contribMgr.GetByIdCalcolo(this.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));

            this.Response.Redirect(String.Format(redirUrl, this.Token, this.Istanza.CODICEISTANZA, contribt.FkCcictId));
        }

        protected void cmdProsegui_Click(object sender, EventArgs e)
        {
            var mgr = new CCITabella1Mgr(this.Database);

            foreach (GridViewRow r in this.gvDettagli.Rows)
            {
                var itbAlloggi = (IntTextBox)r.FindControl("itbAlloggi");
                var dtbSu = (DecimalTextBox)r.FindControl("dtbSu");

                var id = Convert.ToInt32(this.gvDettagli.DataKeys[r.RowIndex].Value);

                var cls = mgr.GetById(this.IdComune, id);

                cls.Alloggi = itbAlloggi.ValoreInt;
                cls.Su = dtbSu.ValoreDecimal;

                mgr.Update(cls);
            }

            var redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella2.aspx?Token={0}&IdCalcolo={1}";
            this.Response.Redirect(String.Format(redirUrl, this.Token, this.Calcolo.Id));
        }
    }
}
