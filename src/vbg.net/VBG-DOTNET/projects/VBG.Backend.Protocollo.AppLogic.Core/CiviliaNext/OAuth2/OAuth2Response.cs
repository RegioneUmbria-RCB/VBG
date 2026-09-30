using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.OAuth2
{
    public class OAuth2Response
    {
        [JsonProperty(PropertyName = "token_type")]
        public string TokenType { get; set; }

        [JsonProperty(PropertyName = "expires_in")]
        public int ExpiresIn { get; set; }
        
        [JsonProperty(PropertyName = "ext_expires_in")]
        public int ExtExpiresIn { get; set; }
        
        [JsonProperty(PropertyName = "expires_on")]
        public int ExpiresOn { get; set; }
        
        [JsonProperty(PropertyName = "not_before")]
        public int NotBefore { get; set; }
        
        [JsonProperty(PropertyName = "resource")]
        public string Resource { get; set; }
        
        [JsonProperty(PropertyName = "access_token")]
        public string AccessToken { get; set; }


    }
}
