using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace OAuth2Service.Models
{
    [DataContract]
    public class OAuth2Response
    {
        [DataMember]
        public string Token { get; set; }
    }
}