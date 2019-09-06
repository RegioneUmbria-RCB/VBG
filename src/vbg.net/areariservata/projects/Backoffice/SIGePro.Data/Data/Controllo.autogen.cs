
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
						/// File generato automaticamente dalla tabella CONTROLLO il 30/07/2008 16.33.27
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
						[DataTable("CONTROLLO")]
						[Serializable]
						public partial class Controllo : BaseDataClass
						{
							#region Membri privati
					
							
							private int? m_codiceistanza = null;

                            private DateTime? m_dataconvocazione = null;

							private string m_odg = null;

							private string m_note = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("DATACONVOCAZIONE" , Type=DbType.DateTime)]
							public DateTime? Dataconvocazione
							{
								get{ return m_dataconvocazione; }
                                set { m_dataconvocazione = VerificaDataLocale(value); }
							}
							
							[DataField("ODG" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Odg
							{
								get{ return m_odg; }
								set{ m_odg = value; }
							}
							
							[DataField("NOTE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Note
							{
								get{ return m_note; }
								set{ m_note = value; }
							}
							
							#endregion

							#endregion
						}
					}
				