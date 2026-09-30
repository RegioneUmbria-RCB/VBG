
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
						/// File generato automaticamente dalla tabella SOFTWAREATTIVI il 09/09/2010 14.57.42
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
						[DataTable("SOFTWAREATTIVI")]
						[Serializable]
						public partial class SoftwareAttivi : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_fk_software = null;

							private int? m_attivo_fo = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("FK_SOFTWARE" , Type=DbType.String, Size=2)]
							public string FkSoftware
							{
								get{ return m_fk_software; }
								set{ m_fk_software = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("ATTIVO_FO" , Type=DbType.Decimal)]
							public int? AttivoFo
							{
								get{ return m_attivo_fo; }
								set{ m_attivo_fo = value; }
							}
							
							#endregion

							#endregion
						}
					}
				