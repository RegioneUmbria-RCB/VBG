#if NET9_0_OR_GREATER
using Microsoft.Extensions.DependencyInjection;
using VBG.Shared.Infrastructure.Caching;
using VBG.Shared.Infrastructure.Caching.Standard;
using VBG.Shared.Infrastructure.DependencyInjection;
using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel.Standard;
#endif

#if NET48
using VBG.Shared.Infrastructure.DependencyInjection;
using VBG.Shared.Infrastructure.Caching;
using VBG.Shared.Infrastructure.Caching.Framework;
using VBG.Shared.Infrastructure.ServiceModel;
using VBG.Shared.Infrastructure.ServiceModel.Framework;
using Microsoft.Extensions.DependencyInjection;
#endif


namespace VBG.Shared.Infrastructure
{
    public static class ConfigurazioneDIInfrastrutturaShared
    {
        //public static IServiceCollection ConfiguraSharedInfrastructureCore(this IServiceCollection services)
        //{
        //    services.AddTransient<IBindingFactory, BindingFactory>();
        //    services.AddSingleton<ITimedCache, TimedCache>();
        //    services.AddScoped<IContextCache, ContextCache>();
        //    services.AddScoped<ISessionCache, SessionCache>();

        //    services.AddSingleton<ApplicationCache>();
        //    services.AddSingleton<IApplicationCache>(ctxt => ctxt.GetService<ApplicationCache>());

        //    return services;
        //}

        public static IDIProvider ConfiguraSharedInfrastructure(this IDIProvider services)
        {
#if NET48
            services.AddScoped<IBindingFactory, BindingFactory>();
            services.AddScoped<IApplicationCache, ApplicationCache>();
            services.AddScoped<ISessionCache, SessionCache>();
            services.AddScoped<IContextCache, ContextCache>();
            services.AddScoped<ITimedCache, TimedCache>();
            services.AddSingleton<ApplicationCache>();
#endif


#if NET9_0_OR_GREATER
            services.AddTransient<IBindingFactory, BindingFactory>();
            services.AddSingleton<ITimedCache, TimedCache>();
            services.AddScoped<IContextCache, ContextCache>();
            services.AddScoped<ISessionCache, SessionCache>();

            services.AddSingleton<ApplicationCache>();
            services.AddSingleton<IApplicationCache>(ctxt => ctxt.GetService<ApplicationCache>());
#endif
            return services;
        }
    }
}
