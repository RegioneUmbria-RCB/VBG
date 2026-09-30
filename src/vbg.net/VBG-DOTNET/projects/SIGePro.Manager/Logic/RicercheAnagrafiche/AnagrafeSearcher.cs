using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Parix;
using PersonalLib2.Data;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche
{
    /// <summary>
    /// Descrizione di riepilogo per AnagrafeSearcher.
    /// </summary>
    public class AnagrafeSearcher : AnagrafeSearcherBase
    {
        public AnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, string className) : base(verticalizzazioniFactory, className)
        {
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            var filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                CODICEFISCALE = codiceFiscale,
                FLAG_DISABILITATO = "0"
            };

            return (Anagrafe)this.SigeproDb.GetClass(filtro);
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            var filtro = new Anagrafe
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
            var filtro = new Anagrafe
            {
                IDCOMUNE = this.IdComune,
                PARTITAIVA = partitaIva,
                FLAG_DISABILITATO = "0"
            };

            var anagrafeRet = this.SigeproDb.GetClass(filtro);

            // Non mi piace ma al momento non c'è alternativa
            var vertParix = new ConfigurazioneParix(this._verticalizzazioniFactory, x =>
            {
                x.IdComune = this.IdComune;
                x.IdComuneAlias = this.Alias;
                x.Database = this.SigeproDb;
                return x;
            });

            if (anagrafeRet == null || (vertParix.IsVerticalizzazioneAttiva && vertParix.CercaSoloCf))
                anagrafeRet = this.ByCodiceFiscaleImp(TipoPersona.PersonaGiuridica, partitaIva);

            return anagrafeRet;

        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            var filtro = new Anagrafe
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
