using Ninject;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Data;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.Istanze.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class GeoInListKey : BaseHandler, IHttpHandler
    {
        [Inject]
        public IVerticalizzazioniFactory _verticalizzazioniFactory { get; set; }

        protected override void DoProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            try
            {
                var software = HttpContext.Current.Request.QueryString["Software"];
                var stato = HttpContext.Current.Request.QueryString["Stato"];
                var result = String.Empty;

                DataSet ds = new DataSet();

                var datiVerticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneIAttivita>(this.AuthenticationInfo.Alias, software);

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

                    sql = String.Format(sql, this.Database.Specifics.QueryParameterName("idcomune"),
                                         this.Database.Specifics.QueryParameterName("software"),
                                         this.Database.Specifics.QueryParameterName("attiva"));

                    using (IDbCommand cmd = this.Database.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(this.Database.CreateParameter("idcomune", this.IdComune));
                        cmd.Parameters.Add(this.Database.CreateParameter("software", software));
                        cmd.Parameters.Add(this.Database.CreateParameter("attiva", stato == "attive" ? 1 : 0));

                        IDataAdapter da = this.Database.CreateDataAdapter(cmd);
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

    }
}
