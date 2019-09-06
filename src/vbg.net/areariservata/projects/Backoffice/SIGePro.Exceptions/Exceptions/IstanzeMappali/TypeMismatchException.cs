namespace Init.SIGePro.Exceptions.IstanzeMappali
{
	/// <summary>
	/// Descrizione di riepilogo per TypeMismatchException.
	/// </summary>
	public class TypeMismatchException : BaseException
	{
		public TypeMismatchException( Init.SIGePro.Data.IstanzeMappali istanzeMappali, string message):base( message + " [ISTANZEMAPPALI.IDCOMUNE: " + istanzeMappali.Idcomune + ", ISTANZEMAPPALI.IDMAPPALE: " + istanzeMappali.Idmappale + "]" ){}
	}
}
