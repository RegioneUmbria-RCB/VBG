namespace Init.SIGePro.Exceptions.Anagrafe
{
	/// <summary>
	/// Descrizione di riepilogo per TypeMismatchException.
	/// </summary>
	public class TypeMismatchException : BaseException
	{
		public TypeMismatchException( Init.SIGePro.Data.Anagrafe anagrafe, string message):base( message + " [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + "]" ){}
	}
}
