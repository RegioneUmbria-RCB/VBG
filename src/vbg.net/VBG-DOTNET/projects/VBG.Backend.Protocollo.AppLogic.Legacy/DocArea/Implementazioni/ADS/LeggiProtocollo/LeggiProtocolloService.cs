using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.ServiceModel;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Implementazioni.ADS.LeggiProtocollo
{
    public class LeggiProtocolloService : ILeggiProtocolloService
    {
        private IProtocolloSerializer _serializer;
        private string _endPointAddress;
        private string _userName;
        private string _password;


        public LeggiProtocolloService(string endPointAddress, string userName, string password, IProtocolloSerializer serializer)
        {
            if (string.IsNullOrEmpty(endPointAddress))
            {
                throw new ArgumentException($"'{nameof(endPointAddress)}' non può essere Null o vuoto", nameof(endPointAddress));
            }

            if (string.IsNullOrEmpty(userName))
            {
                throw new ArgumentException($"'{nameof(userName)}' non può essere Null o vuoto", nameof(userName));
            }

            if (string.IsNullOrEmpty(password))
            {
                throw new ArgumentException($"'{nameof(password)}' non può essere Null o vuoto", nameof(password));
            }

            this._serializer = serializer ?? throw new ArgumentNullException(nameof(serializer));
            this._endPointAddress = endPointAddress;
            this._userName = userName;
            this._password = password;
        }

        public DatiProtocolloLettoResponseType Leggi(int anno, int numero, string tipoRegistro)
        {
            using (var ws = this.CreaWebService())
            {
                try
                {

                    this._serializer.LogAndValidate("GetProtocolloRequestParams.txt", $"anno:{anno}, numero:{numero}, tipoRegistro:{tipoRegistro}", $"Inizio chiamata al metodo ws.getProtocollo({anno}, {numero}, {tipoRegistro})");

                    var responseXML = ws.getProtocollo(anno, numero, tipoRegistro);

                    this._serializer.LogAndValidate("GetProtocolloRequestParamsResponse.xml", responseXML, $"Fine chiamata al metodo ws.getProtocollo({anno}, {numero}, {tipoRegistro})");
                    Protocollo response;

                    using (var reader = new StringReader(responseXML))
                    {
                        XmlSerializer serializer = new XmlSerializer(typeof(Protocollo));
                        response = (Protocollo)serializer.Deserialize(reader);
                    }

                    return new DatiProtocolloLettoResponseType
                    {
                        IdProtocollo = response.Doc.IdDocumento,
                        AnnoProtocollo = response.Doc.Anno,
                        DataProtocollo = response.Doc.Data,
                        NumeroProtocollo = response.Doc.Numero,
                        Oggetto = response.Doc.Oggetto,
                        AnnoNumeroPratica = response.Doc.FascicoloAnno,
                        NumeroPratica = response.Doc.FascicoloNumero,
                        Classifica = response.Doc.ClassCod,
                        Origine = this.TipoProvenienzaToOrigine(response.Doc.Modalita),
                        MittentiDestinatari = this.SetMittDestOut(response.Rapporti),
                        InCaricoA = null,
                        InCaricoA_Descrizione = response.Smistamenti?.Smistamento
                                                    .Where(x => x.StatoSmistamento == "IN_CARICO")
                                                    .Select(x => x.DesUfficioSmistamento)
                                                    .FirstOrDefault(),
                        Allegati = this.SetAllegati(response)
                    };
                }
                catch (Exception ex)
                {
                    this._serializer.LogAndValidate(
                        "GetProtocolloError.txt",
                        ex.ToString(),
                        "Errore chiamata getProtocollo");

                    throw;
                }
                finally
                {
                    if (ws.State == CommunicationState.Faulted)
                        ws.Abort();

                    if (ws.State != CommunicationState.Closed)
                        ws.Close();
                }
            }                
        }



        private AllegatoResponseType[] SetAllegati(Protocollo protocollo)
        {
            List<AllegatoResponseType> retVal = new List<AllegatoResponseType>();

            if (protocollo.FilePrincipale != null)
            {
                retVal.Add(this.FileToAllOut(protocollo.FilePrincipale.File));
            }

            if (protocollo.Allegati != null && protocollo.Allegati.Allegato.Count > 0)
            {
                retVal.AddRange(protocollo.Allegati.Allegato.Select(x => this.FileToAllOut(x.FileAllegati.File)).ToArray());
            }

            return retVal.ToArray();
        }

        private AllegatoResponseType FileToAllOut(File file)
        {
            if (file == null)
            {
                return null;
            }

            return new AllegatoResponseType
            {
                IDBase = file.IdOggettoFile,
                Serial = file.FileName,
                TipoFile = null,
                Commento = file.FileName,
                ContentType = ""
            };
        }

        private MittDestOutType[] SetMittDestOut(Rapporti rapporti)
        {
            return rapporti.Rapporto?.Select(x => new MittDestOutType
            {
                CognomeNome = x.CognomeNome,
                IdSoggetto = null
            }).ToArray();
        }

        private string TipoProvenienzaToOrigine(string provenienza)
        {
            switch (provenienza)
            {
                case "ARR":
                    {
                        return ProtocolloConstants.COD_ARRIVO;
                    }
                case "PAR":
                    {
                        return ProtocolloConstants.COD_PARTENZA;
                    }
                case "INT":
                    {
                        return ProtocolloConstants.COD_INTERNO;
                    }
            }
            return null;
        }

        private EntiAooUtilitySoapBindingImplClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._endPointAddress);
                var binding = new BasicHttpBinding("defaultHttpBinding");
                binding.MessageEncoding = WSMessageEncoding.Mtom;
                binding.TransferMode = TransferMode.Buffered;

                binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.TransportCredentialOnly };

                binding.Security.Transport.ClientCredentialType = HttpClientCredentialType.Basic;
                var client = new EntiAooUtilitySoapBindingImplClient(binding, endPointAddress);
                client.ClientCredentials.UserName.UserName = this._userName;
                client.ClientCredentials.UserName.Password = this._password;

                return client;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
