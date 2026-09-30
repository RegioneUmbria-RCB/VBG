
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
						/// File generato automaticamente dalla tabella PROTOCOLLO_CONFIGURAZIONE il 11/12/2008 11.38.33
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
						[DataTable("PROTOCOLLO_CONFIGURAZIONE")]
						[Serializable]
						public partial class ProtocolloConfigurazione : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

                            private int? m_codtestoistanze = null;

                            private int? m_codtestomovimenti = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("CODTESTOISTANZE" , Type=DbType.Decimal)]
							public int? Codtestoistanze
							{
								get{ return m_codtestoistanze; }
								set{ m_codtestoistanze = value; }
							}
							
							[DataField("CODTESTOMOVIMENTI" , Type=DbType.Decimal)]
							public int? Codtestomovimenti
							{
								get{ return m_codtestomovimenti; }
								set{ m_codtestomovimenti = value; }
							}
							
							#endregion

							#endregion
						}
					}
				