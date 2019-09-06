
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
						/// File generato automaticamente dalla tabella DOMANDEFRONTALBERO il 09/01/2009 16.49.08
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
						[DataTable("DOMANDEFRONTALBERO")]
						[Serializable]
						public partial class DomandeFrontAlbero : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

                            private int? m_id = null;

							private string m_descrizione = null;

							private string m_note = null;

                            private int? m_ordine = null;

                            private int? m_idpadre = null;

                            private int? m_disattiva = null;
			
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
[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=true, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[isRequired]
[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=80)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[DataField("NOTE" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Note
							{
								get{ return m_note; }
								set{ m_note = value; }
							}
							
							[DataField("ORDINE" , Type=DbType.Decimal)]
							public int? Ordine
							{
								get{ return m_ordine; }
								set{ m_ordine = value; }
							}
							
							[DataField("IDPADRE" , Type=DbType.Decimal)]
							public int? Idpadre
							{
								get{ return m_idpadre; }
								set{ m_idpadre = value; }
							}
							
							[DataField("DISATTIVA" , Type=DbType.Decimal)]
							public int? Disattiva
							{
								get{ return m_disattiva; }
								set{ m_disattiva = value; }
							}
							
							#endregion

							#endregion
						}
					}
				