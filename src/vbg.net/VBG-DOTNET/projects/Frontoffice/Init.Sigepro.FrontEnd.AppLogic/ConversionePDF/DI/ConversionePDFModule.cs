using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.Ghostscript;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.ConversionePDF.DI
{
    public static class ConversionePDFModule
    {
        public static IDIProvider ConfiguraConversionePDF(this IDIProvider k)
        {
            k.AddScoped<PhantomjsRenderer>();
            k.AddScoped<PdfToPdfAConverter>();

#if NET48
            k.AddScoped<IHtmlToPdfFileConverter, PhantomjsFileConverter>();
            k.AddScoped<IHtmlToPdfAsyncFileConverter, PhantomjsFileConverter>();

#else
            k.AddScoped<IHtmlToPdfFileConverter, FileConverter2Converter>();
            k.AddScoped<IHtmlToPdfAsyncFileConverter, GotenbergFileConverter>();
#endif

            return k;
        }
    }
}
