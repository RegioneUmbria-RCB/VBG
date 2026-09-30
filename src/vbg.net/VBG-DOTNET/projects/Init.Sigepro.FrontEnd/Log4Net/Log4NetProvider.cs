using Microsoft.Extensions.Logging;
using System.Collections.Concurrent;

namespace Init.Sigepro.FrontEnd.Log4Net
{
    public class Log4NetProvider : ILoggerProvider
    {


        private readonly ConcurrentDictionary<string, ILogger> _loggers =
            new ConcurrentDictionary<string, ILogger>();

        public Log4NetProvider()
        {
        }

        public ILogger CreateLogger(string categoryName)
        {
            return this._loggers.GetOrAdd(categoryName, this.CreateLoggerImplementation);
        }

        public void Dispose()
        {
            this._loggers.Clear();
        }

        private ILogger CreateLoggerImplementation(string name)
        {
            return new Log4NetLogger(name);
        }
    }
}