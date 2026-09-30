namespace Init.SIGePro.Exceptions.Mercati_Uso
{
	/// <summary>
	/// Descrizione di riepilogo per RecordNotfoundException.
	/// </summary>
	public class MoreThanOneRecordException : Init.SIGePro.Exceptions.MoreThanOneRecordException
	{
		public MoreThanOneRecordException( Init.SIGePro.Data.Mercati_Uso mercati_uso ):base( "Sono stati trovati più record nella tabella MERCATI_USO con MERCATI_USO.FKCODICEMERCATO: " + mercati_uso.FkCodiceMercato + ", MERCATI_USO.FKCODICEUSO: " + mercati_uso.FkCodiceUso + ", MERCATI_USO.FKGSID" + mercati_uso.FkGsId ){}
	}
}
