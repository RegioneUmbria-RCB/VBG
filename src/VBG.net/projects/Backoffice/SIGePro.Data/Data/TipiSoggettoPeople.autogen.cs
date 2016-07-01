
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
						/// File generato automaticamente dalla tabella TIPISOGGETTOPEOPLE il 10/07/2008 11.15.56
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
						[DataTable("TIPISOGGETTOPEOPLE")]
						[Serializable]
						public partial class TipiSoggettoPeople : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

							private string m_software = null;

							private string m_tiporapprpeople = null;

                            private int? m_codicetiposoggetto = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, CaseSensitive=true, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, CaseSensitive=true, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[KeyField("TIPORAPPRPEOPLE" , Type=DbType.String, CaseSensitive=true, Size=15)]
							public string Tiporapprpeople
							{
								get{ return m_tiporapprpeople; }
								set{ m_tiporapprpeople = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
                            [DataField("CODICETIPOSOGGETTO" , Type=DbType.Decimal)]
							public int? Codicetiposoggetto
							{
								get{ return m_codicetiposoggetto; }
								set{ m_codicetiposoggetto = value; }
							}
							
							#endregion

							#endregion
						}
					}
				