using System;
using System.Collections.Generic;
using System.Text;
using System.Web;
using Init.SIGePro.Authentication;
using PersonalLib2.Data;
using System.Data;

namespace Init.SIGePro.Manager.Logic.Localizzazione
{
	public partial class CacheLayoutTesti
	{
		const string APPLICATION_KEY = "CacheLayoutTesti";

		public static CacheLayoutTesti Instance
		{
			get
			{
				object cache = HttpContext.Current.Application[APPLICATION_KEY];

				if (cache == null)
					HttpContext.Current.Application[APPLICATION_KEY] = new CacheLayoutTesti();

				return (CacheLayoutTesti)HttpContext.Current.Application[APPLICATION_KEY];
			}
		}




		Dictionary<string, Dictionary<string, string>> m_cacheTestiComuni = new Dictionary<string, Dictionary<string, string>>();
		Dictionary<string, string> m_cacheTesti = new Dictionary<string, string>();

		protected CacheLayoutTesti()
		{
			CaricaCacheTestiBase(HttpContext.Current.Items["Token"].ToString());
		}

		private void CaricaCacheTestiBase(string token)
		{
			if (String.IsNullOrEmpty(token))
				throw new ArgumentException("token");

			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			DataBase db = authInfo.CreateDatabase();

			try
			{
				db.Connection.Open();

				string sql = "select * from layouttestibase";

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							string codice = rd["CODICETESTO"].ToString();
							string software = rd["SOFTWARE"].ToString();
							string testo = rd["TESTO"].ToString();

							string chiave = software + "$" + codice;

							m_cacheTesti[chiave] = testo;
						}
					}
				}
			}
			finally
			{
				db.Connection.Close();
			}
		}

		private void CaricaLayoutTestiLocalizzato(string token, string idComune)
		{
			if (m_cacheTestiComuni.ContainsKey(idComune)) return;

			m_cacheTestiComuni[idComune] = new Dictionary<string, string>();


			AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

			DataBase db = authInfo.CreateDatabase();

			try
			{
				db.Connection.Open();

				string sql = "select * from layouttesti where idcomune=" + db.Specifics.QueryParameterName("IdComune");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));

					using (IDataReader rd = cmd.ExecuteReader())
					{
						while (rd.Read())
						{
							string codice = rd["CODICETESTO"].ToString();
							string software = rd["SOFTWARE"].ToString();
							string testo = rd["NUOVOTESTO"].ToString();

							string chiave = software + "$" + codice;

							m_cacheTestiComuni[idComune][chiave] = testo;
						}
					}
				}
			}
			finally
			{
				db.Connection.Close();
			}

		}





		public string GetTesto(string chiave)
		{
			string idComune = HttpContext.Current.Items["IdComune"].ToString();
			string software = HttpContext.Current.Items["Software"].ToString();
			string token = HttpContext.Current.Items["Token"].ToString();

			if (!m_cacheTestiComuni.ContainsKey(idComune))
				CaricaLayoutTestiLocalizzato(token, idComune);

			return GetResourceStringInternal(idComune, software, chiave);
		}

		private string GetResourceStringInternal(string idComune, string software, string key)
		{
			string chiave = software + "$" + key;

			if (m_cacheTestiComuni[idComune].ContainsKey(chiave))
				return m_cacheTestiComuni[idComune][chiave];

			chiave = "TT$" + key;

			if (m_cacheTestiComuni[idComune].ContainsKey(chiave))
				return m_cacheTestiComuni[idComune][chiave];

			return GetResourceStringInternal(software, key);
		}

		private string GetResourceStringInternal(string software, string key)
		{
			string chiave = software + "$" + key;

			if (m_cacheTesti.ContainsKey(chiave))
				return m_cacheTesti[chiave];

			chiave = "TT$" + key;

			if (m_cacheTesti.ContainsKey(chiave))
				return m_cacheTesti[chiave];

			return String.Empty;
		}
	}
}
