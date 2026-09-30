using AreaRiservataCore.wcf.istanze.visura;
using AreaRiservataCore.wcf.nla;
using CoreWCF;
using CoreWCF.Configuration;
using CoreWCF.Description;
using Init.Sigepro.FrontEnd.WebServices.Nla;

namespace AreaRiservataCore.wcf
{
    public static class CoreWcfExtensions
    {
        public static WebApplicationBuilder? AddArCoreWcfServices(this WebApplicationBuilder? builder)
        {
            if (builder == null) return null;

            builder.Services.AddTransient<SsuNlaService>();
            builder.Services.AddTransient<NlaService>();
            builder.Services.AddTransient<RiepilogoPraticaService>();

            builder.Services.AddServiceModelServices();
            builder.Services.AddServiceModelMetadata();
            builder.Services.AddSingleton<IServiceBehavior, UseRequestHeadersForMetadataAddressBehavior>();

            return builder;
        }

        public static WebApplication? ConfigureArCoreWcfServices(this WebApplication? app)
        {
            if (app == null) return null;

            app.UseServiceModel(serviceBuilder =>
            {
                serviceBuilder.AddService<NlaService>((serviceOptions) =>
                {
                    serviceOptions.DebugBehavior.IncludeExceptionDetailInFaults = true;
                });
                serviceBuilder.AddService<RiepilogoPraticaService>((serviceOptions) =>
                {
                    serviceOptions.DebugBehavior.IncludeExceptionDetailInFaults = true;
                });

                var applicationPathBase = app.Configuration.GetValue<string>("Settings:applicationPathBase") ?? "";

                if (!String.IsNullOrEmpty(applicationPathBase))
                {
                    applicationPathBase += "/";
                }



                var binding = new BasicHttpBinding();
                binding.MessageEncoding = WSMessageEncoding.Mtom;
                serviceBuilder.AddServiceEndpoint<NlaService, Nla>(binding, applicationPathBase + "webservices/nla/nlaservice.svc");

                // 
                serviceBuilder.AddServiceEndpoint<RiepilogoPraticaService, IRiepilogoPraticaService>(new BasicHttpBinding(), applicationPathBase + "webservices/istanze/visura/riepilogo-pratica.asmx");

                var serviceMetadataBehavior = app.Services.GetRequiredService<ServiceMetadataBehavior>();
                serviceMetadataBehavior.HttpGetEnabled = true;
                serviceMetadataBehavior.HttpsGetEnabled = true;

                //serviceMetadataBehavior.HttpGetUrl = new Uri("http://localhost/");
                //serviceMetadataBehavior.HttpsGetUrl = new Uri("https://localhost/");
            });

            return app;
        }
    }
}
