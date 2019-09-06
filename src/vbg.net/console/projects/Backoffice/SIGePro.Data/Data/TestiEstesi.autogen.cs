
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
						/// File generato automaticamente dalla tabella TESTIESTESI il 30/11/2010 16.36.16
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
						[DataTable("TESTIESTESI")]
						[Serializable]
						public partial class TestiEstesi : BaseDataClass
						{
							#region Membri privati
							
							private int? m_codiceinventario = null;

							private int? m_numeronorma = null;

							private string m_normativa = null;

							private int? m_tiponorma = null;

							private string m_nomefile = null;

							private string m_indirizzoweb = null;

							private int? m_codiceoggetto = null;

							private string m_idcomune = null;

							private int? m_id = null;
			
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
							
							[isRequired]
[DataField("CODICEINVENTARIO" , Type=DbType.Decimal)]
							public int? Codiceinventario
							{
								get{ return m_codiceinventario; }
								set{ m_codiceinventario = value; }
							}
							
							[DataField("NUMERONORMA" , Type=DbType.Decimal)]
							public int? Numeronorma
							{
								get{ return m_numeronorma; }
								set{ m_numeronorma = value; }
							}
							
							[DataField("NORMATIVA" , Type=DbType.String, CaseSensitive=false, Size=512)]
							public string Normativa
							{
								get{ return m_normativa; }
								set{ m_normativa = value; }
							}
							
							[DataField("TIPONORMA" , Type=DbType.Decimal)]
							public int? Tiponorma
							{
								get{ return m_tiponorma; }
								set{ m_tiponorma = value; }
							}
							
							[DataField("NOMEFILE" , Type=DbType.String, CaseSensitive=false, Size=255)]
							public string Nomefile
							{
								get{ return m_nomefile; }
								set{ m_nomefile = value; }
							}
							
							[DataField("INDIRIZZOWEB" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Indirizzoweb
							{
								get{ return m_indirizzoweb; }
								set{ m_indirizzoweb = value; }
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
				