using CoreWCF;
using CoreWCF.Configuration;
using CoreWCF.Description;
using VBG.Shared.Infrastructure.DependencyInjection;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Core;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.SIT;
using VBG.Backend.SIT.WebServices;
using VBG.Shared.Infrastructure;

var builder = WebApplication.CreateBuilder(args);

builder.Logging.AddLog4Net("log4net.config", true);

// Add services to the container.
builder.Services.AddMemoryCache();
builder.Services.AddHealthChecks();

builder.Services.AddServiceModelServices().AddServiceModelMetadata();
builder.Services.AddSingleton<IServiceBehavior, UseRequestHeadersForMetadataAddressBehavior>();

builder.Services.ToDIProvider().ConfiguraSharedInfrastructure();
builder.Services.AddScoped<SigeproSecurityProxy>();
builder.Services.AddScoped<IAuthenticationManager, AuthenticationManager>();
builder.Services.AddScoped<RegoleServiceClient>();
builder.Services.AddScoped<ITransientAuthenticationInfoResolver, HttpContextAuthenticationInfoResolver>();
builder.Services.AddScoped<IWsSit, WsSit>();
builder.Services.AddScoped<WsSit>();
builder.Services.AddTransient<IVerticalizzazioniFactory, VerticalizzazioniFactory>();

builder.Services.Configure<ConfigurazioneSigeproSecurityOptions>(builder.Configuration.GetSection(ConfigurazioneSigeproSecurityOptions.SectionName));
var app = builder.Build();

app.RegistraConfigurazioneBackend();


app.UseServiceModel(serviceBuilder =>
{
    serviceBuilder.AddService<WsSit>(serviceOptions =>
    {
        // Abilita i dettagli delle eccezioni nei fault SOAP
        serviceOptions.DebugBehavior.IncludeExceptionDetailInFaults = true;
        // serviceOptions.BaseAddresses.Add(new Uri("http://localhost:8080/"));
    });

    var applicationPathBase = app.Configuration.GetValue<string>("Settings:applicationPathBase") ?? "";

    if (!String.IsNullOrEmpty(applicationPathBase))
    {
        applicationPathBase += "/";
    }

    var servicePath = applicationPathBase + "WsSit.svc";
    app.Logger.LogInformation("Service path: {servicePath}", servicePath);

    serviceBuilder.AddServiceEndpoint<WsSit, IWsSit>(
        new BasicHttpBinding(),
        servicePath);

    var smb = app.Services.GetRequiredService<ServiceMetadataBehavior>();
    smb.HttpGetEnabled = true;
});


app.MapHealthChecks("/health");

await app.RunAsync();
