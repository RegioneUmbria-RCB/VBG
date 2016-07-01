
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
						/// File generato automaticamente dalla tabella MAPPATUREPEOPLET il 28/04/2010 9.52.13
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
						[DataTable("MAPPATUREPEOPLET")]
						[Serializable]
						public partial class MappaturePeopleT : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_idmappatura = null;

							private string m_descrizione = null;

							private int? m_idtipomappatura = null;

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
							
							[KeyField("IDMAPPATURA" , Type=DbType.Decimal)]
[useSequence]
							public int? Idmappatura
							{
								get{ return m_idmappatura; }
								set{ m_idmappatura = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Descrizione
							{
								get{ return m_descrizione; }
								set{ m_descrizione = value; }
							}
							
							[isRequired]
[DataField("IDTIPOMAPPATURA" , Type=DbType.Decimal)]
							public int? Idtipomappatura
							{
								get{ return m_idtipomappatura; }
								set{ m_idtipomappatura = value; }
							}
							
							[isRequired]
[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							#endregion

							#endregion
						}
					}
				