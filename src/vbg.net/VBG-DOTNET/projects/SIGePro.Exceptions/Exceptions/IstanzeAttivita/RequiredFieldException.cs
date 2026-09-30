namespace Init.SIGePro.Exceptions.IstanzeAttivita
{
	/// <summary>
	/// Descrizione di riepilogo per RequiredFieldException.
	/// </summary>
	public class RequiredFieldException : Init.SIGePro.Exceptions.RequiredFieldException
	{
		public RequiredFieldException( Init.SIGePro.Data.IstanzeAttivita istanzeAttivita, string message):base( message + " [ISTANZEATTIVITA.IDCOMUNE: " + istanzeAttivita.IDCOMUNE + ", ISTANZEATTIVITA.CODICEISTANZA: " + istanzeAttivita.CODICEISTANZA + ", ISTANZEATTIVITA.IDATTIVITA: " + istanzeAttivita.IDATTIVITA + "]" ){}
	}
}
