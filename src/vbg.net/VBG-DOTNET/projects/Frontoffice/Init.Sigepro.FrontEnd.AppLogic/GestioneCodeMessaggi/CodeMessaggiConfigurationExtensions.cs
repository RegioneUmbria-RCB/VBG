using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;
using VBG.Shared.Infrastructure.DependencyInjection;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi
{

    internal static class GestioneCodeMessaggiModule
    {
        public static IDIProvider ConfiguraGestioneCodeMessaggi(this IDIProvider k)
        {
            k.AddTransient<IEventiDomandeInBozzaService, EventiDomandeInBozzaService>();
            k.AddTransient<IRabbitChannelFactory, RabbitChannelFactory>();

#if NET48
            k.AddTransient<IProvenienzaDomandaInBozzaService, ProvenienzaDomandaService>();
#endif
            return k;
        }
    }



}
