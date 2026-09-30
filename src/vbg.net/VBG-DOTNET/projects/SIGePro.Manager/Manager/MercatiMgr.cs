using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager
{
    public class MercatiMgr : BaseManager
    {
        #region parametri per la configurazione di alcune procedure automatiche all'interno del manager
        //per default teniamo conto della configurazione di sigepro dal web.
        private int? _posteggiDaGenerare = null;
        #endregion

        public int? PosteggiDaGenerare
        {
            get { return this._posteggiDaGenerare; }
            set { this._posteggiDaGenerare = value; }
        }

        public MercatiMgr(DataBase dataBase) : base(dataBase) { }

        public Mercati GetById(String idComune, int codiceMercato)
        {
            Mercati retVal = new Mercati();
            retVal.CodiceMercato = codiceMercato;
            retVal.IdComune = idComune;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }

        public Mercati GetMercatoByIdPosteggio(string idComune, int idPosteggio)
        {
            var sql = $@"SELECT mercati.* FROM 
                          mercati 
                            INNER JOIN mercati_d ON
                              mercati_d.idcomune = mercati.idcomune AND
                              mercati_d.fkcodicemercato = mercati.codicemercato
                        WHERE
                          mercati_d.idcomune = {this.db.Specifics.QueryParameterName("idComune")} AND
                          mercati_d.idposteggio = {this.db.Specifics.QueryParameterName("idPosteggio")}";

            using (var cmd = this.db.CreateCommand(sql))
            {
                cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                cmd.Parameters.Add(this.db.CreateParameter("idPosteggio", idPosteggio));

                return this.db.GetClass<Mercati>(cmd);
            }
        }

        public void Delete(Mercati p_class)
        {
            this.db.Delete(p_class);
        }

        public Mercati Insert(Mercati p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            p_class = this.ChildDataIntegrations(p_class);

            this.ChildInsert(p_class);

            return p_class;
        }

        public Mercati Update(Mercati p_class)
        {
            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Update);

            this.db.Update(p_class);

            return p_class;
        }

        private Mercati DataIntegrations(Mercati p_class)
        {
            Mercati retVal = (Mercati)p_class.Clone();

            if (string.IsNullOrEmpty(retVal.Software))
                throw new RequiredFieldException("MERCATI.SOFTWARE obbligatorio");

            if (string.IsNullOrEmpty(retVal.IdComune))
                throw new RequiredFieldException("MERCATI.IDCOMUNE obbligatorio");

            if (this._posteggiDaGenerare.GetValueOrDefault(int.MinValue) > int.MinValue)
                this.AppendMercatiD(p_class);

            return retVal;
        }

        private Mercati ChildDataIntegrations(Mercati p_class)
        {
            Mercati retVal = (p_class.Clone() as Mercati);

            #region ii. Integrazione delle classi figlio con i dati della classe padre

            #region 1.	Mercati_D

            foreach (Mercati_D p_dett in retVal.PosteggiMercato)
            {
                if (String.IsNullOrEmpty(p_dett.IdComune))
                    p_dett.IdComune = retVal.IdComune;
                else if (!string.Equals(p_dett.IdComune, retVal.IdComune, StringComparison.OrdinalIgnoreCase))
                    throw new Exceptions.IncongruentDataException("MERCATI_D.IDCOMUNE diverso da MERCATI.IDCOMUNE");

                if (p_dett.FkCodiceMercato.GetValueOrDefault(int.MinValue) == int.MinValue)
                    p_dett.FkCodiceMercato = retVal.CodiceMercato;
                else if (p_dett.FkCodiceMercato != retVal.CodiceMercato)
                    throw new Exceptions.IncongruentDataException("MERCATI_D.FKCODICEMERCATO diverso da MERCATI.CODICEMERCATO");
            }

            #endregion

            #region 2.	MercatiStradario

            foreach (MercatiStradario p_str in retVal.Stradario)
            {
                if (String.IsNullOrEmpty(p_str.IDCOMUNE))
                    p_str.IDCOMUNE = retVal.IdComune;
                else if (!string.Equals(p_str.IDCOMUNE, retVal.IdComune, StringComparison.OrdinalIgnoreCase))
                    throw new Exceptions.IncongruentDataException("MERCATISTRADARIO.IDCOMUNE diverso da MERCATI.IDCOMUNE");

                p_str.FKCODICEMERCATO = retVal.CodiceMercato;

            }

            #endregion

            #endregion

            return retVal;
        }

        private void ChildInsert(Mercati p_class)
        {
            foreach (Mercati_D p_dett in p_class.PosteggiMercato)
            {
                Mercati_DMgr merc_d = new Mercati_DMgr(this.db);
                merc_d.Insert(p_dett);
            }

            foreach (MercatiStradario p_str in p_class.Stradario)
            {
                MercatiStradarioMgr merc_str = new MercatiStradarioMgr(this.db);
                merc_str.Insert(p_str);
            }

        }

        private void Validate(Mercati p_class, Init.SIGePro.Manager.Validator.AmbitoValidazione ambitoValidazione)
        {
            if (String.IsNullOrEmpty(p_class.Attivo))
                p_class.Attivo = "0";

            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Mercati p_class)
        {

        }

        private void AppendMercatiD(Mercati p_class)
        {
            for (int i = 1; i <= this._posteggiDaGenerare.GetValueOrDefault(int.MinValue); i++)
            {
                Mercati_D m = new Mercati_D();
                m.CodicePosteggio = i.ToString().PadLeft(this._posteggiDaGenerare.ToString().Length, Convert.ToChar("0"));

                p_class.PosteggiMercato.Add(m);
            }
        }
    }
}