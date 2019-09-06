
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
						/// File generato automaticamente dalla tabella ISTANZEFRONTOFFICE il 30/01/2009 16.15.19
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
						[DataTable("ISTANZEFRONTOFFICE")]
						[Serializable]
						public partial class IstanzeFrontOffice : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_id = null;

							private string m_software = null;

							private byte[] m_xmldomanda = null;

							private string m_richiedente = null;

							private string m_codicedomanda = null;

                            private int? m_codiceistanza = null;

							private byte[] m_errori = null;

                            private DateTime? m_datapresentazione = null;
			
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
[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[isRequired]
[DataField("XMLDOMANDA" , Type=DbType.Binary)]
							public byte[] Xmldomanda
							{
								get{ return m_xmldomanda; }
								set{ m_xmldomanda = value; }
							}
							
							[isRequired]
[DataField("RICHIEDENTE" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Richiedente
							{
								get{ return m_richiedente; }
								set{ m_richiedente = value; }
							}
							
							[isRequired]
[DataField("CODICEDOMANDA" , Type=DbType.String, CaseSensitive=false, Size=35)]
							public string Codicedomanda
							{
								get{ return m_codicedomanda; }
								set{ m_codicedomanda = value; }
							}
							
							[DataField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[DataField("ERRORI" , Type=DbType.Binary)]
							public byte[] Errori
							{
								get{ return m_errori; }
								set{ m_errori = value; }
							}
							
							[DataField("DATAPRESENTAZIONE" , Type=DbType.DateTime)]
							public DateTime? Datapresentazione
							{
								get{ return m_datapresentazione; }
                                set { m_datapresentazione = VerificaDataLocale(value); }
							}
							
							#endregion

							#endregion
						}
					}
				