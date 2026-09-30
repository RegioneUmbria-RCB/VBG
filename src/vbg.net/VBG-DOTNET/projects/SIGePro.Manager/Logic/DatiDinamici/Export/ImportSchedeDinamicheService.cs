using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.Export
{
    public class IdMapping
    {
        public int OldId { get; set; }
        public int NewId { get; set; }
    }

    public class ImportazioneSchedaDinamicaResult
    {
        public IdMapping IdModello { get; set; }
        public List<IdMapping> IdCampi { get; set; }
        public List<IdMapping> IdTesti { get; set; }
        public List<string> AvvisiScript { get; set; }
    }

    public class ImportSchedeDinamicheService
    {


        private Dictionary<int, IdMapping> _mappingIdCampi = new Dictionary<int, IdMapping>();
        private Dictionary<int, IdMapping> _mappingIdTesti = new Dictionary<int, IdMapping>();
        private Dictionary<int, IdMapping> _mappingStruttura = new Dictionary<int, IdMapping>();

        private readonly DataBase _db;
        private readonly string _idComune;

        public ImportSchedeDinamicheService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public List<String> Verifica(string software, SchedaDinamicaEsportata datiScheda)
        {
            var errori = new List<string>();

            FormattableString sql = $@"SELECT 
                                        count(*)
                                    FROM 
                                        dyn2_modellit
                                    WHERE 
                                        idcomune={this._idComune} AND
                                        codice_scheda = {datiScheda.Modello.CodiceScheda} and
                                        software={software}";

            var conta = this._db.ExecuteScalar(sql, 0);

            if (conta > 0)
            {
                errori.Add($"Esiste già una scheda dinamica con codice {datiScheda.Modello.CodiceScheda}");
            }

            foreach (var campo in datiScheda.CampiDinamici)
            {
                var valido = this.VerificaEsistenzaCampo(software, campo);

                if (!valido)
                {
                    errori.Add($"Il campo {campo.Nomecampo} è già presente nel database");
                }
            }

            return errori;
        }

        private bool VerificaEsistenzaCampo(string software, Dyn2Campi campo)
        {
            FormattableString sql = $@"SELECT 
                                        count(*)
                                    FROM 
                                        dyn2_campi
                                    WHERE 
                                        idcomune={this._idComune} AND
                                        software={software} AND
                                        nomecampo = {campo.Nomecampo}";

            var conta = this._db.ExecuteScalar(sql, 0);

            return conta == 0;
        }

        public ImportazioneSchedaDinamicaResult Import(string software, SchedaDinamicaEsportata datiScheda)
        {
            // Importa i modelli
            try
            {
                this._db.BeginTransaction();

                var idModello = this.ImportaModello(software, datiScheda.Modello);

                // Importa campi e proprietà
                this.ImportaCampi(software, datiScheda.CampiDinamici, datiScheda.ProprietaCampiDinamici);

                // Importa i testi
                this.ImportaTesti(datiScheda.Testi);

                // Importa la struttura
                this.ImportaStruttura(idModello, datiScheda.Struttura);

                // Siccome i testi potrebbero aver variato l'id prova a fare una verifica sul testo delle formule per
                // restituire degli avvisi se le formule contengono riferimenti agli id dei campi
                var avvisiScript = this.VerificaScript(datiScheda.ScriptsModello);

                // Importa le formule
                this.ImportaScript(idModello, datiScheda.ScriptsModello);

                this._db.CommitTransaction();

                return new ImportazioneSchedaDinamicaResult
                {
                    IdModello = new IdMapping { OldId = datiScheda.Modello.Id.Value, NewId = idModello },
                    IdCampi = this._mappingIdCampi.Values.ToList(),
                    IdTesti = this._mappingIdTesti.Values.ToList(),
                    AvvisiScript = avvisiScript.ToList()
                };
            }
            catch (Exception)
            {
                this._db.RollbackTransaction();

                throw;
            }
        }

        private void ImportaScript(int idModello, List<Dyn2ModelliScript> script)
        {
            foreach (var s in script)
            {
                s.IdComune = this._idComune;
                s.FkD2mtId = idModello;

                new Dyn2ModelliScriptMgr(this._db).Insert(s);
            }
        }

        private IEnumerable<string> VerificaScript(List<Dyn2ModelliScript> script)
        {
            foreach (var s in script)
            {
                var testoScript = s.GetTestoScript();

                foreach (var dettaglioId in this._mappingStruttura.Keys)
                {
                    if (testoScript.Contains(dettaglioId.ToString()))
                    {
                        yield return $"Il testo dello script contiene un riferimento al testo con id {dettaglioId} che è stato modificato in {this._mappingStruttura[dettaglioId].NewId}";
                    }
                }
            }
        }

        private void ImportaStruttura(int idModello, List<Dyn2ModelliD> dettagli)
        {
            this._mappingStruttura = new Dictionary<int, IdMapping>();

            foreach (var dettaglio in dettagli)
            {
                var oldId = dettaglio.Id.Value;

                dettaglio.Id = null;
                dettaglio.Idcomune = this._idComune;
                dettaglio.FkD2mtId = idModello;
                dettaglio.FkD2cId = dettaglio.FkD2cId.HasValue ? (int?)this._mappingIdCampi[dettaglio.FkD2cId.Value].NewId : null;
                dettaglio.FkD2mdtId = dettaglio.FkD2mdtId.HasValue ? (int?)this._mappingIdTesti[dettaglio.FkD2mdtId.Value].NewId : null;

                var newDettaglio = new Dyn2ModelliDMgr(this._db).Insert(dettaglio);

                this._mappingStruttura[oldId] = new IdMapping { OldId = oldId, NewId = newDettaglio.Id.Value };
            }
        }

        private void ImportaTesti(List<Dyn2ModelliDTesti> testi)
        {
            this._mappingIdTesti = new Dictionary<int, IdMapping>();

            foreach (var testo in testi)
            {
                var oldId = testo.Id.Value;
                testo.Idcomune = this._idComune;
                testo.Id = null;
                var res = new Dyn2ModelliDTestiMgr(this._db).Insert(testo);
                var newId = res.Id.Value;
                this._mappingIdTesti[oldId] = new IdMapping { OldId = oldId, NewId = newId };
            }
        }

        private void ImportaCampi(string software, List<Dyn2Campi> campi, List<Dyn2CampiProprieta> proprieta)
        {
            this._mappingIdCampi = new Dictionary<int, IdMapping>();

            var dictionaryProprieta = proprieta.GroupBy(x => x.FkD2cId.Value).ToDictionary(x => x.Key, x => x.ToList());

            foreach (var campo in campi)
            {
                var oldId = campo.Id.Value;
                campo.Idcomune = this._idComune;
                campo.Software = software;
                campo.Id = null;

                var res = new Dyn2CampiMgr(this._db).Insert(campo);
                var newId = res.Id.Value;

                this._mappingIdCampi[oldId] = new IdMapping { OldId = oldId, NewId = newId };

                var proprietaCampo = dictionaryProprieta.ContainsKey(oldId) ? dictionaryProprieta[oldId] : new List<Dyn2CampiProprieta>();

                foreach (var prop in proprietaCampo)
                {
                    prop.Idcomune = this._idComune;
                    prop.FkD2cId = newId;

                    new Dyn2CampiProprietaMgr(this._db).Insert(prop);
                }
            }
        }

        private int ImportaModello(string software, Dyn2ModelliT modello)
        {
            modello.Idcomune = this._idComune;
            modello.Software = software;
            modello.Id = null;

            var res = new Dyn2ModelliTMgr(this._db).Insert(modello);

            return res.Id.Value;
        }
    }
}
