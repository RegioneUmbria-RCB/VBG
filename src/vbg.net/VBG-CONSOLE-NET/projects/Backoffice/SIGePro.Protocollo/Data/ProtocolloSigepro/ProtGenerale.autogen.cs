
using System;
using System.Data;
using System.Reflection;
using System.Text;
using Init.SIGePro.Attributes;
using Init.SIGePro.Collection;
using PersonalLib2.Sql.Attributes;
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace Init.SIGePro.Data
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
            get { return _pg_id; }
            set { _pg_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return _idcomune; }
            set { _idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("PG_NUMERO", Type = DbType.Decimal)]
        public int? Pg_Numero
        {
            get { return _pg_numero; }
            set { _pg_numero = value; }
        }

        [DataField("PG_ANNO", Type = DbType.Decimal)]
        public int? Pg_Anno
        {
            get { return _pg_anno; }
            set { _pg_anno = value; }
        }

        [DataField("PG_DATAREGISTRAZIONE", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataregistrazione
        {
            get { return _pg_dataregistrazione; }
            set { _pg_dataregistrazione = value; }
        }

        [DataField("PG_ORAREGISTRAZIONE", Type = DbType.String, CaseSensitive = false, Size = 6)]
        public string Pg_Oraregistrazione
        {
            get { return _pg_oraregistrazione; }
            set { _pg_oraregistrazione = value; }
        }

        [DataField("PG_OGGETTO", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Oggetto
        {
            get { return _pg_oggetto; }
            set { _pg_oggetto = value; }
        }

        [DataField("PG_FKIDMITTENTE", Type = DbType.Decimal)]
        public int? Pg_Fkidmittente
        {
            get { return _pg_fkidmittente; }
            set { _pg_fkidmittente = value; }
        }

        [DataField("PG_IMPRONTA", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Pg_Impronta
        {
            get { return _pg_impronta; }
            set { _pg_impronta = value; }
        }

        [DataField("PG_NUMEROINGRESSO", Type = DbType.Decimal)]
        public int? Pg_Numeroingresso
        {
            get { return _pg_numeroingresso; }
            set { _pg_numeroingresso = value; }
        }

        [DataField("PG_DATAINGRESSO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataingresso
        {
            get { return _pg_dataingresso; }
            set { _pg_dataingresso = value; }
        }

        [DataField("PG_ANNULLATO", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string Pg_Annullato
        {
            get { return _pg_annullato; }
            set { _pg_annullato = value; }
        }

        [DataField("PG_FKIDMOTIVOANNULLAMENTO", Type = DbType.Decimal)]
        public int? Pg_Fkidmotivoannullamento
        {
            get { return _pg_fkidmotivoannullamento; }
            set { _pg_fkidmotivoannullamento = value; }
        }

        [DataField("PG_NOTEANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Noteannullamento
        {
            get { return _pg_noteannullamento; }
            set { _pg_noteannullamento = value; }
        }

        [DataField("PG_FKIDFASCICOLO", Type = DbType.Decimal)]
        public int? Pg_Fkidfascicolo
        {
            get { return _pg_fkidfascicolo; }
            set { _pg_fkidfascicolo = value; }
        }

        [isRequired]
        [DataField("PG_FKIDTIPOLOGIA", Type = DbType.Decimal)]
        public int? Pg_Fkidtipologia
        {
            get { return _pg_fkidtipologia; }
            set { _pg_fkidtipologia = value; }
        }

        [DataField("PG_FKIDMODALITA", Type = DbType.Decimal)]
        public int? Pg_Fkidmodalita
        {
            get { return _pg_fkidmodalita; }
            set { _pg_fkidmodalita = value; }
        }

        [DataField("PG_FKIDCLASSIFICAZIONE", Type = DbType.Decimal)]
        public int? Pg_Fkidclassificazione
        {
            get { return _pg_fkidclassificazione; }
            set { _pg_fkidclassificazione = value; }
        }

        [DataField("PG_FKIDDESTINATARIO", Type = DbType.Decimal)]
        public int? Pg_Fkiddestinatario
        {
            get { return _pg_fkiddestinatario; }
            set { _pg_fkiddestinatario = value; }
        }

        [DataField("PG_DATAANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Dataannullamento
        {
            get { return _pg_dataannullamento; }
            set { _pg_dataannullamento = value; }
        }

        [DataField("PG_ORAANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 4)]
        public string Pg_Oraannullamento
        {
            get { return _pg_oraannullamento; }
            set { _pg_oraannullamento = value; }
        }

        [DataField("PG_FKUTENTEANNULLAMENTO", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Pg_Fkutenteannullamento
        {
            get { return _pg_fkutenteannullamento; }
            set { _pg_fkutenteannullamento = value; }
        }

        [DataField("PG_FKUTENTEINSERIMENTO", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Pg_Fkutenteinserimento
        {
            get { return _pg_fkutenteinserimento; }
            set { _pg_fkutenteinserimento = value; }
        }

        [DataField("PG_MITTENTE", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Mittente
        {
            get { return _pg_mittente; }
            set { _pg_mittente = value; }
        }

        [DataField("PG_DESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Destinatario
        {
            get { return _pg_destinatario; }
            set { _pg_destinatario = value; }
        }

        [DataField("PG_FKIDPROTOCOLLO", Type = DbType.Decimal)]
        public int? Pg_Fkidprotocollo
        {
            get { return _pg_fkidprotocollo; }
            set { _pg_fkidprotocollo = value; }
        }

        [DataField("PG_FKIDPERCONTODI", Type = DbType.Decimal)]
        public int? Pg_Fkidpercontodi
        {
            get { return _pg_fkidpercontodi; }
            set { _pg_fkidpercontodi = value; }
        }

        [DataField("PG_PERCONTODI", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Pg_Percontodi
        {
            get { return _pg_percontodi; }
            set { _pg_percontodi = value; }
        }

        [DataField("PG_OGID", Type = DbType.Decimal)]
        public int? Pg_Ogid
        {
            get { return _pg_ogid; }
            set { _pg_ogid = value; }
        }

        [DataField("PG_NOTE", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        public string Pg_Note
        {
            get { return _pg_note; }
            set { _pg_note = value; }
        }

        [DataField("PG_NRGIORNI", Type = DbType.Decimal)]
        public int? Pg_Nrgiorni
        {
            get { return _pg_nrgiorni; }
            set { _pg_nrgiorni = value; }
        }

        [isRequired]
        [DataField("PG_FKIDAOO", Type = DbType.Decimal)]
        public int? Pg_Fkidaoo
        {
            get { return _pg_fkidaoo; }
            set { _pg_fkidaoo = value; }
        }

        [DataField("PG_RIFREGEMERGENZA", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Pg_Rifregemergenza
        {
            get { return _pg_rifregemergenza; }
            set { _pg_rifregemergenza = value; }
        }

        [DataField("PG_FLAGPRIVACY", Type = DbType.Decimal)]
        public int? Pg_Flagprivacy
        {
            get { return _pg_flagprivacy; }
            set { _pg_flagprivacy = value; }
        }

        [DataField("PG_FK_ESID", Type = DbType.Decimal)]
        public int? Pg_FkEsid
        {
            get { return _pg_fk_esid; }
            set { _pg_fk_esid = value; }
        }

        [DataField("PG_CHIUSURA", Type = DbType.Decimal)]
        public int? Pg_Chiusura
        {
            get { return _pg_chiusura; }
            set { _pg_chiusura = value; }
        }

        [DataField("PG_DATACHIUSURA", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Pg_Datachiusura
        {
            get { return _pg_datachiusura; }
            set { _pg_datachiusura = value; }
        }

        [DataField("PG_PREVRISPOSTA", Type = DbType.Decimal)]
        public int? Pg_Prevrisposta
        {
            get { return _pg_prevrisposta; }
            set { _pg_prevrisposta = value; }
        }

        [DataField("PG_NOTEPREVRISPOSTA", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_Noteprevrisposta
        {
            get { return _pg_noteprevrisposta; }
            set { _pg_noteprevrisposta = value; }
        }

        [DataField("PG_INDIRIZZOMITTENTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoMittente
        {
            get { return _pg_indirizzoMittente; }
            set { _pg_indirizzoMittente = value; }
        }

        [DataField("PG_INDIRIZZOPERCONTODI", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoPerContoDi
        {
            get { return _pg_indirizzoPerContoDi; }
            set { _pg_indirizzoPerContoDi = value; }
        }

        [DataField("PG_INDIRIZZODESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Pg_IndirizzoDestinatario
        {
            get { return _pg_indirizzoDestinatario; }
            set { _pg_indirizzoDestinatario = value; }
        }

        #endregion

        #region Arraylist per gli inserimenti nelle tabelle collegate

        List<ProtAllegatiProtocollo> _protAllegati = new List<ProtAllegatiProtocollo>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AD_DLID")]
        public List<ProtAllegatiProtocollo> ProtAllegati
        {
            get { return _protAllegati; }
            set { _protAllegati = value; }
        }

        List<ProtAltriDestinatari> _protAltriDest = new List<ProtAltriDestinatari>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AD_FKIDPROTOCOLLO")]
        public List<ProtAltriDestinatari> ProtAltriDest
        {
            get { return _protAltriDest; }
            set { _protAltriDest = value; }
        }

        List<ProtAssegnazioni> _protAssegnazioni = new List<ProtAssegnazioni>();
        [ForeignKey("IDCOMUNE,PG_ID", "IDCOMUNE,AS_FKIDPROTOCOLLO")]
        public List<ProtAssegnazioni> ProtAssegnazioni
        {
            get { return _protAssegnazioni; }
            set { _protAssegnazioni = value; }
        }
        #endregion

        #endregion
    }
}
