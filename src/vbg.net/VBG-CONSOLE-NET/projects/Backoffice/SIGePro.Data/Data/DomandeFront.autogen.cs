
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
						/// File generato automaticamente dalla tabella DOMANDEFRONT il 09/01/2009 16.48.35
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
						[DataTable("DOMANDEFRONT")]
						[Serializable]
						public partial class DomandeFront : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_codicedomanda = null;

							private string m_domanda = null;

							private string m_software = null;

                            private int? m_fk_dfa_id = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("CODICEDOMANDA" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Codicedomanda
							{
								get{ return m_codicedomanda; }
								set{ m_codicedomanda = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("DOMANDA" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Domanda
							{
								get{ return m_domanda; }
								set{ m_domanda = value; }
							}
							
							[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[DataField("FK_DFA_ID" , Type=DbType.Decimal)]
							public int? FkDfaId
							{
								get{ return m_fk_dfa_id; }
								set{ m_fk_dfa_id = value; }
							}
							
							#endregion

							#endregion
						}
					}
				