using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione;
using SIGePro.Net;
using System;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
    public partial class CCITabellaSnSa : BasePage
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


        public CCITabellaSnSa()
        {
            //VerificaSoftware = false;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            if (!this.IsPostBack)
            {
                this.DataBind();
            }
        }

        public override void DataBind()
        {
            this.dtbSuArt9.ValoreDecimal = this.Calcolo.SuArt9;
            this.dtbSa.ValoreDecimal = this.Calcolo.Sa;
        }

        protected void cmdProsegui_Click(object sender, EventArgs e)
        {
            this.Calcolo.SuArt9 = this.dtbSuArt9.ValoreDecimal;
            this.Calcolo.Sa = this.dtbSa.ValoreDecimal;

            new CCICalcoliMgr(this.Database).Update(this.Calcolo);

            var elab = new ElaboratoreCostoCostruzione(this.Database, this.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));
            elab.Elabora();

            string url = "~/Istanze/CalcoloOneri/CostoCostruzione/DeterminazioneCCRiepilogo.aspx?Token={0}&IdCalcolo={1}";
            this.Response.Redirect(String.Format(url, this.Token, this.Calcolo.Id));
        }

        protected void cmdChiudi_Click(object sender, EventArgs e)
        {
            string fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";
            this.Response.Redirect(String.Format(fmtUrl, this.Token, this.Calcolo.Id));
        }


    }
}
