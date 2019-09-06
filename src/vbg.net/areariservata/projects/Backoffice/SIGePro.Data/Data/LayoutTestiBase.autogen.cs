
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
						/// File generato automaticamente dalla tabella LAYOUTTESTIBASE il 02/12/2009 16.43.01
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
						[DataTable("LAYOUTTESTIBASE")]
						[Serializable]
						public partial class LayoutTestiBase : BaseDataClass
						{
							#region Membri privati
							
							private string m_codicetesto = null;

							private string m_testo = null;

							private string m_software = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICETESTO" , Type=DbType.String, Size=50)]
							public string Codicetesto
							{
								get{ return m_codicetesto; }
								set{ m_codicetesto = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							#endregion

							#endregion
						}
					}
				