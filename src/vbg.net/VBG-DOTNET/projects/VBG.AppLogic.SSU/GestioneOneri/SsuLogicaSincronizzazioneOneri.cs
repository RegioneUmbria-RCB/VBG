using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.SIGePro.Manager.DTO;
using VBG.AppLogic.SSU.APICatalogoServizi.Client;

namespace VBG.AppLogic.SSU.GestioneOneri
{
    internal class SsuLogicaSincronizzazioneOneri
    {
        private readonly DomandaOnline _domanda;
        private readonly SsuOneriService _ssuOneriService;

        public SsuLogicaSincronizzazioneOneri(DomandaOnline domanda, SsuOneriService ssuOneriService)
        {
            this._domanda = domanda;
            this._ssuOneriService = ssuOneriService;
            this.ComportamentoOneriSenzaImporto = ComportamentoSincronizzazioneOneriSenzaImporto.Includi;
        }

        public ComportamentoSincronizzazioneOneriSenzaImporto ComportamentoOneriSenzaImporto { get; set; }

        internal async Task SincronizzaOneriAsync()
        {
            IDomandaOnlineReadInterface readInterface = this._domanda.ReadInterface;
            IDomandaOnlineWriteInterface writeInterface = this._domanda.WriteInterface;

            var listaOneriSsu = await this._ssuOneriService.GetOneriAsync(this._domanda.DataKey.IdPresentazione);

            listaOneriSsu = listaOneriSsu.Select(x => new ElementoListaOneriProcedimento
            {
                Procedimento = x.Procedimento,
                Oneri = x.Oneri.Where(o => this.ComportamentoOneriSenzaImporto == ComportamentoSincronizzazioneOneriSenzaImporto.Includi ||
                                            this.GetImporto(o) > 0.0f).ToList()
            })
            .ToList();

            var listaNuoviId = listaOneriSsu.SelectMany(el => el.Oneri.Select(o => new IdentificativoOnereSelezionato(o.Id, el.Procedimento.Descrizione, "E")));

            writeInterface.Oneri.EliminaOneriWhereCodiceCausaleNotIn(listaNuoviId);

            foreach (var pair in listaOneriSsu.SelectMany(el => el.Oneri.OrderBy(o => o.Descrizione).Select(o => new { el.Procedimento, Onere = o })))
            {
                var causale = new BaseDto<int, string>
                {
                    Codice = pair.Onere.Id,
                    Descrizione = pair.Onere.Descrizione
                };

                var endoOrigine = new BaseDto<int, string>
                {
                    Codice = pair.Procedimento.Id,
                    Descrizione = pair.Procedimento.Descrizione
                };

                var provenienza = ProvenienzaOnere.Endo;
                var importo = this.GetImporto(pair.Onere);
                var note = pair.Onere.DescrizioneEstesa;

                this._domanda.WriteInterface.Oneri.CreaOAggiornaDatiOnere(causale, provenienza, endoOrigine, importo, note ?? "");
            }
        }

        private float GetImporto(OnereProcedimento onere)
        {
            if (string.IsNullOrEmpty(onere.CampoDinamicoImporto))
            {
                return onere.Importo.HasValue ? (float)onere.Importo.Value : 0f;
            }

            var idCampoDInamico = int.TryParse(onere.CampoDinamicoImporto, out int value) ? value : 0;

            var valoreCampo = this._domanda.ReadInterface.DatiDinamici.DatiDinamici.Where(x => x.IdCampo == idCampoDInamico).FirstOrDefault();

            if (valoreCampo != null)
            {
                return Convert.ToSingle(valoreCampo.Valore);
            }

            return onere.Importo.HasValue ? (float)onere.Importo.Value : 0f;
        }
    }

}
