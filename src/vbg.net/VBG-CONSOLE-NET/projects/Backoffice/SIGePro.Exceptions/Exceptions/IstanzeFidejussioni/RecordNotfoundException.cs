namespace Init.SIGePro.Exceptions.IstanzeFidejussioni
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.IstanzeFidejussioni istanzeFidejussioni, string message):base( message + " [ISTANZEFIDEJUSSIONI.IDCOMUNE: " + istanzeFidejussioni.IDCOMUNE + ", ISTANZEFIDEJUSSIONI.CODICEISTANZA: " + istanzeFidejussioni.CODICEISTANZA + ", ISTANZEFIDEJUSSIONI.IDFIDEJUSSIONE: " + istanzeFidejussioni.IDFIDEJUSSIONE + "]" ){}
	}
}
