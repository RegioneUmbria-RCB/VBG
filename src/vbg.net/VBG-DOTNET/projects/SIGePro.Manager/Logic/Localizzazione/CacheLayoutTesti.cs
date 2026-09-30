#if NET48
using Init.SIGePro.Manager.Authentication;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Text;
using System.Web;

namespace Init.SIGePro.Manager.Logic.Localizzazione
{
    public partial class CacheLayoutTesti
    {
        private static readonly Dictionary<string, Dictionary<string, string>> m_cacheTestiComuni = new Dictionary<string, Dictionary<string, string>>();
        private static readonly Dictionary<string, string> m_cacheTesti = new Dictionary<string, string>();
        private readonly IAuthenticationManager _authenticationManager;

        public CacheLayoutTesti(IAuthenticationManager authenticationManager)
        {
            this._authenticationManager = authenticationManager;
            this.CaricaCacheTestiBase(HttpContext.Current.Items["Token"].ToString());

        }

        private void CaricaCacheTestiBase(string token)
        {
            if (m_cacheTesti.Count != 0)
            {
                return;
            }

            if (String.IsNullOrEmpty(token))
                throw new ArgumentException("token");

            AuthenticationInfo authInfo = this._authenticationManager.CheckToken(token);

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


            AuthenticationInfo authInfo = this._authenticationManager.CheckToken(token);

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
                this.CaricaLayoutTestiLocalizzato(token, idComune);

            return this.GetResourceStringInternal(idComune, software, chiave);
        }

        private string GetResourceStringInternal(string idComune, string software, string key)
        {
            string chiave = software + "$" + key;

            if (m_cacheTestiComuni[idComune].ContainsKey(chiave))
                return m_cacheTestiComuni[idComune][chiave];

            chiave = "TT$" + key;

            if (m_cacheTestiComuni[idComune].ContainsKey(chiave))
                return m_cacheTestiComuni[idComune][chiave];

            return this.GetResourceStringInternal(software, key);
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
#endif