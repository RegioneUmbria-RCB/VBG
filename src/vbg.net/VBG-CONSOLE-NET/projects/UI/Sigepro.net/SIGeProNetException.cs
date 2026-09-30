using System;
using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Utils;
using PersonalLib2.Data;

namespace SIGePro.Net
{
	/// <summary>
	/// Eccezione di tipo SIGeProNetException.
	/// </summary>
	public class SIGeProNetException : Exception
	{
		public SIGeProNetException(AuthenticationInfo authInfo , string modulo,string message, Exception innerException) : base(message, innerException)
		{
			if (authInfo != null)
				Logger.LogEvent( authInfo.CreateDatabase(),authInfo.IdComune,modulo,message,modulo );
		}

		public SIGeProNetException(AuthenticationInfo authInfo , string modulo,string message) : this(authInfo,modulo , message, null)
		{
		}

		public SIGeProNetException(DataBase db , string idComune , string modulo,string message, Exception innerException) : base(message, innerException)
		{
			Logger.LogEvent( db,idComune,modulo,message,modulo );
		}

		public SIGeProNetException(DataBase db ,string idComune , string modulo,string message) : this(db,idComune,modulo,message, null)
		{
		}

		public SIGeProNetException(string idComune ,string message)
		{
			// TODO: come loggare in questo caso?
		}
		public SIGeProNetException() : base()
		{
		}
	}
}