using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per FormeGiuridicheMgr.\n	/// </summary>
    public partial class FormeGiuridicheMgr : BaseManager
    {

        #region Metodi per l'accesso di base al DB

        public FormeGiuridiche? GetByCodiceCciaa(string idComune, string codiceCciaa)
        {
            FormattableString sql = $"SELECT * FROM FORMEGIURIDICHE WHERE CODICECCIAA = {codiceCciaa} AND IDCOMUNE = {idComune}";

            return this.db.GetClassList<FormeGiuridiche>(sql).FirstOrDefault();
        }

        public FormeGiuridiche GetById(String pCODICEFORMAGIURIDICA, String pIDCOMUNE)
        {
            var retVal = new FormeGiuridiche();

            retVal.CODICEFORMAGIURIDICA = pCODICEFORMAGIURIDICA;
            retVal.IDCOMUNE = pIDCOMUNE;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public FormeGiuridiche GetByClass(FormeGiuridiche cls)
        {
            var mydc = this.db.GetClassList(cls, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        /// <summary>
        /// Verifica se si può cancellare la forma giuridica
        /// </summary>
        /// <param name="cls"></param>
        private void VerificaRecordCollegati(FormeGiuridiche cls)
        {
            var conditions = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.IDCOMUNE),
                new KeyValuePair<string, string>("FORMAGIURIDICA", cls.FORMAGIURIDICA)
            };

            if (this.recordCount("ANAGRAFE", "FORMAGIURIDICA", conditions) > 0)
                throw new ReferentialIntegrityException("ANAGRAFE");
        }

        #endregion
    }
}
