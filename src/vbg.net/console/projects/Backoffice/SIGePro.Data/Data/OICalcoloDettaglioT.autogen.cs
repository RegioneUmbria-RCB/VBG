
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
						/// File generato automaticamente dalla tabella O_ICALCOLO_DETTAGLIOT il 27/06/2008 13.01.36
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
						[DataTable("O_ICALCOLO_DETTAGLIOT")]
						[Serializable]
						public partial class OICalcoloDettaglioT : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_fk_oic_id = null;

                            private int? m_ordine = null;

							private string m_fk_occbde_id = null;

                            private int? m_fk_ode_id = null;

							private string m_descrizione = null;

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
                            [DataField("FK_OIC_ID" , Type=DbType.Decimal)]
							public int? FkOicId
							{
								get{ return m_fk_oic_id; }
								set{ m_fk_oic_id = value; }
							}
							
							[isRequired]
                            [DataField("ORDINE" , Type=DbType.Decimal)]
							public int? Ordine
							{
								get{ return m_ordine; }
								set{ m_ordine = value; }
							}
							
							[isRequired]
                            [DataField("FK_OCCBDE_ID" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string FkOccbdeId
							{
								get{ return m_fk_occbde_id; }
								set{ m_fk_occbde_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_ODE_ID" , Type=DbType.Decimal)]
							public int? FkOdeId
							{
								get{ return m_fk_ode_id; }
								set{ m_fk_ode_id = value; }
							}
							
                            [DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
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
				