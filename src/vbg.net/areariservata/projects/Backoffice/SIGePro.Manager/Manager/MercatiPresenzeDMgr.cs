using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using Init.SIGePro.Exceptions;
using Init.Utils.Sorting;

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

            if( minPresenze > int.MinValue )
                cmdText += " and presenze.totale >= " + minPresenze.ToString();

            db.Connection.Open();

            using (IDbCommand cmd = db.CreateCommand(cmdText))
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

            db.Connection.Close();

            return retVal;
        }

        public MercatiPresenzeD GetByClass( MercatiPresenzeD c )
        {
            return (MercatiPresenzeD)db.GetClass(c);
        }

        public MercatiPresenzeD Insert(MercatiPresenzeD cls, bool ExistingRecordException )
        {
            if (ExistingRecordException)
            {
                MercatiPresenzeD m = ( cls.Clone() as MercatiPresenzeD );
                m.Spuntista = null;
                m = GetByClass(m);

                if (m != null)
                    throw new RecordFoundedException("Il record è già presente nel database");
            }
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (MercatiPresenzeD)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public List<MercatiPresenzeD> GetList(MercatiPresenzeD filtro)
        {
            return GetList(filtro, null);
        }

        public List<MercatiPresenzeD> GetList(MercatiPresenzeD filtro, string sortExpression)
        {
            List<MercatiPresenzeD> retVal = db.GetClassList(filtro).ToList<MercatiPresenzeD>();
            if (!String.IsNullOrEmpty(sortExpression))
                ListSortManager<MercatiPresenzeD>.Sort(retVal, sortExpression);

            return retVal;
        }

    }

    public class Spuntisti
    {
        int? m_codiceanagrafe = null;
        public int? CodiceAnagrafe
        {
            get { return m_codiceanagrafe; }
            set { m_codiceanagrafe = value; }
        }

        string m_nominativo = null;
        public string Nominativo
        {
            get { return m_nominativo; }
            set { m_nominativo = value; }
        }

        int m_presenze = 0;
        public int Presenze
        {
            get { return m_presenze; }
            set { m_presenze = value; }
        }
    }
}
