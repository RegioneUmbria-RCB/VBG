namespace Init.SIGePro.Exceptions.Anagrafe
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class IncongruentDataException : BaseException
	{
		public IncongruentDataException( Init.SIGePro.Data.Anagrafe anagrafe, string message):base( message + " [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + "]" ){}
	}
}
