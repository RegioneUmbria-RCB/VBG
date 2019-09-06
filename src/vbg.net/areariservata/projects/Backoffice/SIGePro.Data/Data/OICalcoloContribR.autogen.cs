
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
						/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBR il 28/11/2008 16.42.15
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
						[DataTable("O_ICALCOLOCONTRIBR")]
						[Serializable]
						public partial class OICalcoloContribR : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_fk_oicct_id = null;

                            private int? m_fk_ode_id = null;

                            private int? m_fk_oto_id = null;

                            private double? m_costotot = null;

                            private double? m_costom = null;

                            private double? m_superficie_cubatura = null;

                            private double? m_riduzioneperc = null;

                            private double? m_riduzione = null;

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
                            [DataField("FK_OICCT_ID" , Type=DbType.Decimal)]
							public int? FkOicctId
							{
								get{ return m_fk_oicct_id; }
								set{ m_fk_oicct_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_ODE_ID" , Type=DbType.Decimal)]
							public int? FkOdeId
							{
								get{ return m_fk_ode_id; }
								set{ m_fk_ode_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_OTO_ID" , Type=DbType.Decimal)]
							public int? FkOtoId
							{
								get{ return m_fk_oto_id; }
								set{ m_fk_oto_id = value; }
							}
							
							[DataField("COSTOTOT" , Type=DbType.Decimal)]
							public double? Costotot
							{
								get{ return m_costotot; }
								set{ m_costotot = value; }
							}
							
							[DataField("COSTOM" , Type=DbType.Decimal)]
							public double? Costom
							{
								get{ return m_costom; }
								set{ m_costom = value; }
							}
							
							[DataField("SUPERFICIE_CUBATURA" , Type=DbType.Decimal)]
							public double? SuperficieCubatura
							{
								get{ return m_superficie_cubatura; }
								set{ m_superficie_cubatura = value; }
							}
							
							[DataField("RIDUZIONEPERC" , Type=DbType.Decimal)]
							public double? Riduzioneperc
							{
								get{ return m_riduzioneperc; }
								set{ m_riduzioneperc = value; }
							}
							
							[DataField("RIDUZIONE" , Type=DbType.Decimal)]
							public double? Riduzione
							{
								get{ return m_riduzione; }
								set{ m_riduzione = value; }
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
				