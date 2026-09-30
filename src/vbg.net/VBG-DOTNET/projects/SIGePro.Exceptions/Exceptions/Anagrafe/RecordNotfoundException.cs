namespace Init.SIGePro.Exceptions.Anagrafe
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.Anagrafe anagrafe, string message):base( message + " [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + "]" ){}
	}
}
