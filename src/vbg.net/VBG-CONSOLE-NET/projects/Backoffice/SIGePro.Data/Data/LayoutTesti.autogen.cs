
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
						/// File generato automaticamente dalla tabella LAYOUTTESTI il 02/12/2009 16.47.29
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
						[DataTable("LAYOUTTESTI")]
						[Serializable]
						public partial class LayoutTesti : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

							private string m_codicetesto = null;

							private string m_nuovotesto = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[KeyField("CODICETESTO" , Type=DbType.String, Size=50)]
							public string Codicetesto
							{
								get{ return m_codicetesto; }
								set{ m_codicetesto = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("NUOVOTESTO" , Type=DbType.String, CaseSensitive=false, Size=250)]
							public string Nuovotesto
							{
								get{ return m_nuovotesto; }
								set{ m_nuovotesto = value; }
							}
							
							#endregion

							#endregion
						}
					}
				