
using GeneratoreRiepiloghiHtml.AppLogic;
using GeneratoreRiepiloghiHtml.AppLogic.Authorization;
using Microsoft.AspNetCore.Components.Web;
using Microsoft.AspNetCore.Mvc;
using Scalar.AspNetCore;
using Serilog;
using VBG.BlazorComponentsLibrary.Extensions;
using VBG.SecurityLibrary;

var builder = WebApplication.CreateBuilder(args);

builder.Host.UseSerilog((context, configuration) =>
    configuration.ReadFrom.Configuration(context.Configuration)
                 .Enrich.FromLogContext()
    );

builder.Services.ConfiguraAppLogic(builder);

// Add services to the container.
await builder.Services.AddSecurityLibraryAsync(builder.Configuration, AmbienteTypeEnum.AREA_PERSONALE);

builder.Services.AddScoped<HtmlRenderer>();

// -------- Fine configurazione Custom -----------
builder.Services.AddHealthChecks();

builder.Services.AddVbgBlazorComponents();



builder.Services.AddHttpContextAccessor();
builder.Services.AddControllers(x =>
{
    x.Filters.Add<TokenAuthorizationFilter>();
    x.Filters.Add(new ProducesAttribute("application/json"));
});

builder.Services.AddAuthentication().AddJwtBearer();
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi(opt => opt.AddDocumentTransformer<BearerSecuritySchemeTransformer>());



var app = builder.Build();

// Configure the HTTP request pipeline.

app.MapOpenApi();
app.MapScalarApiReference(options =>
{
    options.AddPreferredSecuritySchemes("bearer")
            .AddImplicitFlow("bearer", bearer =>
            {
                bearer.Token = "xxx";
            });
});

app.UseAuthorization();
app.MapHealthChecks("/health");
app.MapControllers();

app.Run();
