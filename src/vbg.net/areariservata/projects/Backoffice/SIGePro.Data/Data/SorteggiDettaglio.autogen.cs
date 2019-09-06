
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
						/// File generato automaticamente dalla tabella SORTEGGIDETTAGLIO il 27/01/2009 8.43.47
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
						[DataTable("SORTEGGIDETTAGLIO")]
						[Serializable]
						public partial class SorteggiDettaglio : BaseDataClass
						{
							#region Membri privati

                            private int? m_sd_id = null;

                            private int? m_sd_fk_stid = null;

							private string m_idcomune = null;

                            private int? m_codiceistanza = null;

                            private int? m_sorteggiata = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("SD_ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? SdId
							{
								get{ return m_sd_id; }
								set{ m_sd_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("SD_FK_STID" , Type=DbType.Decimal)]
							public int? SdFkStid
							{
								get{ return m_sd_fk_stid; }
								set{ m_sd_fk_stid = value; }
							}
							
							[DataField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[DataField("SORTEGGIATA" , Type=DbType.Decimal)]
							public int? Sorteggiata
							{
								get{ return m_sorteggiata; }
								set{ m_sorteggiata = value; }
							}
							
							#endregion

							#endregion
						}
					}
				