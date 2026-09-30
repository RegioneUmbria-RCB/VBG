
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
						/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBR_RIDUZ il 17/11/2008 17.30.24
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
						[DataTable("O_ICALCOLOCONTRIBR_RIDUZ")]
						[Serializable]
						public partial class OICalcoloContribRRiduz : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_fk_oiccr_id = null;

                            private int? m_fk_ocrr_id = null;

                            private double? m_riduzioneperc = null;

							private string m_note = null;
			
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
                            [DataField("FK_OICCR_ID" , Type=DbType.Decimal)]
							public int? FkOiccrId
							{
								get{ return m_fk_oiccr_id; }
								set{ m_fk_oiccr_id = value; }
							}
							
							[DataField("FK_OCRR_ID" , Type=DbType.Decimal)]
							public int? FkOcrrId
							{
								get{ return m_fk_ocrr_id; }
								set{ m_fk_ocrr_id = value; }
							}
							
							[DataField("RIDUZIONEPERC" , Type=DbType.Decimal)]
							public double? Riduzioneperc
							{
								get{ return m_riduzioneperc; }
								set{ m_riduzioneperc = value; }
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
				