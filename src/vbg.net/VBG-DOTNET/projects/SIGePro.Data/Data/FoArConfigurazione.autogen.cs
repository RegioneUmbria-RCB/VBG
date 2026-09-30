
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    ///
    /// File generato automaticamente dalla tabella FO_ARCONFIGURAZIONE il 11/04/2011 15.58.47
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
    [DataTable("FO_ARCONFIGURAZIONE")]
    [Serializable]
    public partial class FoArConfigurazione : BaseDataClass
    {



        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; } = null;

        [KeyField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; } = null;

        [DataField("STATO_INIZIALE_ISTANZA", Type = DbType.String, CaseSensitive = false, Size = 20)]
        public string StatoInizialeIstanza { get; set; } = null;

        [DataField("INTESTAZIONE_DETTAGLIO_VISURA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string IntestazioneDettaglioVisura { get; set; } = null;

        [DataField("MSG_INVIO_FALLITO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string MsgInvioFallito { get; set; } = null;

        [DataField("CODICEOGGETTO_FIRMA", Type = DbType.Decimal)]
        public int? CodiceoggettoFirma { get; set; } = null;

        //[DataField("CODICEOGGETTO_SOTTOSCRIZ", Type = DbType.Decimal)]
        //public int? CodiceoggettoSottoscriz
        //{
        //    get { return this.m_codiceoggetto_sottoscriz; }
        //    set { this.m_codiceoggetto_sottoscriz = value; }
        //}

        [DataField("NOME_PARAMETRO_LOGIN_URL", Type = DbType.String, CaseSensitive = false, Size = 140)]
        public string NomeParametroLoginUrl { get; set; } = null;

        [DataField("MSG_REGISTRAZIONE_COMPLETATA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string MsgRegistrazioneCompletata { get; set; } = null;

        [DataField("MSG_INVIO_PEC", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string MsgInvioPec { get; set; } = null;

        [DataField("CODICEOGGETTO_WORKFLOW", Type = DbType.Decimal)]
        public int? CodiceoggettoWorkflow { get; set; } = null;

        [DataField("CODICEOGGETTO_MENUXML", Type = DbType.Decimal)]
        public int? CodiceoggettoMenuXml { get; set; } = null;


        [DataField("NOME_CONFIGURAZIONE_CONTENUTI", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string NomeConfigurazioneContenuti
        {
            get;
            set;
        }

        [DataField("FKID_SCHEDA_EC", Type = DbType.Decimal)]
        public int? FkidSchedaEc
        {
            get;
            set;
        }
        [DataField("FLG_SCHEDA_EC_RICHIEDEFIRMA", Type = DbType.Decimal)]
        public int? FlgSchedaEcRichiedeFirma
        {
            get;
            set;
        }

        [DataField("CODOGGETTO_RIEP_SCHEDE", Type = DbType.Decimal)]
        public int? CodiceOggettoRiepilogoSchede
        {
            get;
            set;
        }
    }
}
