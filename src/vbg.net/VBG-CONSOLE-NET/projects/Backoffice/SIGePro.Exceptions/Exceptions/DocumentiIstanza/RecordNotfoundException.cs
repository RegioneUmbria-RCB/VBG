namespace Init.SIGePro.Exceptions.DocumentiIstanza
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.DocumentiIstanza documentiIstanza, string message):base( message + " [DOCUMENTIISTANZA.IDCOMUNE: " + documentiIstanza.IDCOMUNE + ", DOCUMENTIISTANZA.CODICEISTANZA: " + documentiIstanza.CODICEISTANZA + ", DOCUMENTIISTANZA.ID: " + documentiIstanza.Id + "]" ){}
	}
}
