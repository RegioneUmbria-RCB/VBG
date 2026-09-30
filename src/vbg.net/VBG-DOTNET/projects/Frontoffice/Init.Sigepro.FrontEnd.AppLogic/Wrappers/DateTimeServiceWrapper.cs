using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Wrappers
{
    public interface IDateTimeServiceWrapper
    {
        DateTime NewDate();
    }

    public class DateTimeServiceWrapper : IDateTimeServiceWrapper
    {
        public DateTimeServiceWrapper()
        {

        }

        public DateTime NewDate() => new DateTime();
    }
}
