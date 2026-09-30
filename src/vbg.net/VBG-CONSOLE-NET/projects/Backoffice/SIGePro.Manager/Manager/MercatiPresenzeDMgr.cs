using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Validator;
using Init.Utils.Sorting;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{
    public partial class MercatiPresenzeDMgr
    {
        public List<Spuntisti> GetSpuntisti(string idComune, string nominativo, int minPresenze)
        {
            List<Spuntisti> retVal = new List<Spuntisti>();

            string cmdText = "select " +
                                "anagrafe.codiceanagrafe, anagrafe.nominativo || ' ' || anagrafe.nome as spuntista, presenze.totale " +
                             "from " +
                                "anagrafe, (select codiceanagrafe, idcomune, sum(numeropresenze) as totale from mercatipresenze_d where mercatipresenze_d.idcomune = '" + idComune + "' group by codiceanagrafe, idcomune having sum(numeropresenze) >= " + minPresenze.ToString() + ") presenze " +
                             "where " +
                                "presenze.idcomune(+) = anagrafe.idcomune and " +
                                "presenze.codiceanagrafe(+) = anagrafe.codiceanagrafe and " +
                                "anagrafe.idcomune = '" + idComune + "' and " +
                                "anagrafe.flag_disabilitato = 0 ";

            if (!String.IsNullOrEmpty(nominativo))
                cmdText += " and upper(anagrafe.nominativo || ' ' || anagrafe.nome) like '%" + nominativo.ToUpper().Replace("'", "''") + "%'";

            if (minPresenze > int.MinValue)
                cmdText += " and presenze.totale >= " + minPresenze.ToString();

            this.db.Connection.Open();

            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                using (IDataReader rd = cmd.ExecuteReader())
                {
                    while (rd.Read())
                    {
                        Spuntisti s = new Spuntisti();
                        s.CodiceAnagrafe = Convert.ToInt32(rd["codiceanagrafe"].ToString());
                        s.Nominativo = rd["spuntista"].ToString();
                        if (rd["totale"] != DBNull.Value)
                            s.Presenze = Convert.ToInt32(rd["totale"].ToString());

                        retVal.Add(s);
                    }
                }
            }

            this.db.Connection.Close();

            return retVal;
        }

        public MercatiPresenzeD GetByClass(MercatiPresenzeD c)
        {
            return (MercatiPresenzeD)this.db.GetClass(c);
        }

        public MercatiPresenzeD Insert(MercatiPresenzeD cls, bool ExistingRecordException)
        {
            if (ExistingRecordException)
            {
                MercatiPresenzeD m = (cls.Clone() as MercatiPresenzeD);
                m.Spuntista = null;
                m = this.GetByClass(m);

                if (m != null)
                    throw new RecordFoundedException("Il record è già presente nel database");
            }
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            cls = (MercatiPresenzeD)this.ChildDataIntegrations(cls);

            this.ChildInsert(cls);

            return cls;
        }

        public List<MercatiPresenzeD> GetList(MercatiPresenzeD filtro)
        {
            return this.GetList(filtro, null);
        }

        public List<MercatiPresenzeD> GetList(MercatiPresenzeD filtro, string sortExpression)
        {
            List<MercatiPresenzeD> retVal = this.db.GetClassList(filtro);
            if (!String.IsNullOrEmpty(sortExpression))
                ListSortManager<MercatiPresenzeD>.Sort(retVal, sortExpression);

            return retVal;
        }

    }

    public class Spuntisti
    {
        private int? m_codiceanagrafe = null;
        public int? CodiceAnagrafe
        {
            get { return this.m_codiceanagrafe; }
            set { this.m_codiceanagrafe = value; }
        }

        private string m_nominativo = null;
        public string Nominativo
        {
            get { return this.m_nominativo; }
            set { this.m_nominativo = value; }
        }

        private int m_presenze = 0;
        public int Presenze
        {
            get { return this.m_presenze; }
            set { this.m_presenze = value; }
        }
    }
}
