using AreaRiservataCore.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using AreaRiservataCore.AppLogic.GestioneMovimenti;
using AreaRiservataCore.Auth;
using AreaRiservataCore.Pages.Admin.Clients;
using AreaRiservataCore.Pages.EntiTerzi;
using AreaRiservataCore.Pages.MiePratiche;
using AreaRiservataCore.Pages.Movimenti;
using AreaRiservataCore.Utils;
using AreaRiservataCore.wcf;
using Gotenberg.Sharp.API.Client;
using Gotenberg.Sharp.API.Client.Domain.Settings;
using Gotenberg.Sharp.API.Client.Extensions;
using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti;
using Init.Sigepro.FrontEnd.CoreServices.DatiDinamici.Ricerche;
using Init.Sigepro.FrontEnd.CoreServices.IoC;
using Init.Sigepro.FrontEnd.CoreServices.Shared;
using Init.Sigepro.FrontEnd.GestioneMovimenti.NinjectModule;
using Init.Sigepro.FrontEnd.GestioneMovimenti.Persistence;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.HttpOverrides;
using System.Runtime.InteropServices;
using VBG.AppLogic.SSU;
using VBG.AppLogic.SSU.Configurazione;
using VBG.BlazorComponentsLibrary.Extensions;
using VBG.DatiDinamici.Agid.DependencyInjection;
using VBG.DatiDinamici.Agid.HtmlRendering;


if (RuntimeInformation.IsOSPlatform(OSPlatform.Windows) && !Environment.Is64BitProcess)
{
    throw new Exception("Questa applicazione può essere eseguita solo in un ambiente windows a 64 bit. Se si sta utilizzando IIS verificare che il pool di connessioni su" +
        " cui è esguita questa applicazione sia impostato per girare a 64 bit");
}

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddServiceDiscoveryCore();
builder.Services.AddDnsSrvServiceEndpointProvider();

builder.Configuration
           .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
#if DEBUG
          .AddJsonFile("appsettings.dev.json", optional: true, reloadOnChange: true)
#endif
          .AddJsonFile("appsettings.prod.json", optional: true, reloadOnChange: true)

          .AddJsonFile("bindings.json", optional: false, reloadOnChange: true)
#if DEBUG
          .AddJsonFile("bindings.dev.json", optional: true, reloadOnChange: true)
#endif
          .AddJsonFile("bindings.prod.json", optional: true, reloadOnChange: true)
          .AddEnvironmentVariables();

if (args != null)
{
    builder.Configuration.AddCommandLine(args);
}

builder.Services.Configure<ForwardedHeadersOptions>(options =>
{
    options.ForwardedHeaders =
        ForwardedHeaders.XForwardedFor | ForwardedHeaders.XForwardedProto;
    options.ForwardedProtoHeaderName = "X-Forwarded-Proto";
    options.ForwardedForHeaderName = "X-Forwarded-For";
    options.KnownNetworks.Clear();
    options.KnownProxies.Clear();
});

builder.Logging.AddLog4Net("log4net.config", true);
builder.Services.AddHttpContextAccessor();

builder.Services.AddOptions<GotenbergSharpClientOptions>()
        .Bind(builder.Configuration.GetSection(nameof(GotenbergSharpClient)));
builder.Services.AddGotenbergSharpClient();


// Leggi la configurazione per invocare gli endpoint del Generatore SSU e registrare il client SignalR
builder.Services.Configure<SsuOptions>(builder.Configuration.GetSection(SsuOptions.SectionName));
builder.Services.AddScoped<DomandeNotificationClient>();

// Add services to the container.
builder.Services.AddRazorPages();
builder.Services.AddServerSideBlazor();
builder.Services.AddHealthChecks();
builder.Services.AddScoped<FileDownloadHelper>();
builder.Services.AddScoped<CustomAuthStateProvider>();
builder.Services.AddScoped<AuthenticationStateProvider>(x => x.GetService<CustomAuthStateProvider>());

builder.Services
    .ToDIProvider()
    .ConfiguraAliasSoftwareCore()
    .ConfiguraTokenCore()
    .ConfiguraAreaRiservata()
    .ConfiguraMovimenti()
    .ConfiguraIntegrazioneLDP()
    .ConfiguraVisura()
    .ConfiguraGenerazioneDocumenti()
    .ConfiguraCoreServices()
    //.ConfiguraAgidPageLayout()
    .ConfiguraIntegrazionePagamentiNodoPagamenti()
    .ConfiguraIntegrazioneSsu();

// Servizi per la gestione dello stato della pagina
builder.Services.AddScoped<ETListaPraticheService>();
builder.Services.AddScoped<RichiestaListaPraticheService>();
builder.Services.AddScoped<LastStepService>();


builder.Services.AddScoped<IProvenienzaDomandaInBozzaService, ProvenienzaDomandaInBozzaService>();
builder.Services.AddScoped<IIdMovimentoResolver, IdMovimentoResolverService>();

builder.Services.RegistraRenderingModelliHTML();

builder.Services.AddVbgBlazorComponents(options =>
{
    options.AddFileServices<SharedComponentsFileService>();
    options.AddRisorseServices<RisorseTestualiService>();
});

builder.Services.ConfiguraDatiDinamiciCore(options =>
{
    options.SetMarkdownConverter<MarkdownConverter>();
    //options.SetDatiDinamiciSearchService<DOLDatiDinamiciSearchService>();
    options.SetDatiDinamiciSearchService<CoreDatiDinamiciSearchService>();
});

builder.Services.AddOptions();
builder.Services.AddAuthorizationCore();

builder.Services.AddSession(options =>
{
    options.IdleTimeout = TimeSpan.FromMinutes(30);//We set Time here 
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
});

builder.Services.AddScoped<HtmlRenderer>();

// Servizi wcf dell'area riservata (es.nodo nla)
builder.AddArCoreWcfServices();

var app = builder.Build();

app.UseForwardedHeaders();

var supportedCultures = new[] { "it-IT", "en-US" };
var localizationOptions = new RequestLocalizationOptions()
    .SetDefaultCulture(supportedCultures[0])
    .AddSupportedCultures(supportedCultures)
    .AddSupportedUICultures(supportedCultures);

app.UseRequestLocalization(localizationOptions);

var applicationPathBase = app.Configuration.GetValue<string>("Settings:applicationPathBase");

if (!String.IsNullOrEmpty(applicationPathBase))
{
    var logger = app.Services.GetService<ILogger<Program>>();

    logger?.LogInformation("PathBase letto dalla configurazione: {0}", applicationPathBase);

    app.Use(async (context, next) =>
    {
        context.Request.PathBase = applicationPathBase;
        await next.Invoke();
    });
}


// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}

// Servizi wcf dell'area riservata (es.nodo nla)
app.ConfigureArCoreWcfServices();

app.UseSession();
app.UseStaticFiles();
app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();


app.MapBlazorHub();
app.MapHealthChecks("/health");
app.MapFallbackToAreaPage("~/modulifvg/{*clientroutes:nonfile}", "/ModuliFvg/_Host", "modulifvg").Add(b => ((RouteEndpointBuilder)b).Order = int.MaxValue - 1);
app.MapFallbackToPage("/_Host");



app.Run();

