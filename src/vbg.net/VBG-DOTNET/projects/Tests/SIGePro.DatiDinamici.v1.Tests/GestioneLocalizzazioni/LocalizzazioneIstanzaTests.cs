using System;
using VBG.DatiDinamici.GestioneLocalizzazioni.StringaFormattazioneIndirizzi;
using Xunit;

namespace SIGePro.DatiDinamici.v1.Tests.GestioneLocalizzazioni
{
    public class LocalizzazioneIstanzaTests
    {
        public class FormattazioneIndirizzi
        {
            private LocalizzazioneIstanza _localizzazione;

            public FormattazioneIndirizzi()
            {
                this._localizzazione = new LocalizzazioneIstanza
                {
                    Uuid = "uuid",
                    Indirizzo = "Via Manzoni",
                    Civico = "civico",
                    Esponente = "esponente",
                    Scala = "scala",
                    Piano = "piano",
                    Interno = "interno",
                    EsponenteInterno = "espInt",
                    Km = "km",
                    Note = "note",
                    TipoLocalizzazione = "tipo",
                    Coordinate = new LocalizzazioneIstanza.Coordinata
                    {
                        Latitudine = "latitudine",
                        Longitudine = "longitudine"
                    },
                    Mappali = new LocalizzazioneIstanza.RiferimentiCatastali
                    {
                        TipoCatasto = "tipoCatasto",
                        Foglio = "foglio",
                        Particella = "particellza",
                        Sub = "sub"
                    }
                };
            }


            [Fact]
            public void Sostituzione_indirizzo()
            {
                var espressione = "{indirizzo}";

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(this._localizzazione.Indirizzo, result);

                result = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(this._localizzazione.Indirizzo, result);
            }

            [Fact]
            public void Sostituzione_civico()
            {
                var espressione = "{civico}";
                var confronto = this._localizzazione.Civico;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_esponente()
            {
                var espressione = "{esponente}";
                var confronto = this._localizzazione.Esponente;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_scala()
            {
                var espressione = "{scala}";
                var confronto = this._localizzazione.Scala;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_piano()
            {
                var espressione = "{piano}";
                var confronto = this._localizzazione.Piano;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }


            [Fact]
            public void Sostituzione_interno()
            {
                var espressione = "{interno}";
                var confronto = this._localizzazione.Interno;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }


            [Fact]
            public void Sostituzione_esponente_interno()
            {
                var espressione = "{esponenteinterno}";
                var confronto = this._localizzazione.EsponenteInterno;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }


            [Fact]
            public void Sostituzione_km()
            {
                var espressione = "{km}";
                var confronto = this._localizzazione.Km;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_note()
            {
                var espressione = "{note}";
                var confronto = this._localizzazione.Note;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }


            [Fact]
            public void Sostituzione_tipo_localizzazione()
            {
                var espressione = "{tipo}";
                var confronto = this._localizzazione.TipoLocalizzazione;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }


            [Fact]
            public void Sostituzione_coordinate()
            {
                var espressione = "{coordinate}";
                var confronto = this._localizzazione.Coordinate.ToString();

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_coordinate_vuote()
            {
                var espressione = "{coordinate}";
                var confronto = String.Empty;

                this._localizzazione.Coordinate = null;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_mappali()
            {
                var espressione = "{mappali}";
                var confronto = this._localizzazione.Mappali.ToString();

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_mappali_vuoti()
            {
                var espressione = "{mappali}";
                var confronto = String.Empty;

                this._localizzazione.Mappali = null;

                var result1 = this._localizzazione.ToString(espressione);
                var result2 = this._localizzazione.ToString(espressione.ToUpper());

                Assert.Equal(confronto, result1);
                Assert.Equal(confronto, result2);
            }

            [Fact]
            public void Sostituzione_spazi_centrali_consecutivi()
            {
                this._localizzazione = new LocalizzazioneIstanza();

                var espressione = "A {indirizzo} B";
                var confronto = "A B";

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(confronto, result);
            }

            [Fact]
            public void Sostituzione_spazi_centrali_consecutivi2()
            {
                this._localizzazione = new LocalizzazioneIstanza();

                var espressione = "A {indirizzo} {civico} {esponente} B {scala} {piano}";
                var confronto = "A B";

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(confronto, result);
            }

            [Fact]
            public void Sostituzione_spazi_iniziali_consecutivi()
            {
                this._localizzazione = new LocalizzazioneIstanza();

                var espressione = "{indirizzo} A";
                var confronto = "A";

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(confronto, result);
            }

            [Fact]
            public void Sostituzione_spazi_finali_consecutivi()
            {
                this._localizzazione = new LocalizzazioneIstanza();

                var espressione = "A {indirizzo}";
                var confronto = "A";

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(confronto, result);
            }

            [Fact]
            public void Se_l_espressione_di_formattazione_non_è_specificata_restituisce_via_e_civico()
            {
                var espressione = String.Empty;
                var confronto = this._localizzazione.Indirizzo + " " + this._localizzazione.Civico;

                var result = this._localizzazione.ToString(espressione);

                Assert.Equal(confronto, result);
            }
        }

    }
}
