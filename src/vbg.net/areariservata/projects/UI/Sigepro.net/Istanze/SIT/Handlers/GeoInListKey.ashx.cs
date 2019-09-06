using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Verticalizzazioni;

namespace Sigepro.net.Istanze.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class GeoInListKey : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            try
            {
                string software = HttpContext.Current.Request.QueryString["Software"];
                string stato = HttpContext.Current.Request.QueryString["Stato"];
                string result = String.Empty;

                DataSet ds = new DataSet();

                var datiVerticalizzazione = new VerticalizzazioneIAttivita(AuthenticationInfo.Alias, software);
                
				if (datiVerticalizzazione.Attiva)
                {
                    string sql;

					sql = @"SELECT codicestradario as KEY 
						FROM
						  VW_I_ATTIVITALISTA
						WHERE
                           idcomune = {0} and                
                           software = {1} and
                           attiva = {2} and codicestradario is not null";

                    sql = String.Format(sql, Database.Specifics.QueryParameterName("idcomune"),
                                         Database.Specifics.QueryParameterName("software"),
                                         Database.Specifics.QueryParameterName("attiva"));

                    using (IDbCommand cmd = Database.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(Database.CreateParameter("idcomune", IdComune));
                        cmd.Parameters.Add(Database.CreateParameter("software", software));
                        cmd.Parameters.Add(Database.CreateParameter("attiva", stato == "attive" ? 1 : 0));

                        IDataAdapter da = Database.CreateDataAdapter(cmd);
                        da.Fill(ds);
                    }

                    foreach (DataRow dr in ds.Tables[0].Rows)
                    {
                        //Questo controllo è superfluo avendo già messo il filtro nella query
                        if (dr["KEY"] != DBNull.Value && !string.IsNullOrEmpty(dr["KEY"].ToString()))
                                result += dr["KEY"].ToString() + ";";
                    }
                }
                else
                {
                    //Gestione caso in cui non vengono gestite le attività
                }

                if (string.IsNullOrEmpty(result))
                    context.Response.Write(result);
                else
                    context.Response.Write(result.Remove(result.Length - 1));
            }
            catch (Exception ex)
            {
                context.Response.StatusCode = 500;
                context.Response.Write("Error: " + ex.ToString());
            }


        }

        public bool IsReusable
        {
            get
            {
                return false;
            }
        }
    }
}
