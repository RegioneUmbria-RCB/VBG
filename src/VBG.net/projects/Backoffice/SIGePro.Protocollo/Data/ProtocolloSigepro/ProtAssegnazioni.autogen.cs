
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
						/// File generato automaticamente dalla tabella PROT_ASSEGNAZIONI il 17/03/2009 14.24.21
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
						[DataTable("PROT_ASSEGNAZIONI")]
						[Serializable]
						public partial class ProtAssegnazioni : BaseDataClass
						{
							#region Membri privati
							
							private int? m_as_id = null;

							private int? m_as_fkidanagrafe = null;

							private int? m_as_fkidprotocollo = null;

							private string m_as_dataassegnazione = null;

							private string m_as_oraassegnazione = null;

							private string m_as_utenteassegnazione = null;

							private string m_idcomune = null;

							private int? m_as_fkidtipoassegnazione = null;

							private string m_as_note = null;

							private string m_as_log = null;

							private int? m_as_fkidclassificazione = null;

							private int? m_as_fk_clidassegnazione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("AS_ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? As_Id
							{
								get{ return m_as_id; }
								set{ m_as_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("AS_FKIDANAGRAFE" , Type=DbType.Decimal)]
							public int? As_Fkidanagrafe
							{
								get{ return m_as_fkidanagrafe; }
								set{ m_as_fkidanagrafe = value; }
							}
							
							[isRequired]
                            [DataField("AS_FKIDPROTOCOLLO" , Type=DbType.Decimal)]
							public int? As_Fkidprotocollo
							{
								get{ return m_as_fkidprotocollo; }
								set{ m_as_fkidprotocollo = value; }
							}
							
							[DataField("AS_DATAASSEGNAZIONE" , Type=DbType.String, CaseSensitive=false, Size=8)]
							public string As_Dataassegnazione
							{
								get{ return m_as_dataassegnazione; }
								set{ m_as_dataassegnazione = value; }
							}
							
							[DataField("AS_ORAASSEGNAZIONE" , Type=DbType.String, CaseSensitive=false, Size=4)]
							public string As_Oraassegnazione
							{
								get{ return m_as_oraassegnazione; }
								set{ m_as_oraassegnazione = value; }
							}
							
							[DataField("AS_UTENTEASSEGNAZIONE" , Type=DbType.String, CaseSensitive=false, Size=60)]
							public string As_Utenteassegnazione
							{
								get{ return m_as_utenteassegnazione; }
								set{ m_as_utenteassegnazione = value; }
							}
							
							[DataField("AS_FKIDTIPOASSEGNAZIONE" , Type=DbType.Decimal)]
							public int? As_Fkidtipoassegnazione
							{
								get{ return m_as_fkidtipoassegnazione; }
								set{ m_as_fkidtipoassegnazione = value; }
							}
							
							[DataField("AS_NOTE" , Type=DbType.String, CaseSensitive=false, Size=2000)]
							public string As_Note
							{
								get{ return m_as_note; }
								set{ m_as_note = value; }
							}
							
							[DataField("AS_LOG" , Type=DbType.String, CaseSensitive=false, Size=25)]
							public string As_Log
							{
								get{ return m_as_log; }
								set{ m_as_log = value; }
							}
							
							[DataField("AS_FKIDCLASSIFICAZIONE" , Type=DbType.Decimal)]
							public int? As_Fkidclassificazione
							{
								get{ return m_as_fkidclassificazione; }
								set{ m_as_fkidclassificazione = value; }
							}
							
							[DataField("AS_FK_CLIDASSEGNAZIONE" , Type=DbType.Decimal)]
							public int? As_FkClidassegnazione
							{
								get{ return m_as_fk_clidassegnazione; }
								set{ m_as_fk_clidassegnazione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				