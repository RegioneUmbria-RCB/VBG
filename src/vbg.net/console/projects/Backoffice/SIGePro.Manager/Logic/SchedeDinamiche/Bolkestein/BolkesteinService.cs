using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Validator;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.SchedeDinamiche.Bolkestein
{

    public class DatiCommercioAreaPubblica
    {
        public DateTime dataAnzianitaCommercioAP;
        public DateTime? dataInizioSospensione;
        public DateTime? dataFineSospensione;
    }

    public class BolkestainRequest
    {
        public int codiceIstanza;
        public List<DatiCommercioAreaPubblica> datiCommercioAreaPubblica;
        public string idMercato;
        public string idUso;
        public string idPosteggio;
        public bool adeguamentoAlContestoStorico;
    }

    public class PunteggioAnzianita
    {
        public DatiCommercioAreaPubblica datiCommercioAreaPubblica;
        public int anniDaConsiderare;
        public int punteggio;
    }

    public class BolkestainResponse
    {
        public int punteggioAdeguamentoContesto;
        public int punteggioTitolarita;
        public List<PunteggioAnzianita> punteggiAnzianita;
    }

    public class BolkesteinService
    {

        protected BolkestainRequest Parametri
        {
            get;
            set;
        }

        protected AuthenticationInfo AuthInfo
        {
            get;
            set;
        }

        protected List<PunteggioAnzianita> CalcolaPunteggioAnzianita()
        {

            var retVal = new List<PunteggioAnzianita>();

            foreach (var item in Parametri.datiCommercioAreaPubblica)
            {

                var punti = new PunteggioAnzianita();
                punti.datiCommercioAreaPubblica = item;

                int anniTrascorsi = DateTime.Now.Year - item.dataAnzianitaCommercioAP.Year;
                int anniSospensione = 0;

                if (item.dataInizioSospensione != null || item.dataFineSospensione != null)
                {

                    if (item.dataInizioSospensione == null) { throw new Exception("Attenzione è stata indicata la fine della sospensione ma non l'inizio. Impossibile calcolare il punteggio"); }
                    if (item.dataFineSospensione == null) { throw new Exception("Attenzione è stato indicato l'inizio della sospensione ma non la fine. Impossibile calcolare il punteggio"); }

                    anniSospensione = item.dataFineSospensione.Value.Year - item.dataInizioSospensione.Value.Year;
                }

                punti.anniDaConsiderare = anniTrascorsi - anniSospensione;
                punti.punteggio = 0;

                if (punti.anniDaConsiderare <= 5)
                {
                    punti.punteggio = 40;
                }
                else if (punti.anniDaConsiderare <= 10)
                {
                    punti.punteggio = 50;
                }
                else if (punti.anniDaConsiderare > 10)
                {
                    punti.punteggio = 60;
                }

                retVal.Add(punti);
            }

            return retVal;
        }

        protected int CalcolaPunteggioTitolarita()
        {
            int retVal = 0;

            using (var db = AuthInfo.CreateDatabase())
            {
                db.Connection.Open();

                var istanza = new IstanzeMgr(db).GetById(AuthInfo.IdComune, Parametri.codiceIstanza);
                var codiceTitolare = string.IsNullOrEmpty(istanza.CODICETITOLARELEGALE) ? istanza.CODICERICHIEDENTE : istanza.CODICETITOLARELEGALE;

                var sql = @"select 
                              autorizzazioni.id 
                            from 
                              autorizzazioni
                                inner join autorizzazioni_concessioni on 
                                  autorizzazioni.idcomune = autorizzazioni_concessioni.idcomune and 
                                  autorizzazioni.id = autorizzazioni_concessioni.fk_idaut_attuale 
                            where 
                              autorizzazioni.idcomune = ''" + AuthInfo.IdComune + @"'' and
                              autorizzazioni.fk_codiceanagrafe = " + codiceTitolare + @" and
                              autorizzazioni.flag_attiva = 1 and
                              autorizzazioni_concessioni.fk_codicemercato = " + Parametri.idMercato + @" and
                              autorizzazioni_concessioni.fk_idmercatiuso = " + Parametri.idUso + @" and 
                              autorizzazioni_concessioni.fk_idposteggio = " + Parametri.idPosteggio;

                using (var cmd = db.CreateCommand(sql))
                {
                    using (var dataReader = cmd.ExecuteReader())
                    {
                        if (dataReader.Read())
                        {
                            retVal = 40;
                        }
                    }
                }

            }

            return retVal;
        }
        
        protected int CalcolaPunteggioAdeguamentoContesto()
        {
            return (Parametri.adeguamentoAlContestoStorico == true) ? 7 : 0;
        }

        public BolkestainResponse CalcolaPunteggiPosteggio(AuthenticationInfo authInfo, BolkestainRequest parametri)
        {
            this.AuthInfo = authInfo;
            this.Parametri = parametri;

            var response = new BolkestainResponse();

            response.punteggiAnzianita = CalcolaPunteggioAnzianita();
            response.punteggioTitolarita = CalcolaPunteggioTitolarita();
            response.punteggioAdeguamentoContesto = CalcolaPunteggioAdeguamentoContesto();

            return response;
        }
    }
}
