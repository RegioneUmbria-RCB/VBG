
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
    /// File generato automaticamente dalla tabella ALBEROPROC_ENDO il 09/11/2018 11.03.00
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
    [DataTable("ALBEROPROC_ENDO")]
    [Serializable]
    public partial class AlberoProcEndo : BaseDataClass
    {
        #region properties

        #region Key Fields

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [isRequired]
        [KeyField("FKSCID", Type = DbType.Decimal)]
        public int? FkScid { get; set; }

        [isRequired]
        [KeyField("CODICEINVENTARIO", Type = DbType.Decimal)]
        public int? CodiceInventario { get; set; }

        #endregion

        #region Data fields

        [DataField("FKAZID", Type = DbType.Decimal)]
        public int? FkAzid { get; set; }

        [isRequired]
        [DataField("FLAG_RICHIESTO", Type = DbType.Decimal)]
        public int? FlagRichiedente { get; set; }

        [DataField("FLAG_PRINCIPALE", Type = DbType.Decimal)]
        public int? FlagPrincipale { get; set; }

        [DataField("FLAG_PUBBLICA", Type = DbType.Decimal)]
        public int? FlagPubblica { get; set; }

        [DataField("FLAG_RICHIESTO_BO", Type = DbType.Decimal)]
        public int? FlagRichiestaBo { get; set; }

        #endregion

        #endregion
    }
}
