namespace Init.SIGePro.Exceptions.Istanze
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.Istanze istanze, string message):base( message + " [ISTANZE.NUMEROISTANZA: " + istanze.NUMEROISTANZA + ", ISTANZE.IDCOMUNE: " + istanze.IDCOMUNE + ", ISTANZE.SOFTWARE: " + istanze.SOFTWARE + "]" ){}
	}
}
