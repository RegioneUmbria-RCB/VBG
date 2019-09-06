using System;

namespace Init.SIGePro.Exceptions
{
	/// <summary>
	/// Eccezione di tipo DatabaseCreationException.
	/// </summary>
    public class DatabaseCreationException : BaseException
	{
		
		public DatabaseCreationException( string message , System.Exception innerException ) : base( message , innerException )
		{
		}

		public DatabaseCreationException( string message  ) : base( message , null )
		{
		}

		public DatabaseCreationException( ) : base( )
		{
		}
	}
}
