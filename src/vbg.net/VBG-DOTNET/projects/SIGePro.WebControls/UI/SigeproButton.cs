using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.Localizzazione;
using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Web.UI;
using System.Web.UI.WebControls;
// using Init.SIGePro.Manager.Logic.Localizzazione;


namespace SIGePro.WebControls.UI
{
    [DefaultProperty("DateValue"),
   ToolboxData("<{0}:SigeproButton runat=server />")]
    public partial class SigeproButton : Button
    {
        public string IdRisorsa
        {
            get { var o = this.ViewState["IdRisorsa"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["IdRisorsa"] = value; }
        }
        public SigeproButton()
        {

        }

        protected override void OnPreRender(EventArgs e)
        {
            var testo = String.Empty;

            if (!this.DesignMode && !String.IsNullOrEmpty(this.IdRisorsa))
            {
                var cacheLayoutTesti = StaticKernelContainer.GetService<CacheLayoutTesti>();

                var chiaveRisorsa = "BUTTON." + this.IdRisorsa;
                testo = cacheLayoutTesti.GetTesto(chiaveRisorsa);

                if (String.IsNullOrEmpty(testo))
                {
                    Debug.WriteLine($"Risorsa non trovata: {chiaveRisorsa}");
                }
            }

            if (!String.IsNullOrEmpty(testo))
                this.Text = testo;

            base.OnPreRender(e);
        }
    }
}
