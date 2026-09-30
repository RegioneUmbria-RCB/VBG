
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
						/// File generato automaticamente dalla tabella PROT_CLASSIFICAZIONE il 19/01/2009 10.45.59
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
						[DataTable("PROT_CLASSIFICAZIONE")]
						[Serializable]
						public partial class ProtClassificazione : BaseDataClass
						{
							#region Membri privati
							
							private int? m_cl_id = null;

							private string m_cl_descrizione = null;

							private int? m_cl_padre = null;

							private string m_idcomune = null;

							private string m_cl_note = null;

							private int? m_cl_ordinamento = null;

							private int? m_cl_fkidfascicolo = null;

							private int? m_cl_fkidaoo = null;

							private int? m_cl_fkidresponsabile = null;

							private int? m_cl_disabilitato = null;

							private int? m_cl_altreclassificazioni = null;

							private int? m_cl_abilitacollegamento = null;

							private string m_cl_codice = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("CL_ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Cl_Id
							{
								get{ return m_cl_id; }
								set{ m_cl_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("CL_DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Cl_Descrizione
							{
								get{ return m_cl_descrizione; }
								set{ m_cl_descrizione = value; }
							}
							
							[DataField("CL_PADRE" , Type=DbType.Decimal)]
							public int? Cl_Padre
							{
								get{ return m_cl_padre; }
								set{ m_cl_padre = value; }
							}
							
							[DataField("CL_NOTE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Cl_Note
							{
								get{ return m_cl_note; }
								set{ m_cl_note = value; }
							}
							
							[DataField("CL_ORDINAMENTO" , Type=DbType.Decimal)]
							public int? Cl_Ordinamento
							{
								get{ return m_cl_ordinamento; }
								set{ m_cl_ordinamento = value; }
							}
							
							[DataField("CL_FKIDFASCICOLO" , Type=DbType.Decimal)]
							public int? Cl_Fkidfascicolo
							{
								get{ return m_cl_fkidfascicolo; }
								set{ m_cl_fkidfascicolo = value; }
							}
							
							[DataField("CL_FKIDAOO" , Type=DbType.Decimal)]
							public int? Cl_Fkidaoo
							{
								get{ return m_cl_fkidaoo; }
								set{ m_cl_fkidaoo = value; }
							}
							
							[DataField("CL_FKIDRESPONSABILE" , Type=DbType.Decimal)]
							public int? Cl_Fkidresponsabile
							{
								get{ return m_cl_fkidresponsabile; }
								set{ m_cl_fkidresponsabile = value; }
							}
							
							[DataField("CL_DISABILITATO" , Type=DbType.Decimal)]
							public int? Cl_Disabilitato
							{
								get{ return m_cl_disabilitato; }
								set{ m_cl_disabilitato = value; }
							}
							
							[DataField("CL_ALTRECLASSIFICAZIONI" , Type=DbType.Decimal)]
							public int? Cl_Altreclassificazioni
							{
								get{ return m_cl_altreclassificazioni; }
								set{ m_cl_altreclassificazioni = value; }
							}
							
							[DataField("CL_ABILITACOLLEGAMENTO" , Type=DbType.Decimal)]
							public int? Cl_Abilitacollegamento
							{
								get{ return m_cl_abilitacollegamento; }
								set{ m_cl_abilitacollegamento = value; }
							}
							
							[DataField("CL_CODICE" , Type=DbType.String, CaseSensitive=false, Size=100)]
							public string Cl_Codice
							{
								get{ return m_cl_codice; }
								set{ m_cl_codice = value; }
							}
							
							#endregion

							#endregion
						}
					}
				