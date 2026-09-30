[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(Init.Sigepro.FrontEnd.App_Start.AuthenticationHttpModule), "PreApplicationStart")]

namespace Init.Sigepro.FrontEnd.App_Start
{
    using Init.Sigepro.FrontEnd.HttpModules;
    using Microsoft.Web.Infrastructure.DynamicModuleHelper;

    public class AuthenticationHttpModule
    {
        public static void PreApplicationStart()
        {
            DynamicModuleUtility.RegisterModule(typeof(HttpForwardedHeadersModule));
            DynamicModuleUtility.RegisterModule(typeof(UrlSecurityModule));
            DynamicModuleUtility.RegisterModule(typeof(UrlParametersValidationModule));
            DynamicModuleUtility.RegisterModule(typeof(AuthenticationModule));
            DynamicModuleUtility.RegisterModule(typeof(ScrivaniaEntiTerziModule));
        }
    }
}