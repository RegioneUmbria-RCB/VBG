
					using System;
					using System.Data;
					using System.Reflection;
					using System.Text;
					using Init.SIGePro.Attributes;
					using Init.SIGePro.Collection;
					using PersonalLib2.Sql.Attributes;
					using PersonalLib2.Sql;
using System.Xml.Serialization;

					namespace Init.SIGePro.Data
					{
						///
						/// File generato automaticamente dalla tabella OGGETTI_METADATI il 24/05/2013 16.50.58
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
						[DataTable("OGGETTI_METADATI")]
						[Serializable]
						public partial class OggettiMetadati : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private int? m_codiceoggetto = null;

							private string m_chiave = null;

							private string m_valore = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							[XmlElement(Order = 0)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("CODICEOGGETTO" , Type=DbType.Decimal)]
							[XmlElement(Order = 1)]
							public int? Codiceoggetto
							{
								get{ return m_codiceoggetto; }
								set{ m_codiceoggetto = value; }
							}
							
							[KeyField("CHIAVE" , Type=DbType.String, Size=100)]
							[XmlElement(Order = 2)]
							public string Chiave
							{
								get{ return m_chiave; }
								set{ m_chiave = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("VALORE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							[XmlElement(Order = 3)]
							public string Valore
							{
								get{ return m_valore; }
								set{ m_valore = value; }
							}
							
							#endregion

							#endregion
						}
					}
				