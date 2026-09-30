using System;
using System.Security.Cryptography;
using System.ServiceModel.Channels;
using System.Text;
using System.Xml;

namespace Init.SIGePro.Manager.Authentication
{
    public class UserNameSecurityTokenHeader : MessageHeader
    {
        private static class Constants
        {
            public const string HeaderName = "Security";

            internal static class Oasis
            {
                public const string WsseNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
                public const string WsseUtilityNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
                public const string WsseUserNameTokenNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
                public const string WsseNonceTypeNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";

                public const string WsseNamespacePrefix = "wsse";
                public const string WsseUtilityNamespacePrefix = "wsu";
            }

            internal static class UserNameToken
            {
                public static string UserNameTokenElementName { get; } = "UsernameToken";
                public static string UserNameElementName { get; } = "Username";
                public static string PasswordElementName { get; } = "Password";
                public static string NonceElementName { get; } = "Nonce";
                public static string CreatedElementName { get; } = "Created";
                public static string IdAttributeName { get; } = "Id";
                public static string TypeAttributeName { get; } = "Type";
                public static string EncodingType { get; } = "EncodingType";
            }
        }


        private UserNameSecurityTokenHeader() { }

        private string UserName
        {
            get;
            set;
        }

        private string Password
        {
            get;
            set;
        }

        private DateTime Created
        {
            get;
            set;
        }
        private byte[] Nonce { get; set; } = new byte[16];

        private string GetPasswordDigestAsBase64()
        {
            // generate a cryptographically strong random value
            RandomNumberGenerator rndGenerator = new RNGCryptoServiceProvider();
            rndGenerator.GetBytes(this.Nonce);

            //Array.Clear(Nonce, 0, Nonce.Length);


            // get other operands to the right format
            byte[] time = Encoding.UTF8.GetBytes(this.GetCreatedAsString());
            byte[] pwd = Encoding.UTF8.GetBytes(this.Password);
            byte[] operand = new byte[this.Nonce.Length + time.Length + pwd.Length];
            Array.Copy(this.Nonce, operand, this.Nonce.Length);
            Array.Copy(time, 0, operand, this.Nonce.Length, time.Length);
            Array.Copy(pwd, 0, operand, this.Nonce.Length + time.Length, pwd.Length);

            // create the hash
            SHA1 sha1 = SHA1.Create();
            return Convert.ToBase64String(sha1.ComputeHash(operand));
        }

        private string GetCreatedAsString()
        {
            return XmlConvert.ToString(this.Created.ToUniversalTime(), "yyyy-MM-ddTHH:mm:ssZ");
        }

        protected override void OnWriteHeaderContents(System.Xml.XmlDictionaryWriter writer, MessageVersion messageVersion)
        {
            writer.WriteStartElement(Constants.Oasis.WsseNamespacePrefix,
                                    Constants.UserNameToken.UserNameTokenElementName,
                                    Constants.Oasis.WsseNamespace);

            writer.WriteAttributeString(Constants.Oasis.WsseUtilityNamespacePrefix,
                                        Constants.UserNameToken.IdAttributeName,
                                        Constants.Oasis.WsseUtilityNamespace,
                                        Guid.NewGuid().ToString());

            writer.WriteElementString(Constants.Oasis.WsseNamespacePrefix,
                                        Constants.UserNameToken.UserNameElementName,
                                        Constants.Oasis.WsseNamespace,
                                        this.UserName);

            /***Write Password***/
            writer.WriteStartElement(Constants.Oasis.WsseNamespacePrefix,
                                        Constants.UserNameToken.PasswordElementName,
                                        Constants.Oasis.WsseNamespace);

            writer.WriteAttributeString(Constants.UserNameToken.TypeAttributeName,
                                        null,
                                        Constants.Oasis.WsseUserNameTokenNamespace);

            writer.WriteString(this.GetPasswordDigestAsBase64());
            writer.WriteEndElement();
            /***End-Write Password***/

            /* nonce */
            writer.WriteStartElement(Constants.Oasis.WsseNamespacePrefix,
                                        Constants.UserNameToken.NonceElementName,
                                        Constants.Oasis.WsseNamespace);

            writer.WriteAttributeString(Constants.UserNameToken.EncodingType,
                                        null,
                                        Constants.Oasis.WsseNonceTypeNamespace);

            writer.WriteString(Convert.ToBase64String(this.Nonce));
            writer.WriteEndElement();
            /* end nonce */

            /* Created */
            writer.WriteStartElement(Constants.Oasis.WsseUtilityNamespacePrefix,
                                    Constants.UserNameToken.CreatedElementName,
                                    Constants.Oasis.WsseUtilityNamespace);

            writer.WriteString(this.GetCreatedAsString());
            writer.WriteEndElement();
            /* end Created */

            writer.WriteEndElement();
        }

        public override string Name => Constants.HeaderName;
        public override string Namespace => Constants.Oasis.WsseNamespace;
        public override bool MustUnderstand => true;

        internal static UserNameSecurityTokenHeader FromUserNamePassword(string userName, string password)
        {
            UserNameSecurityTokenHeader header = new UserNameSecurityTokenHeader();
            header.UserName = userName;
            header.Password = password;
            header.Created = DateTime.Now;

            return header;
        }
    }
}
