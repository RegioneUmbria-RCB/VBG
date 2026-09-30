using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Services.Description;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.PathMapper
{
    public static class FrameworkPathMapperExtensions
    {
        public static IServiceCollection AddFrameworkPathMapper(this IServiceCollection services, IConfiguration config)
        {
            services.AddSingleton<IPathMapper, FrameworkPathMapper>();


            return services;
        }
    }
}