
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
						/// File generato automaticamente dalla tabella PROT_MODALITAPROTOCOLLO il 19/01/2009 10.47.52
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
						[DataTable("PROT_MODALITAPROTOCOLLO")]
						[Serializable]
						public partial class ProtModalitaProtcollo : BaseDataClass
						{
							#region Membri privati
							
							private int? m_mp_id = null;

							private string m_mp_descrizione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("MP_ID" , Type=DbType.Decimal)]
							public int? Mp_Id
							{
								get{ return m_mp_id; }
								set{ m_mp_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("MP_DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Mp_Descrizione
							{
								get{ return m_mp_descrizione; }
								set{ m_mp_descrizione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				