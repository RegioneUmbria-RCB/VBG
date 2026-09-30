
using System.Text.RegularExpressions;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.TrovaDocFolder;
using VBG.Backend.Protocollo.AppLogic.Core.Auriga.UD.GetMetadataUd;
using static VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper.CommonColumns;

namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper
{


    public static class ResponseInfoAdapterHelper
    {
        public static List<UD.GetMetadataUd.ResponseInfo> FilterAndConvertToGetMetadataUdResponseInfo(Folder.TrovaDocFolder.ResponseInfo source, Dictionary<int, string> filtroColonnaValore)
        {
            if (source == null)
                return null;

            var retValue = new List<UD.GetMetadataUd.ResponseInfo>();

            foreach (var riga in source.ServiceResponse.Righe)
            {
                var dati = ConvertRigaToDatiUD(riga, filtroColonnaValore);

                if (dati != null)
                {
                    retValue.Add(new UD.GetMetadataUd.ResponseInfo
                    {
                        WsResult = source.WsResult,
                        WsError = source.WsError,
                        WarningMessage = source.WarningMessage,
                        DatiUD = dati
                    });
                }
            }

            return retValue;
        }

        private static DatiUD ConvertRigaToDatiUD(Riga riga, Dictionary<int, string> filtroColonnaValore)
        {
            if (riga == null)
                return null;

            if (filtroColonnaValore != null)
            {
                foreach (var colonna in riga.Colonne)
                {
                    if (filtroColonnaValore.ContainsKey(colonna.Nro))
                    {
                        if (colonna.Valore.Trim() != filtroColonnaValore[colonna.Nro].Trim())
                        {
                            // Non corrisponde al filtro quindi questa riga non viene presa in considerazione
                            return null;
                        }
                    }
                }
            }

            var datiUD = new DatiUD();
            SoggettoEsterno? soggettoEsterno = null;
            var isOrigineValorizzata = false;

            foreach (var colonna in riga.Colonne)
            {
                switch (colonna.Nro)
                {
                    case (int)DocumentField.IdUnitOrFolder:
                        datiUD.IdUD = colonna.Valore;
                        break;

                    case (int)DocumentField.GeneralProtocolDetails:
                        if (datiUD.RegistrazioneData == null)
                        {
                            datiUD.RegistrazioneData = new RegistrazioneNumerazioneType[1];
                            datiUD.RegistrazioneData[0] = new RegistrazioneNumerazioneType();
                        }

                        var splitted = colonna.Valore.Split('.');

                        datiUD.RegistrazioneData[0].NumReg = splitted[2];
                        break;

                    case (int)DocumentField.GeneralProtocolTimestamp:
                        if (datiUD.RegistrazioneData == null)
                        {
                            datiUD.RegistrazioneData = new RegistrazioneNumerazioneType[1];
                            datiUD.RegistrazioneData[0] = new RegistrazioneNumerazioneType();
                        }

                        datiUD.RegistrazioneData[0].DataOraReg = Convert.ToDateTime(colonna.Valore);
                        break;

                    case (int)DocumentField.RegistrationDetails:
                        // qui i valori della registrazione vengono dalla prima disponibile tra: (nell'ordine)
                        //Protocollo Generale, Repertorio, Registrazione d'Emergenza, Altra numerazione esterna al sistema, Numerazione interna al sistema
                        // quindi forse è meglio non prendere da qui i valori?
                        break;

                    case (int)DocumentField.CreationTimestamp:
                        datiUD.DataOraCreazione = colonna.Valore;
                        break;

                    case (int)DocumentField.Description:
                        datiUD.OggettoUD = new DatiUDOggettoUD();
                        datiUD.OggettoUD.Value = colonna.Valore;
                        break;

                    case (int)DocumentField.OriginType:
                        if (Enum.TryParse(colonna.Valore, out DatiUDTipoProvenienza result))
                        {
                            datiUD.TipoProvenienza = result;
                            isOrigineValorizzata = true;

                            if (soggettoEsterno != null)
                            {
                                AssegnaSoggettoEsterno(datiUD, soggettoEsterno);
                                soggettoEsterno = null;
                            }
                        }

                        break;

                    case (int)DocumentField.ExternalNames:
                        try
                        {
                            soggettoEsterno = new SoggettoEsterno(colonna.Valore);
                        }
                        catch
                        {
                            soggettoEsterno = null;
                        }


                        if (isOrigineValorizzata)
                        {
                            AssegnaSoggettoEsterno(datiUD, soggettoEsterno);
                            soggettoEsterno = null;
                        }

                        break;

                    case (int)DocumentField.Assignees:
                        datiUD.AssegnazioneInterna = new AssegnazioneInternaType[1];
                        datiUD.AssegnazioneInterna[0] = new AssegnazioneInternaType
                        {
                            Item = colonna.Valore
                        };

                        // valori che poi vengono parsati in:
                        //    InCaricoA = SetInCaricoA(), // id 
                        //    InCaricoA_Descrizione = SetInCaricoADescrizione(), // descrizione

                        break;
                }
            }

            return datiUD;
        }

        private static void AssegnaSoggettoEsterno(DatiUD datiUD, SoggettoEsterno soggettoEsterno)
        {
            switch (datiUD.TipoProvenienza)
            {
                case DatiUDTipoProvenienza.E:
                    if (datiUD.DatiEntrata == null)
                    {
                        datiUD.DatiEntrata = new DatiUDDatiEntrata();
                    }
                    datiUD.DatiEntrata.MittenteEsterno = new SoggettoEsternoType[1];
                    datiUD.DatiEntrata.MittenteEsterno[0] = new SoggettoEsternoType
                    {
                        CodiceFiscale = soggettoEsterno.CodiceFiscale,
                        Denominazione_Cognome = soggettoEsterno.Cognome,
                        Nome = soggettoEsterno.Nome,
                    };
                    break;

                case DatiUDTipoProvenienza.U:
                    if (datiUD.DatiUscita == null)
                    {
                        datiUD.DatiUscita = new DatiUDDatiUscita();
                    }
                    datiUD.DatiUscita.DestinatarioEsterno = new DestinatarioEsternoType[1];
                    datiUD.DatiUscita.DestinatarioEsterno[0] = new DestinatarioEsternoType
                    {
                        CodiceFiscale = soggettoEsterno.CodiceFiscale,
                        Denominazione_Cognome = soggettoEsterno.Cognome,
                        Nome = soggettoEsterno.Nome,
                    };
                    break;

                case DatiUDTipoProvenienza.I:
                    // non ho trovato valori per nominativi interni

                    break;
            }
        }



        private class SoggettoEsterno
        {
            public string CodiceFiscale { get; private set; }
            public string Cognome { get; private set; }
            public string Nome { get; private set; }
            public string Settore { get; private set; }

            private static readonly Regex regex = new Regex(
                @"^(.*?)(?:\s*\(C\.F\.\s*([A-Z0-9]+)\))?\s*;\s*(.*)?$|^(.*?);\s*(.*?)(?:\s*\(C\.F\.\s*([A-Z0-9]+)\))?$",
                RegexOptions.Compiled | RegexOptions.IgnoreCase
            );

            public SoggettoEsterno(string input)
            {
                if (string.IsNullOrWhiteSpace(input))
                    throw new ArgumentException("La stringa di input non può essere vuota o nulla.", nameof(input));

                var match = regex.Match(input);
                if (!match.Success)
                    throw new FormatException("Formato della stringa non valido.");

                if (!string.IsNullOrWhiteSpace(match.Groups[2].Value)) // Caso "Nome Cognome (C.F.) ; Settore"
                {
                    var nomeCompleto = match.Groups[1].Value.Trim();
                    (this.Cognome, this.Nome) = this.EstraiCognomeENome(nomeCompleto);
                    this.CodiceFiscale = match.Groups[2].Value.Trim();
                    this.Settore = match.Groups[3].Value?.Trim();
                }
                else if (!string.IsNullOrWhiteSpace(match.Groups[6].Value)) // Caso "Settore ; Nome Cognome (C.F.)"
                {
                    var nomeCompleto = match.Groups[5].Value.Trim();
                    (this.Cognome, this.Nome) = this.EstraiCognomeENome(nomeCompleto);
                    this.CodiceFiscale = match.Groups[6].Value.Trim();
                    this.Settore = match.Groups[4].Value?.Trim();
                }
            }

            private (string Cognome, string Nome) EstraiCognomeENome(string nomeCompleto)
            {
                var parti = nomeCompleto.Split(new[] { ' ' }, 2, StringSplitOptions.RemoveEmptyEntries);
                if (parti.Length < 2)
                    throw new FormatException($"Nome incompleto: '{nomeCompleto}'");

                return (parti[0], parti[1]); // Presupponiamo che il cognome sia il primo
            }

            public override string ToString()
            {
                return $"Cognome: {this.Cognome}, Nome: {this.Nome}, Codice Fiscale: {this.CodiceFiscale}, Settore: {this.Settore}";
            }
        }


    }
}
