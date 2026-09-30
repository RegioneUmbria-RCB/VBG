
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
						/// File generato automaticamente dalla tabella STP_ENDO_TIPO1 il 06/12/2010 10.11.08
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
						[DataTable("STP_ENDO_TIPO1")]
						[Serializable]
						public partial class StpEndoTipo1 : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_id = null;

							private int? m_codiceinventario = null;

							private int? m_codice_stp = null;

							private int? m_codiceoggetto = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("ID" , Type=DbType.Decimal)]
[useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("CODICEINVENTARIO" , Type=DbType.Decimal)]
							public int? Codiceinventario
							{
								get{ return m_codiceinventario; }
								set{ m_codiceinventario = value; }
							}
							
							[DataField("CODICE_STP" , Type=DbType.Decimal)]
							public int? CodiceStp
							{
								get{ return m_codice_stp; }
								set{ m_codice_stp = value; }
							}
							
							[DataField("CODICEOGGETTO" , Type=DbType.Decimal)]
							public int? Codiceoggetto
							{
								get{ return m_codiceoggetto; }
								set{ m_codiceoggetto = value; }
							}
							
							#endregion

							#endregion
						}
					}
				