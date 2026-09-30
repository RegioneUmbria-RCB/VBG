
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
						/// File generato automaticamente dalla tabella MESSAGGICFGBASE il 23/11/2009 11.30.29
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
						[DataTable("MESSAGGICFGBASE")]
						[Serializable]
						public partial class MessaggiCfgBase : BaseDataClass
						{
							#region Membri privati
							
							private string m_contesto = null;

							private string m_descrizione = null;

							private string m_oggetto = null;

							private string m_corpo = null;

							private int? m_flg_invio = null;

							private int? m_flg_tipoinvio = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CONTESTO" , Type=DbType.String, Size=40)]
							public string Contesto
							{
								get{ return m_contesto; }
								set{ m_contesto = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
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
				