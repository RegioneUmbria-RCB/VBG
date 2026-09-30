namespace Init.SIGePro.Exceptions.IstanzeAttivita
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class IncongruentDataException : Init.SIGePro.Exceptions.IncongruentDataException
	{
		public IncongruentDataException( Init.SIGePro.Data.IstanzeAttivita istanzeAttivita, string message):base( message + " [ISTANZEATTIVITA.IDCOMUNE: " + istanzeAttivita.IDCOMUNE + ", ISTANZEATTIVITA.CODICEISTANZA: " + istanzeAttivita.CODICEISTANZA + ", ISTANZEATTIVITA.IDATTIVITA: " + istanzeAttivita.IDATTIVITA + "]" ){}
	}
}
