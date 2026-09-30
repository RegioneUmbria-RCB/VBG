using System;
using System.Data;
using System.Web;
using System.Collections;
using System.Web.Services;
using System.Web.Services.Protocols;
using Init.SIGePro.Verticalizzazioni;
using System.Web.Script.Serialization;

namespace Sigepro.net.Istanze.SIT.Handlers
{
    /// <summary>
    /// Summary description for $codebehindclassname$
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    public class GeoInGestVert : BaseHandler, IHttpHandler
    {

        public void ProcessRequest(HttpContext context)
        {
            context.Response.ContentType = "text/plain";

            try
            {
                string result = string.Empty;
                string software = HttpContext.Current.Request.QueryString["Software"];
                switch (software)
                {
                    case "CART":
						result = "PANEL_KEY=background,LAYER_KEY=,RENDERER_KEY_ATTIVE=,RENDERER_KEY_CESSATE=,URL_PROXY=";
						//result = "PANEL_KEY=cartografia,LAYER_KEY=,RENDERER_KEY_ATTIVE=,RENDERER_KEY_CESSATE=,URL_PROXY=";
                        break;
                    case "TOPO":
                        result = "PANEL_KEY=topo,LAYER_KEY=,RENDERER_KEY_ATTIVE=,RENDERER_KEY_CESSATE=,URL_PROXY=";
                        break;
                    default:
                        VerticalizzazioneSitQuaestioflorenzia vert = new VerticalizzazioneSitQuaestioflorenzia(IdComuneAlias, software);

                        if (vert.Attiva)
                        {
                            result = "PANEL_KEY=" + vert.PanelKey + ",LAYER_KEY=" + vert.LayerKey + ",RENDERER_KEY_ATTIVE=" + vert.RendererKeyAttivo + ",RENDERER_KEY_CESSATE=" + vert.RendererKeyCessato + ",URL_PROXY=" + vert.UrlProxy;
                        }
                        else
                            throw new Exception("La verticalizzazione QuaestioFlorentia non è attiva");
                        break;
                }

				//JavaScriptSerializer serializer = new JavaScriptSerializer();
				//context.Response.Write(serializer.Serialize(loc));
				//context.Response.ContentType = "application/json";

                context.Response.Write(result);
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
