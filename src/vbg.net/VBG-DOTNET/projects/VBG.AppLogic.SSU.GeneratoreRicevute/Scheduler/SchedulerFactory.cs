namespace VBG.AppLogic.SSU.GeneratoreRicevute.Scheduler
{
    public class SchedulerFactory
    {
        private readonly IServiceProvider _provider;
        private readonly IConfiguration _configuration;
        private readonly ILoggerFactory _loggerFactory;
        private readonly ILogger<SchedulerFactory> _logger;

        public SchedulerFactory(IServiceProvider provider, IConfiguration configuration, ILoggerFactory loggerFactory)
        {
            this._provider = provider;
            this._configuration = configuration;
            this._loggerFactory = loggerFactory;
            this._logger = loggerFactory.CreateLogger<SchedulerFactory>();
        }

        public Scheduler<T> Create<T>(Func<T, Task> callback) where T : notnull
        {
            return new Scheduler<T>(callback, this._provider, this._configuration, this._loggerFactory.CreateLogger<Scheduler<T>>());
        }
    }

}
