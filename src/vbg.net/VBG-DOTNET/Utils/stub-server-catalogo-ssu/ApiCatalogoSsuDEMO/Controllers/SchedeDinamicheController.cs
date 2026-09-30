using ApiCatalogoSsuDEMO.Models;
using Microsoft.AspNetCore.Mvc;

namespace ApiCatalogoSsuDEMO.Controllers
{

    [ApiController]
    [Route("api/v1/{codiceEnte}/schede-dinamiche")]
    [Tags("SchedeDinamiche")]
    public class SchedeDinamicheController : ControllerBase
    {
        [HttpGet]
        [ProducesResponseType(typeof(List<ElementoListaSchedePerProcedimento>), 200)]
        public IActionResult GetSchedeDinamiche(
            string codiceEnte,
            [FromQuery] int[]? procedimenti = null)
        {
            var procIds = procedimenti ?? new[] { 1 };
            var lista = new List<ElementoListaSchedePerProcedimento>();

            foreach (var procId in procIds)
            {
                lista.Add(new ElementoListaSchedePerProcedimento
                {
                    Procedimento = Database.GetProcedimentiById([procId]).Select(x => new EntityBase
                    {
                        Id = x.Id,
                        Descrizione = x.Descrizione
                    }).FirstOrDefault()!,
                    Schede = new List<SchedaDinamica>
                    {
                        new()
                        {
                            Id = procId,
                            Titolo = $"Dati generali per procedimento {procId}",
                            Obbligatoria = procId % 2 == 0,
                            TipoFirma = TipoFirmaSchedaEnum.FirmaInteroModello,
                            Struttura = new StrutturaSchedaDinamica
                            {
                                Script = new ScriptScheda
                                {
                                    Using = "using System;",
                                    Caricamento = "// Codice di caricamento",
                                    Modifica = "// Codice di modifica",
                                    Salvataggio = "// Codice di salvataggio"
                                },
                                Righe = new List<RigaScheda>
                                {
                                    new()
                                    {
                                        PosizioneVerticale = 0,
                                        Multipla = false,
                                        Colonne = new()
                                        {
                                            new ColonnaScheda
                                            {
                                                PosizioneOrizzontale = 1,
                                                Testo = new()
                                                {
                                                    Id = 456,
                                                    Testo = "Questo è un titolo",
                                                    TipoTesto = TipoTestoEnum.Titolo
                                                }
                                            }
                                        }
                                    },
                                    new()
                                    {
                                        PosizioneVerticale = 1,
                                        Multipla = false,
                                        Colonne = new()
                                        {
                                            new ColonnaScheda
                                            {
                                                PosizioneOrizzontale = 1,
                                                Testo = new()
                                                {
                                                    Id = 4561,
                                                    Testo = "Questo è un testo semplice",
                                                    TipoTesto = TipoTestoEnum.TestoEsteso
                                                }
                                            }
                                        }
                                    },

                                    new()
                                    {
                                        PosizioneVerticale = 10,
                                        Multipla = false,
                                        InterrompeTabella = false,
                                        Colonne = new List<ColonnaScheda>
                                        {
                                            new()
                                            {
                                                PosizioneOrizzontale = 1,
                                                Campo = new CampoDinamico
                                                {
                                                    Id = 1,
                                                    Nome = "oggetto",
                                                    Etichetta = "Oggetto della richiesta",
                                                    Tipo = TipoCampoDinamicoEnum.Testo,
                                                    Proprieta = new List<ProprietaCampoDinamico>
                                                    {
                                                        new() { Chiave = "required", Valore = "true" },
                                                        new() { Chiave = "maxLength", Valore = "500" }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    new()
                                    {
                                        PosizioneVerticale = 20,
                                        Multipla = false,
                                        InterrompeTabella = false,
                                        Colonne = new List<ColonnaScheda>
                                        {
                                            new()
                                            {
                                                PosizioneOrizzontale = 1,
                                                Campo = new CampoDinamico
                                                {
                                                    Id = 2,
                                                    Nome = "superficie",
                                                    Etichetta = "Superficie (mq)",
                                                    Tipo = TipoCampoDinamicoEnum.NumericoDouble,
                                                    Proprieta = new List<ProprietaCampoDinamico>
                                                    {
                                                        new() { Chiave = "min", Valore = "0" },
                                                        new() { Chiave = "max", Valore = "10000" }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }

            return this.Ok(lista);
        }
    }
}
