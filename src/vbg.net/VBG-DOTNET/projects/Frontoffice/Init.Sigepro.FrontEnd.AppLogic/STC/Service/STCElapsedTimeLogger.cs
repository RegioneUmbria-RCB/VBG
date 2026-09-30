using Init.Sigepro.FrontEnd.AppLogic.Common;
using log4net;
using System;
using System.Diagnostics;

namespace Init.Sigepro.FrontEnd.AppLogic.STC.Service
{
    internal class STCElapsedTimeLogger : IDisposable
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(STCElapsedTimeLogger));
        private readonly string _operazione;
        private readonly IAliasResolver _aliasResolver;
        private readonly Stopwatch _stopwatch = new Stopwatch();
        private bool _stopped = false;

        public STCElapsedTimeLogger(string operazione, IAliasResolver aliasResolver)
        {
            this._stopwatch.Start();
            this._operazione = operazione;
            this._aliasResolver = aliasResolver;
        }

        public void Stop()
        {
            this._stopwatch.Stop();

            this._log.InfoFormat("{0}\t{1}\t{2}", this._operazione, this._aliasResolver.AliasComune, this._stopwatch.ElapsedMilliseconds);

            this._stopped = true;
        }

        public void Dispose()
        {
            if (!this._stopped)
            {
                this.Stop();
            }
        }
    }
}
