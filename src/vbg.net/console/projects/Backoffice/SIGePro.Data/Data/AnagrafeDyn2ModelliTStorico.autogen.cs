
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
						/// File generato automaticamente dalla tabella ANAGRAFEDYN2MODELLIT_STORICO il 22/02/2010 12.32.49
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
						[DataTable("ANAGRAFEDYN2MODELLIT_STORICO")]
						[Serializable]
						public partial class AnagrafeDyn2ModelliTStorico : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_idversione = null;

							private int? m_codiceanagrafe = null;

							private int? m_fk_d2mt_id = null;

							private DateTime? m_dataversione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("IDVERSIONE" , Type=DbType.Decimal)]
							public int? Idversione
							{
								get{ return m_idversione; }
								set{ m_idversione = value; }
							}
							
							[KeyField("CODICEANAGRAFE" , Type=DbType.Decimal)]
							public int? Codiceanagrafe
							{
								get{ return m_codiceanagrafe; }
								set{ m_codiceanagrafe = value; }
							}
							
							[KeyField("FK_D2MT_ID" , Type=DbType.Decimal)]
							public int? FkD2mtId
							{
								get{ return m_fk_d2mt_id; }
								set{ m_fk_d2mt_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("DATAVERSIONE" , Type=DbType.DateTime)]
							public DateTime? Dataversione
							{
								get{ return m_dataversione; }
								set{ m_dataversione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				