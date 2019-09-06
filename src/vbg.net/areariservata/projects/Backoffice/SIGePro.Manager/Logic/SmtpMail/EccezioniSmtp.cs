using System;
using System.Collections.Generic;
using System.Text;
using System.Web.Services.Protocols;

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	/// <summary>
	/// Summary description for Exceptions
	/// </summary>
	public class ErrorMessages
	{
		public const string ERR_CONFIGURAZIONE = "EM001 - Errore nella configurazione: {0}";
		public const string ERR_INDIRIZZO_MITTENTE_NON_VALIDO = "EM002 - Il formato dell'indirizzo del mittente non è valido (Indirizzo errato:{0})";
		public const string ERR_INDIRIZZO_DESTINATARIO_NON_VALIDO = "EM003 - Il formato dell'indirizzo di uno dei destinatari non è valido (Indirizzo errato:{0})";
		public const string ERR_INDIRIZZO_DESTINATARIO_CC_NON_VALIDO = "EM004 - Il formato dell'indirizzo di uno dei destinatari in copia non è valido (Indirizzo errato:{0})";
		public const string ERR_INDIRIZZO_DESTINATARIO_BCC_NON_VALIDO = "EM005 - Il formato dell'indirizzo di uno dei destinatari in copia nascosta non è valido (Indirizzo errato:{0})";
		public const string ERR_AUTENTICAZIONE_FALLITA = "EM006 - Autenticazione fallita. Token {0} non valido.";
		public const string ERR_NOME_ALLEGATO_NON_SPECIFICATO = "EM007 - Nome allegato non specificato. (Id allegato: {0})";
		public const string ERR_ECCEZIONE_IMPREVISTA = "EM999 - Eccezione imprevista: {0}";
	}


	public class AuthenticationFailedException : Exception
	{
		public AuthenticationFailedException(string token)
			: base(String.Format(ErrorMessages.ERR_AUTENTICAZIONE_FALLITA, token))
		{
		}
	}


	public class NoFileNameSpecifiedException : Exception
	{
		internal NoFileNameSpecifiedException(int attachId)
			: base(String.Format(ErrorMessages.ERR_NOME_ALLEGATO_NON_SPECIFICATO, attachId))
		{
		}
	}

	public class AddressException : Exception
	{
		internal AddressException(string message, string address)
			: base(String.Format(message, address))
		{
		}
	}

	public class ConfigException : Exception
	{
		internal ConfigException(string message)
			: base(String.Format(ErrorMessages.ERR_CONFIGURAZIONE, message))
		{
		}
	}

	public class InvalidSenderException : AddressException
	{
		public InvalidSenderException(string address) : base(ErrorMessages.ERR_INDIRIZZO_MITTENTE_NON_VALIDO, address) { }
	}

	public class InvalidRecipientException : AddressException
	{
		public InvalidRecipientException(string address) : base(ErrorMessages.ERR_INDIRIZZO_DESTINATARIO_NON_VALIDO, address) { }
	}

	public class InvalidCcRecipientException : AddressException
	{
		public InvalidCcRecipientException(string address) : base(ErrorMessages.ERR_INDIRIZZO_DESTINATARIO_CC_NON_VALIDO, address) { }
	}

	public class InvalidBccRecipientException : AddressException
	{
		public InvalidBccRecipientException(string address) : base(ErrorMessages.ERR_INDIRIZZO_DESTINATARIO_BCC_NON_VALIDO, address) { }
	}

	public class UnknownException : Exception
	{
		public UnknownException(Exception ex)
			: base(String.Format(ErrorMessages.ERR_ECCEZIONE_IMPREVISTA, ex.ToString()))
		{
		}
	}

	public class CustomSoapException : SoapException
	{
		public CustomSoapException(Exception ex)
			: base(ex.Message, new System.Xml.XmlQualifiedName(ex.Message.Substring(0, 5)))
		{

		}

		public override string ToString()
		{
			return this.Message;
		}
	}
}
