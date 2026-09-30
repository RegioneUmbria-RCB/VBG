using Init.Sigepro.FrontEnd.AppLogic.Adapters.SigeproPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.Adapters.IOC
{
    public static class IstanzaSigeproAdapterNinjectModule
    {
        public static IDIProvider ConfiguraSigeproAdapters(this IDIProvider k)
        {
            k.AddScoped<AnagraficheSigeproAdapter>();
            k.AddScoped<DatiCatastaliSigeproAdapter>();
            k.AddScoped<DatiDinamiciSigeproAdapter>();
            k.AddScoped<DatiGeneraliSigeproAdapter>();
            k.AddScoped<DocumentiSigeproAdapter>();
            k.AddScoped<EventiSigeproAdapter>();
            k.AddScoped<OneriSigeproAdapter>();
            k.AddScoped<ProcedimentiSigeproAdapter>();
            k.AddScoped<StradarioSigeproAdapter>();
            k.AddScoped<IstanzaSigeproAdapterServiceFactory>();
            k.AddScoped<MetadatiIstanzaPartialAdapter>();

            k.AddScoped<IIstanzaSigeproAdapterService>(ctxt =>
            {
                var factory = ctxt.GetService<IstanzaSigeproAdapterServiceFactory>();

                return factory.Create();
            });

            // STC
            k.AddScoped<ICodiceAccreditamentoHelper, CodiceAccreditamentoHelper>();

            return k;
        }
    }
}
