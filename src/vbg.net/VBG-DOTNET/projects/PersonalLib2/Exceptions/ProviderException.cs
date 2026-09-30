using System;

namespace PersonalLib2.Exceptions
{
	/// <summary>
	/// Descrizione di riepilogo per ProviderException.
	/// </summary>
	public class ProviderException : Exception
	{
		public ProviderException()
		{
		}

		public ProviderException(Exception inner) : base(inner.Message, inner)
		{
		}

		public ProviderException(string personalMessage, Exception inner) : base(inner.Message + ": " + personalMessage, inner)
		{
		}

		public ProviderException(string personalMessage) : base(personalMessage)
		{
		}
	}
}