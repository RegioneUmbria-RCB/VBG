using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public static class ListaSegnapostoRiepilogoDomandaExtensions
    {
        public static void ConfiguraSegnapostoRiepilogo(this IDIProvider services)
        {
            services.AddScoped<ISostituzioneSegnapostoRiepilogoService, SostituzioneSegnapostoRiepilogoService>();

            services.AddScoped<SegnapostoDatoDinamico>();
            services.AddScoped<SegnapostoSchedaDinamica>();
            services.AddScoped<SegnapostoSchedeDinamiche>();
            services.AddScoped<SegnapostoSchedeDinamicheV2>();
            services.AddScoped<SegnapostoSchedeIntervento>();
            services.AddScoped<SegnapostoSchedeEndo>();
            services.AddScoped<SegnapostoNoteCampi>();

            services.AddTransient<ListaSegnapostoRiepilogoDomanda>(ctxt =>
            {
                var segnaposto = ctxt.GetService<SegnapostoDatoDinamico>();
                var segnapostoScheda = ctxt.GetService<SegnapostoSchedaDinamica>();
                var segnapostoSchede = ctxt.GetService<SegnapostoSchedeDinamiche>();
                var segnapostoSchedeV2 = ctxt.GetService<SegnapostoSchedeDinamicheV2>();
                var segnapostoSchedeIntervento = ctxt.GetService<SegnapostoSchedeIntervento>();
                var segnapostoSchedeEndo = ctxt.GetService<SegnapostoSchedeEndo>();
                var segnapostoNote = ctxt.GetService<SegnapostoNoteCampi>();

                var listaSegnaposto = new ListaSegnapostoRiepilogoDomanda(segnapostoNote);
                listaSegnaposto.Add(segnaposto);
                listaSegnaposto.Add(segnapostoScheda);
                listaSegnaposto.Add(segnapostoSchede);
                listaSegnaposto.Add(segnapostoSchedeV2);
                listaSegnaposto.Add(segnapostoSchedeIntervento);
                listaSegnaposto.Add(segnapostoSchedeEndo);

                return listaSegnaposto;
            });
        }
    }
}
