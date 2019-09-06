using System;
using System.Configuration;
using System.Reflection;
using System.Web.UI.WebControls;
//using PersonalLib2.Data;

namespace Init.Sigepro.FrontEnd.WebControls.Common
{
	/// <summary>
	/// Una combo che utilizza le chiavi di filtro per accdere ai dati
	/// </summary>
	public class FilteredDropDownList : DropDownList , IDatabaseSoftwareControl
	{
		/*
		private DataBase m_database;
		public DataBase Database
		{
			get { return m_database; }
			set { m_database = value; }
		}
*/
		public string IdComune
		{
			get
			{
				object o = this.ViewState["IdComune"];

				if (o == null)
				{
					EnsureParameters();

					o = this.ViewState["IdComune"];

					if (o == null)
					{
						throw new ConfigurationErrorsException(this.ToString() + ": parametro idComune non impostato");
					}
				}

				return o.ToString();
			}

			set { this.ViewState["IdComune"] = value; }
		}


		public string Software
		{
			get
			{
				object o = this.ViewState["Software"];

				if (o == null) throw new ConfigurationErrorsException(this.ToString() + ": parametro Software non impostato");

				return o.ToString();
			}

			set { this.ViewState["Software"] = value; }
		}




		public FilteredDropDownList()
		{
			this.Load += new EventHandler(FilteredDropDownList_Load);
		}

		private void FilteredDropDownList_Load(object sender, EventArgs e)
		{
			EnsureParameters();
		}

		private void EnsureParameters()
		{
			Type t = this.Page.GetType();
			PropertyInfo property = t.GetProperty("IdComune");

			if (property != null)
			{
				this.IdComune = property.GetValue(this.Page, null).ToString();
			}


			property = t.GetProperty("Software");

			if (property != null)
			{
				this.Software = property.GetValue(this.Page, null).ToString();
			}
		}
	}
}