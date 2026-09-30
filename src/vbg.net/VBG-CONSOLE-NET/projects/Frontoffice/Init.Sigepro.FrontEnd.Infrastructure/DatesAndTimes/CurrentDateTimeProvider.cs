using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.DatesAndTimes
{
    public class CurrentDateTimeProvider : ICurrentDateTimeProvider
    {
        public DateTime DateTime => DateTime.Now;
    }
}
