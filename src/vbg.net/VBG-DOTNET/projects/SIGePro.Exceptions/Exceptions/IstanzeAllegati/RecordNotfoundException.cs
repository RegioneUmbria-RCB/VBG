namespace Init.SIGePro.Exceptions.IstanzeAllegati
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.IstanzeAllegati istanzeAllegati, string message):base( message + " [ISTANZEALLEGATI.IDCOMUNE: " + istanzeAllegati.IDCOMUNE + ", ISTANZEALLEGATI.CODICEINVENTARIO: " + istanzeAllegati.CODICEINVENTARIO + ", ISTANZEALLEGATI.NUMEROALLEGATO: " + istanzeAllegati.NUMEROALLEGATO + ", ISTANZEALLEGATI.CODICEISTANZA: " + istanzeAllegati.CODICEISTANZA + "]" ){}
	}
}
