
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
						/// File generato automaticamente dalla tabella FO_CONFIGURAZIONE il 14/09/2010 10.14.52
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
						[DataTable("FO_CONFIGURAZIONE")]
						[Serializable]
						public partial class FoConfigurazione : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_codice = null;

							private string m_software = null;

							private int? m_fk_idconfigurazionebase = null;

							private string m_valore = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("CODICE" , Type=DbType.Decimal)]
[useSequence]
							public int? Codice
							{
								get{ return m_codice; }
								set{ m_codice = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[DataField("FK_IDCONFIGURAZIONEBASE" , Type=DbType.Decimal)]
							public int? FkIdconfigurazionebase
							{
								get{ return m_fk_idconfigurazionebase; }
								set{ m_fk_idconfigurazionebase = value; }
							}
							
							[DataField("VALORE" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Valore
							{
								get{ return m_valore; }
								set{ m_valore = value; }
							}
							
							#endregion

							#endregion
						}
					}
				