using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Init.Utils.Web.UI;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.HelperGestioneLocalizzazioni
{
	public class CampoDropDownLabeled : CampoLabeled
	{
		public CampoDropDownLabeled(LabeledDropDownList controlloEdit)
		{
			this.ControlloEdit = controlloEdit;
		}

		public override string Descrizione
		{
			get
			{
				return (this.ControlloEdit as LabeledDropDownList).Item.SelectedItem.Text;
			}
		}

		public override void SvuotaCampo()
		{
			(this.ControlloEdit as LabeledDropDownList).Item.SelectedIndex = 0;
		}

	}
}