namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Helper
{
    public static class CommonColumns
    {
        #region Leggenda DocumentField

        //1 Flag F/U che indica se si tratta di unità documentaria o folder 
        //2 Identificativo dell’unità documentaria o folder(ID_UD o ID_FOLDER)
        //3 Nome dell'unità documentaria o folder 
        //4 Estremi di registrazione/numerazione dell'unità documentaria (se ne ha più di una viene presa la prima registrazione/numerazione presente
        //andando nel seguente ordine: Protocollo Generale, Repertorio, Registrazione d'Emergenza, Altra numerazione esterna al sistema,
        //Numerazione interna al sistema) o estremi del fascicolo archivistico(anno, classificazione, n.ro progr, n.ro sottofasc. E n.ro inserto)

        //5 N.ro secondario del folder 
        //6 Valore di ordinamento(ORDER_BY_VALUE) del folder 
        //7 Data di arrivo o stesura del documento(a seconda che sia aquisito o prodotto) o data di apertura del folder(nel formato dato dal parametro di configurazione FMT_STD_DATA) 
        //8 Estremi di registrazione a Protocollo Generale dell'unità documentaria 
        //9 Data e ora di registrazione a Protocollo Generale dell'unità documentaria (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //10 Estremi di registrazione a Repertorio dell'unità documentaria 
        //11 Data e ora di registrazione a Repertorio dell'unità documentaria (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //12 Estremi di registrazione a Protocollo Particolare dell'unità documentaria 
        //13 Data e ora di registrazione a Protocollo Particolare dell'unità documentaria (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //14 Estremi di altra numerazione dell'unità documentaria che non sia nè un protocollo nè un repertorio 
        //15 Data e ora di numerazione dell'unità documentariache non sia nè un protocollo nè un repertorio (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //16 Data di chiusura del folder 
        //17 Livello di evidenza dell'unità documentaria / folder 
        //18 Descrizione/oggetto del documento / folder
        //19 Nominativi esterni legati al documento / folder(mittenti, destinatari, ecc)
        //20 Tipo di provenienza dell'unità documentaria: E = in Entrata, U = in Uscita, I = tra uffici
        //21 N.ro ultima versione elettronica del documento primario che è valida e visibile all'utente. Se non presente significa che non c'è versione elettronica valida visibile all'utente di lavoro
        //22 Nome del file di ultima versione elettronica del documento primario che è valida e visibile all'utente
        //23 N.ro di allegati dell'unità documentaria
        //24 N.ro di unità documentarie direttamente contenute nel folder
        //25 N.ro di sub-folder direttamente contenuti nel folder
        //26 Data e ora a partire da cui l'unità documentaria / folder si trova in stato di lock (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP)
        //27 Descrizione dell'utente che ha attualmente un lock sull'unità documentaria / folder
        //28 N.ro di folder di diretta appartenenza dell'unità documentaria / folder visibili all'utente di lavoro(diversi dal folder eventuale in cui si sta cercando)
        //29 Id.del folder di diretta appartenenza se ve n'è uno solo visibile all'utente di lavoro(e contenuto nell’eventuale folder in cui si sta cercando se specificato)
        //30 Path del folder di diretta appartenenza se ve n'è uno solo visibile all'utente di lavoro(e contenuto nell’eventuale folder in cui si sta cercando se specificato)
        //31 Id.del tipo del documento primario o del folder
        //32 Nome del tipo del documento primario o del folder
        //33 Id.del documento primario(ID_DOC) dell’unità documentaria
        //34 Id.della libreria di appartenenza
        //35 Data e ora di invio(valorizzata solo se si cerca negli "Inviati" dell'utente) (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //36 Data e ora di eliminazione da una sezione dell'area di lavoro (valorizzata solo se si cerca negli "Eliminati" dell'utente) (nel formato dato dal parametro di configurazione FMT_STD_TIMESTMP) 
        //37 Sezione dell'area lavoro utente da cui il documento o folder è stato eliminato (valorizzata solo se si cerca negli "Eliminati" dell'utente) 
        //38 Destinatari dell'invio (valorizzati solo se si cerca negli "Inviati" dell'utente)
        //39 Non utilizzata
        //40 Serve per indicare cosa il nodo rappresenta tra: FORUM = Un forum; THREAD = Un thread di discussione; CLASSIF = Una classificazione;
        //        FASC_TITOLARIO = Un fascicolo di titolario; SOTTOFASC_TITOLARIO = Un sottofascicolo di titolario; INSERTO_TITOLARIO = Un inserto
        //di un sottofascicolo di titolario; AVVIO_THREAD = L'avvio di un thread di discussione; RISPOSTA_THREAD = Una risposta/contributo in un
        //thread di discussione

        //41 Azioni possibili sull’unità documentaria / folder.E' una stringa di 1( = consentita) o 0( = non consentita) in cui ogni posizione corrisponde ad un'azione:
        //      1: Apertura di un nuovo sub-folder
        //      2: Inserimento di un nuovo documento
        //      3: Copia in altro folder
        //      4: Spostamento in altro folder
        //      5: Incolla/inserimento di un sub-folder
        //      6: Incolla/inserimento di un documento già esistente
        //      7: Modifica
        //      8: Cancellazione
        //      9: Lock
        //      10: Unlock
        //      11: Apertura sub-forum
        //      12: Apertura nuovo thread di discussione
        //      13: Incolla/inserimento di un forum già esistente
        //      14: Incolla/inserimento di un thread di discussione già esistente
        //      15: Aggiunta risposta/contributo ad un thread di discussione
        //      16: Aggiunta ai preferiti
        //      17: Eliminazione dai preferiti
        //      18: Selezione
        //      19: Apertura/scarico file primario
        //42 Assegnatario/i(solo per competenza, nel caso di unità documentaria) dell'unità doc./folder
        //43 Timestamp di creazione del folder/unità documentaria(nel formato del parametro di configurazione FMT_STD_TIMESTAMP)
        //44 Nome dell'utente di creazione del folder/unità documentaria
        //45 Timestamp di ultima modifica del folder/unità documentaria(nel formato del parametro di configurazione FMT_STD_TIMESTAMP)
        //46 Nome dell'utente di ultima modifica del folder/unità documentaria
        //47 Tipo, oggetto + dati di trace sul folder / unità documentaria
        //48 Data e ora di ultimo accesso(in visualizzazione o aggiornamento o per scarico di file) da parte dell'utente di lavoro (nel formato del parametro di configurazione FMT_STD_TIMESTMP)
        //53 Descrizione dello stato principale del documento/folder
        //54 Descrizione dello stato di dettaglio del documento/folder
        //55 Data e ora di ultimo aggiornamento dello stato del documento/folder(nel formato del parametro di configurazione FMT_STD_TIMESTMP)
        //56 Path relativo - a partire dal folder di ricerca - del folder di diretta appartenenza(valorizzato solo se ve n'è uno solo visibile all'utente di lavoro e contenuto nel folder di ricerca)
        //57 Parole chiave
        //58 Flag 1/0 che indica se il documento è stato letto o meno dall’utente dopo l’ultima notifica (valorizzato solo quando si cerca nelle news)
        //59 Livelli dell’unità operativa che in eGrammata deve essere proposta come mittente quando si registra il documento
        //60 Data e ora di ultima assegnazione/notifica all'utente connesso (è valorizzato solo quando si cerca nelle NEWS; è espresso nel formato del parametro di configurazione FMT_STD_TIMESTMP)
        //61 Livello di priorità(numerico: 1=Alta, 0 =Normale, -1 = Bassa) degli invii/notifiche successivi all'ultimo visto e all'ultima eliminazione dalle news(è valorizzato solo quando si cerca nelle NEWS)
        //62 Estremi degli invii/notifiche successivi all'ultimo visto e all'ultima eliminazione dalle news: Mittente/i, Messaggio/i, Data e ora di invio/notifica, Destinatario/i, Motivo, Priorità
        //(è valorizzato solo quando si cerca nelle NEWS)

        //63 N.ro di notifiche di condivisione presenti sul documento/folder e destinate all'utente di lavoro/connesso
        //(è valorizzato solo quando si cerca nelle NEWS)

        //64 N.ro di osservazioni presenti sul documento/folder e destinate all'utente di lavoro/connesso
        //(è valorizzato solo quando si cerca nelle NEWS)

        //65 N.ro di notifiche automatiche generate dal sistema presenti sul documento/folder e destinate all'utente di lavoro/connesso
        //(è valorizzato solo quando si cerca nelle NEWS)

        //66 N.ro di notifiche di condivisione presenti sul documento/folder, destinate all'utente di lavoro/connesso e da lui non ancora lette
        //(è valorizzato solo quando si cerca nelle NEWS)

        //67 N.ro di osservazioni presenti sul documento/folder, destinate all'utente di lavoro/connesso e da lui non ancora lette
        //(è valorizzato solo quando si cerca nelle NEWS)

        //68 N.ro di notifiche automatiche generate dal sistema presenti sul documento/folder, destinate all'utente di lavoro/connesso e da lui non ancora lette
        //(è valorizzato solo quando si cerca nelle NEWS)

        //69 Path e nome del/i folder di appartenenza visibili all'utente di lavoro (se più di uno separati da ;)
        //70 Nome del/i folder di appartenenza visibili all'utente di lavoro (se più di uno separati da ;)

        #endregion

        public enum DocumentField
        {
            FlagFU = 1,
            IdUnitOrFolder = 2,
            Name = 3,
            RegistrationDetails = 4,
            SecondaryFolderNumber = 5,
            OrderByValue = 6,
            DocumentOrFolderDate = 7,
            GeneralProtocolDetails = 8,
            GeneralProtocolTimestamp = 9,
            RepertoireDetails = 10,
            RepertoireTimestamp = 11,
            ParticularProtocolDetails = 12,
            ParticularProtocolTimestamp = 13,
            OtherNumberingDetails = 14,
            OtherNumberingTimestamp = 15,
            FolderClosureDate = 16,
            EvidenceLevel = 17,
            Description = 18,
            ExternalNames = 19,
            OriginType = 20,
            LastValidVersionNumber = 21,
            LastValidVersionFileName = 22,
            AttachmentsCount = 23,
            DirectContainedUnits = 24,
            DirectContainedSubfolders = 25,
            LockTimestamp = 26,
            LockingUserDescription = 27,
            DirectParentFoldersCount = 28,
            DirectParentFolderId = 29,
            DirectParentFolderPath = 30,
            PrimaryDocOrFolderTypeId = 31,
            PrimaryDocOrFolderTypeName = 32,
            PrimaryDocumentId = 33,
            LibraryId = 34,
            SentTimestamp = 35,
            DeletionTimestamp = 36,
            DeletionSection = 37,
            SentRecipients = 38,
            Unused = 39,
            NodeType = 40,
            PossibleActions = 41,
            Assignees = 42,
            CreationTimestamp = 43,
            CreatorUserName = 44,
            LastModificationTimestamp = 45,
            LastModifierUserName = 46,
            TypeObjectTraceData = 47,
            LastAccessTimestamp = 48,
            MainStateDescription = 53,
            DetailedStateDescription = 54,
            LastStateUpdateTimestamp = 55,
            RelativePathFromSearchFolder = 56,
            Keywords = 57,
            ReadFlag = 58,
            OperationalUnitLevels = 59,
            LastAssignmentOrNotificationTimestamp = 60,
            PriorityLevel = 61,
            NotificationsDetails = 62,
            SharingNotificationsCount = 63,
            ObservationsCount = 64,
            SystemGeneratedNotificationsCount = 65,
            UnreadSharingNotificationsCount = 66,
            UnreadObservationsCount = 67,
            UnreadSystemGeneratedNotificationsCount = 68,
            ParentFoldersPath = 69,
            ParentFoldersNames = 70
        }
    }
}
