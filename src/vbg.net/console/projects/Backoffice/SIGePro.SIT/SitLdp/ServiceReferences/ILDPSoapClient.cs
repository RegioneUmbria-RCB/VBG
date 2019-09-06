using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Sit.SitLdp.ServiceReferences
{
    public interface ILDPSoapClient
    {
        IClientChannel InnerChannel { get; }
        void Abort();
    }
}
