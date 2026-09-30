using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent
{
    public class ListenerWorker : BackgroundService
    {
        private readonly ILogger<ListenerWorker> _logger;
        private readonly IListenerStatusRegistry _statusRegistry;
        private readonly IConnectionInstance _connectionInstance;

        private readonly DomandeInBozzaListener _domandeInBozzaListener;

        public ListenerWorker(
            ILogger<ListenerWorker> logger,
            IListenerStatusRegistry statusRegistry,
            IConnectionInstance connectionInstance,
            DomandeInBozzaListener domandeInBozzaListener)
        {
            this._logger = logger;
            this._statusRegistry = statusRegistry;
            this._connectionInstance = connectionInstance;
            this._domandeInBozzaListener = domandeInBozzaListener;
        }

        public override async Task StopAsync(CancellationToken cancellationToken)
        {
            this._logger.LogInformation("Pulizia delle connessioni a causa dello spegnimento");

            await this.ShutdownAsync();

            await base.StopAsync(cancellationToken);
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            stoppingToken.ThrowIfCancellationRequested();

            this._logger.LogInformation("Listener Worker running at: {time}", DateTimeOffset.Now);

            try
            {
                await this._domandeInBozzaListener.StartAsync();

                await Task.Delay(10, stoppingToken);
            }
            catch (TaskCanceledException)
            {
                await this.ShutdownAsync();

                this._logger.LogInformation("Spegnimento a causa di una TaskCanceledException");
                // When the stopping token is canceled, for example, a call made from services.msc,
                // we shouldn't exit with a non-zero exit code. In other words, this is expected...
            }
            catch (Exception ex)
            {
                this._logger.LogError(ex, "Spegnimento a causa di un'eccezione non gestita: {Message}", ex.Message);

                await this.ShutdownAsync();

                // Terminates this process and returns an exit code to the operating system.
                // This is required to avoid the 'BackgroundServiceExceptionBehavior', which
                // performs one of two scenarios:
                // 1. When set to "Ignore": will do nothing at all, errors cause zombie services.
                // 2. When set to "StopHost": will cleanly stop the host, and log errors.
                //
                // In order for the Windows Service Management system to leverage configured
                // recovery options, we need to terminate the process with a non-zero exit code.
                Environment.Exit(1);
            }
            finally
            {
                this._statusRegistry.DumpStatus();
            }


        }

        private async Task ShutdownAsync()
        {
            this._logger.LogInformation("Chiusura delle connessioni a rabbit");

            await this._connectionInstance.ShutdownAsync();

            this._statusRegistry.DumpStatus();
        }
    }
}
