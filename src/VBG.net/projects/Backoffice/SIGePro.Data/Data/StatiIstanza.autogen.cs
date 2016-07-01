
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
						/// File generato automaticamente dalla tabella STATIISTANZA il 31/10/2008 14.59.11
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
						[DataTable("STATIISTANZA")]
						[Serializable]
						public partial class StatiIstanza : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

							private string m_codicestato = null;

							private string m_stato = null;

                            private int? m_modificaistanza = null;

                            private int? m_fkcodcomportamento = null;

                            private int? m_ordine = null;
			
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
							
							[KeyField("CODICESTATO" , Type=DbType.String, Size=2)]
							public string Codicestato
							{
								get{ return m_codicestato; }
								set{ m_codicestato = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("STATO" , Type=DbType.String, CaseSensitive=false, Size=30)]
							public string Stato
							{
								get{ return m_stato; }
								set{ m_stato = value; }
							}
							
							[DataField("MODIFICAISTANZA" , Type=DbType.Decimal)]
							public int? Modificaistanza
							{
								get{ return m_modificaistanza; }
								set{ m_modificaistanza = value; }
							}
							
							[DataField("FKCODCOMPORTAMENTO" , Type=DbType.Decimal)]
							public int? Fkcodcomportamento
							{
								get{ return m_fkcodcomportamento; }
								set{ m_fkcodcomportamento = value; }
							}
							
							[DataField("ORDINE" , Type=DbType.Decimal)]
							public int? Ordine
							{
								get{ return m_ordine; }
								set{ m_ordine = value; }
							}
							
							#endregion

							#endregion
						}
					}
				