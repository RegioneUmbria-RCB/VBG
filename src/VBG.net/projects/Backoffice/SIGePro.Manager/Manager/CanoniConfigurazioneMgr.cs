using System;
using System.Collections.Generic;
using System.Text;
using System.ComponentModel;
using Init.SIGePro.Data;
using Init.SIGePro.Authentication;
using PersonalLib2.Sql;
using Init.Utils.Sorting;
using Init.SIGePro.Exceptions;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class CanoniConfigurazioneMgr
    {
        public CanoniConfigurazione GetById(string idcomune, int anno)
        {
            return GetById(idcomune, anno, useForeignEnum.No);
        }

        public CanoniConfigurazione GetById(string idcomune, int anno, useForeignEnum UseForeign)
        {
            CanoniConfigurazione c = new CanoniConfigurazione();

            c.Idcomune = idcomune;
            c.Anno = anno;
            c.UseForeign = UseForeign;

            return (CanoniConfigurazione)db.GetClass(c);
        }

        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<CanoniConfigurazione> Find(string token, string software, string sortExpression)
        {
            AuthenticationInfo authInfo = AuthenticationManager.CheckToken(token);

            CanoniConfigurazione filtro = new CanoniConfigurazione();
            
            filtro.Idcomune = authInfo.IdComune;
            filtro.Software = software;

            // gestione ordinamento
            List<CanoniConfigurazione> list = authInfo.CreateDatabase().GetClassList(filtro, false, true).ToList<CanoniConfigurazione>();
            ListSortManager<CanoniConfigurazione>.Sort(list, sortExpression);
            // fine gestione ordinamento
            return list;

        }

        private void ForeignValidate(CanoniConfigurazione p_class)
        {
            #region CANONI_CONFIGURAZIONE.FK_COID
            if (p_class.FkCoId.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("TIPICAUSALIONERI", "CO_ID", "WHERE IDCOMUNE = '" + p_class.Idcomune + "' AND CO_ID = " + p_class.FkCoId.Value.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("CANONI_CONFIGURAZIONE.FK_COID (" + p_class.FkCoId.Value.ToString() + ") non trovato nella tabella TIPICAUSALIONERI"));
                }
            }
            #endregion

            #region CANONI_CONFIGURAZIONE.FK_COID_ADDIZCOMUNALE
            if (p_class.FkCoIdAddizComunale.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("TIPICAUSALIONERI", "CO_ID", "WHERE IDCOMUNE = '" + p_class.Idcomune + "' AND CO_ID = " + p_class.FkCoIdAddizComunale.Value.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("CANONI_CONFIGURAZIONE.FK_COID_ADDIZCOMUNALE (" + p_class.FkCoIdAddizComunale.Value.ToString() + ") non trovato nella tabella TIPICAUSALIONERI"));
                }
            }
            #endregion

            #region CANONI_CONFIGURAZIONE.FK_COID_TOTALE
            if (p_class.FkCoIdTotale.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                if (this.recordCount("TIPICAUSALIONERI", "CO_ID", "WHERE IDCOMUNE = '" + p_class.Idcomune + "' AND CO_ID = " + p_class.FkCoIdTotale.Value.ToString()) == 0)
                {
                    throw (new RecordNotfoundException("CANONI_CONFIGURAZIONE.FK_COID_TOTALE (" + p_class.FkCoIdTotale.Value.ToString() + ") non trovato nella tabella TIPICAUSALIONERI"));
                }
            }
            #endregion

       
        }

        private void VerificaRecordCollegati(CanoniConfigurazione cls)
        {
            IstanzeCalcoloCanoniT filtro = new IstanzeCalcoloCanoniT();
            filtro.Idcomune = cls.Idcomune;
            filtro.Anno = cls.Anno;

            List<IstanzeCalcoloCanoniT> a = new IstanzeCalcoloCanoniTMgr( db ).GetList( filtro );
            if (a.Count > 0)
                throw new ReferentialIntegrityException("ISTANZECALCOLOCANONI_T");
        }

        private void EffettuaCancellazioneACascata(CanoniConfigurazione cls)
        {
            CanoniCoefficienti cc = new CanoniCoefficienti();
            cc.Idcomune = cls.Idcomune;
            cc.Anno = cls.Anno;

            List<CanoniCoefficienti> listCc = new CanoniCoefficientiMgr(db).GetList(cc);
            foreach (CanoniCoefficienti a in listCc)
            {
                CanoniCoefficientiMgr mgr = new CanoniCoefficientiMgr(db);
                mgr.Delete(a);
            }


            PertinenzeCoefficienti pc = new PertinenzeCoefficienti();
            pc.Idcomune = cls.Idcomune;
            pc.Anno = cls.Anno;

            List<PertinenzeCoefficienti> listPc = new PertinenzeCoefficientiMgr(db).GetList(pc);
            foreach (PertinenzeCoefficienti b in listPc)
            {
                PertinenzeCoefficientiMgr mgr = new PertinenzeCoefficientiMgr(db);
                mgr.Delete(b);
            }
        }
    }
}
