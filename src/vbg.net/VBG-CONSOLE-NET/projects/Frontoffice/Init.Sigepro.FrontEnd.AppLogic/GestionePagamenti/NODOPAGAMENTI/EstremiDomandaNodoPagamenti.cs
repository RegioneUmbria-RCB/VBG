using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI
{
    public class EstremiDomandaNodoPagamenti
    {
        public readonly int IdDomanda;
        public readonly int StepId;

        public EstremiDomandaNodoPagamenti(int idDomanda, int stepId, AnagraficaDomanda anagrafica, IComuniService comuniService, string email)
        {
            var datiComune = comuniService.GetDatiComune(anagrafica.IndirizzoResidenza.CodiceComune);

            this.IdDomanda = idDomanda;
            this.StepId = stepId;

            // PDM: nel caso in cui l'intestatario di una pendenza sia una persona giuridica la ragione sociale va messa nel
            // campo nome del soggettoDebitoreType.
            var nome = anagrafica.TipoPersona == TipoPersonaEnum.Fisica ? anagrafica.Nome : anagrafica.Nominativo;
            var cognome = anagrafica.TipoPersona == TipoPersonaEnum.Fisica ? anagrafica.Nominativo : "";
            var codiceFiscale = anagrafica.Codicefiscale;
            var indirizzo = anagrafica.IndirizzoResidenza.Via;
            var cap = anagrafica.IndirizzoResidenza.Cap;
            var provincia = anagrafica.IndirizzoResidenza.SiglaProvincia;
            var comune = datiComune.Comune;

            this.RiferimentiUtenteNodoPagamenti = new RiferimentiUtenteNodoPagamenti(nome, cognome, codiceFiscale, comune, indirizzo, provincia, cap, email);
        }

        public RiferimentiUtenteNodoPagamenti RiferimentiUtenteNodoPagamenti { get; }
    }
}
