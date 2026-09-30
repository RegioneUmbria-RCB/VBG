using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.EndoAcquisiti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Frontoffice;
using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.Incompatibilita;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti.IoC
{
    internal static class ConfigurazioneGestioneEndo
    {
        public static IDIProvider ConfiguraGestioneEndo(this IDIProvider k)
        {
            // Endo area riservata
            k.AddScoped<IEndoprocedimentiRepository, WsEndoprocedimentiRepository>();
            k.AddScoped<EndoprocedimentiService>();
            k.AddScoped<IEndoprocedimentiService, EndoprocedimentiService>();
            k.AddScoped<IEndoAcquisitiService, EndoprocedimentiService>();
            k.AddScoped<EndoprocedimentiServiceCreator>();

            // Endo frontoffice (servizi js)
            k.AddScoped<IEndoprocedimentiFrontofficeRepository, WsEndoprocedimentiFrontofficeRepository>();
            k.AddScoped<EndoprocedimentiFrontofficeService>();
            k.AddScoped<EndoFrontofficeServiceCreator>();

            // Compatibilità procedimenti
            k.AddScoped<IEndoprocedimentiIncompatibiliRepository, WsEndoprocedimentiRepository>();
            k.AddScoped<IEndoprocedimentiIncompatibiliService, EndoprocedimentiIncompatibiliService>();



            return k;
        }
    }
}
