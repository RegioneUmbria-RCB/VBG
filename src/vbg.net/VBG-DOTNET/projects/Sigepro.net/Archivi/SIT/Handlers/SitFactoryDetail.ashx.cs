using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.Archivi.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    //public class SitFactoryDetail : BaseHandler, IHttpHandler
    //{

    //    protected override void DoProcessRequest(HttpContext context)
    //    {
    //        context.Response.ContentType = "text/plain";

    //        try
    //        {
    //            string field = HttpContext.Current.Request.QueryString["Field"];
    //            WsSit SitWS = new WsSit();
    //            var detailSit = SitWS.GetDetailField(this.Token, field, new SitAdapter(this._authenticationManager).RequestToDataSit(), HttpContext.Current.Request.QueryString["Software"]);

    //            Dictionary<string, object> dic = SitAdapter.CreateDictionary(detailSit);

    //            context.Response.Write(this.CreaResponse(dic));
    //        }
    //        catch (Exception ex)
    //        {
    //            context.Response.StatusCode = 500;
    //            context.Response.Write(ex.ToString());
    //        }
    //    }


    //}
}
