using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Siena;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Trieste;
using Ninject;

namespace Init.Sigepro.FrontEnd.AppLogic.IoC
{
    internal static class ConfigurazioneAccessoAtti
    {
        public static IKernel RegistraParametriAccessoAtti(this IKernel kernel)
        {
            kernel.Bind<TriesteAccessoAttiService>().ToSelf().InTransientScope();
            kernel.Bind<ISienaAccessoAttiProxy>().To<SienaAccessoAttiProxy>().InTransientScope();

            return kernel;
        }
    }
}
