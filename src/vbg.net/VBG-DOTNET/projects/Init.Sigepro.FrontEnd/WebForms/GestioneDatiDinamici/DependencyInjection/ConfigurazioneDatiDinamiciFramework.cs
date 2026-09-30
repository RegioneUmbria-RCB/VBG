using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.WebForms.GestioneDatiDinamici.NoteModello;
using Init.SIGePro.DatiDinamici.DependencyInjection;
using Init.SIGePro.DatiDinamici.Framework.ModelliFactory;
using VBG.DatiDinamici;
using VBG.DatiDinamici.DependencyInjection;
using VBG.DatiDinamici.Web;

namespace Init.Sigepro.FrontEnd.WebForms.GestioneDatiDinamici.DependencyInjection
{
    public static class ConfigurazioneDatiDinamiciFramework
    {
        public static IDIProvider ConfiguraDatiDinamiciFramework(this IDIProvider services)
        {
            services.AddScoped<IAccumulatoreNoteModelloService, AccumulatoreNoteModelloFramework>();
            services.AddTransient<IModelliDinamiciFactory, ModelliDinamiciFrameworkFactory>();
            services.AddTransient<IFrameworkDependencyResolver, FrameworkDependencyResolver>();
            services.AddTransient<IScriptDependencyInjectionService, FrameworkDependencyInjectionService>();

            return services;
        }
    }
}