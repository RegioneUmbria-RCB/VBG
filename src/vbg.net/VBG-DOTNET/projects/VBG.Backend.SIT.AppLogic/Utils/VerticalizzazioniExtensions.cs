using Microsoft.Extensions.DependencyInjection;
using SIGePro.Manager.VerticalizzazioniBase;

namespace VBG.Backend.SIT.AppLogic.Utils
{
    public static class VerticalizzazioniExtensions
    {
        public static IServiceCollection RegistraVerticalizzazione<T>(this IServiceCollection svc) where T : Verticalizzazione, new()
        {
            svc.AddTransient<T>(context =>
            {
                var vertFactory = context.GetRequiredService<IVerticalizzazioniFactory>();
                //var authInfo = context.GetRequiredService<IAuthenticationInfoResolver>().Resolve();
                return vertFactory.Create<T>("");
            });

            return svc;
        }
    }
}
