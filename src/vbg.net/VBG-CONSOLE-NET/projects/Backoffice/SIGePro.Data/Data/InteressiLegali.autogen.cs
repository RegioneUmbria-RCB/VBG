
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
						/// File generato automaticamente dalla tabella INTERESSI_LEGALI il 20/07/2009 14.56.51
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
						[DataTable("INTERESSI_LEGALI")]
						[Serializable]
						public partial class InteressiLegali : BaseDataClass
						{
							#region Membri privati

                            private int? m_id = null;

                            private DateTime? m_data_inizio = null;

                            private DateTime? m_data_fine = null;

                            private double? m_tasso_percentuale = null;

							private string m_disposizione_normativa = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.Decimal)]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
                            [DataField("DATA_INIZIO" , Type=DbType.DateTime)]
							public DateTime? DataInizio
							{
								get{ return m_data_inizio; }
                                set { m_data_inizio = VerificaDataLocale(value); }
							}
							
							[DataField("DATA_FINE" , Type=DbType.DateTime)]
							public DateTime? DataFine
							{
								get{ return m_data_fine; }
                                set { m_data_fine = VerificaDataLocale(value); }
							}
							
							[isRequired]
                            [DataField("TASSO_PERCENTUALE" , Type=DbType.Decimal)]
							public double? TassoPercentuale
							{
								get{ return m_tasso_percentuale; }
								set{ m_tasso_percentuale = value; }
							}
							
							[DataField("DISPOSIZIONE_NORMATIVA" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string DisposizioneNormativa
							{
								get{ return m_disposizione_normativa; }
								set{ m_disposizione_normativa = value; }
							}
							
							#endregion

							#endregion
						}
					}
				