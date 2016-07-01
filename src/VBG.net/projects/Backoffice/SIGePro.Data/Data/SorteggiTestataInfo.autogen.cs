
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
						/// File generato automaticamente dalla tabella SORTEGGITESTATAINFO il 27/01/2009 8.44.19
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
						[DataTable("SORTEGGITESTATAINFO")]
						[Serializable]
						public partial class SorteggiTestataInfo : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_fk_stid = null;

							private string m_nome = null;

							private string m_etichetta = null;

							private string m_valore = null;

                            private int? m_ordine = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FK_STID" , Type=DbType.Decimal)]
							public int? FkStid
							{
								get{ return m_fk_stid; }
								set{ m_fk_stid = value; }
							}
							
							[KeyField("NOME" , Type=DbType.String, Size=30)]
							public string Nome
							{
								get{ return m_nome; }
								set{ m_nome = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("ETICHETTA" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Etichetta
							{
								get{ return m_etichetta; }
								set{ m_etichetta = value; }
							}
							
							[DataField("VALORE" , Type=DbType.String, CaseSensitive=false, Size=2000)]
							public string Valore
							{
								get{ return m_valore; }
								set{ m_valore = value; }
							}
							
							[isRequired]
                            [DataField("ORDINE" , Type=DbType.Decimal)]
							public int? Ordine
							{
								get{ return m_ordine; }
								set{ m_ordine = value; }
							}
							
							#endregion

							#endregion
						}
					}
				