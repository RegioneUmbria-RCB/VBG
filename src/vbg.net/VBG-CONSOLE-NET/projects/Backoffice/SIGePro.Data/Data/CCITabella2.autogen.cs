
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
						/// File generato automaticamente dalla tabella CC_ITABELLA2 il 27/06/2008 13.01.39
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
						[DataTable("CC_ITABELLA2")]
						[Serializable]
						public partial class CCITabella2 : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_fk_ccic_id = null;

                            private double? m_superficie = null;

                            private int? m_fk_ccds_id = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, CaseSensitive=true, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
                            [DataField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[isRequired]
                            [DataField("FK_CCIC_ID" , Type=DbType.Decimal)]
							public int? FkCcicId
							{
								get{ return m_fk_ccic_id; }
								set{ m_fk_ccic_id = value; }
							}
							
							[isRequired]
                            [DataField("SUPERFICIE" , Type=DbType.Decimal)]
							public double? Superficie
							{
								get{ return m_superficie; }
								set{ m_superficie = value; }
							}
							
							[DataField("FK_CCDS_ID" , Type=DbType.Decimal)]
							public int? FkCcdsId
							{
								get{ return m_fk_ccds_id; }
								set{ m_fk_ccds_id = value; }
							}
							
							#endregion

							#endregion
						}
					}
				