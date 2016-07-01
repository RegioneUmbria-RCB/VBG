
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
						/// File generato automaticamente dalla tabella CC_TABELLA_CLASSIEDIFICIO il 27/06/2008 13.01.40
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
						[DataTable("CC_TABELLA_CLASSIEDIFICIO")]
						[Serializable]
						public partial class CCTabellaClassiEdificio : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

							private string m_descrizione = null;

                            private int? m_da = null;

                            private int? m_a = null;

                            private double? m_maggiorazione = null;

							private string m_software = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, CaseSensitive=true, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
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
                            [DataField("DA" , Type=DbType.Decimal)]
							public int? Da
							{
								get{ return m_da; }
								set{ m_da = value; }
							}
							
							[isRequired]
                            [DataField("A" , Type=DbType.Decimal)]
							public int? A
							{
								get{ return m_a; }
								set{ m_a = value; }
							}
							
							[isRequired]
                            [DataField("MAGGIORAZIONE" , Type=DbType.Decimal)]
							public double? Maggiorazione
							{
								get{ return m_maggiorazione; }
								set{ m_maggiorazione = value; }
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
				