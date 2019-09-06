
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
						/// File generato automaticamente dalla tabella NORMATIVE il 30/11/2010 16.44.24
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
						[DataTable("NORMATIVE")]
						[Serializable]
						public partial class Normative : BaseDataClass
						{
							#region Membri privati
							
							private int? m_codicenormativa = null;

							private string m_normativa = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICENORMATIVA" , Type=DbType.Decimal)]
							public int? Codicenormativa
							{
								get{ return m_codicenormativa; }
								set{ m_codicenormativa = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("NORMATIVA" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Normativa
							{
								get{ return m_normativa; }
								set{ m_normativa = value; }
							}
							
							#endregion

							#endregion
						}
					}
				