using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	public partial class Default : ContenutiBasePage
	{
		[Inject]
		protected ConfigurazioneContenuti _configurazione { get; set; }

		public bool MostraRicercaAteco
		{
			get
			{
				string s = ConfigurationManager.AppSettings["MostraRicercaAtecoNeiContenuti"];

				bool mostraRicerca = true;

				if (bool.TryParse(s, out mostraRicerca))
					return mostraRicerca;

				return true;
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}
	}
}
