using Init.SIGePro.Data;
using Init.SIGePro.Verticalizzazioni;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
    /// <summary>
    /// Descrizione di riepilogo per AnagrafeSearcher.
    /// </summary>
    public class AnagrafeSearcher : AnagrafeSearcherBase
    {
        public AnagrafeSearcher(string className) : base(className)
        {
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            Anagrafe filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                CODICEFISCALE = codiceFiscale,
                FLAG_DISABILITATO = "0"
            };

            return (Anagrafe)this.SigeproDb.GetClass(filtro);
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            Anagrafe filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                CODICEFISCALE = codiceFiscale,
                TIPOANAGRAFE = tipoPersona == TipoPersona.PersonaFisica ? "F" : "G",
                FLAG_DISABILITATO = "0"
            };

            return this.SigeproDb.GetClass(filtro);
        }

        public override Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            Anagrafe filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                PARTITAIVA = partitaIva,
                FLAG_DISABILITATO = "0"
            };

            Anagrafe anagrafeRet = this.SigeproDb.GetClass(filtro);

            // Non mi piace ma al momento non c'è alternativa
            VerticalizzazioneWsanagrafeParix vertParix = new VerticalizzazioneWsanagrafeParix(this.Alias, "TT");

            if (anagrafeRet == null || (vertParix.Attiva && vertParix.CercaSoloCf))
                anagrafeRet = this.ByCodiceFiscaleImp(TipoPersona.PersonaGiuridica, partitaIva);

            return anagrafeRet;

        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            Anagrafe filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                NOME = nome,
                NOMINATIVO = cognome,
                FLAG_DISABILITATO = "0"
            };

            return this.SigeproDb.GetClassList(filtro).ToList<Anagrafe>();
        }
    }
}
