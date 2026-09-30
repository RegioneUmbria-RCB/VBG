using log4net;
using Microsoft.Extensions.Logging;
using System;

namespace Init.Sigepro.FrontEnd.Log4Net
{
    public class Log4NetLogger : ILogger
    {
        private readonly ILog _log;

        public Log4NetLogger(string name)
        {
            this._log = LogManager.GetLogger(name);
        }

        public IDisposable BeginScope<TState>(TState state)
        {
            return null;
        }

        public bool IsEnabled(LogLevel logLevel)
        {
            switch (logLevel)
            {
                case LogLevel.Critical:
                    return this._log.IsFatalEnabled;
                case LogLevel.Debug:
                case LogLevel.Trace:
                    return this._log.IsDebugEnabled;
                case LogLevel.Error:
                    return this._log.IsErrorEnabled;
                case LogLevel.Information:
                    return this._log.IsInfoEnabled;
                case LogLevel.Warning:
                    return this._log.IsWarnEnabled;
                default:
                    throw new ArgumentOutOfRangeException(nameof(logLevel));
            }
        }

        public void Log<TState>(
            LogLevel logLevel,
            EventId eventId,
            TState state,
            Exception exception,
            Func<TState, Exception, string> formatter)
        {
            if (!this.IsEnabled(logLevel))
            {
                return;
            }

            if (formatter == null)
            {
                throw new ArgumentNullException(nameof(formatter));
            }

            string message = $"{formatter(state, exception)} {exception}";

            if (!string.IsNullOrEmpty(message) || exception != null)
            {
                switch (logLevel)
                {
                    case LogLevel.Critical:
                        this._log.Fatal(message);
                        break;
                    case LogLevel.Debug:
                    case LogLevel.Trace:
                        this._log.Debug(message);
                        break;
                    case LogLevel.Error:
                        this._log.Error(message);
                        break;
                    case LogLevel.Information:
                        this._log.Info(message);
                        break;
                    case LogLevel.Warning:
                        this._log.Warn(message);
                        break;
                    default:
                        this._log.Warn($"Encountered unknown log level {logLevel}, writing out as Info.");
                        this._log.Info(message, exception);
                        break;
                }
            }
        }

    }
}