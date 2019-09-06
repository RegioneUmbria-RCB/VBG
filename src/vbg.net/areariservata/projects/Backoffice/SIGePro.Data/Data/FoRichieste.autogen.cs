
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
						/// File generato automaticamente dalla tabella FO_RICHIESTE il 11/12/2009 10.32.39
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
						[DataTable("FO_RICHIESTE")]
						[Serializable]
						public partial class FoRichieste : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_id = null;

							private int? m_codiceanagrafe = null;

							private DateTime? m_datarichiesta = null;

							private int? m_codicerichiesta = null;

							private int? m_codiceoggetto = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("ID" , Type=DbType.Decimal)]
[useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							/*[isRequired]*/
[DataField("CODICEANAGRAFE" , Type=DbType.Decimal)]
							public int? Codiceanagrafe
							{
								get{ return m_codiceanagrafe; }
								set{ m_codiceanagrafe = value; }
							}
							
							[isRequired]
[DataField("DATARICHIESTA" , Type=DbType.DateTime)]
							public DateTime? Datarichiesta
							{
								get{ return m_datarichiesta; }
								set{ m_datarichiesta = value; }
							}
							
							[isRequired]
[DataField("CODICERICHIESTA" , Type=DbType.Decimal)]
							public int? Codicerichiesta
							{
								get{ return m_codicerichiesta; }
								set{ m_codicerichiesta = value; }
							}
							
							[isRequired]
[DataField("CODICEOGGETTO" , Type=DbType.Decimal)]
							public int? Codiceoggetto
							{
								get{ return m_codiceoggetto; }
								set{ m_codiceoggetto = value; }
							}
							
							#endregion

							#endregion
						}
					}
				