using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.OAuth2
{

    public class OAuth2Request
    {
        [JsonProperty(PropertyName = "client_id")]
        public string ClientID { get; set; }

        [JsonProperty(PropertyName = "client_secret")]
        public string ClientSercret { get; set; }

        [JsonProperty(PropertyName = "grant_type")]
        public string GrantType { get; set; }

        [JsonProperty(PropertyName = "resource")]
        public string Resource { get; set; }

        public OAuth2Request()
        {

        }
    }
}
