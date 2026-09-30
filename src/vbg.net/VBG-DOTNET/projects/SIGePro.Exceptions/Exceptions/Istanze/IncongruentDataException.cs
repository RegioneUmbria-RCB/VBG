namespace Init.SIGePro.Exceptions.Istanze
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class IncongruentDataException : Init.SIGePro.Exceptions.IncongruentDataException
	{
		public IncongruentDataException( Init.SIGePro.Data.Istanze istanze, string message):base( message + " [ISTANZE.NUMEROISTANZA: " + istanze.NUMEROISTANZA + ", ISTANZE.IDCOMUNE: " + istanze.IDCOMUNE + ", ISTANZE.SOFTWARE: " + istanze.SOFTWARE + "]" ){}
	}
}
