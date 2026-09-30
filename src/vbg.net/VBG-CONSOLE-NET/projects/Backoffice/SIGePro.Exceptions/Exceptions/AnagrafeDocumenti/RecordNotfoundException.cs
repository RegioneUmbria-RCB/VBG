namespace Init.SIGePro.Exceptions.AnagrafeDocumenti
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.AnagrafeDocumenti anagrafeDoc, string message):base( message + " [ANAGRAFEDOCUMENTI.IDCOMUNE: " + anagrafeDoc.IDCOMUNE + ", ANAGRAFEDOCUMENTI.ID: " + anagrafeDoc.ID + "]" ){}
	}
}
