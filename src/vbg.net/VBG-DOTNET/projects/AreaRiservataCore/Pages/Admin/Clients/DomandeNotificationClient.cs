using Microsoft.AspNetCore.SignalR.Client;
using Microsoft.Extensions.Options;
using VBG.AppLogic.SSU.Configurazione;
using VBG.AppLogic.SSU.DataAccess.Dto;
using VBG.AppLogic.SSU.HubInterfaces;

namespace AreaRiservataCore.Pages.Admin.Clients
{
    public class DomandeNotificationClient : IAsyncDisposable
    {
        private HubConnection? _hubConnection;
        private readonly IOptions<SsuOptions> _generatoreSsuOptions;

        public event Func<string, int, bool, Task>? OnDomandaElaborabile;
        public event Func<bool, Task>? OnGeneratoreStatusChanged;
        public event Func<int, Task>? OnTotDomandeDaElaborareChanged;
        public event Func<string, int, Task>? OnNewErroreDomanda;
        public event Func<string, int, int, int, Task>? OnNuovoStatoDomanda;

        private int? _lastTotDomande;
        private bool? _lastGeneratoreStatus;


        public DomandeNotificationClient(IOptions<SsuOptions> generatoreSsuOptions)
        {
            this._generatoreSsuOptions = generatoreSsuOptions;
        }

        public async Task<bool> StartAsync()
        {
            var hubUrl = _generatoreSsuOptions.Value.SignalRHubUrl;

            _hubConnection = new HubConnectionBuilder()
                .WithUrl(hubUrl)
                .WithAutomaticReconnect()
                .Build();

            #region Registrazione degli eventi ricevuti dal server

            _hubConnection.On<string, int, bool>(
                nameof(IDomandeNotificationClient.SendDomandaElaborabileAsync),
                async (idComune, idDomanda, isElaborabile) =>
                {
                    if (OnDomandaElaborabile != null)
                        await OnDomandaElaborabile.Invoke(idComune, idDomanda, isElaborabile);
                }
            );

            _hubConnection.On<bool>(
                nameof(IDomandeNotificationClient.SendGeneratoreStatusAsync),
                async (isRunning) =>
                {
                    if (_lastGeneratoreStatus == isRunning)
                        return;

                    _lastGeneratoreStatus = isRunning;

                    if (OnGeneratoreStatusChanged != null)
                        await OnGeneratoreStatusChanged.Invoke(isRunning);
                }
            );

            _hubConnection.On<int>(
                nameof(IDomandeNotificationClient.SendNumeroDomandeDaElaborareAsync),
                async (n) =>
                {
                    if (_lastTotDomande == n)
                        return;

                    _lastTotDomande = n;

                    if (OnTotDomandeDaElaborareChanged != null)
                        await OnTotDomandeDaElaborareChanged.Invoke(n);
                }
            );

            _hubConnection.On<string, int>(
                nameof(IDomandeNotificationClient.SendNuovoErroreDomandaAsync),
                async (idComune, idDomanda) =>
                {
                    if (OnNewErroreDomanda != null)
                        await OnNewErroreDomanda.Invoke(idComune, idDomanda);
                }
            );

            _hubConnection.On<string, int, int, int>(
                nameof(IDomandeNotificationClient.SendNuovoStatoDomandaAsync),
                async (idComune, idDomanda, vecchioStato, nuovoStato) =>
                {
                    if (OnNuovoStatoDomanda != null)
                        await OnNuovoStatoDomanda.Invoke(idComune, idDomanda, vecchioStato, nuovoStato);
                }
            );

            #endregion

            _hubConnection.Closed += this.HubConnection_ClosedAsync;

            try
            {
                await _hubConnection.StartAsync();
                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine($"SignalR non raggiungibile: {ex.Message}");
                return false;
            }
        }

        private Task HubConnection_ClosedAsync(Exception? arg)
        {
            Console.WriteLine("Errore SignalR: " + arg?.Message);
            return Task.CompletedTask;
        }

        public async ValueTask DisposeAsync()
        {
            if (_hubConnection != null)
                await _hubConnection.DisposeAsync();
        }
    }
}
