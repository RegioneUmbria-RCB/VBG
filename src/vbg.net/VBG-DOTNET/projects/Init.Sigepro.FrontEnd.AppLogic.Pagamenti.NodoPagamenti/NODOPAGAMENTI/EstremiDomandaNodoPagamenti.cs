using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{
    public class EstremiDomandaNodoPagamenti
    {
        public int IdDomanda { get; }
        public int StepId { get; }
        public RiferimentiUtenteNodoPagamenti SoggettoDebitore { get; }
        public bool IntestatoAPersonaFisica { get; }
        public bool IntestatoAPersonaGiuridica => !this.IntestatoAPersonaFisica;

        public EstremiDomandaNodoPagamenti(int idDomanda, int stepId, AnagraficaDomanda anagrafica, IComuniService comuniService, string email)
        {
            var datiComune = comuniService.GetByCodiceComune(anagrafica.IndirizzoResidenza.CodiceComune);

            this.IdDomanda = idDomanda;
            this.StepId = stepId;
            this.IntestatoAPersonaFisica = anagrafica.TipoPersona == TipoPersonaEnum.Fisica;

            // PDM: nel caso in cui l'intestatario di una pendenza sia una persona giuridica la ragione sociale va messa nel
            // campo nome del soggettoDebitoreType.
            var nome = this.IntestatoAPersonaFisica ? anagrafica.Nome : anagrafica.Nominativo;
            var cognome = this.IntestatoAPersonaFisica ? anagrafica.Nominativo : "";
            var codiceFiscale = anagrafica.Codicefiscale;
            var indirizzo = anagrafica.IndirizzoResidenza?.Via ?? "";
            var cap = anagrafica.IndirizzoResidenza.Cap ?? "";
            var provincia = anagrafica.IndirizzoResidenza.SiglaProvincia ?? "";
            var comune = datiComune?.Comune ?? "";

            this.SoggettoDebitore = new RiferimentiUtenteNodoPagamenti(nome, cognome, codiceFiscale, comune, indirizzo, provincia, cap, email);

            this.ValidaDatiSoggettoDebitore();
        }

        private void ValidaDatiSoggettoDebitore()
        {
            this.ValidaDatoSoggetto(this.SoggettoDebitore.Nome, this.IntestatoAPersonaFisica ? "Nome" : "Ragione sociale");
            this.ValidaDatoSoggetto(this.SoggettoDebitore.Cfpi, "Codice fiscale");
            this.ValidaDatoSoggetto(this.SoggettoDebitore.Indirizzo, $"Indirizzo {(this.IntestatoAPersonaFisica ? "di residenza" : "sede legale")}");
            this.ValidaDatoSoggetto(this.SoggettoDebitore.CAP, $"CAP {(this.IntestatoAPersonaFisica ? "di residenza" : "sede legale")}");
            this.ValidaDatoSoggetto(this.SoggettoDebitore.Comune, $"Comune {(this.IntestatoAPersonaFisica ? "di residenza" : "sede legale")}");
        }

        private void ValidaDatoSoggetto(string parametro, string descrizioneParametro)
        {
            if (!string.IsNullOrEmpty(parametro))
            {
                return;
            }

            throw new ValidazioneIntestatarioPendenzaException(this.SoggettoDebitore.Nome, this.SoggettoDebitore.Cognome, $"{descrizioneParametro} non valorizzato");
        }


    }
}
