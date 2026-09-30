
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.DatiDomandaOnline;
using Init.SIGePro.Manager.Utils;
using PersonalLib2.Data;
using PersonalLib2.Sql;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Linq;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class FoDomandeMgr
    {
        public FoDomande GetById(string idcomune, int? id, bool enableForeignKeys = true)
        {
            var c = new FoDomande();

            c.Idcomune = idcomune;
            c.Id = id;
            c.UseForeign = enableForeignKeys ? useForeignEnum.Yes : useForeignEnum.No;

            return this.db.GetClass(c);
        }

        public int GetProssimoIdDomanda(string idComune)
        {
            var nextVal = 0;
            var puoProseguire = true;
            do
            {
                var seq = new Sequence();

                seq.Db = this.db;
                seq.IdComune = idComune;
                seq.SequenceName = new FoDomande().DataTableName + ".ID";
                nextVal = seq.NextVal();

                var sql = $"select count(*) from fo_domande where idcomune={this.db.QueryParameter(nameof(idComune))} and id={this.db.QueryParameter("id")}";

                puoProseguire = this.db.ExecuteScalar(sql, 0,
                    mp => mp.Add(nameof(idComune), idComune)
                    .Add("id", nextVal)
                ) == 0;

            } while (!puoProseguire);

            return nextVal;
        }

        private int CreaDomanda(string idComune, SalvaDomandaCommandDto salvaDomandaCommand)
        {
            //db.BeginTransaction();

            try
            {
                // Salvo l'oggetto
                var ogg = new Oggetti
                {
                    IDCOMUNE = idComune,
                    NOMEFILE = $"DomandaFrontoffice_{salvaDomandaCommand.IdentificativoDomanda}.xml",
                    OGGETTO = salvaDomandaCommand.DatiDomanda
                };

                var oggMgr = new OggettiMgr(this.db);
                ogg = oggMgr.Insert(ogg);

                // Inserisco la domanda
                var domanda = new FoDomande
                {
                    Idcomune = idComune,
                    Id = salvaDomandaCommand.IdDomanda,
                    Software = salvaDomandaCommand.Software,
                    Codiceoggetto = Convert.ToInt32(ogg.CODICEOGGETTO),
                    Codiceanagrafe = salvaDomandaCommand.CodiceAnagrafe,
                    FlgEliminata = 0,
                    FlgPresentata = 0,
                    FlgTrasferita = 0,
                    DataUltimaModifica = DateTime.Now,
                    Identificativodomanda = salvaDomandaCommand.IdentificativoDomanda,
                    Richiedente = salvaDomandaCommand.Richiedente,
                    Intervento = salvaDomandaCommand.Intervento,
                    CodiceIntervento = salvaDomandaCommand.CodiceIntervento,
                    Oggetto = salvaDomandaCommand.Oggetto,
                    PagamentoAvviato = salvaDomandaCommand.PagamentoAvviato ? 1 : 0,
                    PagamentoCompletato = salvaDomandaCommand.PagamentoCompletato ? 1 : 0,
                    Bookmark = salvaDomandaCommand.Bookmark,
                    UpgradeCompleto = 1,
                    Provenienza = salvaDomandaCommand.Provenienza
                };
                domanda = this.Insert(domanda);

                //db.CommitTransaction();

                return domanda.Id.GetValueOrDefault(-1);
            }
            catch (Exception)
            {
                //db.RollbackTransaction();

                throw;
            }
        }

        private void AggiornaDomanda(string idComune, SalvaDomandaCommandDto salvaDomandaCommand)
        {
            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica("select CODICEOGGETTO from fo_domande where idcomune={0} and id={1}", "idComune", "id");

                var idOggetto = -1;

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("id", salvaDomandaCommand.IdDomanda));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                        throw new ArgumentException("Impossibile trovare la domanda con id " + salvaDomandaCommand.IdDomanda);

                    idOggetto = Convert.ToInt32(obj);
                }

                //db.BeginTransaction();

                var oggMgr = new OggettiMgr(this.db);

                oggMgr.AggiornaCorpoOggetto(idComune, idOggetto, $"DomandaFrontoffice_{salvaDomandaCommand.IdentificativoDomanda}.xml", salvaDomandaCommand.DatiDomanda);

                this.AggiornaDatiMutabili(idComune, salvaDomandaCommand);

                //db.CommitTransaction();
            }
            catch (Exception)
            {
                //if (db.IsInTransaction)
                //	db.RollbackTransaction();

                throw;
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        private void AggiornaDatiMutabili(string idComune, SalvaDomandaCommandDto salvaDomandaCommand)
        {

            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }
                /*
				 * HACK: Impedisco che una domanda impostata nello stato "presentata" venga riportata allo stato "non presentata"
				 */
                var eraPresentata = false;

                var sql = "select flg_presentata from fo_domande where idcomune = {0} and id={1}";

                sql = this.PreparaQueryParametrica(sql, "idComune", "id");

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("id", salvaDomandaCommand.IdDomanda));

                    var objPresentata = cmd.ExecuteScalar();

                    if (objPresentata != null && objPresentata != DBNull.Value)
                        eraPresentata = Convert.ToInt32(objPresentata) > 0;
                }

                var flgPresentata = eraPresentata ? 1 : 0;

                if (flgPresentata == 0)
                    flgPresentata = salvaDomandaCommand.FlagPresentata ? 1 : 0;

                var domanda = this.GetById(idComune, salvaDomandaCommand.IdDomanda, false);

                domanda.Software = domanda.Software;
                domanda.Identificativodomanda = salvaDomandaCommand.IdentificativoDomanda;
                domanda.Bookmark = salvaDomandaCommand.Bookmark;
                domanda.CodiceIntervento = salvaDomandaCommand.CodiceIntervento;
                //domanda.CodiceIstanzaOrigine = salvaDomandaCommand.Codicei;
                domanda.DataUltimaModifica = salvaDomandaCommand.AggiornaDataUltimaModifica ? DateTime.Now : domanda.DataUltimaModifica;
                //domanda.FlgEliminata = salvaDomandaCommand.fl
                domanda.FlgPresentata = flgPresentata;
                domanda.FlgTrasferita = salvaDomandaCommand.FlagTrasferita ? 1 : 0;
                domanda.Intervento = this.TrimString(salvaDomandaCommand.Intervento, 320);
                domanda.Oggetto = this.TrimString(salvaDomandaCommand.Oggetto, 320);
                domanda.PagamentoAvviato = salvaDomandaCommand.PagamentoAvviato ? 1 : 0;
                domanda.PagamentoCompletato = salvaDomandaCommand.PagamentoCompletato ? 1 : 0;
                domanda.Richiedente = this.TrimString(salvaDomandaCommand.Richiedente, 128);
                domanda.UpgradeCompleto = 1;
                domanda.Provenienza = salvaDomandaCommand.Provenienza;

                this.db.Update(domanda);
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        private string TrimString(string text, int maxLength)
        {
            text = text ?? "";

            if (text.Length > maxLength)
            {
                text = text.Substring(0, maxLength);
            }

            return text;
        }

        public byte[] LeggiDomanda(string idCOmune, int idDomanda)
        {
            var oggettoDomanda = this.LeggiOggettoDomanda(idCOmune, idDomanda);

            return oggettoDomanda.OGGETTO;
        }



        public void SegnaDomandaComeTrasferita(string idComune, int idDomanda, List<FoSottoscrizioniMgr.DatiSottoscrizione> datiSottoscrizioni)
        {
            try
            {
                //db.BeginTransaction();

                var dom = this.GetById(idComune, idDomanda);

                if (dom.FlgTrasferita.GetValueOrDefault(0) == 1)
                    throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " è già stata trasferita");

                dom.FlgTrasferita = 1;
                dom.DataUltimaModifica = DateTime.Now;

                this.Update(dom);

                var sottosMgr = new FoSottoscrizioniMgr(this.db);

                sottosMgr.ModificaSottoscrizioniDomanda(idComune, idDomanda, datiSottoscrizioni);


                //db.CommitTransaction();
            }
            catch (Exception)
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

                var dom = this.GetById(idComune, idDomanda);

                if (dom.FlgTrasferita.GetValueOrDefault(0) == 0)
                    throw new InvalidOperationException("La domanda " + idDomanda.ToString() + " non è ancora stata trasferita");

                dom.FlgTrasferita = 0;
                dom.DataUltimaModifica = DateTime.Now;

                this.Update(dom);

                new FoSottoscrizioniMgr(this.db).EliminaSottoscrizioniDomanda(idComune, idDomanda);

                //db.CommitTransaction();
            }
            catch (Exception)
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

        public EsitoSalvataggioDomandaOnline SalvaOAggiorna(string idComune, SalvaDomandaCommandDto salvaDomandaCommand)
        {

            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var domandaOld = this.GetById(idComune, salvaDomandaCommand.IdDomanda, false);

                if (domandaOld == null)
                {
                    this.CreaDomanda(idComune, salvaDomandaCommand);

                    return new EsitoSalvataggioDomandaOnline
                    {
                        Nuova = true,
                        PresentataModificato = false,
                        TrasferimentoAnnullato = false,
                        TrasferitaModificato = false
                    };
                }

                this.AggiornaDomanda(idComune, salvaDomandaCommand);

                var rVal = new EsitoSalvataggioDomandaOnline
                {
                    Nuova = false,
                    PresentataModificato = (domandaOld.FlgPresentata.GetValueOrDefault(0) == 1) != salvaDomandaCommand.FlagPresentata,
                    TrasferitaModificato = (domandaOld.FlgTrasferita.GetValueOrDefault(0) == 1) != salvaDomandaCommand.FlagTrasferita,
                    TrasferimentoAnnullato = (domandaOld.FlgTrasferita.GetValueOrDefault(0) == 1) && !salvaDomandaCommand.FlagTrasferita
                };

                if (rVal.TrasferimentoAnnullato)
                    this.AnnullaTrasferimento(idComune, salvaDomandaCommand.IdDomanda);



                return rVal;

            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        private Oggetti LeggiOggettoDomanda(string idComune, int idDomanda)
        {

            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = this.PreparaQueryParametrica("select CODICEOGGETTO from fo_domande where idcomune={0} and id={1}", "idComune", "id");

                var idOggetto = -1;

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("id", idDomanda));

                    var obj = cmd.ExecuteScalar();

                    if (obj == null || obj == DBNull.Value)
                        throw new ArgumentException("Impossibile trovare la domanda con id " + idDomanda);

                    idOggetto = Convert.ToInt32(obj);
                }

                var oggMgr = new OggettiMgr(this.db);

                var oggettoDomanda = oggMgr.GetById(idComune, idOggetto);

                if (oggettoDomanda == null)
                    throw new ArgumentException("Impossibile trovare l'oggetto binario con id " + idOggetto.ToString() + " della domanda con id " + idDomanda);

                return oggettoDomanda;
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
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
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);

            // L'eliminazione fisica dell'oggetto dal db va effettuata solamente dopo che il record che lo 
            // referenzia è stato eliminato
            this.EliminaOggettoDaDb(cls);
        }

        private void EliminaOggettoDaDb(FoDomande cls)
        {
            // Elimino l'oggetto binario della domanda
            var oggMgr = new OggettiMgr(this.db);

            oggMgr.EliminaOggetto(cls.Idcomune, cls.Codiceoggetto.GetValueOrDefault(-1));
        }

        private void EffettuaCancellazioneACascata(FoDomande cls)
        {
            // Elimino gli allegati della domanda
            var domOggMgr = new FoDomandeOggettiMgr(this.db);
            var allegati = domOggMgr.GetByCodiceDomanda(cls.Idcomune, cls.Id.GetValueOrDefault(-1));

            for (var i = 0; i < allegati.Count; i++)
            {
                domOggMgr.Delete(allegati[i]);
            }
        }

        public bool VerificaSeInviata(string idComune, int idDomanda)
        {
            FormattableString sql = $@"SELECT 
                                Count(*)    
                            FROM 
								domandestc, 
								fo_domande
							WHERE 
								fo_domande.idcomune = domandestc.idcomune AND
								fo_domande.identificativodomanda = domandestc.id_domandamitt AND
								fo_domande.idcomune = {idComune} and
								fo_domande.id = {idDomanda}";

            var count = this.db.ExecuteScalar(sql, 0);

            if (count > 0)
            {
                return true;
            }

            sql = $"select flg_presentata from fo_domande where fo_domande.idcomune = {idComune} and fo_domande.id = {idDomanda}";

            count = this.db.ExecuteScalar(sql, 0);

            return count > 0;
        }

        public void ImpostaIdIstanzaOrigine(string idComune, int idDomanda, int? idDomandaOrigine)
        {
            var foDomande = this.GetById(idComune, idDomanda);

            if (!idDomandaOrigine.HasValue)
            {
                foDomande.CodiceIstanzaOrigine = null;
            }
            else
            {
                var codiceIstanza = this.ExecuteInConnection(() =>
                {
                    var sql = $@"SELECT 
                              domandestc.codiceistanza 
                            FROM 
                              fo_domande 
                                INNER JOIN domandestc ON 
                                  domandestc.idcomune = fo_domande.idcomune AND
                                  domandestc.id_domandamitt = fo_domande.identificativodomanda
                            WHERE 
                              fo_domande.idcomune={this.db.Specifics.QueryParameterName("idComune")} AND 
                              fo_domande.id={this.db.Specifics.QueryParameterName("idDomandaOrigine")}";


                    using (var cmd = this.db.CreateCommand(sql))
                    {
                        cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                        cmd.Parameters.Add(this.db.CreateParameter("idDomandaOrigine", idDomandaOrigine));

                        var res = cmd.ExecuteScalar();

                        return res == null || res == DBNull.Value ? (int?)null : Convert.ToInt32(res);
                    }
                });

                foDomande.CodiceIstanzaOrigine = codiceIstanza;
            }




            this.Update(foDomande);
        }

        public void SalvaCodiceInterventoPerStatistica(string idComune, int idDomanda, int codiceIntervento)
        {
            var closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"update fo_domande 
							set CODICEINTERVENTO = {0} 
							where idcomune = {1} 
								  and id={2}";

                sql = this.PreparaQueryParametrica(sql, "codiceIntervento", "idComune", "id");

                using (var cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("codiceIntervento", codiceIntervento));
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("id", idDomanda));

                    cmd.ExecuteNonQuery();
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }
        }

        public bool DomandaEliminata(string idComune, int idDomanda)
        {
            var sql = $"select count(*) from fo_domande where idcomune={this.db.QueryParameter(nameof(idComune))} and id={this.db.QueryParameter(nameof(idDomanda))}";

            var result = this.db.ExecuteScalar(sql, 0, mp =>
            {
                mp.Add(nameof(idComune), idComune);
                mp.Add(nameof(idDomanda), idDomanda);
            });

            return result == 0;
        }

        public DatiDomandaOnlineDto GetDomandaInSospesoById(string idComune, int idDomanda)
        {
            var sql = $@"SELECT 
	                        fo_domande.*
                        FROM 
	                        fo_domande
		                        INNER JOIN anagrafe ON
			                        anagrafe.idcomune = fo_domande.idcomune AND
			                        anagrafe.codiceAnagrafe = fo_domande.codiceAnagrafe
                        WHERE 
	                        fo_domande.idcomune = {this.db.QueryParameter(nameof(idComune))} AND
	                        fo_domande.id = {this.db.QueryParameter(nameof(idDomanda))}";

            return this.db.ExecuteReader(sql,
            mp => mp.Add(nameof(idComune), idComune)
                    .Add(nameof(idDomanda), idDomanda),
                dr => new DatiDomandaOnlineDto
                {
                    Id = dr.GetInt("id").Value,
                    Bookmark = dr.GetString("bookmark"),
                    CodiceIntervento = dr.GetInt("CODICEINTERVENTO"),
                    CodiceOggetto = dr.GetInt("CODICEOGGETTO"),
                    Intervento = dr.GetString("intervento"),
                    Oggetto = dr.GetString("oggetto"),
                    Richiedente = dr.GetString("richiedente"),
                    PagamentoAvviato = dr.GetInt("pagamento_avviato").GetValueOrDefault(0) == 1,
                    PagamentoCompletato = dr.GetInt("pagamento_completato").GetValueOrDefault(0) == 1,
                    DataUltimaModifica = dr.GetDateTime("DATA_ULTIMA_MODIFICA"),
                    IdentificativoDomanda = dr.GetString("IdentificativoDomanda"),
                    UpgradeCompleto = dr.GetInt("upgrade_completo").GetValueOrDefault(0) == 1
                }).FirstOrDefault();
        }

        public IEnumerable<DatiDomandaOnlineDto> GetDomandeInSospeso(string idComune, string software, int codiceAnagrafe, string provenienza)
        {
            var sql = $@"SELECT 
	                        fo_domande.*
                        FROM 
	                        fo_domande
		                        INNER JOIN anagrafe ON
			                        anagrafe.idcomune = fo_domande.idcomune AND
			                        anagrafe.codiceAnagrafe = fo_domande.codiceAnagrafe
                        WHERE 
	                        fo_domande.idcomune = {this.db.QueryParameter(nameof(idComune))} AND
	                        fo_domande.software = {this.db.QueryParameter(nameof(software))} AND
	                        fo_domande.codiceanagrafe = {this.db.QueryParameter(nameof(codiceAnagrafe))} AND
                            fo_domande.provenienza = {this.db.QueryParameter(nameof(provenienza))} AND 
                            ({this.db.Specifics.CoalesceFunction("fo_domande.FLG_PRESENTATA", 0)} = 0 AND {this.db.Specifics.CoalesceFunction("fo_domande.FLG_TRASFERITA", 0)} = 0)
                        ORDER BY 
	                        fo_domande.DATA_ULTIMA_MODIFICA DESC";

            return this.db.ExecuteReader(sql,
                mp => mp.Add(nameof(idComune), idComune)
                        .Add(nameof(software), software)
                        .Add(nameof(codiceAnagrafe), codiceAnagrafe)
                        .Add(nameof(provenienza), provenienza),
                dr => new DatiDomandaOnlineDto
                {
                    Id = dr.GetInt("id").Value,
                    Bookmark = dr.GetString("bookmark"),
                    CodiceIntervento = dr.GetInt("CODICEINTERVENTO"),
                    CodiceOggetto = dr.GetInt("CODICEOGGETTO"),
                    Intervento = dr.GetString("intervento"),
                    Oggetto = dr.GetString("oggetto"),
                    Richiedente = dr.GetString("richiedente"),
                    PagamentoAvviato = dr.GetInt("pagamento_avviato").GetValueOrDefault(0) == 1,
                    PagamentoCompletato = dr.GetInt("pagamento_completato").GetValueOrDefault(0) == 1,
                    DataUltimaModifica = dr.GetDateTime("DATA_ULTIMA_MODIFICA"),
                    IdentificativoDomanda = dr.GetString("IdentificativoDomanda"),
                    UpgradeCompleto = dr.GetInt("upgrade_completo").GetValueOrDefault(0) == 1,
                });
        }
    }
}
