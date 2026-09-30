using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    public class IstanzeAffissioniMgr : BaseManager
    {

        public IstanzeAffissioniMgr(DataBase dataBase) : base(dataBase) { }


        public IstanzeAffissioni GetById(string IdComune, int CodiceIstanza, int Id)
        {
            IstanzeAffissioni retVal = new IstanzeAffissioni();

            retVal.IDCOMUNE = IdComune;
            retVal.CODICEISTANZA = CodiceIstanza.ToString();
            retVal.ID = Id.ToString();

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public void Delete(IstanzeAffissioni p_class)
        {
            this.EffettuaCancellazioneACascata(p_class);

            this.db.Delete(p_class);
        }

        private void EffettuaCancellazioneACascata(IstanzeAffissioni cls)
        {

            #region ISTANZEAFFISSIONIASSEGNAZIONI
            IstanzeAffissioniAssegnazioni ist_aff = new IstanzeAffissioniAssegnazioni();
            ist_aff.IDCOMUNE = cls.IDCOMUNE;
            ist_aff.CODICEISTANZA = cls.CODICEISTANZA;

            List<IstanzeAffissioniAssegnazioni> lAffissioni = new IstanzeAffissioniAssegnazioniMgr(this.db).GetList(ist_aff);
            foreach (IstanzeAffissioniAssegnazioni affissione in lAffissioni)
            {
                IstanzeAffissioniAssegnazioniMgr mgr = new IstanzeAffissioniAssegnazioniMgr(this.db);
                mgr.Delete(affissione);
            }
            #endregion
        }

        public IstanzeAffissioni Insert(IstanzeAffissioni p_class)
        {

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        private void Validate(IstanzeAffissioni p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);

        }

        private void ForeignValidate(IstanzeAffissioni p_class)
        {
            #region ISTANZEAFFISSIONE.CODICEISTANZA
            if (!String.IsNullOrEmpty(p_class.CODICEISTANZA))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IDCOMUNE),
                    new KeyValuePair<string, string>("CODICEISTANZA", p_class.CODICEISTANZA)
                };

                if (this.recordCount("ISTANZE", "CODICEISTANZA", conditions) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEAFFISSIONE.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion
        }

    }
}