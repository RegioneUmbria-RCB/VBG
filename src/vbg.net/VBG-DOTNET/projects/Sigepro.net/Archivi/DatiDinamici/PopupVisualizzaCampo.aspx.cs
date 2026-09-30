using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Sigepro.net.Archivi.DatiDinamici
{
    public partial class PopupVisualizzaCampo : BasePage
    {
        public static class Constants
        {
            public const string qsIdModello = "idModello";
            public const string qsCampiStatici = "campiStatici";
        }

        public bool CampiStatici
        {
            get
            {
                var qs = this.Request.QueryString[Constants.qsCampiStatici];
                return !String.IsNullOrEmpty(qs) && qs.ToUpperInvariant() == "TRUE";
            }
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

        public class BindingItem
        {
            public string Key { get; set; }
            public string Value { get; set; }
            public int PosOrizzontale { get; set; }
            public int PosVerticale { get; set; }
        }

        public override void DataBind()
        {
            this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

            IEnumerable<BindingItem> campi;

            if (this.CampiStatici)
            {
                campi = new Dyn2ModelliDMgr(this.Database)
                                                .GetCampiStaticiModello(this.IdComune, this.IdModello)
                                                .Select(x => new BindingItem
                                                {
                                                    Key = x.Id.ToString(),
                                                    Value = x.CampoTestuale.ToString(),
                                                    PosOrizzontale = x.Posorizzontale.Value,
                                                    PosVerticale = x.Posverticale.Value
                                                });
            }
            else
            {
                campi = new Dyn2ModelliDMgr(this.Database)
                                                .GetSoloCampiDinamiciModello(this.IdComune, this.IdModello)
                                                .Select(x => new BindingItem
                                                {
                                                    Key = x.CampoDinamico.ToString(),
                                                    Value = x.CampoDinamico.ToString(),
                                                    PosOrizzontale = x.Posorizzontale.Value,
                                                    PosVerticale = x.Posverticale.Value
                                                });
            }

            this.rptListaCampi.DataSource = campi.OrderBy(x => x.PosVerticale).ThenBy(x => x.PosOrizzontale);

            this.rptListaCampi.DataBind();
        }
    }
}