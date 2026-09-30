using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.VerificaFirmaRest;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale.IOC
{
    public static class VerificaFirmaDigitaleNinjectModule
    {
        public static IDIProvider ConfiguraVerificaFirmaDigitale(this IDIProvider k)
        {
            // firma digitale
            k.AddScoped<IFirmaDigitaleMetadataService, VerificaFirmaDigitaleRestService>();
            k.AddScoped<IVerificaFirmaDigitaleService, VerificaFirmaDigitaleRestService>();
            k.AddScoped<VerificaFirmaDigitaleRestClient>();


            return k;
        }
    }
}
