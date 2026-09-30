using Init.Sigepro.FrontEnd.Infrastructure.DatesAndTimes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.GestioneOggetti.UrlDownloadOggetti
{
    public class StaticCurrentDateTimeProvider : ICurrentDateTimeProvider
    {
        private readonly DateTime _staticDateTime;

        public StaticCurrentDateTimeProvider(DateTime staticDateTime)
        {
            _staticDateTime = staticDateTime;
        }
        public DateTime DateTime => _staticDateTime;
    }
}
