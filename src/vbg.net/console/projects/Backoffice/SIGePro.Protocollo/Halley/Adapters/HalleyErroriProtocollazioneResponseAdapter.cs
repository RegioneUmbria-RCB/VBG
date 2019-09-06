using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.HalleyProtoService;

namespace Init.SIGePro.Protocollo.Halley.Adapters
{
    public class HalleyErroriProtocollazioneResponseAdapter
    {
        Dictionary<string, string> _dic;

        public HalleyErroriProtocollazioneResponseAdapter()
        {
            _dic = new Dictionary<string, string>
            {
                {"0", ""}, 
                {"-107", "Documento principale non presente nell'XML di profilazione"}, 
                {"-102", "Id del documento non esistente (documento specificato non inserito)"}, 
                {"-555", "Codice Titolario non presente su database"}, 
                {"-108", "In un flusso in entrata il codice del mittente è legato a più persone nell'anagrafica"}, 
                {"-109", "Errore di scrittura nel database, per l'inserimento di un nuovo mittente nei flussi in entrata"}, 
                {"-110", "In un flusso in entrata il codice destinatario è legato a più uffici"}, 
                {"-111", "In un flusso in entrata il codice destinatario non è legato ad un ufficio presente nel database"}, 
                {"-112", "In un flusso in uscita il codice destinatario è legato a più persone nell'anagrafica"}, 
                {"-113", "Errore di scrittura nel database, per l'inserimento di un nuovo destinatario nei flussi in uscita"}, 
                {"-114", "In un flusso in uscita il codice mittente è legato a più uffici"}, 
                {"-115", "In un flusso in uscita il codice mittente non è legato ad un ufficio presente nel database"}, 
                {"993", "Errore di scrittura nel database durante la fase di protocollazione"}, 
                {"998", "Errore di scrittura nel database durante la fase di protocollazione"}, 
                {"997", "Errore di scrittura nel database durante la fase di protocollazione"}, 
                {"300", "Errore di inserimento nel sistema di gestione documentale Halley per il documento principale "}, 
                {"301", "Errore di inserimento nel sistema di gestione documentale Halley per il documento principale"}, 
                {"302", "Errore di inserimento nel sistema di gestione documentale Halley per il documento principale"}, 
                {"303", "Errore di inserimento nel sistema di gestione documentale Halley per il documento principale"}, 
                {"304", "Errore di inserimento nel sistema di gestione documentale Halley per il documento principale"}, 
                {"992", "Errore di scrittura nel database nella fase di collegamento fra documento principale e protocollo"}, 
                {"310", "Errore di inserimento nel sistema di gestione documentale Halley per un allegato"}, 
                {"311", "Errore di inserimento nel sistema di gestione documentale Halley per un allegato"}, 
                {"312", "Errore di inserimento nel sistema di gestione documentale Halley per un allegato"}, 
                {"313", "Errore di inserimento nel sistema di gestione documentale Halley per un allegato"}, 
                {"314", "Errore di inserimento nel sistema di gestione documentale Halley per un allegato"}, 
                {"994", "Errore di scrittura nel database nella fase di collegamento fra protocollo ed anagrafica"}, 
                {"995", "Errore di scrittura nel database nella fase di collegamento fra protocollo ed ufficio"}, 
                {"996", "Errore di scrittura nel database nella fase di collegamento fra un allegato e protocollo"}
            };

            
        }


        public KeyValuePair<string, string> Adatta(string codice)
        {
            var key = _dic.Where(x => x.Key == codice).FirstOrDefault();

            if (key.Key == null)
                key = new KeyValuePair<string, string>(codice, "NON DEFINITO");
            
            return key;
        }
    }
}
