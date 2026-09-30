using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Anagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.ConfigurazioneBackoffice;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly.DatiDinamici;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOnceOnly
{

    internal static class OnceOnlyModule
    {
        public static IDIProvider ConfiguraOnceOnly(this IDIProvider k)
        {
            k.AddScoped<IOnceOnlyDatiDinamiciService, OnceOnlyDatiDinamiciService>();
            k.AddScoped<IOnceOnlyAnagraficheService, OnceOnlyAnagraficheService>();
            k.AddScoped<DatiDinamiciOnceOnlyClient>();
            k.AddScoped<AnagraficheOnceOnlyClient>();
            k.AddScoped<IDatiDinamiciOnceOnlyRepository, DatiDinamiciOnceOnlyRepository>();

            k.AddScoped<IConfigurazione<ParametriOnceOnly>, ConfigurazioneImpl<ParametriOnceOnly>>();
            k.AddScoped<IConfigurazioneBuilder<ParametriOnceOnly>, ParametriOnceOnlyBuilder>();

            return k;
        }
    }

}
