using System.Web.Http;

namespace Sigepro.net.Api
{
    public class ApiRegister
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "web-api/{controller}",
                defaults: new
                {
                    //idComuneAlias = RouteParameter.Optional,
                    //software = RouteParameter.Optional
                }
            );
        }
    }
}