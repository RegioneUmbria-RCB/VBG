using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class OCausaliRiduzioniRMgr
    {
        public override void RegisterHandlers()
        {
            this.Deleting += new DeletingDelegate(this.OCausaliRiduzioniRMgr_Deleting);
        }

        #region gestione della cancellazione
        private void OCausaliRiduzioniRMgr_Deleting(OCausaliRiduzioniR cls)
        {
            this.VerificaRecordCollegati(cls);
        }

        private void VerificaRecordCollegati(OCausaliRiduzioniR cls)
        {
            var whereParams = new List<KeyValuePair<string, string>>
            {
                new KeyValuePair<string, string>("IDCOMUNE", cls.Idcomune),
                new KeyValuePair<string, string>("fk_ocrr_id", cls.Id.ToString())
            };

            if (this.recordCount("O_ICALCOLOCONTRIBR_RIDUZ", "fk_ocrr_id", whereParams) > 0)
                throw new ReferentialIntegrityException("O_ICALCOLOCONTRIBR_RIDUZ");
        }
        #endregion

        public List<OCausaliRiduzioniR> GetListByCausaliRiduzioniT(string idComune, int fkOcrtId)
        {
            var filtro = new OCausaliRiduzioniR();
            filtro.Idcomune = idComune;
            filtro.FkOcrtId = fkOcrtId;
            filtro.OrderBy = "Descrizione";

            return this.GetList(filtro);
        }

        internal IEnumerable<OCausaliRiduzioniR> GetListByIdTipoCausale(string idComune, int idTipoCausale)
        {
            FormattableString sql = $"SELECT * FROM O_CAUSALIRIDUZIONIR WHERE IDCOMUNE = {idComune} AND FK_OCRT_ID = {idTipoCausale} ORDER BY DESCRIZIONE";

            return this.db.GetClassList<OCausaliRiduzioniR>(sql);
        }
    }
}
