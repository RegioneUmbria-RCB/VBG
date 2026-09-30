using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.SIGePro.DatiDinamici.WebControls;
using Ninject;
using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using VBG.DatiDinamici;
using VBG.DatiDinamici.WebControls.MaschereSolaLettura;

namespace Init.Sigepro.FrontEnd.Reserved.Visura
{
    public partial class schede_dinamiche_readonly : Ninject.Web.UserControlBase
    {
        [Inject]
        public IVisuraDatiDinamiciService _datiDinamiciService { get; set; }

        protected void Page_Load(object sender, EventArgs e)
        {
            this.ViewStateMode = ViewStateMode.Disabled;
        }

        public int ConteggioSchede { get; set; } = 0;

        public Istanze Istanza { get; set; }

        public override void DataBind()
        {
            var schedeIstanza = this._datiDinamiciService.GetTitoliModelli(this.Istanza);

            this.rptListaSchede.DataSource = schedeIstanza;
            this.rptListaSchede.DataBind();

            this.ConteggioSchede = schedeIstanza.Count();
        }

        private ModelloDinamicoIstanza RicaricaScheda(int idScheda)
        {
            return this._datiDinamiciService.GetModello(this.Istanza, idScheda);
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