using VBG.Shared.Infrastructure.DependencyInjection;
namespace Init.Sigepro.FrontEnd.AppLogic.PrecompilazionePDF.DI
{

    internal static class PrecompilazionePDFModule
    {
        public static IDIProvider ConfiguraPrecompilazionePDF(this IDIProvider k)
        {
            k.AddScoped<IPdfUtilsService, PdfUtilsService>();
            k.AddScoped<IPdfUtilsWsWrapper, PdfUtilsWsWrapper>();

            return k;
        }
    }

}
