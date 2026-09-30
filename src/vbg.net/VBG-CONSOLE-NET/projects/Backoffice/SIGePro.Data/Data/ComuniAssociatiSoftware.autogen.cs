
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
						/// File generato automaticamente dalla tabella COMUNIASSOCIATISOFTWARE il 10/03/2011 10.43.10
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
						[DataTable("COMUNIASSOCIATISOFTWARE")]
						[Serializable]
						public partial class ComuniAssociatiSoftware : BaseDataClass
						{
							#region Membri privati
							
							private string m_codicecomune = null;

							private int? m_si_stemma = null;

							private string m_si_intestazione1 = null;

							private string m_si_intestazione2 = null;

							private string m_si_intestazione3 = null;

							private string m_si_pdp1 = null;

							private string m_si_pdp2 = null;

							private string m_idcomune = null;

							private string m_software = null;

							private int? m_id = null;

							private string m_mail = null;

							private string m_mailpec = null;
			
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
							
							[DataField("CODICECOMUNE" , Type=DbType.String, CaseSensitive=false, Size=5)]
							public string Codicecomune
							{
								get{ return m_codicecomune; }
								set{ m_codicecomune = value; }
							}
							
							[DataField("SI_STEMMA" , Type=DbType.Decimal)]
							public int? SiStemma
							{
								get{ return m_si_stemma; }
								set{ m_si_stemma = value; }
							}
							
							[DataField("SI_INTESTAZIONE1" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string SiIntestazione1
							{
								get{ return m_si_intestazione1; }
								set{ m_si_intestazione1 = value; }
							}
							
							[DataField("SI_INTESTAZIONE2" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string SiIntestazione2
							{
								get{ return m_si_intestazione2; }
								set{ m_si_intestazione2 = value; }
							}
							
							[DataField("SI_INTESTAZIONE3" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string SiIntestazione3
							{
								get{ return m_si_intestazione3; }
								set{ m_si_intestazione3 = value; }
							}
							
							[DataField("SI_PDP1" , Type=DbType.String, CaseSensitive=false, Size=255)]
							public string SiPdp1
							{
								get{ return m_si_pdp1; }
								set{ m_si_pdp1 = value; }
							}
							
							[DataField("SI_PDP2" , Type=DbType.String, CaseSensitive=false, Size=255)]
							public string SiPdp2
							{
								get{ return m_si_pdp2; }
								set{ m_si_pdp2 = value; }
							}
							
							[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[DataField("MAIL" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Mail
							{
								get{ return m_mail; }
								set{ m_mail = value; }
							}
							
							[DataField("MAILPEC" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Mailpec
							{
								get{ return m_mailpec; }
								set{ m_mailpec = value; }
							}

                            [DataField("CODICE_ACCREDITAMENTO", Type = DbType.String, CaseSensitive = false, Size = 30)]
                            public string CodiceAccreditamento { get; set; }

                            [DataField("CODICE_AOO", Type = DbType.String, CaseSensitive = false, Size = 20)]
                            public string CodiceAoo { get; set; }

							#endregion

							#endregion
						}
					}
				