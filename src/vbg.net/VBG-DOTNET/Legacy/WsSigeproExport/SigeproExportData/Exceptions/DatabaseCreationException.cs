using System;

namespace Init.SIGeProExport.Exceptions
{
	/// <summary>
	/// Eccezione di tipo DatabaseCreationException.
	/// </summary>
	public class DatabaseCreationException : System.Exception
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
