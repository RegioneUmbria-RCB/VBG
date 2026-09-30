using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo.JProtocollo2.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariArrivo : ILeggiProtoMittentiDestinatari
    {
        leggiProtocolloResponseRispostaLeggiProtocollo _response;

        public MittentiDestinatariArrivo(leggiProtocolloResponseRispostaLeggiProtocollo response)
        {
            _response = response;
        }

        public string InCaricoA
        {
            get 
            {
                return String.Join("\r\n",_response.protocollo.smistamenti
                                        .Where(x => x.corrispondente != null)
                                        .Select(y => y.corrispondente.codice));
            }
        }

        public string InCaricoADescrizione
        {
            get
            {
                return String.Join("\r\n", _response.protocollo.smistamenti
                                          .Where(x => x.corrispondente != null)
                                          .Select(y => y.corrispondente.descrizione));
            }
        }

        public MittDestOut[] GetMittenteDestinatario()
        {
            var retVal = new List<MittDestOut>();

            foreach (var mittente in _response.protocollo.soggetti.Items)
            {
                if (mittente is soggetto)
                    retVal.Add(new MittDestOut { CognomeNome = ((soggetto)mittente).denominazione });

                if (mittente is anagrafica)
                {
                    retVal.Add(new MittDestOut
                    {
                        CognomeNome = ((anagrafica)mittente).denominazione,
                        IdSoggetto = ((anagrafica)mittente).codice
                    });
                }

                if (mittente is amministrazione)
                {
                    retVal.Add(new MittDestOut
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
            get { return ProtocolloConstants.COD_ARRIVO; }
        }
    }
}
