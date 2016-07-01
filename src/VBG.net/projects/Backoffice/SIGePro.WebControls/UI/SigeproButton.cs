using System;
using System.Collections.Generic;
using System.Text;
using System.Web.UI.WebControls;
using System.ComponentModel;
using System.Web.UI;
using System.Web;
using Init.SIGePro.Manager.Logic.Localizzazione;


namespace SIGePro.WebControls.UI
{
	[DefaultProperty("DateValue"),
   ToolboxData("<{0}:SigeproButton runat=server />")]
	public partial class SigeproButton : Button
	{
		public string IdRisorsa
		{
			get { object o = this.ViewState["IdRisorsa"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdRisorsa"] = value; }
		}

		protected override void OnPreRender(EventArgs e)
		{
			string testo = String.Empty;

			if (!DesignMode)
			{
				string chiaveRisorsa = "BUTTON." + IdRisorsa;

				if (!String.IsNullOrEmpty(IdRisorsa))
					testo = CacheLayoutTesti.Instance.GetTesto(chiaveRisorsa);

				if (String.IsNullOrEmpty(testo))
					testo = "[Risorsa non trovata:\"" + chiaveRisorsa + "\"]";
			}
			
			if (!String.IsNullOrEmpty(testo))
				this.Text = testo;
			
			base.OnPreRender(e);
		}
	}
}
