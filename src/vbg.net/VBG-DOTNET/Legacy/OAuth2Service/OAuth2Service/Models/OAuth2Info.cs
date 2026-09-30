using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace OAuth2Service.Models
{
    [DataContract]
    public class OAuth2Info
    {
        [DataMember(Name = "ClientID")]
        public string ClientID { get; set; }

        [DataMember(Name = "Secret")]
        public string Secret { get; set; }

        [DataMember(Name = "AuthContextURL")]
        public string AuthContextURL { get; set; }

        [DataMember(Name = "ResourceUrlWs")]
        public string ResourceUrlWs { get; set; }
    }
}