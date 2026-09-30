
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
    /// File generato automaticamente dalla tabella TIPI_SCADENZA il 26/11/2013 16.48.04
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
    [DataTable("ISTANZEPROCURE")]
    [Serializable]
    public partial class IstanzeProcure : BaseDataClass
    {
        #region Membri privati

        private int? _id = null;

        private string _idcomune = null;

        private int? _codiceIstanza = null;

        private int? _fkAnagProc = null;

        private int? _fkAnagProcStorico = null;

        private int? _fkAnagRapp = null;

        private int? _fkAnagRappStorico = null;

        private int? _codiceOggettoProcura = null;

        private string _stcIdAllegato = null;

        private string _stcIdDocumento = null;

        #endregion

        #region properties

        #region Key Fields

        [KeyField("IDCOMUNE", Type = DbType.String)]
        public string IdComune
        {
            get { return _idcomune; }
            set { _idcomune = value; }
        }

        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get { return _id; }
            set { _id = value; }
        }

        #endregion

        #region Data fields

        [DataField("CODICEISTANZA", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        public int? CodiceIstanza
        {
            get { return _codiceIstanza; }
            set { _codiceIstanza = value; }
        }

        [DataField("FK_ANAG_PROC", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        public int? FkAnagProc
        {
            get { return _fkAnagProc; }
            set { _fkAnagProc = value; }
        }

        [DataField("FK_ANAG_PROC_STORICO", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        public int? FkAnagProcStorico
        {
            get { return _fkAnagProcStorico; }
            set { _fkAnagProcStorico = value; }
        }

        [DataField("FK_ANAG_RAPP", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        public int? FkAnagRapp
        {
            get { return _fkAnagRapp; }
            set { _codiceIstanza = value; }
        }


        [DataField("FK_ANAG_RAPP_STORICO", Type = DbType.Decimal, CaseSensitive = false, Size = 6)]
        public int? FkAnagRappStorico
        {
            get { return _fkAnagRappStorico; }
            set { _fkAnagRappStorico = value; }
        }

        [DataField("CODICEOGGETTOPROCURA", Type = DbType.Decimal, CaseSensitive = false, Size = 10)]
        public int? CodiceOggettoProcura
        {
            get { return _codiceOggettoProcura; }
            set { _codiceOggettoProcura = value; }
        }


        [DataField("STC_ID_ALLEGATO", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string StcIdAllegato
        {
            get { return _stcIdAllegato; }
            set { _stcIdAllegato = value; }
        }


        [DataField("STC_ID_DOCUMENTO", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string StcIdDocumento
        {
            get { return _stcIdDocumento; }
            set { _stcIdDocumento = value; }
        }


        #endregion

        #endregion
    }
}
