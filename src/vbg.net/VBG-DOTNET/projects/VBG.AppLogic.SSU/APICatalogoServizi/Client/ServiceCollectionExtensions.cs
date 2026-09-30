using Microsoft.Extensions.DependencyInjection;

namespace VBG.AppLogic.SSU.APICatalogoServizi.Client
{
    // ==================== DEPENDENCY INJECTION ====================

    public static class ServiceCollectionExtensions
    {
        public static IServiceCollection AddAPICatalogoServiziClient(
            this IServiceCollection services,
            Action<HttpClient> configureClient)
        {
            services.AddHttpClient<SsuCatalogoServiziClient>(configureClient);
            return services;
        }

        public static IServiceCollection AddAPICatalogoServiziClient(
            this IServiceCollection services,
            string baseAddress,
            string? bearerToken = null)
        {
            services.AddHttpClient<SsuCatalogoServiziClient>(client =>
            {
                client.BaseAddress = new Uri(baseAddress);
                if (!string.IsNullOrEmpty(bearerToken))
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", bearerToken);
                }
            });
            return services;
        }
    }
}

/* ==================== ESEMPIO DI UTILIZZO ====================

// In Program.cs o Startup.cs
builder.Services.AddAPICatalogoClient(
    baseAddress: "http://localhost:5075/",
    bearerToken: "your-jwt-token-here"
);

// Oppure con configurazione personalizzata
builder.Services.AddAPICatalogoClient(client =>
{
    client.BaseAddress = new Uri("http://localhost:5075/");
    client.Timeout = TimeSpan.FromSeconds(30);
    client.DefaultRequestHeaders.Authorization = 
        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", "your-token");
});

// In un controller o service
public class MioServizio
{
    private readonly APICatalogoClient _client;

    public MioServizio(APICatalogoClient client)
    {
        _client = client;
    }

    public async Task EsempioUso()
    {
        // Ottenere eventi della vita
        var eventi = await _client.GetEventiDellaVitaAsync("CODICE_ENTE");

        // Ottenere procedimenti con filtri
        var procedimenti = await _client.GetProcedimentiAsync(
            codiceEnte: "CODICE_ENTE",
            evento: "evento-id",
            q: "ricerca",
            page: 1
        );

        // Ottenere allegati per procedimenti
        var allegati = await _client.GetAllegatiAsync(
            codiceEnte: "CODICE_ENTE",
            procedimenti: new[] { 1, 2, 3 }
        );
    }
}

*/

