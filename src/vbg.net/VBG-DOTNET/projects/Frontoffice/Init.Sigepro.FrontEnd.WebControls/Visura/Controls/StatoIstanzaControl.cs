using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.WebControls.Visura.Controls
{
    /// <summary>
    /// Descrizione di riepilogo per StatoIstanzaControl.
    /// </summary>
    public class StatoIstanzaControl : DropDownList
    {
        [Inject]
        public IStatiIstanzaRepository _statiIstanzaRepository { get; set; }
        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }

        public StatoIstanzaControl()
        {
            FoKernelContainer.Inject(this);

            base.DataValueField = "CODICESTATO";
            base.DataTextField = "STATO";
        }


        protected override void OnLoad(EventArgs e)
        {
            if (this.Items.Count == 0)
                this.ReloadDataSource();
        }


        private void ReloadDataSource()
        {
            this.DataSource = this._statiIstanzaRepository.GetList(this._aliasSoftwareResolver.Software);
            this.DataBind();

            base.InsertItem(0, String.Empty, String.Empty);
        }
    }
}