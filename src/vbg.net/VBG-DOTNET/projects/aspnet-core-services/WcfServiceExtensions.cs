namespace AspnetCoreServices
{
    public static class WcfServiceExtensions
    {


        public static IServiceBuilder AddWcfEndpoint<TService, TContract>(this IServiceBuilder builder, string endpoint) where TService : class
        {

            builder.AddService<TService>((serviceOptions) =>
            {
                serviceOptions.DebugBehavior.IncludeExceptionDetailInFaults = true;
            });

            var binding = new BasicHttpBinding();
            binding.MaxReceivedMessageSize = long.MaxValue;

            builder.AddServiceEndpoint<TService, TContract>(binding, endpoint);

            return builder;
        }
    }
}
