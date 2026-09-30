using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneStatoCompilazioneStep
{

    internal static class GestioneStatoCompilazioneStepModule
    {
        public static IDIProvider ConfiguraGestioneStatoCompilazioneStep(this IDIProvider k)
        {
            k.AddScoped<IStatoCompilazioneStepService, StatoCompilazioneStepService>();

            return k;
        }
    }

}
