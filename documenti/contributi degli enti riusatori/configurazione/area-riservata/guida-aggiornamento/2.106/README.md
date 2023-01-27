# 2.106

La versione 2.106 ha rotto la compatibilità con il framework .net 4.0 quindi:

- I server con Windows 2003 Server non sono più supportati
- Il framework 4.8 deve essere installato nel server

## Prerequisiti

Prima di iniziare l'attività di aggiornamento verificare che il framework 4.8 sia installato nel server. Eventualmente l'aggiornamento va coordinato con il CED del comune prima di procedere con l'installazione dell'area riservata.

Il framework 4.8 dovrebbe essere installato in tutte le versioni server con gli ultimi service pack aggiornati. Nel caso in cui non sia disponibile pianificare un aggiornamento con il CED prima di installare l'area riservata.

Per verificare la versione del framework .net installata utilizzare il software scaricabile a questo indirizzo: [https://github.com/jmalarcon/DotNetVersions/releases/download/v1.0.0/DotNetVersions.zip](https://github.com/jmalarcon/DotNetVersions/releases/download/v1.0.0/DotNetVersions.zip) e verificare che nell'output sia presente la versione 4.8

![Output di una macchina con framework 4.8 installato](./images/netversion-output.PNG)

Nel caso in cui il framework non sia installato pianificare un aggiornamento completo della macchina con il CED. Nel caso in cui ciò non sia possibile i pacchetti di installazione sono scaricabili all'indirizzo: [https://support.microsoft.com/it-it/topic/programma-di-installazione-offline-di-microsoft-net-framework-4-8-per-windows-9d23f658-3b97-68ab-d013-aa3c3e7495e0](https://support.microsoft.com/it-it/topic/programma-di-installazione-offline-di-microsoft-net-framework-4-8-per-windows-9d23f658-3b97-68ab-d013-aa3c3e7495e0) 

## Installazione del backend ASPNET

1. Scaricare da devel3 gli zip contenenti il backend ASPNET ( _2.106_aspnet.zip_ )
2. Effettuare un backup completo dell'applicazione
3. Eliminare **tutti** i files contenuti nella cartella **/aspnet**
4. Estrarre i files scaricati nella cartella **/aspnet**
5. Copiare il file **bindings.config** dalla cartella di backup del passo 1 alla cartella **/aspnet**
6. Rinominare il file **web.config_** in **web.config**
7. Aggiungere al file .hosts della macchina le seguenti entries

    > 12.34.56.78		vbg.security

    > 12.34.56.78		vbg.backend

    > 12.34.56.78		vbg.stc

    Sostituendo ai valori 12.34.56.78 gli indirizzi IP dei server del security, backend e stc
    
8. Copiare la sezione log4net dal web.config che si trova nella cartella di backup creata nel passo 1 e inserirla nel web.config che si trova nella cartella **/aspnet**
9. Verificare eventuali differenze tra il web.config della cartella di backup e quella dell'area riservata nelle sezioni  &lt;appSettings&gt;

## Installazione Area Riservata

1. Scaricare da devel3 gli zip contenenti l'area riservata ( _2.106_areariservata.zip_ ) **NON UTILIZZARE LA VERSIONE LITE**
1. Effettuare un backup completo dell'applicazione
1. Eliminare **tutti** i files contenuti nella cartella **/area-riservata**
1. Estrarre i files scaricati nella cartella **/area-riservata**
1. Copiare il file **bindings.config** dalla cartella di backup del passo 1 alla cartella **/area-riservata**
1. Rinominare il file **web.config_** in **web.config**
1. Aggiungere al file .hosts della macchina le seguenti entries

    > 12.34.56.78		vbg.security

    > 12.34.56.78		vbg.backend

    > 12.34.56.78		vbg.stc

    Sostituendo ai valori 12.34.56.78 gli indirizzi IP dei server del security, backend e stc

1. Copiare la sezione log4net dal web.config che si trova nella cartella di backup creata nel passo 1 e inserirla nel web.config che si trova nella cartella **/area-riservata**

1. Verificare eventuali differenze tra il web.config della cartella di backup e quella dell'area riservata nelle sezioni &lt;sigepro&gt; e &lt;appSettings&gt;
