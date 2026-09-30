
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
						/// File generato automaticamente dalla tabella CC_ICALCOLOTOT il 27/06/2008 13.01.38
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
						[DataTable("CC_ICALCOLOTOT")]
						[Serializable]
						public partial class CCICalcoloTot : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private DateTime? m_data = null;

                            private int? m_fk_ccvc_id = null;

							private string m_fk_occbti_id = null;

							private string m_fk_occbde_id = null;

							private string m_fk_bcctc_id = null;

							private string m_descrizione = null;

                            private double? m_quotacontrib_totale = null;
			
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
                            [DataField("DATA" , Type=DbType.DateTime)]
							public DateTime? Data
							{
								get{ return m_data; }
                                set { m_data = VerificaDataLocale(value); }
							}
							
							[isRequired]
                            [DataField("FK_CCVC_ID" , Type=DbType.Decimal)]
							public int? FkCcvcId
							{
								get{ return m_fk_ccvc_id; }
								set{ m_fk_ccvc_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_OCCBTI_ID" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string FkOccbtiId
							{
								get{ return m_fk_occbti_id; }
								set{ m_fk_occbti_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_OCCBDE_ID" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string FkOccbdeId
							{
								get{ return m_fk_occbde_id; }
								set{ m_fk_occbde_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_BCCTC_ID" , Type=DbType.String, CaseSensitive=false, Size=3)]
							public string FkBcctcId
							{
								get{ return m_fk_bcctc_id; }
								set{ m_fk_bcctc_id = value; }
							}
							
							[isRequired]
                            [DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[isRequired]
                            [DataField("QUOTACONTRIB_TOTALE" , Type=DbType.Decimal)]
							public double? QuotacontribTotale
							{
								get{ return m_quotacontrib_totale; }
								set{ m_quotacontrib_totale = value; }
							}
							
							#endregion

							#endregion
						}
					}
				