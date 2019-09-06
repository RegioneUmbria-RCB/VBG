
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
						/// File generato automaticamente dalla tabella FO_CONFIGURAZIONEBASE il 14/09/2010 10.15.14
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
						[DataTable("FO_CONFIGURAZIONEBASE")]
						[Serializable]
						public partial class FoConfigurazioneBase : BaseDataClass
						{
							#region Membri privati
							
							private int? m_codice = null;

							private string m_etichetta = null;

							private string m_chiave = null;

							private string m_fk_contesto = null;
			
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
							
							[DataField("ETICHETTA" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Etichetta
							{
								get{ return m_etichetta; }
								set{ m_etichetta = value; }
							}
							
							[DataField("CHIAVE" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Chiave
							{
								get{ return m_chiave; }
								set{ m_chiave = value; }
							}
							
							[DataField("FK_CONTESTO" , Type=DbType.String, CaseSensitive=false, Size=10)]
							public string FkContesto
							{
								get{ return m_fk_contesto; }
								set{ m_fk_contesto = value; }
							}
							
							#endregion

							#endregion
						}
					}
				