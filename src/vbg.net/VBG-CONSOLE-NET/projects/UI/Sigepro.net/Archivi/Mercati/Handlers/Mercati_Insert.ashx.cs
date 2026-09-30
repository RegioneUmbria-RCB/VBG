using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Manager;
using System.Collections.Generic;

namespace Sigepro.net.Archivi.Mercati.Handlers 
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    public class Mercati_Insert : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            try
            {

                Init.SIGePro.Data.Mercati m = MercatiAdapter.ForInsert();

                MercatiMgr mgr = new MercatiMgr(Database);
                if (!String.IsNullOrEmpty(context.Request.Form["num_posteggi"]))
                    mgr.PosteggiDaGenerare = Convert.ToInt32(context.Request.Form["num_posteggi"]);

                m = mgr.Insert(m);

                Dictionary<string, object> dic = new Dictionary<string, object>();
                dic["IDCOMUNE"] = m.IdComune;
                dic["CODICEMERCATO"] = m.CodiceMercato;

                context.Response.Write( CreaResponse( dic ) );
            }
            catch (Exception ex)
            {
                context.Response.StatusCode = 500;
                context.Response.Write(ex.ToString());
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
