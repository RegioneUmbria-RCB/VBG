
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
						/// File generato automaticamente dalla tabella MAIL_CONFIG il 02/12/2010 16.44.45
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
						[DataTable("MAIL_CONFIG")]
						[Serializable]
						public partial class MailConfig : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_loginpass = null;

							private string m_loginname = null;

							private int? m_ssl_acceptinvalidcertificates = null;

							private int? m_port = null;

							private int? m_useauthentication = null;

							private int? m_usessl = null;

							private string m_mailserver = null;

							private string m_senderaddress = null;

							private string m_software = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("LOGINPASS" , Type=DbType.String, CaseSensitive=false, Size=30)]
							public string Loginpass
							{
								get{ return m_loginpass; }
								set{ m_loginpass = value; }
							}
							
							[DataField("LOGINNAME" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Loginname
							{
								get{ return m_loginname; }
								set{ m_loginname = value; }
							}
							
							[DataField("SSL_ACCEPTINVALIDCERTIFICATES" , Type=DbType.Decimal)]
							public int? SslAcceptinvalidcertificates
							{
								get{ return m_ssl_acceptinvalidcertificates; }
								set{ m_ssl_acceptinvalidcertificates = value; }
							}
							
							[DataField("PORT" , Type=DbType.Decimal)]
							public int? Port
							{
								get{ return m_port; }
								set{ m_port = value; }
							}
							
							[DataField("USEAUTHENTICATION" , Type=DbType.Decimal)]
							public int? Useauthentication
							{
								get{ return m_useauthentication; }
								set{ m_useauthentication = value; }
							}
							
							[DataField("USESSL" , Type=DbType.Decimal)]
							public int? Usessl
							{
								get{ return m_usessl; }
								set{ m_usessl = value; }
							}
							
							[DataField("MAILSERVER" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Mailserver
							{
								get{ return m_mailserver; }
								set{ m_mailserver = value; }
							}
							
							[DataField("SENDERADDRESS" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Senderaddress
							{
								get{ return m_senderaddress; }
								set{ m_senderaddress = value; }
							}
							
							#endregion

							#endregion
						}
					}
				