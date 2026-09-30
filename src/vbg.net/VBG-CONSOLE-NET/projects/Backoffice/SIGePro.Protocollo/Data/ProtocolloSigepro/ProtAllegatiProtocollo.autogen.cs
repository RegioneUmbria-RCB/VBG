
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
						/// File generato automaticamente dalla tabella PROT_ALLEGATIPROTOCOLLO il 09/01/2009 12.28.25
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
						[DataTable("PROT_ALLEGATIPROTOCOLLO")]
						[Serializable]
						public partial class ProtAllegatiProtocollo : BaseDataClass
						{
							#region Membri privati
							
							private int? m_ad_id = null;

							private int? m_ad_ogid = null;

							private string m_ad_descrizione = null;

							private int? m_ad_dlid = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("AD_ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Ad_Id
							{
								get{ return m_ad_id; }
								set{ m_ad_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("AD_OGID" , Type=DbType.Decimal)]
							public int? Ad_Ogid
							{
								get{ return m_ad_ogid; }
								set{ m_ad_ogid = value; }
							}
							
							[DataField("AD_DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=50)]
							public string Ad_Descrizione
							{
								get{ return m_ad_descrizione; }
								set{ m_ad_descrizione = value; }
							}
							
							[isRequired]
                            [DataField("AD_DLID" , Type=DbType.Decimal)]
							public int? Ad_Dlid
							{
								get{ return m_ad_dlid; }
								set{ m_ad_dlid = value; }
							}
							
							#endregion

							#endregion
						}
					}
				