namespace Init.SIGePro.Exceptions.DynDati
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.DynDati dynDati, string message):base( message + " [DYNDATI.IDCOMUNE: " + dynDati.IDCOMUNE + ", DYNDATI.CODICERECORDCORRELATO: " + dynDati.CODICERECORDCORRELATO + ", DYNDATI.CODICECAMPO: " + dynDati.CODICECAMPO + ", DYNDATI.FKIDTESTATA: " + dynDati.FKIDTESTATA + "]" ){}
	}
}
