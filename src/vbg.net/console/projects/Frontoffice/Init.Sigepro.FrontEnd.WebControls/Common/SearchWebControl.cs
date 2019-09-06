using System;
using System.Web.UI.WebControls;
using System.Web;
//using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Descrizione di riepilogo per SearchWebControl.
	/// </summary>
	public class SearchWebControl: WebControl,IDatabaseSoftwareControl
	{/*
		private DataBase m_database;
		public virtual DataBase Database
		{
			get { return m_database; }
			set { m_database = value; }
		}
*/
		public virtual string IdComune
		{
			get
			{
				object o = HttpContext.Current.Request.QueryString["IdComune"];
				return o == null ? "" : o.ToString();
			}
		}

		public virtual string Software
		{
			get
			{
				object o = HttpContext.Current.Request.QueryString["Software"];
				return o == null ? "" : o.ToString();
			}
		}

		public SearchWebControl()
		{
			//
			// TODO: aggiungere qui la logica del costruttore
			//
		}
	}
}
