namespace Init.SIGePro.Exceptions.Autorizzazioni
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.Autorizzazioni autorizzazioni, string message):base( message + " [AUTORIZZAZIONI.IDCOMUNE: " + autorizzazioni.IDCOMUNE + ", AUTORIZZAZIONI.FKIDREGISTRO: " + autorizzazioni.FKIDREGISTRO + ", AUTORIZZAZIONI.FKIDISTANZA: " + autorizzazioni.FKIDISTANZA + "]" ){}
	}
}
