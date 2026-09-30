using Init.Sigepro.Frontend.Infrastructure.DependencyInjection;
using Vbg.CoreControls.JavascriptInterop;

namespace Vbg.CoreControls.DependencyInjection
{
    public static class ConfigurazioneControlliCore
    {
        public static IDIProvider ConfiguraControlliCore(this IDIProvider services)
        {
            services.AddScoped<RisorseTestualiClientService>();

            return services;
        }
    }
}
