using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Linq;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class PopupInserisciAssegnazione : BasePage
    {
        public static class Constants
        {
            public const string qsIdModello = "idModello";
        }

        public int IdModello
        {
            get { return Convert.ToInt32(this.Request.QueryString[Constants.qsIdModello]); }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            var campi = new Dyn2ModelliDMgr(this.Database).GetSoloCampiDinamiciModello(this.IdComune, this.IdModello);

            this.rptListaCampi.DataSource = campi.OrderBy(x => x.Posverticale).ThenBy(x => x.Posorizzontale);
            this.rptListaCampi.DataBind();
        }
    }
}