
using Init.SIGePro.Attributes;
using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigepro.Data
{
    ///
    /// File generato automaticamente dalla tabella PROT_ALTRIDESTINATARI il 09/01/2009 12.28.56
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
    [DataTable("PROT_ALTRIDESTINATARI")]
    [Serializable]
    public partial class ProtAltriDestinatari : BaseDataClass
    {
        #region Membri privati

        private int? _ad_id = null;

        private int? _ad_fkidanagrafe = null;

        private int? _ad_fkidprotocollo = null;

        private string _ad_dataassegnazione = null;

        private string _ad_oraassegnazione = null;

        private string _ad_utenteassegnazione = null;

        private string _ad_destinatario = null;

        private string _idcomune = null;

        private string _ad_indirizzo = null;

        #endregion

        #region properties

        #region Key Fields


        [KeyField("AD_ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Ad_Id
        {
            get { return this._ad_id; }
            set { this._ad_id = value; }
        }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune
        {
            get { return this._idcomune; }
            set { this._idcomune = value; }
        }


        #endregion

        #region Data fields

        [DataField("AD_FKIDANAGRAFE", Type = DbType.Decimal)]
        public int? Ad_Fkidanagrafe
        {
            get { return this._ad_fkidanagrafe; }
            set { this._ad_fkidanagrafe = value; }
        }

        [isRequired]
        [DataField("AD_FKIDPROTOCOLLO", Type = DbType.Decimal)]
        public int? Ad_Fkidprotocollo
        {
            get { return this._ad_fkidprotocollo; }
            set { this._ad_fkidprotocollo = value; }
        }

        [DataField("AD_DATAASSEGNAZIONE", Type = DbType.String, CaseSensitive = false, Size = 8)]
        public string Ad_Dataassegnazione
        {
            get { return this._ad_dataassegnazione; }
            set { this._ad_dataassegnazione = value; }
        }

        [DataField("AD_ORAASSEGNAZIONE", Type = DbType.String, CaseSensitive = false, Size = 4)]
        public string Ad_Oraassegnazione
        {
            get { return this._ad_oraassegnazione; }
            set { this._ad_oraassegnazione = value; }
        }

        [DataField("AD_UTENTEASSEGNAZIONE", Type = DbType.String, CaseSensitive = false, Size = 60)]
        public string Ad_Utenteassegnazione
        {
            get { return this._ad_utenteassegnazione; }
            set { this._ad_utenteassegnazione = value; }
        }

        [DataField("AD_DESTINATARIO", Type = DbType.String, CaseSensitive = false, Size = 150)]
        public string Ad_Destinatario
        {
            get { return this._ad_destinatario; }
            set { this._ad_destinatario = value; }
        }

        [DataField("AD_INDIRIZZO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Ad_Indirizzo
        {
            get { return this._ad_indirizzo; }
            set { this._ad_indirizzo = value; }
        }

        #endregion

        #endregion
    }
}
