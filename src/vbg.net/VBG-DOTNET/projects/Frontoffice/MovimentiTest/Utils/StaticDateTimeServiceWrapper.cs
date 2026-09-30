using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Utils
{
    public class StaticDateTimeServiceWrapper : IDateTimeServiceWrapper
    {
        private readonly DateTime _dt;

        public StaticDateTimeServiceWrapper(DateTime dt)
        {
            _dt = dt;
        }

        public DateTime NewDate() => _dt;
        
    }
}
