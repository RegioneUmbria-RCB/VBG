using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class MercatiPresenzeTMgr
    {
        [DataObjectMethod(DataObjectMethodType.Select)]
        public static List<MercatiPresenzeT> Find(string token, string software, DateTime dataDa, DateTime dataA, int codicemercato, int codiceuso, string descrizione, string sortExpression)
        {
            AuthenticationInfo authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);
            MercatiPresenzeTMgr mgr = new MercatiPresenzeTMgr(authInfo.CreateDatabase());

            MercatiPresenzeT filtro = new MercatiPresenzeT();
            MercatiPresenzeT filtroCompare = new MercatiPresenzeT();

            filtro.Software = software;
            filtro.Idcomune = authInfo.IdComune;
            filtro.Descrizione = descrizione;
            if (codicemercato > 0)
            {
                filtro.Fkcodicemercato = codicemercato;
            }
            else
            {
                filtro.Fkcodicemercato = null;
            }
            if (codiceuso > 0)
            {
                filtro.Fkidmercatiuso = codiceuso;
            }
            else
            {
                filtro.Fkidmercatiuso = null;
            }

            if (dataDa > DateTime.MinValue)
                filtro.OthersWhereClause.Add("DATAREGISTRAZIONE >= TO_DATE('" + dataDa.ToString("dd/MM/yyyy") + "','DD/MM/YYYY')");
            if (dataA > DateTime.MinValue)
                filtro.OthersWhereClause.Add("DATAREGISTRAZIONE <= TO_DATE('" + dataA.ToString("dd/MM/yyyy") + "','DD/MM/YYYY')");

            filtro.UseForeign = useForeignEnum.Yes;

            filtroCompare.Descrizione = "LIKE";

            List<MercatiPresenzeT> list = authInfo.CreateDatabase().GetClassList(filtro, filtroCompare).ToList<MercatiPresenzeT>();

            ListSortManager<MercatiPresenzeT>.Sort(list, sortExpression);

            return list;
        }

        public void AssegnaPresenzaOccupanti(MercatiPresenzeT cls)
        {
            Mercati_D filtro = new Mercati_D();
            filtro.Disabilitato = "0";
            filtro.FkCodiceMercato = cls.Fkcodicemercato;
            filtro.IdComune = cls.Idcomune;
            filtro.Disabilitato = "0";

            List<Mercati_D> mercato = new Mercati_DMgr(this.db).GetList(filtro);
            foreach (Mercati_D posteggio in mercato)
            {
                Mercati_DMgr icMgr = new Mercati_DMgr(this.db);
                List<Occupanti> occupanti = icMgr.GetOccupanti(posteggio.IdComune, posteggio.FkCodiceMercato.Value, posteggio.IdPosteggio.Value, cls.Fkidmercatiuso.Value);

                if (occupanti.Count > 1)
                    throw new MoreThanOneRecordException("Sono stati trovati più concessionari attivi per lo stesso mercato, lo stesso posteggio e lo stesso giorno");

                if (occupanti.Count == 1)
                {
                    MercatiPresenzeD presenza = new MercatiPresenzeD();
                    presenza.Codiceanagrafe = occupanti[0].CodiceAnagrafe.ToString();
                    presenza.Fkidposteggio = Convert.ToInt32(posteggio.IdPosteggio);
                    presenza.Fkidtestata = cls.Id;
                    presenza.Idcomune = posteggio.IdComune;
                    presenza.Numeropresenze = 1;
                    presenza.Spuntista = 0;

                    MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(this.db);
                    mgr.Insert(presenza, true);
                }
            }
        }

        private void EffettuaCancellazioneACascata(MercatiPresenzeT cls)
        {
            MercatiPresenzeD a = new MercatiPresenzeD();
            a.Idcomune = cls.Idcomune;
            a.Fkidtestata = cls.Id;


            List<MercatiPresenzeD> lMercatiD = new MercatiPresenzeDMgr(this.db).GetList(a);
            foreach (MercatiPresenzeD presenza in lMercatiD)
            {
                MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(this.db);
                mgr.Delete(presenza);
            }
        }

        public MercatiPresenzeT GetById(string idcomune, int id)
        {
            return this.GetById(idcomune, id, useForeignEnum.No);
        }

        public MercatiPresenzeT GetById(string idcomune, int id, useForeignEnum UseForeign)
        {
            MercatiPresenzeT c = new MercatiPresenzeT();

            c.Idcomune = idcomune;
            c.Id = id;
            c.UseForeign = UseForeign;
            return this.db.GetClass(c);
        }

        public List<MercatiPresenzeT> GetByCodiceMercato(string idcomune, int fkcodicemercato)
        {
            MercatiPresenzeT mp = new MercatiPresenzeT();

            mp.Idcomune = idcomune;
            mp.Fkcodicemercato = fkcodicemercato;

            List<MercatiPresenzeT> list = this.db.GetClassList(mp).ToList<MercatiPresenzeT>();
            return list;
        }

        public int DeleteDettagli(string idComune, int idTestata)
        {
            string cmdText = "delete from mercatipresenze_d where idcomune = '" + idComune + "' and fkidtestata = " + idTestata.ToString();
            using (IDbCommand cmd = this.db.CreateCommand(cmdText))
            {
                return cmd.ExecuteNonQuery();
            }
        }

        public void InsertDettagli(List<MercatiPresenzeD> arCls)
        {
            foreach (MercatiPresenzeD presenza in arCls)
            {
                MercatiPresenzeDMgr mgr = new MercatiPresenzeDMgr(this.db);
                mgr.Insert(presenza, true);
            }
        }
    }
}
