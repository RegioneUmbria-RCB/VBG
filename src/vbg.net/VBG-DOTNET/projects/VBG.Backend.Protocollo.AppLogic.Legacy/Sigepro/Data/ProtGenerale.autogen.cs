
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data
{
    ///
    /// File generato automaticamente dalla tabella PROT_GENERALE il 09/01/2009 12.31.46
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
    [DataTable("PROT_GENERALE")]
    [Serializable]
    public partial class ProtGenerale : BaseDataClass
    {
        #region Membri privati

        private int? _pg_numero = null;

        private int? _pg_anno = null;

        private string _pg_dataregistrazione = null;

        private string _pg_oraregistrazione = null;

        private string _pg_oggetto = null;

        private int? _pg_fkidmittente = null;

        private string _pg_impronta = null;

        private int? _pg_numeroingresso = null;

        private string _pg_dataingresso = null;

        private string _pg_annullato = null;

        private int? _pg_fkidmotivoannullamento = null;

        private string _pg_noteannullamento = null;

        private int? _pg_fkidfascicolo = null;

        private int? _pg_fkidtipologia = null;

        private int? _pg_fkidmodalita = null;

        private int? _pg_fkidclassificazione = null;

        private int? _pg_fkiddestinatario = null;

        private string _pg_dataannullamento = null;

        private string _pg_oraannullamento = null;

        private string _pg_fkutenteannullamento = null;

        private int? _pg_id = null;

        private string _pg_fkutenteinserimento = null;

        private string _pg_mittente = null;

        private string _pg_destinatario = null;

        private int? _pg_fkidprotocollo = null;

        private int? _pg_fkidpercontodi = null;

        private string _pg_percontodi = null;

        private int? _pg_ogid = null;

        private string _pg_note = null;

        private string _idcomune = null;

        private int? _pg_nrgiorni = null;

        private int? _pg_fkidaoo = null;

        private string _pg_rifregemergenza = null;

        private int? _pg_flagprivacy = null;

        private int? _pg_fk_esid = null;

        private int? _pg_chiusura = null;

        private string _pg_datachiusura = null;

        private int? _pg_prevrisposta = null;

        private string _pg_noteprevrisposta = null;

        private string _pg_indirizzoMittente = null;

        private string _pg_indirizzoPerContoDi = null;

        private string _pg_indirizzoDestinatario = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("PG_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Pg_Id
        {
            get { return this._pg_id; }
            set { this._pg_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this._idcomune; }
            set { this._idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("PG_NUMERO", Type = DbType.Decimal)]
        public int? Pg_Numero
        {
            get { return this._pg_numero; }
            set { this._pg_numero = value; }
        }

        [DataField("PG_ANNO", Type = DbType.Decimal)]
        public int? Pg_Anno
        {
            get { return this._pg_anno; }
            set { this._pg_anno = value; }
        }

        [DataField("PG_DATAREGISTRAZIONE", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataregistrazione
        {
            get { return this._pg_dataregistrazione; }
            set { this._pg_dataregistrazione = value; }
        }

        [DataField("PG_ORAREGISTRAZIONE", Type = DbType.String, CaseSensitive = false, Size = 6)]
        public string Pg_Oraregistrazione
        {
            get { return this._pg_oraregistrazione; }
            set { this._pg_oraregistrazione = value; }
        }

        [DataField("PG_OGGETTO", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Oggetto
        {
            get { return this._pg_oggetto; }
            set { this._pg_oggetto = value; }
        }

        [DataField("PG_FKIDMITTENTE", Type = DbType.Decimal)]
        public int? Pg_Fkidmittente
        {
            get { return this._pg_fkidmittente; }
            set { this._pg_fkidmittente = value; }
        }

        [DataField("PG_IMPRONTA", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Pg_Impronta
        {
            get { return this._pg_impronta; }
            set { this._pg_impronta = value; }
        }

        [DataField("PG_NUMEROINGRESSO", Type = DbType.Decimal)]
        public int? Pg_Numeroingresso
        {
            get { return this._pg_numeroingresso; }
            set { this._pg_numeroingresso = value; }
        }

        [DataField("PG_DATAINGRESSO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataingresso
        {
            get { return this._pg_dataingresso; }
            set { this._pg_dataingresso = value; }
        }

        [DataField("PG_ANNULLATO", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string Pg_Annullato
        {
            get { return this._pg_annullato; }
            set { this._pg_annullato = value; }
        }

        [DataField("PG_FKIDMOTIVOANNULLAMENTO", Type = DbType.Decimal)]
        public int? Pg_Fkidmotivoannullamento
        {
            get { return this._pg_fkidmotivoannullamento; }
            set { this._pg_fkidmotivoannullamento = value; }
        }

        [DataField("PG_NOTEANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Noteannullamento
        {
            get { return this._pg_noteannullamento; }
            set { this._pg_noteannullamento = value; }
        }

        [DataField("PG_FKIDFASCICOLO", Type = DbType.Decimal)]
        public int? Pg_Fkidfascicolo
        {
            get { return this._pg_fkidfascicolo; }
            set { this._pg_fkidfascicolo = value; }
        }

        [isRequired]
        [DataField("PG_FKIDTIPOLOGIA", Type = DbType.Decimal)]
        public int? Pg_Fkidtipologia
        {
            get { return this._pg_fkidtipologia; }
            set { this._pg_fkidtipologia = value; }
        }

        [DataField("PG_FKIDMODALITA", Type = DbType.Decimal)]
        public int? Pg_Fkidmodalita
        {
            get { return this._pg_fkidmodalita; }
            set { this._pg_fkidmodalita = value; }
        }

        [DataField("PG_FKIDCLASSIFICAZIONE", Type = DbType.Decimal)]
        public int? Pg_Fkidclassificazione
        {
            get { return this._pg_fkidclassificazione; }
            set { this._pg_fkidclassificazione = value; }
        }

        [DataField("PG_FKIDDESTINATARIO", Type = DbType.Decimal)]
        public int? Pg_Fkiddestinatario
        {
            get { return this._pg_fkiddestinatario; }
            set { this._pg_fkiddestinatario = value; }
        }

        [DataField("PG_DATAANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataannullamento
        {
            get { return this._pg_dataannullamento; }
            set { this._pg_dataannullamento = value; }
        }

        [DataField("PG_ORAANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 4)]
        public string Pg_Oraannullamento
        {
            get { return this._pg_oraannullamento; }
            set { this._pg_oraannullamento = value; }
        }

        [DataField("PG_FKUTENTEANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Pg_Fkutenteannullamento
        {
            get { return this._pg_fkutenteannullamento; }
            set { this._pg_fkutenteannullamento = value; }
        }

        [DataField("PG_FKUTENTEINSERIMENTO", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Pg_Fkutenteinserimento
        {
            get { return this._pg_fkutenteinserimento; }
            set { this._pg_fkutenteinserimento = value; }
        }

        [DataField("PG_MITTENTE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Mittente
        {
            get { return this._pg_mittente; }
            set { this._pg_mittente = value; }
        }

        [DataField("PG_DESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Destinatario
        {
            get { return this._pg_destinatario; }
            set { this._pg_destinatario = value; }
        }

        [DataField("PG_FKIDPROTOCOLLO", Type = DbType.Decimal)]
        public int? Pg_Fkidprotocollo
        {
            get { return this._pg_fkidprotocollo; }
            set { this._pg_fkidprotocollo = value; }
        }

        [DataField("PG_FKIDPERCONTODI", Type = DbType.Decimal)]
        public int? Pg_Fkidpercontodi
        {
            get { return this._pg_fkidpercontodi; }
            set { this._pg_fkidpercontodi = value; }
        }

        [DataField("PG_PERCONTODI", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Percontodi
        {
            get { return this._pg_percontodi; }
            set { this._pg_percontodi = value; }
        }

        [DataField("PG_OGID", Type = DbType.Decimal)]
        public int? Pg_Ogid
        {
            get { return this._pg_ogid; }
            set { this._pg_ogid = value; }
        }

        [DataField("PG_NOTE", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Note
        {
            get { return this._pg_note; }
            set { this._pg_note = value; }
        }

        [DataField("PG_NRGIORNI", Type = DbType.Decimal)]
        public int? Pg_Nrgiorni
        {
            get { return this._pg_nrgiorni; }
            set { this._pg_nrgiorni = value; }
        }

        [isRequired]
        [DataField("PG_FKIDAOO", Type = DbType.Decimal)]
        public int? Pg_Fkidaoo
        {
            get { return this._pg_fkidaoo; }
            set { this._pg_fkidaoo = value; }
        }

        [DataField("PG_RIFREGEMERGENZA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Pg_Rifregemergenza
        {
            get { return this._pg_rifregemergenza; }
            set { this._pg_rifregemergenza = value; }
        }

        [DataField("PG_FLAGPRIVACY", Type = DbType.Decimal)]
        public int? Pg_Flagprivacy
        {
            get { return this._pg_flagprivacy; }
            set { this._pg_flagprivacy = value; }
        }

        [DataField("PG_FK_ESID", Type = DbType.Decimal)]
        public int? Pg_FkEsid
        {
            get { return this._pg_fk_esid; }
            set { this._pg_fk_esid = value; }
        }

        [DataField("PG_CHIUSURA", Type = DbType.Decimal)]
        public int? Pg_Chiusura
        {
            get { return this._pg_chiusura; }
            set { this._pg_chiusura = value; }
        }

        [DataField("PG_DATACHIUSURA", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Datachiusura
        {
            get { return this._pg_datachiusura; }
            set { this._pg_datachiusura = value; }
        }

        [DataField("PG_PREVRISPOSTA", Type = DbType.Decimal)]
        public int? Pg_Prevrisposta
        {
            get { return this._pg_prevrisposta; }
            set { this._pg_prevrisposta = value; }
        }

        [DataField("PG_NOTEPREVRISPOSTA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_Noteprevrisposta
        {
            get { return this._pg_noteprevrisposta; }
            set { this._pg_noteprevrisposta = value; }
        }

        [DataField("PG_INDIRIZZOMITTENTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoMittente
        {
            get { return this._pg_indirizzoMittente; }
            set { this._pg_indirizzoMittente = value; }
        }

        [DataField("PG_INDIRIZZOPERCONTODI", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoPerContoDi
        {
            get { return this._pg_indirizzoPerContoDi; }
            set { this._pg_indirizzoPerContoDi = value; }
        }

        [DataField("PG_INDIRIZZODESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoDestinatario
        {
            get { return this._pg_indirizzoDestinatario; }
            set { this._pg_indirizzoDestinatario = value; }
        }

        #endregion

        #region Arraylist per gli inserimenti nelle tabelle collegate

        private List<ProtAllegatiProtocollo> _protAllegati = new List<ProtAllegatiProtocollo>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AD_DLID")]
        public List<ProtAllegatiProtocollo> ProtAllegati
        {
            get { return this._protAllegati; }
            set { this._protAllegati = value; }
        }

        private List<ProtAltriDestinatari> _protAltriDest = new List<ProtAltriDestinatari>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AD_FKIDPROTOCOLLO")]
        public List<ProtAltriDestinatari> ProtAltriDest
        {
            get { return this._protAltriDest; }
            set { this._protAltriDest = value; }
        }

        private List<ProtAssegnazioni> _protAssegnazioni = new List<ProtAssegnazioni>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AS_FKIDPROTOCOLLO")]
        public List<ProtAssegnazioni> ProtAssegnazioni
        {
            get { return this._protAssegnazioni; }
            set { this._protAssegnazioni = value; }
        }
        #endregion

        #endregion
    }
}
