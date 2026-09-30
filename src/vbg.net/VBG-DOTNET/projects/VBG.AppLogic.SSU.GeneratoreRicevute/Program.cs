using Gotenberg.Sharp.API.Client;
using Gotenberg.Sharp.API.Client.Domain.Settings;
using Gotenberg.Sharp.API.Client.Extensions;
using VBG.Shared.Infrastructure.DependencyInjection;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Init.Sigepro.FrontEnd.CoreServices.Configurazione;
using Init.Sigepro.FrontEnd.CoreServices.IoC;
using Init.Sigepro.FrontEnd.CoreServices.Shared;
using Microsoft.AspNetCore.Components.Web;
using Serilog;
using VBG.AppLogic.SSU;
using VBG.AppLogic.SSU.GeneratoreRicevute;
using VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneCodeMessaggi.DomandeInBozza;
using VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneGenerazioneRicevuta;
using VBG.AppLogic.SSU.GeneratoreRicevute.AppLogic.GestioneVisuraIstanza;
using VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione;
using VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess;
using VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess.Factory;
using VBG.AppLogic.SSU.GeneratoreRicevute.GenerazioneRicevuta;
using VBG.AppLogic.SSU.GeneratoreRicevute.Hubs;
using VBG.AppLogic.SSU.GeneratoreRicevute.InvioMail;
using VBG.AppLogic.SSU.GeneratoreRicevute.Scheduler;
using VBG.BlazorComponentsLibrary.Extensions;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Agid.DependencyInjection;

var builder = WebApplication.CreateBuilder(args);

// Serilog
builder.Host.UseSerilog((context, configuration) =>
    configuration.ReadFrom.Configuration(context.Configuration)
                 .Enrich.FromLogContext());

builder.Configuration.AddJsonFile("bindings.json", optional: false, reloadOnChange: true)
                     .AddEnvironmentVariables();

// Aggiunta dei servizi di base di ASP.NET Core
builder.Services.AddRazorPages();
builder.Services.AddServerSideBlazor();
builder.Services.AddHealthChecks();
builder.Services.AddScoped<HtmlRenderer>();
builder.Services.AddHttpContextAccessor();
builder.Services.AddMemoryCache();

// Cofigurazione delle librerie Vbg
builder.Services.ConfiguraDatiDinamiciCore();
builder.Services.AddVbgBlazorComponents(options =>
{
    options.AddFileServices<SharedComponentsFileService>();
    options.AddRisorseServices<RisorseTestualiService>();
});

// Gotenberg
builder.Services.AddOptions<GotenbergSharpClientOptions>()
                .Bind(builder.Configuration.GetSection(nameof(GotenbergSharpClient)));
builder.Services.AddGotenbergSharpClient();

// Configurazione dei servizi dell'area riservata
builder.Services
    .ToDIProvider()
    .ConfiguraTokenApplicazione()
    .ConfiguraAliasSoftwareCore()
    .ConfiguraTokenCore()
    .ConfiguraAreaRiservata()
    .ConfiguraVisura()
    .ConfiguraGenerazioneDocumenti()
    .ConfiguraCoreServices()
    .ConfiguraIntegrazioneSsu();

builder.Services.AddScoped<AuthenticationDataResolver>();
builder.Services.AddScoped<IAuthenticationDataResolver>(x => x.GetService<AuthenticationDataResolver>()!);
builder.Services.AddScoped<IAuthenticationDataStore>(x => x.GetService<AuthenticationDataResolver>()!);
builder.Services.AddScoped<IAppConfigurationReader, OptionsReader>();

// Servizi fake per far funzionare il serviceprovider
builder.Services.AddScoped<IProvenienzaDomandaInBozzaService, ProvenienzaDomandaInBozzaService>();
builder.Services.AddScoped<IGeneratoreHtmlSchedeDinamiche, FakeGeneratoreHtmlSchedeDinamiche>();
builder.Services.AddScoped<IModelloDinamicoHtmlRenderer, FakeModelloDinamicoHtmlRenderer>();

// Servizi specifici dell'applicazione
builder.Services.AddScoped<GeneratoreRicevutaScoped>();
builder.Services.AddScoped<GenerazioneRicevutaService>();
builder.Services.AddScoped<GeneratoreRicevuteTask>();
builder.Services.AddScoped<MailServiceServiceCreator>();
builder.Services.AddScoped<MailServiceClient>();
builder.Services.AddScoped<MailServiceServiceCreator>();
builder.Services.AddScoped<InvioEmailService>();
builder.Services.AddScoped<GeneraRicevutaService>();
builder.Services.AddScoped<NotificaStcService>();

// Servizio di scheduling custom
builder.Services.AddSingleton<SchedulerFactory>();

builder.Services.AddScoped<IDbConnectionFactoryProvider, DbConnectionFactoryProvider>();
builder.Services.AddScoped<IDomandeSsuDefaultAliasRepository, DomandeSsuDefaultAliasRepository>();

// Aggiunta di SignalR per la comunicazione in tempo reale con la dashboard di admin
builder.Services.AddSignalR();

// Sezioni di configurazione dell'applicazione
builder.Services.Configure<GeneratoreRicevuteOptions>(builder.Configuration.GetSection(GeneratoreRicevuteOptions.SectionName));

var app = builder.Build();

app.UseHealthChecks("/health");

app.MapGet("/", () => "Hello World!");


var sf = app.Services.GetRequiredService<SchedulerFactory>();
sf.Create<GeneratoreRicevuteTask>(async (t) =>
    await t.RunAsync()
).Start();

// Middleware per la gestione degli errori
app.Use(async (context, next) =>
{
    try
    {
        await next();
    }
    catch (UnauthorizedAccessException ex)
    {
        context.Response.StatusCode = StatusCodes.Status401Unauthorized;
        await context.Response.WriteAsJsonAsync(new { error = ex.Message });
    }
    catch (Exception ex)
    {
        context.Response.StatusCode = StatusCodes.Status500InternalServerError;
        await context.Response.WriteAsJsonAsync(new { error = "Internal Server Error", detail = ex.Message });
    }
});


// Ricevi l'elenco di tutte le domande ssu bloccate
app.MapGet("/domandessu/bloccate/{token}", (string token, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var domande = repository.GetNonElaborabili(applicationToken);
    return Results.Ok(domande);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Ricevi l'elenco delle domande ssu bloccate di un comune
app.MapGet("/domandessu/bloccate/{idComune}/{token}", (string token, string idComune, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var domande = repository.GetNonElaborabili(applicationToken, idComune);
    return Results.Ok(domande);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Sblocca una domanda ssu
app.MapPost("/domandessu/sblocca/{idComune}/{idDomanda}/{token}", async (string token, string idComune, int idDomanda, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    await repository.SetNonElaborabileAsync(applicationToken, idDomanda, true, idComune);
    return Results.Ok();
})
    .AddEndpointFilter<TokenValidationFilter>();

// Ricevi l'elenco di tutte le domande ssu divise per stato
app.MapPost("/domandessu/statiiniziali/{token}", (string token, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var domande = repository.GetStatiDomande(applicationToken);
    return Results.Ok(domande);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Ricevi il totale degli errori delle domande ssu
app.MapGet("/domandessu/errori/count/{token}", (string token, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var count = repository.GetTotErrori(applicationToken);
    return Results.Ok(count);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Ricevi l'elenco degli errori delle domande ssu di un comune
app.MapGet("/domandessu/errori/{idComune}/{token}", (string token, string idComune, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var errori = repository.GetErrori(applicationToken, idComune);
    return Results.Ok(errori);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Ricevi l'elenco degli errori della specifica domanda ssu
app.MapGet("/domandessu/errori/{idComune}/{idDomanda}/{token}", (string token, string idComune, int idDomanda, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    var errori = repository.GetErrori(applicationToken, idComune, idDomanda);
    return Results.Ok(errori);
})
    .AddEndpointFilter<TokenValidationFilter>();

// Elimina tutti gli errori di una specifica domanda
app.MapDelete("/domandessu/errori/{idComune}/{idDomanda}/{token}", (string token, string idComune, int idDomanda, IDomandeSsuDefaultAliasRepository repository, HttpContext ctx) =>
{
    var applicationToken = (string)ctx.Items["ApplicationToken"]!;
    repository.DeleteErrori(applicationToken, idComune, idDomanda);
    return Results.Ok();
})
    .AddEndpointFilter<TokenValidationFilter>();

// SignalR hub endpoint
app.MapHub<DomandeNotificationHub>("/hubs/notifiche-domandessu");

app.Run();
