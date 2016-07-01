
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
						/// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT il 27/06/2008 13.01.35
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
						[DataTable("O_ICALCOLOCONTRIBT")]
						[Serializable]
						public partial class OICalcoloContribT : BaseDataClass
						{
							#region Membri privati
					
							
							private string m_idcomune = null;

                            private int? m_id = null;

                            private int? m_codiceistanza = null;

							private string m_fk_occbde_id = null;

                            private int? m_fk_aree_codicearea_zto = null;

                            private int? m_fk_aree_codicearea_prg = null;

                            private int? m_fk_oit_id = null;

                            private int? m_fk_oin_id = null;

                            private int? m_fk_oin_id_tabd = null;

                            private int? m_fk_oict_id = null;

                            private int? m_fk_ocla_id = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, CaseSensitive=true, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							

							
							
							#endregion
							
							#region Data fields
							
							[isRequired]
                            [DataField("CODICEISTANZA" , Type=DbType.Decimal)]
							public int? Codiceistanza
							{
								get{ return m_codiceistanza; }
								set{ m_codiceistanza = value; }
							}
							
							[isRequired]
                            [DataField("FK_OCCBDE_ID" , Type=DbType.String, CaseSensitive=false, Size=1)]
							public string FkOccbdeId
							{
								get{ return m_fk_occbde_id; }
								set{ m_fk_occbde_id = value; }
							}
							
							[DataField("FK_AREE_CODICEAREA_ZTO" , Type=DbType.Decimal)]
							public int? FkAreeCodiceareaZto
							{
								get{ return m_fk_aree_codicearea_zto; }
								set{ m_fk_aree_codicearea_zto = value; }
							}
							
							[DataField("FK_AREE_CODICEAREA_PRG" , Type=DbType.Decimal)]
							public int? FkAreeCodiceareaPrg
							{
								get{ return m_fk_aree_codicearea_prg; }
								set{ m_fk_aree_codicearea_prg = value; }
							}
							
							[DataField("FK_OIT_ID" , Type=DbType.Decimal)]
							public int? FkOitId
							{
								get{ return m_fk_oit_id; }
								set{ m_fk_oit_id = value; }
							}
							
							[DataField("FK_OIN_ID" , Type=DbType.Decimal)]
							public int? FkOinId
							{
								get{ return m_fk_oin_id; }
								set{ m_fk_oin_id = value; }
							}

							[DataField("FK_OIN_ID_TABD", Type = DbType.Decimal)]
							public int? FkOinIdTabd
							{
								get { return m_fk_oin_id_tabd; }
								set { m_fk_oin_id_tabd = value; }
							}

							[DataField("FK_OICT_ID" , Type=DbType.Decimal)]
							public int? FkOictId
							{
								get{ return m_fk_oict_id; }
								set{ m_fk_oict_id = value; }
							}
							
							[DataField("FK_OCLA_ID" , Type=DbType.Decimal)]
							public int? FkOclaId
							{
								get{ return m_fk_ocla_id; }
								set{ m_fk_ocla_id = value; }
							}
							
							#endregion

							#endregion
						}
					}
				