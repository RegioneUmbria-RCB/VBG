
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
						/// File generato automaticamente dalla tabella FO_DOMANDE_OGGETTI il 06/11/2009 16.30.01
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
						[DataTable("FO_DOMANDE_OGGETTI")]
						[Serializable]
						public partial class FoDomandeOggetti : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_iddomanda = null;

							private int? m_codiceoggetto = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=24)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("IDDOMANDA" , Type=DbType.Decimal)]
							public int? Iddomanda
							{
								get{ return m_iddomanda; }
								set{ m_iddomanda = value; }
							}
							
							[KeyField("CODICEOGGETTO" , Type=DbType.Decimal)]
							public int? Codiceoggetto
							{
								get{ return m_codiceoggetto; }
								set{ m_codiceoggetto = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							#endregion

							#endregion
						}
					}
				