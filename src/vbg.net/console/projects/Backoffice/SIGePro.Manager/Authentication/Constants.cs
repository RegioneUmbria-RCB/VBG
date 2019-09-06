using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Authentication
{
    internal class Constants
    {
        internal class Oasis
        {
            public const string WsseNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
            public const string WsseUtilityNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
            public const string WsseUserNameTokenNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
            public const string WsseNonceTypeNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";

            public const string WsseNamespacePrefix = "wsse";
            public const string WsseUtilityNamespacePrefix = "wsu";
        }

        internal class UserNameToken
        {
            public const string UserNameTokenElementName = "UsernameToken";
            public const string UserNameElementName = "Username";
            public const string PasswordElementName = "Password";
            public const string NonceElementName = "Nonce";
            public const string CreatedElementName = "Created";
            public const string IdAttributeName = "Id";
            public const string TypeAttributeName = "Type";
            public const string EncodingType = "EncodingType";
        }

        internal class Xml
        {
            public const string WsUtilityPrefix = "wsu";
            public const string WsUtilityNamespace = "http://schemas.xmlsoap.org/ws/2003/06/utility";
            public const string UserNameTokenType = "http://schemas.microsoft.com/ws/2006/05/identitymodel/tokens/UserName";

        }

        internal class Serialization
        {
            public const string TokenIdAttributeName = "Id";
        }
    }
}
