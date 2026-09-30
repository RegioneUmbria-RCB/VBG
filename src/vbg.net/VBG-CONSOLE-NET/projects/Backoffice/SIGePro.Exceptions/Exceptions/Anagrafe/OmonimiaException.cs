namespace Init.SIGePro.Exceptions.Anagrafe
{
	/// <summary>
	/// Errori di omonimia
	/// </summary>
	public class OmonimiaException : Init.SIGePro.Exceptions.OmonimiaException
	{
		public OmonimiaException( Init.SIGePro.Data.Anagrafe anagrafe, string message):base(message + " Anagrafe cercata [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + ", ANAGRAFE.PARTITAIVA: " + anagrafe.PARTITAIVA + ", ANAGRAFE.CODICEFISCALE: " + anagrafe.CODICEFISCALE + ", ANAGRAFE.NOMINATIVO: " + anagrafe.NOMINATIVO + " " + anagrafe.NOME + ", ANAGRAFE.DATANASCITA: " + anagrafe.DATANASCITA + ", ANAGRAFE.DATACREAZIONEDITTA: " + anagrafe.DATANOMINATIVO + ", ANAGRAFE.COMUNERESIDENZA: " + anagrafe.COMUNERESIDENZA + "]" )
		{
		}
	}

	/// <summary>
	/// Sono eccezioni che si possono verificare durante l'esecuzione del metodo
	/// IstanzeMgr.Extract.
	/// Se viene trovata una anagrafica in base ad uno dei criteri stabiliti in Extract
	/// (ricerca per CODICEFISCALE, per PARTITAIVA o per NOMINATIVO) viene comunque
	/// generato questo tipo di eccezione se tra l'anagrafica trovata e quella cercata
	/// ci sono delle incongruenze. Es. E' stata trovata un'anagrafica con lo stesso
	/// CODICEFISCALE ma ha NOMINATIVO diverso.
	/// </summary>
	public class OmonimiaExceptionWarning : Init.SIGePro.Exceptions.OmonimiaException
	{
		Init.SIGePro.Data.Anagrafe m_anagrafeCercata;
		Init.SIGePro.Data.Anagrafe m_anagrafeTrovata;

		public Init.SIGePro.Data.Anagrafe AnagrafeCercata
		{
			get { return m_anagrafeCercata; }
		}

		public Init.SIGePro.Data.Anagrafe AnagrafeTrovata
		{
			get { return m_anagrafeTrovata; }
		}

		public OmonimiaExceptionWarning( Init.SIGePro.Data.Anagrafe anagrafeCercata,Init.SIGePro.Data.Anagrafe anagrafeTrovata , string proprietaIncongruente):
			base( "Anagrafe cercata:\n" + anagrafeCercata.ToErrorString(proprietaIncongruente) + "Anagrafe trovata:\n" + anagrafeTrovata.ToErrorString(proprietaIncongruente) + "Errore:\nCampo " + proprietaIncongruente + " incongruente" )
		{
			m_anagrafeCercata = anagrafeCercata;
			m_anagrafeTrovata = anagrafeTrovata;
		}
//
//		public OmonimiaExceptionWarning( Init.SIGePro.Data.Anagrafe anagrafeCercata,Init.SIGePro.Data.Anagrafe anagrafeTrovata , string message):
//			base( "Anagrafe cercata:\n" + anagrafeCercata.ToString() + "Anagrafe trovata:\n" + anagrafeTrovata.ToString() + "Errore:\n" + message )
//		{
//		}

		public OmonimiaExceptionWarning( Init.SIGePro.Data.Anagrafe anagrafe, string message):base(message + " Anagrafe cercata [ANAGRAFE.IDCOMUNE: " + anagrafe.IDCOMUNE + ", ANAGRAFE.CODICEANAGRAFE: " + anagrafe.CODICEANAGRAFE + ", ANAGRAFE.PARTITAIVA: " + anagrafe.PARTITAIVA + ", ANAGRAFE.CODICEFISCALE: " + anagrafe.CODICEFISCALE + ", ANAGRAFE.NOMINATIVO: " + anagrafe.NOMINATIVO + " " + anagrafe.NOME + ", ANAGRAFE.DATANASCITA: " + anagrafe.DATANASCITA + ", ANAGRAFE.DATACREAZIONEDITTA: " + anagrafe.DATANOMINATIVO + ", ANAGRAFE.COMUNERESIDENZA: " + anagrafe.COMUNERESIDENZA + "]" )
		{
		}
	}
}
