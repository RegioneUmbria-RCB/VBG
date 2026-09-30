namespace Init.SIGePro.Exceptions.Anagrafe
{
	/// <summary>
	/// Descrizione di riepilogo per RequiredFieldException.
	/// </summary>
	public class RequiredFieldException : Init.SIGePro.Exceptions.RequiredFieldException
	{
		public RequiredFieldException( Init.SIGePro.Data.Anagrafe anagrafe, string message):base( message + " [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + "]" ){}
	}
}
