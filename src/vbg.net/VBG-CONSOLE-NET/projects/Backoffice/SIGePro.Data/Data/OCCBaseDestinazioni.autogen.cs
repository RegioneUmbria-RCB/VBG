
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
						/// File generato automaticamente dalla tabella OCC_BASEDESTINAZIONI il 27/06/2008 13.01.40
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
						[DataTable("OCC_BASEDESTINAZIONI")]
						[Serializable]
						public partial class OCCBaseDestinazioni : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_id = null;

							private string m_destinazione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
				
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("DESTINAZIONE" , Type=DbType.String, CaseSensitive=false, Size=200)]
							public string Destinazione
							{
								get{ return m_destinazione; }
								set{ m_destinazione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				