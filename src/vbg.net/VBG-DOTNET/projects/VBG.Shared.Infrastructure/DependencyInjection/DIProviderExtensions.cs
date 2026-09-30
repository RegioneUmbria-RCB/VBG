#if NET9_0_OR_GREATER
using VBG.Shared.Infrastructure.DependencyInjection.Standard;
using Microsoft.Extensions.DependencyInjection;
#endif

#if NET48
using VBG.Shared.Infrastructure.DependencyInjection.Framework;
using Ninject;
#endif

namespace VBG.Shared.Infrastructure.DependencyInjection
{
    public static class DIProviderExtensions
    {
#if NET9_0_OR_GREATER
        public static IDIProvider ToDIProvider(this IServiceCollection services)
        {
            return new CoreDIProvider(services);
        }
#endif

#if NET48
        public static IDIProvider ToDIProvider(this IKernel kernel)
        {
            return new NinjectDIProvider(kernel);
        }
#endif
    }
}
