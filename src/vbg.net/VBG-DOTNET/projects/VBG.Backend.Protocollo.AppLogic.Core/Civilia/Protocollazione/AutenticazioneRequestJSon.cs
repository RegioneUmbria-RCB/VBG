using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Civilia.Protocollazione
{

    public class AutenticazioneRequestJSon
    {
        public string ClientID { get; set; }
        public string Secret { get; set; }
        public string AuthContextURL { get; set; }
        public string ResourceUrlWs { get; set; }

        public AutenticazioneRequestJSon()
        {

        }
    }
}
