//using Init.Sigepro.FrontEnd.AppLogic.Readers;

using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Ninject;
using System;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
    /// <summary>
    /// Combo che contiene i dati della tabella Titoli
    /// </summary>
    [ToolboxData("<{0}:ComboTitoli runat=server></{0}:ComboTitoli>")]
    public class ComboTitoli : FilteredDropDownList
    {
        [Inject]
        public ITitoliRepository _titoliRepository { get; set; }


        public ComboTitoli()
        {
            FoKernelContainer.Inject(this);
        }

        protected override void CreateChildControls()
        {
            this.DataTextField = "TITOLO";
            this.DataValueField = "CODICETITOLO";

            base.CreateChildControls();
        }


        public override void DataBind()
        {
            this.EnsureChildControls();


            var titoli = this._titoliRepository.GetList();

            this.Items.Clear();
            this.Items.Add(new ListItem("Selezionare...", String.Empty));

            foreach (var t in titoli)
            {
                this.Items.Add(new ListItem(t.Titolo, t.CodiceTitolo));
            }

            base.DataBind();
        }

    }
}