using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiIstanze;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghiSchede.FileConverter;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;

namespace GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi
{
    public static class GenerazioneRiepiloghiConfigurationExtensions
    {
        public static IServiceCollection ConfiguraGenerazioneRiepiloghi(this IServiceCollection services)
        {
            services.AddTransient<SegnapostoDatoDinamico>();
            services.AddTransient<SegnapostoSchedaDinamica>();
            services.AddTransient<SegnapostoSchedeDinamiche>();
            services.AddTransient<SegnapostoSchedeDinamicheV2>();
            services.AddTransient<SegnapostoSchedeIntervento>();
            services.AddTransient<SegnapostoSchedeEndo>();
            services.AddTransient<SegnapostoNoteCampi>();

            services.AddTransient<List<ISegnapostoRiepilogo>>((ctxt) =>
            {
                return new List<ISegnapostoRiepilogo> {

                    ctxt.GetRequiredService<SegnapostoDatoDinamico>(),
                    ctxt.GetRequiredService<SegnapostoSchedaDinamica>(),
                    ctxt.GetRequiredService<SegnapostoSchedeDinamiche>(),
                    ctxt.GetRequiredService<SegnapostoSchedeDinamicheV2>(),
                    ctxt.GetRequiredService<SegnapostoSchedeIntervento>(),
                    ctxt.GetRequiredService<SegnapostoSchedeEndo>()
                };
            });

            services.AddTransient<GeneratoreRiepilogoSingolaSchedaService>();
            services.AddTransient<SostituzioneSegnapostoRiepilogoService>();
            services.AddTransient<GeneratoreRiepilogoDomanda>();

            services.AddScoped<FileConverterServiceCreator>();
            services.AddScoped<IHtmlToPdfFileConverter, GotenbergFileConverter>();

            return services;
        }
    }
}
