using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;

namespace Init.SIGePro.Protocollo.ApSystems
{
    public class BaseProtocolloApSystems
    {
        protected ServiceProtocolloSoapClient _ws;
        protected AuthenticationDetails _authWs;
    }
}
