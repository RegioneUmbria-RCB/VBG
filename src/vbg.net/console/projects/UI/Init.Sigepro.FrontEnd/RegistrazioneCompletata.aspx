<%@ Page Language="C#" AutoEventWireup="True" CodeBehind="RegistrazioneCompletata.aspx.cs" Inherits="Init.Sigepro.FrontEnd.RegistrazioneCompletata" %>

<!DOCTYPE html>
<html>
<head runat="server">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; utf-8" />
    <title>Registrazione effettuata correttamente</title>
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,400italic,600,600italic,700,700italic,900,300italic,300,200italic,200' rel='stylesheet' type='text/css'>
    <link href="~/vendor/bootstrap-3.3.6-dist/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link href="~/css/jqueryui/ui-bootstrap/jquery-ui-1.10.0.custom.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/ionicons-2.0.1/css/ionicons.min.css" type="text/css" rel="stylesheet" />
    <link href="~/vendor/font-awesome-4.6.3/css/font-awesome.min.css" type="text/css" rel="stylesheet" />

    <link href="~/styles/areariservata.css" type="text/css" rel="stylesheet" id="cssLink" />

    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/jquery.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/vendor/bootstrap-3.3.6-dist/js/bootstrap.min.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/vendor/bootstrap-validator-0.11.5/dist/validator.js")%>'></script>
    <script type="text/javascript" src='<%= ResolveClientUrl("~/js/lib/requirejs.js")%>'></script>

    <script type="text/javascript">
        var paths = {
            "app": "../app",
        };

        if (!window.jQuery) {
            paths.jquery = ['../lib/jquery'];
        } else {
            define('jquery', [], function () { return window.jQuery; });
        }

        requirejs.config({
            "baseUrl": '<%= ResolveClientUrl("~/js/lib") %>',
        "waitSeconds": 0,	// Rimuove il timeout per le installazioni più lente
        "paths": paths,
        "shim": {
            "jquery.ui": ["jquery"],
            "jquery.tooltip.fix": ["jquery"],
            "jquery.dropdown": ["jquery"],
            "jquery.form": ["jquery"],
            "jquery.hoverbox": ["jquery"],
            "jquery.stickyfloat": ["jquery"]
        }
    });

    require(['app/fix-bottoni', 'jquery'], function (fixBottoni, $) {
        fixBottoni.applyFix();

        var banner = $('.banner'),
            nav = $('.main-nav-bar'),
            bannerHeight = banner.height();

        nav.css('margin-top', bannerHeight);

        $(window).on('scroll', function () {
            var scroll = $(this).scrollTop(),
                amount = bannerHeight - scroll;

            if (amount < 0) {
                amount = 0;
            }

            nav.css('margin-top', amount);
        });
    })

    </script>

</head>
<body>
    <form id="form1" runat="server">
        <header class="banner">
            <div class="banner-superiore">
                <div class="container">
                    <div class="row">
                        <div class="col-md-2 logo-comune hidden-xs">
                                <img src='<%= ResolveClientUrl("~/public/handlers/loghi/logo-area-riservata.ashx?&Software=" + Software + "&IdComune=" + IdComune) %>'/ >
                        </div>

                        <div class="col-md-8 titolo-area-riservata">
                            <h1>Area riservata</h1>

                            <h4>
                                <asp:Literal runat="server" ID="lblNomeComune2">Nome Comune</asp:Literal>
                            </h4>
                        </div>

                        <div class="col-md-2 logo-regione">
                            <img src='<%= ResolveClientUrl("~/public/handlers/loghi/logo-regione.ashx?&Software=" + Software + "&IdComune=" + IdComune) %>' />
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <nav class="navbar navbar-default navbar-fixed-top main-nav-bar">
        </nav>

        <div class="container">
            <h1>Registrazione completata</h1>

            <asp:Label ID="lblTesto" runat="server" Text="Richiesta di accesso al sistema inviata correttamente: a breve le verranno comunicate, all’indirizzo di posta elettronica indicato in fase di registrazione, le credenziali di accesso">
            </asp:Label>
            <div class="bottoni">
                <input type="submit" onclick="self.close(); return false;" value="Chiudi finestra" />
            </div>
        </div>
    </form>
</body>
</html>
