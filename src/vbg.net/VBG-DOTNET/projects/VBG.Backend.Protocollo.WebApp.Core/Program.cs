using CoreWCF;
using CoreWCF.Configuration;
using CoreWCF.Description;
using VBG.Shared.Infrastructure.DependencyInjection;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Core;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using log4net;
using log4net.Repository.Hierarchy;
using Microsoft.Extensions.Options;
using SIGePro.Manager.VerticalizzazioniBase;
using Vbg.EventBus.IOC;
using VBG.Backend.Protocollo.AppLogic.Core;
using VBG.Backend.Protocollo.AppLogic.Core.PathMapper;
using VBG.Backend.Protocollo.AppLogic.Shared.PerEvitareLaDependencyInjection;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.WebApp.Core;
using VBG.Backend.Protocollo.WebApp.Core.Interfaces;
using VBG.Shared.Infrastructure;

var builder = WebApplication.CreateBuilder(args);
builder.Configuration.AddEnvironmentVariables();
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
builder.Services.AddScoped<IProtocollazioneService, ProtocollazioneService>();
builder.Services.AddScoped<ProtocollazioneService>();
builder.Services.AddTransient<IVerticalizzazioniFactory, VerticalizzazioniFactory>();
builder.Services.AddProtocolloCore(builder.Configuration);
builder.Services.AddCorePathMapper(builder.Configuration);
builder.Services.ToDIProvider()
    .RegistraSigeproManager()
    .RegistraEventBusModule();

var app = builder.Build();

// Schifezza, serve per istanziare lo StaticKernelContainer
var _kernel = app.Services.GetRequiredService<IKernelContainer>();

// imposto la cultura per formato data etc..
var supportedCultures = new[] { "it-IT", "en-US" }; var localizationOptions = new RequestLocalizationOptions()
    .SetDefaultCulture(supportedCultures[0])
    .AddSupportedCultures(supportedCultures)
    .AddSupportedUICultures(supportedCultures);

app.UseRequestLocalization(localizationOptions);

// modifica il livello di log da variabile environment specificata nel file yaml
var logLevel = Environment.GetEnvironmentVariable("LOG_LEVEL") ?? "DEBUG";
var repository = (Hierarchy)LogManager.GetRepository();
var level = repository.LevelMap[logLevel] ?? repository.LevelMap["DEBUG"];

repository.Root.Level = repository.LevelMap[logLevel];

var protocolloLogger = repository.GetCurrentLoggers()
    .FirstOrDefault(x => x.Name == "VBG.Backend.Protocollo.WebApp.Core.ProtocollazioneService");

if (protocolloLogger is log4net.Repository.Hierarchy.Logger logger)
{
    logger.Level = level;
}

repository.RaiseConfigurationChanged(EventArgs.Empty);



// lo inizializzo passando una classe qualunque del namespace VBG.Backend.Protocollo.AppLogic.Core
TipiProtocolloRegistry.Initialize(typeof(PROTOCOLLO_AIDA).Assembly);

var options = app.Services.GetRequiredService<IOptions<ConfigurazioneSigeproSecurityOptions>>();
var config = app.Services.GetRequiredService<IConfiguration>();
ParametriSecurityStorage.RegistraParametriSecurity(new NetCoreParametriSecurity(options.Value.LoginServiceUrl, options.Value.Username, options.Value.Password));

var protocolloFactory = app.Services.GetRequiredService<IProtocolloFactory>();
ProtocolloFactoryProvider.Factory = protocolloFactory;
FileBasedConfiguration.RegisterProvider(new NetCoreConfigurationProvider(config));


app.UseServiceModel(serviceBuilder =>
{
    serviceBuilder.AddService<ProtocollazioneService>(serviceOptions =>
    {
        // Abilita i dettagli delle eccezioni nei fault SOAP
        serviceOptions.DebugBehavior.IncludeExceptionDetailInFaults = true;
        // serviceOptions.BaseAddresses.Add(new Uri("http://localhost:8080/"));
    });

    var binding = new BasicHttpBinding
    {
        MessageEncoding = WSMessageEncoding.Mtom,
        MaxReceivedMessageSize = 100 * 1024 * 1024 // 100 MB
    };


    serviceBuilder.AddServiceEndpoint<ProtocollazioneService, IProtocollazioneService>(
        binding,
        "/ProtocollazioneService.svc");

    var smb = app.Services.GetRequiredService<ServiceMetadataBehavior>();
    smb.HttpGetEnabled = true;
});


app.MapHealthChecks("/health");

app.Run();
