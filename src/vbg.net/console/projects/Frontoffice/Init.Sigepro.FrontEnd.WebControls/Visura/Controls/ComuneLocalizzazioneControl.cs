using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    public class ComuneLocalizzazioneControl : DropDownList
    {
        [Inject]
        public IComuniService _comuniService { get; set; }

        public bool ContieneComuniAssociati
        {
            get { object o = this.ViewState["ContieneComuniAssociati"]; return o == null ? false : (bool)o; }
            set { this.ViewState["ContieneComuniAssociati"] = value; }
        }


        public ComuneLocalizzazioneControl()
        {
            FoKernelContainer.Inject(this);

            this.DataTextField = "Comune";
            this.DataValueField = "CodiceComune";
        }

        public void EnsureInitialized()
        {
            if (this.Items.Count == 0)
                ReloadDataSource();
        }

        protected override void OnLoad(EventArgs e)
        {
            EnsureInitialized();
        }

        private void ReloadDataSource()
        {
            var comuniAssociati = _comuniService.GetComuniAssociati();

            this.ContieneComuniAssociati = comuniAssociati.Count() > 0;

            this.DataSource = comuniAssociati;
            this.DataBind();

            //base.InsertItem(0, String.Empty, String.Empty);
        }
    }
}
