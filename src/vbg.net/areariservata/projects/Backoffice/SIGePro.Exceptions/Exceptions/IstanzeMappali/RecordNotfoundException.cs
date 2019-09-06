namespace Init.SIGePro.Exceptions.IstanzeMappali
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class RecordNotfoundException : Init.SIGePro.Exceptions.RecordNotfoundException
	{
		public RecordNotfoundException( Init.SIGePro.Data.IstanzeMappali istanzeMappali, string message):base( message + " [ISTANZEMAPPALI.IDCOMUNE: " + istanzeMappali.Idcomune + ", ISTANZEMAPPALI.IDMAPPALE: " + istanzeMappali.Idmappale + "]" ){}
	}
}
