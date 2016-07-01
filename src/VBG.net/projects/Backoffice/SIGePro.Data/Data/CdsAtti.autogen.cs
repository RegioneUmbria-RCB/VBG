
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
						/// File generato automaticamente dalla tabella CDSATTI il 30/07/2008 16.08.34
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
						[DataTable("CDSATTI")]
						[Serializable]
						public partial class CdsAtti : BaseDataClass
						{
							#region Membri privati


                            private int? m_codiceatto = null;

                            private int? m_codiceistanza = null;

                            private DateTime? m_data = null;

							private string m_ora = null;

							private string m_verbale = null;

							private string m_note = null;

                            private DateTime? m_dataconvocazione = null;

							private string m_oraconvocazione = null;

                            private DateTime? m_dataconvocazione2 = null;

							private string m_oraconvocazione2 = null;

							private string m_chiusa = null;

							private string m_fileverbale = null;

                            private int? m_positivia = null;

                            private int? m_codiceoggetto = null;

							private string m_idcomune = null;

                            private int? m_fkidtestata = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICEATTO" , Type=DbType.Decimal)]
							public int? Codiceatto
							{
								get{ return m_codiceatto; }
								set{ m_codiceatto = value; }
							}
							
							[KeyField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FKIDTESTATA" , Type=DbType.Decimal)]
							public int? Fkidtestata
							{
								get{ return m_fkidtestata; }
								set{ m_fkidtestata = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("DATA" , Type=DbType.DateTime)]
							public DateTime? Data
							{
								get{ return m_data; }
                                set { m_data = VerificaDataLocale(value); }
							}
							
							[DataField("ORA" , Type=DbType.String, CaseSensitive=false, Size=12)]
							public string Ora
							{
								get{ return m_ora; }
								set{ m_ora = value; }
							}
							
							[DataField("VERBALE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Verbale
							{
								get{ return m_verbale; }
								set{ m_verbale = value; }
							}
							
							[DataField("NOTE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Note
							{
								get{ return m_note; }
								set{ m_note = value; }
							}
							
							[DataField("DATACONVOCAZIONE" , Type=DbType.DateTime)]
							public DateTime? Dataconvocazione
							{
								get{ return m_dataconvocazione; }
                                set { m_dataconvocazione = VerificaDataLocale(value); }
							}
							
							[DataField("ORACONVOCAZIONE" , Type=DbType.String, CaseSensitive=false, Size=15)]
							public string Oraconvocazione
							{
								get{ return m_oraconvocazione; }
								set{ m_oraconvocazione = value; }
							}
							
							[DataField("DATACONVOCAZIONE2" , Type=DbType.DateTime)]
							public DateTime? Dataconvocazione2
							{
								get{ return m_dataconvocazione2; }
                                set { m_dataconvocazione2 = VerificaDataLocale(value); }
							}
							
							[DataField("ORACONVOCAZIONE2" , Type=DbType.String, CaseSensitive=false, Size=15)]
							public string Oraconvocazione2
							{
								get{ return m_oraconvocazione2; }
								set{ m_oraconvocazione2 = value; }
							}
							
							[DataField("CHIUSA" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string Chiusa
							{
								get{ return m_chiusa; }
								set{ m_chiusa = value; }
							}
							
							[DataField("FILEVERBALE" , Type=DbType.String, CaseSensitive=false, Size=70)]
							public string Fileverbale
							{
								get{ return m_fileverbale; }
								set{ m_fileverbale = value; }
							}
							
							[DataField("POSITIVIA" , Type=DbType.Decimal)]
							public int? Positivia
							{
								get{ return m_positivia; }
								set{ m_positivia = value; }
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
				