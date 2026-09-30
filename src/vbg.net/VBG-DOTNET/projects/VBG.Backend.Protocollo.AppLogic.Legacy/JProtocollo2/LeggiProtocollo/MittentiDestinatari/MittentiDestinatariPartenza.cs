using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariPartenza : ILeggiProtoMittentiDestinatari
    {
        leggiProtocolloResponseRispostaLeggiProtocollo _response;

        public MittentiDestinatariPartenza(leggiProtocolloResponseRispostaLeggiProtocollo response)
        {
            _response = response;
        }

        public string InCaricoA
        {
            get
            {
                return _response.protocollo.mittenteInterno.corrispondente.codice;
            }
        }

        public string InCaricoADescrizione
        {
            get
            {
                return _response.protocollo.mittenteInterno.corrispondente.descrizione;
            }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            var retVal = new List<MittDestOutType>();

            foreach (var mittente in _response.protocollo.soggetti.Items)
            {
                if (mittente is soggetto)
                    retVal.Add(new MittDestOutType { CognomeNome = ((soggetto)mittente).denominazione });

                if (mittente is anagrafica)
                {
                    retVal.Add(new MittDestOutType
                    {
                        CognomeNome = ((anagrafica)mittente).denominazione,
                        IdSoggetto = ((anagrafica)mittente).codice
                    });
                }

                if (mittente is amministrazione)
                {
                    retVal.Add(new MittDestOutType
                    {
                        CognomeNome = ((amministrazione)mittente).ente.descrizione,
                        IdSoggetto = ((amministrazione)mittente).ente.codice
                    });
                }
            }

            return retVal.ToArray();
        }


        public string Flusso
        {
            get { return ProtocolloConstants.COD_PARTENZA; }
        }
    }
}
