namespace Init.SIGePro.Exceptions.DocumentiIstanza
{
	/// <summary>
	/// Descrizione di riepilogo per TypeMismatchException.
	/// </summary>
	public class TypeMismatchException : BaseException
	{
		public TypeMismatchException( Init.SIGePro.Data.DocumentiIstanza documentiIstanza, string message):base( message + " [DOCUMENTIISTANZA.IDCOMUNE: " + documentiIstanza.IDCOMUNE + ", DOCUMENTIISTANZA.CODICEISTANZA: " + documentiIstanza.CODICEISTANZA + ", DOCUMENTIISTANZA.ID: " + documentiIstanza.Id + "]" ){}
	}
}
