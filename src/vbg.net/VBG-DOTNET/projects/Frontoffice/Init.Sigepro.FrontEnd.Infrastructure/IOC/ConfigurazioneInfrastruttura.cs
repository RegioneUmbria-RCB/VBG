using Init.Sigepro.FrontEnd.Infrastructure.Server;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using VBG.Shared.Infrastructure;
using VBG.Shared.Infrastructure.DependencyInjection;

#if NET48
using Init.Sigepro.FrontEnd.Infrastructure.Server.Framework;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Framework;
#endif

#if NET9_0_OR_GREATER
using Init.Sigepro.FrontEnd.Infrastructure.Server.Standard;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths.Standard;
#endif


namespace Init.Sigepro.FrontEnd.Infrastructure.IoC
{
    public static class ConfigurazioneDIInfrastruttura
    {
        public static IDIProvider ConfiguraInfrastruttura(this IDIProvider services)
        {
#if NET48
            services.AddScoped<IPathMapper, PathMapper>();
            services.AddScoped<IResolveUrl, HttpContextResolveUrl>();
#endif

#if NET9_0_OR_GREATER
            services.AddScoped<IPathMapper, PathMapper>();
            services.AddScoped<IResolveUrl, StandardResolveUrl>();
#endif
            services.ConfiguraSharedInfrastructure();

            return services;
        }
    }
}
