using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Formule;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.GenerazionePdfModelli;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Ricerche;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.SchedeCollegate;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.IoC
{
    public static class DatiDinamiciNinjectModule
    {
        public static IDIProvider ConfiguraDatiDinamici(this IDIProvider k)
        {
            // k.AddScoped<IDatiDinamiciRepository, WsDatiDinamiciRepository>();
            k.AddScoped<IDatiDinamiciService, DatiDinamiciService>();
            k.AddScoped<IModelliDinamiciService, ModelliDinamiciService>();
            k.AddScoped<WsDatiDinamiciServiceCreator>();
            k.AddScoped<IRicercheDatiDinamiciService, RicercheDatiDinamiciService>();
            k.AddScoped<IVisuraDatiDinamiciService, VisuraDatiDinamiciService>();
            k.AddScoped<IRiepilogoModelloInHtmlFactory, RiepilogoModelloInHtmlFactory>();
            k.AddScoped<IRiepiloghiDatiDinamiciService, RiepiloghiDatiDinamicService>();
            k.AddScoped<IRiepiloghiDatiDinamiciAsyncService, RiepiloghiDatiDinamicService>();
            k.AddScoped<FormuleDatiDinamiciService>();

            k.AddScoped<WsDatiDinamiciRepository>();
            k.AddScoped<IDatiDinamiciRepository>(ctxt => ctxt.GetService<WsDatiDinamiciRepository>());
            k.AddScoped<IStrutturaModelloDinamicoRepository>(ctxt => ctxt.GetService<WsDatiDinamiciRepository>());

            // Schede collegate
            k.AddScoped<ISchedeCollegateService, SchedeCollegateService>();

            return k;
        }

    }
}
