using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena;
using Init.Sigepro.FrontEnd.AppLogicTests.IntegrazioneLDP;
using System.Linq;
using Xunit;

namespace MovimentiTest.IntegrazioneLDP
{
    public class LocalizzazioneInterventoLDPTests
    {
        [Fact]
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


            Assert.Equal<int>(2, result.Count());
            Assert.Equal<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.Equal("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.Equal("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.Equal("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.Equal("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.Equal("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.Equal<int>(2, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.Equal("2", result.ElementAt(1).Localizzazione.Civico);
            Assert.Equal("B", result.ElementAt(1).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.Equal("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.Equal("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.Equal("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }

        [Fact]
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


            Assert.Equal<int>(2, result.Count());
            Assert.Equal<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.Equal("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.Equal("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.Equal("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.Equal("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.Equal("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.Equal<int>(2, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.Equal("2", result.ElementAt(1).Localizzazione.Civico);
            Assert.Equal("B", result.ElementAt(1).Localizzazione.Esponente);
            Assert.Null(result.ElementAt(1).RiferimentiCatastali);
        }

        [Fact]
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


            Assert.Equal<int>(2, result.Count());
            Assert.Equal<int>(1, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.Equal("1", result.ElementAt(0).Localizzazione.Civico);
            Assert.Equal("A", result.ElementAt(0).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.Equal("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.Equal("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.Equal("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.Equal(1, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.Equal("1", result.ElementAt(1).Localizzazione.Civico);
            Assert.Equal("A", result.ElementAt(1).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.Equal("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.Equal("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.Equal("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }

        [Fact]
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


            Assert.Equal<int>(2, result.Count());
            Assert.Equal<int>(0, result.ElementAt(0).Localizzazione.CodiceStradario);
            Assert.Equal("", result.ElementAt(0).Localizzazione.Civico);
            Assert.Equal("", result.ElementAt(0).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(0).RiferimentiCatastali.Sezione);
            Assert.Equal("f1", result.ElementAt(0).RiferimentiCatastali.Foglio);
            Assert.Equal("p1", result.ElementAt(0).RiferimentiCatastali.Particella);
            Assert.Equal("s1", result.ElementAt(0).RiferimentiCatastali.Sub);


            Assert.Equal<int>(0, result.ElementAt(1).Localizzazione.CodiceStradario);
            Assert.Equal("", result.ElementAt(1).Localizzazione.Civico);
            Assert.Equal("", result.ElementAt(1).Localizzazione.Esponente);
            Assert.Equal("", result.ElementAt(1).RiferimentiCatastali.Sezione);
            Assert.Equal("f2", result.ElementAt(1).RiferimentiCatastali.Foglio);
            Assert.Equal("p2", result.ElementAt(1).RiferimentiCatastali.Particella);
            Assert.Equal("s2", result.ElementAt(1).RiferimentiCatastali.Sub);
        }
    }
}
