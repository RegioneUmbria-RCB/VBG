
					using System;
					using System.Data;
					using System.Reflection;
					using System.Text;
					using Init.SIGePro.Attributes;
					using Init.SIGePro.Collection;
					using PersonalLib2.Sql.Attributes;
					using PersonalLib2.Sql;

					namespace Init.SIGePro.Data
					{
						///
						/// File generato automaticamente dalla tabella MAPPATUREPEOPLER il 14/01/2010 16.22.22
						///
						///												ATTENZIONE!!!
						///	- Specificare manualmente in quali colonne vanno applicate eventuali sequenze		
						/// - Verificare l'applicazione di eventuali attributi di tipo "[isRequired]". In caso contrario applicarli manualmente
						///	- Verificare che il tipo di dati assegnato alle proprietà sia corretto
						///
						///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
						///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
						/// -
						/// -
						/// -
						/// - 
						///
						///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
						///
						[DataTable("MAPPATUREPEOPLER")]
						[Serializable]
						public partial class MappaturePeopleR : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_fkidmappatura = null;

							private int? m_idregola = null;

							private int? m_idtiporegola = null;

							private string m_nometagpeople = null;

							private string m_valoresigepro = null;

							private string m_valorefisso = null;

							private string m_valoreriferimento = null;

							private int? m_usadescrizione = null;

							private string m_proprieta = null;

							private string m_descrizioneproprieta = null;

							private string m_codicetestata = null;

							private string m_codiceriga = null;

							private string m_codicesoftware = null;

							private int? m_accoda = null;

							private string m_separatore = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FKIDMAPPATURA" , Type=DbType.Decimal)]
							public int? Fkidmappatura
							{
								get{ return m_fkidmappatura; }
								set{ m_fkidmappatura = value; }
							}
							
							[KeyField("IDREGOLA" , Type=DbType.Decimal)]
							[useSequence]
							public int? Idregola
							{
								get{ return m_idregola; }
								set{ m_idregola = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("IDTIPOREGOLA" , Type=DbType.Decimal)]
							public int? Idtiporegola
							{
								get{ return m_idtiporegola; }
								set{ m_idtiporegola = value; }
							}
							
							[DataField("NOMETAGPEOPLE" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Nometagpeople
							{
								get{ return m_nometagpeople; }
								set{ m_nometagpeople = value; }
							}
							
							[DataField("VALORESIGEPRO" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Valoresigepro
							{
								get{ return m_valoresigepro; }
								set{ m_valoresigepro = value; }
							}
							
							[DataField("VALOREFISSO" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Valorefisso
							{
								get{ return m_valorefisso; }
								set{ m_valorefisso = value; }
							}
							
							[DataField("VALORERIFERIMENTO" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Valoreriferimento
							{
								get{ return m_valoreriferimento; }
								set{ m_valoreriferimento = value; }
							}
							
							[DataField("USADESCRIZIONE" , Type=DbType.Decimal)]
							public int? Usadescrizione
							{
								get{ return m_usadescrizione; }
								set{ m_usadescrizione = value; }
							}
							
							[DataField("PROPRIETA" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Proprieta
							{
								get{ return m_proprieta; }
								set{ m_proprieta = value; }
							}
							
							[DataField("DESCRIZIONEPROPRIETA" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Descrizioneproprieta
							{
								get{ return m_descrizioneproprieta; }
								set{ m_descrizioneproprieta = value; }
							}
							
							[DataField("CODICETESTATA" , Type=DbType.String, CaseSensitive=false, Size=10)]
							public string Codicetestata
							{
								get{ return m_codicetestata; }
								set{ m_codicetestata = value; }
							}
							
							[DataField("CODICERIGA" , Type=DbType.String, CaseSensitive=false, Size=10)]
							public string Codiceriga
							{
								get{ return m_codiceriga; }
								set{ m_codiceriga = value; }
							}
							
							[DataField("CODICESOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Codicesoftware
							{
								get{ return m_codicesoftware; }
								set{ m_codicesoftware = value; }
							}
							
							[DataField("ACCODA" , Type=DbType.Decimal)]
							public int? Accoda
							{
								get{ return m_accoda; }
								set{ m_accoda = value; }
							}
							
							[DataField("SEPARATORE" , Type=DbType.String, CaseSensitive=false, Size=10)]
							public string Separatore
							{
								get{ return m_separatore; }
								set{ m_separatore = value; }
							}
							
							#endregion

							#endregion
						}
					}
				