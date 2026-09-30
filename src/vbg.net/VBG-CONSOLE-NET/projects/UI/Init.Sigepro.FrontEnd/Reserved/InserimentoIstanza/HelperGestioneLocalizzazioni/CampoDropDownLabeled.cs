using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Utils.Web.UI;
using Init.Sigepro.FrontEnd.WebControls.FormControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	public class CampoDropDownLabeled : CampoLabeled
	{
        public CampoDropDownLabeled(DropDownList controlloEdit)
		{
			this.ControlloEdit = controlloEdit;
		}

		public override string Descrizione
		{
			get
			{
                return (this.ControlloEdit as DropDownList).SelectedItem.Text;
			}
		}

		public override void SvuotaCampo()
		{
            (this.ControlloEdit as DropDownList).SelectedIndex = 0;
		}

	}
}