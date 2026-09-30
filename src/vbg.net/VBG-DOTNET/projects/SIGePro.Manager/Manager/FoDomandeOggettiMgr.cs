
using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoDomandeOggettiMgr
    {
        public List<FoDomandeOggetti> GetByCodiceDomanda(string idComune, int idDomanda)
        {
            FoDomandeOggetti domOgg = new FoDomandeOggetti();
            domOgg.Idcomune = idComune;
            domOgg.Iddomanda = idDomanda;

            return this.GetList(domOgg);
        }


        public int SalvaAllegatoDomanda(string idComune, int idDomanda, int codiceOggetto)
        {
            try
            {
                var sql = $@"select count(*) from fo_domande_oggetti where
                            idcomune={this.db.QueryParameter(nameof(idComune))} and
                            iddomanda={this.db.QueryParameter(nameof(idDomanda))} and
                            codiceoggetto={this.db.QueryParameter(nameof(codiceOggetto))}";

                var cnt = this.db.ExecuteScalar(sql, 0,
                    mp => mp.Add(nameof(idComune), idComune)
                            .Add(nameof(idDomanda), idDomanda)
                            .Add(nameof(codiceOggetto), codiceOggetto));

                if (cnt > 0)
                {
                    // L'oggetto fa già parte della domanda
                    return codiceOggetto;
                }

                this.db.BeginTransaction();

                var domOgg = this.Insert(new FoDomandeOggetti
                {
                    Idcomune = idComune,
                    Iddomanda = idDomanda,
                    Codiceoggetto = codiceOggetto
                });

                this.db.CommitTransaction();

                return domOgg.Codiceoggetto.GetValueOrDefault(-1);
            }
            catch (Exception ex)
            {
                this.db.RollbackTransaction();

                throw;
            }

        }


        public void EliminaAllegatoDomanda(string idComune, int idDomanda, int codiceOggetto)
        {
            FoDomandeOggetti domOgg = this.GetById(idComune, idDomanda, codiceOggetto);

            if (domOgg == null)
                return;

            try
            {
                //db.BeginTransaction();

                this.Delete(domOgg);

                //db.CommitTransaction();
            }
            catch (Exception ex)
            {
                //db.RollbackTransaction();

                throw;
            }
        }

        public void Delete(FoDomandeOggetti cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            this.EliminaOggettoDaDb(cls);
        }

        private void EliminaOggettoDaDb(FoDomandeOggetti cls)
        {
            // NO!
            //OggettiMgr oggMgr = new OggettiMgr(db);
            //oggMgr.EliminaOggetto(cls.Idcomune, cls.Codiceoggetto.GetValueOrDefault(-1));
        }


        private void EffettuaCancellazioneACascata(FoDomandeOggetti cls)
        {
        }
    }
}
