
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
						/// File generato automaticamente dalla tabella MESSAGGICFG il 23/11/2009 11.29.44
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
						[DataTable("MESSAGGICFG")]
						[Serializable]
						public partial class MessaggiCfg : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

							private string m_contesto = null;

							private int? m_id = null;

							private string m_oggetto = null;

							private string m_corpo = null;

							private int? m_flg_invio = null;

							private int? m_flg_tipoinvio = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=24)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=8)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[KeyField("CONTESTO" , Type=DbType.String, Size=40)]
							public string Contesto
							{
								get{ return m_contesto; }
								set{ m_contesto = value; }
							}

							[KeyField("ID", Type = DbType.Decimal)]
							[useSequence]
							public int? Id
							{
								get { return m_id; }
								set { m_id = value; }
							}
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("OGGETTO" , Type=DbType.String, CaseSensitive=false, Size=800)]
							public string Oggetto
							{
								get{ return m_oggetto; }
								set{ m_oggetto = value; }
							}
							
							[isRequired]
[DataField("CORPO" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Corpo
							{
								get{ return m_corpo; }
								set{ m_corpo = value; }
							}
							
							[isRequired]
[DataField("FLG_INVIO" , Type=DbType.Decimal)]
							public int? FlgInvio
							{
								get{ return m_flg_invio; }
								set{ m_flg_invio = value; }
							}
							
							[isRequired]
[DataField("FLG_TIPOINVIO" , Type=DbType.Decimal)]
							public int? FlgTipoinvio
							{
								get{ return m_flg_tipoinvio; }
								set{ m_flg_tipoinvio = value; }
							}
							
							#endregion

							#endregion
						}
					}
				