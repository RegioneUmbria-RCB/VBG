
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.CiviliaNext.Assegnazione
{
    public class AssegnaPraticaAdapter
    {
        private ParametriRegoleInfo _parametri;
        private long _idPratica;
        private DatiProtocolloIn _datiProtocollo;

        public AssegnaPraticaAdapter(ParametriRegoleInfo parametri, long idPratica, DatiProtocolloIn protoIn)
        {
            this._parametri = parametri;
            this._idPratica = idPratica;
            this._datiProtocollo = protoIn;
        }

        public AssegnaPraticaRequest Adatta()
        {
            return new AssegnaPraticaRequest
            {
                IdPratica = this._idPratica,
                IdOperatore = this._parametri.IdOperatore,
                Assegnatari = this.GetAssegnatari(),
            };
        }

        private List<Assegnatario> GetAssegnatari()
        {
            List<Assegnatario> assegnatari = new List<Assegnatario>();

            switch (this._datiProtocollo.Flusso)
            {
                case "P":
                    {
                        return this._datiProtocollo
                            .Mittenti
                            .Amministrazione
                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                            .Select(x => new Assegnatario
                            {
                                CodiceLivelloOrganigrammaAssegnatario = x.PROT_UO,
                                RuoloAssegnatario = "Livello",
                                IsAssegnatario = true
                                //TipoAssegnazione = 0,   //per competenza
                                //TipoAssegnatario = 1    //organigramma
                            })
                            .ToList();
                    }
                default:
                    {
                        return this._datiProtocollo
                            .Destinatari
                            .Amministrazione
                            .Where(x => !String.IsNullOrEmpty(x.PROT_UO))
                            .Select(x => new Assegnatario
                            {
                                CodiceLivelloOrganigrammaAssegnatario = x.PROT_UO,
                                RuoloAssegnatario = "Livello",
                                IsAssegnatario = true
                                //TipoAssegnazione = 0,   //per competenza
                                //TipoAssegnatario = 1    //organigramma
                            })
                            .ToList();
                    }
            }

        }
    }
}
