using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    public class EstremiDomandaEntraNext
    {
        public readonly int IdDomanda;
        public readonly int StepId;

        public readonly string TipoSoggetto;
        public readonly string RagioneSociale;
        public readonly string Nome;
        public readonly string Cognome;
        public readonly string CodiceFiscale;
        public readonly string PartitaIva;
        public readonly string Email;
        public readonly string Indirizzo;
        public readonly string Comune;
        public readonly string Cap;
        public readonly string Provincia;
        public readonly string Localita;

        public EstremiDomandaEntraNext(int idDomanda, int stepId, AnagraficaDomanda anagrafica, IComuniService comuniService)
        {
            var datiComune = comuniService.GetDatiComune(anagrafica.IndirizzoResidenza.CodiceComune);

            this.IdDomanda = idDomanda;
            this.StepId = stepId;
            this.TipoSoggetto = anagrafica.TipoPersona == TipoPersonaEnum.Fisica ? "F" : "G";
            this.RagioneSociale = anagrafica.Nominativo;
            this.Nome = anagrafica.Nome;
            this.Cognome = anagrafica.Nominativo;
            this.CodiceFiscale = anagrafica.Codicefiscale;
            this.PartitaIva = anagrafica.PartitaIva;
            this.Email = anagrafica.Contatti.Email;
            this.Indirizzo = anagrafica.IndirizzoResidenza.Via;
            this.Cap = anagrafica.IndirizzoResidenza.Cap;
            this.Provincia = anagrafica.IndirizzoResidenza.SiglaProvincia;
            this.Comune = datiComune.Comune;
            this.Localita = datiComune.Comune;
        }
    }
}
