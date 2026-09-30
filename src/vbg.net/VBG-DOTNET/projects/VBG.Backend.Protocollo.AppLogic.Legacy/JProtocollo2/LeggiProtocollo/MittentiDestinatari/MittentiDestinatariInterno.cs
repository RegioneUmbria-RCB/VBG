using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo.MittentiDestinatari
{
    public class MittentiDestinatariInterno : ILeggiProtoMittentiDestinatari
    {
        leggiProtocolloResponseRispostaLeggiProtocollo _response;

        public MittentiDestinatariInterno(leggiProtocolloResponseRispostaLeggiProtocollo response)
        {
            _response = response;
        }

        //public string InCaricoA
        //{
        //    get { return _response.protocollo.mittenteInterno.corrispondente.codice; }
        //}

        //public string InCaricoADescrizione
        //{
        //    get { return _response.protocollo.mittenteInterno.corrispondente.descrizione; }
        //}

        //public MittDestOut[] GetMittenteDestinatario()
        //{
        //    var smistamento = _response.protocollo.smistamenti.First();

        //    return new MittDestOut[] 
        //    { 
        //        new MittDestOut 
        //        { 
        //            IdSoggetto = smistamento.corrispondente.codice, 
        //            CognomeNome = smistamento.corrispondente.descrizione 
        //        } 
        //    };
        //}


        public string Flusso
        {
            get { return ProtocolloConstants.COD_INTERNO; }
        }

        public string InCaricoA => ""; //_response.protocollo.smistamenti.First().corrispondente.codice;

        public string InCaricoADescrizione
        {
            get
            {
                //Grandissima VACCATA, ma era il modo più veloce per sistemare la problematica, va modificata la classe che restituisce i dati a java, 
                //va creata una classe InCaricoA che prende codice e descrizione e tornarla come array, poi lato java va gestita.
                //Bisogna però studiarla molto bene perchè tutti i protocolli che hanno la funzionalità LEGGI utilizzano le property InCaricoA e InCaricoADescrizione.

                return String.Join("<br>", _response.protocollo.smistamenti.Select(x => $"[{x.corrispondente.codice}] - {x.corrispondente.descrizione}"));
            }
        }

        public MittDestOutType[] GetMittenteDestinatario()
        {
            return new MittDestOutType[]
            {
                new MittDestOutType
                {
                    IdSoggetto = "", //_response.protocollo.mittenteInterno.corrispondente.codice,
                    CognomeNome = $"[{_response.protocollo.mittenteInterno.corrispondente.codice}] - {_response.protocollo.mittenteInterno.corrispondente.descrizione}"
                }
            };
        }
    }
}
