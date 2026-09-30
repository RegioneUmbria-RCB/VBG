
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
						/// File generato automaticamente dalla tabella STILIFRONTOFFICE il 24/03/2010 10.11.25
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
						[DataTable("STILIFRONTOFFICE")]
						[Serializable]
						public partial class StiliFrontOffice : BaseDataClass
						{
							#region Membri privati
							
							private int? m_ico_anteprima = null;

							private int? m_ico_binocolo = null;

							private int? m_but_allegati = null;

							private int? m_but_freccia = null;

							private int? m_but_calcola = null;

							private int? m_but_chiudi = null;

							private int? m_but_datianagrafici = null;

							private int? m_but_endoprocedimenti = null;

							private int? m_but_esci = null;

							private int? m_but_home = null;

							private int? m_but_indietro = null;

							private int? m_but_nuova = null;

							private int? m_but_nuovaistanza = null;

							private int? m_but_precedente = null;

							private int? m_but_registrati = null;

							private int? m_but_stampa = null;

							private int? m_but_successiva = null;

							private int? m_element_dot = null;

							private int? m_element_dotprincipali = null;

							private int? m_element_menu = null;

							private int? m_ico_cestino = null;

							private int? m_ico_upload = null;

							private int? m_ico_txt = null;

							private int? m_ico_word = null;

							private int? m_ico_collegamento = null;

							private int? m_ico_modello = null;

							private int? m_label_documenti_da_scaricare = null;

							private int? m_label_info_intervento = null;

							private int? m_label_info_interventoon = null;

							private int? m_label_info_personali = null;

							private int? m_label_info_personalion = null;

							private int? m_label_inserimento = null;

							private int? m_label_inserimentoon = null;

							private int? m_label_risultato = null;

							private int? m_label_risultatoon = null;

							private int? m_label_servizi_accessori = null;

							private int? m_label_servizi_principali = null;

							private int? m_logo_comune = null;

							private int? m_logo_suap = null;

							private int? m_wcag1aa = null;

							private int? m_styleie = null;

							private int? m_but_procedi = null;

							private int? m_dot = null;

							private string m_idcomune = null;

							private int? m_ico_msg1 = null;

							private int? m_ico_re = null;

							private int? m_label_accessi = null;

							private int? m_ico_underc = null;

							private int? m_sfondo_tablehome = null;
			
							#endregion
							
							#region properties
							
							#region Key Fields
							
							
							[KeyField("IDCOMUNE" , Type=DbType.String, Size=6)]
							public string Idcomune
							{
								get{ return m_idcomune; }
								set{ m_idcomune = value; }
							}
							
							
							#endregion
							
							#region Data fields
							
							[DataField("ICO_ANTEPRIMA" , Type=DbType.Decimal)]
							public int? IcoAnteprima
							{
								get{ return m_ico_anteprima; }
								set{ m_ico_anteprima = value; }
							}
							
							[DataField("ICO_BINOCOLO" , Type=DbType.Decimal)]
							public int? IcoBinocolo
							{
								get{ return m_ico_binocolo; }
								set{ m_ico_binocolo = value; }
							}
							
							[DataField("BUT_ALLEGATI" , Type=DbType.Decimal)]
							public int? ButAllegati
							{
								get{ return m_but_allegati; }
								set{ m_but_allegati = value; }
							}
							
							[DataField("BUT_FRECCIA" , Type=DbType.Decimal)]
							public int? ButFreccia
							{
								get{ return m_but_freccia; }
								set{ m_but_freccia = value; }
							}
							
							[DataField("BUT_CALCOLA" , Type=DbType.Decimal)]
							public int? ButCalcola
							{
								get{ return m_but_calcola; }
								set{ m_but_calcola = value; }
							}
							
							[DataField("BUT_CHIUDI" , Type=DbType.Decimal)]
							public int? ButChiudi
							{
								get{ return m_but_chiudi; }
								set{ m_but_chiudi = value; }
							}
							
							[DataField("BUT_DATIANAGRAFICI" , Type=DbType.Decimal)]
							public int? ButDatianagrafici
							{
								get{ return m_but_datianagrafici; }
								set{ m_but_datianagrafici = value; }
							}
							
							[DataField("BUT_ENDOPROCEDIMENTI" , Type=DbType.Decimal)]
							public int? ButEndoprocedimenti
							{
								get{ return m_but_endoprocedimenti; }
								set{ m_but_endoprocedimenti = value; }
							}
							
							[DataField("BUT_ESCI" , Type=DbType.Decimal)]
							public int? ButEsci
							{
								get{ return m_but_esci; }
								set{ m_but_esci = value; }
							}
							
							[DataField("BUT_HOME" , Type=DbType.Decimal)]
							public int? ButHome
							{
								get{ return m_but_home; }
								set{ m_but_home = value; }
							}
							
							[DataField("BUT_INDIETRO" , Type=DbType.Decimal)]
							public int? ButIndietro
							{
								get{ return m_but_indietro; }
								set{ m_but_indietro = value; }
							}
							
							[DataField("BUT_NUOVA" , Type=DbType.Decimal)]
							public int? ButNuova
							{
								get{ return m_but_nuova; }
								set{ m_but_nuova = value; }
							}
							
							[DataField("BUT_NUOVAISTANZA" , Type=DbType.Decimal)]
							public int? ButNuovaistanza
							{
								get{ return m_but_nuovaistanza; }
								set{ m_but_nuovaistanza = value; }
							}
							
							[DataField("BUT_PRECEDENTE" , Type=DbType.Decimal)]
							public int? ButPrecedente
							{
								get{ return m_but_precedente; }
								set{ m_but_precedente = value; }
							}
							
							[DataField("BUT_REGISTRATI" , Type=DbType.Decimal)]
							public int? ButRegistrati
							{
								get{ return m_but_registrati; }
								set{ m_but_registrati = value; }
							}
							
							[DataField("BUT_STAMPA" , Type=DbType.Decimal)]
							public int? ButStampa
							{
								get{ return m_but_stampa; }
								set{ m_but_stampa = value; }
							}
							
							[DataField("BUT_SUCCESSIVA" , Type=DbType.Decimal)]
							public int? ButSuccessiva
							{
								get{ return m_but_successiva; }
								set{ m_but_successiva = value; }
							}
							
							[DataField("ELEMENT_DOT" , Type=DbType.Decimal)]
							public int? ElementDot
							{
								get{ return m_element_dot; }
								set{ m_element_dot = value; }
							}
							
							[DataField("ELEMENT_DOTPRINCIPALI" , Type=DbType.Decimal)]
							public int? ElementDotprincipali
							{
								get{ return m_element_dotprincipali; }
								set{ m_element_dotprincipali = value; }
							}
							
							[DataField("ELEMENT_MENU" , Type=DbType.Decimal)]
							public int? ElementMenu
							{
								get{ return m_element_menu; }
								set{ m_element_menu = value; }
							}
							
							[DataField("ICO_CESTINO" , Type=DbType.Decimal)]
							public int? IcoCestino
							{
								get{ return m_ico_cestino; }
								set{ m_ico_cestino = value; }
							}
							
							[DataField("ICO_UPLOAD" , Type=DbType.Decimal)]
							public int? IcoUpload
							{
								get{ return m_ico_upload; }
								set{ m_ico_upload = value; }
							}
							
							[DataField("ICO_TXT" , Type=DbType.Decimal)]
							public int? IcoTxt
							{
								get{ return m_ico_txt; }
								set{ m_ico_txt = value; }
							}
							
							[DataField("ICO_WORD" , Type=DbType.Decimal)]
							public int? IcoWord
							{
								get{ return m_ico_word; }
								set{ m_ico_word = value; }
							}
							
							[DataField("ICO_COLLEGAMENTO" , Type=DbType.Decimal)]
							public int? IcoCollegamento
							{
								get{ return m_ico_collegamento; }
								set{ m_ico_collegamento = value; }
							}
							
							[DataField("ICO_MODELLO" , Type=DbType.Decimal)]
							public int? IcoModello
							{
								get{ return m_ico_modello; }
								set{ m_ico_modello = value; }
							}
							
							[DataField("LABEL_DOCUMENTI_DA_SCARICARE" , Type=DbType.Decimal)]
							public int? LabelDocumentiDaScaricare
							{
								get{ return m_label_documenti_da_scaricare; }
								set{ m_label_documenti_da_scaricare = value; }
							}
							
							[DataField("LABEL_INFO_INTERVENTO" , Type=DbType.Decimal)]
							public int? LabelInfoIntervento
							{
								get{ return m_label_info_intervento; }
								set{ m_label_info_intervento = value; }
							}
							
							[DataField("LABEL_INFO_INTERVENTOON" , Type=DbType.Decimal)]
							public int? LabelInfoInterventoon
							{
								get{ return m_label_info_interventoon; }
								set{ m_label_info_interventoon = value; }
							}
							
							[DataField("LABEL_INFO_PERSONALI" , Type=DbType.Decimal)]
							public int? LabelInfoPersonali
							{
								get{ return m_label_info_personali; }
								set{ m_label_info_personali = value; }
							}
							
							[DataField("LABEL_INFO_PERSONALION" , Type=DbType.Decimal)]
							public int? LabelInfoPersonalion
							{
								get{ return m_label_info_personalion; }
								set{ m_label_info_personalion = value; }
							}
							
							[DataField("LABEL_INSERIMENTO" , Type=DbType.Decimal)]
							public int? LabelInserimento
							{
								get{ return m_label_inserimento; }
								set{ m_label_inserimento = value; }
							}
							
							[DataField("LABEL_INSERIMENTOON" , Type=DbType.Decimal)]
							public int? LabelInserimentoon
							{
								get{ return m_label_inserimentoon; }
								set{ m_label_inserimentoon = value; }
							}
							
							[DataField("LABEL_RISULTATO" , Type=DbType.Decimal)]
							public int? LabelRisultato
							{
								get{ return m_label_risultato; }
								set{ m_label_risultato = value; }
							}
							
							[DataField("LABEL_RISULTATOON" , Type=DbType.Decimal)]
							public int? LabelRisultatoon
							{
								get{ return m_label_risultatoon; }
								set{ m_label_risultatoon = value; }
							}
							
							[DataField("LABEL_SERVIZI_ACCESSORI" , Type=DbType.Decimal)]
							public int? LabelServiziAccessori
							{
								get{ return m_label_servizi_accessori; }
								set{ m_label_servizi_accessori = value; }
							}
							
							[DataField("LABEL_SERVIZI_PRINCIPALI" , Type=DbType.Decimal)]
							public int? LabelServiziPrincipali
							{
								get{ return m_label_servizi_principali; }
								set{ m_label_servizi_principali = value; }
							}
							
							[DataField("LOGO_COMUNE" , Type=DbType.Decimal)]
							public int? LogoComune
							{
								get{ return m_logo_comune; }
								set{ m_logo_comune = value; }
							}
							
							[DataField("LOGO_SUAP" , Type=DbType.Decimal)]
							public int? LogoSuap
							{
								get{ return m_logo_suap; }
								set{ m_logo_suap = value; }
							}
							
							[DataField("WCAG1AA" , Type=DbType.Decimal)]
							public int? Wcag1aa
							{
								get{ return m_wcag1aa; }
								set{ m_wcag1aa = value; }
							}
							
							[DataField("STYLEIE" , Type=DbType.Decimal)]
							public int? Styleie
							{
								get{ return m_styleie; }
								set{ m_styleie = value; }
							}
							
							[DataField("BUT_PROCEDI" , Type=DbType.Decimal)]
							public int? ButProcedi
							{
								get{ return m_but_procedi; }
								set{ m_but_procedi = value; }
							}
							
							[DataField("DOT" , Type=DbType.Decimal)]
							public int? Dot
							{
								get{ return m_dot; }
								set{ m_dot = value; }
							}
							
							[DataField("ICO_MSG1" , Type=DbType.Decimal)]
							public int? IcoMsg1
							{
								get{ return m_ico_msg1; }
								set{ m_ico_msg1 = value; }
							}
							
							[DataField("ICO_RE" , Type=DbType.Decimal)]
							public int? IcoRe
							{
								get{ return m_ico_re; }
								set{ m_ico_re = value; }
							}
							
							[DataField("LABEL_ACCESSI" , Type=DbType.Decimal)]
							public int? LabelAccessi
							{
								get{ return m_label_accessi; }
								set{ m_label_accessi = value; }
							}
							
							[DataField("ICO_UNDERC" , Type=DbType.Decimal)]
							public int? IcoUnderc
							{
								get{ return m_ico_underc; }
								set{ m_ico_underc = value; }
							}
							
							[DataField("SFONDO_TABLEHOME" , Type=DbType.Decimal)]
							public int? SfondoTablehome
							{
								get{ return m_sfondo_tablehome; }
								set{ m_sfondo_tablehome = value; }
							}
							
							#endregion

							#endregion
						}
					}
				