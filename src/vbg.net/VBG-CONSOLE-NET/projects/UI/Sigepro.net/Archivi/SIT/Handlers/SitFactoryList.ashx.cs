using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.Collections.Generic;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Sit.Data;

namespace Sigepro.net.Archivi.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    public class SitFactoryList : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";

            try
            {
                string field = HttpContext.Current.Request.QueryString["Field"];
                WsSit SitWS = new WsSit();
                ListSit listSit = SitWS.GetListField(Token, field, SitAdapter.RequestToDataSit(), HttpContext.Current.Request.QueryString["Software"]);

                Dictionary<string, object> dic = SitAdapter.CreateDictionary(field,listSit);

                context.Response.Write(CreaResponse(dic));
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
