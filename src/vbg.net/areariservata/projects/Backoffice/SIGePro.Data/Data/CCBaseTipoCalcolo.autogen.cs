
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
						/// File generato automaticamente dalla tabella CC_BASETIPOCALCOLO il 27/06/2008 16.48.53
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
						[DataTable("CC_BASETIPOCALCOLO")]
						[Serializable]
						public partial class CCBaseTipoCalcolo : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_id = null;

							private string m_tipocalcolo = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.String, Size=3)]
							public string Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("TIPOCALCOLO" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Tipocalcolo
							{
								get{ return m_tipocalcolo; }
								set{ m_tipocalcolo = value; }
							}
							
							#endregion

							#endregion
						}
					}
				