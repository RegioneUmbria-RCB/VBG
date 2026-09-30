
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
						/// File generato automaticamente dalla tabella HELPBASE il 03/07/2008 14.25.32
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
						[DataTable("HELPBASE")]
						[Serializable]
						public partial class HelpBase : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_software = null;

							private string m_contenttype = null;

							private string m_helptext = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("SOFTWARE" , Type=DbType.String, CaseSensitive=true, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[KeyField("CONTENTTYPE" , Type=DbType.String, CaseSensitive=true, Size=150)]
							public string Contenttype
							{
								get{ return m_contenttype; }
								set{ m_contenttype = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("HELPTEXT" , Type=DbType.String, CaseSensitive=false, Size=3500)]
							public string Helptext
							{
								get{ return m_helptext; }
								set{ m_helptext = value; }
							}
							
							#endregion

							#endregion
						}
					}
				