namespace Init.SIGePro.Exceptions.IstanzeAllegati
{
	/// <summary>
	/// Descrizione di riepilogo per RequiredFieldException.
	/// </summary>
	public class RequiredFieldException : Init.SIGePro.Exceptions.RequiredFieldException
	{
		public RequiredFieldException( Init.SIGePro.Data.IstanzeAllegati istanzeAllegati, string message):base( message + " [ISTANZEALLEGATI.IDCOMUNE: " + istanzeAllegati.IDCOMUNE + ", ISTANZEALLEGATI.CODICEINVENTARIO: " + istanzeAllegati.CODICEINVENTARIO + ", ISTANZEALLEGATI.NUMEROALLEGATO: " + istanzeAllegati.NUMEROALLEGATO + ", ISTANZEALLEGATI.CODICEISTANZA: " + istanzeAllegati.CODICEISTANZA + "]" ){}
	}
}
