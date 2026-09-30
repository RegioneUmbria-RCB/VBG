using GeneratoreRiepiloghiHtml.AppLogic.Authentication;
using GeneratoreRiepiloghiHtml.AppLogic.Authorization;
using GeneratoreRiepiloghiHtml.AppLogic.Fakes;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneCertificatoInvio;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneInterventi;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.Configuration;
using GeneratoreRiepiloghiHtml.AppLogic.Infrastructure.ServiceCreators;
using GeneratoreRiepiloghiHtml.AppLogic.Markdown;
using GeneratoreRiepiloghiHtml.AppLogic.Visura;
using Gotenberg.Sharp.API.Client;
using Gotenberg.Sharp.API.Client.Domain.Settings;
using Gotenberg.Sharp.API.Client.Extensions;
using Microsoft.JSInterop;
using VBG.DatiDinamici.Agid.DependencyInjection;
using VBG.DatiDinamici.Agid.HtmlRendering;

namespace GeneratoreRiepiloghiHtml.AppLogic
{
    public static class AppLogicConfigurationExtensions
    {
        public static IServiceCollection ConfiguraAppLogic(this IServiceCollection services, IHostApplicationBuilder builder)
        {
            services.AddOptions<GotenbergSharpClientOptions>()
                .Bind(builder.Configuration.GetSection(nameof(GotenbergSharpClient)));
            services.AddGotenbergSharpClient();

            services.ConfiguraDatiDinamiciCore(options =>
            {
                options.SetMarkdownConverter<MarkdownConverter>();
            });
            services.RegistraRenderingModelliHTML();


            services.AddScoped<ClaimsBasedIdentityService>();
            services.AddScoped<IUserIdentityService>(s => s.GetService<ClaimsBasedIdentityService>()!);

            // Risoluzione alias e token
            services.AddScoped<IAliasResolver, HttpContextAliasResolver>();
            services.AddScoped<ITokenResolver, HttpContextTokenResolver>();

            // Visura
            services.AddScoped<VisuraServiceCreator>();
            services.AddScoped<IVisuraService, VisuraService>();

            // Allegati intervento
            services.AddScoped<InterventiServiceCreator>();
            services.AddScoped<IInterventiService, InterventiService>();


            // Parametri di configurazione
            services.AddScoped<ParametriSecurityService>();
            services.AddScoped<BindingFactory>();

            // Schede dinamiche
            services.AddScoped<IJSRuntime, FakeJsRuntime>();

            services.AddScoped<StrutturaSchedeDinamicheServiceCreator>();
            services.AddScoped<IStrutturaSchedeDinamicheService, StrutturaSchedeDinamicheService>();

            services.ConfiguraGestioneOggetti()
                    .ConfiguraGenerazioneRiepiloghi()
                    .ConfiguraGenerazioneCertificatoInvio();


            return services;
        }
    }
}
