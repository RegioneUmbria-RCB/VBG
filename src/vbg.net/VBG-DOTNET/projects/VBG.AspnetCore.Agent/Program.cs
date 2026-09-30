// See https://aka.ms/new-console-template for more information
using Microsoft.AspNetCore.Builder;
using Serilog;
using VBG.AspnetCore.Agent;
using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza;
using VBG.AspnetCore.Agent.AppLogic.HealthChecks;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq;
using VBG.SecurityLibrary;


var builder = WebApplication.CreateBuilder(args);

builder.Configuration.AddJsonFile("appsettings.json", optional: true, reloadOnChange: true)
#if DEBUG
            .AddJsonFile("appsettings.Development.json", optional: true, reloadOnChange: true)
#endif
            .AddJsonFile("appsettings.prod.json", optional: true, reloadOnChange: true)
            .AddJsonFile("appsettings.Prod.json", optional: true, reloadOnChange: true)
            .AddEnvironmentVariables();

builder.Host.UseSerilog((hostingContext, services, loggerConfiguration) =>
{
    loggerConfiguration
            .ReadFrom.Configuration(hostingContext.Configuration)
            .Enrich.FromLogContext();
});

builder.Services.AddHealthChecks()
                .AddCheck<ListenerStatusHealthCheck>("ListenerStatus");


await builder.Services.AddSecurityLibraryAsync(builder.Configuration, AmbienteTypeEnum.DOTNET);

builder.Services.AddMemoryCache();

builder.Services.AddScoped<IDomandeInBozzaService, DomandeInBozzaService>();

builder.Services.AddRabbitMq("aspnet-backend-agent");
//workers
builder.Services.AddHostedService<ListenerWorker>();


var app = builder.Build();

app.UseHealthChecks("/health");

var log = app.Services.GetRequiredService<ILogger<Program>>();

log?.LogInformation("Agent rabbit area personale avviato");

// Ottenere IHostApplicationLifetime dal contenitore dei servizi
var lifetime = app.Services.GetRequiredService<IHostApplicationLifetime>();

// Registrare callback per quando l'applicazione si sta arrestando
lifetime.ApplicationStarted.Register(() => log?.LogInformation("Agent rabbit => application started"));
lifetime.ApplicationStopping.Register(() => log?.LogInformation("Agent rabbit => application stopping"));
lifetime.ApplicationStopped.Register(() => log?.LogInformation("Agent rabbit => application stopped"));

await app.RunAsync();



log?.LogInformation("Agent rabbit area personale arrestato");