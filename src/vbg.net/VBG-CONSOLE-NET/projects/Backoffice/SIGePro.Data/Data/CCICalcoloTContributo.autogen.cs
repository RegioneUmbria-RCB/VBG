
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
						/// File generato automaticamente dalla tabella CC_ICALCOLO_TCONTRIBUTO il 10/03/2009 11.28.43
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
						[DataTable("CC_ICALCOLO_TCONTRIBUTO")]
						[Serializable]
						public partial class CCICalcoloTContributo : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_id = null;

							private int? m_codiceistanza = null;

							private int? m_fk_ccict_id = null;

							private string m_stato = null;

                            private double? m_costoc_edificio = null;

                            private int? m_fk_ccic_id = null;

                            private double? m_coefficiente = null;

                            private int? m_fk_ccde_id = null;

                            private double? m_riduzioneperc = null;

							private string m_noteriduzione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
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
                            [DataField("FK_CCICT_ID" , Type=DbType.Decimal)]
							public int? FkCcictId
							{
								get{ return m_fk_ccict_id; }
								set{ m_fk_ccict_id = value; }
							}
							
							[isRequired]
                            [DataField("STATO" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string Stato
							{
								get{ return m_stato; }
								set{ m_stato = value; }
							}
							
							[isRequired]
                            [DataField("COSTOC_EDIFICIO" , Type=DbType.Decimal)]
							public double? CostocEdificio
							{
								get{ return m_costoc_edificio; }
								set{ m_costoc_edificio = value; }
							}
							
							[DataField("FK_CCIC_ID" , Type=DbType.Decimal)]
							public int? FkCcicId
							{
								get{ return m_fk_ccic_id; }
								set{ m_fk_ccic_id = value; }
							}
							
							[isRequired]
                            [DataField("COEFFICIENTE" , Type=DbType.Decimal)]
							public double? Coefficiente
							{
								get{ return m_coefficiente; }
								set{ m_coefficiente = value; }
							}
							
							[DataField("FK_CCDE_ID" , Type=DbType.Decimal)]
							public int? FkCcdeId
							{
								get{ return m_fk_ccde_id; }
								set{ m_fk_ccde_id = value; }
							}
							
							[DataField("RIDUZIONEPERC" , Type=DbType.Decimal)]
							public double? Riduzioneperc
							{
								get{ return m_riduzioneperc; }
								set{ m_riduzioneperc = value; }
							}
							
							[DataField("NOTERIDUZIONE" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Noteriduzione
							{
								get{ return m_noteriduzione; }
								set{ m_noteriduzione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				