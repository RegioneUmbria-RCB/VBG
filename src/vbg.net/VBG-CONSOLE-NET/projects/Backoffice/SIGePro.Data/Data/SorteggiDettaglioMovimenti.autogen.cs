
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
						/// File generato automaticamente dalla tabella SORTEGGIDETTAGLIOMOVIMENTI il 27/01/2009 8.44.05
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
						[DataTable("SORTEGGIDETTAGLIOMOVIMENTI")]
						[Serializable]
						public partial class SorteggiDettaglioMovimenti : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_sdm_fk_sdid = null;

                            private int? m_codicemovimento = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SDM_FK_SDID" , Type=DbType.Decimal)]
							public int? SdmFkSdid
							{
								get{ return m_sdm_fk_sdid; }
								set{ m_sdm_fk_sdid = value; }
							}
							
							[KeyField("CODICEMOVIMENTO" , Type=DbType.Decimal)]
							public int? Codicemovimento
							{
								get{ return m_codicemovimento; }
								set{ m_codicemovimento = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							#endregion

							#endregion
						}
					}
				