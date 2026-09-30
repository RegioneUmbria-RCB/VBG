using System;
using System.Data;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{
    public partial class IstanzeOneri_CanoniMgr
    {
        public int VerificaIstanzeOneriCanoniCollegatiFromIstanza(string idComune, int codiceIstanza, int tipoCausale)
        {
            int retVal = 0;
            foreach (DataRow row in GetIstanzeOneriCanoniFromIstanza(idComune, codiceIstanza, tipoCausale).Tables[0].Rows)
            {
                DeleteIstanzeOneriCanoniCollegati(idComune,Convert.ToInt32(row["fk_idtestata"]), Convert.ToInt32(row["fk_id_istoneri"]));
                retVal = Convert.ToInt32(row["fk_idtestata"]);
            }

            return retVal;
        }

        private DataSet GetIstanzeOneriCanoniFromIstanza(string idComune, int codiceIstanza, int tipoCausale)
        {
            DataSet ds = new DataSet();
            string sql;


            sql = @"SELECT 
						  istanzeoneri_canoni.fk_idtestata,istanzeoneri_canoni.fk_id_istoneri
						FROM
						  istanzeoneri,istanzeoneri_canoni
						WHERE
						   istanzeoneri_canoni.idcomune = istanzeoneri.idcomune and
                           istanzeoneri_canoni.fk_id_istoneri = istanzeoneri.id and
                           istanzeoneri.idcomune = {0} and
                           istanzeoneri.codiceistanza = {1} and
                           istanzeoneri.fkidtipocausale = {2}";


            sql = String.Format(sql, db.Specifics.QueryParameterName("idcomune"),
                                     db.Specifics.QueryParameterName("codiceistanza"),
                                     db.Specifics.QueryParameterName("fkidtipocausale"));


            using (IDbCommand cmd = db.CreateCommand(sql))
            {
                cmd.Parameters.Add(db.CreateParameter("idcomune", idComune));
                cmd.Parameters.Add(db.CreateParameter("codiceistanza", codiceIstanza));
                cmd.Parameters.Add(db.CreateParameter("fkidtipocausale", tipoCausale));

                IDataAdapter da = db.CreateDataAdapter(cmd);
                da.Fill(ds);
            }

            return ds;
        }

        private void DeleteIstanzeOneriCanoniCollegati(string idComune, int fk_idtestata, int fk_id_istoneri)
        {
            IstanzeOneri_Canoni ioc = new IstanzeOneri_Canoni();
            ioc.Idcomune = idComune;
            ioc.FkIdIstoneri = fk_id_istoneri;
            ioc.FkIdtestata = fk_idtestata;
            Delete(ioc);
        }

        public int VerificaIstanzeOneriCanoniCollegatiFromOnere(string idComune, int codiceOnere)
        {
            int retVal = 0;
            IstanzeOneri_Canoni ioc = new IstanzeOneri_Canoni();
            ioc.Idcomune = idComune;
            ioc.FkIdIstoneri = codiceOnere;
            List<IstanzeOneri_Canoni> list = GetList(ioc);
            if ((list != null) && (list.Count != 0))
                retVal = list[0].FkIdtestata.Value;

            return retVal;
        }

        public void AggiornaIstanzeOneriCanoni(string idComune, int fk_idtestata, int fk_id_istoneri)
        {
            IstanzeOneri_Canoni ioc = new IstanzeOneri_Canoni();
            ioc.Idcomune = idComune;
            ioc.FkIdIstoneri = fk_id_istoneri;
            ioc.FkIdtestata = fk_idtestata;
            Insert(ioc);
        }
    }
}
