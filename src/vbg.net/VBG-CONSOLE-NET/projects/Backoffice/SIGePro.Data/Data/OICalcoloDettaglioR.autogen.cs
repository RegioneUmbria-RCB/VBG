
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
						/// File generato automaticamente dalla tabella O_ICALCOLO_DETTAGLIOR il 27/06/2008 13.01.36
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
						[DataTable("O_ICALCOLO_DETTAGLIOR")]
						[Serializable]
						public partial class OICalcoloDettaglioR : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_codiceistanza = null;

                            private int? m_id = null;

                            private int? m_fk_oicdt_id = null;

                            private double? m_qta = null;

                            private double? m_lung = null;

                            private double? m_larg = null;

                            private double? m_alt = null;

                            private double? m_totale = null;
			
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
                            [DataField("FK_OICDT_ID" , Type=DbType.Decimal)]
							public int? FkOicdtId
							{
								get{ return m_fk_oicdt_id; }
								set{ m_fk_oicdt_id = value; }
							}
							
							[isRequired]
                            [DataField("QTA" , Type=DbType.Decimal)]
							public double? Qta
							{
								get{ return m_qta; }
								set{ m_qta = value; }
							}
							
							[isRequired]
                            [DataField("LUNG" , Type=DbType.Decimal)]
							public double? Lung
							{
								get{ return m_lung; }
								set{ m_lung = value; }
							}
							
							[isRequired]
                            [DataField("LARG" , Type=DbType.Decimal)]
							public double? Larg
							{
								get{ return m_larg; }
								set{ m_larg = value; }
							}
							
							[DataField("ALT" , Type=DbType.Decimal)]
							public double? Alt
							{
								get{ return m_alt; }
								set{ m_alt = value; }
							}
							
							[isRequired]
                            [DataField("TOTALE" , Type=DbType.Decimal)]
							public double? Totale
							{
								get{ return m_totale; }
								set{ m_totale = value; }
							}
							
							#endregion

							#endregion
						}
					}
				