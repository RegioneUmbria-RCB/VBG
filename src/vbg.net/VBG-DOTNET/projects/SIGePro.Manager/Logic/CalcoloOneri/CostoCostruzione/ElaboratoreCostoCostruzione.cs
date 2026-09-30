using Init.SIGePro.Data;
using Init.Utils;
using Init.Utils.Math;
using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione
{
    public partial class ElaboratoreCostoCostruzione
    {
        private readonly CCICalcoliMgr _calcoliMgr;
        private readonly CCITabella1Mgr _tabella1Mgr;
        private readonly CCITabella2Mgr _tabella2Mgr;
        private readonly CCITabella3Mgr _tabella3Mgr;
        // CCITabella4Mgr m_tabella4Mgr;
        private readonly CCClassiSuperficiMgr _classiSuperficiMgr;
        private readonly CCICalcoliDettaglioTMgr _calcoliDettaglioMgr;
        private readonly CCICalcoli _calcolo;
        private readonly Istanze _istanza;
        private readonly CCConfigurazione _config;
        private readonly DataBase _database;


        public ElaboratoreCostoCostruzione(DataBase db, string idComune, int idCalcolo)
        {
            this._calcoliMgr = new CCICalcoliMgr(db);
            this._tabella1Mgr = new CCITabella1Mgr(db);
            this._tabella2Mgr = new CCITabella2Mgr(db);
            this._tabella3Mgr = new CCITabella3Mgr(db);
            // this.m_tabella4Mgr = new CCITabella4Mgr(db);
            this._classiSuperficiMgr = new CCClassiSuperficiMgr(db);
            this._calcoliDettaglioMgr = new CCICalcoliDettaglioTMgr(db);

            this._calcolo = this._calcoliMgr.GetById(idComune, idCalcolo);
            this._istanza = new IstanzeMgr(db).GetById(this._calcolo.Idcomune, this._calcolo.Codiceistanza.Value);
            this._config = new CCConfigurazioneMgr(db).GetById(this._istanza.IDCOMUNE, this._istanza.SOFTWARE);

            this._database = db;
        }

        public void Elabora()
        {
            this.ElaboraTabella1();

            this.ElaboraTabella2();

            this.ElaboraTabella3();

            this.ElaboraTabella4();

            this.CalcolaSc();

            this.CalcolaSt();

            this.ElaboraCalcoliCosti();

            this.ElaboraCostoEdificio();


            this._calcoliMgr.Update(this._calcolo);
        }





        private void ElaboraTabella1()
        {
            this._calcolo.I1 = 0.0m;
            this._calcolo.Su = 0.0m;

            var righeInTabella1 = this._calcoliMgr.VerificaEsistenzaRigheInTabella1(this._calcolo.Idcomune, this._istanza.SOFTWARE, this._calcolo.Id.Value, this._calcolo.Codiceistanza.Value);

            if (this._config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
            {
                var totSu = 0.0m;
                var totIncrementoPerClassi = 0.0m;

                righeInTabella1.ForEach(delegate (CCITabella1 t1)
                {
                    totSu += t1.Su.Value;
                });

                this._calcolo.Su = Arrotondamento.PerEccesso(totSu, 2);

                righeInTabella1.ForEach(delegate (CCITabella1 t1)
                {
                    var clsSuperfici = this._classiSuperficiMgr.GetById(t1.Idcomune, t1.FkCccsId.Value);

                    t1.RapportoSu = Arrotondamento.PerEccesso(t1.Su.Value == 0 ? 0.0m : t1.Su.Value / this._calcolo.Su.Value, 3);
                    t1.Incremento = clsSuperfici.Incremento;
                    t1.Incrementoxclassi = t1.RapportoSu * clsSuperfici.Incremento;

                    this._tabella1Mgr.Update(t1);


                    totIncrementoPerClassi += t1.Incrementoxclassi.Value;
                });

                // Il calcolo utilizza i dati immessi direttamente nelle tabelle 1 , 2 e Sn/Sa
                this._calcolo.Su = Arrotondamento.PerEccesso(totSu, 2);
                this._calcolo.I1 = Arrotondamento.PerEccesso(totIncrementoPerClassi, 2);
            }
            else
            {
                // il calcolo utilizza i dati immessi nella tabella CCICalcoliDettaglioT
                var calct1 = new CalcolatoreSuperficiPerTipo(this._calcoliDettaglioMgr, this._calcolo.Idcomune, this._calcolo.Id.Value, this._config.Tab1FkTsId.Value);

                this._calcolo.Su = calct1.SuperficieTotale();
                this._calcolo.I1 = 0.0m;

                righeInTabella1.ForEach(delegate (CCITabella1 t1)
                {
                    var clsSuperfici = this._classiSuperficiMgr.GetById(t1.Idcomune, t1.FkCccsId.Value);

                    var alloggi = 0;
                    var totSu = 0.0m;

                    calct1.SuperficieEAlloggiNellIntervallo(clsSuperfici.Da.Value, clsSuperfici.A.Value, out alloggi, out totSu);

                    t1.Alloggi = alloggi;
                    t1.Su = Arrotondamento.PerEccesso(totSu, 2);
                    t1.RapportoSu = Arrotondamento.PerEccesso(t1.Su.Value == 0 ? 0.0m : t1.Su.Value / this._calcolo.Su.Value, 3);
                    t1.Incremento = clsSuperfici.Incremento;
                    t1.Incrementoxclassi = t1.RapportoSu * clsSuperfici.Incremento;

                    this._calcolo.I1 += t1.Incrementoxclassi;

                    this._tabella1Mgr.Update(t1);
                });

                this._calcolo.I1 = Arrotondamento.PerEccesso(this._calcolo.I1.Value, 2);
            }

        }

        private void ElaboraTabella2()
        {
            this._calcolo.Snr = 0.0m;

            var righeInTabella2 = this._calcoliMgr.VerificaEsistenzaRigheInTabella2(this._calcolo.Idcomune, this._istanza.SOFTWARE, this._calcolo.Id.Value, this._calcolo.Codiceistanza.Value);

            if (this._config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
            {
                righeInTabella2.ForEach(delegate (CCITabella2 t2)
                {
                    this._calcolo.Snr += t2.Superficie;
                });
            }
            else
            {
                righeInTabella2.ForEach(delegate (CCITabella2 t2)
                {
                    var calct2 = new CalcolatoreSuperficiPerDettaglio(this._calcoliDettaglioMgr, this._calcolo.Idcomune, this._calcolo.Id.Value, t2.FkCcdsId.Value);
                    t2.Superficie = calct2.SuperficieTotale();

                    this._calcolo.Snr += t2.Superficie;

                    this._tabella2Mgr.Update(t2);
                });
            }

        }

        private void ElaboraTabella3()
        {
            // Tabella 3
            this._calcolo.I2 = 0.0m;

            var incrementiI2 = 0.0m;
            var numIncrementiI2 = 0;

            var righeInTabella3 = this._calcoliMgr.VerificaEsistenzaRigheInTabella3(this._calcolo.Idcomune, this._istanza.SOFTWARE, this._calcolo.Id.Value, this._calcolo.Codiceistanza.Value);

            //Si va a settare il campo CC_ITabella3.IPOTESICHERICORRE raggruppando i record per CC_TABELLA3.CC_DS_ID (CC_DETTAGLISUPERFICIE.ID)
            //Nella maggior parte delle volte il campo CC_TABELLA3.CC_DS_ID sarà null

            var iTabella3groupByDettagliSuperficie = this._tabella3Mgr.GetDettagliSuperficie(this._calcolo.Idcomune, this._calcolo.Id.Value);

            iTabella3groupByDettagliSuperficie.ForEach(delegate (CCDettagliSuperficie _dettagliSuperficie)
            {
                decimal idv;

                // TODO: Chiedere a chiocci, il calcolo per dettagli potrebbe creare problemi?
                if (!_dettagliSuperficie.Id.HasValue)
                {
                    //Se nella tabella CC_TABELLA3 il campo FK_CCDS_ID è sempre null significa che CC_ITabella3.IPOTESICHERICORRE 
                    //è calcolato in base a SNR
                    idv = (this._calcolo.Snr.Value / this._calcolo.Su.Value) * 100;
                }
                else
                {
                    //calcolo il totale dei dettagli
                    var csd = new CalcolatoreSuperficiPerDettaglio(this._calcoliDettaglioMgr, this._calcolo.Idcomune, this._calcolo.Id.Value, _dettagliSuperficie.Id.Value);
                    idv = (csd.SuperficieTotale() / this._calcolo.Su.Value) * 100;
                }

                righeInTabella3.ForEach(delegate (CCITabella3 t3)
                {
                    var t3cls = new CCTabella3Mgr(this._database).GetById(t3.Idcomune, t3.FkCct3Id.Value);

                    if (t3cls.FkCcDsId == _dettagliSuperficie.Id)
                    {
                        t3.Ipotesichericorre = 0;

                        if (idv > t3cls.RapportoSuSnrDa && idv <= t3cls.RapportoSuSnrA)
                        {
                            t3.Ipotesichericorre = 1;
                            numIncrementiI2++;
                            incrementiI2 += t3.Incremento.Value;
                        }

                        this._tabella3Mgr.Update(t3);
                    }
                });
            });

            if (numIncrementiI2 != 0 && !DoubleChecker.IsEmpty(incrementiI2))
            {
                var val = incrementiI2 / numIncrementiI2;
                this._calcolo.I2 = Arrotondamento.PerEccesso(val, 2);
            }
        }

        private void ElaboraTabella4()
        {
            this._calcolo.I3 = 0.0m;

            var m_righeInTabella4 = this._calcoliMgr.VerificaEsistenzaRigheInTabella4(this._calcolo.Idcomune, this._istanza.SOFTWARE, this._calcolo.Id.Value, this._calcolo.Codiceistanza.Value);

            m_righeInTabella4.ForEach(delegate (CCITabella4 tab4)
            {
                if (tab4.Selezionata == 1)
                    this._calcolo.I3 += tab4.Incremento;
            });
        }

        private void CalcolaSc()
        {
            // Calcolo Sc: 60% Snr + Su
            this._calcolo.Sc = this._calcolo.Snr * 0.6m + this._calcolo.Su;
        }

        private void CalcolaSt()
        {
            this._calcolo.St = 0.0m;

            // Se si utilizza l'immissione diretta la classe m_calcolo contiene già le proprietà SuArt9 e Sa valorizzate
            // altrimenti le devo calcolare in base ai dati immessi nei dettagli
            if (this._config.Usadettagliosup != CCConfigurazione.CALCSUP_MODELLO)
            {
                this._calcolo.SuArt9 = 0.0m;
                this._calcolo.Sa = 0.0m;

                var calcSuArt9 = new CalcolatoreSuperficiPerTipo(this._calcoliDettaglioMgr, this._calcolo.Idcomune, this._calcolo.Id.Value, this._config.Art9suFkTsId.Value);

                this._calcolo.SuArt9 = calcSuArt9.SuperficieTotale();

                var calcSaArt9 = new CalcolatoreSuperficiPerTipo(this._calcoliDettaglioMgr, this._calcolo.Idcomune, this._calcolo.Id.Value, this._config.Art9saFkTsId.Value);

                this._calcolo.Sa = calcSaArt9.SuperficieTotale();
            }

            // St = 60% Sa + Sn
            this._calcolo.St = Arrotondamento.PerEccesso(this._calcolo.SuArt9.Value + (this._calcolo.Sa.Value * 0.6m), 2);
        }

        private void ElaboraCalcoliCosti()
        {
            // TODO: Modificare questa parte per prendere in considerazione gli interventi di dettaglio e destinazioni d'uso di dettaglio
            this._calcolo.Costocmq = this._calcoliMgr.GetCostoAlMetroQuadro(this._calcolo.Idcomune, this._calcolo.Id.Value);

            var totaleI = this._calcolo.I1.Value + this._calcolo.I2.Value + this._calcolo.I3.Value;

            var classeEdificio = new CCTabellaClassiEdificioMgr(this._database).GetClasseEdificio(this._calcolo.Idcomune, this._istanza.SOFTWARE, totaleI);

            this._calcolo.FkCctceId = classeEdificio.Id;

            this._calcolo.Maggiorazione = classeEdificio.Maggiorazione;

            var costoCMqMaggiorato = this._calcolo.Costocmq.Value * (1 + this._calcolo.Maggiorazione.Value * 0.01m);

            this._calcolo.CostocmqMaggiorato = Arrotondamento.PerEccesso(costoCMqMaggiorato, 2);

        }

        private void ElaboraCostoEdificio()
        {
            var tContributoMgr = new CCICalcoloTContributoMgr(this._database);

            var tContributo = tContributoMgr.GetByIdCalcolo(this._calcolo.Idcomune, this._calcolo.Id.Value);

            var costoEdificio = (this._calcolo.Sc.Value + this._calcolo.St.Value) * this._calcolo.CostocmqMaggiorato.Value;

            tContributo.CostocEdificio = Arrotondamento.PerEccesso(costoEdificio, 2);

            tContributoMgr.Update(tContributo);
        }
    }
}
