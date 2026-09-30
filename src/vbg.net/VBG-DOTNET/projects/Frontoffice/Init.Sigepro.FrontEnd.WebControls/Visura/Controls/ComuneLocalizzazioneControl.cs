using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Init.SIGePro.Manager.DTO.Comuni;
using Ninject;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    public class ComuneLocalizzazioneControl : DropDownList
    {
        [Inject]
        public IComuniAssociatiService _comuniService { get; set; }

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
                this.ReloadDataSource();
        }

        protected override void OnLoad(EventArgs e)
        {
            this.EnsureInitialized();
        }

        private void ReloadDataSource()
        {
            var comuniAssociati = this._comuniService.GetComuniAssociati();

            this.ContieneComuniAssociati = comuniAssociati.Count() > 0;

            if (comuniAssociati.Any())
            {
                var l = comuniAssociati.ToList();
                l.Insert(0, new DatiComuneCompatto
                {
                    CodiceComune = "",
                    Comune = "",
                    Cf = "",
                    Provincia = "",
                    SiglaProvincia = ""
                });

                comuniAssociati = l;
            }

            this.DataSource = comuniAssociati;
            this.DataBind();

            //base.InsertItem(0, String.Empty, String.Empty);
        }
    }
}
