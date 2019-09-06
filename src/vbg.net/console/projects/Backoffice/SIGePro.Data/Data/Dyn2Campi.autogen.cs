
					using System;
					using System.Data;
					using System.Reflection;
					using System.Text;
					using Init.SIGePro.Attributes;
					using Init.SIGePro.Collection;
					using PersonalLib2.Sql.Attributes;
					using PersonalLib2.Sql;
using System.Security;
using Init.SIGePro.DatiDinamici.Utils;

namespace Init.SIGePro.Data
					{
						///
						/// File generato automaticamente dalla tabella DYN2_CAMPI il 05/08/2008 16.49.58
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
						[DataTable("DYN2_CAMPI")]
						[Serializable]
						public partial class Dyn2Campi : BaseDataClass
						{
							#region Membri privati
							
							private string m_idcomune = null;

							private string m_software = null;

                            private int? m_id = null;

							private string m_nomecampo = null;

							private string m_etichetta = null;

							private string m_descrizione = null;

							private string m_tipodato = null;

                            private int? m_obbligatorio = null;

							private string m_scrptcode = null;

							private string m_scriptupdatecode = null;

							private string m_fk_d2bc_id = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							[KeyField("ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
[DataField("SOFTWARE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Software
							{
								get{ return m_software; }
								set{ m_software = value; }
							}
							
							[isRequired]
[DataField("NOMECAMPO" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							public string Nomecampo
							{
								get{ return m_nomecampo; }
								set{ m_nomecampo = value; }
							}
							
							[DataField("ETICHETTA" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Etichetta
							{
								get{ return ReplaceNonXmlEntities.Escape(m_etichetta); }
								set{ m_etichetta = value; }
							}
							
							[DataField("DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=1000)]
							public string Descrizione
							{
								get{ return ReplaceNonXmlEntities.Escape(m_descrizione); }
								set{ m_descrizione = value; }
							}
							
							[isRequired]
[DataField("TIPODATO" , Type=DbType.String, CaseSensitive=false, Size=20)]
							public string Tipodato
							{
								get{ return m_tipodato; }
								set{ m_tipodato = value; }
							}
							
							[DataField("OBBLIGATORIO" , Type=DbType.Decimal)]
							public int? Obbligatorio
							{
								get{ return m_obbligatorio; }
								set{ m_obbligatorio = value; }
							}
							
							//[DataField("SCRPTCODE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							//public string Scriptcode
							//{
							//    get{ return m_scrptcode; }
							//    set{ m_scrptcode = value; }
							//}
							
							//[DataField("SCRIPTUPDATECODE" , Type=DbType.String, CaseSensitive=false, Size=4000)]
							//public string Scriptupdatecode
							//{
							//    get{ return m_scriptupdatecode; }
							//    set{ m_scriptupdatecode = value; }
							//}
							
							[DataField("FK_D2BC_ID" , Type=DbType.String, CaseSensitive=false, Size=2)]
							public string FkD2bcId
							{
								get{ return m_fk_d2bc_id; }
								set{ m_fk_d2bc_id = value; }
							}
							
							#endregion

							#endregion
						}
					}
				