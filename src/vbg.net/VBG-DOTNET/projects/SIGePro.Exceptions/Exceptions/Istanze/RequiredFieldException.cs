namespace Init.SIGePro.Exceptions.Istanze
{
	/// <summary>
	/// Descrizione di riepilogo per RequiredFieldException.
	/// </summary>
	public class RequiredFieldException : Init.SIGePro.Exceptions.RequiredFieldException
	{
		public RequiredFieldException( Init.SIGePro.Data.Istanze istanze, string message):base( message + " [ISTANZE.NUMEROISTANZA: " + istanze.NUMEROISTANZA + ", ISTANZE.IDCOMUNE: " + istanze.IDCOMUNE + ", ISTANZE.SOFTWARE: " + istanze.SOFTWARE + "]" ){}
	}
}
