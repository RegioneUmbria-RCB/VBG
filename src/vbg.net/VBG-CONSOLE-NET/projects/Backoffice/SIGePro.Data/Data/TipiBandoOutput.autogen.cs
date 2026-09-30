
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
						/// File generato automaticamente dalla tabella TIPIBANDOOUTPUT il 01/04/2009 9.45.24
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
						[DataTable("TIPIBANDOOUTPUT")]
						[Serializable]
						public partial class TipiBandoOutput : BaseDataClass
						{
							#region Membri privati

                            private int? m_id = null;

                            private int? m_fk_tgt_id = null;

							private string m_tipocalcolo = null;

                            private int? m_fk_tbi_id = null;

                            private int? m_fk_d2c_id_rif = null;

                            private int? m_fk_d2c_id_out = null;

							private string m_idcomune = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("ID" , Type=DbType.Decimal)]
                            [useSequence]
							public int? Id
							{
								get{ return m_id; }
								set{ m_id = value; }
							}
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
                            [DataField("FK_TGT_ID" , Type=DbType.Decimal)]
							public int? FkTgtId
							{
								get{ return m_fk_tgt_id; }
								set{ m_fk_tgt_id = value; }
							}
							
							[isRequired]
                            [DataField("TIPOCALCOLO" , Type=DbType.String, CaseSensitive=false, Size=30)]
							public string Tipocalcolo
							{
								get{ return m_tipocalcolo; }
								set{ m_tipocalcolo = value; }
							}
							
							[DataField("FK_TBI_ID" , Type=DbType.Decimal)]
							public int? FkTbiId
							{
								get{ return m_fk_tbi_id; }
								set{ m_fk_tbi_id = value; }
							}
							
							[DataField("FK_D2C_ID_RIF" , Type=DbType.Decimal)]
							public int? FkD2cIdRif
							{
								get{ return m_fk_d2c_id_rif; }
								set{ m_fk_d2c_id_rif = value; }
							}
							
							[isRequired]
                            [DataField("FK_D2C_ID_OUT" , Type=DbType.Decimal)]
							public int? FkD2cIdOut
							{
								get{ return m_fk_d2c_id_out; }
								set{ m_fk_d2c_id_out = value; }
							}
							
							#endregion

							#endregion
						}
					}
				