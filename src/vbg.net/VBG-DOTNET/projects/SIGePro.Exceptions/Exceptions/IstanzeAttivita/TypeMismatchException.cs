namespace Init.SIGePro.Exceptions.IstanzeAttivita
{
	/// <summary>
	/// Descrizione di riepilogo per TypeMismatchException.
	/// </summary>
	public class TypeMismatchException : BaseException
	{
		public TypeMismatchException( Init.SIGePro.Data.IstanzeAttivita istanzeAttivita, string message):base( message + " [ISTANZEATTIVITA.IDCOMUNE: " + istanzeAttivita.IDCOMUNE + ", ISTANZEATTIVITA.CODICEISTANZA: " + istanzeAttivita.CODICEISTANZA + ", ISTANZEATTIVITA.IDATTIVITA: " + istanzeAttivita.IDATTIVITA + "]" ){}
	}
}
