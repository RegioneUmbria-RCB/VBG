using VBG.Backend.Protocollo.AppLogic.Core.Urbi.Parametri;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Fascicolazione.Ricerca.Parametri
{
    internal class EnableCDATA : IParametro
    {
        public NameValueCollection Parametro { get; } = new NameValueCollection();

        public EnableCDATA(string enableCDATA)
        {
            this.Parametro.Add("EnableCDATA", enableCDATA);
        }

    }
}
