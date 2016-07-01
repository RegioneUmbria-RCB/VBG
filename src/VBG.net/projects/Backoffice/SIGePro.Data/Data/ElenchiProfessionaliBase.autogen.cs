
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
						/// File generato automaticamente dalla tabella ELENCHIPROFESSIONALIBASE il 15/09/2010 12.28.48
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
						[DataTable("ELENCHIPROFESSIONALIBASE")]
						[Serializable]
						public partial class ElenchiProfessionaliBase : BaseDataClass
						{
							#region Membri privati
							
							private int? m_ep_id = null;

							private string m_ep_descrizione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("EP_ID" , Type=DbType.Decimal)]
							public int? EpId
							{
								get{ return m_ep_id; }
								set{ m_ep_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("EP_DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=30)]
							public string EpDescrizione
							{
								get{ return m_ep_descrizione; }
								set{ m_ep_descrizione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				