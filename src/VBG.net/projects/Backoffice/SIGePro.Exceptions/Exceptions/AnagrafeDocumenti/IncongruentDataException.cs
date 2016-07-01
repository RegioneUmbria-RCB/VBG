namespace Init.SIGePro.Exceptions.AnagrafeDocumenti
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class IncongruentDataException : Init.SIGePro.Exceptions.IncongruentDataException
	{
		public IncongruentDataException( Init.SIGePro.Data.AnagrafeDocumenti anagrafeDoc, string message):base( message + " [ANAGRAFEDOCUMENTI.IDCOMUNE: " + anagrafeDoc.IDCOMUNE + ", ANAGRAFEDOCUMENTI.ID: " + anagrafeDoc.ID + "]" ){}
	}
}
