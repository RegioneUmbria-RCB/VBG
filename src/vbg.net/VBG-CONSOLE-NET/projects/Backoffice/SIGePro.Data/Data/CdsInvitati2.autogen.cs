
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
						/// File generato automaticamente dalla tabella CDSINVITATI2 il 30/07/2008 16.22.49
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
						[DataTable("CDSINVITATI2")]
						[Serializable]
						public partial class CdsInvitati2 : BaseDataClass
						{
							#region Membri privati


                            private int? m_codiceinvitato = null;

                            private int? m_codiceistanza = null;

                            private int? m_codiceatto = null;

                            private int? m_codiceanagrafe = null;

							private string m_note = null;

							private string m_idcomune = null;

							private int? m_fkidtestata = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICEINVITATO" , Type=DbType.Decimal)]
							public int? Codiceinvitato
							{
								get{ return m_codiceinvitato; }
								set{ m_codiceinvitato = value; }
							}
							
							[KeyField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FKIDTESTATA" , Type=DbType.Decimal)]
							public int? Fkidtestata
							{
								get{ return m_fkidtestata; }
								set{ m_fkidtestata = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("CODICEATTO" , Type=DbType.Decimal)]
							public int? Codiceatto
							{
								get{ return m_codiceatto; }
								set{ m_codiceatto = value; }
							}
							
							[DataField("CODICEANAGRAFE" , Type=DbType.Decimal)]
							public int? Codiceanagrafe
							{
								get{ return m_codiceanagrafe; }
								set{ m_codiceanagrafe = value; }
							}
							
							[DataField("NOTE" , Type=DbType.String, CaseSensitive=false, Size=255)]
							public string Note
							{
								get{ return m_note; }
								set{ m_note = value; }
							}
							
							#endregion

							#endregion
						}
					}
				