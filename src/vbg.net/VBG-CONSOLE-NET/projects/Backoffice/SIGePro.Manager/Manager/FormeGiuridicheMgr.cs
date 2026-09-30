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
        public FormeGiuridiche GetByCodiceCciaa(string idComune, string codiceCciaa)
        {
            FormattableString sql = $"SELECT * FROM FORMEGIURIDICHE WHERE CODICECCIAA = {codiceCciaa} AND IDCOMUNE = {idComune}";

            return this.db.GetClassList<FormeGiuridiche>(sql).FirstOrDefault();
        }

        #region Metodi per l'accesso di base al DB


        public FormeGiuridiche GetById(String pCODICEFORMAGIURIDICA, String pIDCOMUNE)
        {
            FormeGiuridiche retVal = new FormeGiuridiche();

            retVal.CODICEFORMAGIURIDICA = pCODICEFORMAGIURIDICA;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<FormeGiuridiche> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as FormeGiuridiche;

            return null;
        }

        public FormeGiuridiche GetByClass(FormeGiuridiche cls)
        {
            List<FormeGiuridiche> mydc = this.db.GetClassList(cls, true);
            if (mydc.Count != 0)
                return (mydc[0]) as FormeGiuridiche;

            return null;
        }

        /// <summary>
        /// Verifica se si può cancellare la forma giuridica
        /// </summary>
        /// <param name="cls"></param>
        private void VerificaRecordCollegati(FormeGiuridiche cls)
        {
            if (this.recordCount("ANAGRAFE", "FORMAGIURIDICA", "where IDCOMUNE = '" + cls.IDCOMUNE + "' and FORMAGIURIDICA = " + cls.FORMAGIURIDICA) > 0)
                throw new ReferentialIntegrityException("ANAGRAFE");
        }

        #endregion
    }
}
