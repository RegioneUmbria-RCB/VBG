using System;
using System.Security.Cryptography;
using System.ServiceModel.Channels;
using System.Text;
using System.Xml;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
	public class UserNameSecurityTokenHeader : MessageHeader
	{
		private class Constants
		{
			public class Oasis
			{
				public const string WsseNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
				public const string WsseUtilityNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
				public const string WsseUserNameTokenNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
				public const string WsseNonceTypeNamespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";

				public const string WsseNamespacePrefix = "wsse";
				public const string WsseUtilityNamespacePrefix = "wsu";
			}

			public class UserNameToken
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

			public class Xml
			{
				public const string WsUtilityPrefix = "wsu";
				public const string WsUtilityNamespace = "http://schemas.xmlsoap.org/ws/2003/06/utility";
				public const string UserNameTokenType = "http://schemas.microsoft.com/ws/2006/05/identitymodel/tokens/UserName";

			}

			public class Serialization
			{
				public const string TokenIdAttributeName = "Id";
			}
		}


		private const string NameValue = "Security";
		private const string UserNameParameterName = "userName";
		private const string PasswordParameterName = "password";

		private UserNameSecurityTokenHeader() { }

		public string UserName
		{
			get;
			private set;
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
		byte[] Nonce { get; set; }

		public string GetPasswordDigestAsBase64()
		{
			// generate a cryptographically strong random value
			RandomNumberGenerator rndGenerator = new RNGCryptoServiceProvider();
			rndGenerator.GetBytes(Nonce);

			//Array.Clear(Nonce, 0, Nonce.Length);


			// get other operands to the right format
			byte[] time = Encoding.UTF8.GetBytes(GetCreatedAsString());
			byte[] pwd = Encoding.UTF8.GetBytes(Password);
			byte[] operand = new byte[Nonce.Length + time.Length + pwd.Length];
			Array.Copy(Nonce, operand, Nonce.Length);
			Array.Copy(time, 0, operand, Nonce.Length, time.Length);
			Array.Copy(pwd, 0, operand, Nonce.Length + time.Length, pwd.Length);

			// create the hash
			SHA1 sha1 = SHA1.Create();
			return Convert.ToBase64String(sha1.ComputeHash(operand));
		}

		public string GetCreatedAsString()
		{
			return XmlConvert.ToString(Created.ToUniversalTime(), "yyyy-MM-ddTHH:mm:ssZ");
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

			writer.WriteString(Convert.ToBase64String(Nonce));
			writer.WriteEndElement();
			/* end nonce */

			/* Created */
			writer.WriteStartElement(Constants.Oasis.WsseUtilityNamespacePrefix,
									Constants.UserNameToken.CreatedElementName,
									Constants.Oasis.WsseUtilityNamespace);

			writer.WriteString(GetCreatedAsString());
			writer.WriteEndElement();
			/* end Created */

			writer.WriteEndElement();
		}

		public override string Name
		{
			get { return NameValue; }
		}

		public override string Namespace
		{
			get { return Constants.Oasis.WsseNamespace; }
		}



		public override bool MustUnderstand
		{
			get
			{
				return true;
			}
		}

		internal static UserNameSecurityTokenHeader FromUserNamePassword(string userName, string password)
		{
			UserNameSecurityTokenHeader header = new UserNameSecurityTokenHeader();
			header.UserName = userName;
			header.Password = password;
			header.Created = DateTime.Now;
			header.Nonce = new byte[16];

			return header;
		}
	}
}
