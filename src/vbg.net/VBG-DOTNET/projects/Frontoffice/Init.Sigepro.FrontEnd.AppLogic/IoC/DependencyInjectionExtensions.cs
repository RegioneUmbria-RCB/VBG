using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    public static class DependencyInjectionExtensions
    {
        public static IDIProvider AddConfigurationParameter<TParameter, TBuilder>(this IDIProvider provider)
            where TParameter : class, IParametriConfigurazione
            where TBuilder : class, IConfigurazioneBuilder<TParameter>
        {
            provider.AddScoped<IConfigurazione<TParameter>, ConfigurazioneImpl<TParameter>>();
            provider.AddScoped<IConfigurazioneBuilder<TParameter>, TBuilder>();
            return provider;
        }
    }
}
