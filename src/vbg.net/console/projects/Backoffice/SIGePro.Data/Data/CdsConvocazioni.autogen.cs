
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
						/// File generato automaticamente dalla tabella CDSCONVOCAZIONI il 30/07/2008 16.24.11
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
						[DataTable("CDSCONVOCAZIONI")]
						[Serializable]
						public partial class CdsConvocazioni : BaseDataClass
						{
							#region Membri privati


                            private DateTime? m_dataconvocazione = null;

							private string m_oraconvocazione = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_idtestata = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.Decimal)]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							[KeyField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[KeyField("IDTESTATA" , Type=DbType.Decimal)]
							public int? Idtestata
							{
								get{ return m_idtestata; }
								set{ m_idtestata = value; }
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
							
							[DataField("ORACONVOCAZIONE" , Type=DbType.String, CaseSensitive=false, Size=15)]
							public string Oraconvocazione
							{
								get{ return m_oraconvocazione; }
								set{ m_oraconvocazione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				