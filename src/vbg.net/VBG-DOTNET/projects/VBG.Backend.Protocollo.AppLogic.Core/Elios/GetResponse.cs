using EliosWSProtocollazioneSoapClient;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class GetResponse
    {
        public string Anno { get; private set; }
        public string Classifica { get; private set; }
        public string Data { get; private set; }
        public MittDestOutType[] MittentiDestinatari { get; private set; }
        public string Numero { get; private set; }
        public string Oggetto { get; private set; }
        public AllegatoResponseType[] Allegati { get; private set; }
        public wReFascicolo Fascicolo { get; private set; }
        public wReFascicolo FascicoloPadre { get; set; }

        internal static GetResponse FromWSResponse(wReProtocollo response)
        {
            if (response == null)
            {
                throw new Exception("La risposta ottenuta dal WS è nulla");
            }

            if (response.Esito != 0)
            {
                throw new Exception($"{response.MessaggioEsito} Codice: {response.Esito}");
            }

            return new GetResponse
            {
                Allegati = !response.Allegati.Any()
                                ? new AllegatoResponseType[] { }
                                : response
                                    .Allegati
                                    .Select(x => new AllegatoResponseType
                                    {
                                        IDBase = x.Key,
                                        Commento = x.Nome,
                                        Serial = x.Nome,
                                    }
                                     )
                                    .ToArray(),
                Anno = response.Anno.ToString(),
                Classifica = $"{response.Categoria} {response.Classe} {response.Sottoclasse}",
                Data = response.DataRegistrazione,
                MittentiDestinatari = response
                                        .Anagrafiche?
                                        .Select(x => new MittDestOutType
                                        {
                                            CognomeNome = x.Tipologia == "Giuridica" ? x.RagioneSociale : $"{x.Cognome} {x.Nome}"
                                        })
                                        .ToArray(),
                Numero = response.Numero.ToString(),
                Oggetto = response.Oggetto,
                // dati del fascicolo
                Fascicolo = response.Fascicoli.Any() ? response.Fascicoli.First() : null,

            };
        }

        internal DatiProtocolloLettoResponseType ToDatiProtocolloLetto()
        {
            var response = new DatiProtocolloLettoResponseType
            {
                AnnoProtocollo = Anno,
                Classifica = Classifica,
                DataProtocollo = Data,
                MittentiDestinatari = MittentiDestinatari,
                NumeroProtocollo = Numero,
                Oggetto = Oggetto,
                Allegati = Allegati,
            };

            if (this.Fascicolo != null)
            {
                response.AnnoNumeroPratica = $"{this.Fascicolo.Anno}";
                response.NumeroPratica = this.Fascicolo.Numero.ToString();
            }

            return response;
        }

        internal DatiProtocolloFascicolatoResponseType ToDatiProtocolloFascicolato()
        {
            if (this.Fascicolo == null)
            {
                return new DatiProtocolloFascicolatoResponseType
                {
                    Fascicolato = EnumFascicolatoType.no
                };
            }

            return new DatiProtocolloFascicolatoResponseType
            {
                AnnoFascicolo = this.Fascicolo.Anno.ToString(),
                Fascicolato = EnumFascicolatoType.si,
                NumeroFascicolo = this.Fascicolo.Numero.ToString()
            };
        }
    }
}