using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze;
using Init.SIGePro.Manager.Logic.DatiDinamici.ModelliFactory;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using VBG.DatiDinamici;
using VBG.DatiDinamici.Standard.Scripts;

namespace Init.SIGePro.Manager.Framework.Logic.SchedeIstanza
{
    public class SchedeIstanzaService
    {
        private readonly DataBase db;

        public SchedeIstanzaService(DataBase db)
        {
            this.db = db;
        }
        public void Insert(IstanzeDyn2ModelliT cls)
        {
            var mgr = new IstanzeDyn2ModelliTMgr(this.db);

            mgr.InsertSoloUsoInterno(cls);

            this.InizializzaModello(cls);
        }

        private void InizializzaModello(IstanzeDyn2ModelliT cls)
        {

            Dyn2ModelliDMgr modelliCampiMgr = new Dyn2ModelliDMgr(this.db);
            IstanzeDyn2DatiMgr datiMgr = new IstanzeDyn2DatiMgr(this.db);
            Dyn2CampiProprietaMgr mgrProprieta = new Dyn2CampiProprietaMgr(this.db);
            List<Dyn2ModelliD> campiModello = modelliCampiMgr.GetSoloCampiDinamiciModello(cls.Idcomune, cls.FkD2mtId.GetValueOrDefault(int.MinValue));

            var valoriCampiModello = datiMgr.GetValoriCampiDaIdModello(cls.Idcomune, cls.Codiceistanza.Value, cls.FkD2mtId.Value, 0);

            foreach (Dyn2ModelliD elementoModello in campiModello)
            {
                // Verificao se tra i campi del modello ci sono checkbox
                if (elementoModello.CampoDinamico.Tipodato != "Checkbox") continue;

                int idCampoDinamico = elementoModello.CampoDinamico.Id.GetValueOrDefault(int.MinValue);

                // Leggo che valore ha di default una checkbox non ceccata :P
                Dyn2CampiProprieta prop = mgrProprieta.GetById(cls.Idcomune, idCampoDinamico, "ValoreFalse");

                // se non è stato specificato un valore di default non inserisco nessun valore
                if (prop == null || String.IsNullOrEmpty(prop.Valore.Trim())) continue;

                if (valoriCampiModello.ContainsKey(idCampoDinamico))
                    continue;

                //List<IIstanzeDyn2Dati> campiInseriti = datiMgr.GetValoriCampo(cls.Idcomune, cls.Codiceistanza.GetValueOrDefault(int.MinValue), idCampoDinamico, 0);

                // Il campo è già stato inserito???
                //if (campiInseriti.Count > 0)
                //	continue;




                // inserisco il valore di default per la checkbox
                IstanzeDyn2Dati dato = new IstanzeDyn2Dati();

                dato.Idcomune = cls.Idcomune;
                dato.Codiceistanza = cls.Codiceistanza;
                dato.FkD2cId = idCampoDinamico;
                dato.Valore = prop.Valore;
                dato.Valoredecodificato = prop.Valore;
                dato.Indice = 0;
                dato.IndiceMolteplicita = 0;

                datiMgr.Insert(dato);
            }

            var dap = new IstanzeDyn2DataAccessFactory(this.db, cls.Idcomune, cls.Codiceistanza.GetValueOrDefault(int.MinValue));
            var loader = new ModelloDinamicoLoader(dap, cls.Idcomune, ContestoScriptEnum.Backoffice);
            var modello = new BackendModelliFactory().CreaModelloIstanza(loader, cls.FkD2mtId.GetValueOrDefault(int.MinValue), 0, false);
            modello.EseguiScriptCaricamento();
            modello.EseguiScriptSalvataggio();
            modello.Salva();
        }

        public IEnumerable<BaseDto<int, string>> GetModelliNonUtilizzati(string idComune, int codiceIstanza, string partial, bool usaSoftwareTT)
        {
            Istanze istanza = new IstanzeMgr(this.db).GetById(idComune, codiceIstanza);

            var filtroSoftware = "'" + istanza.SOFTWARE + "'";

            if (usaSoftwareTT)
                filtroSoftware += ",'TT'";

            string sql = @"SELECT 
								DYN2_MODELLIT.id,
								DYN2_MODELLIT.descrizione 
							FROM
								DYN2_MODELLIT
							WHERE
								DYN2_MODELLIT.idcomune = {0} AND
								DYN2_MODELLIT.fk_d2bc_id = 'IS' AND
								DYN2_MODELLIT.Software in (" + filtroSoftware + @") AND
								" + this.db.Specifics.UCaseFunction("DYN2_MODELLIT.descrizione") + @" like {1} and
								DYN2_MODELLIT.id NOT IN 
								( 
									SELECT 
										FK_D2MT_ID
									FROM 
										ISTANZEDYN2MODELLIT
									WHERE
										ISTANZEDYN2MODELLIT.idcomune = {2} AND
										ISTANZEDYN2MODELLIT.codiceIstanza = {3}
								)
							order by descrizione asc";

            sql = String.Format(sql, this.db.Specifics.QueryParameterName("idComune1"),
                                     this.db.Specifics.QueryParameterName("partial"),
                                     this.db.Specifics.QueryParameterName("idComune2"),
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
                    cmd.Parameters.Add(this.db.CreateParameter("idComune1", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("partial", "%" + partial.ToUpperInvariant() + "%"));
                    cmd.Parameters.Add(this.db.CreateParameter("idComune2", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIstanza", codiceIstanza));

                    using (var dr = cmd.ExecuteReader())
                    {
                        var rVal = new List<BaseDto<int, string>>();

                        while (dr.Read())
                            rVal.Add(new BaseDto<int, string>(Convert.ToInt32(dr["id"]), dr["descrizione"].ToString()));

                        return rVal;
                    }
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }
    }
}
