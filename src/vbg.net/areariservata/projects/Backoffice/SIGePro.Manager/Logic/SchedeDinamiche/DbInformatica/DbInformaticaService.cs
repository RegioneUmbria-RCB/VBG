using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Manager.Logic.SchedeDinamiche.DbInformatica
{
    public class DbInformaticaService
    {


        public class VerificaRitardoParams {
            public int codiceIstanza;
            public DateTime dataConcordata;
            public DateTime dataEffettiva;
            public string movChiusuraNeiTermini;
            public string movChiusuraEntroTempoDoppio;
        }

        public class VerificaRitardoResult
        {
            public string MovChiusura;
            public int GGDurata;
            
            public List<string> ErroriValidazione;
        }

        public VerificaRitardoResult VerificaRitardo(AuthenticationInfo authInfo, VerificaRitardoParams vrp )
        {
            VerificaRitardoResult retVal = new VerificaRitardoResult();

            //giorni previsti da procedura
            int giorniProcedura = GiorniPrevistiDaProcedura(authInfo, vrp.codiceIstanza);
            int giorniProceduraX2 = giorniProcedura * 2;
            int giorniProceduraX3 = giorniProcedura * 3;

            //giorni impiegati
            //int giorniImpiegati = -1;
            //if (ValidaCampiCalcoloIndennizzo())
            //{
            int giorniImpiegati = CalcolaGiorniImpiegati(vrp.dataConcordata, vrp.dataEffettiva);
            //}

            //campo con i tipi di chiusura
            //var campoChiusura = ModelloCorrente.TrovaCampo("TIPO_CHIUSURA");
            //var ggDurataEffettiva = ModelloCorrente.TrovaCampo("GG_DURATA_EFFETTIVA");

            retVal.GGDurata = giorniImpiegati;
            retVal.MovChiusura = String.Empty;

            if (giorniImpiegati >= 0)
            {
                //ggDurataEffettiva.Valore = giorniImpiegati.ToString();

                //data del movimento
                //var data_effettiva = ModelloCorrente.TrovaCampo("DATA_EFFETTIVA_APPUNTAMENTO").ListaValori[0].Valore;

                if (giorniImpiegati <= giorniProcedura)
                {
                    // chiusa nei termini
                    retVal.MovChiusura = vrp.movChiusuraNeiTermini;
                    //campoChiusura.Valore = "X1DB13";
                }
                else if (giorniImpiegati <= giorniProceduraX2)
                {
                    // chiusa fuori termini ma entro il doppio del tempo previsto dalla procedura
                    retVal.ErroriValidazione.Add("Non è possibile proseguire con l'iter se non è valorizzato il campo della tipologia di completamento con una delle chiusure oltre i termini");
                }
                else if (giorniImpiegati <= giorniProceduraX3)
                {
                    // chiusa fuori termini ma entro il triplo del tempo previsto dalla procedura
                    retVal.ErroriValidazione.Add("Non è possibile proseguire con l'iter se non è valorizzato il campo 'La pratica è stata completata:' con una delle chiusure oltre i termini");
                }
                else
                {
                    // chiusa fuori termini ma oltre il triplo del tempo previsto dalla procedura
                    retVal.ErroriValidazione.Add("Non è possibile proseguire con l'iter se non è valorizzato il campo 'La pratica è stata completata:' con una delle chiusure oltre i termini");
                }
            }
           //else
           //{
           //     ggDurataEffettiva.Valore = String.Empty;
           //     campoChiusura.Valore = String.Empty;
           //}

            return retVal;

        }

        public int GiorniPrevistiDaProcedura( AuthenticationInfo authInfo, int codiceIstanza )
        {

            int retVal = -1;

            using (var db = authInfo.CreateDatabase())
            {
                try
                {
                    db.Connection.Open();

                    var sql = "select giorniprocedura from istanze_tempistica where idcomune = '" + authInfo.IdComune + "' and codiceistanza = " + codiceIstanza.ToString();

                    using (var cmd = db.CreateCommand(sql))
                    {
                        using (var dataReader = cmd.ExecuteReader())
                        {
                            if (dataReader.Read())
                            {
                                retVal = Convert.ToInt32(dataReader["giorniprocedura"].ToString());
                            }
                        }
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    // Chiudo la connessione al db
                    db.Connection.Close();
                }
            }

            return retVal;
        }

        public int CalcolaGiorniImpiegati(DateTime dataConcordata, DateTime dataEffettiva )
        {

                //prendo la differenza in giorni
            TimeSpan ts = dataEffettiva - dataConcordata;

                //ritorno la differenza
                return ts.Days;
        }

        public void CreaMovimento(AuthenticationInfo authInfo, int codiceIstanza, string dataMovimento, string tipoMovimento, int codiceAmministrazione)
        {
            // string DATA_MOVIMENTO = "20010110 15:14" "yyyyMMdd HH:mm";
            // string MOVIMENTO_DA_EFFETTUARE = "X1DB13";
            // string descMovimento = "Entro i termini";
            // string codiceAmministrazione = "2";


            // Se il campo è stato trovato e ha un valore impostato
            if (!String.IsNullOrEmpty(dataMovimento) && !String.IsNullOrEmpty(tipoMovimento))
            {
                try
                {
                    using (var db = authInfo.CreateDatabase())
                    {
                        var istanza = new IstanzeMgr(db).GetById(authInfo.IdComune, codiceIstanza);


                        var movDaEffettuare = new TipiMovimentoMgr(db).GetById(tipoMovimento, authInfo.IdComune);

                        if (movDaEffettuare == null)
                        {
                            throw new Exception("Tipo movimento " + tipoMovimento + " non trovato");
                        }

                        // Effettuo il parse della data del movimento 
                        var data = DateTime.ParseExact(dataMovimento, "yyyyMMdd HH:mm", null);

                        // Creo il manager da utilizzare per l'inserimento del movimento
                        var mgrMovimenti = new MovimentiMgr(authInfo.CreateDatabase());

                        // Verifico che il movimento non sia già stato inserito
                        var movimento = new Movimenti()
                        {
                            IDCOMUNE = authInfo.IdComune,
                            TIPOMOVIMENTO = tipoMovimento,
                            CODICEISTANZA = codiceIstanza.ToString(),
                            CODICEAMMINISTRAZIONE = codiceAmministrazione.ToString(),
                            OrderBy = "DATA DESC"
                        };

                        var listaMovimenti = mgrMovimenti.GetList(movimento);

                        if (listaMovimenti.Count == 0)                // Il movimento non esiste
                        {
                            // Inserisco un nuovo movimento
                            // riprendo la classe che ho popolato in precedenza
                            movimento.DATA = data;
                            movimento.NOTE = "Movimento creato dai dati dinamici";
                            movimento.MOVIMENTO = movDaEffettuare.Movimento;
                            movimento.ESITO = "1";
                            movimento.DATAINSERIMENTO = DateTime.Now;
                            movimento.FLAG_DA_LEGGERE = 0;
                            movimento.PUBBLICA = "0";
                            movimento.CODICERESPONSABILE = authInfo.CodiceResponsabile.HasValue? authInfo.CodiceResponsabile.ToString() : istanza.CODICERESPONSABILE;

                            new ClassValidator(movimento).RequiredFieldValidator(db, AmbitoValidazione.Insert);

                            db.Insert(movimento);
                        }
                        else
                        {
                            // Aggiorno i dati del movimento più recente 
                            // (dovrebbe essere il primo della lista)
                            listaMovimenti[0].MOVIMENTO = movDaEffettuare.Movimento;
                            listaMovimenti[0].DATA = data;
                            listaMovimenti[0].NOTE += "\r\nValori modificati dai dati dinamici";
                            listaMovimenti[0].ESITO = "1";
                            listaMovimenti[0].DATAINSERIMENTO = DateTime.Now;
                            listaMovimenti[0].FLAG_DA_LEGGERE = 0;
                            listaMovimenti[0].PUBBLICA = "0";
                            listaMovimenti[0].CODICERESPONSABILE = authInfo.CodiceResponsabile.HasValue ? authInfo.CodiceResponsabile.ToString() : istanza.CODICERESPONSABILE;

                            db.Update(listaMovimenti[0]);
                        }

                        var comportamento = ComportamentoElaborazioneEnum.ElaboraSincrono;
                        new ElaborazioneScadenzarioMgr().Elabora(movimento.IDCOMUNE, Convert.ToInt32(movimento.CODICEISTANZA), comportamento);
                    }
                }
                catch (Exception ex)
                {
                    throw ex;
                }
            }
        }

        public void ElaboraMovimenti(AuthenticationInfo authInfo, int codiceIstanza)
        {
            var url = ParametriConfigurazione.Get.WsHostUrlJava + "movimenti/elabora.htm";
            ElaboraMovimenti(authInfo, url, codiceIstanza);
        }

        public void ElaboraMovimenti(AuthenticationInfo authInfo, string urlBase, int codiceIstanza)
        {
            var wc = new WebClient();

            wc.DownloadString(String.Format("{0}?codiceIstanza={1}&Token={2}", urlBase, codiceIstanza.ToString(), authInfo.Token));
        }

        public bool MovimentoEffettuato(AuthenticationInfo authInfo, int codiceIstanza, string tipoMovimento)
        {
            using (var db = authInfo.CreateDatabase())
            {
                var mgr = new MovimentiMgr(authInfo.CreateDatabase());

                var movDaEffettuare = mgr.GetMovimentiIstanza(authInfo.IdComune, codiceIstanza.ToString());
                
                var movimento = movDaEffettuare.Where(x => x.TIPOMOVIMENTO == tipoMovimento && x.DATA.HasValue).FirstOrDefault();

                return movimento != null;
            }
        }
    }
}
