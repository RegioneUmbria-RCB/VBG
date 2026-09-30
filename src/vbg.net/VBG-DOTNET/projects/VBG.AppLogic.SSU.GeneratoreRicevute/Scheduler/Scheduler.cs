namespace VBG.AppLogic.SSU.GeneratoreRicevute.Scheduler
{
    public class Scheduler<T> where T : notnull
    {
        private readonly Func<T, Task> _callback;
        private readonly IServiceProvider _provider;
        private readonly IConfiguration _configuration;
        private readonly ILogger<Scheduler<T>> _logger;
        private Timer? _t;

        public Scheduler(Func<T, Task> callback, IServiceProvider provider, IConfiguration configuration, ILogger<Scheduler<T>> logger)
        {
            this._callback = callback;
            this._provider = provider;
            this._configuration = configuration;
            this._logger = logger;
        }

        private SchedulerConfig ReadConfiguration()
        {
            if (this._configuration == null)
                throw new InvalidOperationException("Configuration not initialized");

            var taskName = typeof(T).Name;
            var section = this._configuration.GetSection($"Scheduler:{taskName}");

            if (!section.Exists())
                throw new InvalidOperationException(
                    $"Missing configuration section Scheduler:{taskName}");

            var config = section.Get<SchedulerConfig>();

            if (config == null)
                throw new InvalidOperationException(
                    $"Invalid config for task {taskName}");

            return config;
        }


        public void Start()
        {
            var conf = this.ReadConfiguration();

            var interval = TimeSpan.FromMilliseconds(conf.IntervalInMilliseconds);
            var startDelay = TimeSpan.FromMilliseconds(conf.StartDelayInMilliseconds);

#pragma warning disable VSTHRD101 // Avoid unsupported async delegates
            this._t = new(async _ =>
                {
                    try
                    {
                        this._t!.Change(Timeout.Infinite, Timeout.Infinite);

                        using var scope = this._provider.CreateScope();
                        var instance = scope.ServiceProvider.GetRequiredService<T>();

                        await this._callback(instance);
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine($"{DateTime.Now:HH:mm:ss} - Exception: {ex.Message}");
                        this._logger.LogError(ex, "Errore durante l'esecuzione del task schedulato");
                    }
                    finally
                    {
                        this._t!.Change(conf.IntervalInMilliseconds, Timeout.Infinite);
                    }
                },
                null,
                startDelay,
                interval
            );
#pragma warning restore VSTHRD101 // Avoid unsupported async delegates
        }
    }
}
