
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
						/// File generato automaticamente dalla tabella ISTANZEEVENTI il 06/11/2009 9.50.31
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
						[DataTable("ISTANZEEVENTI")]
						[Serializable]
						public partial class IstanzeEventi : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_idevento = null;

							private int? m_codiceistanza = null;

							private string m_fkidcategoriaevento = null;

							private string m_descrizione = null;

							private DateTime? m_data = null;

							private int? m_codiceanagrafe = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("IDEVENTO" , Type=DbType.Decimal)]
							[useSequence]
							public int? Idevento
							{
								get{ return m_idevento; }
								set{ m_idevento = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[isRequired]
[DataField("FKIDCATEGORIAEVENTO" , Type=DbType.String, CaseSensitive=false, Size=16)]
							public string Fkidcategoriaevento
							{
								get{ return m_fkidcategoriaevento; }
								set{ m_fkidcategoriaevento = value; }
							}
							
							[isRequired]
[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[isRequired]
[DataField("DATA" , Type=DbType.DateTime)]
							public DateTime? Data
							{
								get{ return m_data; }
								set{ m_data = value; }
							}
							
							[DataField("CODICEANAGRAFE" , Type=DbType.Decimal)]
							public int? Codiceanagrafe
							{
								get{ return m_codiceanagrafe; }
								set{ m_codiceanagrafe = value; }
							}
							
							#endregion

							#endregion
						}
					}
				