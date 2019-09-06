
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
						/// File generato automaticamente dalla tabella CC_ICALCOLI il 27/06/2008 13.01.37
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
						[DataTable("CC_ICALCOLI")]
						[Serializable]
						public partial class CCICalcoli : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private double? m_su = null;

                            private double? m_snr = null;

							private double? m_sc = null;

                            private double? m_st = null;

							private double? m_sa = null;

                            private double? m_su_art9 = null;

                            private double? m_i1 = null;

                            private double? m_i2 = null;

                            private double? m_i3 = null;

                            private int? m_fk_cctce_id = null;

                            private double? m_maggiorazione = null;

                            private double? m_costocmq = null;

                            private double? m_costocmq_maggiorato = null;
			
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
                            [DataField("SU" , Type=DbType.Decimal)]
							public double? Su
							{
								get{ return m_su; }
								set{ m_su = value; }
							}
							
							[isRequired]
                            [DataField("SNR" , Type=DbType.Decimal)]
							public double? Snr
							{
								get{ return m_snr; }
								set{ m_snr = value; }
							}
							
							[isRequired]
                            [DataField("SC" , Type=DbType.Decimal)]
							public double? Sc
							{
								get{ return m_sc; }
								set{ m_sc = value; }
							}
							
							[isRequired]
                            [DataField("ST" , Type=DbType.Decimal)]
							public double? St
							{
								get{ return m_st; }
								set{ m_st = value; }
							}
							
							[isRequired]
                            [DataField("SA" , Type=DbType.Decimal)]
							public double? Sa
							{
								get{ return m_sa; }
								set{ m_sa = value; }
							}
							
							[isRequired]
                            [DataField("SU_ART9" , Type=DbType.Decimal)]
							public double? SuArt9
							{
								get{ return m_su_art9; }
								set{ m_su_art9 = value; }
							}
							
							[isRequired]
                            [DataField("I1" , Type=DbType.Decimal)]
							public double? I1
							{
								get{ return m_i1; }
								set{ m_i1 = value; }
							}
							
							[isRequired]
                            [DataField("I2" , Type=DbType.Decimal)]
							public double? I2
							{
								get{ return m_i2; }
								set{ m_i2 = value; }
							}
							
							[isRequired]
                            [DataField("I3" , Type=DbType.Decimal)]
							public double? I3
							{
								get{ return m_i3; }
								set{ m_i3 = value; }
							}
							
                            [DataField("FK_CCTCE_ID" , Type=DbType.Decimal)]
							public int? FkCctceId
							{
								get{ return m_fk_cctce_id; }
								set{ m_fk_cctce_id = value; }
							}
							
							[isRequired]
                            [DataField("MAGGIORAZIONE" , Type=DbType.Decimal)]
							public double? Maggiorazione
							{
								get{ return m_maggiorazione; }
								set{ m_maggiorazione = value; }
							}
							
							[isRequired]
                            [DataField("COSTOCMQ" , Type=DbType.Decimal)]
							public double? Costocmq
							{
								get{ return m_costocmq; }
								set{ m_costocmq = value; }
							}
							
							[isRequired]
                            [DataField("COSTOCMQ_MAGGIORATO" , Type=DbType.Decimal)]
							public double? CostocmqMaggiorato
							{
								get{ return m_costocmq_maggiorato; }
								set{ m_costocmq_maggiorato = value; }
							}
							
							#endregion

							#endregion
						}
					}
				