
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using Init.Utils;
using Init.SIGePro.Utils;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoDomandeMgr
    {
        const string NOME_FILE_DOMANDA = "DomandaFrontoffice.xml";


        public FoDomande GetById(string idcomune, int? id)
        {
            return GetById(idcomune, id, false);
        }

        public FoDomande GetById(string idcomune, int? id, bool enableForeignKeys)
        {
            FoDomande c = new FoDomande();

            c.Idcomune = idcomune;
            c.Id = id;
            c.UseForeign = useForeignEnum.Yes;

            return (FoDomande)db.GetClass(c);
        }

        public int GetProssimoIdDomanda(string idComune)
        {
            var seq = new Sequence();

            seq.Db = db;
            seq.IdComune = idComune;
            seq.SequenceName = new FoDomande().DataTableName + ".ID";
            return seq.NextVal();
        }

        private int CreaDomanda(string idComune, string software, int idDomanda, int codiceAnagrafe, string identificativoDomanda, byte[] datiDomanda)
        {
            //db.BeginTransaction();

            try
            {
                // Salvo l'oggetto
                var ogg = new Oggetti
                {
                    IDCOMUNE = idComune,
                    NOMEFILE = NOME_FILE_DOMANDA,
                    OGGETTO = datiDomanda
                };

                OggettiMgr oggMgr = new OggettiMgr(db);
                ogg = oggMgr.Insert(ogg);

                // Inserisco la domanda
                var domanda = new FoDomande
                {
                    Idcomune = idComune,
                    Id = idDomanda,
                    Software = software,
                    Codiceoggetto = Convert.ToInt32(ogg.CODICEOGGETTO),
                    Codiceanagrafe = codiceAnagrafe,
                    FlgEliminata = 0,
                    FlgPresentata = 0,
                    FlgTrasferita = 0,
                    DataUltimaModifica = DateTime.Now,
                    Identificativodomanda = identificativoDomanda
                };
                domanda = this.Insert(domanda);

                //db.CommitTransaction();

                return domanda.Id.GetValueOrDefault(-1);
            }
            catch (Exception ex)
            {
                //db.RollbackTransaction();

                throw;
            }
        }


        private void AggiornaDomanda(string idComune, int idDomanda, byte[] datiDomanda, string identificativoDomanda, bool presentata, bool trasferita)
        {
            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica("select CODICEOGGETTO from fo_domande where idcomune={0} and id={1}", "idComune", "id");

                var idOggetto = -1;

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("id", idDomanda));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                        throw new ArgumentException("Impossibile trovare la domanda con id " + idDomanda);

                    idOggetto = Convert.ToInt32(obj);
                }

                //db.BeginTransaction();

                OggettiMgr oggMgr = new OggettiMgr(db);

                oggMgr.AggiornaCorpoOggetto(idComune, idOggetto, NOME_FILE_DOMANDA, datiDomanda);

                AggiornaDatiMutabili(idComune, idDomanda, identificativoDomanda, trasferita, presentata);

                //db.CommitTransaction();
            }
            catch (Exception ex)
            {
                //if (db.IsInTransaction)
                //	db.RollbackTransaction();

                throw;
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }
        }

        private void AggiornaDatiMutabili(string idCOmune, int idDomanda, string identificativoDomanda, bool trasferita, bool presentata)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }
                /*
				 * HACK: Impedisco che una domanda impostata nello stato "presentata" venga riportata allo stato "non presentata"
				 */
                var eraPresentata = false;

                var sql = "select flg_presentata from fo_domande where idcomune = {0} and id={1}";

                sql = PreparaQueryParametrica(sql, "idComune", "id");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idCOmune));
                    cmd.Parameters.Add(db.CreateParameter("id", idDomanda));

                    var objPresentata = cmd.ExecuteScalar();

                    if (objPresentata != null && objPresentata != DBNull.Value)
                        eraPresentata = Convert.ToInt32(objPresentata) > 0;
                }

                var flgPresentata = eraPresentata ? 1 : 0;

                if (flgPresentata == 0)
                    flgPresentata = presentata ? 1 : 0;

                /*
				 * Update dei flags della domanda
				 */
                sql = @"update fo_domande 
							set DATA_ULTIMA_MODIFICA = {0}, 
								identificativodomanda = {1}, 
								flg_presentata={2},
								flg_trasferita={3} 
							where idcomune = {4} 
								  and id={5}";

                sql = PreparaQueryParametrica(sql, "data", "identificativodomanda", "flg_presentata", "flg_trasferita", "idComune", "id");

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("data", DateTime.Now));
                    cmd.Parameters.Add(db.CreateParameter("identificativodomanda", identificativoDomanda));
                    cmd.Parameters.Add(db.CreateParameter("flg_presentata", flgPresentata));
                    cmd.Parameters.Add(db.CreateParameter("flg_trasferita", trasferita ? 1 : 0));
                    cmd.Parameters.Add(db.CreateParameter("idComune", idCOmune));
                    cmd.Parameters.Add(db.CreateParameter("id", idDomanda));

                    cmd.ExecuteNonQuery();
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }

        public byte[] LeggiDomanda(string idCOmune, int idDomanda)
        {
            Oggetti oggettoDomanda = LeggiOggettoDomanda(idCOmune, idDomanda);

            return oggettoDomanda.OGGETTO;
        }

        public void EliminaDomanda(string idComune, int idDomanda)
        {
            //bool inTransaction = db.IsInTransaction;

            try
            {
                FoDomande dom = GetById(idComune, idDomanda);

                if (dom == null)
                    throw new ArgumentException("Impossibile trovare la domanda con id " + idDomanda);

                this.Delete(dom);
            }
            catch (Exception ex)
            {
                throw;
            }
        }

        public void SegnaDomandaComeTrasferita(string idComune, int idDomanda, List<FoSottoscrizioniMgr.DatiSottoscrizione> datiSottoscrizioni)
        {
            try
            {
                //db.BeginTransaction();

                FoDomande dom = GetById(idComune, idDomanda);

                if (dom.FlgTrasferita.GetValueOrDefault(0) == 1)
                    throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " è già stata trasferita");

                dom.FlgTrasferita = 1;
                dom.DataUltimaModifica = DateTime.Now;

                Update(dom);

                FoSottoscrizioniMgr sottosMgr = new FoSottoscrizioniMgr(db);

                sottosMgr.ModificaSottoscrizioniDomanda(idComune, idDomanda, datiSottoscrizioni);


                //db.CommitTransaction();
            }
            catch (Exception ex)
            {
                //db.RollbackTransaction();

                throw;
            }
        }

        public void AnnullaTrasferimento(string idComune, int idDomanda)
        {
            try
            {
                //db.BeginTransaction();

                FoDomande dom = GetById(idComune, idDomanda);

                if (dom.FlgTrasferita.GetValueOrDefault(0) == 0)
                    throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " non è ancora stata trasferita");

                dom.FlgTrasferita = 0;
                dom.DataUltimaModifica = DateTime.Now;

                Update(dom);

                new FoSottoscrizioniMgr(db).EliminaSottoscrizioniDomanda(idComune, idDomanda);

                //db.CommitTransaction();
            }
            catch (Exception ex)
            {
                //db.RollbackTransaction();

                throw;
            }
        }

        public class EsitoSalvataggioDomandaOnline
        {
            public bool Nuova { get; set; }
            public bool TrasferitaModificato { get; set; }
            public bool TrasferimentoAnnullato { get; set; }
            public bool PresentataModificato { get; set; }
        }

        public EsitoSalvataggioDomandaOnline SalvaOAggiorna(string idComune, string token, string software, int idDomanda, int codiceAnagrafica, byte[] datiDomanda, string identificativoDomanda, bool flagTrasferita, bool flagPresentata)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var domandaOld = GetById(idComune, idDomanda);

                if (domandaOld == null)
                {
                    CreaDomanda(idComune, software, idDomanda, codiceAnagrafica, identificativoDomanda, datiDomanda);

                    return new EsitoSalvataggioDomandaOnline
                    {
                        Nuova = true,
                        PresentataModificato = false,
                        TrasferimentoAnnullato = false,
                        TrasferitaModificato = false
                    };
                }

                AggiornaDomanda(idComune, idDomanda, datiDomanda, identificativoDomanda, flagPresentata, flagTrasferita);

                var rVal = new EsitoSalvataggioDomandaOnline
                {
                    Nuova = false,
                    PresentataModificato = (domandaOld.FlgPresentata.GetValueOrDefault(0) == 1) != flagPresentata,
                    TrasferitaModificato = (domandaOld.FlgTrasferita.GetValueOrDefault(0) == 1) != flagTrasferita,
                    TrasferimentoAnnullato = (domandaOld.FlgTrasferita.GetValueOrDefault(0) == 1) && !flagTrasferita
                };

                if (rVal.TrasferimentoAnnullato)
                    AnnullaTrasferimento(idComune, idDomanda);



                return rVal;

            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }



        private Oggetti LeggiOggettoDomanda(string idComune, int idDomanda)
        {

            bool closeCnn = false;

            try
            {
                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                var sql = PreparaQueryParametrica("select CODICEOGGETTO from fo_domande where idcomune={0} and id={1}", "idComune", "id");

                var idOggetto = -1;

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("id", idDomanda));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                        throw new ArgumentException("Impossibile trovare la domanda con id " + idDomanda);

                    idOggetto = Convert.ToInt32(obj);
                }

                OggettiMgr oggMgr = new OggettiMgr(db);

                Oggetti oggettoDomanda = oggMgr.GetById(idComune, idOggetto);

                if (oggettoDomanda == null)
                    throw new ArgumentException("Impossibile trovare l'oggetto binario con id " + idOggetto.ToString() + " della domanda con id " + idDomanda);

                return oggettoDomanda;
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }




            /*
			FoDomande dom = GetById(idComune, idDomanda);

			if (dom == null)
				throw new ArgumentException("Impossibile trovare la domanda con id " + idDomanda);

			OggettiMgr oggMgr = new OggettiMgr(db);

			Oggetti oggettoDomanda = oggMgr.GetById(idComune, dom.Codiceoggetto.GetValueOrDefault(-1) );

			if (oggettoDomanda == null)
				throw new ArgumentException("Impossibile trovare l'oggetto binario con id " + dom.Codiceoggetto.ToString() + " della domanda con id " + idDomanda);

			return oggettoDomanda;*/
        }


        public void Delete(FoDomande cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);

            // L'eliminazione fisica dell'oggetto dal db va effettuata solamente dopo che il record che lo 
            // referenzia è stato eliminato
            EliminaOggettoDaDb(cls);
        }

        private void EliminaOggettoDaDb(FoDomande cls)
        {
            // Elimino l'oggetto binario della domanda
            OggettiMgr oggMgr = new OggettiMgr(db);

            oggMgr.EliminaOggetto(cls.Idcomune, cls.Codiceoggetto.GetValueOrDefault(-1));
        }

        private void EffettuaCancellazioneACascata(FoDomande cls)
        {
            // Elimino gli allegati della domanda
            FoDomandeOggettiMgr domOggMgr = new FoDomandeOggettiMgr(db);
            List<FoDomandeOggetti> allegati = domOggMgr.GetByCodiceDomanda(cls.Idcomune, cls.Id.GetValueOrDefault(-1));

            for (int i = 0; i < allegati.Count; i++)
            {
                domOggMgr.Delete(allegati[i]);
            }
        }

        public bool VerificaSeInviata(string idComune, int idDomanda)
        {

            bool closeCnn = false;

            try
            {
                var sql = PreparaQueryParametrica(@"SELECT 
								Count(*)
							FROM 
								domandestc, 
								fo_domande
							WHERE 
								fo_domande.idcomune = domandestc.idcomune AND
								fo_domande.identificativodomanda = domandestc.id_domandamitt AND
								fo_domande.idcomune = {0} and
								fo_domande.id = {1}", "idComune", "idDomanda");

                if (db.Connection.State == ConnectionState.Closed)
                {
                    db.Connection.Open();
                    closeCnn = true;
                }

                using (IDbCommand cmd = db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(db.CreateParameter("idDomanda", idDomanda));

                    var cnt = cmd.ExecuteScalar();

                    if (cnt == null || cnt == DBNull.Value)
                        return false;

                    return Convert.ToInt32(cnt) > 0;
                }
            }
            finally
            {
                if (closeCnn)
                    db.Connection.Close();
            }

        }


        public void ImpostaIdIstanzaOrigine(string idComune, int idDomanda, int? idDomandaOrigine)
        {
            var foDomande = GetById(idComune, idDomanda);

            if (!idDomandaOrigine.HasValue)
            {
                foDomande.CodiceIstanzaOrigine = null;
            }
            else
            {
                var codiceIstanza = ExecuteInConnection(() =>
                {
                    var sql = $@"SELECT 
                              domandestc.codiceistanza 
                            FROM 
                              fo_domande 
                                INNER JOIN domandestc ON 
                                  domandestc.idcomune = fo_domande.idcomune AND
                                  domandestc.id_domandamitt = fo_domande.identificativodomanda
                            WHERE 
                              fo_domande.idcomune={db.Specifics.QueryParameterName("idComune")} AND 
                              fo_domande.id={db.Specifics.QueryParameterName("idDomandaOrigine")}";


                    using (IDbCommand cmd = db.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(db.CreateParameter("idComune", idComune));
                        cmd.Parameters.Add(db.CreateParameter("idDomandaOrigine", idDomandaOrigine));

                        var res = cmd.ExecuteScalar();

                        return res == null || res == DBNull.Value ? (int?)null: Convert.ToInt32(res);
                    }
                });
                
                foDomande.CodiceIstanzaOrigine = codiceIstanza;
            }


            

            Update(foDomande);
        }
    }
}
