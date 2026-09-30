using AspnetCoreServices;
using AspnetCoreServices.AppLogic;
using VBG.Shared.Infrastructure.Caching;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Authentication.Core;
using Init.SIGePro.Manager.Authentication.SoftwareAttivi;
using PersonalLib2.Data.Metadata;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AccessoAtti;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AllegatiDomanda;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Anagrafiche;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Autorizzazioni;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Bookmarks;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Configurazione;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.ConfigurazioneContenuti;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Console;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Conti;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDinamici;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDomanda;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EndoFrontoffice;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Endoprocedimenti;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EntiTerzi;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.IntegrazioneLDP;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Istanze;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Mappature;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Oneri;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.pagamenti;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.PagamentiESED;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.QuestionarioFo;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.RisorseTestuali;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Scadenzario;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.SoggettiFirmatari;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Stradario;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TabelleDiBase;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TipiSoggetto;
using Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Visura;

var builder = WebApplication.CreateBuilder();

builder.Services.AddServiceDiscoveryCore();
builder.Services.AddDnsSrvServiceEndpointProvider();

builder.Services.AddMemoryCache();
builder.Services.AddHealthChecks();

builder.Services.AddServiceModelServices();
builder.Services.AddServiceModelMetadata();
builder.Services.AddSingleton<IServiceBehavior, UseRequestHeadersForMetadataAddressBehavior>();


builder.Services.Configure<ConfigurazioneSigeproSecurityOptions>(builder.Configuration.GetSection(ConfigurazioneSigeproSecurityOptions.SectionName));
// builder.Services.AddScoped<CoreAuthenticationManager>();

// Registrazione dei services. Vanno registrati due volte, devo capire meglio perché
builder.Services.AddScoped<ITokenValidatorService, WcfServiceBase>();
builder.Services.AddScoped<WsConfigurazioneAreaRiservata>();
builder.Services.AddScoped<WsAccessoAttiService>();
builder.Services.AddScoped<WsComuniService>();
builder.Services.AddScoped<WsStradarioService>();
builder.Services.AddScoped<WsIstanzeService>();
builder.Services.AddScoped<WsAutorizzazioniService>();
builder.Services.AddScoped<WsScadenzarioService>();
builder.Services.AddScoped<WsBookmarksService>();
builder.Services.AddScoped<WsEntiTerziService>();
builder.Services.AddScoped<WsNodoPagamentiService>();
builder.Services.AddScoped<WsUrlAccessoConsoleService>();
builder.Services.AddScoped<WsEndoFrontofficeService>();
builder.Services.AddScoped<WsEndoprocedimenti>();
builder.Services.AddScoped<WsInterventi>();
builder.Services.AddScoped<WsConfigurazioneAreaRiservata>();
builder.Services.AddScoped<WsCommissioni>();
builder.Services.AddScoped<WsVotazioniCommissione>();
builder.Services.AddScoped<WsCommissioniAccessoPIN>();
builder.Services.AddScoped<WsQuestionarioFoService>();
builder.Services.AddScoped<WsAllegatiDomanda>();
builder.Services.AddScoped<WsOneriService>();
builder.Services.AddScoped<WsContiService>();
builder.Services.AddScoped<WsTipiSoggettoService>();
builder.Services.AddScoped<WsDatiDomandaService>();
builder.Services.AddScoped<WsRicercaVisuraService>();
builder.Services.AddScoped<WsPagamentiESEDService>();
builder.Services.AddScoped<WsSoggettiFirmatariService>();
builder.Services.AddScoped<WsTabelleDiBaseService>();
builder.Services.AddScoped<WsMappatureService>();
builder.Services.AddScoped<WsRisorseTestualiService>();
builder.Services.AddScoped<WsIntegrazioneLDPService>();
builder.Services.AddScoped<WsConfigurazioneContenutiService>();
builder.Services.AddScoped<WsAnagraficheService>();
builder.Services.AddScoped<WsDatiDinamici>();
builder.Services.AddScoped<WsVisuraService>();

builder.Services.ConfiguraDependencyInjectionBackend();

// Configurazione log4net
builder.ConfiguraLog4Net();

var app = builder.Build();

app.UseServiceModel(serviceBuilder =>
{
    serviceBuilder.AddWcfEndpoint<WsAccessoAttiService, IWsAccessoAttiService>("/WebServices/WsAreaRiservata/WcfServices/AccessoAtti/WsAccessoAttiService.svc");
    serviceBuilder.AddWcfEndpoint<WsComuniService, IWsComuniService>("/WebServices/WsAreaRiservata/WcfServices/WsComuniService.svc");
    serviceBuilder.AddWcfEndpoint<WsStradarioService, IWsStradarioService>("/WebServices/WsAreaRiservata/WcfServices/Stradario/WsStradarioService.svc");
    serviceBuilder.AddWcfEndpoint<WsIstanzeService, IWsIstanzeService>("/WebServices/WsAreaRiservata/WcfServices/Istanze/WsIstanzeService.svc");
    serviceBuilder.AddWcfEndpoint<WsAutorizzazioniService, IWsAutorizzazioniService>("/WebServices/WsAreaRiservata/WcfServices/Autorizzazioni/WsAutorizzazioniService.svc");
    serviceBuilder.AddWcfEndpoint<WsScadenzarioService, IWsScadenzarioService>("/WebServices/WsAreaRiservata/WcfServices/Scadenzario/WsScadenzarioService.svc");
    serviceBuilder.AddWcfEndpoint<WsBookmarksService, IWsBookmarksService>("/WebServices/WsAreaRiservata/WcfServices/Bookmarks/WsBookmarksService.svc");
    serviceBuilder.AddWcfEndpoint<WsEntiTerziService, IWsEntiTerziService>("/WebServices/WsAreaRiservata/WcfServices/EntiTerzi/WsEntiTerziService.svc");
    serviceBuilder.AddWcfEndpoint<WsNodoPagamentiService, IWsNodoPagamentiService>("/WebServices/WsAreaRiservata/WcfServices/pagamenti/WsNodoPagamentiService.svc");
    serviceBuilder.AddWcfEndpoint<WsUrlAccessoConsoleService, IWsUrlAccessoConsoleService>("/WebServices/WsAreaRiservata/WcfServices/Console/WsUrlAccessoConsoleService.svc");
    serviceBuilder.AddWcfEndpoint<WsEndoFrontofficeService, IWsEndoFrontofficeService>("/WebServices/WsAreaRiservata/WcfServices/EndoFrontoffice/WsEndoFrontofficeService.svc");
    serviceBuilder.AddWcfEndpoint<WsEndoprocedimenti, IWsEndoprocedimenti>("/WebServices/WsAreaRiservata/WcfServices/Endoprocedimenti/WsEndoprocedimenti.svc");
    serviceBuilder.AddWcfEndpoint<WsInterventi, IWsInterventi>("/WebServices/WsAreaRiservata/WcfServices/Interventi/WsInterventi.svc");
    serviceBuilder.AddWcfEndpoint<WsConfigurazioneAreaRiservata, IWsConfigurazioneAreaRiservata>("/WebServices/WsAreaRiservata/WcfServices/Configurazione/WsConfigurazioneAreaRiservata.svc");
    serviceBuilder.AddWcfEndpoint<WsCommissioni, IWsCommissioni>("/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioni.svc");
    serviceBuilder.AddWcfEndpoint<WsVotazioniCommissione, IWsVotazioniCommissione>("/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsVotazioniCommissione.svc");
    serviceBuilder.AddWcfEndpoint<WsCommissioniAccessoPIN, IWsCommissioniAccessoPIN>("/WebServices/WsAreaRiservata/WcfServices/Commissioni/WsCommissioniAccessoPIN.svc");
    serviceBuilder.AddWcfEndpoint<WsQuestionarioFoService, IWsQuestionarioFoService>("/WebServices/WsAreaRiservata/WcfServices/QuestionarioFo/WsQuestionarioFoService.svc");
    serviceBuilder.AddWcfEndpoint<WsAllegatiDomanda, IWsAllegatiDomanda>("/WebServices/WsAreaRiservata/WcfServices/AllegatiDomanda/WsAllegatiDomanda.svc");
    serviceBuilder.AddWcfEndpoint<WsOneriService, IWsOneriService>("/WebServices/WsAreaRiservata/WcfServices/Oneri/WsOneriService.svc");
    serviceBuilder.AddWcfEndpoint<WsContiService, IWsContiService>("/WebServices/WsAreaRiservata/WcfServices/Conti/WsContiService.svc");
    serviceBuilder.AddWcfEndpoint<WsTipiSoggettoService, IWsTipiSoggettoService>("/WebServices/WsAreaRiservata/WcfServices/TipiSoggetto/WsTipiSoggettoService.svc");
    serviceBuilder.AddWcfEndpoint<WsDatiDomandaService, IWsDatiDomandaService>("/WebServices/WsAreaRiservata/WcfServices/DatiDomanda/WsDatiDomandaService.svc");
    serviceBuilder.AddWcfEndpoint<WsRicercaVisuraService, IWsRicercaVisuraService>("/WebServices/WsAreaRiservata/WcfServices/Visura/WsRicercaVisuraService.svc");
    serviceBuilder.AddWcfEndpoint<WsPagamentiESEDService, IWsPagamentiESEDService>("/WebServices/WsAreaRiservata/WcfServices/PagamentiESED/WsPagamentiESEDService.svc");
    serviceBuilder.AddWcfEndpoint<WsSoggettiFirmatariService, IWsSoggettiFirmatariService>("/WebServices/WsAreaRiservata/WcfServices/SoggettiFirmatari/WsSoggettiFirmatariService.svc");
    serviceBuilder.AddWcfEndpoint<WsTabelleDiBaseService, IWsTabelleDiBaseService>("/WebServices/WsAreaRiservata/WcfServices/TabelleDiBase/WsTabelleDiBaseService.svc");
    serviceBuilder.AddWcfEndpoint<WsMappatureService, IWsMappatureService>("/WebServices/WsAreaRiservata/WcfServices/Mappature/WsMappatureService.svc");
    serviceBuilder.AddWcfEndpoint<WsRisorseTestualiService, IWsRisorseTestualiService>("/WebServices/WsAreaRiservata/WcfServices/RisorseTestuali/WsRisorseTestualiService.svc");
    serviceBuilder.AddWcfEndpoint<WsIntegrazioneLDPService, IWsIntegrazioneLDPService>("/WebServices/WsAreaRiservata/WcfServices/IntegrazioneLDP/WsIntegrazioneLDPService.svc");
    serviceBuilder.AddWcfEndpoint<WsConfigurazioneContenutiService, IWsConfigurazioneContenutiService>("/WebServices/WsAreaRiservata/WcfServices/ConfigurazioneContenuti/WsConfigurazioneContenutiService.svc");
    serviceBuilder.AddWcfEndpoint<WsAnagraficheService, IWsAnagraficheService>("/WebServices/WsAreaRiservata/WcfServices/Anagrafiche/WsAnagraficheService.svc");
    serviceBuilder.AddWcfEndpoint<WsDatiDinamici, IWsDatiDinamici>("/WebServices/WsAreaRiservata/WcfServices/DatiDinamici/WsDatiDinamici.svc");
    serviceBuilder.AddWcfEndpoint<WsVisuraService, IWsVisuraService>("/WebServices/WsAreaRiservata/WcfServices/Visura/WsVisuraService.svc");


    var serviceMetadataBehavior = app.Services.GetRequiredService<ServiceMetadataBehavior>();
    serviceMetadataBehavior.HttpGetEnabled = true;
});

app.RegistraRatingMinimalApi();

app.RegistraConfigurazioneBackend();

app.MapHealthChecks("/health");

MetadataAnalyzer.Instance.EnsureAnalysisExistsFor(typeof(Init.SIGePro.Data.Istanze));

app.MapGet("/clearcache", (IContextCache contextCache, ITimedCache timedCache) =>
{
    SoftwareAttiviService.ClearCache();
    SigeproSecurityProxy.ClearCache();
    contextCache.Clear();
    timedCache.Clear();

    return Results.Ok("Cache cleared");
});

app.Run();
