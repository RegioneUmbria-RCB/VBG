namespace Init.SIGePro.Exceptions.I_Attivita
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class MoreThanOneRecordException : Init.SIGePro.Exceptions.MoreThanOneRecordException
	{
		public MoreThanOneRecordException( Init.SIGePro.Data.IAttivita i_attivita ):base( "Sono stati trovati più record nella tabella I_ATTIVITA con I_ATTIVITA.IDCOMUNE: " + i_attivita.IdComune + ", I_ATTIVITA.DENOMINAZIONE: " + i_attivita.Denominazione ){}
	}
}
