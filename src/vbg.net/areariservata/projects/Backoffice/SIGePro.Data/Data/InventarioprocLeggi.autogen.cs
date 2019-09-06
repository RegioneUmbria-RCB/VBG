
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
						/// File generato automaticamente dalla tabella INVENTARIOPROC_LEGGI il 10/01/2011 12.18.08
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
						[DataTable("INVENTARIOPROC_LEGGI")]
						[Serializable]
						public partial class InventarioprocLeggi : BaseDataClass
						{
							#region Membri privati
							
							private int? m_id = null;

							private string m_idcomune = null;

							private int? m_codiceinventario = null;

							private int? m_fkleid = null;

							private string m_riferimenti = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.Decimal)]
[useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("CODICEINVENTARIO" , Type=DbType.Decimal)]
							public int? Codiceinventario
							{
								get{ return m_codiceinventario; }
								set{ m_codiceinventario = value; }
							}
							
							[DataField("FKLEID" , Type=DbType.Decimal)]
							public int? Fkleid
							{
								get{ return m_fkleid; }
								set{ m_fkleid = value; }
							}
							
							[DataField("RIFERIMENTI" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Riferimenti
							{
								get{ return m_riferimenti; }
								set{ m_riferimenti = value; }
							}
							
							#endregion

							#endregion
						}
					}
				