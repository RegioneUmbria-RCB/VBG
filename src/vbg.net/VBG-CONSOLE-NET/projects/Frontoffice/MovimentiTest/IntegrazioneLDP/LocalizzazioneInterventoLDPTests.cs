using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.IntegrazioneLDP
{
    [TestClass]
    public class LocalizzazioneInterventoLDPTests
    {
        [TestMethod]
        public void Inizializzazione_con_localizzazioni_e_mappali_in_uguale_numero()
        {
            var dati = new ComplexTypePraticaDatiTerritoriali
            {
                point = new ComplexTypePoint
                {
                    x = 1.0000f,
                    y = 2.0000f
                },
                a_civici = new[]
                {
                    new ComplexTypeCivico
                    {
                        codice_strada = "1",
                        numero = "1",
                        esponente = "A"
                    },

                    new ComplexTypeCivico
                    {
                        codice_strada = "2",
                        numero = "2",
                        esponente = "B"
                    }
                },
                a_subalterni = new[]
                {
                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f1",
                        particella = "p1",
                        subalterno = "s1"
                    },

                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f2",
                        particella = "p2",
                        subalterno = "s2"
                    },
                }
            };

            var service = new LocalizzazioneInterventoLDP(dati, new LocalizzazioneServiceStub());

            var result = service.DatiLocalizzativi;


            Assert.AreEqual<int>(2, result.Count());
            Assert.AreEqual<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.AreEqual<string>("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.AreEqual<int>(2, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("2", result.ElementAt(1).Localizzazione.Civico);
            Assert.AreEqual<string>("B", result.ElementAt(1).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }

        [TestMethod]
        public void Inizializzazione_con_localizzazioni_superiori_a_mappali()
        {
            var dati = new ComplexTypePraticaDatiTerritoriali
            {
                point = new ComplexTypePoint
                {
                    x = 1.0000f,
                    y = 2.0000f
                },
                a_civici = new[]
                {
                    new ComplexTypeCivico
                    {
                        codice_strada = "1",
                        numero = "1",
                        esponente = "A"
                    },

                    new ComplexTypeCivico
                    {
                        codice_strada = "2",
                        numero = "2",
                        esponente = "B"
                    }
                },
                a_subalterni = new[]
                {
                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f1",
                        particella = "p1",
                        subalterno = "s1"
                    }
                }
            };

            var service = new LocalizzazioneInterventoLDP(dati, new LocalizzazioneServiceStub());

            var result = service.DatiLocalizzativi;


            Assert.AreEqual<int>(2, result.Count());
            Assert.AreEqual<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.AreEqual<string>("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.AreEqual<int>(2, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("2", result.ElementAt(1).Localizzazione.Civico);
            Assert.AreEqual<string>("B", result.ElementAt(1).Localizzazione.Esponente);
            Assert.IsNull(result.ElementAt(1).RiferimentiCatastali);
        }

        [TestMethod]
        public void Inizializzazione_con_mappali_superiori_a_localizzazioni()
        {
            var dati = new ComplexTypePraticaDatiTerritoriali
            {
                point = new ComplexTypePoint
                {
                    x = 1.0000f,
                    y = 2.0000f
                },
                a_civici = new[]
                {
                    new ComplexTypeCivico
                    {
                        codice_strada = "1",
                        numero = "1",
                        esponente = "A"
                    }
                },
                a_subalterni = new[]
                {
                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f1",
                        particella = "p1",
                        subalterno = "s1"
                    },

                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f2",
                        particella = "p2",
                        subalterno = "s2"
                    },
                }
            };

            var service = new LocalizzazioneInterventoLDP(dati, new LocalizzazioneServiceStub());

            var result = service.DatiLocalizzativi;


            Assert.AreEqual<int>(2, result.Count());
            Assert.AreEqual<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.AreEqual<string>("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.AreEqual<int>(1, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("1", result.ElementAt(1).Localizzazione.Civico);
            Assert.AreEqual<string>("A", result.ElementAt(1).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }

        [TestMethod]
        public void Inizializzazione_con_mappali_e_senza_localizzazioni()
        {
            var dati = new ComplexTypePraticaDatiTerritoriali
            {
                point = new ComplexTypePoint
                {
                    x = 1.0000f,
                    y = 2.0000f
                },
                a_civici = new ComplexTypeCivico[0],
                a_subalterni = new[]
                {
                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f1",
                        particella = "p1",
                        subalterno = "s1"
                    },

                    new ComplexTypeSubalterno
                    {
                        sezione = "",
                        foglio = "f2",
                        particella = "p2",
                        subalterno = "s2"
                    },
                }
            };

            var service = new LocalizzazioneInterventoLDP(dati, new LocalizzazioneServiceStub());

            var result = service.DatiLocalizzativi;


            Assert.AreEqual<int>(2, result.Count());
            Assert.AreEqual<int>(0, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("", result.ElementAt(0).Localizzazione.Civico);
            Assert.AreEqual<string>("", result.ElementAt(0).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.AreEqual<int>(0, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.AreEqual<string>("", result.ElementAt(1).Localizzazione.Civico);
            Assert.AreEqual<string>("", result.ElementAt(1).Localizzazione.Esponente);
            Assert.AreEqual<string>("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.AreEqual<string>("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.AreEqual<string>("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.AreEqual<string>("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }
    }
}
