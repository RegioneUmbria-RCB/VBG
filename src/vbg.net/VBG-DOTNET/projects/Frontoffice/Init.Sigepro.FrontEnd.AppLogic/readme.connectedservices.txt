 * ATTENZIONE! il connected service dei dati dinamici è stato Generato manualmente dal comando 
 * 
 *      dotnet-svcutil http://localhost:2640/sigepro.net/WebServices/WsAreaRiservata/WcfServices/DatiDinamici/WsDatiDinamici.svc -syn -r "projects\Backoffice\SIGePro.Manager.DTO\bin\Debug\netstandard2.0\SIGePro.Manager.DTO.dll" -r "projects\Utils\PersonalLib2\bin\Debug\netstandard2.0\PersonalLib2.dll" -n "*,Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici" -d "projects\Frontoffice\Init.Sigepro.FrontEnd.AppLogic\Connected Services\Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici"
 * 
 * Perché nella gestione dei connected services di VS2022 (e anche 2019) non si possono ancora riutilizzare i tipi definiti negli assembly referenziati.
 *  (vd. https://github.com/dotnet/wcf/issues/3812)
 * 
 * il comando va lanciato nella root del progetto (es. dentro "C:\sviluppo\sigepro\trunk\")