
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
						/// File generato automaticamente dalla tabella DYN2_CAMPIPROPRIETA il 05/08/2008 16.49.58
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
						[DataTable("DYN2_CAMPIPROPRIETA")]
						[Serializable]
						public partial class Dyn2CampiProprieta : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

                            private int? m_fk_d2c_id = null;

							private string m_proprieta = null;

							private string m_valore = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FK_D2C_ID" , Type=DbType.Decimal)]
							public int? FkD2cId
							{
								get{ return m_fk_d2c_id; }
								set{ m_fk_d2c_id = value; }
							}
							
							[KeyField("PROPRIETA" , Type=DbType.String, Size=30)]
							public string Proprieta
							{
								get{ return m_proprieta; }
								set{ m_proprieta = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("VALORE" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Valore
							{
								get{ return m_valore; }
								set{ m_valore = value; }
							}
							
							#endregion

							#endregion
						}
					}
				