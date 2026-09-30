using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Istanze;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Linq;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.GestioneContesti
{
    public class Dyn2DatiService
    {
        private readonly DataBase _db;
        private readonly string _idComune;
        private readonly int _codiceIstanza;
        private readonly IstanzeDyn2DatiRepository _repository;

        public Dyn2DatiService(AuthenticationInfo authInfo, int codiceIstanza)
            : this(authInfo.CreateDatabase(), authInfo.IdComune, codiceIstanza)
        {
        }

        public Dyn2DatiService(DataBase db, string idComune, int codiceIstanza)
        {
            this._db = db;
            this._idComune = idComune;
            this._codiceIstanza = codiceIstanza;
            this._repository = new IstanzeDyn2DatiRepository(this._db, codiceIstanza, idComune);
        }

        public void RimuoviCampi(string contesto, int idModello, List<string> contestiCampiDaRimuovere)
        {
            var campiPresenti = this.RecuperaDaIstanza(contesto);

            var campiDaEliminare = new List<DatiIdentificativiCampo>();

            foreach (var contestoCampo in contestiCampiDaRimuovere)
            {
                campiDaEliminare.AddRange(
                                    campiPresenti[contestoCampo]
                                        .Where(x => x.IdCampoDinamico.HasValue)
                                        .Select(x => new DatiIdentificativiCampo(x.IdCampoDinamico.Value))
                );
            }

            var modello = new DatiIdentificativiModello(idModello, 0);

            this._repository.EliminaValoriCampi(modello, campiDaEliminare);
        }

        public void SalvaValoriCampi(int idModello, IEnumerable<Dyn2Dato> dati)
        {
            foreach (var dato in dati)
            {
                this.SalvaValoreCampo(idModello, dato);
            }
        }

        public void SalvaValoreCampo(int idModello, CampoDaSalvare campoDaSalvare)
        {
            var datiModello = new DatiIdentificativiModello(idModello, 0);

            var campiDaSalvare = new List<CampoDaSalvare>
            {
                campoDaSalvare
             };


            this._repository.SalvaValoriCampi(datiModello, campiDaSalvare);
        }

        public void SalvaValoreCampo(int idModello, Dyn2Dato dato)
        {
            var campo = new CampoDaSalvare(dato.IdCampoDinamico.Value, "", false);
            campo.AggiungiValore(dato.Valore, dato.ValoreDecodificato);

            this.SalvaValoreCampo(idModello, campo);
        }

        public Dictionary<string, List<Dyn2Dato>> RecuperaDaIstanzaCollegata(string contesto, string contestoCampo)
        {
            var codiceIstanzaCollegata = this.RecuperaIstanzaCollegataConTipoOccupazioneValorizzato(contesto, contestoCampo);
            if (codiceIstanzaCollegata == null)
            {
                return new Dictionary<string, List<Dyn2Dato>>();
            }

            return RecuperaDaIstanza(contesto, this._idComune, codiceIstanzaCollegata.Value);
        }

        public Dictionary<string, List<Dyn2Dato>> RecuperaDaIstanza(string contesto)
        {
            return RecuperaDaIstanza(contesto, this._idComune, this._codiceIstanza);
        }

        public Dictionary<string, List<Dyn2Dato>> RecuperaDaIstanza(string contesto, string idComune, int codiceIstanza)
        {
            string sql = $@"select
                                    dyn2_metadati.contesto_campo, istanzedyn2dati.codiceistanza, dyn2_metadati.fk_campodinamico_id,
                                    dyn2_campi.nomecampo,
                                    istanzedyn2dati.indice, istanzedyn2dati.indice_molteplicita,
                                    istanzedyn2dati.valore, istanzedyn2dati.valoredecodificato
                                from
                                    dyn2_metadati_contesti
                                        inner join dyn2_metadati on 
                                            dyn2_metadati_contesti.idcomune = dyn2_metadati.idcomune and 
                                            dyn2_metadati_contesti.id = dyn2_metadati.fk_contesto_id
                                        inner join dyn2_campi on
                                            dyn2_metadati.idcomune = dyn2_campi.idcomune and 
                                            dyn2_metadati.fk_campodinamico_id = dyn2_campi.id 
                                        left join istanzedyn2dati on 
                                            dyn2_metadati.idcomune = istanzedyn2dati.idcomune and 
                                            dyn2_metadati.fk_campodinamico_id = istanzedyn2dati.fk_d2c_id and 
                                            istanzedyn2dati.codiceistanza = {this._db.Specifics.QueryParameterName("codiceIstanza")}
                                where
                                    dyn2_metadati_contesti.idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
                                    dyn2_metadati_contesti.contesto = {this._db.Specifics.QueryParameterName("contesto")}
                                order by
                                 istanzedyn2dati.fk_d2c_id asc,
                                 istanzedyn2dati.indice_molteplicita asc";

            return this._db.ExecuteReader(
                sql,
                mp =>
                {
                    mp.AddParameter("contesto", contesto);
                    mp.AddParameter("idComune", idComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                },
                dr => new Dyn2Dato
                {
                    ContestoCampo = dr.GetString("contesto_campo"),
                    CodiceIstanza = dr.GetInt("codiceistanza"),
                    IdCampoDinamico = dr.GetInt("fk_campodinamico_id"),
                    Indice = dr.GetInt("indice"),
                    IndiceMolteplicita = dr.GetInt("indice_molteplicita"),
                    NomeCampoDinamico = dr.GetString("nomecampo"),
                    Valore = dr.GetString("valore"),
                    ValoreDecodificato = dr.GetString("valoredecodificato")
                })
                .GroupBy(x => x.ContestoCampo)
                .ToDictionary(x => x.Key, y => y.ToList());
        }

        private int? RecuperaIstanzaCollegataConTipoOccupazioneValorizzato(string contesto, string contestoCampo)
        {
            string sql = $@"select 
                                  istanze.codiceistanza, istanze.datavalidita
                                from 
                                  istanzecollegate 
                                    inner join istanze istanzacorrente on
                                      istanzecollegate.idcomune = istanzacorrente.idcomune and 
                                      istanzecollegate.codiceistanza = istanzacorrente.codiceistanza 
                                    inner join istanzecollegate collegamenti on 
	                                  istanzecollegate.idcomune = collegamenti.idcomune and
	                                  istanzecollegate.progressivo = collegamenti.progressivo
                                    inner join istanze on
	                                  collegamenti.idcomune = istanze.idcomune and 
	                                  collegamenti.codiceistanza = istanze.codiceistanza
                                    inner join istanzedyn2dati on 
                                      istanze.idcomune = istanzedyn2dati.idcomune and 
                                      istanze.codiceistanza = istanzedyn2dati.codiceistanza
                                    inner join dyn2_campi on
                                      istanzedyn2dati.idcomune = dyn2_campi.idcomune and
                                      istanzedyn2dati.fk_d2c_id = dyn2_campi.id
                                    inner join dyn2_metadati on 
                                      dyn2_campi.idcomune = dyn2_metadati.idcomune and 
                                      dyn2_campi.id = dyn2_metadati.fk_campodinamico_id and
                                      dyn2_metadati.contesto_campo = {this._db.Specifics.QueryParameterName("contesto_campo")}
                                    inner join dyn2_metadati_contesti on
                                      dyn2_metadati.idcomune = dyn2_metadati_contesti.idcomune and
                                      dyn2_metadati.fk_contesto_id = dyn2_metadati_contesti.id and
                                      dyn2_metadati_contesti.contesto = {this._db.Specifics.QueryParameterName("contesto")}
                                where 
                                  istanzecollegate.idcomune = {this._db.Specifics.QueryParameterName("idComune")} and
                                  istanzecollegate.codiceistanza = {this._db.Specifics.QueryParameterName("codiceIstanza")} and
                                  istanzacorrente.datavalidita is not null and
                                  istanzacorrente.datavalidita  >= istanze.datavalidita 
                                group by
                                  istanze.codiceistanza, istanze.datavalidita
                                order by
                                  istanze.datavalidita desc";

            return this._db.ExecuteReader(
                sql,
                mp =>
                {
                    mp.AddParameter("contesto_campo", contestoCampo);
                    mp.AddParameter("contesto", contesto);
                    mp.AddParameter("idComune", this._idComune);
                    mp.AddParameter("codiceIstanza", this._codiceIstanza);
                },
                dr => dr.GetInt("codiceistanza")).FirstOrDefault();
        }
    }
}
