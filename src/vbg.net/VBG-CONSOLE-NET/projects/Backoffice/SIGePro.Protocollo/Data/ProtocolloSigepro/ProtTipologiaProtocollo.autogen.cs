
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
						/// File generato automaticamente dalla tabella PROT_TIPOLOGIAPROTOCOLLO il 19/01/2009 10.56.08
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
						[DataTable("PROT_TIPOLOGIAPROTOCOLLO")]
						[Serializable]
						public partial class ProtTipologiaProtocollo : BaseDataClass
						{
							#region Membri privati
							
							private int? m_tp_id = null;

							private string m_tp_descrizione = null;

							private int? m_tp_nrgiorni = null;

							private string m_idcomune = null;

							private int? m_tp_creaistanza = null;

							private int? m_tp_oggettorifistanza = null;

							private int? m_tp_oggettorifsoftware = null;

							private int? m_tp_oggettorifintervento = null;

							private int? m_tp_previstarisp = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("TP_ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Tp_Id
							{
								get{ return m_tp_id; }
								set{ m_tp_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("TP_DESCRIZIONE" , Type=DbType.String, CaseSensitive=false, Size=150)]
							public string Tp_Descrizione
							{
								get{ return m_tp_descrizione; }
								set{ m_tp_descrizione = value; }
							}
							
							[DataField("TP_NRGIORNI" , Type=DbType.Decimal)]
							public int? Tp_Nrgiorni
							{
								get{ return m_tp_nrgiorni; }
								set{ m_tp_nrgiorni = value; }
							}
							
							[DataField("TP_CREAISTANZA" , Type=DbType.Decimal)]
							public int? Tp_Creaistanza
							{
								get{ return m_tp_creaistanza; }
								set{ m_tp_creaistanza = value; }
							}
							
							[DataField("TP_OGGETTORIFISTANZA" , Type=DbType.Decimal)]
							public int? Tp_Oggettorifistanza
							{
								get{ return m_tp_oggettorifistanza; }
								set{ m_tp_oggettorifistanza = value; }
							}
							
							[DataField("TP_OGGETTORIFSOFTWARE" , Type=DbType.Decimal)]
							public int? Tp_Oggettorifsoftware
							{
								get{ return m_tp_oggettorifsoftware; }
								set{ m_tp_oggettorifsoftware = value; }
							}
							
							[DataField("TP_OGGETTORIFINTERVENTO" , Type=DbType.Decimal)]
							public int? Tp_Oggettorifintervento
							{
								get{ return m_tp_oggettorifintervento; }
								set{ m_tp_oggettorifintervento = value; }
							}
							
							[DataField("TP_PREVISTARISP" , Type=DbType.Decimal)]
							public int? Tp_Previstarisp
							{
								get{ return m_tp_previstarisp; }
								set{ m_tp_previstarisp = value; }
							}
							
							#endregion

							#endregion
						}
					}
				