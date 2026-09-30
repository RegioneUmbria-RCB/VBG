using Sigepro.net.WebServices.WsSIGePro;
using System;
using System.Collections.Generic;
using System.Web;

namespace Sigepro.net.Archivi.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    //public class SitFactoryValidate : BaseHandler, IHttpHandler
    //{

    //    protected override void DoProcessRequest(HttpContext context)
    //    {
    //        context.Response.ContentType = "text/plain";

    //        try
    //        {
    //            var dataSit = new SitAdapter(this._authenticationManager).RequestToDataSit();
    //            string field = HttpContext.Current.Request.QueryString["Field"];
    //            WsSit SitWS = new WsSit();
    //            var validateSit = SitWS.ValidateField(this.Token, field, dataSit, HttpContext.Current.Request.QueryString["Software"]);

    //            Dictionary<string, object> dic = new SitAdapter(this._authenticationManager).CreateDictionary(validateSit);

    //            context.Response.Write(this.CreaResponse(dic));
    //        }
    //        catch (Exception ex)
    //        {
    //            context.Response.StatusCode = 500;
    //            context.Response.Write(ex.ToString());
    //        }
    //    }

    //    public bool IsReusable
    //    {
    //        get
    //        {
    //            return false;
    //        }
    //    }
    //}
}
