using Init.Sigepro.FrontEnd.AppLogic.Adapters.StcPartialAdapters;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using System.Text;
using System.Text.Json;

namespace VBG.AppLogic.SSU.STC
{
    public class ProcedimentiSsuPartialAdapter : IStcPartialAdapter
    {
        private readonly ParametriHelper _parametriHelper = new ParametriHelper();

        public void Adapt(IDomandaOnlineReadInterface readInterface, DettaglioPraticaType dettaglioPratica)
        {
            var listaProcedimenti = new List<ProcedimentoStc>();
            var allegatiProcedimento = new List<AllegatoProcedimentoStc>();

            //creo la struttura da salvare
            foreach (var procedimento in readInterface.Ssu.ProcedimentiPrincipali?.Procedimenti ?? [])
            {
                listaProcedimenti.Add(new ProcedimentoStc
                {
                    Codice = procedimento.Id,
                    Descrizione = procedimento.Descrizione,
                    Allegati = this.GetAllegatiDaIdEndo(readInterface, procedimento.Id).ToList(),
                    Schede = this.GetSchedeDaIdEndo(readInterface, procedimento.Id).ToList(),
                    Fattispecie = procedimento.FattispeciePrimarie?.Select(x => new FattispecieStc
                    {
                        Codice = x.Id,
                        Descrizione = x.Descrizione
                    })
                    .ToList() ?? [],
                    AttivatoDa = null
                });

                foreach (var primaria in procedimento.FattispeciePrimarie ?? [])
                {
                    foreach (var secondaria in primaria.FattispecieSecondarie)
                    {
                        listaProcedimenti.Add(new ProcedimentoStc
                        {
                            Codice = secondaria.Procedimento.Id,
                            Descrizione = secondaria.Procedimento.Descrizione,
                            Allegati = this.GetAllegatiDaIdEndo(readInterface, secondaria.Procedimento.Id).ToList(),
                            Schede = this.GetSchedeDaIdEndo(readInterface, secondaria.Procedimento.Id).ToList(),
                            Fattispecie = [
                                new()
                                {
                                    Codice = secondaria.Id,
                                    Descrizione = secondaria.Descrizione
                                }
                            ],
                            AttivatoDa = new AttivazioneProcedimentoStc
                            {
                                Procedimento = procedimento.Id,
                                Fattispecie = primaria.Id
                            }
                        });

                    }
                }
            }

            //serializzo l'oggetto e lo converto in base64
            var listaProcedimentiSerialized = JsonSerializer.Serialize(listaProcedimenti);
            var listaProcedimentiBase64 = Convert.ToBase64String(Encoding.UTF8.GetBytes(listaProcedimentiSerialized));

            var datiDaAggiungere = new ParametroType[]
            {
                this._parametriHelper.CreaParametroType("SSU_PROCEDIMENTI", "SSU_PROCEDIMENTI", listaProcedimentiBase64)
            };

            //aggiungo i nuovi dati senza sovrascrivere quelli già presenti
            dettaglioPratica.altriDati = dettaglioPratica.altriDati.Union(datiDaAggiungere).ToArray();

        }

        private IEnumerable<AllegatoProcedimentoStc> GetAllegatiDaIdEndo(IDomandaOnlineReadInterface readInterface, int idEndo)
        {
            var allegati = readInterface.Documenti.Endo
                                .GetByIdEndo(idEndo)
                                .Where(x => x.AllegatoDellUtente != null)
                                .Select(x => new AllegatoProcedimentoStc
                                {
                                    Codice = x.Id,
                                    CodiceOggetto = x.AllegatoDellUtente.CodiceOggetto,
                                    Descrizione = x.Descrizione,
                                    NomeFile = x.AllegatoDellUtente.NomeFile
                                });
            return allegati;
        }

        private IEnumerable<SchedaProcedimentoStc> GetSchedeDaIdEndo(IDomandaOnlineReadInterface readInterface, int idEndo)
        {
            var riepiloghi = readInterface.RiepiloghiSchedeDinamiche
                                            .GetByCodiceEndo(idEndo)
                                            .Where(riepilogo => riepilogo.AllegatoDellUtente is not null)
                                            .Select(riepilogo => new RiepilogoSchedaProcedimentoStc
                                            {
                                                CodiceOggetto = riepilogo.AllegatoDellUtente.CodiceOggetto,
                                                NomeFile = riepilogo.AllegatoDellUtente.NomeFile
                                            });

            var datiSchede = readInterface.DatiDinamici.GetModelliEndo(idEndo).Select(modello => new SchedaProcedimentoStc
            {
                Codice = modello.Modello.IdModello,
                Riepiloghi = riepiloghi.ToList()
            });

            return datiSchede;
        }
    }
}
