namespace Init.SIGePro.Exceptions.IstanzeAllegati
{
	/// <summary>
	/// Descrizione di riepilogo per TypeMismatchException.
	/// </summary>
	public class TypeMismatchException : BaseException
	{
		public TypeMismatchException( Init.SIGePro.Data.IstanzeAllegati istanzeAllegati, string message):base( message + " [ISTANZEALLEGATI.IDCOMUNE: " + istanzeAllegati.IDCOMUNE + ", ISTANZEALLEGATI.CODICEINVENTARIO: " + istanzeAllegati.CODICEINVENTARIO + ", ISTANZEALLEGATI.NUMEROALLEGATO: " + istanzeAllegati.NUMEROALLEGATO + ", ISTANZEALLEGATI.CODICEISTANZA: " + istanzeAllegati.CODICEISTANZA + "]" ){}
	}
}
