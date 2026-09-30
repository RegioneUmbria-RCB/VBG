
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
						/// File generato automaticamente dalla tabella CONFIGURAZIONEUTENTE il 16/12/2008 10.39.59
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
						[DataTable("CONFIGURAZIONEUTENTE")]
						[Serializable]
						public partial class ConfigurazioneUtente : BaseDataClass
						{
							#region Membri privati

                            private int? m_codiceresponsabile = null;

							private string m_nomeparametro = null;

							private string m_valore = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICERESPONSABILE" , Type=DbType.Decimal)]
							public int? Codiceresponsabile
							{
								get{ return m_codiceresponsabile; }
								set{ m_codiceresponsabile = value; }
							}
							
							[KeyField("NOMEPARAMETRO" , Type=DbType.String, Size=50)]
							public string Nomeparametro
							{
								get{ return m_nomeparametro; }
								set{ m_nomeparametro = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("VALORE" , Type=DbType.String, CaseSensitive=false, Size=300)]
							public string Valore
							{
								get{ return m_valore; }
								set{ m_valore = value; }
							}
							
							#endregion

							#endregion
						}
					}
				