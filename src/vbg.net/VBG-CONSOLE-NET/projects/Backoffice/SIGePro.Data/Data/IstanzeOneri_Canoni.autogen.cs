
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
						/// File generato automaticamente dalla tabella ISTANZEONERI_CANONI il 16/09/2008 18.51.40
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
						[DataTable("ISTANZEONERI_CANONI")]
						[Serializable]
						public partial class IstanzeOneri_Canoni : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_fk_id_istoneri = null;

                            private int? m_fk_idtestata = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FK_ID_ISTONERI" , Type=DbType.Decimal)]
							public int? FkIdIstoneri
							{
								get{ return m_fk_id_istoneri; }
								set{ m_fk_id_istoneri = value; }
							}
							
							[KeyField("FK_IDTESTATA" , Type=DbType.Decimal)]
							public int? FkIdtestata
							{
								get{ return m_fk_idtestata; }
								set{ m_fk_idtestata = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							#endregion

							#endregion
						}
					}
				