namespace Init.SIGePro.Exceptions.IstanzeAllegati
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class IncongruentDataException : Init.SIGePro.Exceptions.IncongruentDataException
	{
		public IncongruentDataException( Init.SIGePro.Data.IstanzeAllegati istanzeAllegati, string message):base( message + " [ISTANZEALLEGATI.IDCOMUNE: " + istanzeAllegati.IDCOMUNE + ", ISTANZEALLEGATI.CODICEINVENTARIO: " + istanzeAllegati.CODICEINVENTARIO + ", ISTANZEALLEGATI.NUMEROALLEGATO: " + istanzeAllegati.NUMEROALLEGATO + ", ISTANZEALLEGATI.CODICEISTANZA: " + istanzeAllegati.CODICEISTANZA + "]" ){}
	}
}
