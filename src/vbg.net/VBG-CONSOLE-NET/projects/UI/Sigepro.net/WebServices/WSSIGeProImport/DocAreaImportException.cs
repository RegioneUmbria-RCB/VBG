using System;

namespace SIGePro.Net.WebServices.WSSIGeProImport
{
	/// <summary>
	/// Descrizione di riepilogo per DocAreaImportException.
	/// </summary>
	public class DocAreaImportException : Exception
	{
		public DocAreaImportException(string message, Exception innerException) : base(message, innerException)
		{
		}

		public DocAreaImportException(string message) : base(message, null)
		{
		}

		public DocAreaImportException() : base()
		{
		}

	}
}