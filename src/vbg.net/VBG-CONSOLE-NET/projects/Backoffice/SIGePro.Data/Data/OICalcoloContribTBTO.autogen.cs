
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
						/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT_BTO il 08/07/2008 10.12.49
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
						[DataTable("O_ICALCOLOCONTRIBT_BTO")]
						[Serializable]
						public partial class OICalcoloContribTBTO : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

                            private int? m_fk_oicct_id = null;

							private string m_fk_bto_id = null;

                            private double? m_costotot = null;
			
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
                            [DataField("FK_OICCT_ID" , Type=DbType.Decimal)]
							public int? FkOicctId
							{
								get{ return m_fk_oicct_id; }
								set{ m_fk_oicct_id = value; }
							}
							
							[isRequired]
                            [DataField("FK_BTO_ID" , Type=DbType.String, CaseSensitive=false, Size=3)]
							public string FkBtoId
							{
								get{ return m_fk_bto_id; }
								set{ m_fk_bto_id = value; }
							}
							
							[DataField("COSTOTOT" , Type=DbType.Decimal)]
							public double? Costotot
							{
								get{ return m_costotot; }
								set{ m_costotot = value; }
							}
							
							#endregion

							#endregion
						}
					}
				