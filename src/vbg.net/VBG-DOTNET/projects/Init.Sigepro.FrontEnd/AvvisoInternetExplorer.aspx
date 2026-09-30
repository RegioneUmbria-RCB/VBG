<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="AvvisoInternetExplorer.aspx.cs" Inherits="Init.Sigepro.FrontEnd.AvvisoInternetExplorer" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>

    <link href="~/vendor/bootstrap-3.3.6-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="~/css/jqueryui/ui-bootstrap/jquery-ui-1.10.0.custom.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/ionicons-2.0.1/css/ionicons.min.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/font-awesome-4.6.3/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
    <link href="~/styles/areariservata.css" type="text/css" rel="stylesheet" id="cssLink" />
    <link href="~/vendor/datatables/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="~/vendor/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" />

    <link href='~/extra-css-handler.ashx' type="text/css" rel="stylesheet" />

    <%= LoadScripts(new[] {
            "~/js/lib/jquery.js",
            "~/vendor/bootstrap-3.3.6-dist/js/bootstrap.min.js",
            "~/js/lib/jquery.ui.js",
            "~/vendor/bootstrap-validator-0.11.5/dist/validator.js",
            "~/vendor/datatables/js/jquery.dataTables.min.js",
            "~/vendor/datatables/js/dataTables.bootstrap.min.js",
            "~/vendor/bootstrap-datepicker/js/bootstrap-datepicker.min.js",
            "~/vendor/bootstrap-datepicker/locales/bootstrap-datepicker.it.min.js",
            "~/js/app/fix-bottoni.js",

    }) %>

    <style>
        .avviso-ie {
            margin-top: 15%;
        }

        .list {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container avviso-ie">
        <div class="panel panel-danger">
            <div class="panel-heading">Browser non supportato</div>
            <div class="panel-body">
                Per accedere al servizio è necessario utilizzare un browser che supporti i recenti standard web.<br />
                Ti consigliamo di utilizzare uno dei seguenti browser che puoi scaricare gratuitamente:                
                <ul class="list">
                    <li><a href="https://www.google.com/intl/it_it/chrome/" target="_blank">Google Chrome</a></li>
                    <li><a href="https://www.mozilla.org/it/firefox/new/" target="_blank">Mozilla Firefox</a></li>
                    <li><a href="https://www.opera.com/it" target="_blank">Opera</a></li>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>