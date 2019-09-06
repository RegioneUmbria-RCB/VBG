
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
						/// File generato automaticamente dalla tabella TEMPIFICAZIONI il 30/11/2010 11.50.53
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
						[DataTable("TEMPIFICAZIONI")]
						[Serializable]
						public partial class Tempificazioni : BaseDataClass
						{
							#region Membri privati
							
							private int? m_codicetempificazione = null;

							private string m_tempificazione = null;

							private int? m_ordine = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CODICETEMPIFICAZIONE" , Type=DbType.Decimal)]
[useSequence]
							public int? Codicetempificazione
							{
								get{ return m_codicetempificazione; }
								set{ m_codicetempificazione = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("TEMPIFICAZIONE" , Type=DbType.String, CaseSensitive=false, Size=35)]
							public string Tempificazione
							{
								get{ return m_tempificazione; }
								set{ m_tempificazione = value; }
							}
							
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
				