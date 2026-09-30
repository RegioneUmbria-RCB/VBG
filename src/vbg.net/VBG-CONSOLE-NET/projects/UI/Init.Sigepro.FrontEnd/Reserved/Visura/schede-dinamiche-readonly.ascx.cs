using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class schede_dinamiche_readonly : System.Web.UI.UserControl
    {
        [Inject]
        public IVisuraDatiDinamiciService _datiDinamiciService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.ViewStateMode = ViewStateMode.Disabled;
        }

        public int CodiceIstanza
        {
            get { object o = this.ViewState["CodiceIstanza"]; return o == null ? -1 : (int)o; }
            set { this.ViewState["CodiceIstanza"] = value; }
        }

        public bool DaArchivio
        {
            get { return !this.Visible; }
            set { this.Visible = !value; }
        }

        public override void DataBind()
        {
            var schedeIstanza = this._datiDinamiciService.GetTitoliModelli(this.CodiceIstanza);

            rptListaSchede.DataSource = schedeIstanza;
            rptListaSchede.DataBind();
        }

        private ModelloDinamicoIstanza RicaricaScheda(int idScheda)
        {
            return this._datiDinamiciService.GetModello(this.CodiceIstanza, idScheda);
        }

        protected void rptListaSchede_ItemDataBound(object sender, RepeaterItemEventArgs e)
        {
            if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
            {
                var datiScheda = e.Item.DataItem as dynamic;
                var renderer = (ModelloDinamicoRenderer)e.Item.FindControl("renderer");

                var scheda = RicaricaScheda(datiScheda.Id);

                
                //renderer.RicaricaModelloDinamico += (object sender2, EventArgs e2) => RicaricaScheda(datiScheda.Id);
                renderer.DataSource = scheda;
                renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaCompleta());
                renderer.DataBind();
            }
        }

    }
}