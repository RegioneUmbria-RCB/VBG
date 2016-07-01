
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
						/// File generato automaticamente dalla tabella CC_CAUSALIRIDUZIONIR il 06/03/2009 12.28.44
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
						[DataTable("CC_CAUSALIRIDUZIONIR")]
						[Serializable]
						public partial class CcCausaliRiduzioniR : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_fk_cccrt_id = null;

							private string m_descrizione = null;

                            private double? m_riduzioneperc = null;
			
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
                            [DataField("FK_CCCRT_ID" , Type=DbType.Decimal)]
							public int? FkCccrtId
							{
								get{ return m_fk_cccrt_id; }
								set{ m_fk_cccrt_id = value; }
							}
							
							[isRequired]
                            [DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[DataField("RIDUZIONEPERC" , Type=DbType.Decimal)]
							public double? Riduzioneperc
							{
								get{ return m_riduzioneperc; }
								set{ m_riduzioneperc = value; }
							}
							
							#endregion

							#endregion
						}
					}
				