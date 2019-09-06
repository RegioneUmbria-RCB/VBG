
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
						/// File generato automaticamente dalla tabella ATECO il 06/12/2010 12.13.36
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
						[DataTable("ATECO")]
						[Serializable]
						public partial class Ateco : BaseDataClass
						{
							#region Membri privati
							
							private string m_codice = null;

							private string m_titolo = null;

							private string m_descrizione = null;

							private int? m_id = null;

							private int? m_fkidpadre = null;

							private string m_codicebreve = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.Decimal)]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("CODICE" , Type=DbType.String, CaseSensitive=false, Size=25)]
							public string Codice
							{
								get{ return m_codice; }
								set{ m_codice = value; }
							}
							
							[isRequired]
[DataField("TITOLO" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Titolo
							{
								get{ return m_titolo; }
								set{ m_titolo = value; }
							}
							
							[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=2147483647)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[DataField("FKIDPADRE" , Type=DbType.Decimal)]
							public int? Fkidpadre
							{
								get{ return m_fkidpadre; }
								set{ m_fkidpadre = value; }
							}
							
							[DataField("CODICEBREVE" , Type=DbType.String, CaseSensitive=false, Size=20)]
							public string Codicebreve
							{
								get{ return m_codicebreve; }
								set{ m_codicebreve = value; }
							}
							
							#endregion

							#endregion
						}
					}
				