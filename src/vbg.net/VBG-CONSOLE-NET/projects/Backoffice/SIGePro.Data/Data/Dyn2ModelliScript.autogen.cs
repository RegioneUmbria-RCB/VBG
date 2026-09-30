
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
						/// File generato automaticamente dalla tabella DYN2_MODELLI_SCRIPT il 22/12/2008 12.26.33
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
						[DataTable("DYN2_MODELLI_SCRIPT")]
						[Serializable]
						public partial class Dyn2ModelliScript : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_fk_d2mt_id = null;

							private string m_evento = null;

							private byte[] m_script = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FK_D2MT_ID" , Type=DbType.Decimal)]
							public int? FkD2mtId
							{
								get{ return m_fk_d2mt_id; }
								set{ m_fk_d2mt_id = value; }
							}
							
							[KeyField("EVENTO" , Type=DbType.String, Size=15)]
							public string Evento
							{
								get{ return m_evento; }
								set{ m_evento = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("SCRIPT" , Type=DbType.Binary)]
							public byte[] Script
							{
								get{ return m_script; }
								set{ m_script = value; }
							}
							
							#endregion

							#endregion
						}
					}
				