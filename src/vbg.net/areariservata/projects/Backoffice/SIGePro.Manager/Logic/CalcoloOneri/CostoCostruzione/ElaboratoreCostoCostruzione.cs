using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using Init.Utils.Math;
using Init.Utils;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione
{
	public partial class ElaboratoreCostoCostruzione
	{
		CCICalcoliMgr m_calcoliMgr;
		CCITabella1Mgr m_tabella1Mgr;
		CCITabella2Mgr m_tabella2Mgr;
		CCITabella3Mgr m_tabella3Mgr;
		CCITabella4Mgr m_tabella4Mgr;
		CCClassiSuperficiMgr m_classiSuperficiMgr;
		CCICalcoliDettaglioTMgr m_calcoliDettaglioMgr;

		CCICalcoli m_calcolo;

		Istanze m_istanza;

		CCConfigurazione m_config;

		DataBase m_database;


		public ElaboratoreCostoCostruzione(DataBase db, string idComune, int idCalcolo)
		{
			m_calcoliMgr = new CCICalcoliMgr(db);
			m_tabella1Mgr = new CCITabella1Mgr(db);
			m_tabella2Mgr = new CCITabella2Mgr(db);
			m_tabella3Mgr = new CCITabella3Mgr(db);
			m_tabella4Mgr = new CCITabella4Mgr(db);
			m_classiSuperficiMgr = new CCClassiSuperficiMgr(db);
			m_calcoliDettaglioMgr = new CCICalcoliDettaglioTMgr(db);

			m_calcolo = m_calcoliMgr.GetById(idComune, idCalcolo);
            m_istanza = new IstanzeMgr(db).GetById(m_calcolo.Idcomune, m_calcolo.Codiceistanza.Value);
			m_config = new CCConfigurazioneMgr(db).GetById(m_istanza.IDCOMUNE, m_istanza.SOFTWARE);

			m_database = db;
		}

		public void Elabora()
		{
			ElaboraTabella1();

			ElaboraTabella2();

			ElaboraTabella3();

			ElaboraTabella4();

			CalcolaSc();

			CalcolaSt();

			ElaboraCalcoliCosti();

			ElaboraCostoEdificio();


			m_calcoliMgr.Update(m_calcolo);
		}





		private void ElaboraTabella1()
		{
			m_calcolo.I1 = 0.0d;
			m_calcolo.Su = 0.0d;

			List<CCITabella1> righeInTabella1 = m_calcoliMgr.VerificaEsistenzaRigheInTabella1(m_calcolo.Idcomune, m_istanza.SOFTWARE, m_calcolo.Id.Value, m_calcolo.Codiceistanza.Value);

			if (m_config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
			{
				double totSu = 0.0f;
				double totIncrementoPerClassi = 0.0f;

				righeInTabella1.ForEach(delegate(CCITabella1 t1)
				{
                    totSu += t1.Su.GetValueOrDefault(0);
				});

				m_calcolo.Su = Arrotondamento.PerEccesso(totSu, 2);

				righeInTabella1.ForEach(delegate(CCITabella1 t1)
				{
					CCClassiSuperfici clsSuperfici = m_classiSuperficiMgr.GetById(t1.Idcomune, t1.FkCccsId.Value);

                    t1.RapportoSu = Arrotondamento.PerEccesso(t1.Su.GetValueOrDefault(double.MinValue) == 0 ? 0.0d : t1.Su.GetValueOrDefault(double.MinValue) / m_calcolo.Su.GetValueOrDefault(double.MinValue), 3);
					t1.Incremento = clsSuperfici.Incremento;
					t1.Incrementoxclassi = t1.RapportoSu * clsSuperfici.Incremento;

					m_tabella1Mgr.Update(t1);


                    totIncrementoPerClassi += t1.Incrementoxclassi.GetValueOrDefault(double.MinValue);
				});

				// Il calcolo utilizza i dati immessi direttamente nelle tabelle 1 , 2 e Sn/Sa
				m_calcolo.Su = Arrotondamento.PerEccesso(totSu, 2);
				m_calcolo.I1 = Arrotondamento.PerEccesso(totIncrementoPerClassi, 2);
			}
			else
			{
				// il calcolo utilizza i dati immessi nella tabella CCICalcoliDettaglioT
                CalcolatoreSuperficiPerTipo calct1 = new CalcolatoreSuperficiPerTipo(m_calcoliDettaglioMgr, m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_config.Tab1FkTsId.GetValueOrDefault(int.MinValue));

				m_calcolo.Su = calct1.SuperficieTotale();
				m_calcolo.I1 = 0.0f;

				righeInTabella1.ForEach(delegate(CCITabella1 t1)
				{
                    CCClassiSuperfici clsSuperfici = m_classiSuperficiMgr.GetById(t1.Idcomune, t1.FkCccsId.GetValueOrDefault(int.MinValue));

					int alloggi = 0;
					double totSu = 0.0f;

                    calct1.SuperficieEAlloggiNellIntervallo(clsSuperfici.Da.GetValueOrDefault(int.MinValue), clsSuperfici.A.GetValueOrDefault(int.MinValue), out alloggi, out totSu);

					t1.Alloggi = alloggi;
					t1.Su = Arrotondamento.PerEccesso(totSu, 2);
                    t1.RapportoSu = Arrotondamento.PerEccesso(t1.Su.GetValueOrDefault(double.MinValue) == 0 ? 0.0f : t1.Su.GetValueOrDefault(double.MinValue) / m_calcolo.Su.GetValueOrDefault(double.MinValue), 3);
					t1.Incremento = clsSuperfici.Incremento;
					t1.Incrementoxclassi = t1.RapportoSu * clsSuperfici.Incremento;

					m_calcolo.I1 += t1.Incrementoxclassi;

					m_tabella1Mgr.Update(t1);
				});

                m_calcolo.I1 = Arrotondamento.PerEccesso(m_calcolo.I1.GetValueOrDefault(double.MinValue), 2);
			}

		}

		private void ElaboraTabella2()
		{
			m_calcolo.Snr = 0.0f;

            List<CCITabella2> righeInTabella2 = m_calcoliMgr.VerificaEsistenzaRigheInTabella2(m_calcolo.Idcomune, m_istanza.SOFTWARE, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));

			if (m_config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
			{
				righeInTabella2.ForEach(delegate(CCITabella2 t2)
				{
					m_calcolo.Snr += t2.Superficie;
				});
			}
			else
			{
				righeInTabella2.ForEach(delegate(CCITabella2 t2)
				{
                    CalcolatoreSuperficiPerDettaglio calct2 = new CalcolatoreSuperficiPerDettaglio(m_calcoliDettaglioMgr, m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue), t2.FkCcdsId.GetValueOrDefault(int.MinValue));
					t2.Superficie = calct2.SuperficieTotale();

					m_calcolo.Snr += t2.Superficie;

					m_tabella2Mgr.Update(t2);
				});
			}

		}

		private void ElaboraTabella3()
		{
			// Tabella 3
			m_calcolo.I2 = 0.0f;

			double incrementiI2 = 0.0;
			int numIncrementiI2 = 0;

            List<CCITabella3> m_righeInTabella3 = m_calcoliMgr.VerificaEsistenzaRigheInTabella3(m_calcolo.Idcomune, m_istanza.SOFTWARE, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));

			//Si va a settare il campo CC_ITabella3.IPOTESICHERICORRE raggruppando i record per CC_TABELLA3.CC_DS_ID (CC_DETTAGLISUPERFICIE.ID)
			//Nella maggior parte delle volte il campo CC_TABELLA3.CC_DS_ID sarà null

            List<CCDettagliSuperficie> iTabella3groupByDettagliSuperficie = m_tabella3Mgr.GetDettagliSuperficie(m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue));

			iTabella3groupByDettagliSuperficie.ForEach(delegate(CCDettagliSuperficie _dettagliSuperficie)
			{
				double idv;

				// TODO: Chiedere a chiocci, il calcolo per dettagli potrebbe creare problemi?
                if (_dettagliSuperficie.Id.GetValueOrDefault(int.MinValue) == int.MinValue)
				{
					//Se nella tabella CC_TABELLA3 il campo FK_CCDS_ID è sempre null significa che CC_ITabella3.IPOTESICHERICORRE 
					//è calcolato in base a SNR
                    idv = (m_calcolo.Snr.GetValueOrDefault(double.MinValue) / m_calcolo.Su.GetValueOrDefault(double.MinValue)) * 100;
				}
				else
				{
					//calcolo il totale dei dettagli
                    CalcolatoreSuperficiPerDettaglio csd = new CalcolatoreSuperficiPerDettaglio(m_calcoliDettaglioMgr, m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue), _dettagliSuperficie.Id.GetValueOrDefault(int.MinValue));
                    idv = (csd.SuperficieTotale() / m_calcolo.Su.GetValueOrDefault(double.MinValue)) * 100;
				}

				m_righeInTabella3.ForEach(delegate(CCITabella3 t3)
				{
                    CCTabella3 t3cls = new CCTabella3Mgr(m_database).GetById(t3.Idcomune, t3.FkCct3Id.GetValueOrDefault(int.MinValue));

					if (t3cls.FkCcDsId == _dettagliSuperficie.Id)
					{
						t3.Ipotesichericorre = 0;

						if (idv > t3cls.RapportoSuSnrDa && idv <= t3cls.RapportoSuSnrA)
						{
							t3.Ipotesichericorre = 1;
							numIncrementiI2++;
                            incrementiI2 += t3.Incremento.GetValueOrDefault(0);
						}

						m_tabella3Mgr.Update(t3);
					}
				});
			});

			if (numIncrementiI2 != 0 && !DoubleChecker.IsDoubleEmpty(incrementiI2))
			{
				double val = incrementiI2 / (double)numIncrementiI2;
				m_calcolo.I2 = Arrotondamento.PerEccesso(val, 2);
			}
		}

		private void ElaboraTabella4()
		{
			m_calcolo.I3 = 0.0f;

            List<CCITabella4> m_righeInTabella4 = m_calcoliMgr.VerificaEsistenzaRigheInTabella4(m_calcolo.Idcomune, m_istanza.SOFTWARE, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));

			m_righeInTabella4.ForEach(delegate(CCITabella4 tab4)
			{
				if (tab4.Selezionata == 1)
					m_calcolo.I3 += tab4.Incremento;
			});
		}

		private void CalcolaSc()
		{
			// Calcolo Sc: 60% Snr + Su
			m_calcolo.Sc = m_calcolo.Snr * 0.6f + m_calcolo.Su;
		}

		private void CalcolaSt()
		{
			m_calcolo.St = 0.0d;

			// Se si utilizza l'immissione diretta la classe m_calcolo contiene già le proprietà SuArt9 e Sa valorizzate
			// altrimenti le devo calcolare in base ai dati immessi nei dettagli
			if (m_config.Usadettagliosup != CCConfigurazione.CALCSUP_MODELLO)
			{
				m_calcolo.SuArt9 = 0.0d;
				m_calcolo.Sa = 0.0d;

                CalcolatoreSuperficiPerTipo calcSuArt9 = new CalcolatoreSuperficiPerTipo(m_calcoliDettaglioMgr, m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_config.Art9suFkTsId.GetValueOrDefault(int.MinValue));

				m_calcolo.SuArt9 = calcSuArt9.SuperficieTotale();

                CalcolatoreSuperficiPerTipo calcSaArt9 = new CalcolatoreSuperficiPerTipo(m_calcoliDettaglioMgr, m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue), m_config.Art9saFkTsId.GetValueOrDefault(int.MinValue));

				m_calcolo.Sa = calcSaArt9.SuperficieTotale();
			}

			// St = 60% Sa + Sn
            m_calcolo.St = Arrotondamento.PerEccesso(m_calcolo.SuArt9.GetValueOrDefault(double.MinValue) + (m_calcolo.Sa.GetValueOrDefault(double.MinValue) * 0.6f), 2);
		}

		private void ElaboraCalcoliCosti()
		{
            m_calcolo.Costocmq = m_calcoliMgr.GetCostoAlMetroQuadro(m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue));

            double totaleI = m_calcolo.I1.GetValueOrDefault(double.MinValue) + m_calcolo.I2.GetValueOrDefault(double.MinValue) + m_calcolo.I3.GetValueOrDefault(double.MinValue);

			CCTabellaClassiEdificio classeEdificio = new CCTabellaClassiEdificioMgr(m_database).GetClasseEdificio(m_calcolo.Idcomune, m_istanza.SOFTWARE, totaleI);

			m_calcolo.FkCctceId = classeEdificio.Id;

			m_calcolo.Maggiorazione = classeEdificio.Maggiorazione;

            double costoCMqMaggiorato = m_calcolo.Costocmq.GetValueOrDefault(0) * (1 + m_calcolo.Maggiorazione.GetValueOrDefault(0) * 0.01f);

			m_calcolo.CostocmqMaggiorato = Arrotondamento.PerEccesso(costoCMqMaggiorato, 2);

		}

		private void ElaboraCostoEdificio()
		{
			CCICalcoloTContributoMgr tContributoMgr = new CCICalcoloTContributoMgr(m_database);

            CCICalcoloTContributo tContributo = tContributoMgr.GetByIdCalcolo(m_calcolo.Idcomune, m_calcolo.Id.GetValueOrDefault(int.MinValue));

            double costoEdificio = (m_calcolo.Sc.GetValueOrDefault(0) + m_calcolo.St.GetValueOrDefault(0)) * m_calcolo.CostocmqMaggiorato.GetValueOrDefault(0);

			tContributo.CostocEdificio = Arrotondamento.PerEccesso(costoEdificio, 2);

			tContributoMgr.Update(tContributo);
		}
	}
}
