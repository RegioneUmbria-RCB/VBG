using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Contenuti;

namespace Init.Sigepro.FrontEnd.Sir
{
	public partial class _defaultSir : ContenutiBasePage
	{
		public static class Constants
		{
			public const string UrlRicecrcaContenuti = "~/sir/step2.aspx?alias={0}&Software={1}";
			public const string UrlAreaRiservata = "~/login.aspx?alias={0}&Software={1}";
		}
		

		public string UrlRicercaInterventi
		{
			get
			{
				return String.Format(Constants.UrlRicecrcaContenuti, AliasComune, Software);
			}
		}

		public string UrlAreaRiservata
		{
			get
			{
				return String.Format(Constants.UrlAreaRiservata, AliasComune, Software);
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{

		}
	}
}