
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
						/// File generato automaticamente dalla tabella CITTADINANZA il 15/09/2010 12.14.35
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
						[DataTable("CITTADINANZA")]
						[Serializable]
						public partial class Cittadinanza : BaseDataClass
						{
							#region Membri privati
							
							private int? m_codice = null;

							private string m_cittadinanza = null;

							private string m_cf = null;

							private int? m_disabilitato = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICE" , Type=DbType.Decimal)]
							public int? Codice
							{
								get{ return m_codice; }
								set{ m_codice = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("CITTADINANZA" , Type=DbType.String, CaseSensitive=false, Size=128)]
							public string Descrizione
							{
								get{ return m_cittadinanza; }
								set{ m_cittadinanza = value; }
							}
							
							[DataField("CF" , Type=DbType.String, CaseSensitive=false, Size=5)]
							public string Cf
							{
								get{ return m_cf; }
								set{ m_cf = value; }
							}
							
							[DataField("DISABILITATO" , Type=DbType.Decimal)]
							public int? Disabilitato
							{
								get{ return m_disabilitato; }
								set{ m_disabilitato = value; }
							}

							[DataField("FLG_PAESE_COMUNITARIO", Type = DbType.Decimal)]
							public int? FlgPaeseComunitario
							{
								get;
								set;
							}

							

							#endregion

							#endregion
						}
					}
				