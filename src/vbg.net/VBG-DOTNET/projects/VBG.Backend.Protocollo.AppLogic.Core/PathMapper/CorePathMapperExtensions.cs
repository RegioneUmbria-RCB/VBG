using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.PathMapper
{
    public static class CorePathMapperExtensions
    {
        public static IServiceCollection AddCorePathMapper(this IServiceCollection services, IConfiguration config)
        {
            services.AddSingleton<IPathMapper, CorePathMapper>();


            return services;
        }
    }
}
