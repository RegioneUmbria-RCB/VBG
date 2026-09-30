
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
						/// File generato automaticamente dalla tabella PROTOCOLLO_TIPIDOCUMENTO il 05/03/2009 17.48.16
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
						[DataTable("PROTOCOLLO_TIPIDOCUMENTO")]
						[Serializable]
						public partial class ProtocolloTipiDocumento : BaseDataClassProtocollo
						{
                            [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
                            public string Idcomune { get; set; }

                            [KeyField("ID", Type = DbType.Decimal, Size = 10)]
                            public int? Id { get; set; }

                            [isRequired]
                            [DataField("CODICE", Type = DbType.String, Size = 10)]
                            public string Codice { get; set; }
							
                            [isRequired]
                            [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 100)]
                            public string Descrizione { get; set; }
						}
					}
				