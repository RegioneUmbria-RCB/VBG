
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
						/// File generato automaticamente dalla tabella FO_MESSAGGI il 23/11/2009 11.03.00
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
						[DataTable("FO_MESSAGGI")]
						[Serializable]
						public partial class FoMessaggi : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

							private int? m_id = null;

							private int? m_codicedomanda = null;

							private string m_mittente = null;

							private string m_codicefiscaledestinatario = null;

							private string m_oggetto = null;

							private string m_corpo = null;

							private DateTime? m_data = null;

							private int? m_flg_letto = null;

							private string m_codicefiscalemittente = null;
			
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
							
							[DataField("CODICEDOMANDA" , Type=DbType.Decimal)]
							public int? Codicedomanda
							{
								get{ return m_codicedomanda; }
								set{ m_codicedomanda = value; }
							}
							
							[DataField("MITTENTE" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Mittente
							{
								get{ return m_mittente; }
								set{ m_mittente = value; }
							}
							
							[isRequired]
[DataField("CODICEFISCALEDESTINATARIO" , Type=DbType.String, CaseSensitive=false, Size=16)]
							public string Codicefiscaledestinatario
							{
								get{ return m_codicefiscaledestinatario; }
								set{ m_codicefiscaledestinatario = value; }
							}
							
							[isRequired]
[DataField("OGGETTO" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Oggetto
							{
								get{ return m_oggetto; }
								set{ m_oggetto = value; }
							}
							
							[isRequired]
[DataField("CORPO" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Corpo
							{
								get{ return m_corpo; }
								set{ m_corpo = value; }
							}
							
							[isRequired]
[DataField("DATA" , Type=DbType.DateTime)]
							public DateTime? Data
							{
								get{ return m_data; }
								set{ m_data = value; }
							}
							
							[isRequired]
[DataField("FLG_LETTO" , Type=DbType.Decimal)]
							public int? FlgLetto
							{
								get{ return m_flg_letto; }
								set{ m_flg_letto = value; }
							}
							
							[DataField("CODICEFISCALEMITTENTE" , Type=DbType.String, CaseSensitive=false, Size=64)]
							public string Codicefiscalemittente
							{
								get{ return m_codicefiscalemittente; }
								set{ m_codicefiscalemittente = value; }
							}
							
							#endregion

							#endregion
						}
					}
				