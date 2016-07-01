using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Validator;
using System.Collections.Generic;
using PersonalLib2.Sql.Collections;
using Init.SIGePro.Exceptions;

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

            DataClassCollection mydc = db.GetClassList(retVal, true, false);
            if (mydc.Count != 0)
                return (mydc[0]) as IstanzeAffissioni;

            return null;
        }


        public List<IstanzeAffissioni> GetList(IstanzeAffissioni p_class)
        {
            return this.GetList(p_class, null);
        }

        public List<IstanzeAffissioni> GetList(IstanzeAffissioni p_class, IstanzeAffissioni p_cmpClass)
        {
            return db.GetClassList(p_class, p_cmpClass, false, false).ToList<IstanzeAffissioni>();
        }


        public void Delete(IstanzeAffissioni p_class)
        {
            EffettuaCancellazioneACascata(p_class);

            db.Delete(p_class);
        }

        private void EffettuaCancellazioneACascata(IstanzeAffissioni cls)
        {

            #region ISTANZEAFFISSIONIASSEGNAZIONI
            IstanzeAffissioniAssegnazioni ist_aff = new IstanzeAffissioniAssegnazioni();
            ist_aff.IDCOMUNE = cls.IDCOMUNE;
            ist_aff.CODICEISTANZA = cls.CODICEISTANZA;

            List<IstanzeAffissioniAssegnazioni> lAffissioni = new IstanzeAffissioniAssegnazioniMgr(db).GetList(ist_aff);
            foreach (IstanzeAffissioniAssegnazioni affissione in lAffissioni)
            {
                IstanzeAffissioniAssegnazioniMgr mgr = new IstanzeAffissioniAssegnazioniMgr(db);
                mgr.Delete(affissione);
            }
            #endregion
        }

        public IstanzeAffissioni Insert(IstanzeAffissioni p_class)
        {

            Validate(p_class, AmbitoValidazione.Insert);

            db.Insert(p_class);

            return p_class;
        }

        private void Validate(IstanzeAffissioni p_class, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(p_class, ambitoValidazione);

            ForeignValidate(p_class);

        }

        private void ForeignValidate(IstanzeAffissioni p_class)
        {
            #region ISTANZEAFFISSIONE.CODICEISTANZA
            if (!IsStringEmpty(p_class.CODICEISTANZA))
            {
                if (this.recordCount("ISTANZE", "CODICEISTANZA", "WHERE IDCOMUNE = '" + p_class.IDCOMUNE + "' AND CODICEISTANZA = " + p_class.CODICEISTANZA) == 0)
                {
                    throw (new RecordNotfoundException("ISTANZEAFFISSIONE.CODICEISTANZA non trovato nella tabella ISTANZE"));
                }
            }
            #endregion
        }

    }
}