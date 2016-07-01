
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
						/// File generato automaticamente dalla tabella FO_VISURA_CAMPI il 28/07/2011 9.55.35
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
						[DataTable("FO_VISURA_CAMPI")]
						[Serializable]
						public partial class FoVisuraCampi : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

							private string m_fkidcontesto = null;

							private string m_fkidcampo = null;

							private int? m_posizione = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("SOFTWARE" , Type=DbType.String, Size=2)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[KeyField("FKIDCONTESTO" , Type=DbType.String, Size=50)]
							public string Fkidcontesto
							{
								get{ return m_fkidcontesto; }
								set{ m_fkidcontesto = value; }
							}
							
							[KeyField("FKIDCAMPO" , Type=DbType.String, Size=50)]
							public string Fkidcampo
							{
								get{ return m_fkidcampo; }
								set{ m_fkidcampo = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("POSIZIONE" , Type=DbType.Decimal)]
							public int? Posizione
							{
								get{ return m_posizione; }
								set{ m_posizione = value; }
							}
							
							#endregion

							#endregion
						}
					}
				