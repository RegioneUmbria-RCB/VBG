
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
						/// File generato automaticamente dalla tabella HELP il 03/07/2008 12.58.39
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
						[DataTable("HELP")]
						[Serializable]
						public partial class Help : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

							private string m_contenttype = null;

							private string m_helptext = null;

							private string m_software = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, CaseSensitive=true, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("CONTENTTYPE" , Type=DbType.String, CaseSensitive=true, Size=150)]
							public string Contenttype
							{
								get{ return m_contenttype; }
								set{ m_contenttype = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, CaseSensitive=true, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
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
				