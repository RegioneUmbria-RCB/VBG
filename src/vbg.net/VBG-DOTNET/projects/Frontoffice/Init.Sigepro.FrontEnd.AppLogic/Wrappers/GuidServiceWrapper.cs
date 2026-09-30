using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Wrappers
{
    public interface IGuidWrapperService
    {
        Guid NewGuid();
    }
    public class GuidServiceWrapper : IGuidWrapperService
    {
        public GuidServiceWrapper()
        {

        }

        public Guid NewGuid()
        {
            return Guid.NewGuid();
        }
    }
}
