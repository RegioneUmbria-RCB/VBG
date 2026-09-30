using Init.SIGePro.Manager;
using System;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.Archivi.Mercati.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    public class Mercati_Insert : BaseHandler, IHttpHandler
    {

        protected override void DoProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";
            try
            {

                Init.SIGePro.Data.Mercati m = new MercatiAdapter(this._authenticationManager).ForInsert();

                MercatiMgr mgr = new MercatiMgr(this.Database);
                if (!String.IsNullOrEmpty(context.Request.Form["num_posteggi"]))
                    mgr.PosteggiDaGenerare = Convert.ToInt32(context.Request.Form["num_posteggi"]);

                m = mgr.Insert(m);

                Dictionary<string, object> dic = new Dictionary<string, object>();
                dic["IDCOMUNE"] = m.IdComune;
                dic["CODICEMERCATO"] = m.CodiceMercato;

                context.Response.Write(this.CreaResponse(dic));
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
