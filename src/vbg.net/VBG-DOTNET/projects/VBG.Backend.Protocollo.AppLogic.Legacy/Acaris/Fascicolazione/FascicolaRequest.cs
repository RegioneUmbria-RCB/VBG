using Init.SIGePro.Manager.Logic.GestioneContesti;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Fascicolazione
{
    public class FascicolaRequest
    {
        public ParametriRegoleInfo Configurazione { get; set; }
        public string IdProtocollo { get; set; }
        public string NumeroProtocollo { get; set; }
        public DateTime? DataProtocollo { get; set; }
        public AmbitoProtocollazioneEnum TipoAmbito { get; set; }
        public ProtocolloBase Protocollo { get; set; }
        public string IdentificativoUtente { get; internal set; }
        public string CodiceDossier { get; internal set; }
        public string TemplateDescrizioneDossier { get; internal set; }
        public Dictionary<string, List<Dyn2Dato>> DatiContestoDossier { get; internal set; }
    }
}
