
using Init.SIGePro.Data;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita.Eventi;
using Init.SIGePro.Manager.Validator;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using Vbg.EventBus.Abstractions;
using VBG.DatiDinamici;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class IstanzeDyn2ModelliTMgr
    {
        public class ElementoListaModelliIstanza : ElementoListaModelli
        {
            public string Provenienza { get; set; }

            public ElementoListaModelliIstanza(int id, string descrizione, string provenienza) : base(id, descrizione)
            {
                this.Provenienza = provenienza;
            }
        }


        public IstanzeDyn2ModelliT InsertSoloUsoInterno(IstanzeDyn2ModelliT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            return cls;
        }





        public List<ElementoListaModelliIstanza> GetModelliIstanza(string idComune, int codiceIstanza, string codiceMovimento)
        {
            if (String.IsNullOrEmpty(codiceMovimento))
                return this.GetModelliNoMovimento(idComune, codiceIstanza);

            return this.GetModelliMovimento(idComune, codiceIstanza, Convert.ToInt32(codiceMovimento));
        }

        private List<ElementoListaModelliIstanza> GetModelliMovimento(string idComune, int codiceIstanza, int codiceMovimento)
        {
            string sql = @"SELECT 
							dyn2_modellit.id,
							dyn2_modellit.descrizione,
							tipimovimento.movimento
						FROM
							istanzeDyn2Modellit,
							dyn2_modellit,
							movimentiDyn2Modellit,
							movimenti,
							tipimovimento
						WHERE
							dyn2_modellit.idcomune = istanzeDyn2Modellit.idcomune  AND
							dyn2_modellit.id = istanzeDyn2Modellit.fk_d2mt_id AND
							movimentiDyn2Modellit.idcomune = istanzeDyn2Modellit.idcomune AND
							movimentiDyn2Modellit.fk_d2mt_id = istanzeDyn2Modellit.fk_d2mt_id  and
							movimentiDyn2Modellit.codiceistanza = istanzeDyn2Modellit.codiceistanza AND
							movimenti.idcomune = movimentiDyn2Modellit.idcomune AND
							movimenti.codicemovimento = movimentiDyn2Modellit.codicemovimento AND
							tipimovimento.idcomune = movimenti.idcomune AND
							tipimovimento.tipomovimento = movimenti.tipomovimento and
							istanzeDyn2Modellit.idcomune = {0} AND
							istanzeDyn2Modellit.codiceistanza = {1} AND
							movimentiDyn2Modellit.codicemovimento = {2}
						order by dyn2_modellit.descrizione asc";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idcomune"),
                                        this.db.Specifics.QueryParameterName("codiceIstanza"),
                                        this.db.Specifics.QueryParameterName("codiceMovimento"));

            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }

            List<ElementoListaModelliIstanza> list = new List<ElementoListaModelliIstanza>();

            try
            {
                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceMovimento", codiceMovimento));

                    using (IDataReader rd = cmd.ExecuteReader())
                    {
                        while (rd.Read())
                        {
                            int key = Convert.ToInt32(rd["id"]);
                            string val = rd["descrizione"].ToString();
                            string provenienza = rd["movimento"].ToString();
                            list.Add(new ElementoListaModelliIstanza(key, val, provenienza));
                        }
                    }

                    return list;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        private List<ElementoListaModelliIstanza> GetModelliNoMovimento(string idComune, int codiceIstanza)
        {
            string sql = @"SELECT 
							  dyn2_modellit.id,
							  dyn2_modellit.descrizione 
							FROM
							  istanzeDyn2Modellit,
							  dyn2_modellit
							WHERE
							   dyn2_modellit.idcomune = istanzeDyn2Modellit.idcomune  AND
							   dyn2_modellit.id = istanzeDyn2Modellit.fk_d2mt_id AND
							   istanzeDyn2Modellit.idcomune = {0} AND
							   istanzeDyn2Modellit.codiceistanza = {1}
							order by dyn2_modellit.descrizione asc";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idcomune"),
                                        this.db.Specifics.QueryParameterName("codiceIstanza"));

            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }

            List<ElementoListaModelliIstanza> list = new List<ElementoListaModelliIstanza>();

            try
            {
                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));

                    using (IDataReader rd = cmd.ExecuteReader())
                    {
                        while (rd.Read())
                        {
                            int key = Convert.ToInt32(rd["id"]);
                            string val = rd["descrizione"].ToString();

                            list.Add(new ElementoListaModelliIstanza(key, val, String.Empty));
                        }
                    }

                    this.VerificaProvenienzaModello(idComune, codiceIstanza, list);

                    return list;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        private void VerificaProvenienzaModello(string idComune, int codiceIstanza, List<ElementoListaModelliIstanza> list)
        {
            string sql = @"SELECT
								tipimovimento.movimento
							FROM
							  movimentiDyn2Modellit,
							  movimenti,
							  tipimovimento
							WHERE
							  tipimovimento.idcomune = movimenti.idcomune AND
							  tipimovimento.tipomovimento = movimenti.tipomovimento and
							  movimenti.idcomune = movimentiDyn2Modellit.idcomune AND
							  movimenti.codicemovimento = movimentiDyn2Modellit.codicemovimento AND
							  movimentiDyn2Modellit.idcomune = {0} AND
							  movimentiDyn2Modellit.codiceistanza = {1} AND
							  movimentiDyn2Modellit.fk_d2mt_id = {2}";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                    this.db.Specifics.QueryParameterName("codiceIstanza"),
                                    this.db.Specifics.QueryParameterName("idModello"));

            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                closeCnn = true;
                this.db.Connection.Open();
            }

            try
            {
                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", -1));
                    cmd.Parameters.Add(this.db.CreateParameter("idModello", -1));

                    foreach (ElementoListaModelliIstanza it in list)
                    {
                        ((IDataParameter)cmd.Parameters[this.db.Specifics.ParameterName("codiceIstanza")]).Value = codiceIstanza;
                        ((IDataParameter)cmd.Parameters[this.db.Specifics.ParameterName("idModello")]).Value = it.Id;

                        object provenienza = cmd.ExecuteScalar();

                        if (provenienza == null || provenienza.ToString() == String.Empty)
                            it.Provenienza = "Istanza";
                        else
                            it.Provenienza = provenienza.ToString();
                    }

                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }


        private void EffettuaCancellazioneACascata(IstanzeDyn2ModelliT cls)
        {
            // se sono stati inseriti valori elmino tutti quelli che non sono già utilizzati in altri modelli
            this.EliminaValoriInutilizzati(cls);
        }

        private void EliminaValoriInutilizzati(IstanzeDyn2ModelliT cls)
        {
            string sql = @"SELECT 
							dyn2_modellid.fk_d2c_ID
						FROM 
							istanzeDyn2modellit,
							dyn2_modellid
						WHERE
							dyn2_modellid.idcomune =istanzeDyn2modellit.idComune AND
							dyn2_modellid.fk_d2mt_id =istanzeDyn2modellit.fk_d2mt_id and 
							dyn2_modellid.fk_d2mdt_id IS NULL and
							istanzeDyn2modellit.idComune = {0} AND
							istanzeDyn2modellit.CodiceiStanza = {1} AND
							istanzeDyn2modellit.fk_d2mt_id = {2} AND
							dyn2_modellid.fk_d2c_ID NOT IN 
							(
								SELECT 
								  dyn2_modellid.fk_d2c_ID
								FROM 
								  istanzeDyn2modellit,
								  dyn2_modellid
								WHERE
								  dyn2_modellid.idcomune =istanzeDyn2modellit.idComune AND
								  dyn2_modellid.fk_d2mt_id =istanzeDyn2modellit.fk_d2mt_id and 
								  dyn2_modellid.fk_d2mdt_id IS NULL and
								  istanzeDyn2modellit.idComune = {3} AND
								  istanzeDyn2modellit.CodiceiStanza = {4} AND
								  istanzeDyn2modellit.fk_d2mt_id <> {5}
							)";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune1"),
                                    this.db.Specifics.QueryParameterName("codiceIstanza1"),
                                    this.db.Specifics.QueryParameterName("codiceModello1"),
                                    this.db.Specifics.QueryParameterName("idComune2"),
                                    this.db.Specifics.QueryParameterName("codiceIstanza2"),
                                    this.db.Specifics.QueryParameterName("codiceModello2"));

            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                closeCnn = true;
            }

            List<int> ids = new List<int>();

            try
            {
                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune1", cls.Idcomune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza1", cls.Codiceistanza));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceModello1", cls.FkD2mtId));
                    cmd.Parameters.Add(this.db.CreateParameter("idComune2", cls.Idcomune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza2", cls.Codiceistanza));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceModello2", cls.FkD2mtId));

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                            ids.Add(Convert.ToInt32(dr[0]));
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

            IstanzeDyn2DatiMgr mgrDati = new IstanzeDyn2DatiMgr(this.db);

            foreach (int id in ids)
            {
                List<IstanzeDyn2Dati> dati = mgrDati.GetByIdNoIndice(cls.Idcomune, cls.Codiceistanza.GetValueOrDefault(int.MinValue), id);

                for (int i = 0; i < dati.Count; i++)
                    mgrDati.Delete(dati[i]);
            }
        }

        public List<int> GetListaIndiciScheda(string idComune, int codiceIstanza, int codiceModello)
        {
            string sql = @"SELECT 
							  distinct istanzedyn2dati.indice
							FROM 
							  dyn2_modellid,
							  istanzedyn2dati
							WHERE
							  istanzedyn2dati.idcomune = dyn2_modellid.idcomune AND
							  istanzedyn2dati.fk_d2c_id = dyn2_modellid.fk_d2c_id AND
							  dyn2_modellid.idcomune = {0} AND
							  dyn2_modellid.fk_d2mt_id = {1} AND
							  istanzedyn2dati.codiceistanza = {2} 
							order by istanzedyn2dati.indice asc";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune"),
                                        this.db.Specifics.QueryParameterName("codiceModello"),
                                        this.db.Specifics.QueryParameterName("codiceIstanza"));


            bool closeCnn = false;

            if (this.db.Connection.State == ConnectionState.Closed)
            {
                this.db.Connection.Open();
                closeCnn = true;
            }

            try
            {
                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceModello", codiceModello));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));

                    List<int> rVal = new List<int>();

                    using (IDataReader dr = cmd.ExecuteReader())
                    {
                        while (dr.Read())
                            rVal.Add(Convert.ToInt32(dr[0]));
                    }

                    return rVal;
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public void Delete(IstanzeDyn2ModelliT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.NotificaRimozioneScheda(cls);
        }

        private void NotificaRimozioneScheda(IstanzeDyn2ModelliT scheda)
        {
            // La notifica deve avvenire solamente se l'istanza da cui sto eliminando la scheda è 
            // collegata ad un'attività

            var eventPublisher = StaticKernelContainer.GetService<IEventPublisher>();

            eventPublisher.Publish(new SchedaDinamicaIstanzaEliminata(scheda.Codiceistanza.Value, scheda.FkD2mtId.Value));
        }

    }
}
