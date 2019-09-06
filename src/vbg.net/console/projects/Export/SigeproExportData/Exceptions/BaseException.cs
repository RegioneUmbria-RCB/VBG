namespace Init.SIGeProExport.Exceptions
{
	/// <summary>
	/// Descrizione di riepilogo per BaseException.
	/// </summary>
	public class BaseException : System.Exception
	{
		public BaseException():base()
		{
			//
			// TODO: aggiungere qui la logica del costruttore
			//
		}

		public BaseException(string message):base(message){}
		public BaseException(string message, System.Exception inner):base(message,inner){}

	}
}
