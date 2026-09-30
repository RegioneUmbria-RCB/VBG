
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
						/// File generato automaticamente dalla tabella TIPIENDO il 13/01/2009 14.43.43
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
						[DataTable("TIPIENDO")]
						[Serializable]
						public partial class TipiEndo : BaseDataClass
						{
							#region Membri privati

                            private int? m_codice = null;

							private string m_tipo = null;

							private string m_note = null;

                            private int? m_ordine = null;

							private string m_software = null;

							private string m_idcomune = null;

                            private int? m_codicefamigliaendo = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICE" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Codice
							{
								get{ return m_codice; }
								set{ m_codice = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("TIPO" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Tipo
							{
								get{ return m_tipo; }
								set{ m_tipo = value; }
							}
							
							[DataField("NOTE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
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
							
							[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[DataField("CODICEFAMIGLIAENDO" , Type=DbType.Decimal)]
							public int? Codicefamigliaendo
							{
								get{ return m_codicefamigliaendo; }
								set{ m_codicefamigliaendo = value; }
							}
							
							#endregion

							#endregion
						}
					}
				