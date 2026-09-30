namespace Init.SIGePro.Exceptions
{
	/// <summary>
	/// Descrizione di riepilogo per BaseException.
	/// </summary>
	public class BaseException : System.Exception
	{
		public BaseException():base()
		{
		}

		public BaseException(string message):base(message){}
		public BaseException(string message, System.Exception inner):base(message,inner){}

	}
}
