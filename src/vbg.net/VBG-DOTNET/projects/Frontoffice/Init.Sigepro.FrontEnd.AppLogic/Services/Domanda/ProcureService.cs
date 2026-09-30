using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda;

//using Init.Sigepro.FrontEnd.AppLogic.Model.Domanda.Commands;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.Services.Domanda
{
    public class ProcureService
    {
        private readonly ISalvataggioDomandaStrategy _salvataggioStrategy;
        private readonly IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;

        public class DatiProcuratore
        {
            public string CodiceFiscaleSottoscrivente { get; set; }
            public string CodiceFiscaleProcuratore { get; set; }
        }

        public ProcureService(ISalvataggioDomandaStrategy salvataggioStrategy, IAllegatiDomandaFoRepository allegatiDomandaFoRepository)
        {
            this._salvataggioStrategy = salvataggioStrategy;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
        }


        public void ImpostaProcuratori(int idDomanda, List<DatiProcuratore> nuoviProcuratori)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var datiProcureDaSincronizzare = nuoviProcuratori.Select(x =>
                new SincronizzaProcureCommand.DatiProcuraDaSincronizzare
                {
                    CodiceFiscaleProcuratore = x.CodiceFiscaleProcuratore,
                    CodiceFiscaleSottoscrivente = x.CodiceFiscaleSottoscrivente
                }
            );

            domanda.WriteInterface.Procure.Sincronizza(new SincronizzaProcureCommand(datiProcureDaSincronizzare));

            this._salvataggioStrategy.Salva(domanda);
        }

        //public void EliminaProcure(int idDomanda)
        //{
        //    var domanda = _salvataggioStrategy.GetById(idDomanda);

        //    foreach (var procura in domanda.Procure.All())
        //    {
        //        if (!procura.IsCodiceOggettoProcuraNull() && !String.IsNullOrEmpty(procura.CodiceOggettoProcura))
        //            _allegatiDomandaFoRepository.EliminaAllegato(_aliasSoftwareResolver.AliasComune, idDomanda, Convert.ToInt32(procura.CodiceOggettoProcura));
        //    }

        //    domanda.Procure.Elimina();

        //    _salvataggioStrategy.Salva(domanda);
        //}

        //public void ModificaProcure(int idDomanda, List<DatiProcuraDaEliminare> procureDaEliminare, List<DatiProcuraDaAggiungere> procureDaAggiungere )
        //{
        //    var domanda = _salvataggioStrategy.GetById(idDomanda);

        //    foreach (var p in procureDaEliminare)
        //    {
        //        var datiProcura = domanda.Procure.Get(p.CodiceFiscaleSottoscrivente, p.CodiceFiscaleProcuratore);

        //        if (!String.IsNullOrEmpty(datiProcura.CodiceOggettoProcura))
        //            _allegatiDomandaFoRepository.EliminaAllegato(_aliasSoftwareResolver.AliasComune, idDomanda , Convert.ToInt32(datiProcura.CodiceOggettoProcura));

        //        domanda.Procure.Elimina(p.CodiceFiscaleSottoscrivente, p.CodiceFiscaleProcuratore);
        //    }

        //    foreach (var p in procureDaAggiungere)
        //    {
        //        domanda.Procure.Aggiungi(p.CodiceFiscaleSottoscrivente, p.CodiceFiscaleProcuratore);
        //    }

        //    _salvataggioStrategy.Salva(domanda);
        //}
        public void CaricaDocumentoIdentitaProcura(int idDomanda, string codiceFiscaleSottoscrittore, string codiceFiscaleProcuratore, BinaryFile file)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, false);

            domanda.WriteInterface.Procure.AllegaDocumentoIdentita(codiceFiscaleSottoscrittore, codiceFiscaleProcuratore, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void CaricaOggettoProcura(int idDomanda, string codiceFiscaleSottoscrittore, string codiceFiscaleProcuratore, BinaryFile file)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, file, false);

            domanda.WriteInterface.Procure.AllegaFileProcura(codiceFiscaleSottoscrittore, codiceFiscaleProcuratore, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void CaricaAllegatiProcura(int idDomanda, string codiceFiscaleSottoscrittore, string codiceFiscaleProcuratore, int codiceOggettoProcura, int? codiceOggettoDocumentoIdentita)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggettoProcura);

            domanda.WriteInterface.Procure.AllegaFileProcura(codiceFiscaleSottoscrittore, codiceFiscaleProcuratore, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            if (codiceOggettoDocumentoIdentita.HasValue)
            {
                esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggettoDocumentoIdentita.Value);

                domanda.WriteInterface.Procure.AllegaDocumentoIdentita(codiceFiscaleSottoscrittore, codiceFiscaleProcuratore, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);
            }

            this._salvataggioStrategy.Salva(domanda);
        }

        public void CaricaOggettoProcura(int idDomanda, string codiceFiscaleSottoscrittore, string codiceFiscaleProcuratore, int codiceOggetto)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            var esitoSalvataggio = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, codiceOggetto);

            domanda.WriteInterface.Procure.AllegaFileProcura(codiceFiscaleSottoscrittore, codiceFiscaleProcuratore, esitoSalvataggio.CodiceOggetto, esitoSalvataggio.NomeFile, esitoSalvataggio.FirmatoDigitalmente);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void EliminaOggettoProcura(int idDomanda, string codiceFiscaleProcurato, string codiceFiscaleProcuratore)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Procure.EliminaFileProcura(codiceFiscaleProcurato, codiceFiscaleProcuratore);

            this._salvataggioStrategy.Salva(domanda);
        }

        public void EliminaOggettoDocIdentita(int idDomanda, string codiceFiscaleProcurato, string codiceFiscaleProcuratore)
        {
            var domanda = this._salvataggioStrategy.GetById(idDomanda);

            domanda.WriteInterface.Procure.EliminaDocumentoIdentita(codiceFiscaleProcurato, codiceFiscaleProcuratore);

            this._salvataggioStrategy.Salva(domanda);
        }
    }
}
